<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%-- 引入捐助相关样式 --%>
<link href="/static/css/donate.css" rel="stylesheet">

<div class="col-md-12 margin-t10">

    <div class="panel panel-info donate-panel">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-trophy"></i> 捐助榜</h3>
        </div>
        <div class="panel-body">
            <ul id="donateTab" class="nav nav-pills donate-tabs">
                <li class="active">
                    <a href="#1" onclick="loadDonates(1)" data-toggle="tab">
                        <i class="fa fa-star"></i> 大老板
                    </a>
                </li>
                <li>
                    <a href="#2" onclick="loadDonates(2)" data-toggle="tab">
                        <i class="fa fa-star-half-o"></i> 老板
                    </a>
                </li>
                <li>
                    <a href="#3" onclick="loadDonates(3)" data-toggle="tab">
                        <i class="fa fa-heart"></i> 好心人
                    </a>
                </li>
            </ul>
            <div id="donateContent" class="tab-content donate-content">
            </div>
        </div>
    </div>

</div>





