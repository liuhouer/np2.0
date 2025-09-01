<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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
<%--            <div class="navbar-brand">--%>

<%--                <c:if test="${user==null }">--%>
<%--                    <a href="/login" title="登陆" class="mainhead-avatar avatar centre" height="40" width="40">--%>
<%--                        <img src="/static/img/davatar.jpg" alt="davatar"--%>
<%--                             class="img-circle max-width-50" height="40" width="40">--%>
<%--                    </a>--%>
<%--                </c:if>--%>
<%--                <c:if test="${user!=null }">--%>

<%--                    <a href="/cm/channel" title="个人中心" class="mainhead-avatar avatar centre" height="40" width="40">--%>
<%--                        <c:if test="${user.headPath==null }">--%>
<%--                            <span class="max-width-50  ${user.headSpanClass }" alt="${user.username}" height="40"--%>
<%--                                  width="40">${user.headSpan }</span>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${user.headPath!=null }">--%>
<%--                            <img src="/bruce${user.headPath}" alt="davatar" class="img-circle max-width-50" height="40"--%>
<%--                                 width="40" />--%>
<%--                        </c:if>--%>
<%--                    </a>--%>

<%--                </c:if>--%>

<%--                    <a href="/lyrics/add" title="添加最爱" class="btn btn-hero margin-l20">--%>
<%--                    <span class="fa fa-plus"></span> 添加</a>--%>

<%--                    <a href="/" title="首页" class="btn-xs margin-l10">--%>
<%--                    <span class="fa fa-home fa-lg padding5"></span></a>--%>

<%--            </div>--%>
        </div>
        <div class="navbar-collapse collapse mainhead-collapse">
            <ul class="nav mainhead-nav" id="J_tabs">

                <li cname="movies"><a href="/movies" title="包含最新的影视剧资源"><i class="fa fa-film padding5"></i>影视</a></li>
                <li cname="soft"><a href="/soft/mac" title="丰富的Mac软件资源"><i class="fa fa-apple padding5"></i>软件</a></li>
                <li cname="learning"><a href="/learning" title="学习/课程/书籍/知识"><i class="fa fa-graduation-cap padding5"></i>学习</a></li>
<%--                <li cname="soft"><a href="/soft/mac" title="丰富的Mac软件资源"><i class="fa fa-music padding5"></i>音乐</a></li>--%>
                <li cname="pic"><a href="/love" title="最爱主题"><i class="fa fa-picture-o padding5"></i>最爱</a></li>
                <li cname="note"><a href="/note" title="树洞的精神角落"><i class="fa fa-comment padding5"></i>树洞</a> </li>
                <li>
                    <c:if test="${user==null }">
                        <a id="J_log_info_r" title="登录-个人中心" href="/login">
                            <i class="fa fa-sign-in padding5"></i>登陆
                        </a>
                    </c:if>
                    <c:if test="${user!=null }">
                        <div class="user-avatar-dropdown dropdown">
                            <a class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" title="${user.username}的个人中心">
                                <!-- 头像显示优先级：headPath > avatarUrl > 首字母 -->
                                <c:choose>
                                    <c:when test="${not empty user.headPath}">
                                        <img src="/bruce${user.headPath}" alt="${user.username}" class="avatar-image" />
                                    </c:when>
                                    <c:when test="${not empty user.avatarUrl}">
                                        <img src="${user.avatarUrl}" alt="${user.username}" class="avatar-image" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="firstChar" value="${fn:substring(user.username, 0, 1)}" />
                                        <c:set var="upperChar" value="${fn:toUpperCase(firstChar)}" />
                                        <!-- 为中文字符和特殊字符提供颜色映射 -->
                                        <c:choose>
                                            <c:when test="${upperChar >= 'A' && upperChar <= 'Z'}">
                                                <c:set var="colorClass" value="text-${upperChar}" />
                                            </c:when>
                                            <c:when test="${upperChar >= '0' && upperChar <= '9'}">
                                                <c:set var="colorClass" value="text-${upperChar}" />
                                            </c:when>
                                            <c:otherwise>
                                                <!-- 为中文字符等使用哈希算法分配颜色 -->
                                                <c:set var="charCode" value="${firstChar.hashCode() % 12}" />
                                                <c:choose>
                                                    <c:when test="${charCode == 0 || charCode == -12}"><c:set var="colorClass" value="avatar-color-1" /></c:when>
                                                    <c:when test="${charCode == 1 || charCode == -11}"><c:set var="colorClass" value="avatar-color-2" /></c:when>
                                                    <c:when test="${charCode == 2 || charCode == -10}"><c:set var="colorClass" value="avatar-color-3" /></c:when>
                                                    <c:when test="${charCode == 3 || charCode == -9}"><c:set var="colorClass" value="avatar-color-4" /></c:when>
                                                    <c:when test="${charCode == 4 || charCode == -8}"><c:set var="colorClass" value="avatar-color-5" /></c:when>
                                                    <c:when test="${charCode == 5 || charCode == -7}"><c:set var="colorClass" value="avatar-color-6" /></c:when>
                                                    <c:when test="${charCode == 6 || charCode == -6}"><c:set var="colorClass" value="avatar-color-7" /></c:when>
                                                    <c:when test="${charCode == 7 || charCode == -5}"><c:set var="colorClass" value="avatar-color-8" /></c:when>
                                                    <c:when test="${charCode == 8 || charCode == -4}"><c:set var="colorClass" value="avatar-color-9" /></c:when>
                                                    <c:when test="${charCode == 9 || charCode == -3}"><c:set var="colorClass" value="avatar-color-10" /></c:when>
                                                    <c:when test="${charCode == 10 || charCode == -2}"><c:set var="colorClass" value="avatar-color-11" /></c:when>
                                                    <c:otherwise><c:set var="colorClass" value="avatar-color-12" /></c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="user-avatar-letter ${colorClass}">${firstChar}</span>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                <li role="presentation">
                                    <a title="未读消息" href="/notifications">
                                        <i class="fa fa-envelope"></i>消息
                                        <span class="notification-badge" id="J_notify_box">0</span>
                                    </a>
                                </li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation">
                                    <a role="menuitem" tabindex="-1" href="/lyrics/add" title="添加最爱">
                                        <i class="fa fa-plus"></i>添加
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a role="menuitem" tabindex="-1" href="/cm/channel" title="${user.username}的个人空间">
                                        <i class="fa fa-user"></i>空间
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a role="menuitem" tabindex="-1" href="/me/settings" title="修改档案">
                                        <i class="fa fa-cog"></i>档案
                                    </a>
                                </li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation">
                                    <a role="menuitem" tabindex="-1" href="/cm/logout">
                                        <i class="fa fa-sign-out"></i>注销
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </li>

            </ul>
        </div>
    </div>
</div>
