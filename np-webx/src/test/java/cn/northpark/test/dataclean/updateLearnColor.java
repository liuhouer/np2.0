package cn.northpark.test.dataclean;

import cn.northpark.utils.NPQueryRunner;
import cn.northpark.utils.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * 更新学习颜色
 */
@Slf4j
public class updateLearnColor {

    public static void main(String[] args) {
        String sql = "select id,brief from bc_knowledge ";
        List<Map<String, Object>> maps = NPQueryRunner.query(sql, new MapListHandler());
        System.err.println(maps);
        //拿到集合并行更新
        maps.stream().parallel().unordered().forEach(item->{
            String id = item.get("id").toString();
            String brief = item.get("brief").toString();

            Document parsed = Jsoup.parse(brief);
            parsed.select("blockquote").removeAttr("style");
            String up_sql = "update bc_knowledge set brief = '"+parsed.html()+"' where id = "+id;
            NPQueryRunner.update(up_sql);
        });

    }

}
