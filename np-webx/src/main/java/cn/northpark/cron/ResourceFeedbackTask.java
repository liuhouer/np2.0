package cn.northpark.cron;

import cn.northpark.constant.BC_Constant;
import cn.northpark.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.URLEncoder;
import java.util.*;

@Component
@EnableScheduling
@Slf4j
public class ResourceFeedbackTask {

    private static final String SEARCH_API = "https://so.northpark.cn/api/search?kw=%s&res=merge&src=all";
    private static final List<String> PRIORITY_TYPES = Arrays.asList("baidu", "aliyun", "123", "xunlei", "magnet");

    @Scheduled(fixedRate = 5 * 60 * 60 * 1000) // 每5小时执行一次
    public void processInvalidResources() {
        try {
            RedisUtil redisUtil = RedisUtil.getInstance();
            ObjectMapper mapper = new ObjectMapper();

            // 获取所有反馈的 spanID
            Set<String> keys = redisUtil.keys(BC_Constant.REDIS_FEEDBACK + ":*");
            if (keys == null || keys.isEmpty()) {
                log.info("没有失效资源反馈需要处理");
                return;
            }

            for (String key : keys) {
                if (!key.endsWith(":users") && !key.endsWith(":count")) {
                    try {
                        // 获取反馈信息和用户列表
                        String feedbackJson = redisUtil.hGet(key, "feedback");
                        if (feedbackJson == null) continue;

                        Map<String, Object> feedback = JsonUtil.json2map(feedbackJson);
                        String title = feedback.get("title").toString();
                        String href = feedback.get("href").toString();
                        String spanID = key.split(":")[1];
                        Set<String> userIds = redisUtil.sMembers(key + ":users");

                        // 提取搜索词
                        String searchTerm = title;
                        if (title.contains("《") && title.contains("》")) {
                            int start = title.indexOf("《") + 1;
                            int end = title.indexOf("》");
                            if (end > start) {
                                searchTerm = title.substring(start, end);
                            }
                        }

                        // 调用搜索接口
                        String encodedTitle = URLEncoder.encode(searchTerm, "UTF-8");
                        String apiUrl = String.format(SEARCH_API, encodedTitle);
                        String response = httpGet(apiUrl);
                        Map<String, Object> result = mapper.readValue(response, Map.class);

                        // 处理搜索结果
                        if (result.get("code").equals(0) && result.get("data") != null) {
                            Map<String, Object> data = (Map<String, Object>) result.get("data");
                            if ((Integer) data.get("total") > 0) {
                                Map<String, List<Map<String, String>>> mergedByType = (Map<String, List<Map<String, String>>>) data.get("merged_by_type");
                                List<String> htmlLinks = new ArrayList<>();

                                // 优先处理 baidu, aliyun, 123, xunlei, magnet
                                for (String type : PRIORITY_TYPES) {
                                    if (mergedByType.containsKey(type)) {
                                        List<Map<String, String>> resources = mergedByType.get(type);
                                        int count = 0;
                                        for (Map<String, String> resource : resources) {
                                            if (count >= 2) break;
                                            String url = resource.get("url");
                                            String password = resource.get("password");
                                            String note = resource.get("note");
                                            String linkHtml = String.format("<a href=\"%s\" target=\"_blank\">%s</a>%s",
                                                    url, note, StringUtils.isNotBlank(password) ? " 提取码: " + password : "");
                                            htmlLinks.add(linkHtml);
                                            count++;
                                        }
                                    }
                                }

                                // 如果优先类别未生成足够链接（少于2个），处理其他类别
                                if (htmlLinks.size() < 2) {
                                    for (String type : mergedByType.keySet()) {
                                        if (!PRIORITY_TYPES.contains(type)) {
                                            List<Map<String, String>> resources = mergedByType.get(type);
                                            int count = 0;
                                            for (Map<String, String> resource : resources) {
                                                if (count >= 2 || htmlLinks.size() >= 2) break;
                                                String url = resource.get("url");
                                                String password = resource.get("password");
                                                String note = resource.get("note");
                                                String linkHtml = String.format("<a href=\"%s\" target=\"_blank\">%s</a>%s",
                                                        url, note, StringUtils.isNotBlank(password) ? " 提取码: " + password : "");
                                                htmlLinks.add(linkHtml);
                                                count++;
                                            }
                                        }
                                    }
                                }

                                // 构造 HTML
                                String path = String.join("<br>", htmlLinks);

                                // 根据 href 判断资源类型并更新数据库
                                String tableName;
                                if (href.contains("/movies")) {
                                    tableName = "bc_movies";
                                } else if (href.contains("/soft")) {
                                    tableName = "bc_soft";
                                } else if (href.contains("/learning")) {
                                    tableName = "bc_knowledge";
                                } else {
                                    log.warn("未知资源类型: {}", href);
                                    continue;
                                }

                                String upSql = String.format("update %s set path = ? where id = ?", tableName);
                                NPQueryRunner.update(upSql, path, spanID);

                                // 通知所有反馈用户
                                for (String uID : userIds) {
                                    String userEmail = NotifyUtil.getUserEmailByID(uID);
                                    if (StringUtils.isNotBlank(userEmail)) {
                                        String subject = "资源失效反馈更新通知";
                                        String msg = String.format("您反馈的资源《%s》已更新，请访问 <a href=\"%s\">%s</a> 查看。",
                                                title, href, href);
                                        EmailUtils.getInstance().sendEMAIL(userEmail, subject, msg);
                                    }
                                }

                                // 删除 Redis 记录
                                redisUtil.del(key);
                                redisUtil.del(key + ":users");
                                redisUtil.del(key + ":count");
                            }
                        }
                    } catch (Exception e) {
                        log.error("处理资源 {} 失败", key, e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("定时任务处理失效资源出错", e);
        }
    }



    public static String httpGet(String url) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } finally {
            client.close();
        }
        return null;
    }
}
