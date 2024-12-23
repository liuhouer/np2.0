<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="row">
    <c:if test="${!empty lovelist }">
        <c:forEach items="${lovelist }" var="s" varStatus="ss">
            <div class="col-xs-6 col-sm-3 margin-b20 ">
                <div class="blog-post">
                    <div class="blog-thumb">
                        <a href="/love/${s.title_code}.html" title="${s.title}">
                            <img class="imgbreath" width="257" height="193"
                            <c:choose>
                                <c:when test="${fn:contains(s.album_img,'http://')}">
                                    src="${s.album_img}"
                                </c:when>
                                <c:otherwise>
                                    src="/bruce/${s.album_img}"
                                </c:otherwise>
                            </c:choose>
                            alt="${s.title} - ${s.album} - ${s.artist}"
                            loading="lazy">
                        </a>
                    </div>
                    <div class="blog-content">
                        <div class="content-show">
                            <h4>
                                <a href="/love/${s.title_code }.html">
                                        ${s.cut_title }
                                </a></h4>
                            <span>${s.engDate }</span>
                        </div>
                            <%-- <div class="content-hide" style="display: none;">
                                <p>${s.album }<br>${s.artist }</p>
                            </div> --%>
                    </div>


                </div>
                <!-- pl和点赞的div -->
                <div class="row margin-t0 iteminfo" style="margin-top: -1.5em;">
                    <div class="col-xs-7 text-left">
                    </div>
                    <div class="col-xs-5 text-right">
                        <span class="glyphicon glyphicon-heart"></span> ${s.zan }
                        <span class="hidden-sm hidden-xs"> &nbsp;
													<span class="glyphicon glyphicon-comment"></span> ${s.pl } 						</span>
                    </div>
                </div>

            </div>
        </c:forEach>
    </c:if>
</div>
	
	
					
					
		   


