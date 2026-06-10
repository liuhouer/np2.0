package cn.northpark.np5.controller;

import cn.northpark.np5.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        request.getSession().setAttribute("tabs", "index");
        // 获取用户总数展示在首页中
        int userCount = Math.toIntExact(userService.count());
        model.addAttribute("userCount", userCount);
        return "index";
    }
}