package cn.northpark.test.ret;

import cn.northpark.constant.BC_Constant;
import cn.northpark.threadPool.MultiThread;
import cn.northpark.utils.HTMLParserUtil;
import cn.northpark.utils.HttpGetUtils;
import cn.northpark.utils.NPQueryRunner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author bruce
 * @date 2024年01月24日 14:20:10
 */
@Slf4j
public class HandlePathNullMoveis {
    public static void main(String[] args) {



        List<Map<String, Object>> mapList1 = NPQueryRunner.query("select * from bc_movies where movie_desc like '%资源%' and path = ''  order by id desc",new MapListHandler());


        CopyOnWriteArrayList<Map<String, Object>>  mapList = new CopyOnWriteArrayList(mapList1) ;

        for (Map<String, Object> objectMap : mapList) {
            String movie_name = objectMap.get("movie_name").toString();//名称
            String id = objectMap.get("id").toString();//id


            if(movie_name.contains("》")){
                movie_name = movie_name.substring(0,movie_name.indexOf("》")+1);
            }

            movie_name = movie_name.replace("《","").replace("》","");

            objectMap.put("movie_name",movie_name);

        }


        /*构造页码*/
        List<Integer> todo_list = new ArrayList<>();
        for (int i = 201; i <= 2000; i++) {
            todo_list.add(i);
        }

        CopyOnWriteArrayList<Map<String, Object>> finalMapList = mapList;
        MultiThread<Integer, Integer> multiThread = new MultiThread<Integer, Integer>(todo_list) {
            @Override
            public Integer outExecute(int currentThread, Integer data) {

                System.err.println("currentThread===>"+currentThread);
                System.err.println("page id ===>"+data);
                run(data, finalMapList);
                return currentThread;
            }
        };

        try {
            System.err.println("线程"+multiThread.getResult()+"正在执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    /**
     * 执行一页的爬取
     */
    static void run(final int taskNum, CopyOnWriteArrayList<Map<String, Object>> finalMapList){
        System.out.println("正在执行task " + taskNum);

        //====================执行逻辑=====================

        try {

            judgeRRMovies(taskNum, BC_Constant.RET_RR_MOVIES,finalMapList);
        } catch (Exception e) {

            e.printStackTrace();
        }

        //====================执行逻辑====================================

        System.out.println("task " + taskNum + "执行完毕");
    }

    /**
     * 爬取1页的人人电影资源
     */
    public static List<Map<String, String>> judgeRRMovies(Integer index, String rettype , CopyOnWriteArrayList<Map<String, Object>> mapList ) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = Lists.newArrayList();
        HashMap<String, String> map = null;
        try {

            log.info("page=============================" + index + "============================页");

            String url = rettype + index + ".html";

            System.err.println("爬取地址+++++"+url);

            String dataResult = HttpGetUtils.getDataByHtmlUnit(url);

            while (StringUtils.isBlank(dataResult)) {
                dataResult = HttpGetUtils.getDataByHtmlUnit(url);
            }

            System.out.println(dataResult);
            Document doc = Jsoup.parse(dataResult, url);
            Element ul = doc.select("#movielist").get(0);
            Elements lis = ul.select("li");
            if (!lis.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    Element li = lis.get(i);

                    //标题
                    String title = li.select("div.pure-u-19-24 > div > h2 >a").get(0).attr("title");


                    for (Map<String, Object> o : mapList) {
                        if(title.contains(o.get("movie_name").toString())){

                            log.error("title======================》"+title);

                            String path = "";


                            //获取正文内容

                            String a_url = BC_Constant.RET_RR_BASE + li.select("a").get(0).attr("href");



                            String dataResult_ = HttpGetUtils.getDataResult(a_url);

                            Document doc_ = Jsoup.parse(dataResult_, a_url);


                            Element detail = doc_.select("div.movie-des.shadow > div.movie-txt").get(0);

                            //跳过订阅付费文章
                            if(detail.html().contains("这篇文档需要") && detail.html().contains("包月VIP")){
                                continue;
                            }


                            //========================解析路径start======================================
                            //删除打赏和微信二维码信息

                            StringBuilder sb = new StringBuilder();



                            //处理div中的连接，就去p磁力链查找下载地址，删除后，设置到path
                            Elements ps = detail.select("div.movie-txt > div");
                            if (!CollectionUtils.isEmpty(ps)) {
                                for (Iterator iterator = ps.iterator(); iterator.hasNext(); ) {
                                    Element link = (Element) iterator.next();
                                    if(link.select("a").size()>0){
                                        //把iterater里面的元素连接提取到path中
                                        HTMLParserUtil.handleLink(sb, link, "div");
                                    }
                                }
                            }

                            //处理完div后 处理样式有问题的span
                            Elements spans = detail.select("div.movie-txt > span");
                            if (!CollectionUtils.isEmpty(spans)) {
                                for (Iterator iterator = spans.iterator(); iterator.hasNext(); ) {
                                    Element link = (Element) iterator.next();
                                    //把iterater里面的元素连接提取到path中
                                    HTMLParserUtil.handleLink(sb, link, "span");
                                }
                            }


                            path = sb.toString();

                            //========================解析路径======================================

                            //path 静态路径替换

                            path = path.replace(BC_Constant.ignore_pic_list.get(0), BC_Constant.np_thunder_down).replace(BC_Constant.ignore_pic_list.get(1), BC_Constant.np_cloud_down);


                            System.err.println("path--->"+path);

                            //更新下载地址

                            String upSQL= "update bc_movies set path = ? where id = ?";
                            NPQueryRunner.update(upSQL,path,o.get("id").toString());

                            //已处理的数据清除
                            mapList.remove(o);

                        }
                    }





                }

            }
        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }
}
