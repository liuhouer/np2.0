package cn.northpark.service.impl;

import cn.northpark.cron.CronScheduleManager;
import cn.northpark.mapper.CronTaskLogMapper;
import cn.northpark.mapper.CronTaskMapper;
import cn.northpark.model.CronTask;
import cn.northpark.model.CronTaskLog;
import cn.northpark.service.CronTaskService;
import cn.northpark.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务管理服务实现
 * @author bruce
 */
@Slf4j
@Service
public class CronTaskServiceImpl implements CronTaskService {

    @Autowired
    private CronTaskMapper cronTaskMapper;

    @Autowired
    private CronTaskLogMapper cronTaskLogMapper;

    @Autowired
    private CronScheduleManager cronScheduleManager;

    @Override
    public List<CronTask> findAllTasks() {
        return cronTaskMapper.findAll();
    }

    @Override
    public CronTask findTaskById(Long id) {
        return cronTaskMapper.findById(id);
    }

    @Override
    public void addTask(CronTask cronTask) {
        cronTaskMapper.insert(cronTask);
        // 如果新任务是启用状态，立即注册到调度器
        if (cronTask.getStatus() == 1) {
            cronScheduleManager.registerTask(cronTask);
        }
    }

    @Override
    public void updateTask(CronTask cronTask) {
        cronTaskMapper.update(cronTask);
        // 更新调度器中的任务
        cronScheduleManager.updateTask(cronTask);
    }

    @Override
    public void deleteTask(Long id) {
        cronTaskMapper.deleteById(id);
        // 从调度器中移除任务
        cronScheduleManager.unregisterTask(id);
    }

    @Override
    public void toggleTaskStatus(Long id) {
        CronTask task = cronTaskMapper.findById(id);
        if (task != null) {
            // 更新调度器状态 + 任务状态
            cronScheduleManager.toggleTaskStatus(id);
        }
    }

    @Override
    public Map<String, Object> executeTask(Long taskId, Integer startPage, Integer endPage, String executeBy) {
        Map<String, Object> result = new HashMap<>();
        CronTask task = cronTaskMapper.findById(taskId);
        
        if (task == null) {
            result.put("success", false);
            result.put("message", "任务不存在");
            return result;
        }

        if (task.getSupportManual() == 0) {
            result.put("success", false);
            result.put("message", "该任务不支持手动触发");
            return result;
        }

        // 创建执行日志
        CronTaskLog taskLog = new CronTaskLog();
        taskLog.setTaskId(taskId);
        taskLog.setTaskName(task.getTaskName());
        taskLog.setExecuteType("MANUAL");
        
        // 记录页码参数
        if (startPage != null || endPage != null) {
            taskLog.setExecuteParams("{\"startPage\":" + (startPage != null ? startPage : "null") + 
                                     ",\"endPage\":" + (endPage != null ? endPage : "null") + "}");
        }
        
        taskLog.setStartTime(new Date());
        taskLog.setStatus("RUNNING");
        taskLog.setCreateBy(executeBy);
        
        cronTaskLogMapper.insert(taskLog);
        Long logId = taskLog.getId();

        // 异步执行任务
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            StringBuilder logContent = new StringBuilder();
            
            try {
                // 通过反射调用任务方法
                Object bean = SpringContextUtils.getBean(task.getTaskClass());
                
                logContent.append("开始执行任务: ").append(task.getTaskName()).append("\n");
                logContent.append("执行时间: ").append(new Date()).append("\n");
                
                // 根据任务类型和参数决定调用的方法和参数
                Method method;
                Object callResult;
                
                if ("cn.northpark.cron.RetMovieAndSendMQTask".equals(task.getTaskClass()) && 
                    (startPage != null || endPage != null)) {
                    // 电影爬取任务：支持页码范围参数
                    int start = startPage != null ? startPage : 1;
                    int end = endPage != null ? endPage : start;
                    logContent.append("参数: startPage=").append(start).append(", endPage=").append(end).append("\n");
                    method = bean.getClass().getMethod("retMovieWithPageRange", int.class, int.class);
                    callResult = method.invoke(bean, start, end);
                } else {
                    // 其他任务：调用无参方法
                    if (startPage != null || endPage != null) {
                        logContent.append("参数: startPage=").append(startPage).append(", endPage=").append(endPage)
                                  .append("（任务不支持参数，将忽略）\n");
                    }
                    method = bean.getClass().getMethod(task.getTaskMethod());
                    callResult = method.invoke(bean);
                }
                logContent.append("----------------------------------------\n");
                
                long duration = System.currentTimeMillis() - startTime;
                logContent.append("----------------------------------------\n");
                logContent.append("任务执行成功\n");
                logContent.append("耗时: ").append(duration).append("ms\n");
                
                // 更新日志状态为成功
                CronTaskLog updateLog = new CronTaskLog();
                updateLog.setId(logId);
                updateLog.setEndTime(new Date());
                updateLog.setDuration(duration);
                updateLog.setStatus("SUCCESS");
                updateLog.setLogContent(logContent.toString());
                cronTaskLogMapper.update(updateLog);
                
                log.info("手动触发任务成功: {}, logId: ", task.getTaskName(), logId);
                
            } catch (Exception e) {
                long duration = System.currentTimeMillis() - startTime;
                logContent.append("----------------------------------------\n");
                logContent.append("任务执行失败\n");
                logContent.append("错误信息: ").append(e.getMessage()).append("\n");
                
                // 更新日志状态为失败
                CronTaskLog updateLog = new CronTaskLog();
                updateLog.setId(logId);
                updateLog.setEndTime(new Date());
                updateLog.setDuration(duration);
                updateLog.setStatus("FAILED");
                updateLog.setErrorMsg(e.getMessage());
                updateLog.setLogContent(logContent.toString());
                cronTaskLogMapper.update(updateLog);
                
                log.error("手动触发任务失败: {}, logId: {}", task.getTaskName(), logId, e);
            }
        }).start();

        result.put("success", true);
        result.put("message", "任务已提交执行");
        result.put("logId", logId);
        return result;
    }

    @Override
    public Map<String, Object> findTaskLogs(Long taskId, Integer page, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (page - 1) * pageSize;
        List<CronTaskLog> logs = cronTaskLogMapper.findByTaskId(taskId, offset, pageSize);
        int total = cronTaskLogMapper.countByTaskId(taskId);
        
        result.put("logs", logs);
        result.put("total", total);
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("totalPages", (total + pageSize - 1) / pageSize);
        
        return result;
    }

    @Override
    public Long saveTaskLog(CronTaskLog cronTaskLog) {
        cronTaskLogMapper.insert(cronTaskLog);
        return cronTaskLog.getId();
    }

    @Override
    public void updateTaskLog(CronTaskLog cronTaskLog) {
        cronTaskLogMapper.update(cronTaskLog);
    }
}