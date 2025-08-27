package cn.northpark.service.impl;

import cn.northpark.config.OAuthConfig;
import cn.northpark.mapper.UserMapper;
import cn.northpark.model.User;
import cn.northpark.service.OAuthService;
import cn.northpark.utils.PinyinUtil;
import cn.northpark.utils.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private OAuthConfig oauthConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getGoogleAuthUrl(String state) {
        try {
            if (StringUtils.isEmpty(state)) {
                state = UUID.randomUUID().toString();
            }

            StringBuilder url = new StringBuilder();
            url.append(oauthConfig.getGoogle().getAuthUrl())
               .append("?client_id=").append(oauthConfig.getGoogle().getClientId())
               .append("&redirect_uri=").append(URLEncoder.encode(oauthConfig.getGoogle().getRedirectUri(), "UTF-8"))
               .append("&scope=").append(URLEncoder.encode(oauthConfig.getGoogle().getScope(), "UTF-8"))
               .append("&response_type=code")
               .append("&state=").append(state);

            return url.toString();
        } catch (UnsupportedEncodingException e) {
            log.error("构建Google授权URL失败", e);
            throw new RuntimeException("构建授权URL失败");
        }
    }

    @Override
    public String getGithubAuthUrl(String state) {
        try {
            if (StringUtils.isEmpty(state)) {
                state = UUID.randomUUID().toString();
            }

            StringBuilder url = new StringBuilder();
            url.append(oauthConfig.getGithub().getAuthUrl())
               .append("?client_id=").append(oauthConfig.getGithub().getClientId())
               .append("&redirect_uri=").append(URLEncoder.encode(oauthConfig.getGithub().getRedirectUri(), "UTF-8"))
               .append("&scope=").append(URLEncoder.encode(oauthConfig.getGithub().getScope(), "UTF-8"))
               .append("&state=").append(state);

            return url.toString();
        } catch (UnsupportedEncodingException e) {
            log.error("构建GitHub授权URL失败", e);
            throw new RuntimeException("构建授权URL失败");
        }
    }

    @Override
    public User handleGoogleCallback(String code, String state) {
        try {
            // 1. 用授权码换取access_token
            String accessToken = getGoogleAccessToken(code);
            if (StringUtils.isEmpty(accessToken)) {
                throw new RuntimeException("获取Google访问令牌失败");
            }

            // 2. 用access_token获取用户信息
            JSONObject userInfo = getGoogleUserInfo(accessToken);
            if (userInfo == null) {
                throw new RuntimeException("获取Google用户信息失败");
            }

            String googleId = userInfo.getString("id");
            String email = userInfo.getString("email");

            // 3. 检查用户是否已存在（通过Google ID）
            User existingUser = userMapper.findUserByGoogleId(googleId);
            if (existingUser != null) {
                // 更新最后登录时间和用户信息
                updateUserFromGoogle(existingUser, userInfo);
                return existingUser;
            }

            // 4. 检查邮箱是否已被其他账号使用
            if (StringUtils.isNotEmpty(email)) {
                User emailUser = userMapper.findUserByEmail(email);
                if (emailUser != null) {
                    // 邮箱已存在，将Google信息合并到已有账户
                    log.info("邮箱{}已存在，将Google信息合并到已有账户ID:{}", email, emailUser.getId());
                    mergeGoogleInfoToExistingUser(emailUser, userInfo);
                    return emailUser;
                }
            }

            // 5. 创建新用户
            User newUser = createUserFromGoogle(userInfo);
            userMapper.insert(newUser);

            return newUser;

        } catch (Exception e) {
            log.error("处理Google回调失败", e);
            throw new RuntimeException("Google登录失败：" + e.getMessage());
        }
    }

    @Override
    public User handleGithubCallback(String code, String state) {
        try {
            // 1. 用授权码换取access_token
            String accessToken = getGithubAccessToken(code);
            if (StringUtils.isEmpty(accessToken)) {
                throw new RuntimeException("获取GitHub访问令牌失败");
            }

            // 2. 用access_token获取用户信息
            JSONObject userInfo = getGithubUserInfo(accessToken);
            if (userInfo == null) {
                throw new RuntimeException("获取GitHub用户信息失败");
            }

            // 3. 获取用户邮箱（GitHub可能需要单独接口）
            String email = getGithubUserEmail(accessToken);
            if (StringUtils.isNotEmpty(email)) {
                userInfo.put("email", email);
            }

            String githubId = userInfo.getString("id");

            // 4. 检查用户是否已存在（通过GitHub ID）
            User existingUser = findUserByGithubId(githubId);
            if (existingUser != null) {
                // 更新最后登录时间和用户信息
                updateUserFromGithub(existingUser, userInfo);
                return existingUser;
            }

            // 5. 检查邮箱是否已被其他账号使用
            if (StringUtils.isNotEmpty(email)) {
                User emailUser = userMapper.findUserByEmail(email);
                if (emailUser != null) {
                    // 邮箱已存在，将GitHub信息合并到已有账户
                    log.info("邮箱{}已存在，将GitHub信息合并到已有账户ID:{}", email, emailUser.getId());
                    mergeGithubInfoToExistingUser(emailUser, userInfo);
                    return emailUser;
                }
            }

            // 6. 创建新用户
            User newUser = createUserFromGithub(userInfo);
            userMapper.insert(newUser);

            return newUser;

        } catch (Exception e) {
            log.error("处理GitHub回调失败", e);
            throw new RuntimeException("GitHub登录失败：" + e.getMessage());
        }
    }

    private String getGoogleAccessToken(String code) {
        try {
            // 配置代理
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", getProxyPort()));
            factory.setProxy(proxy);

            // 创建 RestTemplate 并应用代理配置
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", oauthConfig.getGoogle().getClientId());
            params.add("client_secret", oauthConfig.getGoogle().getClientSecret());
            params.add("code", code);
            params.add("grant_type", "authorization_code");
            params.add("redirect_uri", oauthConfig.getGoogle().getRedirectUri());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    oauthConfig.getGoogle().getTokenUrl(), request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = JSON.parseObject(response.getBody());
                return json.getString("access_token");
            }

            return null;
        } catch (Exception e) {
            log.error("获取Google访问令牌失败", e);
            return null;
        }
    }

    private JSONObject getGoogleUserInfo(String accessToken) {
        try {

            // 配置代理
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", getProxyPort()));
            factory.setProxy(proxy);

            // 创建 RestTemplate 并应用代理配置
            RestTemplate restTemplate = new RestTemplate(factory);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                oauthConfig.getGoogle().getUserInfoUrl(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return JSON.parseObject(response.getBody());
            }

            return null;
        } catch (Exception e) {
            log.error("获取Google用户信息失败", e);
            return null;
        }
    }

    private String getGithubAccessToken(String code) {
        try {
            // 配置代理
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", getProxyPort()));
            factory.setProxy(proxy);

            // 创建 RestTemplate 并应用代理配置
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Accept", "application/json");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", oauthConfig.getGithub().getClientId());
            params.add("client_secret", oauthConfig.getGithub().getClientSecret());
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                oauthConfig.getGithub().getTokenUrl(), request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = JSON.parseObject(response.getBody());
                return json.getString("access_token");
            }

            return null;
        } catch (Exception e) {
            log.error("获取GitHub访问令牌失败", e);
            return null;
        }
    }

    private JSONObject getGithubUserInfo(String accessToken) {
        try {
            // 配置代理
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", getProxyPort()));
            factory.setProxy(proxy);

            // 创建 RestTemplate 并应用代理配置
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.set("User-Agent", "NorthPark-App");

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    oauthConfig.getGithub().getUserInfoUrl(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return JSON.parseObject(response.getBody());
            }

            return null;
        } catch (Exception e) {
            log.error("获取GitHub用户信息失败", e);
            return null;
        }
    }

    private String getGithubUserEmail(String accessToken) {
        try {
            // 配置代理
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", getProxyPort()));
            factory.setProxy(proxy);

            // 创建 RestTemplate 并应用代理配置
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.set("User-Agent", "NorthPark-App");

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                oauthConfig.getGithub().getEmailUrl(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONArray emails = JSON.parseArray(response.getBody());
                for (int i = 0; i < emails.size(); i++) {
                    JSONObject emailObj = emails.getJSONObject(i);
                    if (emailObj.getBooleanValue("primary")) {
                        return emailObj.getString("email");
                    }
                }
            }

            return null;
        } catch (Exception e) {
            log.error("获取GitHub用户邮箱失败", e);
            return null;
        }
    }


    @Override
    public User findUserByGithubId(String githubId) {
        try {
            return userMapper.findUserByGithubId(githubId);
        } catch (Exception e) {
            log.error("根据GitHub ID查找用户失败", e);
            return null;
        }
    }


    private User createUserFromGoogle(JSONObject userInfo) {
        User user = new User();
        user.setGoogleId(userInfo.getString("id"));
        user.setGoogleInfo(userInfo.toJSONString());
        user.setEmail(userInfo.getString("email"));
        user.setUsername(userInfo.getString("name"));
        user.setRealName(userInfo.getString("name"));
        user.setAvatarUrl(userInfo.getString("picture"));
        user.setLoginType("google");
        user.setEmailFlag("1"); // Google邮箱默认已验证
        user.setDateJoined(TimeUtils.nowTime());
        user.setLastLogin(TimeUtils.nowTime());
        user.setIsDel(0);

        //默认字符头像===================================================
        String abc = PinyinUtil.paraseStringToPinyin(user.getUsername());
        if (StringUtils.isNotEmpty(abc)) {
            String head_span = abc.substring(0, 1).toUpperCase();
            String head_span_class = "text-" + head_span.toLowerCase();
            user.setHeadSpan(head_span);
            user.setHeadSpanClass(head_span_class);
        }
        //默认字符头像===================================================

        // 生成唯一的tail_slug
        String tailSlug = generateTailSlug(userInfo.getString("name"));
        user.setTailSlug(tailSlug);

        return user;
    }

    private User createUserFromGithub(JSONObject userInfo) {
        User user = new User();
        user.setGithubId(userInfo.getString("id"));
        user.setGithubInfo(userInfo.toJSONString());
        user.setEmail(userInfo.getString("email"));
        user.setUsername(userInfo.getString("login"));
        user.setRealName(userInfo.getString("name"));
        user.setAvatarUrl(userInfo.getString("avatar_url"));
        user.setLocation(userInfo.getString("location"));
        user.setCompany(userInfo.getString("company"));
        user.setBio(userInfo.getString("bio"));
        user.setBlogSite(userInfo.getString("blog"));
        user.setLoginType("github");
        user.setEmailFlag(StringUtils.isNotEmpty(userInfo.getString("email")) ? "1" : "0");
        user.setDateJoined(TimeUtils.nowTime());
        user.setLastLogin(TimeUtils.nowTime());
        user.setIsDel(0);
        //默认字符头像===================================================
        String abc = PinyinUtil.paraseStringToPinyin(user.getUsername());
        if (StringUtils.isNotEmpty(abc)) {
            String head_span = abc.substring(0, 1).toUpperCase();
            String head_span_class = "text-" + head_span.toLowerCase();
            user.setHeadSpan(head_span);
            user.setHeadSpanClass(head_span_class);
        }
        //默认字符头像===================================================

        // 生成唯一的tail_slug
        String tailSlug = generateTailSlug(userInfo.getString("login"));
        user.setTailSlug(tailSlug);

        return user;
    }

    private void updateUserFromGoogle(User user, JSONObject userInfo) {
        user.setGoogleInfo(userInfo.toJSONString());
        user.setLastLogin(TimeUtils.nowTime());

        // 更新可能变化的信息
        if (StringUtils.isNotEmpty(userInfo.getString("name"))) {
            user.setRealName(userInfo.getString("name"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("picture"))) {
            user.setAvatarUrl(userInfo.getString("picture"));
        }

        userMapper.updateByPrimaryKey(user);
    }

    private void updateUserFromGithub(User user, JSONObject userInfo) {
        user.setGithubInfo(userInfo.toJSONString());
        user.setLastLogin(TimeUtils.nowTime());

        // 更新可能变化的信息
        if (StringUtils.isNotEmpty(userInfo.getString("name"))) {
            user.setRealName(userInfo.getString("name"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("avatar_url"))) {
            user.setAvatarUrl(userInfo.getString("avatar_url"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("location"))) {
            user.setLocation(userInfo.getString("location"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("company"))) {
            user.setCompany(userInfo.getString("company"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("bio"))) {
            user.setBio(userInfo.getString("bio"));
        }
        if (StringUtils.isNotEmpty(userInfo.getString("blog"))) {
            user.setBlogSite(userInfo.getString("blog"));
        }

        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 将Google信息合并到已有用户账户
     */
    private void mergeGoogleInfoToExistingUser(User existingUser, JSONObject userInfo) {
        // 设置Google相关信息
        existingUser.setGoogleId(userInfo.getString("id"));
        existingUser.setGoogleInfo(userInfo.toJSONString());
        existingUser.setLastLogin(TimeUtils.nowTime());

        // 如果现有用户没有头像，使用Google头像
        if (StringUtils.isEmpty(existingUser.getAvatarUrl()) && StringUtils.isNotEmpty(userInfo.getString("picture"))) {
            existingUser.setAvatarUrl(userInfo.getString("picture"));
        }

        // 如果现有用户没有真实姓名，使用Google姓名
        if (StringUtils.isEmpty(existingUser.getRealName()) && StringUtils.isNotEmpty(userInfo.getString("name"))) {
            existingUser.setRealName(userInfo.getString("name"));
        }

        // 确保邮箱验证标志为已验证（Google邮箱默认已验证）
        if (StringUtils.isEmpty(existingUser.getEmailFlag()) || "0".equals(existingUser.getEmailFlag())) {
            existingUser.setEmailFlag("1");
        }

        userMapper.updateByPrimaryKey(existingUser);
        log.info("成功将Google信息合并到用户ID:{}，邮箱:{}", existingUser.getId(), existingUser.getEmail());
    }

    /**
     * 将GitHub信息合并到已有用户账户
     */
    private void mergeGithubInfoToExistingUser(User existingUser, JSONObject userInfo) {
        // 设置GitHub相关信息
        existingUser.setGithubId(userInfo.getString("id"));
        existingUser.setGithubInfo(userInfo.toJSONString());
        existingUser.setLastLogin(TimeUtils.nowTime());

        // 如果现有用户没有头像，使用GitHub头像
        if (StringUtils.isEmpty(existingUser.getAvatarUrl()) && StringUtils.isNotEmpty(userInfo.getString("avatar_url"))) {
            existingUser.setAvatarUrl(userInfo.getString("avatar_url"));
        }

        // 如果现有用户没有真实姓名，使用GitHub姓名
        if (StringUtils.isEmpty(existingUser.getRealName()) && StringUtils.isNotEmpty(userInfo.getString("name"))) {
            existingUser.setRealName(userInfo.getString("name"));
        }

        // 补充其他GitHub特有信息（如果现有用户没有的话）
        if (StringUtils.isEmpty(existingUser.getLocation()) && StringUtils.isNotEmpty(userInfo.getString("location"))) {
            existingUser.setLocation(userInfo.getString("location"));
        }
        if (StringUtils.isEmpty(existingUser.getCompany()) && StringUtils.isNotEmpty(userInfo.getString("company"))) {
            existingUser.setCompany(userInfo.getString("company"));
        }
        if (StringUtils.isEmpty(existingUser.getBio()) && StringUtils.isNotEmpty(userInfo.getString("bio"))) {
            existingUser.setBio(userInfo.getString("bio"));
        }
        if (StringUtils.isEmpty(existingUser.getBlogSite()) && StringUtils.isNotEmpty(userInfo.getString("blog"))) {
            existingUser.setBlogSite(userInfo.getString("blog"));
        }

        // 如果有邮箱且邮箱验证标志为空或未验证，设置为已验证
        if (StringUtils.isNotEmpty(userInfo.getString("email")) &&
            (StringUtils.isEmpty(existingUser.getEmailFlag()) || "0".equals(existingUser.getEmailFlag()))) {
            existingUser.setEmailFlag("1");
        }

        userMapper.updateByPrimaryKey(existingUser);
        log.info("成功将GitHub信息合并到用户ID:{}，邮箱:{}", existingUser.getId(), existingUser.getEmail());
    }

    @Override
    public String generateTailSlug(String baseName) {
        if (StringUtils.isEmpty(baseName)) {
            baseName = "user";
        }

        // 清理用户名，只保留字母数字和下划线
        String cleanName = baseName.replaceAll("[^a-zA-Z0-9_]", "").toLowerCase();
        if (cleanName.length() > 20) {
            cleanName = cleanName.substring(0, 20);
        }

        String tailSlug = cleanName;
        int counter = 1;

        // 检查是否已存在，如果存在则添加数字后缀
        while (isSlugExists(tailSlug)) {
            tailSlug = cleanName + counter;
            counter++;
        }

        return tailSlug;
    }

    private boolean isSlugExists(String tailSlug) {
        try {
            List<Object> result = userMapper.isSlugExists(tailSlug);
            if (result != null && result.size() > 0) {
                Long count = (Long) result.get(0);
                return count > 0;
            }
            return false;
        } catch (Exception e) {
            log.error("检查tail_slug是否存在失败", e);
            return true; // 出错时返回true，避免重复
        }
    }

    private int getProxyPort(){
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {// windows操作系统
            return 1081;
        }else{
            return 7890;
        }
    }
}
