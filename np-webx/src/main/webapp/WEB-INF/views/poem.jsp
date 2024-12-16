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
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
    <c:if test="${page==null || page==''}">
        <title>诗词秀 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>诗词秀::第${page}页 | NorthPark</title>
    </c:if>

    <meta name="keywords" content="诗词,古诗文,${keyword},诗词赏析,诗词名句,NorthPark">
    <meta name="description" content="NorthPark诗词频道，收录历代诗词名句、诗词赏析。包含唐诗宋词元曲等，按朝代、作者、题材分类展示。">

    <style>
        .poem-list-item {
            background: #fff;
            padding: 25px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
        }

        .poem-list-item:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
        }

        .poem-title {
            font-size: 24px;
            margin-bottom: 15px;
        }

        .poem-title a {
            color: #333;
            text-decoration: none;
        }

        .poem-meta {
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .poem-meta a {
            color: #d7c374;
            margin: 0 5px;
            transition: color 0.3s ease;
        }

        .poem-meta a:hover {
            color: #dac658;
            text-decoration: none;
        }

        .poem-content {
            color: #555;
            line-height: 1.8;
            margin-bottom: 20px;
        }

        .search-box {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }

        .sidebar-box {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }
    </style>

</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">诗词秀</h1>

<div class="clearfix maincontent grayback">


    <div class="container">

        <div class="mainbody" style="margin-top:80px; ">


            <div class="row">
                <div class="col-md-8">

                    <div class="col-sm-12">

                        <div class="search-box">
                            <form class="form-search" id="J_ser_from" method="post" action="/poem/page/1">
                                <div class="input-group">
                                    <input type="text" id="keyword" name="keyword" value="${keyword}"
                                        class="form-control input-lg" placeholder="搜索诗词名句...">
                                    <span class="input-group-btn">
                                        <button class="btn btn-warning btn-lg" type="button" id="J_ser_btn">
                                            <i class="glyphicon glyphicon-search"></i> 搜索
                                        </button>
                                    </span>
                                </div>
                            </form>
                        </div>

                    </div>

                    <c:forEach items="${list }" var="s" varStatus="ss">

                        <div class="poem-list-item">
                            <h2 class="poem-title">
                                <a href="/poem/enjoy/${s.id}.html">${s.title}</a>
                            </h2>
                            <div class="poem-meta">
                                <span>作者：${s.author} </span>
                                <span>朝代：<a href="/poem/dynasty/${s.yearsCode}">${s.years}</a></span>
                                <span>类别：<a href="/poem/types/${s.typesCode}">${s.types}</a></span>
                            </div>
                            <div class="poem-content">${s.content}</div>
                            <a class="btn btn-warning" href="/poem/enjoy/${s.id}.html">
                                阅读全文 <i class="glyphicon glyphicon-arrow-right"></i>
                            </a>
                        </div>
                    </c:forEach>
                    <c:if test="${pagein!='no' }">
                        <%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>
                    </c:if>
                </div>

                <div class="col-md-4 ">

                    <!-- 轮播开始 -->
                    <div class="row padding-t20  bg-lyellow">
                        <div class="col-md-10 col-md-offset-1">
                            <div class="testimonails-slider">

                                <div class="flex-viewport"
                                     style="overflow: hidden; position: relative;">
                                    <ul class="slides"
                                        style="width: 1000%; transition-duration: 0.6s; transform: translate3d(-1500px, 0px, 0px);vertical-align: middle;">

                                        <c:forEach items="${poem }" var="s" varStatus="ss">

                                            <li class="center"

                                                    <c:if test="${ss.index % 3==0}">
                                                        class="clone"
                                                    </c:if>
                                                    <c:if test="${ss.index %3 ==1 } ">
                                                        class=""
                                                    </c:if>
                                                    <c:if test="${ss.index %3 ==2} ">
                                                        class="flex-active-slide"
                                                    </c:if>

                                                aria-hidden="true" style="float: left; display: block; width: 750px;">

                                                <div class="testimonails-content avatar ">
                                                    <p class="text-color-${ fn:toLowerCase(fn:substring( s.retCode ,0,1))}">${s.title }</p>
                                                    <p class="text-color-${ fn:toLowerCase(fn:substring( s.retCode ,0,1))}">${s.content1 }</p>
                                                    <p>
                                                        <a

                                                                href="/poem/enjoy/${s.id }.html"

                                                                title="${s.title}">


                                                            <span class=" imgbreath text-${ fn:toLowerCase(fn:substring( s.retCode ,0,1))}"
                                                                  alt="${s.title}">${ fn:toUpperCase(fn:substring( s.retCode ,0,1))}</span>
                                                        </a>
                                                    </p>
                                                    <h6 class="gray-text">
                                                            ${s.author }
                                                    </h6>
                                                    <h6 class="gray-text">
                                                            ${s.years }
                                                    </h6>
                                                </div>
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </div>
                                <ul class="flex-direction-nav">
                                    <li><a class="flex-prev" href="#"></a></li>
                                    <li><a class="flex-next" href="#"></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- 轮播结束 -->
                    <!-- hot  -->
                    <div class="row margin-t20 clearfix sidebar radius-5  bg-lyellow ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-leaf margin5"></span> 朝代</h4>
                        </div>
                        <c:forEach var="z" items="${years_tag }">

                            <div class="col-md-10 margin5">
                                <c:if test="${z.tagCode == sel_tag }">
                                    <span class="glyphicon glyphicon-arrow-right margin5"></span>
                                    <a style="color: #45d0c6;" href="/poem/dynasty/${z.tagCode}"
                                       title="${z.tag }">${z.tag } </a>
                                </c:if>
                                <c:if test="${z.tagCode != sel_tag }">
                                    <span class="glyphicon glyphicon-tree-conifer margin5"></span>
                                    <a href="/poem/dynasty/${z.tagCode}" title="${z.tag }">${z.tag } </a>
                                </c:if>


                            </div>
                        </c:forEach>

                    </div>


                    <!-- tags  -->
                    <div class="row margin-t20 clearfix sidebar radius-10   bg-lyellow">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-tags margin5"></span> 词牌类别</h4>
                        </div>
                        <c:forEach var="z" items="${types_tag }">

                            <div class="col-md-10 margin5">
                                <c:if test="${z.tagCode == sel_tag }">
                                    <span class="glyphicon glyphicon-arrow-right margin5"></span>
                                    <a style="color: #45d0c6;" href="/poem/types/${z.tagCode}"
                                       title="${z.tag }">${z.tag } </a>
                                </c:if>
                                <c:if test="${z.tagCode != sel_tag }">
                                    <span class="glyphicon glyphicon-tag  margin5"></span>
                                    <a href="/poem/types/${z.tagCode}" title="${z.tag }">${z.tag } </a>
                                </c:if>


                            </div>
                        </c:forEach>


                    </div>


                </div>


            </div>


        </div>
    </div>
</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>


<script src="https://northpark.cn/statics/js/poem/easing.js" type="text/javascript"></script>

<script src="https://northpark.cn/statics/js/poem/timers.js" type="text/javascript"></script>

<script src="https://northpark.cn/statics/js/poem/dualSlider.js" type="text/javascript"></script>

<script type="text/javascript">

    $(function () {
        //搜索事件处理
        $("#J_ser_btn").click(function () {
            var keyword = $("#keyword").val();
            if (keyword) {
                window.location.href = "/poem/page/1?keyword=" + keyword;
            }
        })
    })


    /*  ##set query param */
    var keyword = $("#keyword").val();
    if (keyword) {
        $("#pageForm a").each(function () {
            var href = $(this).attr("href");
            $(this).attr("href", href + "?keyword=" + keyword);
        })
    }


</script>
<link media="all" type="text/css" rel="stylesheet" href="https://northpark.cn/statics/css/flexslider.css"><!-- 轮播css -->
<script type="text/javascript" src="https://northpark.cn/statics/js/jquery.flexslider.js"></script><!-- 轮播js -->
<script type="text/javascript">

    $(document).ready(function () {


        //激活动作
        $('.flexslider').flexslider({
            prevText: '',
            nextText: ''
        });

        $('.testimonails-slider').flexslider({
            animation: 'slide',
            slideshowSpeed: 5000,
            prevText: '',
            nextText: '',
            controlNav: false
        });


    });

</script>


</body>
</html>
