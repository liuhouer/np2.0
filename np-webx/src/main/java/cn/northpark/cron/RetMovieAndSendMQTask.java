package cn.northpark.cron;

import cn.northpark.constant.BC_Constant;
import cn.northpark.result.Message;
import cn.northpark.utils.HTMLParserUtil;
import cn.northpark.utils.JsonUtil;
import cn.northpark.utils.KafkaString;
import cn.northpark.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author bruce
 * @date 2024年1月25日
 */
@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class RetMovieAndSendMQTask {

    /**
     * 爬取电影 & 组装信息发送到消息队列
     *
     * @author zhangyang
     */
    @Async("taskExecutor")
//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟执行一次
    @Scheduled(cron = "0 30 11 * * ?")//每天中午11:30执行一次
    public void RetMovieAndSendMQTask() {
        log.info("#####开始获取["+ TimeUtils.nowDate() +"]电影信息#######");

        for (int i = 1; i < 3; i++) {
            log.info("正在执行task " + i);

            //====================执行逻辑=====================
            List<Map<String, String>> collect = new ArrayList<>();

            try {

                List<Map<String, String>> list = HTMLParserUtil.retRRMovies(i, BC_Constant.RET_RR_MOVIES);
                collect.addAll(list);
            } catch (Exception e) {

                e.printStackTrace();
            }


            //每页请求一次新增数据
            String jsonData = JsonUtil.object2json(collect);


            log.info("爬取的数据----》,{}", jsonData);

            //组装数据
            Message msg = new Message();
            msg.setBody(jsonData);
            msg.setMsgType("movie");

            Properties properties = KafkaString.buildBasicKafkaProperty();
            KafkaString.sendKafkaString(properties,"northpark",JsonUtil.object2json(msg));

            //====================执行逻辑====================================

            log.info("task " + 1 + "执行完毕");
        }

    }

    public static void main(String[] args) {
        log.info("#####开始获取["+ TimeUtils.nowDate() +"]电影信息#######");

        for (int i = 1; i < 20; i++) {
            log.info("正在执行task " + i);

            //====================执行逻辑=====================
            List<Map<String, String>> collect = new ArrayList<>();

            try {

                List<Map<String, String>> list = HTMLParserUtil.retRRMovies(i, BC_Constant.RET_RR_MOVIES);
                collect.addAll(list);
            } catch (Exception e) {

                e.printStackTrace();
            }


            //每页请求一次新增数据
            String jsonData = JsonUtil.object2json(collect);


            log.info("爬取的数据----》,{}", jsonData);

            //组装数据
            Message msg = new Message();
            msg.setBody(jsonData);
            msg.setMsgType("movie");

            Properties properties = KafkaString.buildBasicKafkaProperty();
            KafkaString.sendKafkaString(properties,"northpark",JsonUtil.object2json(msg));

            //====================执行逻辑====================================

            log.info("task " + 1 + "执行完毕");
        }
    }

}
