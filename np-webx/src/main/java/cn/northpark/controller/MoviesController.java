
package cn.northpark.controller;

import cn.northpark.annotation.BruceOperation;
import cn.northpark.annotation.Desc;
import cn.northpark.annotation.RateLimit;
import cn.northpark.constant.BC_Constant;
import cn.northpark.exception.NorthParkException;
import cn.northpark.model.Movies;
import cn.northpark.model.NotifyRemindB;
import cn.northpark.model.Tags;
import cn.northpark.notify.NotifyEnum;
import cn.northpark.result.Result;
import cn.northpark.result.ResultEnum;
import cn.northpark.result.ResultGenerator;
import cn.northpark.service.MoviesService;
import cn.northpark.service.TagsService;
import cn.northpark.threadPool.AsyncThreadPool;
import cn.northpark.utils.*;
import cn.northpark.utils.encrypt.MD5Utils;
import cn.northpark.utils.safe.WAQ;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/*
 *@author bruce
 *
 **/

@Controller
@Slf4j
public class MoviesController {

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private TagsService tagsService;


    /**
     * 每页展示多少条电影数
     */
    private static int MoviesCount = 15;

    /**
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/movies/getFeedBack")
    @Desc(value="异步获取资源失效反馈")
	public String getFeedBack(HttpServletRequest request, ModelMap map) {

		String result = "feedback_list";

		List<Map<String, Object>> rs = new ArrayList<>();

		RedisUtil.getInstance().sMembers(BC_Constant.REDIS_FEEDBACK).forEach(i -> {
			rs.add(JsonUtil.json2map(i));
		});

		map.put("feedback_list", rs);

		return "/page/common/" + result;
	}

    /**
     * @param map
     * @return
     * @author Bruce
     * 资源失效反馈，找站长
     */
    @RequestMapping("/movies/resFeedBack")
    @ResponseBody
    @Desc("资源失效反馈，找站长")
	public Result<String> resFeedBack(HttpServletRequest request, @RequestBody Object map) {
		String rs = "success";
		try {
			log.info("资源失效反馈,------{}",String.valueOf(map));
			if (RedisUtil.getInstance().sIsMember(BC_Constant.REDIS_FEEDBACK, String.valueOf(map))) {
				return ResultGenerator.genSuccessResult(rs);
			} else {


                //===================================异步操作=================================================
                ThreadPoolExecutor threadPoolExecutor = AsyncThreadPool.getInstance().getThreadPoolExecutor();
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        //发送异步站长通知消息
                        try {
                            //=================================消息提醒====================================================

                            //判断主题类型
                            NotifyEnum match = NotifyEnum.FEED;

                            //提醒系统赋值
                            NotifyRemindB nr = new NotifyRemindB();

                            //common
                            //{"spanID":"746358","uID":"519795","href":"https://northpark.cn/movies/post-746358.html",
                            // "title":"《卡比利亚之夜》百度云网盘下载[MP4//mkv]蓝光"}
                            Map<String, Object> feed_map = JsonUtil.json2map(String.valueOf(map));

                            nr.setRecipientId("507723");
                            nr.setSenderName(NotifyUtil.getUserNameByID(feed_map.get("uID").toString()));
                            nr.setObject(feed_map.get("title").toString());
                            nr.setObjectId(feed_map.get("spanID").toString());
                            nr.setObjectLinks(feed_map.get("href").toString());
                            nr.setMessage("---"+TimeUtils.nowTime()+"---提醒资源失效---");
                            nr.setStatus("0");


                            match.notifyInstance.execute(nr);

                            // 给站长发邮件
                            try {
                                EmailUtils.getInstance().resFeedBack(String.valueOf(map));

                            } catch (Exception ignore) {
                                log.error(ignore.getMessage());
                            }

                            //=================================消息提醒====================================================
                        }catch (Exception ig){
                            log.error("feedBack-notice-has-ignored-------:",ig);
                        }
                    }



                });
                //===================================异步操作=================================================

				// 添加到集合中
				RedisUtil.getInstance().sAdd(BC_Constant.REDIS_FEEDBACK, String.valueOf(map));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("resFeedBack------>", e);
			rs = "ex";
		}
		return ResultGenerator.genSuccessResult(rs);
	}


    /**
     * @param request
     * @return
     * @author Bruce
     * 置顶的方法
     */
    @RequestMapping("/movies/handup")
    @ResponseBody
    @BruceOperation
    public Result<String> handUp(HttpServletRequest request) {
        String rs = "success";
        try {
            String id = request.getParameter("id");
            String max_hot_sql_id = "select max(hot_index) as hot_index from bc_movies ";
            List<Map<String, Object>> list = moviesService.querySqlMap(max_hot_sql_id);
            Integer hot_index = 0;
            if (!CollectionUtils.isEmpty(list)) {
                hot_index = (Integer) list.get(0).get("hot_index");
                hot_index++;
            }
            if (hot_index > 0) {
                Movies m = moviesService.findMovies(Integer.parseInt(id));
                if (m != null) {
                    m.setHotIndex(hot_index);
                    moviesService.updateMovies(m);
                }
            }

        } catch (Exception e) {
            log.error("movies action------>", e);
            rs = "ex";
        }
        return ResultGenerator.genSuccessResult(rs);
    }


    /**
     * 隐藏电影的方法
     *
     * @param request
     * @return
     */
    @RequestMapping("/movies/hideup")
    @ResponseBody
    @BruceOperation
    public Result<String> hideUp(HttpServletRequest request) {
        String rs = "success";
        try {
            String id = request.getParameter("id");

            Movies m = moviesService.findMovies(Integer.parseInt(id));
            if (m != null) {
                m.setDisplayed("N");
                moviesService.updateMovies(m);
            }

        } catch (Exception e) {
            log.error("movies action------>", e);
            rs = "ex";
        }
        return ResultGenerator.genSuccessResult(rs);
    }


    /**
     * 跳转后台添加
     *
     * @param map
     * @return
     */
    @RequestMapping("/movies/add")
    @BruceOperation
    public String toAdd(ModelMap map, HttpServletRequest request) {

        return "/page/admin/movies/moviesAdd";
    }


    /**
     * 跳转到电影编辑页面
     *
     * @param map
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/movies/edit/{id}")
    @BruceOperation
    public String edit(ModelMap map, @PathVariable String id, HttpServletRequest request ) {

        if (StringUtils.isNotEmpty(id)) {
            //sql注入处理
            id = WAQ.forSQL().escapeSql(id);
            Movies  model = moviesService.findMovies(Integer.valueOf(id));
            if(model!=null) {
            	map.addAttribute("model", model);
            }
        }


        return "/page/admin/movies/moviesAdd";
    }


    /**
     * 保存电影的方法
     *
     * @param map
     * @return
     */
    @RequestMapping("/movies/addItem")
    @ResponseBody
    @BruceOperation
    public Result<String> addItem(ModelMap map, Movies model) {

        assert Objects.nonNull(model)
                && StringUtils.isNotBlank(model.getMovieDesc())
                && StringUtils.isNotBlank(model.getMovieName())
                && StringUtils.isNotBlank(model.getPath())
                && StringUtils.isNotBlank(model.getColor());
        String rs = "success";
        try {
        	//更新
        	if(model.getId()!=null && model.getId()!=0) {
                model.setAddTime(TimeUtils.nowDate());
        		moviesService.updateMovies(model);

                //从redis set里面删除更新的失效资源
                if(RedisUtil.getInstance().sMembers(BC_Constant.REDIS_FEEDBACK).toString().contains(model.getId().toString())){
                    RedisUtil.getInstance().sMembers(BC_Constant.REDIS_FEEDBACK).forEach(item->{
                        if(item.contains(model.getId().toString())) {
                            //{"spanID":"746358","uID":"519795","href":"https://northpark.cn/movies/post-746358.html",
                            // "title":"《卡比利亚之夜》百度云网盘下载[MP4//mkv]蓝光"}
                            Map<String, Object> feed_map = JsonUtil.json2map(item);

                            //===================================异步操作=================================================
                            ThreadPoolExecutor threadPoolExecutor = AsyncThreadPool.getInstance().getThreadPoolExecutor();
                            threadPoolExecutor.execute(new Runnable() {
                                @Override
                                public void run() {

                                    //发送异步站长通知消息
                                    try {
                                        //=================================消息提醒====================================================

                                        //判断主题类型
                                        NotifyEnum match = NotifyEnum.FEED;

                                        //提醒系统赋值
                                        NotifyRemindB nr = new NotifyRemindB();

                                        //common
                                        nr.setRecipientId(feed_map.get("uID").toString());
                                        nr.setObject(feed_map.get("title").toString());
                                        nr.setObjectId(feed_map.get("spanID").toString());
                                        nr.setObjectLinks(feed_map.get("href").toString());
                                        nr.setMessage("---"+TimeUtils.nowTime()+"---资源已更新，请知悉---");
                                        nr.setStatus("0");


                                        match.notifyInstance.execute(nr);

                                        //=================================消息提醒====================================================
                                    }catch (Exception ig){
                                        log.error("addItem-notice-has-ignored-------:",ig);
                                    }
                                }



                            });
                            //===================================异步操作=================================================

                            //从redis移除
                            RedisUtil.getInstance().sRem(BC_Constant.REDIS_FEEDBACK, item);
                        }
                    });
                }

        	}else {//新增

                 model.setRetCode(MD5Utils.encrypt(model.getMovieName(),MD5Utils.MD5_KEY));
        		 model.setAddTime(TimeUtils.nowDate());
                 moviesService.addMovies(model);
        	}

        } catch (Exception e) {
            log.error("movies action------>", e);
            rs = "ex";
        }
        return ResultGenerator.genSuccessResult(rs);
    }


    /**
     * 按照日期计算
     *
     * @param map
     * @param tags_code
     * @param request
     * @return
     */
    @RequestMapping("/movies/date/{tags_code}")
    public String dateSearch(ModelMap map, @PathVariable String tags_code, HttpServletRequest request) {
        //防止sql注入
        tags_code = WAQ.forSQL().escapeSql(tags_code);
        String whereSql = " where add_time = '" + tags_code + "' ";

        map.put("sel_date", tags_code);


        log.info("sql ---" + whereSql);


        //排序条件
        String orderBy = "hot_index desc,id desc";

        PageHelper.startPage(1,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,orderBy);
        PageInfo pageInfo = new PageInfo(result_list);


        //处理标签列表
        handleTag(result_list);

        map.addAttribute("page", 1);
        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies/date/" + tags_code);

        //获取标签模块
        getTags(map, request);
        return "movies2";
    }

    /**
     * 按照日期分页计算
     *
     * @param map
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/movies/date/{tags_code}/page/{page}")
    public String dateListPage(ModelMap map, @PathVariable Integer page, HttpServletRequest request, @PathVariable String tags_code,
                               HttpServletResponse response, HttpSession session) throws IOException {

        //防止sql注入
        tags_code = WAQ.forSQL().escapeSql(tags_code);
        String whereSql = " where add_time = '" + tags_code + "' ";

        map.put("sel_date", tags_code);


        log.info("sql ---" + whereSql);


        //排序条件
        String orderBy = "hot_index desc,id desc";

        PageHelper.startPage(page,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,orderBy);
        PageInfo pageInfo = new PageInfo(result_list);


        //处理标签列表
        handleTag(result_list);

        map.addAttribute("page", page);
        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies/date/" + tags_code);

        //获取标签模块
        getTags(map, request);
        return "movies2";
    }


    /**
     * 按照标签计算
     *
     * @param map
     * @param tags_code
     * @param request
     * @return
     */
    @RequestMapping("/movies/tag/{tags_code}")
    public String tagSearch(ModelMap map, @PathVariable String tags_code, HttpServletRequest request) {
        //防止sql注入
        tags_code = WAQ.forSQL().escapeSql(tags_code);
        String whereSql = " where tag_code like '%" + tags_code + "%' ";

        map.put("sel_tag", tags_code);


        log.info("sql ---" + whereSql);


        //排序条件
        String orderBy = "hot_index desc,id desc";

        PageHelper.startPage(1,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,orderBy);
        PageInfo pageInfo = new PageInfo(result_list);

        //处理标签列表
        handleTag(result_list);


        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies/tag/" + tags_code);
        map.addAttribute("page", 1);
        //获取标签模块
        getTags(map, request);

        return "movies2";
    }

    /**
     * 按照标签分页计算
     *
     * @param map
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "/movies/tag/{tags_code}/page/{page}")
    public String tag_list_page(ModelMap map, @PathVariable Integer page, HttpServletRequest request, @PathVariable String tags_code,
                              HttpServletResponse response, HttpSession session) throws IOException {

        //防止sql注入
        tags_code = WAQ.forSQL().escapeSql(tags_code);
        String whereSql = " where tag_code like '%" + tags_code + "%' ";

        map.put("sel_tag", tags_code);


        log.info("sql ---" + whereSql);


        //排序条件
        String orderBy = "hot_index desc,id desc";

        PageHelper.startPage(page,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,orderBy);
        PageInfo pageInfo = new PageInfo(result_list);

        //处理标签列表
        handleTag(result_list);


        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies/tag/" + tags_code);
        map.addAttribute("page", page);
        //获取标签模块
        getTags(map, request);

        return "movies2";
    }

    /**
     * 查询列表--首页
     *
     * @return
     */
    @RequestMapping(value = "/movies")
    //@UseCK
    @RateLimit
    public String list(ModelMap map,  HttpServletRequest request, HttpSession session) throws Exception {

        session.removeAttribute("tabs");
        session.setAttribute("tabs", "movies");

        String whereSql = " where displayed is null ";


        //搜索
        String keyword = request.getParameter("keyword");
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = URLDecoder.decode(keyword, "UTF-8");
        }
        map.put("keyword", keyword);
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = WAQ.forSQL().escapeSql(keyword);
            if (keyword.contains(" ")) {
                String keyword2 = keyword.replaceAll(" ", "");
                whereSql += " and movie_name like '%" + keyword + "%' or movie_name like '%" + keyword2 + "%' ";
            } else {
                whereSql += " and movie_name like '%" + keyword + "%' ";
            }


        }

        log.info("sql ---" + whereSql);
        //排序条件
        String _orderBy = "";
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotEmpty(orderBy)) {
            if ("hot".equals(orderBy)) {
                _orderBy = "hot_index desc" ;
            } else if ("latest".equals(orderBy)) {
                _orderBy = "id desc" ;
            }
            map.put("orderBy", orderBy);
        } else {
            _orderBy = "add_time desc,id desc" ;
        }


        PageHelper.startPage(1,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,_orderBy);
        PageInfo pageInfo = new PageInfo(result_list);


        //处理标签列表
        handleTag(result_list);

        map.put("page", 1);
        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies");

        //获取标签模块
        getTags(map, request);

        return "movies2";
    }

    /**
     * 电影分页列表
     *
     * @param map
     * @param page
     * @param request
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/movies/page/{page}")
    @RateLimit
    public String listPage(ModelMap map, @PathVariable Integer page, HttpServletRequest request,
                           HttpSession session) throws IOException {

        session.removeAttribute("tabs");
        session.setAttribute("tabs", "movies");

        String whereSql = " where displayed is  null ";


        //搜索
        String keyword = request.getParameter("keyword");
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = URLDecoder.decode(keyword, "UTF-8");
        }
        map.put("keyword", keyword);
        if (StringUtils.isNotEmpty(keyword)) {
            keyword = WAQ.forSQL().escapeSql(keyword);
            if (keyword.contains(" ")) {
                String keyword2 = keyword.replaceAll(" ", "");
                whereSql += " and movie_name like '%" + keyword + "%' or movie_name like '%" + keyword2 + "%' ";
            } else {
                whereSql += " and movie_name like '%" + keyword + "%' ";
            }


        }

        log.info("sql ---" + whereSql);

        //排序条件
        String _orderBy = "";
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotEmpty(orderBy)) {
            if ("hot".equals(orderBy)) {
                _orderBy = "hot_index desc" ;
            } else if ("latest".equals(orderBy)) {
                _orderBy = "id desc" ;
            }
            map.put("orderBy", orderBy);
        } else {
            _orderBy = "add_time desc,id desc" ;
        }


        PageHelper.startPage(page,MoviesCount);
        List<Movies> result_list = moviesService.findByCondition(whereSql,_orderBy);
        PageInfo pageInfo = new PageInfo(result_list);

        //处理标签列表
        handleTag(result_list);

        map.addAttribute("page", page);
        map.addAttribute("pageInfo", pageInfo);
        map.addAttribute("list", result_list);
        map.addAttribute("actionUrl", "/movies");

        //获取标签模块
        getTags(map, request);

        return "movies2";
    }


    /**
     * 处理标签列表
     *
     * @param result_list
     */
    private void handleTag(List<Movies> result_list) {
        if (!CollectionUtils.isEmpty(result_list)) {

            for (Movies m : result_list) {
                String tag = m.getTag();
                String tag_code = m.getTagCode();
                if (StringUtils.isNotEmpty(tag) && StringUtils.isNotEmpty(tag_code)) {
                    String[] tags = tag.split(",");
                    String[] tag_codes = tag_code.split(",");
                    if (tags.length != tag_codes.length) {
                    	throw new NorthParkException(ResultEnum.Movie_Tag_Not_Match);
                    }
                    List<Map<String, String>> tag_list = Lists.newArrayList();
                    for (int i = 0; i < tags.length; i++) {
                        Map<String, String> map = Maps.newHashMap();
                        map.put("tag", tags[i]);
                        map.put("tag_code", tag_codes[i]);
                        tag_list.add(map);
                        map = null;
                    }
                    m.setTag_list(tag_list);
                }

            }
        }
    }



    /**
     * 跳转到电影详情页面
     *
     * @param map
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/movies/post-{id}.html")
    @RateLimit
    public String postDetail(ModelMap map, @PathVariable String id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        if (StringUtils.isNotEmpty(id)) {
            //sql注入处理
            id = WAQ.forSQL().escapeSql(id);
            Movies  model = moviesService.findMovies(Integer.valueOf(id));
            if(model!=null && !StringUtils.equals("N",model.getDisplayed())) {

                //页面描述
            	if(StringUtils.isNotEmpty(model.getMovieDesc())) map.put("movieDesc", Jsoup.parse(model.getMovieDesc()).text());

            	map.addAttribute("model", model);
            }
        }


        return "/movies-article";
    }


    //、、、、、、、、、、、、、、、、、、、、、、以上为用到的方法、、、、、、、、、、、、、、、、、、、、、、、、、、、

    /**
     * 获取标签模块
     *
     * @param map
     */
    private void getTags(ModelMap map, HttpServletRequest request) {
        List<Tags> tags = null;
        List<Map<String, Object>> movies_hot_list = null;

        //从redis取
        String tags_str = RedisUtil.getInstance().get("moviesTags");
        String movies_hot_list_str = RedisUtil.getInstance().get("movies_hot_list");
        if(StringUtils.isNotEmpty(tags_str) && StringUtils.isNotEmpty(movies_hot_list_str)) {

        	movies_hot_list = JsonUtil.json2ListMap(movies_hot_list_str);
        	tags = JsonUtil.json2list(tags_str, Tags.class);
        }

        //从数据库取
        if (CollectionUtils.isEmpty(tags) && CollectionUtils.isEmpty(movies_hot_list)) {
            //获取标签

            tags = tagsService.findByCondition(" where tag_type = '1' ","");

            //获取热门电影
            String hot_sql = "select id,movie_name,color from bc_movies order by rand() desc limit 0,70";
            movies_hot_list = moviesService.querySqlMap(hot_sql);

            RedisUtil.getInstance().set("movies_hot_list", JsonUtil.object2json(movies_hot_list), 24 * 60 * 60);
            RedisUtil.getInstance().set("moviesTags", JsonUtil.object2json(tags), 24 * 60 * 60);

        }


        map.put("movies_hot_list", movies_hot_list);
        map.put("moviesTags", tags);

    }


}
