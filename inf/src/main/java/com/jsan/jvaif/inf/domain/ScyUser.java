package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: 用户
 * @author: Stone
 * @create: 2019-05-30 00:40
 **/
@Data
@TableName("t_scy_user" )
public class ScyUser {
    private long id;
    private String name;
    private String password;

}
