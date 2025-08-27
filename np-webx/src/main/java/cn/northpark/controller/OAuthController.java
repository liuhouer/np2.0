package cn.northpark.controller;

import cn.northpark.annotation.RateLimit;
import cn.northpark.constant.CookieConstant;
import cn.northpark.constant.RedisConstant;
import cn.northpark.model.NotifyRemindB;
import cn.northpark.model.User;
import cn.northpark.notify.NotifyEnum;
import cn.northpark.result.Result;
import cn.northpark.result.ResultEnum;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.OAuthService;
import cn.northpark.utils.CookieUtil;
import cn.northpark.utils.JsonUtil;
import cn.northpark.utils.RedisUtil;
import cn.northpark.utils.TimeUtils;
import cn.northpark.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/auth")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;

    /**
     * 获取Google授权URL
     */
    @RequestMapping("/google/url")
    @ResponseBody
    @RateLimit
    public Result<String> getGoogleAuthUrl(@RequestParam(required = false) String redirectURI) {
        try {
            String state = StringUtils.isNotEmpty(redirectURI) ? redirectURI : "/";
            String authUrl = oauthService.getGoogleAuthUrl(state);
            return ResultGenerator.genSuccessResult(authUrl);
        } catch (Exception e) {
            log.error("获取Google授权URL失败", e);
            return ResultGenerator.genErrorResult(ResultEnum.AUTH_URL_NOT_VERIFIED);
        }
    }

    /**
     * 获取GitHub授权URL
     */
    @RequestMapping("/github/url")
    @ResponseBody
    @RateLimit
    public Result<String> getGithubAuthUrl(@RequestParam(required = false) String redirectURI) {
        try {
            String state = StringUtils.isNotEmpty(redirectURI) ? redirectURI : "/";
            String authUrl = oauthService.getGithubAuthUrl(state);
            return ResultGenerator.genSuccessResult(authUrl);
        } catch (Exception e) {
            log.error("获取GitHub授权URL失败", e);
            return ResultGenerator.genErrorResult(ResultEnum.AUTH_URL_NOT_VERIFIED);
        }
    }

    /**
     * Google授权回调
     */
    @RequestMapping("/google/callback")
    public String googleCallback(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error,
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap map) {

        try {
            if (StringUtils.isNotEmpty(error)) {
                log.error("Google授权失败: {}", error);
                map.addAttribute("error", "Google授权失败");
                return "login2";
            }

            // 处理Google回调
            User user = oauthService.handleGoogleCallback(code, state);
            if (user == null) {
                map.addAttribute("error", "Google登录失败");
                return "login2";
            }

            // 设置用户会话
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            //往redis设置key=UUID,value=xyz
            String token = UUID.randomUUID().toString();
            RedisUtil.getInstance().set(String.format(RedisConstant.TOKEN_TEMPLATE, token), JsonUtil.object2json(userVO), CookieConstant.expire);

            //设置cookie openid = abc
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);

            request.getSession().setAttribute("user", userVO);

            log.info("Google登录成功: {}", user.getEmail());


            //发送异步站长通知消息
            try {
                //=================================消息提醒====================================================

                //判断主题类型
                NotifyEnum match = NotifyEnum.WEBMASTER;

                //提醒系统赋值
                NotifyRemindB nr = new NotifyRemindB();

                //common
                nr.setMessage(user.toString() + "---" + TimeUtils.nowTime() + "---通过Google登录了---");
                nr.setStatus("0");


                match.notifyInstance.execute(nr);

                //=================================消息提醒====================================================
            } catch (Exception ig) {
                log.error("login-notice-has-ignored-------:", ig);
            }

            // 重定向到指定页面或首页
            String redirectUrl = StringUtils.isNotEmpty(state) && !state.equals("null") ? state : "/";
            return "redirect:" + redirectUrl;

        } catch (Exception e) {
            log.error("Google登录回调处理失败", e);
            map.addAttribute("error", "Google登录失败，请稍后重试");
            return "login2";
        }
    }

    /**
     * GitHub授权回调
     */
    @RequestMapping("/github/callback")
    public String githubCallback(
            @RequestParam String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error,
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap map) {

        try {
            if (StringUtils.isNotEmpty(error)) {
                log.error("GitHub授权失败: {}", error);
                map.addAttribute("error", "GitHub授权失败");
                return "login2";
            }

            // 处理GitHub回调
            User user = oauthService.handleGithubCallback(code, state);
            if (user == null) {
                map.addAttribute("error", "GitHub登录失败");
                return "login2";
            }

            // 设置用户会话
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            //往redis设置key=UUID,value=xyz
            String token = UUID.randomUUID().toString();
            RedisUtil.getInstance().set(String.format(RedisConstant.TOKEN_TEMPLATE, token), JsonUtil.object2json(userVO), CookieConstant.expire);

            //设置cookie openid = abc
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);

            request.getSession().setAttribute("user", userVO);

            log.info("GitHub登录成功: {}", user.getEmail());

            //发送异步站长通知消息
            try {
                //=================================消息提醒====================================================

                //判断主题类型
                NotifyEnum match = NotifyEnum.WEBMASTER;

                //提醒系统赋值
                NotifyRemindB nr = new NotifyRemindB();

                //common
                nr.setMessage(user.toString() + "---" + TimeUtils.nowTime() + "---通过Github登录了---");
                nr.setStatus("0");


                match.notifyInstance.execute(nr);

                //=================================消息提醒====================================================
            } catch (Exception ig) {
                log.error("login-notice-has-ignored-------:", ig);
            }


            // 重定向到指定页面或首页
            String redirectUrl = StringUtils.isNotEmpty(state) && !state.equals("null") ? state : "/";
            return "redirect:" + redirectUrl;

        } catch (Exception e) {
            log.error("GitHub登录回调处理失败", e);
            map.addAttribute("error", "GitHub登录失败，请稍后重试");
            return "login2";
        }
    }

    /**
     * 直接跳转到Google授权页面
     */
    @RequestMapping("/google")
    public void redirectToGoogle(
            @RequestParam(required = false) String redirectURI,
            HttpServletResponse response) throws IOException {

        try {
            String state = StringUtils.isNotEmpty(redirectURI) ? redirectURI : "/";
            String authUrl = oauthService.getGoogleAuthUrl(state);
            response.sendRedirect(authUrl);
        } catch (Exception e) {
            log.error("跳转到Google授权页面失败", e);
            response.sendRedirect("/login?error=oauth_error");
        }
    }

    /**
     * 直接跳转到GitHub授权页面
     */
    @RequestMapping("/github")
    public void redirectToGithub(
            @RequestParam(required = false) String redirectURI,
            HttpServletResponse response) throws IOException {

        try {
            String state = StringUtils.isNotEmpty(redirectURI) ? redirectURI : "/";
            String authUrl = oauthService.getGithubAuthUrl(state);
            response.sendRedirect(authUrl);
        } catch (Exception e) {
            log.error("跳转到GitHub授权页面失败", e);
            response.sendRedirect("/login?error=oauth_error");
        }
    }
}
