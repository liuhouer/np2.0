package cn.northpark.np5.controller;

import cn.northpark.np5.model.Movies;
import cn.northpark.np5.model.Tags;
import cn.northpark.np5.service.MoviesService;
import cn.northpark.np5.service.TagsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

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
        session.setAttribute("tabs", "movies");

        Page<Movies> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Movies> query = new QueryWrapper<>();
        query.isNull("displayed");

        if (StringUtils.isNotBlank(keyword)) {
            query.and(q -> q.like("movie_name", keyword.trim())
                    .or()
                    .like("movie_name", keyword.trim().replace(" ", "")));
        }

        if ("hot".equals(orderBy)) {
            query.orderByDesc("hot_index");
        } else if ("latest".equals(orderBy)) {
            query.orderByDesc("id");
        } else {
            query.orderByDesc("add_time").orderByDesc("id");
        }

        moviesService.page(page, query);
        List<Movies> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("orderBy", orderBy);

        loadSidebar(model);

        return "movies/list";
    }

    @RequestMapping("/tag/{tagCode}")
    public String tagSearch(Model model, @PathVariable("tagCode") String tagCode) {
        return tagSearchPage(model, tagCode, 1);
    }

    @RequestMapping("/tag/{tagCode}/page/{pageNo}")
    public String tagSearchPage(Model model, @PathVariable("tagCode") String tagCode, @PathVariable("pageNo") Integer pageNo) {
        Page<Movies> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Movies> query = new QueryWrapper<>();
        query.isNull("displayed").like("tag_code", tagCode);
        query.orderByDesc("hot_index").orderByDesc("id");

        moviesService.page(page, query);
        List<Movies> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("selTag", tagCode);

        loadSidebar(model);

        return "movies/list";
    }

    @RequestMapping("/date/{date}")
    public String dateSearch(Model model, @PathVariable("date") String date) {
        return dateSearchPage(model, date, 1);
    }

    @RequestMapping("/date/{date}/page/{pageNo}")
    public String dateSearchPage(Model model, @PathVariable("date") String date, @PathVariable("pageNo") Integer pageNo) {
        Page<Movies> page = new Page<>(pageNo, PAGE_SIZE);
        QueryWrapper<Movies> query = new QueryWrapper<>();
        query.isNull("displayed").eq("add_time", date);
        query.orderByDesc("hot_index").orderByDesc("id");

        moviesService.page(page, query);
        List<Movies> list = page.getRecords();
        handleTags(list);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getPages());
        model.addAttribute("selDate", date);

        loadSidebar(model);

        return "movies/list";
    }

    @RequestMapping("/post-{id}.html")
    public String postDetail(Model model, @PathVariable("id") Integer id) {
        Movies item = moviesService.getById(id);
        if (item != null && !"N".equals(item.getDisplayed())) {
            if (StringUtils.isNotBlank(item.getMovieDesc())) {
                model.addAttribute("movieDescText", Jsoup.parse(item.getMovieDesc()).text());
            }
            handleTags(Collections.singletonList(item));
            model.addAttribute("model", item);
        }
        return "movies/detail";
    }

    private void handleTags(List<Movies> list) {
        for (Movies m : list) {
            // 解析图片地址
            if (StringUtils.isNotBlank(m.getMovieDesc())) {
                org.jsoup.nodes.Document doc = Jsoup.parse(m.getMovieDesc());
                org.jsoup.nodes.Element img = doc.selectFirst("img");
                if (img != null) {
                    m.setImgUrl(img.attr("src"));
                }
            }

            if (StringUtils.isNotBlank(m.getTag()) && StringUtils.isNotBlank(m.getTagCode())) {
                String[] tags = m.getTag().split(",");
                String[] codes = m.getTagCode().split(",");
                if (tags.length == codes.length) {
                    List<Map<String, String>> tagList = new ArrayList<>();
                    for (int i = 0; i < tags.length; i++) {
                        Map<String, String> tagMap = new HashMap<>();
                        tagMap.put("tag", tags[i]);
                        tagMap.put("tag_code", codes[i]);
                        tagList.add(tagMap);
                    }
                    m.setTagList(tagList);
                }
            }
        }
    }

    private void loadSidebar(Model model) {
        // 侧边栏随机获取电影
        QueryWrapper<Movies> movieQuery = new QueryWrapper<>();
        movieQuery.isNull("displayed").last("order by rand() limit 20");
        List<Movies> hotMovies = moviesService.list(movieQuery);
        model.addAttribute("moviesHotList", hotMovies);

        // 侧边栏标签
        QueryWrapper<Tags> tagQuery = new QueryWrapper<>();
        tagQuery.eq("tag_type", "1");
        List<Tags> tags = tagsService.list(tagQuery);
        model.addAttribute("moviesTags", tags);
    }
}