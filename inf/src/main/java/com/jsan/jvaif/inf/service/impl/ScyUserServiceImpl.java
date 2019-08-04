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
import com.jsan.jvaif.inf.util.ResultUtil;
import com.jsan.jvaif.inf.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * @param name 用户名
     * @return scyUser
     */
    @Override
    public ScyUser getScyUserByName(String name) {
        return scyUserMapper.selectOne(new QueryWrapper<ScyUser>().eq("name", name));
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
        ScyUser queryRs = scyUserMapper.selectOne(new QueryWrapper<ScyUser>().eq("name", userName).eq("status",1));
        if (queryRs != null) {
            throw new BusinessException(ResultEnum.exception_userName_exists, userName);
        }
        ScyUser scyUser = new ScyUser();
        scyUser.setName(userName);
        //5位随机字符串作为salt
        String salt = MathUtil.getRandomString(5);
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
     * @param userName 用户名
     * @return 是否正常
     */
    @Override
    public boolean isValid(String userName) {
        ScyUser queryRs = this.getScyUserByName(userName);
        log.info("from isValid : {}", queryRs);
        if (queryRs == null) {
            return false;
        }
        return queryRs.getStatus() == PublicConstant.COMMON_VALID;

    }
}
