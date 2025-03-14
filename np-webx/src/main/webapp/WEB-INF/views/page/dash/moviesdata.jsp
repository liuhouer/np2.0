<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- 代码开始 -->
<div class="moviebox">

    <div class="picbox">

        <ul class="piclist mainlist">
            <c:forEach items="${movies_list}" var="s" varStatus="ss">
                <li>
                    <a class="no-decoration" href="/movies/post-${s.id}.html" target="_blank">
                        <img src="https://northpark.cn/statics/img/index/movie${ss.index%11 +1}.png" alt="${s.movie_name}"/>
                        <span class="movie-title">${s.movie_name}</span>
                    </a>
                </li>
            </c:forEach>

        </ul>

    </div>

    <div class="og_prev"></div>

    <div class="og_next"></div>

</div>
<!-- 代码结束 -->