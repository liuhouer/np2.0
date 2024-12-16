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
    <meta name="robots" content="index,follow">
    <meta name="google-site-verification" content="Av5O436-yp3doL_sBZPluCUvSVYJywMpFGjVDi8Qu7k"/>
    <meta name="msvalidate.01" content="B11539DE5DB069595F60D57A3EDCDBE9"/>
    <link rel="canonical" href="https://northpark.cn/" />
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    
    <title>NorthPark - Mac软件下载|最新影视资源|学习资源|主题图册 - 文艺清新的互动社区</title>
    
    <meta name="keywords" content="NorthPark,Mac软件下载,最新影视资源,学习资源,情商提升,主题图册,互动社区,文艺范,小清新,碎碎念">
    <meta name="description" content="NorthPark是一个集Mac软件下载、最新影视资源分享、学习资源、情商提升技巧、主题图册于一体的文艺清新互动社区。这里有丰富的Mac软件资源、最新影视剧资源、专业的情商提升指导、精选主题图册,以及碎碎念等互动版块。加入我们,发现更多精彩！">

    <!-- Schema.org 结构化数据标记 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "WebSite",
      "name": "NorthPark",
      "url": "https://northpark.cn",
      "description": "文艺清新的互动社区,提供Mac软件下载、最新影视资源、情商提升指导、主题图册等服务",
      "potentialAction": {
        "@type": "SearchAction",
        "target": "https://northpark.cn/search?q={search_term_string}",
        "query-input": "required name=search_term_string"
      }
    }
    </script>

    <!-- /**
     *
     * 　　　┏┓　　　┏┓
     * 　　┏┛┻━━━┛┻┓
     * 　　┃　　　　　　　┃
     * 　　┃　　　━　　　┃
    * 　　┃　┳┛　┗┳　┃
     * 　　┃　　　　　　　┃
     * 　　┃　　　┻　　　┃
     * 　　┃　　　　　　　┃
     * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
     * 　　　　┃　　　┃
     * 　　　　┃　　　┗━━━┓
     * 　　　　┃　　　　　　　┣┓
     * 　　　　┃　　　　　　　┏┛
     * 　　　　┗┓┓┏━┳┓┏┛
     * 　　　　　┃┫┫　┃┫┫
     * 　　　　　┗┻┛　┗┻┛
     *
     */ -->

<!-- load css -->
<%@ include file="/WEB-INF/views/page/common/common.jsp" %>
<link media="all" type="text/css" rel="stylesheet" href="/static/css/flexslider.css"><!-- 碎碎念轮播css -->


</head>

<body>
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>


<div class="clearfix maincontent grayback" id="J_maincontent">

    <!-- 轮播区域 引入轮播模块代码 -->
    <%@ include file="/WEB-INF/views/page/dash/dash-slide.jsp" %>


    <!-- ================================================================================== -->

<%--    <div class="container">--%>
<%--        <div class="clearfix center logbox gray-text">--%>
<%--            <h1>--%>
<%--                northpark center--%>
<%--            </h1>--%>
<%--        </div>--%>
<%--    </div>--%>
    <div class="container">
        <div class="clearfix center logbox gray-text">
            <h2>
                最爱
            </h2>
        </div>
        <hr class="smallhr">
        <div class="row sloganbox">
            <div class="col-xs-8 col-xs-offset-2 center">

                <p>什么时候爱上一首曲子，什么时候恋上一张贴画，什么时候迷上一种习惯...</p>
                <p>一件件美好的事物，勾勒出您最真实的生命轨迹。</p>
                <a href="/signup" class="btn btn-hero btn-lg radius-2 margin-t10 no-decoration">Join NorthPark ››</a>
            </div>
        </div>
    </div>

    <!-- 最爱 模块代码-->
    <div class="container grayback" id="J_container_love">


        <div class="row center margin20">
            <a href="/love" class="btn btn-gray btn-lg radius-2 margin-t10 no-decoration">发现更多 ››</a>
        </div>

        <!-- ================================================================================== -->

    </div>

    <div class="container">
        <div class="clearfix center logbox gray-text">
            <h2>
                碎碎念
            </h2>
        </div>

        <hr class="smallhr">

        <div class="row sloganbox">
            <div class="col-xs-8 col-xs-offset-2 center">

                <p>扯淡、吐槽、心情、树洞……想说啥就说啥！...</p>
                <p>NorthPark就是你的树洞。</p>
            </div>
        </div>
    </div>
    <!-- 碎碎念 模块代码-->
    <div class="container grayback" id="J_container_note">

    </div>


    <!-- ================================================================================== -->

    <div class="container">
        <div class="clearfix center logbox gray-text">
            <h2>
                情圣时刻
            </h2>
        </div>
        <hr class="smallhr">

        <div class="row sloganbox">
            <div class="col-xs-8 col-xs-offset-2 center" style="font-family: 'Dorsa-Regular';">


                <p>情商提升的技巧和讲解
                </p>
                <p>情圣专业讲解、解决各种各样的宅男问题.</p>
            </div>
        </div>

    </div>

    <!-- 情圣日记 模块代码-->
    <div class="container grayback" id="J_container_romeo">

    </div>


    <!-- ================================================================================== -->

    <div class="container">
        <div class="clearfix center logbox gray-text">
            <h2>
                热映电影
            </h2>
        </div>
        <hr class="smallhr">

        <div class="row sloganbox">
            <div class="col-xs-8 col-xs-offset-2 center" style="font-family: 'Dorsa-Regular';">

                <p>最新热映电影、BT、云盘、火热资源
                </p>
                <p>每周更新.</p>
            </div>
        </div>

    </div>

    <!-- 电影 模块代码-->
    <div class="container grayback" id="J_container_movies">

    </div>

    <!-- ================================================================================== -->


</div>



<!-- load js -->
<%@ include file="/WEB-INF/views/page/common/container.jsp" %>
<script type="text/javascript" src="/static/js/jquery.flexslider.js"></script><!-- 碎碎念轮播js -->
<script src="static/js/main.js"></script>

</body>

</html>
