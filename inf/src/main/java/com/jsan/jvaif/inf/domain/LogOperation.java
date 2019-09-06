package com.jsan.jvaif.inf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 业务日志实体类
 * @author: jcwang
 * @create: 2019-09-06 17:38
 **/
@TableName("t_log_operation")
@Data
public class LogOperation {
    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 状态
     */
    private String status;
    /**
     * 执行人
     */
    private String createdBy;
    /**
     * 执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**
     * 操作模块
     */
    private String module;
    /**
     * 操作动作
     */
    private String action;
    /**
     * 操作描述
     */
    private String actionDesc;
    /**
     * 操作ip
     */
    private String ip;
    /**
     * 备注
     */
    private String note;
}
