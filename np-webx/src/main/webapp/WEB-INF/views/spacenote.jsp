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
    <title>${MyInfo.username}的树洞 - 心情随笔与生活感悟 | NorthPark</title>
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

    <!-- SEO优化 -->
    <meta name="description" content="${MyInfo.username}的树洞空间 - 查看Ta的心情随笔与生活感悟 | NorthPark">
    <meta name="keywords" content="${MyInfo.username},树洞,心情随笔,生活感悟,留言板,NorthPark">
    
    <!-- Open Graph tags -->
    <meta property="og:title" content="${MyInfo.username}的树洞 | NorthPark">
    <meta property="og:description" content="探索${MyInfo.username}的内心世界,分享Ta的心情故事">
    <meta property="og:type" content="article">
    <meta property="og:url" content="https://northpark.cn/note/viewNotes/${MyInfo.id}">
    
    <!-- 结构化数据 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "BlogPosting",
      "mainEntityOfPage": {
        "@type": "WebPage",
        "@id": "https://northpark.cn/note/viewNotes/${MyInfo.id}"
      },
      "author": {
        "@type": "Person",
        "name": "${MyInfo.username}"
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
                    <li><a


                            <c:if test="${MyInfo.tailSlug==null || MyInfo.tailSlug==''}">
                                href="/cm/channel/${MyInfo.id}"
                            </c:if>
                            <c:if test="${MyInfo.tailSlug!=null }">
                                href="/people/${MyInfo.tailSlug}"
                            </c:if>

                    >最爱</a></li>
                    <li class="active"><a href="/note/viewNotes/${MyInfo.id}">留言</a></li>
                    <li><a href="/cm/fans/${MyInfo.id}">Fans</a></li>


                </ul>
            </div>
            <form id="f2" method="post"><input name="userid" type="hidden" value="${MyInfo.id }"/></form>
            <div class="row bg-white margin-t10 margin-b10  ">
                <div class="col-sm-1 avatar">
                    <a

                            <c:if test="${MyInfo.tailSlug==null || MyInfo.tailSlug==''}">
                                href="/cm/channel/${MyInfo.id}"
                            </c:if>
                            <c:if test="${MyInfo.tailSlug!=null }">
                                href="/people/${MyInfo.tailSlug}"
                            </c:if>

                            title="${MyInfo.username}的最爱">
                        <c:if test="${MyInfo.headPath==null }">
                            <span class=" ${MyInfo.headSpanClass }"
                                  alt="${s.get('username')}">${MyInfo.headSpan }</span>

                        </c:if>
                        <c:if test="${MyInfo.headPath!=null }">
                            <img alt="${MyInfo.username }的最爱"
                            <c:choose>
                                 <c:when test="${fn:contains(MyInfo.headPath ,'http://') }">src="${MyInfo.headPath}"</c:when>
                                 <c:otherwise>src="/bruce/${MyInfo.headPath }"</c:otherwise>
                            </c:choose>

                            >
                        </c:if>
                        <!-- <img src="img/davatar.jpg" class="img-responsive  img-circle max-width-50" alt="654714226的最爱"> -->
                    </a>
                </div>
            </div>

            <c:forEach items="${list }" var="s" varStatus="ss"> <%--bean--%>

                <div class='row bg-white margin-t10 margin-b10' id='notebox_${s.id }'>
                    <div class='col-sm-1'>
                        <small class='label label-gray'>${s.createTime }</small>
                    </div>
                    <div class='col-sm-11'>
                            <%-- <label class='btn btn-gray btn-xs pull-right delNoteBtn1' rel='${s.id }' onclick="removes(this)"><span class='glyphicon glyphicon-remove'></span></label> --%>
                        <p>${s.note }</p>
                        <hr/>
                    </div>

                </div>
            </c:forEach>


        </div>

        <div class="row center">
        </div>
        <br>
        <br>

    </div>

    <%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>


<script src="https://northpark.cn/statics/js/page/spacenote.js"></script>


</body>
</html>
