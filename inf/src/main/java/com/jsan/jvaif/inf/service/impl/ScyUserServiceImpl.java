package com.jsan.jvaif.inf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsan.jvaif.common.util.MathUtil;
import com.jsan.jvaif.inf.constant.PublicConstant;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.domain.ScyUser;
import com.jsan.jvaif.inf.exption.BusinessException;
import com.jsan.jvaif.inf.mapper.ScyUserMapper;
import com.jsan.jvaif.inf.service.IScyUserService;
import com.jsan.jvaif.inf.util.JwtUtil;
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
    public Result addUser(String userName, String password) {
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
        int rs = scyUserMapper.insert(scyUser);
        return ResultUtil.success(rs);
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
            return JwtUtil.sign(userName, scyUser.getPassword());
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
        if (checkToken(token)) {
            String userName = JwtUtil.getClaim(token);
            ScyUser scyUser = getScyUserByName(userName);
            if (checkUserStatus(scyUser)) {
                //TODO: 旧的token不会失效
                return JwtUtil.sign(userName, scyUser.getPassword());
            }
        }
        return null;
    }

    void invalidToken(String userName, String token) {
    }
}
