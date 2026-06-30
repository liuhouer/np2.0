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
    <meta name="robots" content="index,follow,archive">
    <link rel="shortcut icon" href="/static/img/favicon.ico">
    <%@ include file="page/common/common.jsp" %>
    <c:if test="${page==null || page==''}">
        <title>Mac软件下载 | 精品Mac破解软件资源 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>Mac软件下载第${page}页 | 精品Mac破解软件资源 | NorthPark</title>
    </c:if>

    <meta name="keywords" content="Mac软件下载,Mac破解软件,macOS应用,苹果软件,免费Mac软件,办公软件,图像处理,视频剪辑,开发工具,设计软件,NorthPark软件站">
    <meta name="description"
          content="NorthPark Mac软件站 - 专业的Mac软件下载平台，提供最新macOS应用、破解软件、办公工具、设计软件、开发工具等精品Mac软件资源。所有软件经过安全检测，支持最新macOS系统，让您的Mac发挥最大潜能！">

    <!-- 动态canonical链接 -->
    <c:if test="${page==null || page==''}">
        <link rel="canonical" href="https://northpark.cn/soft/" />
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <link rel="canonical" href="https://northpark.cn/soft/page/${page}" />
        <!-- 分页SEO标签 -->
        <c:if test="${page > 1}">
            <link rel="prev" href="https://northpark.cn/soft/page/${page-1}" />
        </c:if>
        <c:if test="${page == 1}">
            <link rel="prev" href="https://northpark.cn/soft/" />
        </c:if>
        <link rel="next" href="https://northpark.cn/soft/page/${page+1}" />
    </c:if>

    <!-- Open Graph 标签 -->
    <meta property="og:title" content="<c:if test='${page==null || page==\'\'}'>Mac软件下载 | 精品Mac破解软件资源 | NorthPark</c:if><c:if test='${page!=null && page!=\'\'}'>Mac软件下载第${page}页 | 精品Mac破解软件资源 | NorthPark</c:if>">
    <meta property="og:description" content="专业的Mac软件下载平台，提供最新macOS应用、破解软件、办公工具、设计软件、开发工具等精品Mac软件资源。所有软件经过安全检测">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://northpark.cn/soft/">
    <meta property="og:image" content="https://minioapi.northpark.cn/pic/mac-banner.jpg">

    <!-- Twitter Card 标签 -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="Mac软件下载 - NorthPark">
    <meta name="twitter:description" content="精品Mac破解软件资源下载">
    <meta name="twitter:image" content="https://minioapi.northpark.cn/pic/mac-banner.jpg">
<style>
:root {
    --soft-accent: #45d0c6;
    --soft-accent-dark: #38b2a8;
    --soft-card-bg: #ffffff;
    --soft-page-bg: #f5f7fa;
    --soft-text: #2d3436;
    --soft-text-muted: #636e72;
    --soft-border: #e8ecf1;
    --soft-shadow-sm: 0 2px 8px rgba(0,0,0,0.06);
    --soft-shadow-md: 0 6px 20px rgba(0,0,0,0.08);
    --soft-shadow-lg: 0 12px 36px rgba(0,0,0,0.12);
    --soft-radius: 14px;
    --soft-radius-sm: 8px;
}

.maincontent.grayback {
    background: var(--soft-page-bg) !important;
}

/* Search wrapper - glassmorphism */
.search-wrapper {
    background: rgba(255,255,255,0.75);
    backdrop-filter: blur(12px);
    -webkit-backdrop-filter: blur(12px);
    border: 1px solid rgba(255,255,255,0.5);
    border-radius: var(--soft-radius);
    padding: 20px 24px;
    box-shadow: var(--soft-shadow-sm);
    margin-bottom: 24px;
}
.search-wrapper .form-search {
    display: flex;
    gap: 12px;
    align-items: center;
}
.search-wrapper input[type="text"] {
    flex: 1;
    border: 2px solid var(--soft-border) !important;
    border-radius: 50px !important;
    padding: 12px 24px !important;
    font-size: 15px;
    background: #fff !important;
    transition: all 0.3s ease;
    outline: none;
    box-shadow: none !important;
}
.search-wrapper input[type="text"]:focus {
    border-color: var(--soft-accent) !important;
    box-shadow: 0 0 0 4px rgba(69,208,198,0.15) !important;
}
.search-wrapper input[type="text"]::placeholder {
    color: #b2bec3;
    font-style: italic;
}
.search-wrapper .btn-hero {
    background: linear-gradient(135deg, var(--soft-accent), var(--soft-accent-dark)) !important;
    color: #fff !important;
    border: none !important;
    border-radius: 50px !important;
    padding: 12px 28px !important;
    font-size: 15px;
    font-weight: 600;
    letter-spacing: 0.5px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 14px rgba(69,208,198,0.3);
    white-space: nowrap;
}
.search-wrapper .btn-hero:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(69,208,198,0.4);
}

/* Software cards */
.soft-card {
    background: var(--soft-card-bg);
    border-radius: var(--soft-radius);
    box-shadow: var(--soft-shadow-sm);
    padding: 28px;
    margin-bottom: 20px;
    transition: all 0.35s cubic-bezier(0.4,0,0.2,1);
    border: 1px solid var(--soft-border);
    position: relative;
    overflow: hidden;
}
.soft-card:hover {
    box-shadow: var(--soft-shadow-lg);
    transform: translateY(-4px);
}
.soft-card .card-title {
    font-size: 20px;
    font-weight: 700;
    color: var(--soft-text);
    margin: 0 0 8px 0;
    line-height: 1.4;
}
.soft-card .card-title a {
    color: var(--soft-text);
    text-decoration: none;
    transition: color 0.2s;
}
.soft-card .card-title a:hover {
    color: var(--soft-accent);
}

/* Pin ribbon */
.pin-icon {
    position: absolute;
    top: 14px;
    left: 14px;
    background: linear-gradient(135deg, #e17055, #d63031);
    color: #fff;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 13px;
    box-shadow: 0 2px 8px rgba(214,48,49,0.3);
    z-index: 2;
}

/* Meta chips */
.meta-chips {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin: 10px 0;
}
.meta-chip {
    display: inline-flex;
    align-items: center;
    gap: 5px;
    background: var(--soft-page-bg);
    color: var(--soft-text-muted);
    padding: 5px 14px;
    border-radius: 50px;
    font-size: 12px;
    font-weight: 500;
    text-decoration: none !important;
    transition: all 0.2s;
    border: 1px solid transparent;
}
.meta-chip:hover {
    background: #fff;
    border-color: var(--soft-accent);
    color: var(--soft-accent);
    text-decoration: none !important;
}
.meta-chip i {
    font-size: 11px;
    opacity: 0.7;
}

/* Card description */
.soft-card .card-brief {
    color: var(--soft-text-muted);
    font-size: 14px;
    line-height: 1.7;
    margin: 12px 0 16px 0;
}

/* Read more button */
.btn-readmore {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    background: var(--soft-text);
    color: #fff;
    padding: 10px 24px;
    border-radius: 50px;
    font-size: 13px;
    font-weight: 600;
    text-decoration: none !important;
    letter-spacing: 0.5px;
    transition: all 0.3s ease;
    border: none;
}
.btn-readmore:hover {
    background: var(--soft-accent);
    color: #fff !important;
    text-decoration: none !important;
    transform: translateX(4px);
}
.btn-readmore .arrow {
    transition: transform 0.3s ease;
    font-size: 14px;
}
.btn-readmore:hover .arrow {
    transform: translateX(4px);
}

/* Sidebar blocks - reset global .sidebar styles */
.sidebar-block,
.sidebar-block.sidebar {
    background: var(--soft-card-bg);
    border-radius: var(--soft-radius);
    box-shadow: var(--soft-shadow-sm);
    padding: 20px;
    margin: 0 0 20px 0;
    border: 1px solid var(--soft-border);
    box-sizing: border-box;
    width: 100% !important;
    float: none !important;
    display: block;
    position: relative;
    overflow: visible;
}
.sidebar-block .block-title {
    font-size: 16px;
    font-weight: 700;
    color: var(--soft-text);
    margin: 0 0 16px 0;
    padding-bottom: 12px;
    border-bottom: 2px solid var(--soft-accent);
    display: flex;
    align-items: center;
    gap: 8px;
}
.sidebar-block .block-title i {
    color: var(--soft-accent);
}

/* Hot list */
.hot-list-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 8px 0;
    border-bottom: 1px solid var(--soft-border);
}
.hot-list-item:last-child {
    border-bottom: none;
}
.hot-avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 15px;
    font-weight: 700;
    color: #fff;
    flex-shrink: 0;
    text-transform: uppercase;
}
.hot-avatar-0 { background: linear-gradient(135deg, #6c5ce7, #a29bfe); }
.hot-avatar-1 { background: linear-gradient(135deg, #00b894, #55efc4); }
.hot-avatar-2 { background: linear-gradient(135deg, #e17055, #fab1a0); }
.hot-avatar-3 { background: linear-gradient(135deg, #0984e3, #74b9ff); }
.hot-avatar-4 { background: linear-gradient(135deg, #fd79a8, #e84393); }
.hot-avatar-5 { background: linear-gradient(135deg, #fdcb6e, #f39c12); }
.hot-avatar-6 { background: linear-gradient(135deg, #00cec9, #81ecec); }
.hot-avatar-7 { background: linear-gradient(135deg, #636e72, #b2bec3); }
.hot-list-item .hot-title {
    font-size: 13px;
    color: var(--soft-text-muted);
    text-decoration: none;
    line-height: 1.4;
    transition: color 0.2s;
}
.hot-list-item .hot-title:hover {
    color: var(--soft-accent);
}

/* Tag cloud */
.tag-cloud {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}
.tag-pill {
    display: inline-flex;
    align-items: center;
    gap: 5px;
    padding: 6px 14px;
    border-radius: 50px;
    font-size: 12px;
    font-weight: 500;
    text-decoration: none !important;
    background: var(--soft-page-bg);
    color: var(--soft-text-muted);
    border: 1px solid var(--soft-border);
    transition: all 0.25s ease;
}
.tag-pill:hover {
    background: var(--soft-accent);
    color: #fff !important;
    border-color: var(--soft-accent);
    text-decoration: none !important;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(69,208,198,0.25);
}
.tag-pill.active {
    background: var(--soft-accent);
    color: #fff !important;
    border-color: var(--soft-accent);
}
.tag-pill .tag-count {
    background: rgba(0,0,0,0.08);
    padding: 1px 7px;
    border-radius: 50px;
    font-size: 11px;
}
.tag-pill.active .tag-count {
    background: rgba(255,255,255,0.25);
}

/* Month list */
.month-list {
    list-style: none;
    padding: 0;
    margin: 0;
}
.month-list li {
    margin-bottom: 4px;
}
.month-list li a {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 14px;
    border-radius: var(--soft-radius-sm);
    font-size: 13px;
    color: var(--soft-text-muted);
    text-decoration: none !important;
    transition: all 0.2s;
}
.month-list li a:hover {
    background: var(--soft-page-bg);
    color: var(--soft-accent);
    text-decoration: none !important;
}
.month-list li.active a {
    background: var(--soft-accent);
    color: #fff !important;
    font-weight: 600;
}
.month-list li a i {
    font-size: 12px;
    opacity: 0.6;
}
.month-list li.active a i {
    opacity: 1;
}
</style>
</head>

<body>

<%@ include file="page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant center">Mac软件</h1>


<!-- 结构化数据 -->
<script type="application/ld+json">
{
  "@context": "https://schema.org",
  "@type": "CollectionPage",
  "name": "Mac软件下载",
  "description": "精品Mac破解软件资源下载",
  "url": "https://northpark.cn/soft/",
  "mainEntity": {
    "@type": "ItemList",
    "name": "Mac软件列表",
    "numberOfItems": "${fn:length(list)}"
  }
}
</script>

<div class="clearfix maincontent grayback">
    <div class="container">
        <div class="mainbody" style="margin-top:80px; ">

            <!-- 面包屑导航 -->
            <nav aria-label="breadcrumb" class="container">
                <ol class="breadcrumb" style="background-color: transparent;">
                    <li class="breadcrumb-item"><a href="/"><i class="fa fa-home"></i> 首页</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Mac软件</li>
                    <c:if test="${page!=null && page!=''}">
                        <li class="breadcrumb-item active" aria-current="page">第${page}页</li>
                    </c:if>
                </ol>
            </nav>


            <div class="row">
                <div class="col-md-8">
                    <div class="col-sm-12">

                        <div class="search-wrapper">
                        <form class="form-search" id="J_ser_from" method="post" accept-charset="UTF-8"
                              action="/soft/mac/page/1" onkeydown="if(event.keyCode==13){return false;}">
                            <input id="keyword" placeholder="搜索 Mac 精品软件..." value="${keyword }"
                                   class="width100 input-medium search-query input-lg   border-light-1 bg-lyellow  radius-0"
                                   name="keyword" type="text">
                            <input data-activetext="搜索 ››" class="btn btn-hero " value="搜索" type="button"
                                   id="J_ser_btn">
                        </form>
                        </div>

                    </div>
                    <c:forEach items="${list }" var="s" varStatus="ss">

                        <div class="col-sm-12">
                            <div class="soft-card" itemscope itemtype="http://schema.org/SoftwareApplication">
                                <c:if test="${s.hotIndex>0}">
                                    <div class="pin-icon"><i class="fa fa-thumb-tack"></i></div>
                                </c:if>
                                <h3 class="card-title">
                                    <a href="/soft/${s.retCode}.html" itemprop="name">${s.title}</a>
                                </h3>
                                <meta itemprop="operatingSystem" content="${s.os}">
                                <meta itemprop="applicationCategory" content="${s.tags}">
                                <div class="meta-chips">
                                    <a class="meta-chip" href="/soft/date/${s.postDate }" title="${s.postDate}">
                                        <i class="fa fa-calendar-o"></i> ${s.postDate}
                                    </a>
                                    <a class="meta-chip" href="/soft/tag/${s.tagsCode }" title="${s.tags}">
                                        <i class="fa fa-tag"></i> ${s.tags}
                                    </a>
                                    <a class="meta-chip" href="/soft/${s.os }" title="${s.os}">
                                        <i class="fa fa-apple"></i> ${s.os}
                                    </a>
                                </div>
                                <p class="card-brief" id="brief_${ss.index}" itemprop="description">
                                    ${s.brief }
                                </p>
                                <a class="btn-readmore" href="/soft/${s.retCode }.html">
                                    Read More <span class="arrow">&#8594;</span>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${pagein!='no' }">
                        <%@ include file="page/common/fenye.jsp" %>
                    </c:if>
                </div>
                <div class="col-md-4 ">


                    <!-- donate  -->
                        <!-- load donate list  -->
                        <!-- <%@ include file="page/common/donate.jsp" %> -->
                    <!-- donate  -->

                    <!-- hot  -->
                    <div class="sidebar-block sidebar clearfix">
                        <h4 class="block-title"><i class="fa fa-fire"></i> 热门</h4>
                        <c:forEach var="z" items="${hot_list }" varStatus="hs">
                            <div class="hot-list-item">
                                <div class="hot-avatar hot-avatar-${hs.index % 8}">
                                    ${ fn:toUpperCase(fn:substring(z.title ,0,1)) }
                                </div>
                                <a class="hot-title" href="/soft/${z.ret_code }.html" title="${z.title }">${z.title }</a>
                            </div>
                        </c:forEach>
                    </div>


                    <!-- tags  -->
                    <div class="sidebar-block sidebar clearfix">
                        <h4 class="block-title"><i class="fa fa-tags"></i> 标签</h4>
                        <div class="tag-cloud">
                            <c:forEach var="z" items="${soft_tags }">
                                <a class="tag-pill ${z.tags_code == sel_tag ? 'active' : ''}" href="/soft/tag/${z.tags_code }" title="${z.tags }">
                                    ${z.tags } <span class="tag-count">${z.num }</span>
                                </a>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- month  -->
                    <div class="sidebar-block sidebar clearfix">
                        <h4 class="block-title"><i class="fa fa-clock-o"></i> 月份</h4>
                        <ul class="month-list">
                            <c:forEach var="z" items="${date_list }">
                                <li class="${z.month == sel_month ? 'active' : ''}">
                                    <a href="/soft/month/${z.month}" title="${z.month }">
                                        <i class="fa fa-calendar"></i> ${z.month }
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>


        </div>
    </div>
</div>

<%@ include file="page/common/container.jsp" %>

<script type="text/javascript"  >
    //禁止图片拉伸
    $(function () {
        $("img").each(function () {
            $(this).css('max-width', $(".bg-white").width());
        })

        //搜索
        $("#J_ser_btn").click(function () {
            $("#J_ser_btn").attr('disabled', true);
            if ($("#keyword").val() && $("#keyword").val() != "${keyword }") {

                window.location.href = "/soft/mac/page/1?keyword=" + $("#keyword").val();
            }
            setTimeout("$('#J_ser_btn').removeAttr('disabled')", 5000); //设置5秒后提交按钮 显示
        })

        //ENTER事件
        $("body").keydown(function () {
            if (event.keyCode.toString() === "13"){
                $("#J_ser_btn").click();
            }
        });

        // mac_tips();
    })
</script>

<script type="text/javascript"  >
    /*  ##set query param */
    var keyword = $("#keyword").val();
    if (keyword) {
        $("#pageForm a").each(function () {
            var href = $(this).attr("href");
            $(this).attr("href", href + "?keyword=" + keyword);
        })
    }

    function mac_tips(){
            //右下角消息窗口
            art.dialog({
            time: 30,
            id: 'notice',
            title: '全站通知！',
            content:
                '<p>-macOS 12 系统正式版发布了，有些小伙伴升级后发现部分软件安装后不能使用。</p>' +
                '<p>-主要发生在 Intel CPU 的电脑上，这个目前没有解决方法，只能等待相关破解团队的跟进。</p>' +
                '<p>-正版基本上没有这个情况，条件允许可以去入正，反之暂不要升级到 12。</p>',
            width: 300, //设置窗口大小
            height: 200,
            left: '100%',
            top: '100%',
            fixed: true, //浮动窗口 不跟随滚动条移动
            lock: false,
            drag: true, //允许拖动
            resize: false //不能改变大小
        })
    }


</script>

<script>

    //loadDonates(1);

</script>
</body>
</html>
