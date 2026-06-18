<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${feedback_list != null && feedback_list.size() > 0}">
	<div class="feedback-list-box">
		<div class="feedback-list-title">
			<i class="fa fa-exclamation-circle"></i> 已失效/待更新资源
		</div>
		<div>
			<c:forEach var="y" items="${feedback_list}">
				<div class="feedback-item">
					<a class="feedback-item-title" href="${y.href}" title="${y.title}">
						${y.title}
					</a>
					<span class="feedback-item-badge">
						失效反馈 ${y.count} 次
					</span>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>
