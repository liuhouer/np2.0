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
        <title>树洞-心灵驿站 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>树洞-心灵驿站::第${page}页 | NorthPark</title>
    </c:if>


    <meta name="keywords" content="NorthPark,树洞,心情分享,情感交流,心灵驿站,倾诉空间">
    <meta name="description" content="NorthPark树洞是一个让人倾诉心声的温暖空间。在这里,你可以分享生活点滴、记录心情感悟,与志同道合的朋友交流互动。一个充满人文关怀、富有交互性和趣味性的心灵驿站。">

    <meta property="og:title" content="树洞-心灵驿站 | NorthPark">
    <meta property="og:description" content="NorthPark树洞是一个让人倾诉心声的温暖空间。在这里,你可以分享生活点滴、记录心情感悟。">
    <meta property="og:image" content="https://northpark.cn/statics/img/tree-hole-banner.jpg">
    <meta property="og:url" content="https://northpark.cn/note/story">
    <link rel="stylesheet" href="/static/css/story.css">
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
