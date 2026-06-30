package cn.northpark.controller;

import cn.northpark.annotation.BruceOperation;
import cn.northpark.model.User;
import cn.northpark.result.JsonResult;
import cn.northpark.service.UserService;
import cn.northpark.threadLocal.RequestHolder;
import cn.northpark.utils.NPQueryRunner;
import cn.northpark.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理控制器（管理员）
 *
 * @author bruce
 */
@Slf4j
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    /**
     * 用户管理页面
     */
    @BruceOperation
    @RequestMapping("")
    public String userManage(ModelMap model, HttpServletRequest request) {
        UserVO user = RequestHolder.getUserInfo(request);
        model.put("user", user);
        return "/page/admin/user/userManage";
    }

    /**
     * 获取用户列表（分页 + 搜索）
     */
    @BruceOperation
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult list(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "20") int pageSize,
                           @RequestParam(required = false) String keyword) {
        try {
            int offset = (page - 1) * pageSize;

            String whereSql = "";
            Object[] params;
            if (StringUtils.isNotBlank(keyword)) {
                String kw = "%" + keyword.trim() + "%";
                whereSql = " WHERE (username LIKE ? OR email LIKE ?) AND is_del = 0";
                params = new Object[]{kw, kw, pageSize, offset};
            } else {
                whereSql = " WHERE is_del = 0";
                params = new Object[]{pageSize, offset};
            }

            String countSql = "SELECT COUNT(*) FROM bc_user" + whereSql;
            String dataSql = "SELECT id, username, email, date_joined, last_login, login_type, avatar_url, is_del FROM bc_user"
                    + whereSql + " ORDER BY id DESC LIMIT ? OFFSET ?";

            // Count params exclude LIMIT/OFFSET
            Object[] countParams = new Object[params.length - 2];
            System.arraycopy(params, 0, countParams, 0, countParams.length);

            Long total = (Long) NPQueryRunner.query(countSql, new ScalarHandler(), countParams);
            List<?> users = NPQueryRunner.query(dataSql,
                    new org.apache.commons.dbutils.handlers.MapListHandler(), params);

            return JsonResult.ok()
                    .put("users", users)
                    .put("total", total)
                    .put("page", page)
                    .put("pageSize", pageSize);
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return JsonResult.error("获取用户列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户详情
     */
    @BruceOperation
    @RequestMapping("/detail")
    @ResponseBody
    public JsonResult detail(@RequestParam Integer id) {
        try {
            User user = userService.findUser(id);
            if (user == null) {
                return JsonResult.error("用户不存在");
            }
            // 不返回密码字段
            user.setPassword(null);
            return JsonResult.ok().put("user", user);
        } catch (Exception e) {
            log.error("获取用户详情失败, id={}", id, e);
            return JsonResult.error("获取用户详情失败: " + e.getMessage());
        }
    }

    /**
     * 封禁用户（软删除，is_del = 1）
     */
    @BruceOperation
    @RequestMapping("/ban")
    @ResponseBody
    public JsonResult ban(@RequestParam Integer id) {
        try {
            User user = userService.findUser(id);
            if (user == null) {
                return JsonResult.error("用户不存在");
            }
            user.setIsDel(1);
            userService.updateUser(user);
            log.info("封禁用户: id={}, email={}", id, user.getEmail());
            return JsonResult.ok().put("msg", "封禁成功");
        } catch (Exception e) {
            log.error("封禁用户失败, id={}", id, e);
            return JsonResult.error("封禁失败: " + e.getMessage());
        }
    }

    /**
     * 解封用户（is_del = 0）
     */
    @BruceOperation
    @RequestMapping("/unban")
    @ResponseBody
    public JsonResult unban(@RequestParam Integer id) {
        try {
            User user = userService.findUser(id);
            if (user == null) {
                return JsonResult.error("用户不存在");
            }
            user.setIsDel(0);
            userService.updateUser(user);
            log.info("解封用户: id={}, email={}", id, user.getEmail());
            return JsonResult.ok().put("msg", "解封成功");
        } catch (Exception e) {
            log.error("解封用户失败, id={}", id, e);
            return JsonResult.error("解封失败: " + e.getMessage());
        }
    }
}
