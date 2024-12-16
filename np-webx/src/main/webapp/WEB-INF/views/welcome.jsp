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
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    <%@ include file="page/common/common.jsp" %>
    <c:if test="${page==null || page==''}">
        <title>最爱主题 - 分享生活中的美好瞬间 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>最爱主题第${page}页 - 分享生活中的美好瞬间 | NorthPark</title>
    </c:if>
    <meta name="keywords" content="最爱主题,生活记录,图片分享,美好瞬间,NorthPark">
    <meta name="description" content="NorthPark最爱主题,记录并分享生活中的美好瞬间。在这里,你可以找到有趣的主题图片,与志同道合的朋友互动交流,留下生活的精彩印记。">

    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "CollectionPage",
      "name": "最爱主题",
      "description": "NorthPark最爱主题,记录并分享生活中的美好瞬间",
      "publisher": {
        "@type": "Organization",
        "name": "NorthPark"
      }
    }
    </script>

</head>

<body>
<%@ include file="page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">最爱-主题图册</h1>
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
