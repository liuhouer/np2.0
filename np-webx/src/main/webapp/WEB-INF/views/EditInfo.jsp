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
    <title>编辑我的档案 | NorthPark</title>
    <meta name="keywords" content="NorthPark,NorthPark中文网,编辑档案">
    <meta name="description"
          content="NorthPark-编辑我的档案">
    <link href="https://northpark.cn/statics/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<div class="clearfix maincontent">
    <div class="container">
        <div class="mainbody padding-t20" id="J_maincontent" style="margin-top:70px;">

            <input type="hidden" id="J_tail_slug" value="${MyInfo.tailSlug }"/>
            <div class="col-lg-6 col-lg-offset-3 bg-white radius-5  min-width-300">
                <form method="POST" action="/cm/saveEditInfo" enctype="multipart/form-data"
                      accept-charset="UTF-8" role="form" style="color:#444;" id="f1" class="form-horizontal margin-t20">
                    <div class="form-group">
                        <div class="col-md-9 col-md-offset-3 text-left">
                            <h3>
                                编辑我的档案
                            </h3>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group">
                        <label for="email" class="col-md-3 control-label input-lg">
                            注册邮箱：
                        </label>
                        <div class="col-md-9 text-left">
                            <p id="email" class="input-lg padding0 control-label text-left">
                                ${MyInfo.email }
                            </p>
                            <p class="help-block">
                                如需修改邮箱，请
                                <a href="mailto:zhangyang.z@icloud.com">
                                    联系我们
                                </a>
                            </p>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="username" class="col-md-3 control-label input-lg">
                            昵称：
                        </label>
                        <div class="col-md-9 text-left">
                            <input id="username" placeholder="肥肥安1987"
                                   class="form-control border-light-1 input-lg bg-lyellow padding10 grid70 radius-0"
                                   name="username" type="text" value="${MyInfo.username }">
                            <p class="help-block">
                                可以包含中文、英文、字母
                            </p>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="user_avatar" class="col-md-3 control-label input-lg">
                            头像：
                        </label>
                        <div class="col-md-9 text-left">
                            <div class="row">
                                <div class="col-xs-3" id="preview">
                                    <img id="imghead" alt="avatar"
                                    <c:if test="${MyInfo.headPath==null }">
                                         src="https://northpark.cn/statics/img/davatar.jpg"
                                    </c:if>
                                    <c:if test="${MyInfo.headPath!=null }">
                                    <c:choose>
                                    <c:when test="${fn:contains(MyInfo.headPath ,'http://') }">
                                         src="${MyInfo.headPath }"
                                    </c:when>
                                    <c:otherwise>
                                         src="/bruce/${MyInfo.headPath }"
                                    </c:otherwise>
                                    </c:choose>
                                    </c:if>
                                         class="img-responsive">
                                </div>
                                <div class="col-xs-9">
                                    <div id="plupload_box_uploadavatar50777" style="position: relative;">
                                        <div class="clearfix " id="plupload_queue_uploadavatar50777"
                                             style="height:10px;">
                                        </div>
                                        <div class="clearfix">
                                            <button type="button" id="plupload_pickbtn_uploadavatar50777"
                                                    class="btn btn-large btn-gray btn-round "
                                                    style="position: relative; z-index: 0;" onclick="user_avatar.click()">
																	<span class="glyphicon">
																	</span>
                                                修改头像
                                            </button>
                                            <button type="button" id="plupload_startupload_uploadavatar50777"
                                                    class="hide  btn">
                                                .
                                            </button>
                                        </div>
                                        <input id="user_avatar" name="file" style="visibility: hidden;"
                                               type="file" accept="jpg,png" onchange="previewImage(this)">
                                    </div>
                                    <p class="help-block">
                                        JPG或者PNG格式
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="tailSlug" class="col-md-3 control-label input-lg ">
                            域名代号：
                        </label>
                        <div class="col-md-9 text-left ">
                            <div class="clearfix">
                                <p class="">
                                    <b style="font-size:1.6em">
                                        <input id="tailSlug" placeholder="vivian1987" required
                                               class="input-lg grid33 border-light-1 bg-lyellow radius-0"
                                               name="tail_slug" type="text" value="${MyInfo.tailSlug }">
                                    </b>
                                </p>
                            </div>
                            <div class="clearfix">
                                <p class="help-block">
                                    可以用2~20位的英文或者数字组成
                                </p>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="user_birth" class="col-md-3 control-label input-lg">
                            生日：
                        </label>
                        <div class="col-md-9 text-left">
                            <div class="row">
                                <input id="user_birth" placeholder="1991-12-31"
                                       class="form_datetime form-control border-light-1 input-lg bg-lyellow padding10 grid70 radius-0"
                                       name="year_of_birth" type="text" value="${DT.yearOfBirth }">
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="courseware" class="col-md-3 control-label input-lg">
                            博客/网站：
                        </label>
                        <div class="col-md-9 text-left">
                            <div class="row">
                                <input id="courseware" placeholder="http://meditic.com"
                                       class="form-control border-light-1 input-lg bg-lyellow padding10 grid70 radius-0"
                                       name="courseware" type="text" value="${DT.courseware }">
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group ">
                        <label for="passwordBtn" class="col-md-3 control-label input-lg">
                            密码：
                        </label>
                        <div class="col-md-9 text-left">
                            <p class="input-lg padding0" id="passwordBtn">
                                <button type="button" class="btn btn-gray btn-lg click2show" data-dismiss="#passwordBtn"
                                        data-target="#passwordBox">
                                    修改密码
                                </button>
                            </p>
                            <div class="row hidden" id="passwordBox">
                                <div class="col-sm-6">
                                    <p>
                                        新密码：
                                        <input id="new_password"
                                               class="form-control  input-md  border-light-1 bg-lyellow  grid98 radius-0"
                                               name="new_password" type="password" value="">
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <p>
                                        重复一次：
                                        <input id="new_password_confirmation"
                                               class="form-control  input-md  border-light-1 bg-lyellow  grid98 radius-0"
                                               name="new_password_confirmation" type="password" value="">
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-md-9  text-left">
                            <input class="btn btn-hero btn-lg margin-t10 " type="button" onclick="saves()"
                                   value="更新档案">
                        </div>
                    </div>
                </form>
            </div>


        </div>
    </div>
</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>
<script src="https://northpark.cn/statics/js/bootstrap-datetimepicker.js"></script>
<script src="https://northpark.cn/statics/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="https://northpark.cn/statics/js/page/editinfo.js"></script>

<script>
    $(function () {
        $('.form_datetime').datetimepicker({
            language:'zh-CN',
            format:'yyyy-mm-dd',
            dateFormat: 'yyyy-mm-dd',
            minView: "month",//选择日期后，不会再跳转去选择时分秒
            autoclose:true
        })
    })

</script>

</body>
</html>
