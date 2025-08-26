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
    <title>忘记密码 | NorthPark</title>
    <meta name="keywords" content="NorthPark,忘记密码,重置密码">
    <meta name="description"
          content="NorthPark-重置密码">

    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>


</head>

<body style="">
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<input type="hidden" id="J_msg" value="${msg}"/>
<!-- 页面标题 -->
<div class="clearfix maincontent">
    <div class="container">


            <div class="align-center bg-white radius-5 padding10 max-width-400 min-width-300" style="margin-top: 120px;">

                <div class="clearfix">
                    <h1 class="font-elegant">忘记密码</h1>
                    <hr>
                </div>

                <!-- 第一步：输入邮箱 -->
                <div id="step1">
                    <div class="form-group ">
                        <label for="J_email" class="control-label">Email：</label> <input
                            id="J_email" placeholder="example@gmail.com"
                            class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                            name="email" type="text">
                    </div>
                    <label class="control-label iteminfo " id="J_tip"></label>
                    <div class="form-group">
                        <input id="sendCodeBtn" data-activetext="发送验证码 ››"
                               class="btn btn-hero btn-xlg margin-t10 grid98" type="button"
                               value="发送验证码" disabled="disabled">
                    </div>
                </div>

                <!-- 第二步：验证邮箱验证码 -->
                <div id="step2" style="display: none;">
                    <div class="form-group">
                        <label for="J_code" class="control-label">验证码：</label>
                        <input id="J_code" placeholder="请输入邮箱收到的验证码"
                               class="form-control input-lg border-light-1 bg-lyellow grid98 radius-0"
                               name="code" type="text" maxlength="6">
                    </div>
                    <div class="form-group">
                        <input id="verifyCodeBtn" class="btn btn-hero btn-xlg margin-t10 grid98"
                               type="button" value="验证验证码">
                    </div>
                    <div class="form-group">
                        <input id="resendCodeBtn" class="btn btn-default btn-lg margin-t10 grid98"
                               type="button" value="重新发送验证码">
                    </div>
                </div>

                <!-- 第三步：设置新密码 -->
                <div id="step3" style="display: none;">
                    <div class="form-group">
                        <label for="J_newPassword" class="control-label">新密码：</label>
                        <input id="J_newPassword" placeholder="请输入新密码"
                               class="form-control input-lg border-light-1 bg-lyellow grid98 radius-0"
                               name="newPassword" type="password" minlength="6">
                    </div>
                    <div class="form-group">
                        <label for="J_confirmPassword" class="control-label">确认密码：</label>
                        <input id="J_confirmPassword" placeholder="请再次输入新密码"
                               class="form-control input-lg border-light-1 bg-lyellow grid98 radius-0"
                               name="confirmPassword" type="password" minlength="6">
                    </div>
                    <div class="form-group">
                        <input id="resetPasswordBtn" class="btn btn-hero btn-xlg margin-t10 grid98"
                               type="button" value="重置密码">
                    </div>
                </div>
            </div>
            <br><br>



    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>
<script src="/static/js/page/forget.js"></script>


</body>
</html>
