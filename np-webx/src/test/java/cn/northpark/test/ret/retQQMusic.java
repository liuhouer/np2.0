package cn.northpark.test.ret;

import cn.northpark.utils.HttpGetUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 人人电影资源获取
 */
@Slf4j
public class retQQMusic {


    public static void main(String[] args) {
        String url = "https://y.qq.com/n/ryqq/playlist/8820405070";

        System.err.println("爬取地址+++++" + url);

        String dataResult = HttpGetUtils.getDataResult(url);

        System.out.println(dataResult);
        Document doc = Jsoup.parse(dataResult, url);
        Elements ul = doc.select("#data__info");
        for (int i = 0; i < ul.size(); i++) {
            Element element = ul.get(i);
            if(element.html().contains("播放量")){
                log.info(element.text());
                System.err.println(element.text());
            }
        }
    }


}
