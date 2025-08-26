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

<!-- 优化布局结构 -->
<div class="clearfix maincontent">
    <div class="container">
        <!-- 页面标题部分 -->
        <div class="row">
            <div class="col-sm-12">
                <h1 class="font-elegant text-center" style="margin:30px 0;color:#2c3e50;">
                    <i class="fa fa-bell"></i> 消息通知中心
                </h1>
            </div>
        </div>

        <!-- 消息类型筛选部分 -->
        <div class="row">
            <div class="col-sm-12">
                <div class="notification-filter text-center" style="margin-bottom:30px;">
                    <div class="btn-group" role="group" aria-label="消息类型">
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="1">
                            <i class="fa fa-comments-o"></i> 文章评论
                        </button>
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="2">
                            <i class="fa fa-heart-o"></i> 收到点赞
                        </button>
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="3">
                            <i class="fa fa-commenting-o"></i> 树洞回复
                        </button>
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="4">
                            <i class="fa fa-user-plus"></i> 新增关注
                        </button>
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="5">
                            <i class="fa fa-envelope"></i> 站内消息
                        </button>
                        <button class="btn tag-node" style="margin:0 5px;min-width:90px;border-radius:20px;" oid="6">
                            <i class="fa fa-file-text-o"></i> 资源动态
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 主内容区域 -->
        <div class="row">
            <!-- 消息列表 -->
            <div class="col-sm-8">
                <div class="notification-list">
                    <c:forEach var="y" items="${list}" varStatus="ss">
                        <div class="notification-item bg-white">
                            <!-- 1类：文章评论通知 -->
                            <c:if test="${y.remindId==1}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <a href="/cm/channel/${y.senderId}" class="user-link">
                                            ${y.senderName}
                                        </a>
                                        <span class="action-text">在回复</span>
                                        <a href="${y.objectLinks}" class="object-link">
                                            ${y.object}
                                        </a>
                                        <span class="action-text">时提到了你</span>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-body">
                                        <blockquote class="message-quote">
                                            ${y.message}
                                        </blockquote>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>

                            <!-- 2类：点赞通知 -->
                            <c:if test="${y.remindId==2}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <a href="/cm/channel/${y.senderId}" class="user-link">
                                            ${y.senderName}
                                        </a>
                                        <span class="action-text">赞了你的</span>
                                        <a href="${y.objectLinks}" class="object-link">
                                            ${y.object}
                                        </a>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>

                            <!-- 3类：树洞回复 -->
                            <c:if test="${y.remindId==3}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <a href="/cm/channel/${y.senderId}" class="user-link">
                                            ${y.senderName}
                                        </a>
                                        <span class="action-text">回复了你的树洞</span>
                                        <a href="${y.objectLinks}" class="object-link">
                                            ${y.object}
                                        </a>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-body">
                                        <blockquote class="message-quote">
                                            ${y.message}
                                        </blockquote>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>

                            <!-- 4类：关注通知 -->
                            <c:if test="${y.remindId==4}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <a href="/cm/channel/${y.senderId}" class="user-link">
                                            ${y.senderName}
                                        </a>
                                        <span class="action-text">关注了你</span>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>

                            <!-- 5类：站内消息 -->
                            <c:if test="${y.remindId==5}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <span class="user-link">${y.senderName}</span>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-body">
                                        <div class="system-message">
                                            ${y.message}
                                        </div>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>

                            <!-- 6类：资源动态 -->
                            <c:if test="${y.remindId==6}">
                                <div class="notification-content">
                                    <div class="notification-header">
                                        <div class="avatar-wrapper">
                                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(y.senderId,0,1))}">
                                                ${fn:toUpperCase(fn:substring(y.senderName,0,1))}
                                            </span>
                                        </div>
                                        <span class="user-link">${y.senderName}</span>
                                        <c:if test="${y.status==0}">
                                            <span class="unread-badge">
                                                <i class="fa fa-bell-o" title="未读"></i>
                                                <input type="hidden" name="unReadId" value="${y.id}">
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="notification-body">
                                        <div class="resource-message">
                                            ${y.message}
                                        </div>
                                        <a href="${y.objectLinks}" class="resource-link">
                                            <i class="fa fa-external-link"></i> ${y.object}
                                        </a>
                                    </div>
                                    <div class="notification-footer">
                                        <small class="text-muted">
                                            <i class="fa fa-clock-o"></i> ${y.createTime}
                                        </small>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>

                    <!-- 分页 -->
                    <c:if test="${pagein!='no' and list.size()>0}">
                        <%@ include file="/WEB-INF/views/page/common/fenye.jsp" %>
                    </c:if>

                    <!-- 空状态 -->
                    <c:if test="${list.size()==0}">
                        <div class="empty-state text-center" style="padding:40px 0;">
                            <i class="fa fa-bell-slash-o fa-3x text-muted"></i>
                            <p class="text-muted" style="margin-top:10px;">暂无消息通知</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- 用户信息侧边栏 -->
            <div class="col-sm-4">
                <div class="user-profile bg-white" style="padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);">
                    <div class="profile-header">
                        <div class="avatar-wrapper text-center">
                            <span class="avatar-text text-${fn:toLowerCase(fn:substring(user.username,0,1))}" style="width:80px;height:80px;line-height:80px;font-size:32px;">
                                ${fn:toUpperCase(fn:substring(user.username,0,1))}
                            </span>
                        </div>
                        <h4 class="text-center" style="margin:15px 0;">${user.username}</h4>
                    </div>
                    <div class="profile-info">
                        <p><i class="fa fa-envelope"></i> ${user.email}</p>
                        <p><i class="fa fa-link"></i> ${user.blogSite==null?'-':user.blogSite}</p>
                        <p><i class="fa fa-globe"></i> ${user.meta==null?'-':user.meta}</p>
                        <p><i class="fa fa-calendar"></i> ${user.dateJoined}</p>
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

<style>
/* 通用样式 */
.notification-item {
    padding: 20px;
    margin-bottom: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    background: #fff;
}

.notification-header {
    margin-bottom: 10px;
}

.avatar-text {
    display: inline-block;
    width: 28px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    border-radius: 50%;
    margin: 0 5px;
    font-weight: 500;
}

/* 消息类型特定颜色 */
.text-purple {
    color: #9b59b6;
}

.system-message, .resource-message {
    background: #f8f9fa;
    padding: 15px;
    border-radius: 4px;
    margin: 10px 0;
}

.resource-link {
    display: block;
    margin-top: 10px;
    color: #3498db;
}

/* 分页样式优化 */
#pageForm {
    margin-top: 20px;
}

#pageForm a {
    color: #3498db;
    background: #fff;
    padding: 8px 12px;
    border-radius: 4px;
    margin: 0 2px;
    border: 1px solid #e0e0e0;
}

#pageForm a:hover {
    background: #3498db;
    color: #fff;
    text-decoration: none;
}

#pageForm .current {
    background: #3498db;
    color: #fff;
    padding: 8px 12px;
    border-radius: 4px;
    margin: 0 2px;
}

/* 标签按钮样式优化 */
.tag-node {
    transition: all 0.3s ease;
    border: none;
    opacity: 0.9;
}

.tag-node:hover {
    opacity: 1;
    transform: translateY(-1px);
}

.tag-node.active {
    opacity: 1;
    transform: translateY(-1px);
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* 头像样式优化 */
.avatar-text {
    display: inline-block;
    width: 36px;
    height: 36px;
    line-height: 36px;
    text-align: center;
    border-radius: 50%;
    margin: 0 5px;
    font-weight: 500;
    color: #fff;
    font-size: 16px;
    text-transform: uppercase; /* 确保字母大写 */
}

/* 用户资料卡片中的大头像 */
.profile-header .avatar-text {
    width: 80px;
    height: 80px;
    line-height: 80px;
    font-size: 32px;
}

/* 预设的头像背景色 */
.text-0 { background-color: #3498db; }
.text-1 { background-color: #2ecc71; }
.text-2 { background-color: #e74c3c; }
.text-3 { background-color: #f1c40f; }
.text-4 { background-color: #9b59b6; }
.text-5 { background-color: #1abc9c; }
.text-6 { background-color: #e67e22; }
.text-7 { background-color: #34495e; }
.text-8 { background-color: #16a085; }
.text-9 { background-color: #d35400; }
.text-a { background-color: #8e44ad; }
.text-b { background-color: #2980b9; }
.text-c { background-color: #27ae60; }
.text-d { background-color: #c0392b; }
.text-e { background-color: #f39c12; }
.text-f { background-color: #7f8c8d; }

/* 头像容器样式 */
.avatar-wrapper {
    display: inline-block;
    vertical-align: middle;
}
</style>
