<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">

    <c:forEach items="${eq_list }" var="s" varStatus="ss">
        <div class="col-sm-4 col-xs-6">
            <div class="clearfix bg-white margin-t10 margin-b10 padding20 romeo-item" style="height: 220px;">
                <div class="row">
                    <div class="col-sm-5">
                        <div class="thumbnail border-1 center" style="height: 100px;">
                            <a title="${s.title}">
                                <img src="${s.img }" alt="${s.title}" style="max-height: 90px; width: auto;">
                            </a>
                        </div>
                    </div>

                    <div class="col-sm-7">
                        <p>
                            <small class="label label-gray">${s.date }</small> &nbsp; <br>
                            <a style="text-decoration: none; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical; overflow: hidden;" title="${s.title}">${s.title}</a>
                        </p>
                        <div id="brief_${ss.index}" style="height: 40px; overflow: hidden; margin-bottom: 10px;">
                            ${s.brief}
                        </div>
                        <p><a class="readmore no-decoration" target="_blank" href="/romeo/${s.id}.html">READ MORE</a></p>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

</div>

