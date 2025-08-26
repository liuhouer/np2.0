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
    <meta name="theme-color" content="#45d0c6">
    <meta name="format-detection" content="telephone=no">
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    <%@ include file="page/common/common.jsp" %>
    <!-- 动态canonical链接 -->
    <c:if test="${page==null || page==''}">
        <link rel="canonical" href="https://northpark.cn/love/" />
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <link rel="canonical" href="https://northpark.cn/love/page/${page}" />
    </c:if>

    <!-- Open Graph 标签 -->
    <meta property="og:title" content="<c:if test='${page==null || page==\'\'}'>最爱主题 - 生活美学分享社区</c:if><c:if test='${page!=null && page!=\'\'}'>最爱主题第${page}页 - 生活美学分享社区</c:if>">
    <meta property="og:description" content="专注生活美学的分享社区，记录并分享生活中的美好瞬间。发现有趣的主题图片，与文艺青年互动交流">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://northpark.cn/love/">
    <meta property="og:image" content="https://northpark.cn/statics/img/love-banner.jpg">

    <!-- Twitter Card 标签 -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="NorthPark最爱主题 - 生活美学分享">
    <meta name="twitter:description" content="记录美好瞬间，分享生活美学，与文艺青年互动交流">
    <meta name="twitter:image" content="https://northpark.cn/statics/img/love-banner.jpg">

    <c:if test="${page==null || page==''}">
        <title>最爱主题 - 生活美学分享社区 | 记录美好瞬间 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>最爱主题第${page}页 - 生活美学分享社区 | 记录美好瞬间 | NorthPark</title>
    </c:if>
    <meta name="keywords" content="最爱主题,生活美学,图片分享,美好瞬间,生活记录,文艺分享,创意生活,生活灵感,美学社区,NorthPark最爱">
    <meta name="description" content="NorthPark最爱主题 - 专注生活美学的分享社区，记录并分享生活中的美好瞬间。发现有趣的主题图片，与文艺青年互动交流，用镜头捕捉生活之美，用文字记录心灵感悟，打造属于你的生活美学空间。">

    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "CollectionPage",
      "name": "NorthPark最爱主题",
      "description": "专注生活美学的分享社区，记录并分享生活中的美好瞬间",
      "url": "https://northpark.cn/love/",
      "mainEntity": {
        "@type": "ItemList",
        "name": "生活美学主题列表",
        "description": "发现有趣的主题图片，与文艺青年互动交流"
      },
      "publisher": {
        "@type": "Organization",
        "name": "NorthPark",
        "logo": {
          "@type": "ImageObject",
          "url": "https://northpark.cn/statics/img/logo.png"
        }
      }
    }
    </script>

</head>

<body>
<%@ include file="page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">最爱-主题</h1>
<div class="clearfix maincontent grayback" id="J_maincontent">


    <div class="container grayback" id="J_container">
        <div class="mainbody padding-t20" style="margin-top:70px;">
            <div id="J_progress" class="center padding-t20"></div>
        </div>


    </div>


</div>
<%@ include file="page/common/fenye.jsp" %>
<%@ include file="page/common/container.jsp" %>


<script src="/static/js/page/welcome.js"></script>
</body>

</html>
