package cn.northpark.cron;

import cn.northpark.mapper.CronTaskMapper;
import cn.northpark.model.CronTask;
import cn.northpark.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 动态定时任务调度管理器
 * 支持运行时动态添加、删除、修改定时任务
 * @author bruce
 */
@Slf4j
@Component
@EnableScheduling
public class CronScheduleManager {

    @Autowired
    private CronTaskMapper cronTaskMapper;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    // 存储已注册的任务 key: taskId, value: ScheduledFuture
    private Map<Long, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    /**
     * 应用启动时初始化所有启用的定时任务
     */
    @PostConstruct
    public void initializeTasks() {
        try {
            log.info("初始化定时任务调度...");
            List<CronTask> tasks = cronTaskMapper.findAll();
            
            for (CronTask task : tasks) {
                if (task.getStatus() == 1) {  // 只加载启用的任务
                    registerTask(task);
                }
            }
            log.info("定时任务调度初始化完成，共加载 {} 个任务", tasks.size());
        } catch (Exception e) {
            log.error("初始化定时任务失败", e);
        }
    }

    /**
     * 注册一个定时任务
     */
    public void registerTask(CronTask task) {
        try {
            if (task.getCronExpression() == null || task.getCronExpression().trim().isEmpty()) {
                log.warn("任务 {} 没有配置 Cron 表达式，跳过注册", task.getTaskName());
                return;
            }

            // 移除已存在的任务
            if (scheduledTasks.containsKey(task.getId())) {
                unregisterTask(task.getId());
            }

            // 创建任务
            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> executeTask(task),
                    new CronTrigger(task.getCronExpression())
            );

            scheduledTasks.put(task.getId(), future);
            log.info("定时任务已注册: {} (cron: {})", task.getTaskName(), task.getCronExpression());

        } catch (Exception e) {
            log.error("注册定时任务失败: {}", task.getTaskName(), e);
        }
    }

    /**
     * 注销一个定时任务
     */
    public void unregisterTask(Long taskId) {
        try {
            ScheduledFuture<?> future = scheduledTasks.get(taskId);
            if (future != null) {
                future.cancel(false);
                scheduledTasks.remove(taskId);
                log.info("定时任务已注销, taskId: {}", taskId);
            }
        } catch (Exception e) {
            log.error("注销定时任务失败, taskId: {}", taskId, e);
        }
    }

    /**
     * 执行任务（通过反射调用）
     */
    private void executeTask(CronTask task) {
        try {
            Object bean = SpringContextUtils.getBean(task.getTaskClass());
            Method method = bean.getClass().getMethod(task.getTaskMethod());
            
            log.info("执行定时任务: {} ({})", task.getTaskName(), task.getTaskClass());
            method.invoke(bean);
            log.info("定时任务执行成功: {}", task.getTaskName());
            
        } catch (Exception e) {
            log.error("定时任务执行失败: {}", task.getTaskName(), e);
        }
    }

    /**
     * 更新任务（删除旧的，注册新的）
     */
    public void updateTask(CronTask task) {
        unregisterTask(task.getId());
        if (task.getStatus() == 1) {
            registerTask(task);
        }
    }

    /**
     * 切换任务状态
     */
    public void toggleTaskStatus(Long taskId) {
        CronTask task = cronTaskMapper.findById(taskId);
        if (task != null) {
            if (task.getStatus() == 1) {
                unregisterTask(taskId);
                task.setStatus(0);
            } else {
                task.setStatus(1);
                registerTask(task);
            }
            cronTaskMapper.update(task);
        }
    }
}