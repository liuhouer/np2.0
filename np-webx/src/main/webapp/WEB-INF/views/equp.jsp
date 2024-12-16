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
        <title>情商提升 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>情商提升日记::第${page}页 | NorthPark</title>
    </c:if>
    <meta name="keywords" content="NorthPark,情商提升,恋爱技巧,两性关系,社交技巧,个人成长,情感咨询">
    <meta name="description"
          content="NorthPark情商提升频道,提供专业的情商培训、恋爱技巧指导、两性关系咨询等服务,帮助你提升社交能力,建立良好的人际关系。">
</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>


<!-- 页面标题 -->
<h1 class="font-elegant">情商提升</h1>
<div class="clearfix maincontent grayback">
    <div class="container">

        <div class="mainbody" id="J_maincontent">


            <div class="row">
                <div class="col-sm-8 margin-b10">
                    <form class=" form-search " action="/movies/search" method="post" accept-charset="UTF-8">
                        <input id="keyword" placeholder="约不出来怎么办" value="${keyword }"
                               class="width100 input-medium search-query input-lg  border-light-1 bg-lyellow  radius-0"
                               name="keyword" type="text">
                        <input data-activetext="搜索 ››" id="J_search" class=" btn btn-hero " value="搜索" type="button">
                    </form>
                </div>

                <div id="J_progress" class="center padding-t20"></div>

                <c:forEach items="${list }" var="s" varStatus="ss">
                    <div class="col-sm-6">
                        <div class="clearfix bg-white margin-t10 margin-b10 padding20 article-box">
                            <div class="row">
                                <div class="col-sm-4">
                                    <div class="thumbnail border-0 center">
                                        <a title="${s.title}">
                                            <c:if test="${s.img==null ||s.img==''}">
                                                <img src="https://northpark.cn/statics/img/davatar.jpg" alt="${s.title}" class="imgbreath article-img">
                                            </c:if>
                                            <c:if test="${s.img!=null }">
                                                <img src="${s.img }" alt="${s.title}" class="imgbreath article-img">
                                            </c:if>
                                        </a>
                                        <p><label class="bold-text article-title" title="${s.title}">${s.title}</label></p>
                                    </div>
                                </div>

                                <div class="col-sm-8">
                                    <p>
                                        <small class="label label-gray">${s.date }</small>
                                        <a href="/romeo/${s.id }.html#comment">
                                            <small class="label label-gray"><span class="glyphicon glyphicon-comment margin5"></span></small>
                                        </a>
                                        <br> <br>
                                    </p>
                                    <div id="brief_${ss.index}" class="note-brief article-brief">
                                        <c:if test="${!fn:startsWith(s.brief, '<')}">
                                            <p></p>
                                            <p>
                                        </c:if>
                                        ${s.brief }
                                        <c:if test="${!fn:endsWith(s.brief, '>')}">
                                            </p>
                                        </c:if>
                                    </div>
                                    <div class="clearfix hidden" id="text_${ss.index}">
                                        ${s.article }
                                    </div>

                                    <c:if test="${fn:length(s.brief) > 200}">
                                        <button class="clearfix btn btn-gray btn-xs click2show"
                                                data-dismiss="#brief_${ss.index}" data-target="#text_${ss.index}">
                                            &nbsp; <span class="glyphicon glyphicon-chevron-down"></span> &nbsp;
                                        </button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>


            </div>


        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>
<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<script src="https://northpark.cn/statics/js/page/eq.js"></script>

<style>
.article-box {
    min-height: 300px;
    position: relative;
    margin-bottom: 20px;
}

.article-img {
    max-height: 150px;
    width: auto;
    object-fit: cover;
}

.article-title {
    font-size: 16px;
    margin: 10px 0;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.article-brief {
    min-height: 120px;
    max-height: 200px;
    overflow: hidden;
    position: relative;
    margin-bottom: 30px;
}

.article-brief:before {
    content: '';
    width: 100%;
    height: 40px;
    position: absolute;
    bottom: 0;
    left: 0;
    background: linear-gradient(transparent, #fff);
    display: none;
}

.article-brief.truncated:before {
    display: block;
}

.click2show {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
}
</style>

<script>
$(function() {
    // 检查文章简介是否需要截断
    $('.article-brief').each(function() {
        if($(this).prop('scrollHeight') > $(this).height()) {
            $(this).addClass('truncated');
        }
    });
});
</script>

</body>
</html>
