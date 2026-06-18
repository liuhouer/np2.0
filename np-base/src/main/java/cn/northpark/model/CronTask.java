package cn.northpark.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务配置实体
 * @author bruce
 */
@Data
public class CronTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String taskName;
    private String taskClass;
    private String taskMethod;
    private String cronExpression;
    private String description;
    private Integer status; // 0-禁用，1-启用
    private Integer supportManual; // 0-否，1-是
    private Date createTime;
    private Date updateTime;
}