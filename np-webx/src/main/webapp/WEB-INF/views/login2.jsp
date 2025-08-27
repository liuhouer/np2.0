<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Login | NorthPark</title>
    <meta name="keywords" content="NorthPark,登录,Login">
    <meta name="description"
          content="NorthPark-登录">

    <link media="all" type="text/css" rel="stylesheet" href="/static/css/login/owl-login.css">
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

    <style>

        .vcode {
            height: 34px;
            padding: 6px 12px;
            font-size: 14px;
            line-height: 1.42857143;
            color: #555;
            background-color: #fff;
            background-image: none;
            border: 1px solid #ccc;
            border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
            -webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
        }

        /* 图形验证码行样式 */
        .captcha-row {
            display: flex;
            align-items: center;
            gap: 8px;
            flex-wrap: wrap;
        }

        .captcha-input {
            flex: 1;
            min-width: 100px;
        }

        .captcha-canvas {
            flex-shrink: 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            cursor: pointer;
        }

        /* 移动端适配 */
        @media (max-width: 480px) {
            .captcha-row {
                flex-direction: column;
                align-items: stretch;
                gap: 10px;
            }
            
            .captcha-input {
                width: 100%;
            }
            
            .captcha-canvas {
                align-self: center;
            }
        }

        /* 平板端适配 */
        @media (min-width: 481px) and (max-width: 768px) {
            .captcha-input {
                min-width: 120px;
            }
        }

        /* OAuth按钮样式 */
        .btn-danger {
            background-color: #dd4b39;
            border-color: #dd4b39;
            color: #fff;
        }
        
        .btn-danger:hover {
            background-color: #c23321;
            border-color: #c23321;
            color: #fff;
        }
        
        .btn-dark {
            background-color: #333;
            border-color: #333;
            color: #fff;
        }
        
        .btn-dark:hover {
            background-color: #222;
            border-color: #222;
            color: #fff;
        }
        
        .oauth-buttons .btn {
            padding: 8px 16px;
            font-size: 14px;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
        }
        
        .oauth-buttons .btn:focus,
        .oauth-buttons .btn:active {
            outline: none;
            box-shadow: none;
        }
        
        /* 错误提示样式 */
        .alert {
            padding: 10px 15px;
            margin-bottom: 15px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        
        .alert-danger {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
    </style>

</head>

<body>

<div class="navbar navbar-default navbar-fixed-top mainhead-navbox" role="navigation">

    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle mainhead-navbtn" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">菜单导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><img src="/static/img/logo.png" width="96" height="16"></img></a>
        </div>
        <div class="navbar-collapse collapse mainhead-collapse">
            <ul class="nav mainhead-nav">
                <li><a href="/movies" title="包含最新的影视剧资源"><i class="fa fa-film padding5"></i>影视</a></li>
                <li><a href="/soft/mac" title="丰富的Mac软件资源"><i class="fa fa-apple padding5"></i>软件</a></li>
                <li><a href="/learning" title="学习/课程/书籍/知识"><i class="fa fa-graduation-cap padding5"></i>学习</a></li>
                <li><a href="/love" title="最爱主题"><i class="fa fa-picture-o padding5"></i>最爱</a></li>
                <li><a href="/note" title="树洞的精神角落"><i class="fa fa-comment padding5"></i>树洞</a> </li>


                <li class="active"><a href="/signup" title="去注册"><i class="fa fa-sign-in padding5"></i>登陆</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- 页面标题 -->
<h1 class="font-elegant">Login</h1>
<div class="maincontent margin-b10" style="min-height: 800px;">
    <div class="container">


        <!-- begin -->
        <div id="login">

            <div class="wrapper">
                <div class="login">

                    <form id="loginForm" action="/cm/login" method="post" class="container offset1 loginform">
                        <div id="owl-login">
                            <div class="hand"></div>
                            <div class="hand hand-r"></div>
                            <div class="arms">
                                <div class="arm"></div>
                                <div class="arm arm-r"></div>
                            </div>
                        </div>
                        <div class="pad">
                            <div class="control-group">
                                <div class="controls">
                                    <label for="email" class="control-label fa fa-envelope"></label>
                                    <input id="email" type="email" name="email" placeholder="邮箱" tabindex="1"
                                           autofocus="autofocus" class="form-control input-medium">
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <label for="password" class="control-label fa fa-asterisk"></label>
                                    <input id="password" type="password" name="password" placeholder="密码"
                                           tabindex="2" class="form-control input-medium">
                                </div>
                            </div>

                            <div class="control-group">
                                <div class="controls captcha-row">
                                    <label for="code" class="control-label fa fa-check-square"></label>
                                    <input id="code" type="text" name="code" placeholder="机器人？"
                                           tabindex="3" class="vcode captcha-input">
                                    <canvas id="canvas" width="100" height="37" class="captcha-canvas"></canvas>
                                </div>
                            </div>

                            <input id="redirectURI" name="redirectURI" type="hidden"
                                   value="${redirectURI} ">
                            
                            <!-- 错误信息显示 -->
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" style="margin: 10px 0; padding: 10px; background-color: #f2dede; border: 1px solid #ebccd1; color: #a94442; border-radius: 4px;">
                                    <i class="fa fa-exclamation-triangle" style="margin-right: 5px;"></i>
                                    ${error}
                                </div>
                            </c:if>
                            
                            <div id="showResult" class="control-group center margen-b10">
                            </div>
                        </div>
                        <div class="form-actions">
                            <a href="/cm/forget" target="_blank" tabindex="5" class="btn pull-left btn-link text-muted">忘记密码</a>
                            <a href="/signup?redirectURI=${redirectURI}" tabindex="6" class="btn btn-link text-muted">注册</a>
                            <button id="formSubmit" type="button" tabindex="4" class="btn btn-primary">登录</button>
                        </div>
                        
                        <!-- 第三方登录 -->
                        <div class="oauth-login" style="margin-top: 20px; text-align: center;">
                            <div style="margin-bottom: 15px; color: #999; font-size: 14px;">
                                <span style="background: #fff; padding: 0 10px;">或使用第三方账号登录</span>
                                <hr style="margin: -10px 0 0 0; border-color: #eee;">
                            </div>
                            <div class="oauth-buttons" style="display: flex; gap: 10px; justify-content: center; flex-wrap: wrap;">
                                <button type="button" id="googleLogin" class="btn btn-danger" style="background: #dd4b39; border-color: #dd4b39; min-width: 120px;">
                                    <i class="fa fa-google" style="margin-right: 5px;"></i>Google
                                </button>
                                <button type="button" id="githubLogin" class="btn btn-dark" style="background: #333; border-color: #333; min-width: 120px;">
                                    <i class="fa fa-github" style="margin-right: 5px;"></i>GitHub
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <!-- end -->


    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<%--<script src="/static/js/page/login2.js"></script>--%>
<script src="/static/js/page/login2.js"></script>

<script src="/static/js/code.js"></script>


</body>
</html>
