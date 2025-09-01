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
    <link rel="shortcut icon" href="/static/img/favicon.ico">
    <title>${dataMap.lrc_title} - 最爱主题详情 | NorthPark</title>
    <meta name="keywords" content="${dataMap.lrc_title},${dataMap.by_username},最爱主题,NorthPark">
    <meta name="description" content="查看${dataMap.by_username}分享的${dataMap.lrc_title}。NorthPark最爱主题让你记录生活点滴,分享美好回忆。">
    <link href="/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
    <!-- Quill.js 富文本编辑器 -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    
    <style>
        #comment-editor {
            height: 150px;
        }
        .ql-editor {
            min-height: 120px;
        }
    </style>


</head>

<body style="">


<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant">${ dataMap.lrc_title}</h1>
<div class="clearfix maincontent">
    <div class="container">

        <div class="mainbody padding10" style="margin-top:5em;">
            <input type="hidden" id="J_lrcid" value="${ dataMap.lrc_id}"/>
            <input type="hidden" id="J_uid" value="${ user.id}"/>
            <input type="hidden" id="J_yizan" value="${yizan }"/>
            <div class="row">
                <div class="col-md-8">

                    <h2 class="margin0">${ dataMap.lrc_title} &nbsp;
                        <small><span class="glyphicon glyphicon-user"></span> 由<a

                                <c:if test="${dataMap.by_tail_slug==null || dataMap.by_tail_slug==''}">
                                    href="/cm/channel/${dataMap.by_id}"
                                </c:if>
                                <c:if test="${dataMap.by_tail_slug!=null }">
                                    href="/people/${dataMap.by_tail_slug }"
                                </c:if>

                                title="${dataMap.by_username }的最爱">${dataMap.by_username }</a>创建
                        </small>
                    </h2>

                    <hr>

                    <div class="row">
                        <div class="col-sm-7 ">

                            <div class="clearfix" style="position:relative">
                                <div class="clearfix" id="mainThumb"><img class="img-responsive img-full"
                                                                          src="/bruce/${dataMap.lrc_album_img }"
                                                                          alt="${ dataMap.lrc_title}"></div>

                            </div>


                        </div>




                        <div class="col-sm-5">



                            <div class="clearfix">
                                <h4><span class="glyphicon glyphicon-heart"></span> ${dataMap.zanNum }人最爱</h4>
                                <p class="pline">

                                <div id="J_zan_div" class="pline">
                                    <c:forEach var="x" varStatus="xx" items="${zanList }">
							    	<span><a
                                            <c:if test="${x.tailSlug==null || x.tailSlug==''}">
                                                href="/cm/channel/${x.id}"
                                            </c:if>
									    <c:if test="${x.tailSlug!=null }">
                                            href="/people/${x.tailSlug }"
                                        </c:if>

                                            title="${x.username }">${x.username }</a> &nbsp;</span>

                                    </c:forEach>
                                </div>
                                <!-- >10个喜欢才有查看更多按钮 -->
                                <c:if test="${dataMap.zanNum > 10}">
                                    <button id="J_lovers_box" class="btn btn-gray btn-xs click2show"
                                            data-target=".lovers_box">查看更多 ››
                                    </button>
                                </c:if>
                                </p>
                            </div>

                            <input type="hidden" id="by_id" value="${dataMap.by_id }"/>

                            <div id="showResult" class="control-group center margen-b10">
                            </div>

                            <!-- 隐藏组件 -->
                            <div class="form-group clearfix  hidden" id="loveBox" >

                                <p>
                                    <span class="bold-text">设置爱上时间</span>
                                </p>
                                <p>
                                    <input id="loveDate" placeholder="1995-06-06"
                                           class="form_datetime form-inline border-light-1  bg-lyellow  grid50 radius-0"
                                           name="loveDate" type="text" >
                                    <button data-dismiss="#loveBox"
                                            id="J_gz_btn"
                                            title="加入我的最爱"
                                            class="btn btn-hero form-inline" style="vertical-align: bottom;padding: 2px 10px;">
                                        <i class="fa fa-floppy-o"></i> 爱上</button>
                                </p>

                            </div>
                            <!-- 隐藏组件 -->

                            <h2>
                                <%--id="J_gz_btn"--%>
                                <button class="btn btn-warning btn-xlg click2love"
                                        title="加入我的最爱 展开/收起"
                                        data-target="#loveBox">
                                    <span class="glyphicon glyphicon-heart"></span>
                                    <c:if test="${yizan eq 'yizan' }">已爱上~</c:if>
                                    <c:if test="${yizan ne 'yizan' }">加入我的最爱 </c:if>
                                </button>
                            </h2>

                        </div>
                    </div>
                    <div class="clearfix margin-t20">

                        <h4><span class="glyphicon glyphicon-comment"></span> ${dataMap.plNum }条回忆</h4>
                        <hr>
                        <%--发表评论--%>
                        <c:if test="${user!=null }">
                            <div class="row">
                                <div class="col-xs-3 col-sm-2">
                                    <a
                                            <c:if test="${user.tailSlug==null || user.tailSlug==''}">
                                                href="/cm/channel/${user.id}"
                                            </c:if>
                                            <c:if test="${user.tailSlug!=null }">
                                                href="/people/${user.tailSlug }"
                                            </c:if>

                                            title="${user.username }的最爱"><img
                                            <c:if test="${user.headPath == null}">src="/static/img/davatar.jpg"</c:if>
                                        <c:if test="${user.headPath != null}">
                                    <c:choose>
                                            <c:when test="${fn:contains(user.headPath  ,'http://') }">src="${user.headPath  }"</c:when>
                                            <c:otherwise>src="/bruce/${user.headPath  }"</c:otherwise>
                                    </c:choose>

                                    </c:if> class="img-responsive  img-circle max-width-60" alt="${user.username }的最爱"></a>
                                </div>
                                <div class="col-xs-9 col-sm-10">
                                    <div class="form-group">
                                        <div id="comment-editor"></div>
                                        <textarea id="J_comment" name="comment" style="display:none;"></textarea>
                                    </div>
                                    <div class="form-group text-right">
                                        <input class="btn btn-info btn-md" type="button" id="J_commentBtn" value="+ 发布">
                                    </div>

                                    <hr>
                                </div>
                            </div>
                        </c:if>
                        <%--发表评论--%>


                        <%--评论列表--%>
                        <div class="clearfix" id="stuffCommentBox">

                        </div>

                        <div class="row center">


                            <div class="row margin-b20" id="loadingAnimation">
                                <img alt="load comment of ${ dataMap.lrc_title}" src="/static/img/loading.gif" width="30"
                                     height="30" />
                            </div>
                            <button class="btn btn-default btn-lg margin-b20" id="loadStuffCommentBtn"
                                    data-htmlboxid="stuffCommentBox" rel="938">加载更多 <span
                                    class="glyphicon glyphicon-chevron-down"></span></button>
                            <input type="hidden" id="comment_id_from" value="1">
                            <input type="hidden" id="J_lrc_id" value="${dataMap.lrc_id }">
                            <input type="hidden" id="J_tail" value="${tail }">
                        </div>
                        <%--评论列表--%>


                    </div>

                </div>

                <%--右侧广场--%>
                <div class="col-md-4">
                    <div class="clearfix sidebar radius-5">
                        <div class="clearfix border-bottom">
                            <h4><span class=" glyphicon glyphicon-th-large margin-b20"></span> 随便看看</h4>
                        </div>
                        <c:forEach var="z" items="${loveList }"> <%-- map --%>
                            <div class="row padding10">
                                <div class="col-xs-2 avatar">
                                    <c:if test="${z.head_path == null}">
                                        <span class="${z.head_span_class }">${z.head_span }</span>
                                    </c:if>
                                    <c:if test="${z.head_path != null}">
                                        <img alt=""
                                        <c:choose>
                                             <c:when test="${fn:contains(z.head_path ,'http://') }">src="${z.head_path }"</c:when>
                                             <c:otherwise>src="/bruce/${z.head_path }"</c:otherwise>
                                        </c:choose>
                                        >
                                    </c:if>

                                </div>
                                <div class="col-xs-10" style="line-height:30px;">
                                    <a


                                            <c:if test="${z.tail_slug==null || z.tail_slug==''}">
                                                href="/cm/channel/${z.userid}"
                                            </c:if>
                                            <c:if test="${z.tail_slug!=null }">
                                                href="/people/${z.tail_slug }"
                                            </c:if>


                                            title="${z.username }的最爱">${z.username }</a> 爱上了 <a style="color: #45d0c6"
                                                                                                title="${z.title }"
                                                                                                href="/love/${z.title_code }.html">${z.title }</a>
                                </div>
                            </div>
                        </c:forEach>

                        <p class="white-line margin0"></p>
                    </div>

                </div>
                <%--右侧广场--%>
            </div>


        </div>


    </div>
</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/jquery.min.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
<script src="/static/js/bootstrap-datetimepicker.js"></script>
<script src="/static/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script>
// 初始化 Quill 编辑器
var commentQuill = new Quill('#comment-editor', {
    theme: 'snow',
    modules: {
        toolbar: [
            ['bold', 'italic', 'underline'],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            ['link', 'blockquote'],
            ['clean']
        ]
    },
    placeholder: '分享你的回忆...'
});

// 评论提交功能
$("#J_commentBtn").click(function () {
    var content = commentQuill.root.innerHTML;
    var comment = commentQuill.getText().trim();
    
    if (comment) {
        // 同步内容到隐藏的textarea
        $('#J_comment').val(content);
        
        var lrcid = $("#J_lrcid").val();
        var uid = $("#J_uid").val();
        
        $.ajax({
            url: "/zanAction/addComment",
            type: "post",
            dataType: "json",
            data: {"lyricsid": lrcid, "userid": uid, "comment": content},
            success: function (msg) {
                if (msg.data == "success") {
                    // 清空编辑器
                    commentQuill.setContents([]);
                    alert('评论成功');
                    window.location.href = window.location.href;
                } else {
                    alert(msg.result || '评论失败');
                }
            }
        });
    } else {
        alert("请输入评论内容");
    }
});

// 原 zancmt.js 中的其他功能
var lrcid = $("#J_lrcid").val();
var uid = $("#J_uid").val();
var yizan = $("#J_yizan").val();

//获取所有点赞人填充
$("#J_lovers_box").click(function () {
    // 隐藏按钮
    $(this).hide();

    $.ajax({
        url: "/lyrics/getMoreZan",
        type: "post",
        data: {"lyricsid": lrcid},
        success: function (data) {
            //填充结果
            $("#J_zan_div").empty().prepend(data.replace(/(^")|("$)/g, ''));
        }
    });
});

//添加到最爱
$("#J_gz_btn").click(function () {
    //把userid的判断转为后台判断
    let uri = window.location.href;
    if (yizan == 'yizan') {
        return ;
    }

    let loveDate = $("#loveDate").val();
    if(!loveDate){
        alert("完善爱上时间");
        return ;
    }
    $.ajax({
        url: "/cm/loginFlag",
        type: "get",
        dataType: "json",
        success: function (msg) {
            if (msg.data == "1") {//已登录
                var userid = uid;
                $.ajax({
                    url: "/zanAction/zan",
                    type: "post",
                    dataType: "json",
                    data: {"lyricsid": lrcid, "userid": userid,"loveDate": loveDate},
                    beforeSend: beforeSendZAN, //发送请求
                    complete: completeZAN,
                    success: function (data) {
                        if (data.data == "success") {
                            alert("已爱上~");
                            window.location.href = window.location.href;
                        }
                    }
                });
            } else if (msg.data == "0") {//没有登录
                window.location.href = "/login?redirectURI=" + uri;
            }
        }
    });
});

//load comment
loadcmt();

//加载更多
$("#loadStuffCommentBtn").click(function () {
    var pagenow = $("#comment_id_from").val();
    $("#comment_id_from").val(parseInt(pagenow) + 1);
    loadcmt();
});

//load data...
function loadcmt() {
    var pagenow = $("#comment_id_from").val();
    var lrcid = $("#J_lrc_id").val();
    $.ajax({
        url: "/lyrics/commentQuery",
        type: "post",
        data: {"currentPage": pagenow, "lrcid": lrcid},
        beforeSend: beforeSend, //发送请求
        complete: complete,
        success: function (data) {
            if (data) {
                $("#stuffCommentBox").append(data);
                var tail = $("#J_tail").val();
                if (tail == 'tail') {
                    $("#loadStuffCommentBtn").remove();
                }
            }
        }
    });
}

function beforeSend(XMLHttpRequest) {
    $("#loadingAnimation").show();
}

function complete(XMLHttpRequest, textStatus) {
    $("#loadingAnimation").hide();
}

function beforeSendZAN(XMLHttpRequest) {
    $("#showResult").append("<div><img src='/static/img/loading.gif' style='width:32px;height:32px;' /></div>");
}

function completeZAN(XMLHttpRequest, textStatus) {
    $("#showResult").empty();
}
</script>

<script>
    $(function () {
        $('.form_datetime').datetimepicker({
            language:'zh-CN',
            format:'yyyy-mm-dd',
            dateFormat: 'yyyy-mm-dd',
            minView: "month",//选择日期后，不会再跳转去选择时分秒
            autoclose:true
        })

        $(".click2love").click(function (){
            //1
            if (yizan == 'yizan') {
                return ;
            }
            //2
            let cl = $(this);
            let uri = window.location.href;
            //日期控件
            let love_date_id = $(this).data('target');

            $.ajax({
                url: "/cm/loginFlag",
                type: "get",
                dataType: "json",
                success: function (msg) {
                    if (msg.data == "1") {//已登录

                        //3
                        // 点击添加最爱按钮,展开
                        let display = $(love_date_id).css("display");
                        if (display == 'none') {
                            $(love_date_id).removeClass("hidden").css("display", "block");
                            $(cl).find('span').addClass("glyphicon-chevron-up").removeClass("glyphicon-heart");
                        } else if (display == 'block') {
                            $(love_date_id).addClass("hidden").css("display", "none");
                            $(cl).find('span').addClass("glyphicon-heart").removeClass("glyphicon-chevron-up");
                        }



                    } else if (msg.data == "0") {//没有登录

                        window.location.href = "/login?redirectURI=" + uri;
                    }

                }
            });

        })
    })

</script>

<script type="application/ld+json">
{
  "@context": "https://schema.org",
  "@type": "ImageObject",
  "name": "${dataMap.lrc_title}",
  "author": {
    "@type": "Person",
    "name": "${dataMap.by_username}"
  },
  "description": "${dataMap.lrc_title}",
  "contentUrl": "/bruce/${dataMap.lrc_album_img}",
  "datePublished": "${dataMap.create_time}",
  "interactionStatistic": [
    {
      "@type": "InteractionCounter",
      "interactionType": "https://schema.org/LikeAction",
      "userInteractionCount": "${dataMap.zanNum}"
    },
    {
      "@type": "InteractionCounter",
      "interactionType": "https://schema.org/CommentAction",
      "userInteractionCount": "${dataMap.plNum}"
    }
  ]
}
</script>

</body>
</html>
