<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimal-ui">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <meta http-equiv="Content-Language" content="zh-CN">

    <meta name="author" content="NorthPark">
    <meta name="robots" content="index,follow,archive">
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    <%@ include file="page/common/common.jsp" %>

    <c:if test="${page==null || page==''}">
        <title>NorthPark影视窝 - 最新电影、电视剧、动漫资源分享下载</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>NorthPark影视窝第${page}页 - 最新电影、电视剧、动漫资源分享下载</title>
    </c:if>

    <meta name="keywords" content="NorthPark影视窝,最新电影,电视剧,动漫,电影下载,在线观看,${keyword}">
    <meta name="description" content="NorthPark影视窝提供最新电影、电视剧、动漫资源,支持在线观看和下载,每天实时更新热门影视资源。${keyword}相关影视资源尽在NorthPark影视窝。">

    <style>
        hr{
            border: 1px solid #dedede
        }
    </style>

    <%-- 引入捐助相关样式 --%>
    <link href="/static/css/donate.css" rel="stylesheet">

</head>

<body>

<%@ include file="page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">影视窝</h1>
<main class="clearfix maincontent grayback">
    <div class="container">
        <article class="mainbody" style="margin-top:80px; ">


            <div class="row">
                <div class="col-md-8">

                    <div class="col-sm-12">


                        <form class="form-search " id="J_ser_from" method="post" accept-charset="UTF-8" action="/movies/page/1" onkeydown="if(event.keyCode==13){return false;}">
                            <input id="keyword" placeholder="电影名" value="${keyword }"
                                   class="width100 input-medium search-query input-lg  border-light-1 bg-lyellow  radius-0"
                                   name="keyword" type="text">
                            <input data-activetext="搜索 ››" class="btn btn-hero " value="搜索" type="button"
                                   id="J_ser_btn">
                        </form>

                        <div class="row   padding20">
                            <input class="btn tag-node " oid="hot" type="button" value="热门排序">
                            <input class="btn tag-node " oid="latest" type="button" value="上新排序">
                            <input class="btn tag-node " type="button" value="影视网盘"><span class="badge green-badge">new</span>
                        </div>


                    </div>

                    <c:if test="${not empty list}">

                        <!-- 如果list不为空，显示以下内容 -->
                        <c:forEach items="${list }" var="s" varStatus="ss">

                        <div class="col-sm-12">
                            <div class="clearfix bg-white margin-b10 padding20 ">
                                <div class="row margin5  word-return">
                                    <div class="border-0 center">
                                        <p>
                                            <a href="/movies/post-${s.id }.html" oid="${s.id }">
                                                <small class="green-text">
                                                    <font size="5"><strong>
                                                        <c:if test="${s.hotIndex>0}">
                                                            <i class="fa fa-thumb-tack" title="已置顶"></i>
                                                        </c:if>
                                                            ${s.movieName}</strong></font>
                                                </small>
                                            </a>
                                        </p>


                                        <div class="clearfix visible-xs">
                                            <hr>
                                        </div>
                                    </div>

                                    <p>

                                        	发表于：<span class=" glyphicon glyphicon-time margin10"></span><span
                                            class="common-a-right" title="${s.addTime}"
                                            href="/movies/date/${s.addTime}">${s.addTime}</span>

                                        <span class=" glyphicon glyphicon-tags margin10"></span>

                                        <c:forEach items="${s.tag_list }" var="y" varStatus="yy">
                                            <strong><a class="common-a-right" title="${y.tag}"
                                                       href="/movies/tag/${y.tag_code }">${y.tag}</a></strong>
                                        </c:forEach>
                                        <c:if test="${user!=null }">
                                            <c:if test="${user.email == '654714226@qq.com' || user.email == 'qhdsoft@126.com' || user.email == 'woaideni@qq.com'}">
                                                <span class=" glyphicon glyphicon-arrow-up margin10"></span>
		                                        <a class="common-a-right" title="置顶" href="" onclick="handup('${s.id}')">置顶</a>
		                                        <span class=" glyphicon glyphicon-eye-close margin10"></span>
		                                        <a class="common-a-right" title="隐藏" href="" onclick="hideup('${s.id}')">大尺度隐藏</a>
		                                        <span class="glyphicon glyphicon-pencil margin10"></span>
		                                        <a class="common-a-right" title="编辑" href="/movies/edit/${s.id}" >快速编辑</a>
                                            </c:if>
                                        </c:if>

                                    </p>
                                    <p id="brief_${ss.index}">

                                            ${s.movieDesc }
                                    </p>

                                    <p>
                                        <a class="btn btn-warning margin-t10" href="/movies/post-${s.id}.html">
                                            Read More
                                            <span class="glyphicon  glyphicon-circle-arrow-right padding5"></span>
                                        </a>
                                    </p>

                                </div>
                            </div>
                        </div>
                    </c:forEach>


                        <c:if test="${pagein!='no' }">
                            <%@ include file="page/common/fenye.jsp" %>
                        </c:if>


                        <!-- 如果list不为空，显示更多内容 -->
                        <div class="col-sm-12">
                            <hr>
                            NorthPark影视网盘可以搜索到更多4K在线+阿里网盘高速下载影片，快去试试吧
                            <p>
                            <a class="btn btn-warning margin-t10"
                               target="_blank"
                               id="J_wp_btn2">
                                NorthPark影视网盘
                            </a>
                            </p>

                        </div>

                    </c:if>
                    <c:if test="${empty list}">

                        <!-- 如果list为空，显示以下内容 -->
                        <div class="col-sm-12">
                            <hr>
                            找不到相关影片，以下内容来自NorthPark影视网盘搜索，可以搜索到更多4K在线+阿里网盘高速下载影片，快去试试吧
                            <p>

                                <a class="btn btn-warning margin-t10"
                                   target="_blank"
                                   id="J_wp_btn">
                                    NorthPark影视网盘
                                </a>
                            </p>



                        </div>

                    </c:if>


                    <%--NorthPark网盘搜索展示部分--%>
                    <div id="search-results-container" style="margin: 20px; padding: 20px; ">

                    </div>




                </div>
                <div class="col-md-4 ">

                    <!-- donate  -->
                        <!-- load donate list  -->
                        <%@ include file="page/common/donate.jsp" %>

                    <!-- donate  -->


                    <!-- hot  -->
                    <div class="clearfix sidebar radius-5 ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-leaf margin5"></span> 随便看看</h4>
                        </div>
                        <c:forEach var="z" items="${movies_hot_list }"><%--map格式--%>

                            <div class="col-md-12 margin-t10">
                                <div class="col-xs-2 avatar">

                                    <span class="text-${ z.color }">${ fn:toUpperCase(fn:substring(z.movie_name ,0,1))   }</span>

                                </div>
                                <div class="col-xs-10">

                                    <a style="font-size: 14px;line-height: 32px;color: #888"
                                       href="/movies/post-${z.id }.html" title="${z.movie_name }">${z.movie_name } </a>

                                </div>


                            </div>
                        </c:forEach>


                    </div>


                    <!-- tags  -->
                    <div class="clearfix sidebar radius-10 ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-tags margin5"></span> 标签</h4>
                        </div>
                        <c:forEach var="z" items="${moviesTags }"><%--T格式--%>

                            <div class="col-md-10 margin5">
                                <c:if test="${z.tagCode == sel_tag }">
                                    <span class="glyphicon glyphicon-arrow-right margin5"></span>
                                    <a style="color: #45d0c6;" href="/movies/tag/${z.tagCode }"
                                       title="${z.tag }">${z.tag } </a>
                                </c:if>
                                <c:if test="${z.tagCode != sel_tag }">
                                    <span class="glyphicon glyphicon-tag margin5"></span>
                                    <a href="/movies/tag/${z.tagCode }" title="${z.tag }">${z.tag } </a>
                                </c:if>


                            </div>
                        </c:forEach>


                    </div>


                </div>
            </div>


        </article>
    </div>
</main>


<%@ include file="page/common/container.jsp" %>

<script type="text/javascript">
    //禁止图片拉伸
    $(function () {


        //设置标签颜色,绑定动作按钮
        $(".tag-node").each(function () {
            $(this).css("backgroundColor", getRandomColor());
            $(this).css("color", "#fff");

            $(this).click(function () {
                var oid = $(this).attr("oid");
                var val = $(this).val();
                if(oid){

                    window.location.href = "/movies/page/1?orderBy=" + oid;
                }

                if(val =='影视网盘'){
                    window.location.href = "http://so.northpark.cn/" ;
                }
            })
        });


    })
</script>

<script type="text/javascript">

    $(function () {
        //搜索事件处理
        $("#J_ser_btn").click(function () {
            $("#J_ser_btn").attr('disabled', true);
            var keyword = $("#keyword").val();
            if (keyword && keyword != "${keyword }") {
                window.location.href = "/movies/page/1?keyword=" + keyword;
            }
            setTimeout("$('#J_ser_btn').removeAttr('disabled')", 5000); //设置5秒后提交按钮 显示
        })

        //ENTER事件
        $("body").keydown(function () {
            if (event.keyCode.toString() === "13"){
                $("#J_ser_btn").click();
            }
        });


        // 打开northpark网盘搜索 - 修改为调用API并在前端解析展示
        $("#J_wp_btn").click(function (e) {
            e.preventDefault(); // 阻止默认行为
            var keyword = $("#keyword").val();
            if (keyword) {
                var apiUrl = "https://so.northpark.cn/api/search?kw=" + encodeURIComponent(keyword) + "&res=merge&src=all&ext=%7B%22referer%22:%22https:%2F%2Fdm.xueximeng.com%22%7D";
                $.ajax({
                    url: apiUrl,
                    type: 'GET',
                    dataType: 'json',
                    success: function (response) {
                        if (response.code === 0 && response.data) {
                            displaySearchResults(response.data);
                        } else {
                            alert('搜索失败: ' + response.message);
                        }
                    },
                    error: function () {
                        alert('请求失败，请稍后重试');
                    }
                });
            }
        });

        // 对于J_wp_btn2，类似处理，但不自动打开，可根据需求调整
        $("#J_wp_btn2").click(function (e) {
            e.preventDefault();
            var keyword = $("#keyword").val();
            if (keyword) {
                var apiUrl = "https://so.northpark.cn/api/search?kw=" + encodeURIComponent(keyword) + "&res=merge&src=all&ext=%7B%22referer%22:%22https:%2F%2Fdm.xueximeng.com%22%7D";
                $.ajax({
                    url: apiUrl,
                    type: 'GET',
                    dataType: 'json',
                    success: function (response) {
                        if (response.code === 0 && response.data) {
                            displaySearchResults(response.data);
                        } else {
                            alert('搜索失败: ' + response.message);
                        }
                    },
                    error: function () {
                        alert('请求失败，请稍后重试');
                    }
                });
            }
        });


        // 函数：解析并分类展示搜索结果
        function displaySearchResults(data) {
            var container = $('#search-results-container'); // 假设页面有一个ID为search-results-container的div用于展示结果
            if (container.length === 0) {
                // 如果没有容器，动态创建
                $('body').append('<div id="search-results-container" style="margin: 20px; padding: 20px; border: 1px solid #ccc; background: #fff;"></div>');
                container = $('#search-results-container');
            }
            container.empty(); // 清空现有内容

            container.append('<h2>搜索结果 (总数: ' + data.total + ')</h2>');

            var mergedByType = data.merged_by_type;
            for (var type in mergedByType) {
                if (mergedByType.hasOwnProperty(type)) {
                    var items = mergedByType[type];
                    container.append('<h3>' + type.toUpperCase() + ' (' + items.length + ')</h3>');
                    var ul = $('<ul></ul>');
                    $.each(items, function (index, item) {
                        var li = $('<li></li>');
                        li.append('<a href="' + item.url + '" target="_blank">' + (item.note || '无描述') + '</a>');
                        if (item.password) {
                            li.append(' <span>(密码: ' + item.password + ')</span>');
                        }
                        li.append(' <span>(' + item.source + ', ' + item.datetime + ')</span>');
                        ul.append(li);
                    });
                    container.append(ul);
                }
            }
        }
    })

    /*  ##set query param */
    var keyword = $("#keyword").val();
    if (keyword) {
        $("#pageForm a").each(function () {
            var href = $(this).attr("href");
            $(this).attr("href", href + "?keyword=" + keyword);
        })
    }

    var orderBy = "${orderBy}";
    if (orderBy) {
        $("#pageForm a").each(function () {
            var href = $(this).attr("href");
            $(this).attr("href", href + "?orderBy=" + orderBy);
        });

        //设置选中的标签格式
        $(".tag-node").each(function () {
            if ($(this).attr("oid") == orderBy) {
                $(this).css("border-radius", '0px');
            }

        });
    }


    //1、随机色的函数-rgb
    function getRandomColor() {
        var rgb = 'rgb(' + Math.floor(Math.random() * 255) + ','
            + Math.floor(Math.random() * 255) + ','
            + Math.floor(Math.random() * 255) + ')';
        console.log(rgb);
        return rgb;
    }


</script>
<script src="https://northpark.cn/statics/js/page/movies.js"></script>

<script>

    $(function () {
        $(".bg-white").find("img").each(function () {
            $(this).css('max-width', ($(".bg-white").width() * 0.85));
            $(this).css('margin-right', '20%');
        })

    })
</script>

<script>

    loadDonates(1);

</script>
</body>
</html>
