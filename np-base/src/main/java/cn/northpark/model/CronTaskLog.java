package cn.northpark.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务执行日志实体
 * @author bruce
 */
@Data
public class CronTaskLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long taskId;
    private String taskName;
    private String executeType; // AUTO-自动，MANUAL-手动
    private String executeParams;
    private Date startTime;
    private Date endTime;
    private Long duration; // 执行耗时（毫秒）
    private String status; // RUNNING-运行中，SUCCESS-成功，FAILED-失败
    private String errorMsg;
    private String logContent;
    private String createBy;
}