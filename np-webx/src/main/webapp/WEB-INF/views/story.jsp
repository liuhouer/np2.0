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
    <link rel="shortcut icon" href="/static/img/favicon.ico">

    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

    <!-- 动态canonical链接 -->
    <c:if test="${page==null || page==''}">
        <link rel="canonical" href="https://northpark.cn/note/story" />
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <link rel="canonical" href="https://northpark.cn/note/story/page/${page}" />
    </c:if>
    <c:if test="${page==null || page==''}">
        <title>树洞心灵驿站 - 情感倾诉与心情分享社区 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>树洞心灵驿站第${page}页 - 情感倾诉与心情分享社区 | NorthPark</title>
    </c:if>

    <meta name="keywords" content="树洞,心灵驿站,情感倾诉,心情分享,情感交流,心理疏导,倾诉空间,匿名分享,心情日记,情感支持,NorthPark树洞">
    <meta name="description" content="NorthPark树洞心灵驿站 - 温暖的情感倾诉社区，为您提供安全的心情分享空间。在这里可以匿名倾诉心声、记录生活感悟、获得情感支持，与理解你的朋友交流互动。一个充满人文关怀的心灵港湾。">

    <meta property="og:title" content="<c:if test='${page==null || page==\'\'}'>树洞心灵驿站 - 情感倾诉与心情分享社区</c:if><c:if test='${page!=null && page!=\'\'}'>树洞心灵驿站第${page}页 - 情感倾诉与心情分享社区</c:if>">
    <meta property="og:description" content="温暖的情感倾诉社区，为您提供安全的心情分享空间。匿名倾诉心声、记录生活感悟、获得情感支持">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://northpark.cn/note/story">
    <meta property="og:image" content="https://minioapi.northpark.cn/pic/treehole-banner.jpg">

    <!-- Twitter Card 标签 -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="NorthPark树洞心灵驿站">
    <meta name="twitter:description" content="温暖的情感倾诉社区，安全的心情分享空间，匿名倾诉获得情感支持">
    <meta name="twitter:image" content="https://minioapi.northpark.cn/pic/treehole-banner.jpg">
    <link rel="stylesheet" href="/static/css/story.css">

    <!-- 结构化数据 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "CollectionPage",
      "name": "NorthPark树洞心灵驿站",
      "description": "温暖的情感倾诉社区，为您提供安全的心情分享空间",
      "url": "https://northpark.cn/note/story",
      "mainEntity": {
        "@type": "ItemList",
        "name": "心情分享列表",
        "description": "匿名倾诉心声、记录生活感悟、获得情感支持"
      },
      "publisher": {
        "@type": "Organization",
        "name": "NorthPark",
        "logo": {
          "@type": "ImageObject",
          "url": "/static/img/logo.png"
        }
      }
    }
    </script>
</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">树洞-心灵驿站</h1>
<div class="clearfix maincontent grayback">
    <div class="container">
        <div class="mainbody padding-t20" id="J_maincontent" style="margin-top:70px;">


            <div id="J_progress" class="center padding-t20"></div>


        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<script src="/static/js/page/story.js"></script>
</body>
</html>
