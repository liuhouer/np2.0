<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <meta name="theme-color" content="#45d0c6">
        <meta name="format-detection" content="telephone=no">
        <link rel="shortcut icon" href="/static/img/favicon.ico">

        <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

        <!-- canonical链接 -->
        <link rel="canonical" href="https://northpark.cn/note/findAll" />

        <!-- Open Graph 标签 -->
        <meta property="og:title" content="${user.username}的个人树洞 - 心灵记录空间">
        <meta property="og:description" content="${user.username}的专属心灵记录空间，自由记录心情、分享生活感悟、留下珍贵的情感足迹">
        <meta property="og:type" content="profile">
        <meta property="og:url" content="https://northpark.cn/note/findAll">
        <meta property="og:image" content="/static/img/davatar.jpg">
        <meta property="profile:username" content="${user.username}">

        <!-- Twitter Card 标签 -->
        <meta name="twitter:card" content="summary">
        <meta name="twitter:title" content="${user.username}的个人树洞">
        <meta name="twitter:description" content="专属的心灵记录空间，记录心情分享生活感悟">
        <meta name="twitter:image" content="/static/img/davatar.jpg">
        <title>${user.username}的个人树洞 - 心灵记录与情感分享空间 | NorthPark</title>
        <meta name="keywords" content="${user.username}树洞,个人空间,心情记录,情感日记,生活感悟,心灵角落,NorthPark个人树洞">
        <meta name="description"
            content="${user.username}的个人树洞 - 专属的心灵记录空间，在这里可以自由记录心情、分享生活感悟、留下珍贵的情感足迹。一个富有交互性和趣味性的精神角落，见证成长的每一个瞬间。">

        <link href="/static/wangEditor/css/wangEditor-1.3.12.css" rel="stylesheet" />


    </head>


    <body style="">

        <%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>
            <%@ include file="/WEB-INF/views/page/common/centre.jsp" %>


                <div class="clearfix maincontent">
                    <div class="container">
                        <div class="mainbody padding10" style="margin-top:2em;">

                            <div class="clearfix margin-b20">
                                <ul class="nav nav-tabs">
                                    <li><a href="/cm/channel">最爱</a></li>
                                    <li class="active"><a href="note/findAll">留言</a></li>
                                    <li><a href="/cm/myfans">Fans</a></li>


                                </ul>
                            </div>
                            <form id="f2" method="post"><input name="userid" type="hidden" value="${user.id }" /></form>
                            <form action="/me/settings" method="post" id="f1">
                                <input name="userid" value="${user.id }" type="hidden">
                            </form>
                            <div class="row bg-white margin-t10 margin-b10  ">
                                <div class="col-sm-1">
                                    <a href="/cm/channel" title="${user.username}的最爱"><img
                                            src="/static/img/davatar.jpg"
                                            class="img-responsive  img-circle max-width-50"
                                            alt="${user.username}的最爱"></a>
                                </div>
                                <div class="col-sm-11">
                                    <form method="POST" action="/note/addNote" accept-charset="UTF-8" role="form"
                                        class="form">
                                        <input name="userid" type="hidden" value="${user.id }" />

                                        <div class="form-group">
                                            <textarea id="J_md_text" style="height:200px; max-height:400px; width:100%;"
                                                name="note" rows="5"></textarea>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-inverse btn-md"><span
                                                    class="glyphicon glyphicon-music"></span> 添加树洞
                                            </button>
                                            &nbsp; &nbsp;
                                            <span class="bg-lyellow"><input name="opened" type="checkbox" value="no">
                                                <small>仅供自己看到，不对外公布</small></span>
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <div class="row bg-white margin-t10 margin-b10 center">
                                <div id="showResult" class="form-group">

                                </div>
                            </div>
                            <c:forEach items="${list }" var="s" varStatus="ss">

                                <div class='row bg-white margin-t10 margin-b10' id='notebox_${s.id }'>
                                    <div class='col-sm-1'>
                                        <small class='label label-gray'>${s.createTime }</small>
                                    </div>
                                    <div class='col-sm-11'>
                                        <label class='btn btn-gray btn-xs pull-right delNoteBtn1' rel='${s.id }'
                                            onclick="removes(this)"><span
                                                class='glyphicon glyphicon-trash'></span></label>
                                        <p>${s.note }</p>
                                        <hr />
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

                    <script src="/static/wangEditor/js/jquery-1.10.2.min.js"
                        type="text/javascript"></script>
                    <script src="/static/wangEditor/js/wangEditor-1.3.12.js"
                        type="text/javascript"></script>


                    <script src="/static/js/page/note.js"></script>

    </body>

    </html>
