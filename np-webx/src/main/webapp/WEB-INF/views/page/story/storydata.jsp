<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div class="story-row">
    <c:forEach items="${list }" var="s" varStatus="ss">
        <div class="story-col">
            <div class="story-card">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="user-profile text-center">
                            <div class="thumbnail border-0 center ">
                                <div class="avatar">
                                    <a

                                            <c:if test="${s.tail_slug==null ||s.tail_slug==''}">
                                                href="/cm/channel/${s.userid}"
                                            </c:if>
                                            <c:if test="${s.tail_slug!=null }">
                                                href="/people/${s.tail_slug}"
                                            </c:if>

                                            title="${s.get('username')}:我的故事">
                                        <c:if test="${s.get('head_path') ==null||s.get('head_path') ==''||s.get('head_path').length()==0}">
                                            <span class=" imgbreath ${s.head_span_class }"
                                                  alt="${s.get('username')}">${s.head_span }</span>
                                        </c:if>
                                        <c:if test="${s.get('head_path') !=null && s.get('head_path').length()>0}">
                                            <img class="imgbreath"
                                            <c:choose>
                                                 <c:when test="${fn:contains(s.head_path ,'http://') }">src="${s.head_path }"</c:when>
                                                 <c:otherwise>src="/bruce/${s.head_path }"</c:otherwise>
                                            </c:choose>

                                                 alt="${s.username}的故事">
                                        </c:if>
                                    </a>
                                    <p>
                                        <small class="gray-text">${s.username}</small>
                                    </p>
                                    <div class="clearfix visible-xs">
                                        <hr>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-9">
                        <div class="story-content">
                            <p>
                                <small class="label label-gray">${s.create_time }</small> &nbsp; <a

                                    <c:if test="${s.tail_slug==null ||s.tail_slug==''}">
                                        href="/cm/channel/${s.userid}"
                                    </c:if>
                                    <c:if test="${s.tail_slug!=null }">
                                        href="/people/${s.tail_slug}"
                                    </c:if>


                                    title="${s.username}的最爱">${s.username}</a> 写到：
                            </p>
                            <div id="content_${ss.index}" class="story-content-text" data-content="${fn:escapeXml(s.note)}">
                                <div class="story-text-content">
                                    ${s.note}
                                </div>
                            </div>

                            <div class="hidden" id="stuffCommentList_${s.noteid}">

                                <h4><span class="glyphicon glyphicon-comment" title="展示评论详情;登录后参与评论"></span></h4>
                                <hr>
                                    <%--展示评论详情--%>
                                <div class="clearfix" id="stuffCommentBox_${s.noteid}">

                                </div>

                            </div>




                            <!-- 操作按钮区域 -->
                            <div class="story-actions">
                                <button class="btn story-action-btn story-expand-btn" 
                                        data-target="#content_${ss.index}"
                                        style="display: none;">
                                    <span class="glyphicon glyphicon-chevron-down expand-icon"></span>
                                    <span class="expand-text">展开</span>
                                </button>
                                
                                <c:if test="${user!=null }">
                                    <button class="btn story-action-btn click2comment"
                                            title="点击回复"
                                            data-dismiss="#comment_${s.noteid}_${ss.index}"
                                            data-target="#comment_${s.noteid}_${ss.index}">
                                        <span class="glyphicon glyphicon-comment"></span> 回复
                                    </button>
                                </c:if>
                            </div>

                            <!-- 评论区域 -->
                            <c:if test="${user!=null }">
                                <div class="form-group note-comment" id="comment_${s.noteid}_${ss.index}" style="display: none">
                                    <textarea id="input_cm_${s.noteid}_${ss.index}"
                                              class="form-control"
                                              placeholder="分享你的想法..."
                                              rows="3"></textarea>

                                    <button title="提交评论"
                                            class="btn btn-hero click2save margin-t10"
                                            topic-id="${s.noteid}"
                                            topic-type="1"
                                            from-uid="${user.id}"
                                            from-uname="${user.username}"
                                            data-dismiss="#comment_${s.noteid}_${ss.index}"
                                            data-target="#content_${ss.index}"
                                            data-input="#input_cm_${s.noteid}_${ss.index}">
                                        <i class="fa fa-paper-plane"></i> 发送
                                    </button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

