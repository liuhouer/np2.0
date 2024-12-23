<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!-- saved from url=(0030)myself.jsp -->
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
    <title>${MyInfo.username}的个人空间 - 分享生活，记录精彩 | NorthPark</title>
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

    <!-- SEO优化 -->
    <meta name="description" content="${MyInfo.username}的个人主页 - 分享Ta的最爱、兴趣爱好以及生活点滴 | NorthPark">
    <meta name="keywords" content="${MyInfo.username},个人主页,兴趣爱好,生活分享,NorthPark">
    
    <!-- Open Graph tags -->
    <meta property="og:title" content="${MyInfo.username}的个人空间 | NorthPark">
    <meta property="og:description" content="欢迎来到${MyInfo.username}的个人空间,这里展示着Ta的兴趣爱好与生活分享">
    <meta property="og:type" content="profile">
    <meta property="og:url" content="https://northpark.cn/people/${MyInfo.tailSlug}">
    
    <!-- 结构化数据 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "ProfilePage",
      "mainEntity": {
        "@type": "Person",
        "name": "${MyInfo.username}",
        "url": "https://northpark.cn/people/${MyInfo.tailSlug}"
      }
    }
    </script>

</head>

<body style="">
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<%@ include file="/WEB-INF/views/page/common/centrespace.jsp" %>

<input type="hidden" id="J_uid" value="${ user.id}"/>

<input type="hidden" id="J_gz" value="${gz }"/>


<div class="clearfix maincontent">
    <div class="container">
        <div class="mainbody padding10" style="margin-top:2em;">

            <div class="clearfix margin-b20">
                <ul class="nav nav-tabs">
                    <li class="active"><a
                            <c:if test="${MyInfo.tailSlug==null || MyInfo.tailSlug==''}">
                                href="/cm/channel/${MyInfo.id}"
                            </c:if>
                            <c:if test="${MyInfo.tailSlug!=null }">
                                href="/people/${MyInfo.tailSlug}"
                            </c:if>

                    >最爱</a></li>
                    <li><a href="/note/viewNotes/${MyInfo.id}">留言</a></li>
                    <li><a href="/cm/fans/${MyInfo.id}">Fans</a></li>

                </ul>
            </div>
            <c:forEach items="${channelList }" var="s" varStatus="ss">
                <div class="row">
                    <div class="col-md-2">
                        <h3 class="label label-gray">${s.love_date }：</h3>
                    </div>
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col-xs-4 col-sm-2 center">
                                <a href="/love/${s.title_code}.html"
                                   title="${s.title }" class="thumbnail border-0"> <img
                                        src="/bruce/${s.album_img }" alt="${s.title }">
                                    <c:if test="${s.data_type=='创建数据'}">
                                        <i class="fa fa-copyright padding5"></i>
                                    </c:if>
                                    <c:if test="${s.data_type=='点赞数据'}">
                                        <i class="fa fa-heart padding5"></i>
                                    </c:if>
                                    <p>${s.title }</p>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <hr>
            </c:forEach>

        </div>
        <br>
        <br>

    </div>

</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>


<script src="https://northpark.cn/statics/js/page/space.js"></script>


</body>
</html>
