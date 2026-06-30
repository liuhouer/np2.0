package cn.northpark.controller;

import cn.northpark.annotation.BruceOperation;
import cn.northpark.mapper.EnvCfgMapper;
import cn.northpark.model.EnvCfg;
import cn.northpark.result.JsonResult;
import cn.northpark.threadLocal.RequestHolder;
import cn.northpark.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 环境配置管理控制器
 *
 * @author bruce
 */
@Slf4j
@Controller
@RequestMapping("/admin/envcfg")
public class EnvCfgAdminController {

    @Autowired
    private EnvCfgMapper envCfgMapper;

    /**
     * 环境配置管理页面
     */
    @BruceOperation
    @RequestMapping("")
    public String envCfgManage(ModelMap model, HttpServletRequest request) {
        List<EnvCfg> cfgList = envCfgMapper.selectAll();
        model.put("cfgList", cfgList);

        UserVO user = RequestHolder.getUserInfo(request);
        model.put("user", user);

        return "/page/admin/envcfg/envCfgManage";
    }

    /**
     * 获取配置列表（JSON）
     */
    @BruceOperation
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult list() {
        try {
            List<EnvCfg> cfgList = envCfgMapper.selectAll();
            return JsonResult.ok().put("cfgList", cfgList);
        } catch (Exception e) {
            log.error("获取环境配置列表失败", e);
            return JsonResult.error("获取配置列表失败: " + e.getMessage());
        }
    }

    /**
     * 新增配置项
     */
    @BruceOperation
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(@RequestParam String vcCfgName,
                          @RequestParam String vcCfgValue,
                          @RequestParam(required = false) String vcDesc,
                          @RequestParam(defaultValue = "1") String cStatus) {
        try {
            EnvCfg cfg = new EnvCfg();
            cfg.setVcCfgName(vcCfgName.trim());
            cfg.setVcCfgValue(vcCfgValue.trim());
            cfg.setVcDesc(vcDesc);
            cfg.setcStatus(cStatus);
            cfg.setdMdfTime(new Date());
            envCfgMapper.insert(cfg);
            log.info("新增环境配置: name={}", vcCfgName);
            return JsonResult.ok().put("msg", "新增成功");
        } catch (Exception e) {
            log.error("新增环境配置失败", e);
            return JsonResult.error("新增失败: " + e.getMessage());
        }
    }

    /**
     * 更新配置项
     */
    @BruceOperation
    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestParam Integer lCfgId,
                             @RequestParam String vcCfgName,
                             @RequestParam String vcCfgValue,
                             @RequestParam(required = false) String vcDesc,
                             @RequestParam(defaultValue = "1") String cStatus) {
        try {
            EnvCfg cfg = new EnvCfg();
            cfg.setlCfgId(lCfgId);
            cfg.setVcCfgName(vcCfgName.trim());
            cfg.setVcCfgValue(vcCfgValue.trim());
            cfg.setVcDesc(vcDesc);
            cfg.setcStatus(cStatus);
            cfg.setdMdfTime(new Date());
            envCfgMapper.updateByPrimaryKey(cfg);
            log.info("更新环境配置: id={}, name={}", lCfgId, vcCfgName);
            return JsonResult.ok().put("msg", "更新成功");
        } catch (Exception e) {
            log.error("更新环境配置失败, id={}", lCfgId, e);
            return JsonResult.error("更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除配置项
     */
    @BruceOperation
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam Integer lCfgId) {
        try {
            envCfgMapper.deleteByPrimaryKey(lCfgId);
            log.info("删除环境配置: id={}", lCfgId);
            return JsonResult.ok().put("msg", "删除成功");
        } catch (Exception e) {
            log.error("删除环境配置失败, id={}", lCfgId, e);
            return JsonResult.error("删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取单条配置（JSON）
     */
    @BruceOperation
    @RequestMapping("/get")
    @ResponseBody
    public JsonResult get(@RequestParam Integer lCfgId) {
        try {
            EnvCfg cfg = envCfgMapper.selectByPrimaryKey(lCfgId);
            if (cfg == null) {
                return JsonResult.error("配置项不存在");
            }
            return JsonResult.ok().put("cfg", cfg);
        } catch (Exception e) {
            log.error("获取环境配置失败, id={}", lCfgId, e);
            return JsonResult.error("获取失败: " + e.getMessage());
        }
    }
}
