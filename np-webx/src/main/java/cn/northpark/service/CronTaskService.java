package cn.northpark.service;

import cn.northpark.model.CronTask;
import cn.northpark.model.CronTaskLog;

import java.util.List;
import java.util.Map;

/**
 * 定时任务管理服务
 * @author bruce
 */
public interface CronTaskService {

    /**
     * 查询所有定时任务
     */
    List<CronTask> findAllTasks();

    /**
     * 根据ID查询定时任务
     */
    CronTask findTaskById(Long id);

    /**
     * 新增定时任务
     */
    void addTask(CronTask cronTask);

    /**
     * 编辑定时任务
     */
    void updateTask(CronTask cronTask);

    /**
     * 删除定时任务
     */
    void deleteTask(Long id);

    /**
     * 禁用/启用定时任务
     */
    void toggleTaskStatus(Long id);

    /**
     * 手动触发定时任务
     */
    Map<String, Object> executeTask(Long taskId, Integer startPage, Integer endPage, String executeBy);

    /**
     * 查询任务执行日志（分页）
     */
    Map<String, Object> findTaskLogs(Long taskId, Integer page, Integer pageSize);

    /**
     * 保存任务执行日志
     */
    Long saveTaskLog(CronTaskLog cronTaskLog);

    /**
     * 更新任务执行日志
     */
    void updateTaskLog(CronTaskLog cronTaskLog);
}