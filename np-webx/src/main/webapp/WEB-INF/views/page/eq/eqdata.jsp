<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">

    <c:forEach items="${list }" var="s" varStatus="ss">
        <div class="col-sm-6">
            <article class="clearfix bg-white margin-t10 margin-b10 padding20" itemscope itemtype="http://schema.org/Article">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="thumbnail border-0 center">
                            <a href="/romeo/${s.id}.html" title="${s.title}" itemprop="url">
                                <img itemprop="image" src="${s.img == null ? 'https://northpark.cn/statics/img/davatar.jpg' : s.img}" 
                                     class="imgbreath" alt="${s.title}">
                            </a>
                            <p><label class="bold-text cutline" itemprop="headline" title="${s.title}">${s.title}</label></p>
                        </div>
                    </div>
                    
                    <div class="col-sm-8">
                        <p>
                            <time itemprop="datePublished" datetime="${s.date}" class="label label-gray">${s.date}</time>
                            <a href="/romeo/${s.id}.html#comment">
                                <small class="label label-gray"><span class="glyphicon glyphicon-comment margin5"></span></small>
                            </a>
                        </p>
                        <div itemprop="description" id="brief_${ss.index}">
                            ${s.brief}
                        </div>
                        <div class="clearfix hidden" id="text_${ss.index}">
                                ${s.article }
                        </div>

                    </div>
                </div>
            </article>
        </div>
    </c:forEach>


</div>


