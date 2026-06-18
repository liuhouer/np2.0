package cn.northpark.controller;

import cn.northpark.annotation.BruceOperation;
import cn.northpark.model.CronTask;
import cn.northpark.result.JsonResult;
import cn.northpark.service.CronTaskService;
import cn.northpark.threadLocal.RequestHolder;
import cn.northpark.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 定时任务管理控制器
 * @author bruce
 */
@Slf4j
@Controller
@RequestMapping("/admin/cron")
public class CronTaskController {

    @Autowired
    private CronTaskService cronTaskService;

    /**
     * 定时任务管理页面
     */
    @BruceOperation
    @RequestMapping("")
    public String cronTaskManage(ModelMap model, HttpServletRequest request) {
        List<CronTask> tasks = cronTaskService.findAllTasks();
        model.put("tasks", tasks);

        UserVO user = RequestHolder.getUserInfo(request);
        model.put("user", user);

        return "/page/admin/cron/cronManage";
    }

    /**
     * 获取定时任务列表（JSON）
     */
    @BruceOperation
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult getTaskList() {
        try {
            List<CronTask> tasks = cronTaskService.findAllTasks();
            return JsonResult.ok().put("tasks", tasks);
        } catch (Exception e) {
            log.error("获取定时任务列表失败", e);
            return JsonResult.error("获取定时任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 手动触发定时任务
     */
    @BruceOperation
    @RequestMapping("/execute/{taskId}")
    @ResponseBody
    public JsonResult executeTask(@PathVariable Long taskId,
                                  @RequestParam(required = false) Integer startPage,
                                  @RequestParam(required = false) Integer endPage,
                                  HttpServletRequest request) {
        try {
            UserVO user = RequestHolder.getUserInfo(request);
            String executeBy = user != null ? user.getEmail() : "unknown";

            Map<String, Object> result = cronTaskService.executeTask(taskId, startPage, endPage, executeBy);

            if ((Boolean) result.get("success")) {
                return JsonResult.ok()
                        .put("message", result.get("message"))
                        .put("logId", result.get("logId"));
            } else {
                return JsonResult.error((String) result.get("message"));
            }
        } catch (Exception e) {
            log.error("手动触发任务失败, taskId: {}", taskId, e);
            return JsonResult.error("手动触发任务失败: " + e.getMessage());
        }
    }

    /**
     * 查询任务执行日志
     */
    @BruceOperation
    @RequestMapping("/logs/{taskId}")
    @ResponseBody
    public JsonResult getTaskLogs(@PathVariable Long taskId,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "20") Integer pageSize) {
        try {
            Map<String, Object> result = cronTaskService.findTaskLogs(taskId, page, pageSize);
            return JsonResult.ok()
                    .put("logs", result.get("logs"))
                    .put("total", result.get("total"))
                    .put("page", result.get("page"))
                    .put("pageSize", result.get("pageSize"))
                    .put("totalPages", result.get("totalPages"));
        } catch (Exception e) {
            log.error("查询任务执行日志失败, taskId: {}", taskId, e);
            return JsonResult.error("查询任务执行日志失败: " + e.getMessage());
        }
    }

    /**
     * 新增定时任务
     */
    @BruceOperation
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult addTask(@RequestParam String taskName,
                              @RequestParam String taskClass,
                              @RequestParam String taskMethod,
                              @RequestParam String cronExpression,
                              @RequestParam(required = false) String description,
                              @RequestParam(defaultValue = "1") Integer status,
                              @RequestParam(defaultValue = "1") Integer supportManual) {
        try {
            CronTask cronTask = new CronTask();
            cronTask.setTaskName(taskName);
            cronTask.setTaskClass(taskClass);
            cronTask.setTaskMethod(taskMethod);
            cronTask.setCronExpression(cronExpression);
            cronTask.setDescription(description);
            cronTask.setStatus(status);
            cronTask.setSupportManual(supportManual);

            cronTaskService.addTask(cronTask);
            return JsonResult.ok().put("message", "任务添加成功");
        } catch (Exception e) {
            log.error("添加定时任务失败", e);
            return JsonResult.error("添加定时任务失败: " + e.getMessage());
        }
    }

    /**
     * 编辑定时任务
     */
    @BruceOperation
    @RequestMapping("/update/{taskId}")
    @ResponseBody
    public JsonResult updateTask(@PathVariable Long taskId,
                                 @RequestParam(required = false) String taskName,
                                 @RequestParam(required = false) String cronExpression,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) Integer supportManual) {
        try {
            CronTask cronTask = cronTaskService.findTaskById(taskId);
            if (cronTask == null) {
                return JsonResult.error("任务不存在");
            }

            if (taskName != null) cronTask.setTaskName(taskName);
            if (cronExpression != null) cronTask.setCronExpression(cronExpression);
            if (description != null) cronTask.setDescription(description);
            if (supportManual != null) cronTask.setSupportManual(supportManual);

            cronTaskService.updateTask(cronTask);
            return JsonResult.ok().put("message", "任务更新成功");
        } catch (Exception e) {
            log.error("编辑定时任务失败, taskId: {}", taskId, e);
            return JsonResult.error("编辑定时任务失败: " + e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    @BruceOperation
    @RequestMapping("/delete/{taskId}")
    @ResponseBody
    public JsonResult deleteTask(@PathVariable Long taskId) {
        try {
            cronTaskService.deleteTask(taskId);
            return JsonResult.ok().put("message", "任务删除成功");
        } catch (Exception e) {
            log.error("删除定时任务失败, taskId: {}", taskId, e);
            return JsonResult.error("删除定时任务失败: " + e.getMessage());
        }
    }

    /**
     * 禁用/启用定时任务
     */
    @BruceOperation
    @RequestMapping("/toggle/{taskId}")
    @ResponseBody
    public JsonResult toggleTask(@PathVariable Long taskId) {
        try {
            CronTask task = cronTaskService.findTaskById(taskId);
            if (task == null) {
                return JsonResult.error("任务不存在");
            }

            cronTaskService.toggleTaskStatus(taskId);
            String status = task.getStatus() == 1 ? "已禁用" : "已启用";
            return JsonResult.ok().put("message", "任务" + status);
        } catch (Exception e) {
            log.error("切换任务状态失败, taskId: {}", taskId, e);
            return JsonResult.error("切换任务状态失败: " + e.getMessage());
        }
    }
}