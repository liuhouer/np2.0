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
    <meta name="theme-color" content="#45d0c6">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta name="author" content="NorthPark">
    <meta name="robots" content="index,follow">
    <meta name="google-site-verification" content="Av5O436-yp3doL_sBZPluCUvSVYJywMpFGjVDi8Qu7k"/>
    <meta name="msvalidate.01" content="B11539DE5DB069595F60D57A3EDCDBE9"/>
    <link rel="canonical" href="https://northpark.cn/" />
    <link rel="shortcut icon" href="/static/img/favicon.ico">

    <title>NorthPark - Mac软件下载|最新影视资源|学习资源|最爱主题 - 文艺清新的互动社区</title>

    <meta name="keywords" content="NorthPark,Mac软件下载,破解软件,最新电影,电视剧下载,在线学习,编程教程,情商提升,生活分享,文艺社区,资源分享,免费下载,macOS应用,影视资源,知识分享">
    <meta name="description" content="NorthPark - 专业的资源分享社区，提供最新Mac软件破解版下载、热门影视资源、优质学习教程、情商提升指导。汇聚文艺青年，分享生活美好，打造温馨的知识交流平台。免费注册即可获取海量资源！">

    <!-- Open Graph 标签 -->
    <meta property="og:title" content="NorthPark - Mac软件下载|最新影视资源|学习资源|最爱主题 - 文艺清新的互动社区">
    <meta property="og:description" content="专业的资源分享社区，提供最新Mac软件破解版下载、热门影视资源、优质学习教程、情商提升指导。汇聚文艺青年，分享生活美好">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://northpark.cn/">
    <meta property="og:image" content="/static/img/logo.png">
    <meta property="og:site_name" content="NorthPark">

    <!-- Twitter Card 标签 -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="NorthPark - 专业资源分享社区">
    <meta name="twitter:description" content="Mac软件破解版、热门影视资源、优质学习教程、情商提升指导 - 免费注册获取海量资源">
    <meta name="twitter:image" content="/static/img/logo.png">

    <!-- Schema.org 结构化数据标记 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "WebSite",
      "name": "NorthPark",
      "url": "https://northpark.cn",
      "description": "文艺清新的互动社区,提供Mac软件下载、最新影视资源、情商提升指导、最爱主题等服务",
      "potentialAction": {
        "@type": "SearchAction",
        "target": "https://northpark.cn/search?q={search_term_string}",
        "query-input": "required name=search_term_string"
      },
      "publisher": {
        "@type": "Organization",
        "name": "NorthPark",
        "logo": {
          "@type": "ImageObject",
          "url": "/static/img/logo.png"
        }
      },
      "sameAs": [
        "https://github.com/liuhouer",
        "https://weibo.com/northpark"
      ]
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
<link media="all" type="text/css" rel="stylesheet" href="/static/css/flexslider.css"><!-- 树洞轮播css -->

<style>
    /* Admin Toolbox Styles */
    .admin-toolbox {
        position: fixed;
        bottom: 20px;
        right: 20px;
        z-index: 1000;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    }

    .toolbox-toggle {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background-color: #45d0c6;
        border: none;
        color: white;
        font-size: 24px;
        cursor: pointer;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.3s ease;
    }

    .toolbox-toggle:hover {
        background-color: #38c0b6;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
        transform: scale(1.1);
    }

    .toolbox-toggle.active {
        transform: rotate(45deg);
    }

    .toolbox-menu {
        position: absolute;
        bottom: 80px;
        right: 0;
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 2px 16px rgba(0, 0, 0, 0.15);
        overflow: hidden;
        min-width: 200px;
        display: none;
        animation: slideUp 0.3s ease forwards;
    }

    .toolbox-menu.show {
        display: block;
    }

    @keyframes slideUp {
        from {
            opacity: 0;
            transform: translateY(10px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    .toolbox-menu-item {
        display: flex;
        align-items: center;
        padding: 12px 16px;
        border-bottom: 1px solid #f0f0f0;
        text-decoration: none;
        color: #333;
        transition: all 0.2s ease;
        cursor: pointer;
    }

    .toolbox-menu-item:last-child {
        border-bottom: none;
    }

    .toolbox-menu-item:hover {
        background-color: #f5f5f5;
        color: #45d0c6;
        padding-left: 20px;
    }

    .toolbox-menu-item i {
        width: 20px;
        margin-right: 12px;
        text-align: center;
        font-size: 14px;
    }

    .toolbox-menu-item span {
        font-size: 14px;
        font-weight: 500;
    }

    .toolbox-title {
        padding: 12px 16px;
        font-weight: bold;
        color: #45d0c6;
        border-bottom: 2px solid #45d0c6;
        font-size: 13px;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .admin-toolbox {
            bottom: 10px;
            right: 10px;
        }

        .toolbox-toggle {
            width: 50px;
            height: 50px;
            font-size: 20px;
        }

        .toolbox-menu {
            min-width: 180px;
        }
    }
</style>

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
            <h2 id="love-section">
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
            <h2 id="note-section">
                树洞
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
    <!-- 树洞 模块代码-->
    <div class="container grayback" id="J_container_note">

    </div>


    <!-- ================================================================================== -->

    <div class="container">
        <div class="clearfix center logbox gray-text">
            <h2 id="romeo-section">
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
            <h2 id="movies-section">
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
<script type="text/javascript" src="/static/js/jquery.flexslider.js"></script><!-- 树洞轮播js -->
<script src="static/js/main.js"></script>

<!-- Admin Toolbox -->
<c:if test="${user != null && user.email == '654714226@qq.com'}">
    <div class="admin-toolbox" id="J_admin_toolbox">
        <div class="toolbox-menu" id="J_toolbox_menu">
            <div class="toolbox-title">
                <i class="fa fa-cog"></i> 管理工具
            </div>
            <a href="/admin/cron" class="toolbox-menu-item" title="定时任务管理">
                <i class="fa fa-clock-o"></i>
                <span>定时任务</span>
            </a>
            <a href="/admin/stat" class="toolbox-menu-item" title="系统概览">
                <i class="fa fa-bar-chart"></i>
                <span>系统概览</span>
            </a>
        </div>
        <button class="toolbox-toggle" id="J_toolbox_toggle" title="管理工具">
            <i class="fa fa-cog"></i>
        </button>
    </div>

    <script>
        (function() {
            var toggle = document.getElementById('J_toolbox_toggle');
            var menu = document.getElementById('J_toolbox_menu');

            if (!toggle || !menu) return;

            // Toggle menu visibility
            toggle.addEventListener('click', function(e) {
                e.stopPropagation();
                toggle.classList.toggle('active');
                menu.classList.toggle('show');
            });

            // Close menu when clicking outside
            document.addEventListener('click', function(e) {
                var toolbox = document.getElementById('J_admin_toolbox');
                if (toolbox && !toolbox.contains(e.target)) {
                    toggle.classList.remove('active');
                    menu.classList.remove('show');
                }
            });

            // Close menu when menu item is clicked
            var menuItems = menu.querySelectorAll('.toolbox-menu-item');
            menuItems.forEach(function(item) {
                item.addEventListener('click', function() {
                    toggle.classList.remove('active');
                    menu.classList.remove('show');
                });
            });

            // Close menu on Escape key
            document.addEventListener('keydown', function(e) {
                if (e.key === 'Escape') {
                    toggle.classList.remove('active');
                    menu.classList.remove('show');
                }
            });
        })();
    </script>
</c:if>

</body>

</html>
