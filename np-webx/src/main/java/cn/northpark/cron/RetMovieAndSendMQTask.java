package cn.northpark.cron;

import cn.northpark.constant.BC_Constant;
import cn.northpark.model.Movies;
import cn.northpark.result.Message;
import cn.northpark.service.MoviesService;
import cn.northpark.threadPool.MultiThread;
import cn.northpark.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author bruce
 * @date 2024年1月25日
 */
@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class RetMovieAndSendMQTask {


    @Autowired
    MoviesService moviesService;

    /**
     * 爬取电影 & 组装信息发送到消息队列
     *
     * @author zhangyang
     */
    @Async("taskExecutor")
//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟执行一次
    @Scheduled(cron = "0 18 23 * * ?")//每天中午11:30执行一次
    public void RetMovieAndSendMQTask() {
        log.info("#####开始获取["+ TimeUtils.nowDate() +"]电影信息#######");

        for (int i = 3; i < 11; i++) {
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
            retMoviesPage(collect);
//            String jsonData = JsonUtil.object2json(collect);
//
//
//            log.info("爬取的数据----》,{}", jsonData);
//
//            //组装数据
//            Message msg = new Message();
//            msg.setBody(jsonData);
//            msg.setMsgType("movie");
//
//            Properties properties = KafkaString.buildBasicKafkaProperty();
//            KafkaString.sendKafkaString(properties,"northpark",JsonUtil.object2json(msg));

            //====================执行逻辑====================================

            log.info("task " + 1 + "执行完毕");
        }

    }



    private void retMoviesPage(List list) {


        MultiThread<Map<String, String>,Integer > multiThread = new MultiThread<Map<String, String>,Integer>(list) {

//            private transient MoviesService moviesService =  (MoviesService)SpringContextUtils.getBean("MoviesService");

            @Override
            public Integer outExecute(int currentThread, Map<String, String> map) {

                System.err.println("currentThread===>"+currentThread);
                System.err.println("代理处map数据 ===>"+map);

                //逻辑处理start=====================================================
                try {

                    String title = (String) map.get("title");

                    String date = (String) map.get("date");
                    String article = (String) map.get("article");
                    String ret_code = (String) map.get("ret_code");
                    String tag = (String) map.get("tag");
                    String tag_code = (String) map.get("tag_code");
                    String path = (String) map.get("path");


                    List<Map<String, Object>> list = NPQueryRunner.findByCondition("bc_movies", " ret_code= '" + ret_code+ "' ");

                    if (CollectionUtils.isEmpty(list)) {
                        Movies model = new Movies();
                        model.setMovieName(title);
                        model.setAddTime(date);
                        model.setMovieDesc(article);
                        model.setPrice(Integer.valueOf(1));
                        model.setRetCode(ret_code);
                        model.setTag(tag);
                        model.setTagCode(tag_code);
                        model.setViewNum(Integer.valueOf(HTMLParserUtil.geneview_num()));
                        model.setColor(PinyinUtil.getFirstChar(title));
                        model.setPath(path);
                        moviesService.addMovies(model);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //逻辑处理end==========================================================



                return currentThread;
            }
        };
        //调度查看执行结果
        try {
            System.err.println("线程"+multiThread.getResult()+"正在执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        log.info("#####开始获取["+ TimeUtils.nowDate() +"]电影信息#######");

        for (int i = 1; i < 2; i++) {
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
//            String jsonData = JsonUtil.object2json(collect);
//
//
//            log.info("爬取的数据----》,{}", jsonData);
//
//            //组装数据
//            Message msg = new Message();
//            msg.setBody(jsonData);
//            msg.setMsgType("movie");
//
//            Properties properties = KafkaString.buildBasicKafkaProperty();
//            KafkaString.sendKafkaString(properties,"northpark",JsonUtil.object2json(msg));

            //====================执行逻辑====================================

            log.info("task " + i + "执行完毕");
        }
    }

}
