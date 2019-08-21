package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.common.util.MathUtil;
import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.domain.ScyRole;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.domain.shiro.ApiRealm;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.mapper.ScyUserMapper;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.service.ITokenService;
import com.jsan.jvaif.inf.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.Set;

import static com.jsan.jvaif.inf.constant.ResultEnum.*;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-08-01 21:58
 **/
@Service
@Slf4j
public class ScyUserServiceImpl extends ServiceImpl<ScyUserMapper, ScyUser> implements IScyUserService {

    @Resource
    private ScyUserMapper scyUserMapper;

    @Resource(name = "authTokenServiceImpl")
    private ITokenService tokenService;

    /**
     * 根据用户名获取用户实体类
     *
     * @param userName 用户名
     * @return scyUser
     */
    @Override
    public ScyUser getScyUserByName(String userName) {
        return scyUserMapper.selectOne(new QueryWrapper<ScyUser>().eq("user_name", userName));
    }

    /**
     * 新增一个用户实体
     *
     * @param userName 用户名
     * @param password 密码
     * @return scyUser
     */
    @Override
    public int addUser(String userName, String password) {
        ScyUser queryRs =
            scyUserMapper.selectOne(new QueryWrapper<ScyUser>().eq("user_name", userName).eq("status", 1));
        if (queryRs != null) {
            throw new BusinessException(ResultEnum.exception_user_exists, userName);
        }
        ScyUser scyUser = new ScyUser();
        scyUser.setUserName(userName);
        //5位随机字符串作为salt
        String salt = MathUtil.getRandomString(5);
        scyUser.setStatus(PublicConstant.COMMON_VALID);
        scyUser.setSalt(salt);
        scyUser.setPassword((String)shiroMd5(password, userName, salt));
        return scyUserMapper.insert(scyUser);
    }

    /**
     * md5加密
     * (userName+salt)作为salt,md5加密两次
     *
     * @param text     原始密码
     * @param userName 用户名
     * @param salt     salt
     * @return (userName + salt)作为salt, md5加密两次得到的字符串
     */
    @Override
    public String shiroMd5(Object text, String userName, String salt) {
        // 加密次数
        int hashIterations = 2;
        Object rs = new SimpleHash("Md5", text, (userName + salt), hashIterations);
        return rs.toString();
    }

    /**
     * 判断用户是否正常
     *
     * @param scyUser ScyUser
     * @return 是否正常
     */
    @Override
    public boolean checkUserStatus(ScyUser scyUser) {
        return scyUser.getStatus() == PublicConstant.COMMON_VALID;

    }

    /**
     * @param userName 用户名
     * @param password 密码
     * @return 校验是否通过
     */
    @Override
    public ScyUser checkUsernameAndPassword(String userName, String password) {
        ScyUser scyUser = getScyUserByName(userName);
        if (scyUser == null) {
            throw new AuthenticationException(ResultEnum.exception_user_notExists.getMsg());
        }
        String salt = scyUser.getSalt();
        // 计算密码
        String encodePassword = shiroMd5(password, userName, salt);
        if (encodePassword.equals(scyUser.getPassword())) {
            return scyUser;
        } else {
            return null;
        }
    }

    /**
     * @param userName 用户名
     * @param password 密码
     * @return Result
     */
    @Override
    public String login(String userName, String password) {
        ScyUser scyUser = checkUsernameAndPassword(userName, password);
        if (scyUser != null) {
            return tokenService.genToken(userName, scyUser.getPassword());
        }
        return null;
    }

    /**
     * 判断token是否有效
     *
     * @param token token
     * @return token是否有效
     */
    @Override
    public boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(exception_token_required);
        }

        String userName = JwtUtil.getClaim(token);
        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(exception_token_decode_fail);
        }

        // 从Redis中获取token
        String tokenFromRedis = tokenService.getToken(userName);
        if (!StringUtils.isEmpty(tokenFromRedis)) {
            if (!token.equals(tokenService.getToken(userName))) {
                throw new BusinessException(exception_token_illegal);
            }
        }

        // 如果redis中由于某种原因没有保存token, 则重新验证
        // 验证token有效性
        ScyUser scyUser = getScyUserByName(userName);
        if (scyUser == null) {
            throw new BusinessException(exception_user_notExists);
        }
        if (!checkUserStatus(scyUser)) {
            throw new BusinessException(exception_user_invalid);
        }
        if (!JwtUtil.verify(token, scyUser.getUserName(), scyUser.getPassword())) {
            throw new BusinessException(exception_token_illegal);
        }
        return true;
    }

    /**
     * 更新token
     *
     * @param token token
     * @return 新的token
     */
    @Override
    public String refreshToken(String token) {
        String userName = JwtUtil.getClaim(token);
        ScyUser scyUser = getScyUserByName(userName);
        if (checkUserStatus(scyUser)) {
            return tokenService.genToken(userName, scyUser.getPassword());
        }
        return null;
    }

    /**
     * 按用户名获取所属角色
     *
     * @param userName 用户名
     * @return 该用户名的所有角色信息
     */
    @Override
    public Set<ScyRole> getRoleSetByUserName(String userName) {
        return scyUserMapper.getRoleSetByUsername(userName);
    }

    /**
     * 按用户名获取权限集合
     *
     * @param userName 用户名
     * @return 该用户的所有权限信息
     */
    @Override
    public Set<ScyAuth> getAuthSetByUserName(String userName) {
        return scyUserMapper.getAuthSetByUserName(userName);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        RealmSecurityManager realmSecurityManager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        ApiRealm realm = (ApiRealm)realmSecurityManager.getRealms().iterator().next();
        // 退出登录时清空缓存中的权限, 避免修改权限后不能立即生效
        realm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
