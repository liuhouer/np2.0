package cn.northpark.np5.controller;

import cn.northpark.np5.model.Knowledge;
import cn.northpark.np5.model.Tags;
import cn.northpark.np5.service.KnowledgeService;
import cn.northpark.np5.service.TagsService;
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
@RequestMapping("/learning")
@Slf4j
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private TagsService tagsService;

    private static final int PAGE_SIZE = 15;

    @RequestMapping("")
    public String list(Model model,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "orderBy", required = false) String orderBy,
                       HttpSession session) {
        return listPage(model, 1, keyword, orderBy, session);
    }

    @RequestMapping("/page/{pageNo}")
    public String listPage(Model model,
                           @PathVariable("pageNo") Integer pageNo,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "orderBy", required = false) String orderBy,
                           HttpSession session) {
        session.setAttribute("tabs", "learning");

        String decodedKeyword = null;
        if (StringUtils.isNotBlank(keyword)) {
            try {
                decodedKeyword = URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("decode error", e);
            }
        }

        Page<Knowledge> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Knowledge> query = new QueryWrapper<>();
        query.isNull("displayed");

        if (StringUtils.isNotBlank(decodedKeyword)) {
            String cleanKeyword = decodedKeyword.trim();
            query.and(q -> q.like("title", cleanKeyword)
                    .or()
                    .like("title", cleanKeyword.replace(" ", "")));
        }

        if ("hot".equals(orderBy)) {
            query.orderByDesc("hot_index");
        } else if ("latest".equals(orderBy)) {
            query.orderByDesc("id");
        } else {
            query.orderByDesc("post_date").orderByDesc("id");
        }

        knowledgeService.page(page, query);
        List<Knowledge> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("keyword", decodedKeyword);
        model.addAttribute("orderBy", orderBy);

        loadSidebar(model);

        return "learning/list";
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
        session.setAttribute("tabs", "learning");

        Page<Knowledge> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Knowledge> query = new QueryWrapper<>();
        query.isNull("displayed").like("tags_code", tagCode);
        query.orderByDesc("hot_index").orderByDesc("id");

        knowledgeService.page(page, query);
        List<Knowledge> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("selTag", tagCode);

        loadSidebar(model);

        return "learning/list";
    }

    @RequestMapping("/post-{id}.html")
    public String postDetail(Model model, @PathVariable("id") Integer id) {
        Knowledge item = knowledgeService.getById(id);
        if (item != null && !"N".equals(item.getDisplayed())) {
            if (StringUtils.isNotBlank(item.getContent())) {
                model.addAttribute("contentHtml", item.getContent());
                model.addAttribute("contentDescText", Jsoup.parse(item.getContent()).select("p").text());
            }
            handleTags(java.util.Collections.singletonList(item));
            model.addAttribute("model", item);
        }
        return "learning/detail";
    }

    private void handleTags(List<Knowledge> list) {
        for (Knowledge k : list) {
            if (StringUtils.isNotBlank(k.getTags()) && StringUtils.isNotBlank(k.getTagsCode())) {
                String[] tags = k.getTags().split(",");
                String[] codes = k.getTagsCode().split(",");
                if (tags.length == codes.length) {
                    List<Map<String, String>> tagList = new ArrayList<>();
                    for (int i = 0; i < tags.length; i++) {
                        Map<String, String> tagMap = new HashMap<>();
                        tagMap.put("tag", tags[i]);
                        tagMap.put("tag_code", codes[i]);
                        tagList.add(tagMap);
                    }
                    k.setTagList(tagList);
                }
            }
        }
    }

    private void loadSidebar(Model model) {
        // 侧边栏课程推荐 (bc_knowledge 中 tags_code 包含 'classhare' 的随机获取)
        QueryWrapper<Knowledge> kQuery = new QueryWrapper<>();
        kQuery.isNull("displayed")
              .like("tags_code", "classhare")
              .last("order by rand() limit 20");
        List<Knowledge> hotList = knowledgeService.list(kQuery);
        model.addAttribute("learnHotList", hotList);

        // 侧边栏标签 (tag_type = '4')
        QueryWrapper<Tags> tagQuery = new QueryWrapper<>();
        tagQuery.eq("tag_type", "4");
        List<Tags> tags = tagsService.list(tagQuery);
        model.addAttribute("learnTags", tags);
    }
}