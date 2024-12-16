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
        <title>个人消息通知中心 - 评论回复/点赞/关注动态 | NorthPark</title>
    </c:if>
    <c:if test="${page!=null && page!=''}">
        <title>个人消息通知中心::第${page}页 - 评论回复/点赞/关注动态 | NorthPark</title>
    </c:if>

    <meta name="keywords" content="NorthPark通知系统,消息提醒,评论回复,点赞通知,关注动态,站内消息">
    <meta name="description" content="NorthPark个人消息通知中心,及时获取评论回复、点赞、新增关注、站内消息等动态提醒,随时掌握互动最新状态。">
    <meta property="og:title" content="个人消息通知中心 | NorthPark">
    <meta property="og:description" content="NorthPark个人消息通知中心,及时获取评论回复、点赞、新增关注、站内消息等动态提醒">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://northpark.cn/notifications">
</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<!-- 页面标题 -->
<h1 class="font-elegant center" style="margin:30px 0;color:#2c3e50;">
    <i class="fa fa-bell"></i> 消息通知中心
</h1>
<div class="clearfix maincontent grayback">
    <div class="container">
        <div class="mainbody" style="margin-top:100px; ">


            <div class="row padding20">
                <div class="btn-group" role="group" aria-label="消息类型">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="1" type="button" value="文章评论">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="2" type="button" value="收到点赞">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="3" type="button" value="树洞回复">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="4" type="button" value="新增关注">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="5" type="button" value="站内消息">
                    <input class="btn tag-node" style="margin:0 5px;border-radius:15px;" oid="6" type="button" value="资源动态">
                </div>
            </div>


            <div class="row">
                <div class="col-sm-7">
                    <c:forEach var="y" items="${list }" varStatus="ss">
                    <%--remindID--%>
                        <div class="row bg-white padding20">

                            <div class="col-sm-10 avatar">

                                <%-- 1类：在某文章界面评论被回复【通知-被回复人】【通知-站长】--%>
                                <c:if test="${y.remindId==1}">
                                        <p>
                                        <i class="fa fa-comments-o text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                            <a href="/cm/channel/${y.senderId}" title="${y.senderName }">
                                                    ${y.senderName }
                                            </a>
                                            <label class="padding5">在回复</label>
                                            <a href="${y.objectLinks}" style="font-size: 12px;line-height: 1.67;font-weight: 400;letter-spacing: normal;">${y.object}</a>
                                            <label class="padding5">时提到了你</label>
                                            <c:if test="${y.status==0}">
                                                <i class="fa fa-bell-o padding5" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </c:if>
                                        </p>
                                        <blockquote>
                                            <p style="font-size: 12px;line-height: 1.67;font-weight: 400;letter-spacing: normal;">
                                                    ${y.message }
                                            </p>
                                        </blockquote>

                                </c:if>

                                <%--留言--%>
                                <c:if test="${y.remindId==3}">
                                    <p>
                                        <i class="fa fa-commenting-o text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                        <a href="/cm/channel/${y.senderId}" title="${y.senderName }">
                                                ${y.senderName }
                                        </a>
                                        <label class="padding5">在回复</label>
                                        <a href="${y.objectLinks}" style="font-size: 12px;line-height: 1.67;font-weight: 400;letter-spacing: normal;">${y.object}</a>
                                        <label class="padding5">时提到了你</label>
                                        <c:if test="${y.status==0}">
                                            <i class="fa fa-bell-o padding5" title="未读"></i>
                                            <input type="hidden" name="unReadId" value="${y.id}">
                                        </c:if>
                                    </p>
                                    <blockquote>
                                        <p style="font-size: 12px;line-height: 1.67;font-weight: 400;letter-spacing: normal;">
                                                ${y.message }
                                        </p>
                                    </blockquote>

                                </c:if>

                                <%--最爱--%>
                                <c:if test="${y.remindId==2}">
                                    <p>
                                        <i class="fa fa-heart-o text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                        <a href="/cm/channel/${y.senderId}" title="${y.senderName }">
                                                ${y.senderName }
                                        </a>
                                        <label class="padding5">${y.message }:</label>
                                        <a href="${y.objectLinks}">${y.object}</a>
                                        <c:if test="${y.status==0}">
                                            <i class="fa fa-bell-o padding5" title="未读"></i>
                                            <input type="hidden" name="unReadId" value="${y.id}">
                                        </c:if>
                                    </p>
                                </c:if>

                                <%--关注--%>
                                <c:if test="${y.remindId==4}">
                                    <p>
                                        <i class="fa fa-user-plus text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                        <a href="/cm/channel/${y.senderId}" title="${y.senderName }">
                                                ${y.senderName }
                                        </a>
                                        <label class="padding5">${y.message }</label>
                                        <c:if test="${y.status==0}">
                                            <i class="fa fa-bell-o padding5" title="未读"></i>
                                            <input type="hidden" name="unReadId" value="${y.id}">
                                        </c:if>
                                    </p>
                                </c:if>

                                <%--5类-站内消息--%>
                                <c:if test="${y.remindId==5}">
                                    <p>
                                        <i class="fa fa-envelope text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                            ${y.senderName }
                                            <p>
                                            <label class="padding5  text-primary">${y.message }</label>
                                            </p>
                                            <c:if test="${y.status==0}">
                                                <i class="fa fa-bell-o padding5" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </c:if>
                                    </p>
                                </c:if>
                                <%--6类-资源提醒消息--%>
                                <c:if test="${y.remindId==6}">
                                        <p>
                                        <i class="fa fa-file-text-o text-primary padding5"></i>
                                        <span class="text-${y.senderId.substring(0,1) }" style="width: 28px;height: 28px;line-height: 28px;">
                                                ${y.senderName.substring(0,1) }
                                        </span>
                                                ${y.senderName }
                                        <p>
                                            <label class="padding5  text-primary">${y.message }</label>
                                        </p>
                                        <a href="${y.objectLinks}">${y.object}</a>
                                        <c:if test="${y.status==0}">
                                            <i class="fa fa-bell-o padding5" title="未读"></i>
                                            <input type="hidden" name="unReadId" value="${y.id}">
                                        </c:if>
                                        </p>
                                </c:if>
                                <p>
                                    <small class="label label-gray">${y.createTime }</small>
                                </p>
                            </div>

                        </div>
                        <hr>
                    </c:forEach>
                    <c:if test="${pagein!='no' and list.size()>0 }">
                        <%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>
                    </c:if>
                    <c:if test="${ list.size()==0 }">
                        <p class="center">
                            <small class="label label-gray">空空如也</small>
                        </p>

                        <hr class="border-light-1">
                    </c:if>
                </div>
                <div class="col-sm-offset-1 col-sm-4 ">

                    <div class="row bg-lblue padding20 radius-5">
                        <c:if test="${user.headPath!=null and user.headPath!=''}">
                            <div class="col-xs-2 avatar padding10">
                             <img src="/bruce/${user.headPath}"></img>
                            </div>
                        </c:if>
                        <c:if test="${user.headPath==null or user.headPath==''}">
                            <div class="col-xs-2 avatar padding10">
                                <span class="text-1" >
                                        ${user.username.substring(0,1) }
                                </span>
                            </div>

                        </c:if>
                        <div class="col-xs-10">
                            <h4 style="color:#333">${user.username}</h4>
                            <hr>
                            <p class="gray-text"><i class="fa fa-envelope padding5"></i>${user.email}</p>
                            <p class="gray-text"><i class="fa fa-link padding5"></i>
                                <c:if test="${user.blogSite==null or user.blogSite==''}">
                                    -
                                </c:if>
                                <c:if test="${user.blogSite!=null and user.blogSite!=''}">
                                    ${user.blogSite}
                                </c:if>
                            </p>
                            <p class="gray-text"><i class="fa fa-globe padding5"></i>
                                <c:if test="${user.meta==null or user.meta==''}">
                                -
                                </c:if>
                                <c:if test="${user.meta!=null and user.meta!=''}">
                                    ${user.meta}
                                </c:if>

                            </p>
                            <p class="gray-text"><i class="fa fa-calendar-times-o padding5"></i>${user.dateJoined}</p>
                        </div>

                    </div>

                </div>
            </div>


        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<script type="text/javascript"  >
    //禁止图片拉伸
    $(function () {
        $("img").each(function () {
            $(this).css('max-width', '100%')
                  .css('height', 'auto')
                  .attr('loading', 'lazy');
        })

    })

    function readNotify(){
        const arr =[];
        $("input[name='unReadId']").each(function (){
            arr.push($(this).val());
        });
        if(arr.length>0){
            var id = arr.join(",");
            console.log("id",id);
            if(id){
                $.ajax({
                    url: "/notify/readNotify",
                    type: "post",
                    dataType: "json",
                    data:{"id":id},
                    success: function (msg) {

                        if(msg.result){
                            //清理未读消息
                            console.log('清理未读消息',msg.data);
                        }else{
                            console.log('清理未读消息失败--->'+msg.message);
                        }
                    }
                });
            }

        }

    }


    setTimeout(readNotify(), 30*1000 );

</script>


<script>

    //loadDonates(1);

    //禁止图片拉伸
    $(function () {

        //设置标签颜色,绑定动作按钮
        $(".tag-node").each(function () {
            $(this).css("backgroundColor", getRandomColor());
            $(this).css("color", "#fff");

            $(this).click(function () {
                var oid = $(this).attr("oid");
                window.location.href = "/notifications?remindID=" + oid;
            })
        });

        /*  ##set query param */
        var remindID = "${remindID}";
        if (remindID) {
            $("#pageForm a").each(function () {
                var href = $(this).attr("href");
                $(this).attr("href", href + "?remindID=" + remindID);
            })
        }

        // 高亮当前选中的消息类型
        if(remindID) {
            $(".tag-node[oid='" + remindID + "']").addClass('active');
        }

        // 优化未读消息处理
        readNotify();

    })


    //1、随机色的函数-rgb
    function getRandomColor() {
        const colors = [
            '#3498db', '#2ecc71', '#e74c3c', '#f1c40f', 
            '#9b59b6', '#1abc9c', '#e67e22', '#34495e'
        ];
        return colors[Math.floor(Math.random() * colors.length)];
    }
</script>
</body>
</html>
