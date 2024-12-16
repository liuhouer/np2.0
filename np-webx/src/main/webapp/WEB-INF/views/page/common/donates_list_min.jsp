<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col-md-12 margin-t10">

     <%
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");

        String type_id = (String) request.getAttribute("type_id");

    %>
        <div class="tab-pane fade in active" id="3">

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
