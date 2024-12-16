<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col-md-12 margin-t10" id="1">


    <%
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");

        String type_id = (String) request.getAttribute("type_id");

    %>

    <c:forEach var="y" items="${list }" varStatus="ss">
        <div class="donate-item">
            <p class="time">
                <i class="fa fa-clock-o"></i>
                ${y.add_time}
            </p>
            <p class="user">
                <i class="fa fa-user"></i>
                ${y.account_name}
            </p>
            <p class="message">
                ${y.reward_msg}
            </p>
            <p class="amount">
                <i class="fa fa-jpy"></i>
                ${y.order_amount}
            </p>
        </div>
    </c:forEach>


    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2018-04-10 08:51
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            黄婧怡
        </p>
        <p class="message">
            人非常好，重要的是网速特快
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>


    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2018-03-29 22:17:15
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            fromqtoj
        </p>
        <p class="message">
            10000395010***730739800
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            100.00
        </p>
    </div>
    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2018-03-29 10:56:54
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            augustvino
        </p>
        <p class="message">
            10000395010***560211665
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>
    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2018-02-12 15:42
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            韩锡勋哦哦
        </p>
        <p class="message">
            20180212210***247083668
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>
    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2018-01-09 22:30
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            b***re
        </p>
        <p class="message">
            20180109210***234125769
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>


    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2017-08-29 22:00
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            Billvampire
        </p>
        <p class="message">
            20170829210***205016276
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>
    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2017-08-13 01:31
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            *梓涵
        </p>
        <p class="message">
            20170813210***217537854
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            100.00
        </p>
    </div>
    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2017-08-05 23:02:42
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            季*菲
        </p>
        <p class="message">
            20170805210***203034131
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>


    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2017-07-03 18:41
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            臧*康
        </p>
        <p class="message">
            20170703210***217675150
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            99.00
        </p>
    </div>


    <div class="donate-item">
        <p class="time">
            <i class="fa fa-clock-o"></i>
            2017-04-28 23:08:59
        </p>
        <p class="user">
            <i class="fa fa-user"></i>
            lo***ng
        </p>
        <p class="message">
            10000394010***323845052
        </p>
        <p class="amount">
            <i class="fa fa-jpy"></i>
            100.00
        </p>
    </div>

	<div class="donate-pagination">
    <ul class="qinco-pagination pagination-sm ">
        <li><a onclick="loadDonates(<%=type_id%>,1)">‹‹</a></li>
        <li><a onclick="loadDonates(<%=type_id%>,<%=pageInfo.getPrePage()%>)"
                <% if (!pageInfo.isHasPreviousPage()) { %>
               onclick="return false;"
                <% } %>

        >‹</a></li>
        <%
            //<显示分页码
            for (int i = pageInfo.getNavigateFirstPage(); i <= pageInfo.getNavigateLastPage(); i++) {
                if (i != pageInfo.getPageNum()) {//如果i不等于当前页
        %>
        <li><a onclick="loadDonates(<%=type_id%>,<%=i%>)"><%=i%>
        </a></li>
        <%
        } else {
        %>
        <li class="active"><a><%=i%>
        </a></li>
        <%
                }
            }//显示分页码>
        %>
        <li><a onclick="loadDonates(<%=type_id%>,<%=pageInfo.getNextPage()%>)">›</a></li>
        <li><a onclick="loadDonates(<%=type_id%>,<%=pageInfo.getPages()%>)">››</a></li>
    </ul>
	
	</div> 
	
    <div class="donate-footer">
        <p>
            ~ 生活不止苟且，还有诗和远方 ~
        </p>
    </div>

	
</div>
