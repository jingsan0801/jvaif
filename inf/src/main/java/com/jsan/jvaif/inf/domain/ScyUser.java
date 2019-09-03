package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 用户
 * @author: Stone
 * @create: 2019-05-30 00:40
 **/
@Data
@TableName("t_scy_user")
public class ScyUser {
    @TableId(type = IdType.UUID)
    private String id;
    private String userName;
    private String password;
    private String salt;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}
