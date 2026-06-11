package cn.northpark.np5.controller;

import cn.northpark.np5.model.Soft;
import cn.northpark.np5.model.Tags;
import cn.northpark.np5.service.SoftService;
import cn.northpark.np5.service.TagsService;
import cn.northpark.np5.utils.MinioUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/soft")
@Slf4j
public class SoftController {

    @Autowired
    private SoftService softService;

    private static final int PAGE_SIZE = 15;

    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(value = "keyword", required = false) String keyword,
                        HttpSession session) {
        return listPage(model, 1, keyword, session);
    }

    @RequestMapping("/mac")
    public String list(Model model,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       HttpSession session) {
        return listPage(model, 1, keyword, session);
    }

    @RequestMapping("/mac/page/{pageNo}")
    public String listPage(Model model,
                           @PathVariable("pageNo") Integer pageNo,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           HttpSession session) {
        session.setAttribute("tabs", "soft");

        String decodedKeyword = null;
        if (StringUtils.isNotBlank(keyword)) {
            try {
                decodedKeyword = URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("decode error", e);
            }
        }

        Page<Soft> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Soft> query = new QueryWrapper<>();
        query.isNull("displayed");

        if (StringUtils.isNotBlank(decodedKeyword)) {
            String cleanKeyword = decodedKeyword.trim();
            query.and(q -> q.like("title", cleanKeyword)
                    .or()
                    .like("title", cleanKeyword.replace(" ", "")));
        }

        query.orderByDesc("hot_index").orderByDesc("post_date").orderByDesc("id");

        softService.page(page, query);
        List<Soft> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("keyword", decodedKeyword);

        loadSidebar(model);

        return "soft/list";
    }

    @RequestMapping("/tag/{tagCode}")
    public String tagSearch(Model model, @PathVariable("tagCode") String tagCode, HttpSession session) {
        return tagSearchPage(model, tagCode, 1, session);
    }

    @RequestMapping("/tag/{tagCode}/page/{pageNo}")
    public String tagSearchPage(Model model,
                               @PathVariable("tagCode") String tagCode,
                               @PathVariable("pageNo") Integer pageNo,
                               HttpSession session) {
        session.setAttribute("tabs", "soft");

        Page<Soft> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Soft> query = new QueryWrapper<>();
        query.isNull("displayed").eq("tags_code", tagCode);
        query.orderByDesc("hot_index").orderByDesc("post_date").orderByDesc("id");

        softService.page(page, query);
        List<Soft> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("selTag", tagCode);

        loadSidebar(model);

        return "soft/list";
    }

    @RequestMapping("/month/{month}")
    public String monthSearch(Model model, @PathVariable("month") String month, HttpSession session) {
        return monthSearchPage(model, month, 1, session);
    }

    @RequestMapping("/month/{month}/page/{pageNo}")
    public String monthSearchPage(Model model,
                                 @PathVariable("month") String month,
                                 @PathVariable("pageNo") Integer pageNo,
                                 HttpSession session) {
        session.setAttribute("tabs", "soft");

        Page<Soft> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Soft> query = new QueryWrapper<>();
        query.isNull("displayed").eq("month", month);
        query.orderByDesc("hot_index").orderByDesc("post_date").orderByDesc("id");

        softService.page(page, query);
        List<Soft> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("selMonth", month);

        loadSidebar(model);

        return "soft/list";
    }

    @RequestMapping("/{retCode}.html")
    public String softDetail(Model model, @PathVariable("retCode") String retCode) {
        QueryWrapper<Soft> query = new QueryWrapper<>();
        query.eq("ret_code", retCode);
        Soft item = softService.getOne(query);

        if (item != null && !"N".equals(item.getDisplayed())) {
            // BRUCETIPS! 富文本处理 -- 从minio读取
            if ("1".equals(item.getUseMinio()) && StringUtils.isNotBlank(item.getContentMinio())) {
                item.setContent(MinioUtils.readText(item.getContentMinio()));
            }

            if (StringUtils.isNotBlank(item.getBrief())) {
                model.addAttribute("softDescText", Jsoup.parse(item.getBrief()).text());
            }

            handleTags(java.util.Collections.singletonList(item));
            model.addAttribute("model", item);
        }

        return "soft/detail";
    }

    private void handleTags(List<Soft> list) {
        for (Soft s : list) {
            // 解析 brief 里的 img 标签
            if (StringUtils.isNotBlank(s.getBrief())) {
                org.jsoup.nodes.Document doc = Jsoup.parse(s.getBrief());
                org.jsoup.select.Elements imgs = doc.select("img");
                if (!imgs.isEmpty()) {
                    s.setBriefImg(imgs.get(0).attr("src"));
                }
                // 去除 HTML 标签后的纯文本，用于简要描述
                s.setBriefShow(doc.text());
            }

            if (StringUtils.isNotBlank(s.getTags()) && StringUtils.isNotBlank(s.getTagsCode())) {
                String[] tags = s.getTags().split(",");
                String[] codes = s.getTagsCode().split(",");
                if (tags.length == codes.length) {
                    List<Map<String, String>> tagList = new ArrayList<>();
                    for (int i = 0; i < tags.length; i++) {
                        Map<String, String> tagMap = new HashMap<>();
                        tagMap.put("tag", tags[i]);
                        tagMap.put("tag_code", codes[i]);
                        tagList.add(tagMap);
                    }
                    s.setTagList(tagList);
                }
            }
        }
    }

    private void loadSidebar(Model model) {
        // 侧边栏随机推荐或者热门软件
        QueryWrapper<Soft> softQuery = new QueryWrapper<>();
        softQuery.isNull("displayed").last("order by rand() limit 10");
        List<Soft> hotList = softService.list(softQuery);
        model.addAttribute("softHotList", hotList);

        // 软件标签 (根据 soft 表的标签分组)
        List<Map<String, Object>> rawTags = softService.querySqlMap("select count(tags) as num,tags,tags_code from bc_soft group by tags,tags_code order by num desc");
        List<Map<String, String>> tags = new ArrayList<>();
        if (rawTags != null) {
            for (Map<String, Object> map : rawTags) {
                String tag = (String) map.get("tags");
                String tagCode = (String) map.get("tags_code");
                if (StringUtils.isNotBlank(tag) && StringUtils.isNotBlank(tagCode)) {
                    Map<String, String> tagMap = new HashMap<>();
                    tagMap.put("tag", tag);
                    tagMap.put("tagCode", tagCode);
                    tags.add(tagMap);
                }
            }
        }
        model.addAttribute("softTags", tags);

        // 月份归档
        QueryWrapper<Soft> monthQuery = new QueryWrapper<>();
        monthQuery.select("distinct month").isNotNull("month").orderByDesc("month");
        List<Soft> months = softService.list(monthQuery);
        List<String> monthList = new ArrayList<>();
        for (Soft s : months) {
            if (StringUtils.isNotBlank(s.getMonth())) {
                monthList.add(s.getMonth());
            }
        }
        model.addAttribute("monthList", monthList);
    }
}