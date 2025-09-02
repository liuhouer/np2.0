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
                    <h5 style="margin-bottom:15px;color:#7f8c8d;"><i class="fa fa-filter"></i> 消息筛选</h5>
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
                <div class="notification-list-header" style="background:#fff;padding:15px 20px;border-radius:8px 8px 0 0;box-shadow:0 2px 4px rgba(0,0,0,0.1);margin-bottom:0;">
                    <div style="display:flex;justify-content:space-between;align-items:center;">
                        <h4 style="margin:0;color:#2c3e50;"><i class="fa fa-list"></i> 消息列表 <small class="text-muted">(共 ${list.size()} 条)</small></h4>
                        <button id="markAllReadBtn" class="btn btn-primary" style="border-radius:20px;padding:6px 16px;font-size:12px;" title="标记当前页所有消息为已读">
                            <i class="fa fa-check-circle"></i> 一键已读
                        </button>
                    </div>
                </div>
                <div class="notification-list" style="background:#f8f9fa;padding:0 20px 20px;border-radius:0 0 8px 8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);">
                    <c:forEach var="y" items="${list}" varStatus="ss">
                        <div class="notification-item bg-white">
                            <!-- 1类：文章评论通知 -->
                            <c:if test="${y.remindId==1}">
                                <div class="notification-content comment-notification">
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
                                <div class="notification-content like-notification">
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
                                <div class="notification-content reply-notification">
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
                                <div class="notification-content follow-notification">
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
                                <div class="notification-content system-notification">
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
                                <div class="notification-content resource-notification">
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
                <!-- 用户资料卡片 -->
                <div class="user-profile bg-white" style="padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);margin-bottom:20px;">
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
                
                <!-- 消息统计卡片 -->
                <div class="message-stats bg-white" style="padding:20px;border-radius:8px;box-shadow:0 2px 4px rgba(0,0,0,0.1);">
                    <h5 style="margin-bottom:15px;color:#2c3e50;"><i class="fa fa-bar-chart"></i> 消息统计</h5>
                    <div class="stats-grid">
                        <div class="stat-item" style="text-align:center;padding:10px;margin:5px 0;background:#f8f9fa;border-radius:4px;">
                            <div style="font-size:24px;font-weight:bold;color:#3498db;">${list.size()}</div>
                            <div style="font-size:12px;color:#7f8c8d;">本页消息</div>
                        </div>
                        <div class="stat-item" style="text-align:center;padding:10px;margin:5px 0;background:#f8f9fa;border-radius:4px;">
                            <div style="font-size:24px;font-weight:bold;color:#e74c3c;" id="unreadCount">0</div>
                            <div style="font-size:12px;color:#7f8c8d;">未读消息</div>
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


    // 移除30秒自动标记已读的逻辑，改为手动一键已读

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

        // 更新未读消息数量显示的函数
        function updateUnreadCount() {
            if (typeof getNotifyCount === 'function') {
                var totalUnreadCount = getNotifyCount();
                $("#unreadCount").text(totalUnreadCount);
                return totalUnreadCount;
            } else {
                // 降级方案：使用当前页的未读数量
                var currentPageUnreadCount = $("input[name='unReadId']").length;
                $("#unreadCount").text(currentPageUnreadCount);
                return currentPageUnreadCount;
            }
        }
        
        // 初始化未读消息数量显示
        updateUnreadCount();
        
        // 页面加载完成后再次更新未读数量，确保数据准确
        setTimeout(function() {
            updateUnreadCount();
        }, 1000);
        
        // 检查当前页是否有未读消息，决定是否显示一键已读按钮
        var currentPageUnreadCount = $("input[name='unReadId']").length;
        if (currentPageUnreadCount === 0) {
            $("#markAllReadBtn").hide();
        }
        
        // 一键已读按钮事件
        $("#markAllReadBtn").click(function() {
            var btn = $(this);
            var originalText = btn.html();
            
            // 获取当前页所有未读消息ID
            const arr = [];
            $("input[name='unReadId']").each(function() {
                arr.push($(this).val());
            });
            
            if (arr.length === 0) {
                alert('当前页没有未读消息');
                return;
            }
            
            // 按钮状态变更
            btn.html('<i class="fa fa-spinner fa-spin"></i> 处理中...').prop('disabled', true);
            
            var id = arr.join(",");
            $.ajax({
                url: "/notify/readNotify",
                type: "post",
                dataType: "json",
                data: {"id": id},
                success: function(msg) {
                    if (msg.result) {
                        // 标记成功，移除未读标识
                        $("input[name='unReadId']").each(function() {
                            $(this).closest('.unread-badge').fadeOut(300, function() {
                                $(this).remove();
                            });
                        });
                        
                        // 使用common.js中的函数更新全局未读数量
                        if (typeof fetchNotifyCount === 'function') {
                            fetchNotifyCount();
                        }
                        
                        // 延迟更新本页的未读数量显示，确保服务器端数据已更新
                        setTimeout(function() {
                            updateUnreadCount();
                        }, 500);
                        
                        // 更新页面标题，移除未读数量提示
                        var title = document.title;
                        if (title.indexOf('（') > -1) {
                            document.title = title.replace(/（\d+）/, '');
                        }
                        
                        // 隐藏一键已读按钮
                        setTimeout(function() {
                            $("#markAllReadBtn").fadeOut(300);
                        }, 1500);
                        
                        // 显示成功提示
                        btn.html('<i class="fa fa-check"></i> 已完成').removeClass('btn-primary').addClass('btn-success');
                        
                        setTimeout(function() {
                            btn.html(originalText).removeClass('btn-success').addClass('btn-primary').prop('disabled', false);
                        }, 2000);
                        
                    } else {
                        console.log('标记已读失败--->' + msg.message);
                        alert('操作失败：' + msg.message);
                        btn.html(originalText).prop('disabled', false);
                    }
                },
                error: function() {
                    alert('网络错误，请稍后重试');
                    btn.html(originalText).prop('disabled', false);
                }
            });
        });
        
        // 为通知项添加动画延迟
        $(".notification-item").each(function(index) {
            $(this).css('animation-delay', (index * 0.1) + 's');
        });
        
        // 添加点击反馈效果
        $(".notification-item").on('click', function() {
            $(this).addClass('clicked');
            setTimeout(() => {
                $(this).removeClass('clicked');
            }, 200);
        });
        
        // 优化长文本显示和JSON字符串处理
        $(".system-message, .resource-message, .message-quote").each(function() {
            var text = $(this).text();
            var originalHtml = $(this).html();
            
            // 检测是否为JSON字符串
            var isJson = false;
            try {
                if (text.trim().startsWith('{') || text.trim().startsWith('[')) {
                    JSON.parse(text.trim());
                    isJson = true;
                }
            } catch (e) {
                // 不是有效JSON，按普通文本处理
            }
            
            if (isJson) {
                // JSON字符串格式化处理
                try {
                    var jsonObj = JSON.parse(text.trim());
                    var formattedJson = JSON.stringify(jsonObj, null, 2);
                    $(this).html('<pre style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;overflow-wrap:anywhere;max-width:100%;margin:0;font-family:monospace;font-size:12px;background:#f8f9fa;padding:10px;border-radius:4px;overflow:hidden;">' + formattedJson + '</pre>');
                } catch (e) {
                    // JSON解析失败，按普通文本处理
                    $(this).css({
                        'white-space': 'pre-wrap',
                        'word-wrap': 'break-word',
                        'word-break': 'break-all',
                        'overflow-wrap': 'anywhere',
                        'max-width': '100%',
                        'overflow': 'hidden'
                    });
                }
            } else if (text.length > 200) {
                // 普通长文本处理
                var shortText = text.substring(0, 200) + '...';
                var fullText = text;
                $(this).html(shortText + '<br><a href="#" class="expand-text" style="color:#3498db;font-size:12px;">展开全文</a>');
                
                $(this).find('.expand-text').click(function(e) {
                    e.preventDefault();
                    var parent = $(this).parent();
                    if ($(this).text() === '展开全文') {
                        parent.html('<div style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;overflow-wrap:anywhere;max-width:100%;overflow:hidden;">' + fullText + '</div><br><a href="#" class="expand-text" style="color:#3498db;font-size:12px;">收起</a>');
                    } else {
                        parent.html(shortText + '<br><a href="#" class="expand-text" style="color:#3498db;font-size:12px;">展开全文</a>');
                    }
                });
            } else {
                // 短文本也要确保不溢出
                $(this).css({
                    'white-space': 'pre-wrap',
                    'word-wrap': 'break-word',
                    'word-break': 'break-all',
                    'overflow-wrap': 'anywhere',
                    'max-width': '100%',
                    'overflow': 'hidden'
                });
            }
        });

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
    transition: all 0.3s ease;
    border-left: 4px solid transparent;
    /* 防止内容溢出 */
    overflow: hidden;
    word-wrap: break-word;
    word-break: break-all;
    overflow-wrap: anywhere;
    max-width: 100%;
    min-width: 0;
}

.notification-item:hover {
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    transform: translateY(-2px);
}

.notification-content {
    word-wrap: break-word;
    word-break: break-all;
    overflow-wrap: break-word;
    max-width: 100%;
    overflow: hidden;
    /* 确保容器不会被撑开 */
    min-width: 0;
    flex: 1;
    /* 处理超长无空格字符串 */
    word-break: break-word !important;
    overflow-wrap: anywhere !important;
}

.notification-header {
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 8px;
}

.notification-body {
    margin: 15px 0;
}

.notification-footer {
    margin-top: 15px;
    padding-top: 10px;
    border-top: 1px solid #f0f0f0;
}

.message-quote {
    background: #f8f9fa;
    border-left: 4px solid #3498db;
    padding: 15px;
    margin: 10px 0;
    border-radius: 0 4px 4px 0;
    word-wrap: break-word;
    word-break: break-all;
    white-space: pre-wrap;
    line-height: 1.6;
    max-width: 100%;
    overflow-wrap: break-word;
    overflow: hidden;
    /* 强制换行处理JSON等长字符串 */
    -webkit-hyphens: auto;
    -moz-hyphens: auto;
    -ms-hyphens: auto;
    hyphens: auto;
    /* 确保容器不会被撑开 */
    min-width: 0;
    flex: 1;
    /* 处理超长无空格字符串 */
    word-break: break-word !important;
    overflow-wrap: anywhere !important;
}

.user-link {
    color: #3498db;
    font-weight: 500;
    text-decoration: none;
}

.user-link:hover {
    color: #2980b9;
    text-decoration: underline;
}

.object-link {
    color: #27ae60;
    font-weight: 500;
    text-decoration: none;
}

.object-link:hover {
    color: #229954;
    text-decoration: underline;
}

.action-text {
    color: #7f8c8d;
    margin: 0 4px;
}

.unread-badge {
    background: #e74c3c;
    color: white;
    padding: 2px 6px;
    border-radius: 10px;
    font-size: 12px;
    margin-left: 8px;
}

.unread-badge i {
    color: white;
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
    word-wrap: break-word;
    word-break: break-all;
    white-space: pre-wrap;
    line-height: 1.6;
    max-width: 100%;
    overflow-wrap: break-word;
    overflow: hidden;
    text-overflow: ellipsis;
    /* 强制换行处理JSON等长字符串 */
    -webkit-hyphens: auto;
    -moz-hyphens: auto;
    -ms-hyphens: auto;
    hyphens: auto;
    /* 确保容器不会被撑开 */
    min-width: 0;
    flex: 1;
    /* 处理超长无空格字符串 */
    word-break: break-word !important;
    overflow-wrap: anywhere !important;
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

/* 不同通知类型的边框颜色 */
.comment-notification {
    border-left-color: #3498db !important;
}

.like-notification {
    border-left-color: #e74c3c !important;
}

.reply-notification {
    border-left-color: #f1c40f !important;
}

.follow-notification {
    border-left-color: #2ecc71 !important;
}

.system-notification {
    border-left-color: #9b59b6 !important;
}

.resource-notification {
    border-left-color: #e67e22 !important;
}

/* 空状态样式优化 */
.empty-state {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    margin: 20px 0;
}

/* 用户资料卡片优化 */
.user-profile {
    position: sticky;
    top: 20px;
}

.profile-info p {
    margin: 8px 0;
    color: #7f8c8d;
    display: flex;
    align-items: center;
    gap: 8px;
}

.profile-info i {
    width: 16px;
    text-align: center;
}

/* 响应式优化 */
@media (max-width: 768px) {
    .notification-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 4px;
    }
    
    .notification-filter .btn-group {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 8px;
    }
    
    .tag-node {
        margin: 4px !important;
        min-width: 80px !important;
    }
    
    .user-profile {
        position: static;
        margin-top: 20px;
    }
}

/* 加载动画 */
.notification-item {
    animation: fadeInUp 0.5s ease-out;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* 筛选按钮组优化 */
.notification-filter {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* 页面标题优化 */
.maincontent h1 {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    text-shadow: none;
}

/* 滚动条美化 */
.notification-list::-webkit-scrollbar {
    width: 6px;
}

.notification-list::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 3px;
}

.notification-list::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 3px;
}

.notification-list::-webkit-scrollbar-thumb:hover {
    background: #a8a8a8;
}

/* 消息内容文本优化 - 强化版本 */
.notification-content p, .notification-content div, .notification-content span {
    word-wrap: break-word;
    word-break: break-all;
    overflow-wrap: break-word;
    hyphens: auto;
    /* 确保容器不会被撑开 */
    min-width: 0;
    max-width: 100%;
    overflow: hidden;
    /* 处理超长无空格字符串 */
    word-break: break-word !important;
    overflow-wrap: anywhere !important;
    /* 强制换行处理JSON等长字符串 */
    -webkit-hyphens: auto;
    -moz-hyphens: auto;
    -ms-hyphens: auto;
    hyphens: auto;
}

/* 针对JSON字符串的特殊处理 */
.notification-content pre, .notification-content code {
    white-space: pre-wrap !important;
    word-wrap: break-word !important;
    word-break: break-all !important;
    overflow-wrap: anywhere !important;
    max-width: 100% !important;
    overflow: hidden !important;
}

/* 强制所有文本内容换行 */
.notification-item * {
    word-wrap: break-word !important;
    word-break: break-all !important;
    overflow-wrap: anywhere !important;
    max-width: 100% !important;
}

/* 全局防溢出规则 */
.notification-list, .notification-item, .notification-content, 
.system-message, .resource-message, .message-quote {
    overflow: hidden !important;
    word-wrap: break-word !important;
    word-break: break-all !important;
    overflow-wrap: anywhere !important;
    max-width: 100% !important;
    min-width: 0 !important;
}

/* 特殊处理表格和代码块 */
.notification-item table {
    table-layout: fixed !important;
    width: 100% !important;
}

.notification-item td, .notification-item th {
    word-wrap: break-word !important;
    word-break: break-all !important;
    overflow-wrap: anywhere !important;
    max-width: 0 !important;
}

/* 处理超长URL和链接 */
.notification-item a {
    word-wrap: break-word !important;
    word-break: break-all !important;
    overflow-wrap: anywhere !important;
    max-width: 100% !important;
    display: inline-block !important;
}

/* 链接悬停效果优化 */
.notification-item a {
    transition: all 0.2s ease;
}

.notification-item a:hover {
    transform: translateX(2px);
}

/* 时间戳样式优化 */
.notification-footer small {
    font-size: 11px;
    opacity: 0.8;
}

/* 消息类型图标优化 */
.notification-header i {
    margin-right: 4px;
}

/* 点击反馈效果 */
.notification-item.clicked {
    transform: scale(0.98);
    box-shadow: 0 2px 8px rgba(0,0,0,0.2);
}

/* 展开文本链接样式 */
.expand-text {
    font-weight: 500;
    text-decoration: none !important;
}

.expand-text:hover {
    text-decoration: underline !important;
}

/* 一键已读按钮样式 */
#markAllReadBtn {
    transition: all 0.3s ease;
    box-shadow: 0 2px 4px rgba(52, 152, 219, 0.3);
}

#markAllReadBtn:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 8px rgba(52, 152, 219, 0.4);
}

#markAllReadBtn:disabled {
    opacity: 0.7;
    cursor: not-allowed;
    transform: none;
}

/* 消息列表头部样式优化 */
.notification-list-header {
    border-bottom: 1px solid #e9ecef;
}

.notification-list-header h4 {
    flex: 1;
}
</style>
