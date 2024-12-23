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
    <%@ include file="page/common/common.jsp" %>
    <c:if test="${page==null || page==''}">
        <title>Mac软件下载 | 精品Mac破解软件资源 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>Mac软件下载第${page}页 | 精品Mac破解软件资源 | NorthPark</title>
    </c:if>

    <meta name="keywords" content="NorthPark,Mac软件下载,Mac破解软件,精品Mac资源,macOS软件">
    <meta name="description"
          content="NorthPark提供海量精品Mac软件下载,包括办公软件、图像处理、视频剪辑等各类Mac破解软件资源,同时提供macOS使用技巧和教程">
</head>

<body>

<%@ include file="page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant center">Mac软件</h1>
<div class="clearfix maincontent grayback">
    <div class="container">
        <div class="mainbody" style="margin-top:100px; ">


            <div class="row">
                <div class="col-md-8">
                    <div class="col-sm-12">

                        <form class="form-search" id="J_ser_from" method="post" accept-charset="UTF-8"
                              action="/soft/mac/page/1" onkeydown="if(event.keyCode==13){return false;}">
                            <input id="keyword" placeholder="Mac软件板块上线啦~" value="${keyword }"
                                   class="width100 input-medium search-query input-lg   border-light-1 bg-lyellow  radius-0"
                                   name="keyword" type="text">
                            <input data-activetext="搜索 ››" class="btn btn-hero " value="搜索" type="button"
                                   id="J_ser_btn">
                        </form>

                    </div>
                    <c:forEach items="${list }" var="s" varStatus="ss">

                        <div class="col-sm-12">
                            <div class="clearfix bg-white margin-t10 margin-b10 padding20" itemscope itemtype="http://schema.org/SoftwareApplication">
                                <div class="row margin5">
                                    <div class="border-0 center">
                                        <p>
                                            <a href="/soft/${s.retCode}.html">
                                                <small class="green-text">
                                                    <font size="5">
                                                        <strong itemprop="name">
                                                            <c:if test="${s.hotIndex>0}">
                                                                <i class="fa fa-thumb-tack" title="已置顶"></i>
                                                            </c:if>
                                                            ${s.title}
                                                        </strong>
                                                    </font>
                                                </small>
                                            </a>
                                        </p>
                                        <meta itemprop="operatingSystem" content="${s.os}">
                                        <meta itemprop="applicationCategory" content="${s.tags}">


                                        <div class="clearfix visible-xs">
                                            <hr>
                                        </div>
                                    </div>

                                    <p>

                                        发表于：<strong><a class="common-a-right" title="${s.postDate}"
                                                       href="/soft/date/${s.postDate }">${s.postDate}</a></strong>

                                        <strong><a class="common-a-right" title="${s.tags}"
                                                   href="/soft/tag/${s.tagsCode }">${s.tags}</a></strong>

                                        <strong><a class="common-a-right" title="${s.os}"
                                                   href="/soft/${s.os }">${s.os}</a></strong>

                                            <%--  <a href="/soft/${s.retCode }.html#comment" class=" reply-count count-text common-a" data-thread-key="${s.retCode }" data-count-type="comments"></a> --%>
                                    </p>
                                    <p id="brief_${ss.index}">

                                            ${s.brief }
                                    </p>

                                    <p>
                                        <a class="btn btn-warning margin-t10" href="/soft/${s.retCode }.html">
                                            Read More
                                            <span class="glyphicon  glyphicon-circle-arrow-right padding5"></span>
                                        </a>
                                    </p>

                                </div>
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
                        <%@ include file="page/common/donate.jsp" %>
                    <!-- donate  -->

                    <!-- hot  -->
                    <div class="clearfix sidebar radius-5 ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-leaf margin5"></span> 热门</h4>
                        </div>
                        <c:forEach var="z" items="${hot_list }">

                            <div class="col-md-12 margin-t10">
                                <div class="col-xs-2 avatar">

                                    <span class="text-${ fn:toLowerCase(fn:substring( z.title ,0,1)) }">${ fn:toUpperCase(fn:substring(z.title ,0,1))   }</span>

                                </div>
                                <div class="col-xs-10">

                                    <a style="font-size: 14px;line-height: 32px;color: #888"
                                       href="/soft/${z.ret_code }.html" title="${z.title }">${z.title } </a>

                                </div>


                            </div>
                        </c:forEach>


                    </div>


                    <!-- tags  -->
                    <div class="clearfix sidebar radius-10 ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-tags margin5"></span> 标签</h4>
                        </div>
                        <c:forEach var="z" items="${soft_tags }">

                            <div class="col-md-10 margin5">
                                <c:if test="${z.tags_code == sel_tag }">
                                    <span class="glyphicon glyphicon-arrow-right margin5"></span>
                                    <a style="color: #45d0c6;" href="/soft/tag/${z.tags_code }"
                                       title="${z.tags }">${z.tags } </a>
                                </c:if>
                                <c:if test="${z.tags_code != sel_tag }">
                                    <span class="glyphicon glyphicon-tag margin5"></span>
                                    <a href="/soft/tag/${z.tags_code }" title="${z.tags }">${z.tags } </a>
                                </c:if>


                                <a style="color: #45d0c6;" href="/soft/tag/${z.tags_code }" title="">(${z.num }) </a>

                            </div>
                        </c:forEach>


                    </div>

                    <!-- month  -->
                    <div class="clearfix sidebar radius-10 ">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-time margin5"></span> 月份</h4>
                        </div>
                        <c:forEach var="z" items="${date_list }">

                            <div class="col-md-10 margin5">
                                <c:if test="${z.month == sel_month }">
                                    <span class="glyphicon glyphicon-arrow-right margin5"></span>
                                    <a style="color: #45d0c6;" href="/soft/month/${z.month}"
                                       title="${z.month }">${z.month } </a>
                                </c:if>
                                <c:if test="${z.month != sel_month }">
                                    <span class="glyphicon glyphicon-tree-conifer margin5"></span>
                                    <a href="/soft/month/${z.month}" title="${z.month }">${z.month } </a>
                                </c:if>


                            </div>
                        </c:forEach>


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

    loadDonates(1);

</script>
</body>
</html>
