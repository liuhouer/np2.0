package cn.northpark.utils;

import cn.northpark.constant.BC_Constant;
import cn.northpark.constant.RetType;
import cn.northpark.utils.encrypt.NorthParkCryptUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author bruce
 */

@Slf4j
public class HTMLParserUtil {


//    /**
//     * 根据名字获取图片并且自动上传到服务器，返回上传地址
//
//     * http://photopin.com/free-photos/%E6%96%87%E7%AB%A0
//     * @throws IOException
//     */
//    public static String retPicByName(String title,String title_code) throws IOException {
//     StringBuilder sb =  new StringBuilder();
//            try{
//            	Result parse = ToAnalysis.parse(title);
//            	System.out.println(parse);
//
//            	List<Term> terms = parse.getTerms();
//
//            	String term = terms.get(terms.size()-1).getName();
//            	System.out.println(term);
//            Document doc = Jsoup.connect("http://photopin.com/free-photos/"+URLEncoder.encode(term))
//                                     .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                                          .referrer("http://www.google.com")
//                                          .ignoreHttpErrors(true)
//                                           .timeout(1000*5) //it's in milliseconds, so this means 5 seconds.
//                                     .get();
//
//
//            //取得第一张照片
//            Element img = doc.select("div[class=items-grid search-results]").select("img").get(0);
//
//            //上传
//            HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(img.attr("src"), getLocalFolderByOS("album") ,title_code);
//
//            String albumimg = map22.get("trimpath");
//            sb.append(albumimg);
//            System.out.println(img.attr("src"));
//            System.out.println(albumimg);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//
//        return sb.toString();
//    }


    /**
     * 爬虫采麦的最爱主题关联信息  ------------根据主题页  获取 评论列表、、
     * <p>
     * http://www.caimai.cc/love/xiang-ni-de-mei-yi-tian-lyz
     *
     * @throws IOException
     */
//    public static synchronized List<Map<String, String>> retCaiMaiZT_PL(String title_code) throws IOException {
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        try {
//            Document doc = Jsoup.connect("http://www.caimai.cc/love/" + title_code)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//
//            //取得评论
//
//            String retid = doc.getElementById("loadStuffCommentBtn").attr("rel");
//
//            String commenturl = "http://www.caimai.cc/do/loadstuffcomment?user_id=0&user_agent=538B9ABCD308&timestamp=1513240566&user_keychain=69D7D3053A75&comment_id_from=9999999999999&comment_perpage=1000&stuff_id=" + retid;
//
//            Response res = Jsoup.connect(commenturl)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .ignoreContentType(true)
//                    .execute();
//
//            Map<?, ?> map = JsonUtil.json2map(res.body());
//
//
//            Object object = map.get("html");
//
//            String htmljson = JsonUtil.object2json(object);
//
//            Document doc2 = Jsoup.parse(htmljson);
//
//            System.out.println(htmljson);
//
//            Elements pls = doc2.select("div[class=row]");
//
//            for (int i = 0; i < pls.size(); i++) {
//                Element p = pls.get(i).select("div[class=col-xs-9 col-sm-10]").get(0).select("p").get(0);
//                String comment = replaceBlank(p.text());
//                String tailslug = replaceBlank(p.select("a").get(0).attr("href").replace("/", ""));
//                ;
//                String username = replaceBlank(p.select("a").get(0).text());
//                Element p2 = pls.get(i).select("div[class=col-xs-9 col-sm-10]").get(0).select("p").get(1);
//                String shijian = replaceBlank(p2.text());
//
//                comment = comment.replace(username + "：", "");
//                Map<String, String> map_ = new HashMap<String, String>();
//                map_.put("username", username);
//                map_.put("tailslug", tailslug);
//                map_.put("comment", comment);
//                map_.put("shijian", shijian);
//                list.add(map_);
//                System.out.println("username--->" + username);
//                System.out.println("tailslug--->" + tailslug);
//                System.out.println("comment---->" + comment);
//                System.out.println("shijian---->" + shijian);
//                System.out.println("---------------------------------------------");
//
//            }
//
//
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//
//        return list;
//    }

    /**
     * 爬虫采麦的最爱主题关联信息  ------------根据主题页  获取 粉丝列表、、
     * <p>
     * http://www.caimai.cc/love/xiang-ni-de-mei-yi-tian-lyz
     *
     * @throws IOException
     */
//    public static String retCaiMaiZT_ZAN(String title_code) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        try {
//            Document doc = Jsoup.connect("http://www.caimai.cc/love/" + title_code)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//
//            //取得点赞的用户
//            Elements spans = doc.select("div[class=col-sm-5]").get(0).select("span");
//
//            System.out.println(spans.size());
//            if (spans.size() > 0) {
//                for (Element e : spans) {
//                    Elements as = e.select("a");
//                    if (as.size() > 0) {
//                        Element a = as.get(0);
//                        String tailslug = a.attr("href").replace("/", "");
//                        sb.append("'").append(tailslug).append("',");
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//        log.info(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
//
//        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
//    }


    /**
     * 爬虫采麦的最爱信息
     * loveUdavid
     * ret_url:http://www.caimai.cc/love/page2
     * http://www.caimai.cc/love/page397
     *
     * @throws IOException
     */
//    public static List<Map<String, String>> retCaiMai(int index) throws IOException {
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        try {
//            Document doc = Jsoup.connect("http://www.caimai.cc/love/page" + index)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//            //   `id`,  `title`, `title_code`,  `update_date`, `album_img`, `zan`, `pl`
//            Elements info = doc.select("div[class=col-xs-6 col-sm-3 margin-b20 ]");
//            for (Element p : info) {
//
//                HashMap<String, String> map = new HashMap<String, String>();
//
//
//                //标题的主题页面
//                Elements elements = p.select("div[class=thumbnail radius-0 border-0 margin-b0]");
//
//                Element a = elements.get(0).select("a").get(0);
//
//                String a_url = a.attr("href");
//
//                String title_code = a_url.replace("/love/", "");
//
//                System.out.println(title_code);
//
//                Element img = a.select("img").get(0);
//
//                String date = TimeUtils.getRandomDate();
//
//                //下载图片
//                String weburl = img.attr("src");
//
//                //标题
//
//                String title = img.attr("alt");
//                System.out.println(title);
//
//                HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS("album"), date);
//
//                String albumimg = map22.get("trimpath");
//
//                //赞和评论
//                String zan_pl = p.select("div[class=col-xs-5 text-right]").text();
//                String zan = zan_pl.split("  ")[0];
//                String pl = zan_pl.split("  ")[1];
//                System.out.println(zan_pl.split("  ")[0]);
//                System.out.println(zan_pl.split("  ")[1]);
//
//
//                map.put("title", title);
//                map.put("title_code", title_code);
//                map.put("album_img", albumimg);
//                map.put("zan", zan);
//                map.put("pl", pl);
//                list.add(map);
//
//            }
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//
//        return list;
//    }


    /**
     * 爬虫采麦的最爱主题关联信息  ------------根据主题页  获取作者信息  、、粉丝列表、、评论列表
     * <p>
     * http://www.caimai.cc/love/xiang-ni-de-mei-yi-tian-lyz
     *
     * @throws IOException
     */
//    public static Map<String, String> retCaiMaiZT(String title_code) throws IOException {
//        Map<String, String> map = new HashMap<>();
//        try {
//            Document doc = Jsoup.connect("http://www.caimai.cc/love/" + title_code)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//
//            //根据主题页  获取作者信息  、、粉丝列表、、评论列表
//
//            Element author = doc.select("h2[class=margin0]").get(0);
//
//            Element author_info = author.select("a").get(0);
//
//            String username = author_info.text();
//
//            String author_href = author_info.attr("href");
//
//            String tailslug = author_href.replace("/", "");
//
//            author_href = "http://www.caimai.cc" + author_href;
//
//
//            Document doc2 = Jsoup.connect(author_href)
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//            //头像
//            Element avatar = doc2.select("div[class=thumbnail bg-no border-0]").get(0).select("img").get(0);
//
//
//            String courseware = "";
//            String date = "";
//            int size = doc2.select("h3[class=margin0]").size();
//
//            if (size >= 2) {
//                courseware = doc2.select("h3[class=margin0]").get(0).text();
//
//                date = doc2.select("h3[class=margin0]").get(1).text().replace("加入时间：", "");
//            } else {
//                date = doc2.select("h3[class=margin0]").get(0).text().replace("加入时间：", "");
//            }
//
//
//            String meta = "";
//            Elements desc_element = doc2.select("p[class=margin0]");
//
//            if (desc_element.size() > 0) {
//                meta = desc_element.get(0).text();
//            }
//
//            String head_path = "";
//            //下载图片
//            try {
//                String weburl = avatar.attr("src");
//
//
//                HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS("heads"), date);
//
//                head_path = map22.get("trimpath");
//
//
//            } catch (Exception e) {
//
//                log.error("ret pic exception===>" + e.toString());
//            }
//
//            //作者用户信息
//            map.put("username", username);
//            map.put("tailslug", tailslug);
//            map.put("courseware", courseware);
//            map.put("date", date);
//            map.put("meta", meta);
//            map.put("head_path", head_path);
//
//
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//
//        return map;
//    }


    /**
     * 微读吧：停止更新到2018-4-28 爬虫获取情圣的文章
     * loveUdavid
     *
     * @throws IOException
     */
//    public static List<Map<String, String>> retEQArticle(int index) throws IOException {
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        try {
//            String initUrl = "http://www.weiduba.net/author/loveudavid/index" + index + ".html";
//            final String dataResult = HttpGetUtils.getDataResult(initUrl);
//
//            System.out.println(dataResult);
//            Document doc = Jsoup.parse(dataResult, initUrl);
//            Elements info = doc.select("div[class=cl]").select("div[class=titless]");
//            for (Element p : info) {
//
//                HashMap<String, String> map = new HashMap<String, String>();
//                //标题
//                Elements titles = p.select("a");
//
//                String title = titles.get(0).text();
//                title = title.replace("大卫", "");
//                String a_url = "http://www.weiduba.net" + titles.get(0).attr("href");
//
//                Elements dates = p.select("samp[class=title]");
//
//                String date = dates.get(0).ownText();
//
//                //文章
//
//
//                //休眠
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    // TODO Auo-generated catch block
//                    e.printStackTrace();
//                }
//
//                Document doc_ = Jsoup.parse(HttpGetUtils.getDataResult(a_url));
//                Elements articles = doc_.select("div[class=cont]");
//                Element article_ele = articles.get(0);
//                //去掉图片、去掉strong
//                article_ele.select("img").remove();
//                article_ele.select("strong").remove();
//                String article = article_ele.html();
//                article = article.replace("大卫", "<情圣>").replace("<p>请回复：<span style=\"background-color: rgb(255, 251, 0);\">千万别追女神</span></p>", "")
//                        .replace("<p>请回复：<span style=\"background-color: rgb(255, 255, 0);\">千万别追女神</span></p>", "")
//                        .replace("<p>你想学习正确的追女孩技巧，早日抱得美人归吗？</p>", "")
//                        .replace("<p>你想得到<情圣>的帮助，解决棘手的情感问题吗？</p>", "")
//                        .replace("<p><img class=\"\" data-ratio=\"0.736\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"1000\" src=\"http://img.chuansong.me/mmbiz/nMpCNia3hrN4BiaKeiadNc9Cd33C0g7Dt22pn0KOE54PowMzbygnmChH9lpBOLZEK1YvvNJtk1TOgiazP4VhibTAImw/0?wx_fmt=jpeg\" style=\"\"></p>", "")
//                        .replace("<p style=\"white-space: normal;text-align: center;\"><span style=\"color: rgb(255, 169, 0);\"><strong>长按图片赞赏，请<情圣>吃金拱门！</strong></span></p>", "")
//                        .replace("<p style=\"white-space: normal;\"><br></p>", "")
//                        .replace("<p style=\"white-space: normal;\"><img class=\"\" data-copyright=\"0\" data-ratio=\"1\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"1080\" src=\"http://img.chuansong.me/mmbiz_jpg/nMpCNia3hrN67eXCUbQySMmXpKibVaPsicm1KHjywtIvUXWFDlBqnIrZMhCJ1Z1rKg9qM8ETCGt2ypMClrkB1oNHw/0?wx_fmt=jpeg\"></p>", "")
//                        .replace("<p><img class=\"\" data-ratio=\"0.6826196473551638\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"794\" src=\"http://img.chuansong.me/mmbiz/nMpCNia3hrN4BiaKeiadNc9Cd33C0g7Dt22uCIbRO1q9t6uFJSzAmYjvmkkJSAw6ZN78QdqBcGf7hZ2UEFkzLXMGQ/0?wx_fmt=jpeg\" style=\"\"></p>", "")
//                        .replace("<img data-ratio=\"1\" data-w=\"20\" src=\"http://read.html5.qq.com/image?src=forum&amp;q=5&amp;r=0&amp;imgflag=7&amp;imageUrl=https://res.wx.qq.com/mpres/htmledition/images/icon/common/emotion_panel/emoji_wx/2_06.png\" style=\"display:inline-block;width:20px;vertical-align:text-bottom;\">", "")
//
//
//                ;
//
//
//                //简介
//                Elements texts = doc_.select("p");
//                texts.select("a").remove();
//                texts.select("img").remove();
//                texts.select("i").remove();
//                StringBuilder sb = new StringBuilder();
//                for (Element text : texts) {
//
//                    if (text.html().contains("大卫回复")) {
//                        break;
//                    }
//
//                    sb.append(text);
//                }
//                String brief = sb.toString().replaceAll("大卫", "情圣");
//
//
//                // 生成码
//                String ret_code = EnDecryptUtils.md5Encrypt(title);
//
//                map.put("title", title);
//                map.put("a_url", a_url);
//                map.put("brief", brief);
//                map.put("date", date);
//                map.put("article", article);
//                map.put("ret_code", ret_code);
//
//
//                log.info(title + "\t\r" + a_url + "\t\r" + "\t\r" + brief + "\t\r" + article + "\t\r" + date + "-----------------");
//
//                list.add(map);
//
//            }
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//
//        return list;
//    }


    /**
     * 爬虫获取情圣的文章
     * loveUdavid
     * ret_url:http://chuansong.me/account/loveUdavid?start=(i-1)*12
     *
     * @throws IOException
     */
    public static List<Map<String, String>> retEQArticle() throws IOException {
        List<Map<String, String>> list = Lists.newArrayList();
//        for(int i=11;i<=20;i++){
        try {
//            String initUrl = "http://chuansong.me/account/loveudavid?start=" + i*12 ;
            String initUrl = "http://chuansong.me/account/loveudavid";
            final String dataResult = HttpGetUtils.getDataResult(initUrl);
            Document doc = Jsoup.parse(dataResult, initUrl);
            Elements info = doc.select("div[class=pagedlist_item]");
            for (Element p : info) {

                HashMap<String, String> map = new HashMap<String, String>();
                //标题
                Elements titles = p.select("a[class=question_link]");

                String title = titles.get(0).text();
                title = title.replace("大卫", "");
                String a_url = "http://chuansong.me" + titles.get(0).select("a").get(0).attr("href");

                Elements dates = p.select("span[class=timestamp]");

                String date = dates.get(0).text();

                //文章


                //休眠
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auo-generated catch block
                    e.printStackTrace();
                }

                Document doc_ = Jsoup.connect(a_url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(20000) //it's in milliseconds, so this means 5 seconds.
                        .followRedirects(true)
                        .maxBodySize(1024 * 1024 * 3)    //3Mb Max
                        //.ignoreContentType(true) //for download xml, json, etc
                        .get();
                Elements articles = doc_.select("div[class=rich_media_content]");
                Element article_ele = articles.get(0);
                //去掉图片、去掉strong
                article_ele.select("img").remove();
                article_ele.select("strong").remove();
                String article = article_ele.html();
                article = article.replace("大卫", "<情圣>").replace("<p>请回复：<span style=\"background-color: rgb(255, 251, 0);\">千万别追女神</span></p>", "")
                        .replace("<p>请回复：<span style=\"background-color: rgb(255, 255, 0);\">千万别追女神</span></p>", "")
                        .replace("<p>你想学习正确的追女孩技巧，早日抱得美人归吗？</p>", "")
                        .replace("<p>你想得到<情圣>的帮助，解决棘手的情感问题吗？</p>", "")
                        .replace("<p><img class=\"\" data-ratio=\"0.736\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"1000\" src=\"http://img.chuansong.me/mmbiz/nMpCNia3hrN4BiaKeiadNc9Cd33C0g7Dt22pn0KOE54PowMzbygnmChH9lpBOLZEK1YvvNJtk1TOgiazP4VhibTAImw/0?wx_fmt=jpeg\" style=\"\"></p>", "")
                        .replace("<p style=\"white-space: normal;text-align: center;\"><span style=\"color: rgb(255, 169, 0);\"><strong>长按图片赞赏，请<情圣>吃金拱门！</strong></span></p>", "")
                        .replace("<p style=\"white-space: normal;\"><br></p>", "")
                        .replace("<p style=\"white-space: normal;\"><img class=\"\" data-copyright=\"0\" data-ratio=\"1\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"1080\" src=\"http://img.chuansong.me/mmbiz_jpg/nMpCNia3hrN67eXCUbQySMmXpKibVaPsicm1KHjywtIvUXWFDlBqnIrZMhCJ1Z1rKg9qM8ETCGt2ypMClrkB1oNHw/0?wx_fmt=jpeg\"></p>", "")
                        .replace("<p><img class=\"\" data-ratio=\"0.6826196473551638\" data-s=\"300,640\" data-type=\"jpeg\" data-w=\"794\" src=\"http://img.chuansong.me/mmbiz/nMpCNia3hrN4BiaKeiadNc9Cd33C0g7Dt22uCIbRO1q9t6uFJSzAmYjvmkkJSAw6ZN78QdqBcGf7hZ2UEFkzLXMGQ/0?wx_fmt=jpeg\" style=\"\"></p>", "")
                        .replace("<img data-ratio=\"1\" data-w=\"20\" src=\"http://read.html5.qq.com/image?src=forum&amp;q=5&amp;r=0&amp;imgflag=7&amp;imageUrl=https://res.wx.qq.com/mpres/htmledition/images/icon/common/emotion_panel/emoji_wx/2_06.png\" style=\"display:inline-block;width:20px;vertical-align:text-bottom;\">", "")


                ;


                //简介
                Elements texts = doc_.select("p");
                texts.select("a").remove();
                texts.select("img").remove();
                texts.select("i").remove();
                StringBuilder sb = new StringBuilder();
                for (Element text : texts) {

                    if (text.html().contains("大卫回复")) {
                        break;
                    }

                    sb.append(text);
                }
                String brief = sb.toString().replaceAll("大卫", "情圣");


                // 生成码
                String ret_code = NorthParkCryptUtils.md5Encrypt(title);

                map.put("title", title);
                map.put("a_url", a_url);
                map.put("brief", brief);
                map.put("date", date);
                map.put("article", article);
                map.put("ret_code", ret_code);


                log.info(title + "\t\r" + a_url + "\t\r" + "\t\r" + brief + "\t\r" + article + "\t\r" + date + "-----------------");

                list.add(map);

            }
        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
            ;
        }


        //休眠10秒
        try {
            Thread.sleep(1000 * 5);
//			    log.info("第"+i+"页================");
        } catch (InterruptedException e) {
            // TODO Auo-generated catch block
            e.printStackTrace();
        }
//        }
        return list;
    }




    /**
     * 爬取1页的mac软件内容
     */
//    public static List<Map<String, String>> retSoft(Integer index) {
//        // TODO Auto-generated method stub
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        HashMap<String, String> map = null;
//        try {
//            Document doc = Jsoup.connect("http://www.sdifen.com/category/black-apple/apple-software/page/" + index + "/")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                    .referrer("http://www.google.com")
//                    .timeout(1000 * 30) //it's in milliseconds, so this means 5 seconds.
//                    .get();
//
//
//            Elements articles = doc.select("article");
//            if (!articles.isEmpty()) {
//                for (int i = 0; i < articles.size(); i++) {
//
//                    Element article = articles.get(i);
//
//                    //code
//                    String code = article.attr("id");
//
//                    log.info("code====================" + code);
//
//                    //判断code在系统不存在再去处理后面的事
//
//                    SoftManager softManager = (SoftManager) SpringContextUtils.getBean("SoftManager");
//                    int flag = softManager.countHql(" where o.ret_code= '" + code + "' ");
//
//                    if (flag <= 0) {
//
//                        map = new HashMap<String, String>();
//
//                        //标题
//                        Elements titles = article.getElementsByClass("entry-title");
//
//                        String title = titles.get(0).text();
//                        log.info("title====================" + title);
//                        String a_url = titles.get(0).select("a").get(0).attr("href");
//
//                        //标签tags
//
//                        Elements tags = article.select("span[class=cat-links]");
//
//                        String tag = tags.get(0).text();
//                        tag = tag.replaceAll("发表在", "").replaceAll(" ", "");
//
//                        log.info("tag====================" + tag);
//                        //计算标签编码、
//                        String tag_code = "005";
//                        if (tag.contains("应用")) {
//                            tag_code = "001";
//                            tag = "系统、应用软件";
//                        } else if (tag.contains("开发")) {
//                            tag_code = "002";
//                            tag = "开发、设计软件";
//                        } else if (tag.contains("媒体")) {
//                            tag_code = "003";
//                            tag = "媒体软件";
//                        } else if (tag.contains("安全")) {
//                            tag_code = "004";
//                            tag = "网络、安全软件";
//                        } else if (tag.contains("其他")) {
//                            tag_code = "005";
//                            tag = "其他软件";
//                        } else if (tag.contains("游戏")) {
//                            tag_code = "006";
//                            tag = "游戏一箩筐";
//                        } else if (tag.contains("限时免费")) {
//                            tag_code = "007";
//                            tag = "限免软件";
//                        } else if (tag.contains("疑难")) {
//                            tag_code = "008";
//                            tag = "疑难杂症";
//                        } else {
//                            tag_code = "005";
//                            tag = "其他软件";
//                        }
//                        log.info("tag_code====================" + tag_code);
//
//                        //日期
//                        Elements dates = article.select("time[class=entry-date]");
//                        String date = dates.get(0).text();
//                        date = date.replaceAll(" ", "");
//                        date = date.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
//                        log.info("date====================" + date);
//                        //月
//                        String month = date.substring(0, date.lastIndexOf("-"));
//                        log.info("month====================" + month);
//                        //年
//                        String year = month.substring(0, month.lastIndexOf("-"));
//                        log.info("year====================" + year);
//                        //文章
//                        StringBuilder sb = new StringBuilder();
//
//
//                        //休眠
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            // TODO Auo-generated catch block
//                            e.printStackTrace();
//                        }
//
//                        Document doc_ = Jsoup.connect(a_url)
//                                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
//                                .referrer("http://www.google.com")
//                                .timeout(1000 * 30) //it's in milliseconds, so this means 5 seconds.
//                                .get();
//                        Elements article_alls = doc_.select("div[class=entry-content] p");
//                        if (!article_alls.isEmpty()) {
//                            for (int i1 = 0; i1 < article_alls.size(); i1++) {
//
//                                Elements imgs = article_alls.get(i1).select("img");
//                                for (int j = 0; j < imgs.size(); j++) {
//                                    try {
//                                        String weburl = imgs.get(j).attr("src");
//                                        //web图片上传到七牛
//
//                                        //-------------开始--------------------------------
//
//                                        HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS(), date);
//
//                                        String rs = QiniuUtils.getInstance.upload(map22.get("localpath"), "soft/" + date + "/" + map22.get("key"));
//
//                                        //-------------结束--------------------------------
//
//                                        imgs.get(j).attr("src", rs);
//                                        imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
//                                    } catch (Exception e) {
//
//                                        log.error("ret pic exception===>" + e.toString());
//                                        continue;
//                                    }
//
//                                }
//
//                                String p1 = article_alls.get(i1).html();
//                                if (!p1.contains("本文链接") && !p1.contains("转载声明")) {
//                                    sb.append("<p>" + p1 + "</p>");
//                                }
//                            }
//                        }
//
//                        String text = sb.toString().replaceAll("小子", "小布");
//                        log.info("article=============" + text);
//                        //简介
//                        Elements briefs = article.select("div[class=entry-content] p");
//
//                        StringBuilder sb2 = new StringBuilder();
//
//                        if (!briefs.isEmpty()) {
//                            for (int i1 = 0; i1 < briefs.size(); i1++) {
//
//                                Elements imgs = briefs.get(i1).select("img");
//                                for (int j = 0; j < imgs.size(); j++) {
//
//                                    try {
//
//                                        String weburl = imgs.get(j).attr("src");
//                                        //web图片上传到七牛
//
//                                        //-------------开始--------------------------------
//
//                                        HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS(), date);
//
//                                        String rs = QiniuUtils.getInstance.upload(map22.get("localpath"), "soft/" + date + "/" + map22.get("key"));
//
//                                        //-------------结束--------------------------------
//
//                                        imgs.get(j).attr("src", rs);
//                                        imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
//                                    } catch (Exception e) {
//
//                                        log.error("ret pic exception===>" + e.toString());
//                                        continue;
//                                    }
//
//                                }
//                                String p1 = briefs.get(i1).html();
//                                if (!p1.contains("继续阅读")) {
//                                    sb2.append("<p>" + p1 + "</p>");
//                                }
//                            }
//                        }
//
//                        String brief = sb2.toString().replaceAll("小子", "小布");
//
//                        log.info("brief====================" + brief);
//                        map.put("title", title);
//                        map.put("a_url", a_url);
//                        map.put("brief", brief);
//                        map.put("date", date);
//                        map.put("article", text);
//                        map.put("tag", tag);
//                        map.put("code", code);
//                        map.put("os", "mac");
//                        map.put("month", month);
//                        map.put("year", year);
//                        map.put("tag_code", tag_code);
//                        list.add(map);
//
//                    }
//                }
//            }
//
////                log.info(title+"\t\r"+a_url+"\t\r"+"\t\r"+brief+"\t\r"+article+"\t\r"+date+"-----------------");
//
//        } catch (Exception e) {
//            log.error("HTMLPARSERutils------->", e);
//            ;
//        }
//
//        return list;
//    }


    /**
     * 0DayDown
     * 爬取1页的mac软件内容
     */
    public static List<Map<String, String>> retSoft0DayDown(Integer index) {
        List<Map<String, String>> list = Lists.newArrayList();
        HashMap<String, String> map = null;
        try {

            String initUrl = "https://www.0daydown.com/category/software/mac/page/" + index;
            String dataResult = HttpGetUtils.getDataByHtmlUnit(initUrl);

            while (StringUtils.isBlank(dataResult)) {
                dataResult = HttpGetUtils.getDataByHtmlUnit(initUrl);
            }

            System.out.println(dataResult);
            Document doc = Jsoup.parse(dataResult, initUrl);

            Elements soft11 = null;
            //取得一页内容梗概
            soft11 = doc.select("div.content").select("article");

            for (Element e : soft11) {

                try {

                    String url = e.select("a").get(0).attr("href");
                    String img_url = e.select("img").get(0).attr("data-src");


                    //获取全文的内容
                    String dataResult1 = HttpGetUtils.getDataByHtmlUnit(url);
                    while (StringUtils.isBlank(dataResult1)) {
                        dataResult1 = HttpGetUtils.getDataByHtmlUnit(url);
                    }


                    /*System.out.println(dataResult1);*/
                    Document parse = Jsoup.parse(dataResult1, url);


                    //日期
                    String date = "";
                    try {
                        date = parse.select("time").get(0).text();

                        System.err.println(date);
                        if (date.contains("天前")) {
                            String N = date.replace("天前", "");
                            date = TimeUtils.getDayAfterOrBeforeN(TimeUtils.nowTime(), -Integer.parseInt(N));
                        }else if(date.contains("周前")){
                            String dateCut = date.substring(date.indexOf("(")+1,date.indexOf(")"));
                            date= TimeUtils.getYear("2022")+"-"+dateCut;
                        }else if(date.contains("月前")){
                            String dateCut = date.substring(date.indexOf("(")+1,date.indexOf(")"));
                            date= TimeUtils.getYear("2022")+"-"+dateCut;
                        }

                    } catch (Exception e2) {

                        date = TimeUtils.nowDate();
                    }


                    if (StringUtils.isNotEmpty(date) && date.contains("前")) date = TimeUtils.nowDate();
                    date = date.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
                    log.info("date====================" + date);

//                    try {
//                        date = TimeUtils.pointToSimle(date);
//                    } catch (ParseException ex) {
//                        date = TimeUtils.nowdate();
//
//                    }
                    //月
                    String month = date.substring(0, date.lastIndexOf("-"));
                    log.info("month====================" + month);
                    //年
                    String year = month.substring(0, month.lastIndexOf("-"));
                    log.info("year====================" + year);


                    //标题
                    String title = "";
                    try {
                        title = parse.select("h1.article-title").get(0).text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("h1.article-title"));
                        log.error("h1.article-title不包含h1标题，请检查文本内容");
                        try {
                            title = parse.select("div.breadcrumbs").select("span.muted").get(0).text();
                        } catch (Exception ignore) {

                            log.info("---->", parse.select("div.breadcrumbs").select("span.muted"));
                            log.error("面包屑不包含标题，请检查文本内容");
                        }
                    }

                    //唯一码
                    String code = title.toLowerCase().replace("macos", "").trim().replace(" ", "-").replaceAll(".html", "");


                    //标签
                    String tag = "";

                    try {
                        tag = parse.select("div.hot-tags").select("a").text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("div.hot-tags"));
                        log.error("div.hot-tags不包含tag标签，请检查文本内容");
                    }


                    //处理软件logo上传
                    HashMap<String, String> map11 = HTMLParserUtil.webPic2Disk(img_url, getLocalFolderByOS(), date);

                    String logo_url = QiniuUtils.getInstance().upload(map11.get("localpath"), "soft/" + date + "/" + map11.get("key"));

                    String logo_p = "<p><img class=\"aligncenter size-full wp-image-" + code + "\" title=\"" + title + "\" alt=\"" + title + "\" src=\"" + logo_url + "\" width=\"300\" height=\"300\" style=\"max-width: 424.566px;\">";


                    //计算标签编码、
                    String tag_code = "005";
                    if (tag.contains("应用") || tag.contains("系统")) {
                        tag_code = "001";
                        tag = "系统、应用软件";
                    } else if (tag.contains("开发") || tag.contains("设计")) {
                        tag_code = "002";
                        tag = "开发、设计软件";
                    } else if (tag.contains("媒体")) {
                        tag_code = "003";
                        tag = "媒体软件";
                    } else if (tag.contains("安全") || tag.contains("网络")) {
                        tag_code = "004";
                        tag = "网络、安全软件";
                    } else if (tag.contains("其他")) {
                        tag_code = "005";
                        tag = "其他软件";
                    } else if (tag.contains("游戏")) {
                        tag_code = "006";
                        tag = "游戏一箩筐";
                    } else if (tag.contains("限时免费")) {
                        tag_code = "007";
                        tag = "限免软件";
                    } else if (tag.contains("疑难")) {
                        tag_code = "008";
                        tag = "疑难杂症";
                    } else {
                        tag_code = "005";
                        tag = "其他软件";
                    }
                    log.info("tag_code====================" + tag_code);

                    //正文
                    String article = "";

                    //简介
                    StringBuilder brief = new StringBuilder();

                    //下载地址
                    String path = "";


                    Element articles = parse.select("article.article-content").get(0);

                    //处理征文图像
                    Elements imgs = articles.select("img");
                    for (int j = 0; j < imgs.size(); j++) {
                        try {
                            String weburl = imgs.get(j).attr("src");
                            if(weburl.contains("data:image")){
                                weburl = imgs.get(j).attr("data-src");
                            }
                            //   web图片上传到七牛

                            //-------------开始--------------------------------

                            HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS(), date);

                            String rs = QiniuUtils.getInstance().upload(map22.get("localpath"), "soft/" + date + "/" + map22.get("key"));

                            //-------------结束--------------------------------
                            if (StringUtils.isNotEmpty(rs)) {
                                imgs.get(j).attr("src", rs);
                            }
                            imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
                        } catch (Exception e1) {

                            log.error("ret pic exception===>" + e1.toString());
                            continue;
                        }

                    }


                    //处理正文的不合法url
                    Elements a1 = articles.select("a");
                    for (Element s : a1) {
                        if (s.attr("href").contains("/tag")) {
                            s.before(s.text());
                            s.remove();
                        } else if (s.attr("href").contains("/xpay-html")) {
                            s.remove();
                        } else if (s.attr("href").endsWith(".jpg") || s.attr("href").endsWith(".jpeg") || s.attr("href").endsWith(".png")) {
                            s.attr("href", "");
                        } else if (s.attr("href").contains("waitsun.com/?dl_id")) {//改成短连接  并且改样式
                            s.addClass("btn-warning").addClass("btn");
                        }
                    }

                    //删除vip
                    Elements vip = articles.select("div.erphpdown");
                    if (vip.html().contains("此资源仅限VIP下载")) {
                        vip.remove();
                    }

                    //处理下载地址
                    StringBuilder sb_path = new StringBuilder();


                    //新的解析下载地址
                    //有下载框，不处理a连接
                    Elements downSelect = parse.select("article").select("div.blockSpoiler-content");
                    if (downSelect.size() > 0) {
                        System.out.println("处理后的下载样式---->" + downSelect.html());
                        sb_path.append(downSelect.html());
                        //删除正文内的downselect
                        downSelect.remove();
                    } else {
                        //执行2次抓取下载地址
                        Elements pp = articles.select("p");
                        if (!CollectionUtils.isEmpty(pp)) {
                            for (Element p : pp) {
                                Elements aa = p.select("a");
                                if (!CollectionUtils.isEmpty(aa)) {

                                    for (Element element : aa) {
                                        String outerHtml = element.outerHtml();
                                        if (outerHtml.contains("下载") || outerHtml.contains(".com") || outerHtml.contains(".to") || outerHtml.contains("网盘")) {
                                            System.out.println("========================");

                                            System.out.println("===========是=============");
                                            String download = p.outerHtml();
                                            System.out.println(download);
                                            sb_path.append(download);
                                            //删除最后的路径元素
                                            p.remove();
                                        } else {
                                            System.out.println("========================");

                                            System.out.println("===========否=============");
                                        }
                                    }


                                }
                            }

                        }


                    }

                    System.out.println("sb_path===================>" + sb_path.toString().replace("svg", "span"));
                    path = sb_path.toString()
                    ;

                    //设置正文
                    article = articles.html();
                    brief.append(logo_p);
                    brief.append("<p>");
                    int id = 0;
                    while (articles.select("p").get(id).html().contains("<img")) {
                        id++;
                    }
                    brief.append(articles.select("p").get(id).html());
                    brief.append("</p>");
                    System.out.println("===================================================================================================");
                    System.out.println("正文内容===================>" + articles.html());
                    System.out.println("===================================================================================================");

                    map = new HashMap<>();
                    map.put("title", title);
                    map.put("a_url", url);
                    map.put("brief", brief.toString());
                    map.put("date", date);
                    map.put("article", article);
                    map.put("tag", tag);
                    map.put("code", code);
                    map.put("os", "mac");
                    map.put("month", month);
                    map.put("year", year);
                    map.put("tag_code", tag_code);
                    map.put("path", path);
                    map.put("color", PinyinUtil.getFirstChar(title));
                    list.add(map);

//                    }


                } catch (Exception e2) {
                    log.error("HTMLPARSERutils------->", e2);
                }
            }


            System.out.println(index + "页爬取完毕！ the end===============================================================================================");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }



    /**
     * 潘多拉盒子
     * 爬取1页的mac软件内容
     */
    public static List<Map<String, String>> retSoftNew(Integer index) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = Lists.newArrayList();
        HashMap<String, String> map = null;
        try {

            String initUrl = "https://www.inpandora.com/resources/page/" + index;
            final String dataResult = HttpGetUtils.getDataByHtmlUnit(initUrl);

            System.out.println(dataResult);
            Document doc = Jsoup.parse(dataResult, initUrl);

            Elements soft11 = null;
            //取得一页内容梗概
            soft11 = doc.select("div.articles-list").select("article.post");


            for (Element e : soft11) {
                try {

                    //跳过ad link
                    if (e.select("a").get(0).attr("href").contains("iamxk.com")) {
                        continue;
                    }
                    String url = e.select("a").get(0).attr("href");
                    String img_url = e.select("img").get(0).attr("data-src");


                    //获取全文的内容
                    String dataResult1 = HttpGetUtils.getDataByHtmlUnit(url);

                    /*System.out.println(dataResult1);*/
                    Document parse = Jsoup.parse(dataResult1, url);

                    //唯一码
                    String code = url.substring(url.lastIndexOf("/") + 1, url.length()).replaceAll(".html", "");



                    //日期
                    String date = "";
                    try {
                        date = parse.select("em.meta-post_date").get(0).text();

                    } catch (Exception e2) {

                        date = TimeUtils.nowDate();
                    }


                    if (StringUtils.isNotEmpty(date) && date.contains("前")) date = TimeUtils.nowDate();
                    date = date.replaceAll(" ", "");
                    date = date.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
                    log.info("date====================" + date);

                    try {
                        date = TimeUtils.pointToSimle(date);
                    } catch (ParseException ex) {
                        date = TimeUtils.nowDate();

                    }
                    //月
                    String month = date.substring(0, date.lastIndexOf("-"));
                    log.info("month====================" + month);
                    //年
                    String year = month.substring(0, month.lastIndexOf("-"));
                    log.info("year====================" + year);


                    //标题
                    String title = "";
                    try {
                        title = parse.select("h1.title-detail").get(0).text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("h1.title-detail"));
                        log.error("h1.title-detail不包含h1标题，请检查文本内容");
                        try {
                            title = parse.select("div.bread-crumbs").select("strong").get(0).text();
                        } catch (Exception ignore) {

                            log.info("---->", parse.select("div.bread-crumbs").select("strong"));
                            log.error("面包屑不包含标题，请检查文本内容");
                        }
                    }

                    //标签
                    String tag = "";

                    try {
                        tag = parse.select("div.hot-tags").select("a").text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("div.hot-tags"));
                        log.error("div.hot-tags不包含tag标签，请检查文本内容");
                    }


                    //处理软件logo上传
                    HashMap<String, String> map11 = HTMLParserUtil.webPic2Disk(img_url, getLocalFolderByOS(), date);

                    String logo_url = QiniuUtils.getInstance().upload(map11.get("localpath"), "soft/" + date + "/" + map11.get("key"));

                    String logo_p = "<p><img class=\"aligncenter size-full wp-image-" + code + "\" title=\"" + title + "\" alt=\"" + title + "\" src=\"" + logo_url + "\" width=\"300\" height=\"300\" style=\"max-width: 424.566px;\">";


                    //计算标签编码、
                    String tag_code = "005";
                    if (tag.contains("应用") || tag.contains("系统")) {
                        tag_code = "001";
                        tag = "系统、应用软件";
                    } else if (tag.contains("开发") || tag.contains("设计")) {
                        tag_code = "002";
                        tag = "开发、设计软件";
                    } else if (tag.contains("媒体")) {
                        tag_code = "003";
                        tag = "媒体软件";
                    } else if (tag.contains("安全") || tag.contains("网络")) {
                        tag_code = "004";
                        tag = "网络、安全软件";
                    } else if (tag.contains("其他")) {
                        tag_code = "005";
                        tag = "其他软件";
                    } else if (tag.contains("游戏")) {
                        tag_code = "006";
                        tag = "游戏一箩筐";
                    } else if (tag.contains("限时免费")) {
                        tag_code = "007";
                        tag = "限免软件";
                    } else if (tag.contains("疑难")) {
                        tag_code = "008";
                        tag = "疑难杂症";
                    } else {
                        tag_code = "005";
                        tag = "其他软件";
                    }
                    log.info("tag_code====================" + tag_code);

                    //正文
                    String article = "";

                    //简介
                    StringBuilder brief = new StringBuilder();

                    //下载地址
                    String path = "";


                    Element articles = parse.select("article.article-detail").get(0);

                    //处理征文图像
                    Elements imgs = articles.select("img");
                    for (int j = 0; j < imgs.size(); j++) {
                        try {
//                            String weburl = imgs.get(j).attr("src");
//                            //   web图片上传到七牛
//
//                            //-------------开始--------------------------------
//
//                            HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS(), date);
//
//                            String rs = QiniuUtils.getInstance().upload(map22.get("localpath"), "soft/" + date + "/" + map22.get("key"));
//
//                            //-------------结束--------------------------------
//                            if (StringUtils.isNotEmpty(rs)) {
//                                imgs.get(j).attr("src", rs);
//                            }
                            imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
                        } catch (Exception e1) {

                            log.error("ret pic exception===>" + e1.toString());
                            continue;
                        }

                    }


                    //处理正文的不合法url
                    Elements a1 = articles.select("a");
                    for (Element s : a1) {
                        if (s.attr("href").contains("/tag")) {
                            s.before(s.text());
                            s.remove();
                        } else if (s.attr("href").contains("/xpay-html")) {
                            s.remove();
                        } else if (s.attr("href").endsWith(".jpg") || s.attr("href").endsWith(".jpeg") || s.attr("href").endsWith(".png")) {
                            s.attr("href", "");
                        } else if (s.attr("href").contains("waitsun.com/?dl_id")) {//改成短连接  并且改样式
                            s.addClass("btn-warning").addClass("btn");
                        }
                    }

                    //处理下载地址
                    StringBuilder sb_path = new StringBuilder();

                    /*********注释掉以前的旧有解析下载
                     *
                     //执行2次抓取下载地址
                     Elements last = articles.select("a");
                     if(!CollectionUtils.isEmpty(last)) {

                     for (Element element : last) {
                     String outerHtml = element.outerHtml();
                     if(outerHtml.contains("下载")||outerHtml.contains("www.waitsun.com")||outerHtml.contains("ctfile.com")) {
                     System.out.println("========================");

                     System.out.println("===========是=============");
                     String download = outerHtml;
                     System.out.println(download);
                     sb_path.append(download);
                     //删除最后的路径元素
                     element.remove();
                     }else {
                     System.out.println("========================");

                     System.out.println("===========否=============");
                     }
                     }


                     }
                     **/

                    //新的解析下载地址
                    Elements downSelect = parse.select("div.version-box");
                    downSelect.select("table").removeClass("table").removeClass("table-version");
                    downSelect.select(".icon").select("icon-bdpan").remove();

                    downSelect.select("td").after("<span>  </span>");
                    downSelect.select("th").after("<span>  </span>");
                    downSelect.select("a:contains(下载)").forEach(i -> {
                        i.select("svg").remove();
                        i.addClass("btn btn-hero").attr("href",i.attr("data-dl-url"));

                        try {
                            i.append(i.attr("data-clipboard-text"));
                        } catch (Exception ex) {

                        }
                    });
                    downSelect.select("div.view-more-bar").remove();

                    System.out.println("处理后的下载样式---->"+downSelect.html());

                    sb_path.append(downSelect.html());
                    System.out.println("sb_path===================>" + sb_path.toString().replace("svg","span"));
                    path = sb_path.toString()
                            .replace("<div class=\"vb-inner\">","<div class=\"vb-inner  table-responsive\">")
                            .replace("<table class=\"\">","<table class=\"table text-nowrap table-striped\" style=\"overflow: auto;\">")
                            .replace("<svg","<i")
                            .replace("/svg>","/i>")
                    ;

                    //设置正文
                    article = articles.html();
                    brief.append(logo_p);
                    brief.append("<p>");
                    brief.append(articles.select("p").get(0).html());
                    brief.append("</p>");
                    System.out.println("===================================================================================================");
                    System.out.println("正文内容===================>" + articles.html());
                    System.out.println("===================================================================================================");


                    map = new HashMap<>();
                    map.put("title", title);
                    map.put("a_url", url);
                    map.put("brief", brief.toString());
                    map.put("date", date);
                    map.put("article", article);
                    map.put("tag", tag);
                    map.put("code", code);
                    map.put("os", "mac");
                    map.put("month", month);
                    map.put("year", year);
                    map.put("tag_code", tag_code);
                    map.put("path", path);
                    list.add(map);

//                    }
                } catch (Exception e2) {
                    log.error("HTMLPARSERutils------->", e2);
                    continue;
                }

            }
            System.out.println(index + "页爬取完毕！ the end===============================================================================================");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }


    /**
     * http://www.chaoxz.com/
     * 爬取1页的mac软件内容
     */
    public static List<Map<String, String>> retSoftChaoxzCom(Integer index) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = Lists.newArrayList();
        HashMap<String, String> map = null;
        try {


            String initUrl = "http://www.chaoxz.com/category/mac%e8%bd%af%e4%bb%b6/page/" + index;
            final String dataResult = HttpGetUtils.getDataByHtmlUnit(initUrl);

            System.out.println(dataResult);
            Document doc = Jsoup.parse(dataResult, initUrl);

            Elements soft11 = null;
            //取得一页内容梗概
            soft11 = doc.select("div.site-content").select("article");


            for (Element e : soft11) {
                try {

                    String url = e.select("a").get(0).attr("href");
                    String img_url = e.select("img").get(0).attr("src");

                    //获取全文的内容
                    String dataResult1 = HttpGetUtils.getDataByHtmlUnit(url);

                    /*System.out.println(dataResult1);*/
                    Document parse = Jsoup.parse(dataResult1, url);

                    //唯一码
                    if (url.endsWith("/")) {
                        url = url.substring(0, url.length() - 1);
                    }
                    String code = url.substring(url.lastIndexOf("/") + 1, url.length()).replaceAll(".html", "");



                    //日期======================================================================================================================================
                    String date = "";
                    try {
                        date = parse.select("div.single-content").select("div.videos-content").select("span.date").get(0).ownText();

                    } catch (Exception e2) {

                        date = TimeUtils.nowDate();
                    }


                    if (StringUtils.isNotEmpty(date) && date.contains("前")) date = TimeUtils.nowDate();
                    date = date.replace(" ", "").replace("年", "-").replace("月", "-").replace("日", "");
                    log.info("date====================" + date);

                    //月
                    String month = date.substring(0, date.lastIndexOf("-"));
                    log.info("month====================" + month);
                    //年
                    String year = month.substring(0, month.lastIndexOf("-"));
                    log.info("year====================" + year);
                    //日期======================================================================================================================================

                    //标题
                    String title = "";
                    try {
                        title = parse.select("header.entry-header").select("h1").get(0).text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("h1.title-detail"));
                        log.error("h1.title-detail不包含h1标题，请检查文本内容");
                    }

                    //标签===================================================================================================
                    String tag = "";

                    try {
                        tag = parse.select("span.category").select("a").text();
                    } catch (Exception e2) {

                        log.info("---->", parse.select("div.hot-tags"));
                        log.error("div.hot-tags不包含tag标签，请检查文本内容");
                    }
                    //计算标签编码、
                    String tag_code = "005";
                    if (tag.contains("应用") || tag.contains("系统")) {
                        tag_code = "001";
                        tag = "系统、应用软件";
                    } else if (tag.contains("开发") || tag.contains("设计")) {
                        tag_code = "002";
                        tag = "开发、设计软件";
                    } else if (tag.contains("媒体")) {
                        tag_code = "003";
                        tag = "媒体软件";
                    } else if (tag.contains("安全") || tag.contains("网络")) {
                        tag_code = "004";
                        tag = "网络、安全软件";
                    } else if (tag.contains("其他")) {
                        tag_code = "005";
                        tag = "其他软件";
                    } else if (tag.contains("游戏")) {
                        tag_code = "006";
                        tag = "游戏一箩筐";
                    } else if (tag.contains("限时免费")) {
                        tag_code = "007";
                        tag = "限免软件";
                    } else if (tag.contains("疑难")) {
                        tag_code = "008";
                        tag = "疑难杂症";
                    } else {
                        tag_code = "005";
                        tag = "其他软件";
                    }
                    log.info("tag_code====================" + tag_code);
                    //标签===================================================================================================


                    //处理软件logo上传
                    HashMap<String, String> map11 = HTMLParserUtil.webPic2Disk(img_url, getLocalFolderByOS(), date);

                    String logo_url = QiniuUtils.getInstance().upload(map11.get("localpath"), "soft/" + date + "/" + map11.get("key"));

                    String logo_p = "<p><img class=\"aligncenter size-full wp-image-" + code + "\" title=\"" + title + "\" alt=\"" + title + "\" src=\"" + logo_url + "\" width=\"300\" height=\"300\" style=\"max-width: 424.566px;\">";




                    //正文==============================================================================================================
                    String article = "";

                    Elements articles = parse.select("article").select("div.entry-content");

                    //删除概览
                    articles.select("div.videos-content").remove();
                    //删除微信
                    articles.select("div.s-weixin-one").remove();
                    //删除分享
                    articles.select("div[id=social]").remove();
                    //删除footer
                    articles.select("footer.single-footer").remove();

                    //处理正文图像
                    Elements imgs = articles.select("img");
                    for (int j = 0; j < imgs.size(); j++) {
                        try {
                            String weburl = imgs.get(j).attr("src");
                            //   web图片上传到七牛

                            //-------------开始--------------------------------

                            HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOS(), date);

                            String rs = QiniuUtils.getInstance().upload(map22.get("localpath"), "soft/" + date + "/" + map22.get("key"));

                            //-------------结束--------------------------------
                            if (StringUtils.isNotEmpty(rs)) {
                                imgs.get(j).attr("src", rs);
                            }
                            imgs.get(j).removeAttr("srcset");
                            imgs.get(j).removeAttr("sizes");
                            imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
                        } catch (Exception e1) {

                            log.error("ret pic exception===>" + e1.toString());
                            continue;
                        }

                    }

                    //处理正文的不合法url
                    Elements a1 = articles.select("a");
                    for (Element s : a1) {
                        if (s.attr("href").contains("/tag")) {
                            s.before(s.text());
                            s.remove();
                        } else if (s.attr("href").contains("/xpay-html")) {
                            s.remove();
                        } else if (s.attr("href").endsWith(".jpg") || s.attr("href").endsWith(".jpeg") || s.attr("href").endsWith(".png")) {
                            s.attr("href", "");
                        } else if (s.attr("href").contains("waitsun.com/?dl_id")) {//改成短连接  并且改样式
                            s.addClass("btn-warning").addClass("btn");
                        }
                    }

                    //正文==============================================================================================================




                    //下载地址==============================================================================================================
                        String path = "";

                        //处理下载地址
                        StringBuilder sb_path = new StringBuilder();


                        //新的解析下载地址
                        String downLoadUrl = articles.select("div.down-form").select("a").get(0).attr("href");
                        String downPage = HttpGetUtils.getDataByHtmlUnit(downLoadUrl);

                        Document downInfo = Jsoup.parse(downPage, downLoadUrl);

                        Elements downs = downInfo.select("div.down-main");
                        Elements downBtns = downs.select("div.down-list-box").select("div.down-but");

                        for (int i = 0; i < downBtns.size(); i++) {
                            if(downBtns.get(i).outerHtml().contains("正版")){
                                downBtns.get(i).addClass("removesByBruce");
                            }else{
                                downBtns.get(i).addClass("btn btn-hero");
                            }
                        }
                        downBtns.select(".removesByBruce").remove();

                        sb_path.append(downs.html());
                        System.out.println("sb_path===================>" + sb_path.toString());
                        path = sb_path.toString();

                        //删除正文的下载模块
                        articles.select("div.down-form").remove();
                    //下载地址==============================================================================================================

                    //设置正文
                    article = articles.html();

                    //简介
                    StringBuilder brief = new StringBuilder();
                    brief.append(logo_p);
                    brief.append("<p>");
                    brief.append(articles.select("p").get(0).html());
                    brief.append("</p>");



                    map = new HashMap<>();
                    map.put("title", title);
                    map.put("a_url", url);
                    map.put("brief", brief.toString());
                    map.put("date", date);
                    map.put("article", article);
                    map.put("tag", tag);
                    map.put("code", code);
                    map.put("os", "mac");
                    map.put("month", month);
                    map.put("year", year);
                    map.put("tag_code", tag_code);
                    map.put("path", path);
                    list.add(map);

//                    }
                } catch (Exception e2) {
                    log.error("HTMLPARSERutils------->", e2);
                    continue;
                }

            }
            System.out.println(index + "页爬取完毕！ the end===============================================================================================");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }

    /**
     * 爬取 河马BT电影站的电影-SQ
     * @param index
     * @return
     */
    public static List<Map<String, String>> ret_SQ_hema(Integer index) {

        return ret_hema_type(index,BC_Constant.HEMA_BT.SQ);
    }

    /**
     * 爬取 河马BT电影站的电影
     * @param index
     * @return
     */
    public static List<Map<String, String>> ret_hema_type(Integer index, BC_Constant.HEMA_BT bt_type) {

        List<Map<String, String>> list = Lists.newArrayList();
        final String BT_HM = "http://www.hemabt.com";
        String url = null;

        if(bt_type.name().equals("GUIFU")){
             url = BT_HM+bt_type.getName()+index;
        }else {
             url = BT_HM+bt_type.getName()+index+".html";
        }


        final String dataResult = HttpGetUtils.getDataResult(url,"gb2312");

        System.out.println(dataResult);
        Document doc = Jsoup.parse(dataResult, url);

//     w700->listpic | listtxt   w960tv

        Element ul = doc.select("div[class=w700]").get(0);

        Elements lis = ul.select("div[class=listpic]");

        if (!lis.isEmpty()) {
            for (int i = 0; i < lis.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                    Element li = lis.get(i);
                    String logo_url = BT_HM + li.select("img").get(0).attr("src");

                    HashMap<String, String> pic2Disk = HTMLParserUtil.webPic2Disk(logo_url, getLocalFolderByOS("mv"), "mv_");
//

                    String logo_p = "";

                    String a_url = BT_HM + li.select("a").get(0).attr("href");
                    String title = li.select("a").get(0).attr("title");
                    map.put("title", title);
                    System.err.println(logo_url);
                    System.err.println(a_url);
                    System.err.println(JsonUtil.object2json(pic2Disk));


                    //获取详情
                    String dataResult_ = HttpGetUtils.getDataResult(a_url,"gb2312");

                    Document doc_ = Jsoup.parse(dataResult_, a_url);

                    //获取详情1
                    //w960tv -> nrwjm
                    Element info = doc_.select("div.nrwjm").get(0);

                    info.select("a").removeAttr("href");

                    Elements info_lis = info.select("li");

                    map.put("a_url", a_url);
                    if(!CollectionUtils.isEmpty(info_lis)){
                        for (Element info_li : info_lis) {
                            String html = info_li.html();
                            if(html.contains("<h3")){
                                String ret_code = NorthParkCryptUtils.md5Encrypt(title);

                                map.put("ret_code", ret_code);

                                logo_p = "<p><img class=\"aligncenter size-full wp-image-" + ret_code + "\" title=\"" + title + "\" alt=\"" + title + "\" " +
                                        "src=\"/" +
                                        pic2Disk.get("trimPan") + "\" width=\"300\" height=\"300\" style=\"max-width: 424.566px;\">"
                                +"</p><p></p>";


                            }else if(html.contains("地区")){
                                String tag = info_li.text().replace("地区：", "");
                                String tag_code = PinyinUtil.paraseStringToPinyin(tag).toLowerCase();
                                tag = tag  + bt_type.getTag();
                                tag_code = tag_code  + bt_type.gettag_code();

                                map.put("tag", tag);
                                map.put("tag_code", tag_code);
                            }
                        }
                    }

                    //获取详情2 下载地址
                    Element down = doc_.select("div.play").get(0);
                    Elements down_lis = down.select("li");
                    if(!CollectionUtils.isEmpty(down_lis)){
                        Element lastDown = down_lis.get(down_lis.size() - 1);
                        String down_href = BT_HM + lastDown.select("a").attr("onclick");
                        down_href = down_href.replace("document.getElementById('down2').href", "").replace(" ", "").replace("=", "").replace(";", "");
                        down_href  = down_href.replace("'", "");

                        String downResult_ = HttpGetUtils.getDataResult(down_href,"gb2312");
                        Document down_doc_ = Jsoup.parse(downResult_, down_href);
                        String path = "<p>"+down_doc_.getElementById("downbox").select("a").text()+"</p>";
                        map.put("path", path);
                    }

                    //获取详情3 w960nr
                    Element info3 = doc_.select("div.w960nr").get(0);

                    //del href
                    info3.select("a").removeAttr("href").removeAttr("target");

                    String replace = info3.html().replace("河马BT电影站", "NorthPark")
                            .replace("小编", " Bot")
                            .replace("请记住我们的网址", "")
                            .replace("http://www.hemabt.com/btcontent/", "");

                    replace = replace.substring(0, replace.indexOf("<br>"));
                    String info3_html = "<h2>《"+map.get("title")+"》剧情介绍</h2><hr/><p></p><p></p>"
                            +replace;

                    String desc = logo_p + info.html() +"<p></p>" + info3_html;


                    map.put("date", TimeUtils.nowDate());
                    map.put("article", desc);

                    System.err.println(JsonUtil.object2json(map));
                    list.add(map);

            }
        }
        return list;
    }

    /**
     * 爬取1页的电影图书资源
     */
    public static List<Map<String, String>> retMovies(Integer index, String rettype) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = Lists.newArrayList();
        HashMap<String, String> map = null;
        try {

            log.info("page=============================" + index + "============================页");
            //String url = "http://www.vip588660.cm/page/"+index+"/";
//        		String url = "http://www.vip588660.com/category/movie/page/"+index+"/";
//	        	String url = "http://www.vip588660.com/category/dianshiju/page/"+index+"/";
//	        	String url = "http://www.vip588660.com/category/dongman/page/"+index+"/";

            String url = rettype + index + "/";

            final String dataResult = HttpGetUtils.getDataResult(url);

            System.out.println(dataResult);
            Document doc = Jsoup.parse(dataResult, url);
            Element ul = doc.select("div[class=bt_img mi_ne_kd mrb]").get(0);
            Elements lis = ul.select("li");
            if (!lis.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    Element li = lis.get(i);

                    //<h3 class="dytit"><a target="_blank" href="http://m.orisi.cn/41183.html">追捕者</a></h3>

                    String title = li.select("h3.dytit").get(0).text();

                    String ret_code = NorthParkCryptUtils.md5Encrypt(title);

                    String path = "";

                    String date = TimeUtils.nowDate();


                    //判断code在系统不存在再去处理后面的事

//                    MoviesManager moviesManager = (MoviesManager) SpringContextUtils.getBean("MoviesManager");
//                    int flag = moviesManager.countHql(" where o.ret_code= '" + ret_code + "' ");

//                    if (flag <= 0) {

                    map = new HashMap<String, String>();
                    StringBuilder sb_brief = new StringBuilder();

//                        //拼接brief
//                        String logo_url = li.select("img").get(0).attr("src");
//
//                        //-------------开始--------------------------------
//
//                        HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(logo_url, getLocalFolderByOSMovies(), TimeUtils.nowdate());
//
//                        String rs = QiniuUtils.getInstance.upload(map22.get("localpath"), "movies/" + TimeUtils.nowdate() + "/" + map22.get("key"));
//
//                        sb_brief.append("<img src=\"").append(rs).append("\" />");
//                        //-------------结束--------------------------------
//
//                        String brief_content = li.select("p").get(0).html();
//
//                        sb_brief.append(brief_content);


                    //获取正文内容

                    String a_url = li.select("a").get(0).attr("href");


                    String desc = "";


                    //休眠
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auo-generated catch block
                        e.printStackTrace();
                    }

                    String dataResult_ = HttpGetUtils.getDataResult(a_url);

                    Document doc_ = Jsoup.parse(dataResult_, a_url);
                    //yp_context
                    //dyxingq
                    Element info = doc_.select("div.dyxingq").get(0);

                    Element detail = doc_.select("div.yp_context").get(0);


                    //========================解析路径start======================================
                    //删除打赏和微信二维码信息

                    StringBuilder sb = new StringBuilder();

                    //处理h2
                    Elements h2 = detail.select("h2");


                    if (!CollectionUtils.isEmpty(h2)) {
                        for (Iterator iterator = h2.iterator(); iterator.hasNext(); ) {
                            Element link = (Element) iterator.next();
                            //把iterater里面的元素连接提取到path中
                            handleLink(sb, link, "h2");
                        }


                    }

                    //处理a连接，就去a连接查找下载地址，删除后，设置到path
                    Elements links = detail.select("a");
                    if (!CollectionUtils.isEmpty(links)) {
                        for (Iterator iterator = links.iterator(); iterator.hasNext(); ) {
                            Element link = (Element) iterator.next();
                            //把iterater里面的元素连接提取到path中
                            handleLink(sb, link, "a");

                        }
                    }


                    //处理p中的连接，就去p磁力链查找下载地址，删除后，设置到path
                    Elements ps = detail.select("p");
                    if (!CollectionUtils.isEmpty(ps)) {
                        for (Iterator iterator = ps.iterator(); iterator.hasNext(); ) {
                            Element link = (Element) iterator.next();
                            //把iterater里面的元素连接提取到path中
                            handleLink(sb, link, "p");
                        }
                    }


                    path = sb.toString();

                    //========================解析路径======================================


                    //处理图片上传和格式化
                    handleMoviePic(title, date, info);

                    handleMoviePic(title, date, detail);

                    StringBuilder sb_tag = new StringBuilder();
                    //处理所有A链接
                    handelAlinkHrefOrGenTag(info, sb_tag);
                    //处理所有A链接
                    handelAlinkHrefOrGenTag(detail, sb_tag);

                    //美化图库的样式
                    detail.select("div[id=gallery-1]").attr("class", "row");
                    detail.select("div[id=gallery-1]").select("a").addClass(" col-xs-12 col-sm-6 margin-b20 ");


                    String tag = "";
                    String sb_tag_str = sb_tag.toString();
                    if (sb_tag_str.endsWith(",")) {
                        tag = sb_tag_str.substring(0, sb_tag_str.length() - 1);
                        System.out.println(tag);
                    }
                    String tag_code = "";
                    try {

                        tag_code = PinyinUtil.paraseStringToPinyin(tag).toLowerCase();
                    } catch (Exception e) {

                        tag_code = "";
                    }


                    desc = "#电影简介\n";

//                              desc +=desc+preText+markText;


                    //删除播放器样式
                    try {

                        detail.select("div.MinePlayer").remove();
                        detail.select("link").remove();
                        detail.select("div.MineBottomList").remove();
                        detail.select("script").remove();
                        detail.select("div.uc-rating").remove();
                        detail.select("div.uc-favorite-2").remove();
                        detail.getElementById("sociables").remove();
                    } catch (Exception e) {

                        log.error("del ex--->{}", e);
                    }
                    desc += info.html();

                    desc += detail.html();


                    log.info("title==============>" + title);
                    log.info("a_url==============>" + a_url);
                    log.info("date==============>" + date);
                    log.info("tag==============>" + tag);
                    log.info("tag_code==============>" + tag_code);
                    log.info("desc==============>" + desc);

                    map.put("title", title);
                    map.put("a_url", a_url);
                    map.put("date", date);
                    map.put("article", desc);
                    map.put("ret_code", ret_code);
                    map.put("tag", tag);
                    map.put("tag_code", tag_code);
                    map.put("path", path);
                    list.add(map);
                }

            }
//            }

//                log.info(title+"\t\r"+a_url+"\t\r"+"\t\r"+brief+"\t\r"+article+"\t\r"+date+"-----------------");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }

    /**
     * 爬取1页的人人电影资源
     */
    public static List<Map<String, String>> retRRMovies(Integer index, String rettype) {
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
//                    测试第一条数据
//                    if(i!=8){
//                        continue;
//                    }

                    Element li = lis.get(i);

                    //标题
                    String title = li.select("div.pure-u-19-24 > div > h2 >a").get(0).attr("title");

                    String ret_code = NorthParkCryptUtils.md5Encrypt(title);

                    String path = "";

                    //日期
                    String date = "";
                    try {
                        date = li.select("div.pure-u-19-24 > div > div.tags").get(0).ownText();
                    }catch (Exception e){
                        date =  TimeUtils.nowDate();
                    }




                    //判断code在系统不存在再去处理后面的事
                    map = new HashMap<String, String>();
                    StringBuilder sb_brief = new StringBuilder();

                    //获取正文内容

                    String a_url = BC_Constant.RET_RR_BASE + li.select("a").get(0).attr("href");


                    String desc = "";


                    //休眠
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auo-generated catch block
                        e.printStackTrace();
                    }

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
                                handleLink(sb, link, "div");
                            }
                        }
                    }

                    //处理完div后 处理样式有问题的span
                    Elements spans = detail.select("div.movie-txt > span");
                    if (!CollectionUtils.isEmpty(spans)) {
                        for (Iterator iterator = spans.iterator(); iterator.hasNext(); ) {
                            Element link = (Element) iterator.next();
                            //把iterater里面的元素连接提取到path中
                            handleLink(sb, link, "span");
                        }
                    }


                    path = sb.toString();

                    //========================解析路径======================================


                    //处理图片上传和格式化

                    handleMoviePicMinio(title, date, detail);

                    StringBuilder sb_tag = new StringBuilder();

                    //处理所有A链接 | 根据div生成电影标签
                    handelAlinkHrefOrGenTag(detail, sb_tag);

                    //处理图片地址替换
                    Elements imgs = detail.select("div.movie-txt > img");
                    if (!CollectionUtils.isEmpty(imgs)) {
                        for (Iterator iterator = imgs.iterator(); iterator.hasNext(); ) {
                            Element img = (Element) iterator.next();
                            if(img.attr("src").equals(BC_Constant.ignore_pic_list.get(0))){
                                img.attr("src",BC_Constant.np_thunder_down);
                            }else if(img.attr("src").equals(BC_Constant.ignore_pic_list.get(1))){
                                img.attr("src",BC_Constant.np_cloud_down);
                            }
                        }
                    }

                    //美化图库的样式

                    //生成标签 类型:
                    String tag = "";
                    String sb_tag_str = sb_tag.toString();
                    if (sb_tag_str.endsWith(",")) {
                        tag = sb_tag_str.substring(0, sb_tag_str.length() - 1);
                        System.out.println(tag);
                    }
                    String tag_code = "";
                    try {

                        tag_code = PinyinUtil.paraseStringToPinyin(tag).toLowerCase();
                    } catch (Exception e) {

                        tag_code = "";
                    }


                    desc = "#电影简介\n";

//                              desc +=desc+preText+markText;


                    desc += detail.html();


                    //path 静态路径替换

                    path = path.replace(BC_Constant.ignore_pic_list.get(0), BC_Constant.np_thunder_down).replace(BC_Constant.ignore_pic_list.get(1), BC_Constant.np_cloud_down);


                    log.info("title==============>" + title);
                    log.info("a_url==============>" + a_url);
                    log.info("date==============>" + date);
                    log.info("tag==============>" + tag);
                    log.info("tag_code==============>" + tag_code);
                    log.info("desc==============>" + desc);

                    map.put("title", title);
                    map.put("a_url", a_url);
                    map.put("date", date);
                    map.put("article", desc);
                    map.put("ret_code", ret_code);
                    map.put("tag", tag);
                    map.put("tag_code", tag_code);
                    map.put("path", path);
                    System.err.println("path--->"+path);
                    list.add(map);
                }

            }
//            }

//                log.info(title+"\t\r"+a_url+"\t\r"+"\t\r"+brief+"\t\r"+article+"\t\r"+date+"-----------------");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }

        return list;
    }

    /**
     * 处理所有A链接 | 根据div生成电影标签
     * @param info
     * @param sb_tag
     */
    private static void handelAlinkHrefOrGenTag(Element info, StringBuilder sb_tag) {
        Elements alinks = info.select("a");
        for (Element alink : alinks) {
            //获取标签
            if (alink.attr("rel").equals("tag")) {
                sb_tag.append(alink.text()).append(",");
            }
            if (!alink.attr("href").contains("douban")) {

                alink.removeAttr("href");
            }

            //
        }

        Elements divs = info.select("div");
        for (Element div : divs) {
            //获取标签
            try {
                String span = div.select("span").get(0).text();
                System.err.println(span);
                if (span.contains("类型: ")) {
                    String[] split = span.replace("类型: ", "").split("/");
                    for (int i = 0; i < split.length; i++) {
                        sb_tag.append(split[i].trim()).append(",");
                    }

                }
            }catch (Exception e){
                continue;
            }


        }
    }

    /**
     * 处理电影的图片上传和格式化
     *
     * @param title
     * @param date
     * @param element
     */
    private static void handleMoviePic(String title, String date, Element element) {
        Elements imgs = element.select("img");
        for (int j = 0; j < imgs.size(); j++) {
            try {
                String weburl = imgs.get(j).attr("src");
                if(!BC_Constant.ignore_pic_list.contains(weburl)){
                    //web图片上传到七牛

                    //-------------开始--------------------------------

                    HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOSMovies(), date);

                    String rs = QiniuUtils.getInstance().upload(map22.get("localpath"), "movies/" + date + "/" + map22.get("key"));

                    //-------------结束--------------------------------

                    imgs.get(j).attr("src", rs);
                    imgs.get(j).removeAttr("srcset");
                    imgs.get(j).removeAttr("sizes");
                    imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录
                    imgs.get(j).addClass("col-xs-12 col-sm-6 margin-b20");
                }

            } catch (Exception e) {

                log.error("ret movies pic exception===>" + e.toString());
                continue;
            }

        }
    }

    /**
     * minio处理电影的图片上传和格式化
     *
     * @param title
     * @param date
     * @param element
     */
    private static void handleMoviePicMinio(String title, String date, Element element) {
        Elements imgs = element.select("img");
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        for (int j = 0; j < imgs.size(); j++) {
            try {
                String weburl = imgs.get(j).attr("src");
                if(!BC_Constant.ignore_pic_list.contains(weburl)) {
                    // 获取图片后缀
                    String extension = getImageExtension(weburl);
                    // 生成新文件名
                    String newFileName = timestampFormat.format(new Date()) + extension;
                    // Minio的目标路径
                    String minioPath = date + "/" + newFileName;

                    // 上传图片到Minio
                    String minioUrl = uploadWebImageToMinio(weburl, "movies", minioPath);

                    // 更新图片元素属性
                    imgs.get(j).attr("src", minioUrl);
                    imgs.get(j).removeAttr("srcset");
                    imgs.get(j).removeAttr("sizes");
                    imgs.get(j).attr("alt", title);
                    imgs.get(j).addClass("col-xs-12 col-sm-6 margin-b20");
                }
            } catch (Exception e) {
                log.error("处理图片异常: " + e.toString(), e);
                continue;
            }
        }
    }

    /**
     * 获取图片后缀
     */
    private static String getImageExtension(String imageUrl) {
        String extension = ".jpg"; // 默认后缀
        if (StringUtils.isNotEmpty(imageUrl)) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            if (fileName.contains("?")) {
                fileName = fileName.substring(0, fileName.indexOf("?"));
            }
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = fileName.substring(dotIndex);
            }
        }
        return extension.toLowerCase();
    }

    /**
     * 上传网络图片到Minio
     */
    private static String uploadWebImageToMinio(String imageUrl, String bucketName, String objectName) throws Exception {
        // 下载图片
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (InputStream inputStream = conn.getInputStream()) {
            // 压缩图片
            ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .scale(1f)
                    .outputQuality(0.5f)
                    .toOutputStream(compressedImageStream);

            // 将压缩后的图片上传到Minio
            try (ByteArrayInputStream uploadStream = new ByteArrayInputStream(compressedImageStream.toByteArray())) {
                MinioUtils.putObject(
                        bucketName,
                        objectName,
                        uploadStream,
                        compressedImageStream.size(),
                        "image/" + objectName.substring(objectName.lastIndexOf(".") + 1)
                );
            }
        }

        // 返回Minio URL
        return EnvCfgUtil.getValByCfgName("MINIO_API_") + "/" + bucketName + "/" + objectName;
    }

    /**
     * 把iterater里面的元素连接提取到path中
     *
     * @param sb
     * @param link
     */
    public static void handleLink(StringBuilder sb, Element link, String type) {

        String linkHtml = link.outerHtml();
        if (type.equals("a")) {
            if (linkHtml.contains("百度网盘") || linkHtml.contains("网盘")
                    || linkHtml.contains("迅雷") || linkHtml.contains("密码")
                    || linkHtml.contains("下载") || linkHtml.contains("视频")
                    || linkHtml.contains("百度云") || linkHtml.contains("链接")
                    || linkHtml.contains("季") || linkHtml.contains("pan.baidu.com")
                    || linkHtml.contains("download") || linkHtml.contains("在线地址") || linkHtml.contains("ed2k") || linkHtml.contains("magnet")
            ) {

                sb.append(linkHtml);
                link.remove();

            }
        } else if (type.equals("p")) {
            if (linkHtml.contains("百度网盘") || linkHtml.contains("网盘")
                    || linkHtml.contains("迅雷") || linkHtml.contains("密码")
                    || linkHtml.contains("下载") || linkHtml.contains("视频")
                    || linkHtml.contains("百度云")
                    || linkHtml.contains("pan.baidu.com")
                    || linkHtml.contains("download") || linkHtml.contains("在线地址") || linkHtml.contains("ed2k") || linkHtml.contains("magnet")
            ) {

                sb.append(linkHtml);
                link.remove();

            }
        } else if (type.equals("h2")) {
            if (linkHtml.contains("百度网盘") || linkHtml.contains("网盘")
                    || linkHtml.contains("迅雷") || linkHtml.contains("密码")
                    || linkHtml.contains("下载") || linkHtml.contains("视频")
                    || linkHtml.contains("百度云")
                    || linkHtml.contains("季") || linkHtml.contains("pan.baidu.com")
                    || linkHtml.contains("download") || linkHtml.contains("在线地址") || linkHtml.contains("ed2k") || linkHtml.contains("magnet")
            ) {

                sb.append(linkHtml);
                link.remove();

            }
        }else if (type.equals("div")) {
            String div_cont = link.html();
            System.err.println("html--->"+div_cont);
            if (div_cont.contains("百度网盘") || div_cont.contains("迅雷云盘") || div_cont.contains("阿里网盘")|| div_cont.contains("提取码")
                    || div_cont.contains("迅雷下载") || div_cont.contains("云盘下载") || linkHtml.contains("ed2k") || linkHtml.contains("magnet")
                    || div_cont.contains("下载") || div_cont.contains(BC_Constant.ignore_pic_list.get(0)) //迅雷下载图标
                    || div_cont.contains("网盘")
            ) {

                sb.append(link.outerHtml());
                link.remove();

            }
        }else if (type.equals("span")) {
            String div_cont = link.html();
            System.err.println("html--->"+div_cont);
            if (div_cont.contains("百度网盘") || div_cont.contains("迅雷云盘") || div_cont.contains("阿里网盘")|| div_cont.contains("提取码")
                    || div_cont.contains("迅雷下载") || div_cont.contains("云盘下载") || linkHtml.contains("ed2k") || linkHtml.contains("magnet")
                    || div_cont.contains("下载") || div_cont.contains("网盘")
            ) {

                //包含下载 && 有a链接
                if( link.select("a").size()>0 ){
                    sb.append(link.outerHtml());
                    link.remove();

                    //只有迅雷下载图标 && 没有a链接
                }else  if( div_cont.contains(BC_Constant.ignore_pic_list.get(0) )&&link.select("a").size()==0 ){
                    link.select("img").remove();

                }

            }
        }


    }


    /**
     * 爬取1页的诗词
     */
    public static List<Map<String, String>> retPoem(Integer index) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = Lists.newArrayList();
        try {

            log.info("page=============================" + index + "============================页");
            //String url = "http://www.vip588660.cm/page/"+index+"/";
//        		String url = "http://www.vip588660.com/category/movie/page/"+index+"/";
//	        	String url = "http://www.vip588660.com/category/dianshiju/page/"+index+"/";
//	        	String url = "http://www.haoshici.com/"+index+"_E59490_0_0_0_0.html";
//        		String url = "http://www.haoshici.com/"+index+"_E5AE8B_0_0_0_0.html";
            String url = "http://www.haoshici.com/" + index + "_E58583_0_0_0_0.html";


            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
                    .get();
            Elements lis = doc.select("li[class=lst ");
            if (!lis.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element li = lis.get(i);

                    /**
                     *
                     <li class="lst">
                     <a href="Lishangyin4.html" target="_top" title="七绝">
                     <span class="bigger">
                     《初入武夷》
                     </span>
                     </a>
                     <span class="_11px">
                     （唐代李商隐作品）
                     </span>
                     <br>
                     <a href="Lishangyin4.html" target="_top" title="">
                     未到名山梦已新，千峰拔地玉嶙峋。幔亭一夜风吹雨，似与游人洗俗尘。
                     </a>
                     </li>
                     *
                     *
                     * */
                    //获取相关信息
                    Elements a_s = li.select("a");

                    //诗词类型【五言、七言、绝句】
                    String types = a_s.get(0).attr("title");

                    //诗词名
                    String title = a_s.get(0).select("span").get(0).text();


                    //作者
                    String author = li.select("span[class=_11px]").get(0).text();

                    author = author.replaceAll("唐代", "").replaceAll("作品", "");


                    //内容
                    String content = a_s.get(1).text();

                    //详情url
                    String detail_url = "http://www.haoshici.com/" + a_s.get(1).attr("href");


                    //生成代码
                    String ret_code = NorthParkCryptUtils.md5Encrypt(title + types + author + detail_url);


                    log.info("title==============>" + title);
                    log.info("detail_url==============>" + detail_url);
                    log.info("author==============>" + author);
                    log.info("content==============>" + content);
                    log.info("types==============>" + types);


                    //图片赏析
                    String pic_poem = "";

                    //文字赏析
                    String enjoys = "";
                    //诗词内容
                    String content1 = "";


                    //休眠
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auo-generated catch block
                        e.printStackTrace();
                    }

                    Document doc_ = Jsoup.connect(detail_url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .timeout(1000 * 5) //it's in milliseconds, so this means 5 seconds.
                            .get();
                    Elements article_alls = doc_.select("div[class=middle]");
                    if (!article_alls.isEmpty()) {

                        Elements imgs = article_alls.get(0).select("img");
                        String date = TimeUtils.nowDate();
                        for (int j = 0; j < imgs.size(); j++) {
                            try {
                                String weburl = imgs.get(j).attr("src");
                                log.info(weburl);
                                //web图片上传到七牛
                                if (StringUtils.isNotEmpty(weburl) && !weburl.contains("verify.php")) {
                                    //-------------开始--------------------------------

                                    if (!weburl.startsWith("http://www.haoshici.com")) {
                                        weburl = "http://www.haoshici.com/" + weburl;
                                    }

                                    HashMap<String, String> map22 = HTMLParserUtil.webPic2Disk(weburl, getLocalFolderByOSMovies(), date);

                                    String rs = QiniuUtils.getInstance().upload(map22.get("localpath"), "movies/" + date + "/" + map22.get("key"));

                                    //-------------结束--------------------------------

                                    imgs.get(j).attr("src", rs);

                                    imgs.get(j).attr("alt", title);//给图像添加元素标记，便于搜索引擎的记录

                                    pic_poem = rs;
                                }


                            } catch (Exception e) {

                                log.error("ret movies pic exception===>" + e.toString());
                                continue;
                            }

                        }

                        //诗词赏析
                        enjoys = article_alls.get(0).select("p[class=explanation]").html();

                        log.info("enjoys==============>" + enjoys);

                        //诗词内容
                        content1 = article_alls.get(0).select("p[class=poetry]").get(0).html();
                        log.info("content1==============>" + content1);

                    }


                    map.put("title", title);
                    map.put("detail_url", detail_url);
                    map.put("author", author);
                    map.put("content", content);
                    map.put("types", types);
                    map.put("enjoys", enjoys);
                    map.put("pic_poem", pic_poem);
                    map.put("ret_code", ret_code);
                    map.put("content1", content1);

                    list.add(map);
                }
            }

//                log.info(title+"\t\r"+a_url+"\t\r"+"\t\r"+brief+"\t\r"+article+"\t\r"+date+"-----------------");

        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
            ;
        }

        return list;
    }


    /**
     *	=====================================tools开始================================================================
     */


    /**
     * 读取网络图片到硬盘
     */
    public static void readPic2Disk() {
        try {
            //retMeizitu();

            Document doc = Jsoup.connect("http://orenogazoo.tumblr.com/")
                    .get();
            //src
            Elements pics = doc.select("img");
            for (Element p : pics) {
                try {
                    URL url = new URL(p.attr("src"));
                    URLConnection uc = url.openConnection();
                    InputStream is = uc.getInputStream();

                    String name = p.attr("src").substring(p.attr("src").lastIndexOf("/") + 1, p.attr("src").length());

                    String path = "/Users/zhangyang/Pictures/";

                    String date = TimeUtils.nowDate() + "/";
                    path = path + date;

                    File filepath = new File(path);
                    if (!filepath.exists() && !filepath.isDirectory()) {
                        filepath.mkdirs();
                    }
                    if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".ico") || name.endsWith(".gif")) {
                        FileOutputStream out = new FileOutputStream(path + name);
                        log.info("写入中..." + path + name);
                        int i1 = 0;
                        while ((i1 = is.read()) != -1) {
                            out.write(i1);
                        }

                        out.close();
                    }
                    is.close();
                } catch (Exception e) {
                    log.error("HTMLPARSERutils------->", e);
                    ;
                    continue;
                }
            }
        } catch (IOException e) {

            log.error("HTMLPARSERutils------->", e);
            ;
        }
    }

    /**
     * 读取网络图片到硬盘
     */
    public static HashMap<String, String> webPic2Disk(String weburl, String localpath, String date) {
        HashMap<String, String> map = new HashMap<String, String>();
        String path = "";
        String name = "";
        try {

//            byte[] img_byte = HttpGetUtils.getImg(weburl);

            //拼接名字
            if (StringUtils.isNotEmpty(weburl) && weburl.contains("/")) {
                name = weburl.substring(weburl.lastIndexOf("/") + 1, weburl.length());
            }
            if (name.contains("?")) {
                name = name.substring(0, name.indexOf("?"));

            }

            if (StringUtils.isNotEmpty(name)) {
                path = localpath;//"/Users/zhangyang/Pictures/";

                //拼接路径
                path = path +"/"+ date+"/";

                //写入文件
                FileUtils.downloadUrlFile2Local(weburl,path ,path + name);


                //图片压缩处理 BRUCE TIPS！
                synchronized (Thumbnails.class){
                    Thumbnails.of(path + name)
                            .scale(1f)
                            .outputQuality(0.5f)
                            .toFile(path + name);
                }

            }


            map.put("key", name);
            map.put("localpath", path + name);
            try {
                map.put("trimpath", (path + name).replace("E:\\bruce\\", ""));
                map.put("trimPan", (path + name).replace("E:\\", "")).replace("D:\\","/bruce/");
            }catch (Exception ig){

            }


        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
        }
        return map;
    }

    /**
     * 解析七牛URL为markdown格式
     *
     * @return
     */
    public static List<String> url2markdown() {
        List<String> urilist = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        try {
            File in = new File("C:\\Users\\bruce\\Desktop\\111\\aaaa.html");

            Document doc = Jsoup.parse(in, "UTF-8", "");
            Elements notes = doc.select("button[class=test_pic]");
            for (Element p : notes) {

                String links = p.attr("cval");
                urilist.add(links);

            }

            HashSet<String> v = new HashSet<String>();
            v.addAll(urilist);

            list.addAll(v);
            for (String s : list) {
                log.info(s);
            }

        } catch (IOException e) {

            log.error("HTMLPARSERutils------->", e);
            ;
        }
        return list;
    }

    /**
     * 生成妹子图从N张动漫
     *
     * @return
     */
    public static List<String> gegeMEIZITU() {
        List<String> meizi = Lists.newArrayList();

        for (int i = 1; i <= 18; i++) {
            String img = "/static/img/eq/tumblr_o" + i + ".png";
            meizi.add(img);
        }
        return meizi;
    }

    /**
     * @return
     * @desc 随机取出一个数【size 为  10 ，取得类似0-9的区间数】
     */
    public static Integer getRandomOne(List<?> list) {

        Random ramdom = new Random();
        int number = -1;
        int max = list.size();

        //size 为  10 ，取得类似0-9的区间数
        number = Math.abs(ramdom.nextInt() % max);

        return number;

    }

    /**
     * 根据操作系统获取本地存储文件夹
     *
     * @return
     */
    public static String getLocalFolderByOS() {

        return BC_Constant.getPicStartByOs() + "/" + RetType.Soft.getTypeName();

    }

    /**
     * 根据操作系统获取本地存储文件夹
     *
     * @return
     */
    public static String getLocalFolderByOS(String retType) {

        return BC_Constant.getPicStartByOs() + "/" +retType;

    }

    /**
     * 根据操作系统获取本地存储文件夹Movies
     *
     * @return
     */
    public static String getLocalFolderByOSMovies() {

        return BC_Constant.getPicStartByOs() + "/" + RetType.Movie.getTypeName();

    }


    /**
     * //妹子图获取
     * // http://huaban.com/from/meizitu.com/
     *
     * @throws IOException
     */
    public static List<String> retMeizitu() throws IOException {
        List<String> list = Lists.newArrayList();
        try {
            Document doc = Jsoup.connect("http://huaban.com/from/meizitu.com/?inepv8xm&max=695360534&limit=300&wfl=1").get();
            //src
            Elements pics = doc.select("img");
//                int index = 0;
            for (Element p : pics) {
//                    index++;
                list.add(p.attr("src"));
                //log.info(index+ p.attr("src"));
            }
            list.remove(list.size() - 1);
            //session.setAttribute("meizutu", list);
        } catch (Exception e) {
            log.error("HTMLPARSERutils------->", e);
            ;
        }
        return list;
    }


    public static int geneview_num() {
        int max = 59000;
        int min = 1000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        log.info("" + s);
        return s;
    }


    /**
     * 字符串超长截取
     *
     * @param s
     * @param length
     * @return
     * @throws Exception
     */
    public static String CutString(String s, int length) {
        String rs = "";
        try {

            byte[] bytes = s.getBytes("Unicode");
            int n = 0; // 表示当前的字节数
            int i = 2; // 要截取的字节数，从第3个字节开始
            for (; i < bytes.length && n < length; i++) {
                // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (i % 2 == 1) {
                    n++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[i] != 0) {
                        n++;
                    }
                }
            }
            // 如果i为奇数时，处理成偶数
            if (i % 2 == 1) {
                // 该UCS2字符是汉字时，去掉这个截一半的汉字
                if (bytes[i - 1] != 0)
                    i = i - 1;
                    // 该UCS2字符是字母或数字，则保留该字符
                else
                    i = i + 1;
            }

            rs = new String(bytes, 0, i, "Unicode");
            if (!rs.equals(s)) {
                rs = rs + "...";
            }
        } catch (Exception e) {

            rs = s;

        }
        return rs;
    }


    /**
     * 去除所有的\n\t\r
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String temp = str.replace("n", "").replace("r", "").replace("t", "").replaceAll("\\\\", "");
        return temp;
    }

    /**
     * =====================================tools结束================================================================
     */

    public static void main(String[] args) {
        try {
            String pic = uploadWebImageToMinio("https://p0.pipi.cn/mmdb/54ecde022ffddd7df5e19bcd40fe1e3b5a182.jpg?imageView2/1/w/464/h/677", "pic", "54ecde022ffddd7df5e19bcd40fe1e3b5a182.jpg");

            System.err.println(pic);
        } catch (Exception e) {

            log.error("HTMLPARSERutils------->", e);
            ;
        }
    }


}
