<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimal-ui">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Language" content="zh-CN">
        <meta name="description" content="NorthPark影视">
        <meta name="keywords" content="NorthPark">
        <meta name="author" content="bruce">
        <meta name="robots" content="index,follow,archive">
        <title>NorthPark / 添加课程|书籍</title>

        <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
        <!-- Quill.js 富文本编辑器 -->
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
        <script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>

</head>

<body>
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>


<div class="clearfix maincontent">
        <div class="container">
                <div class="mainbody" style="margin-top: 5em;">
                        <div class="align-center bg-white radius-5 padding10 max-width-700 min-width-300">
                                <form method="POST" action="/learning/addItem" accept-charset="UTF-8" role="form" id="addItemForm"
                                      style="color: #444;" class="form margin-t20" enctype="multipart/form-data">
                                        <div class="clearfix">
                                                <h4>
                                                        <span class="glyphicon glyphicon-plus"></span> 添加学习资源
                                                </h4>
                                                <hr>
                                        </div>
                                        <input type="hidden" name="id" value="${model.id }"/>
                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span> 课程|书籍
                                                <input id="J_name" placeholder="课程/书籍" required
                                                       class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                                       name="title" type="text" value="${model.title }">
                                        </div>
                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span>下载地址
                                                <div id="J_path_editor" style="height: 200px;"></div>
                                                <textarea id="J_path" name="path" style="display: none;">${model.path }</textarea>
                                        </div>
                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span>学习颜色
                                                <input id="J_color" placeholder="学习颜色" required
                                                       class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                                       name="color" type="text" value="${model.color }">
                                        </div>
                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span>学习标签
                                                <input id="J_tag" placeholder="学习标签" required
                                                       class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                                       name="tags" type="text" value="${model.tags },课程分享">
                                        </div>
                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span>学习标签-英文
                                                <input id="J_tag_code" placeholder="学习标签-英文" required
                                                       class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                                       name="tagsCode" type="text" value="${model.tagsCode },classhare">
                                        </div>

                                        <div class="form-group ">
                                                <span class="glyphicon glyphicon-star"></span>定价
                                                <input id="J_price" placeholder="学习定价" required
                                                       class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                                       name="price" type="number" value="${model.price }">
                                        </div>

                                        <div class="form-group">
                                                <span class="glyphicon glyphicon-star"></span>学习预览
                                                <div id="J_md_brief_editor" style="height: 200px;"></div>
                                                <textarea id="J_md_brief" name="brief" style="display: none;"><c:out value="${model.brief }" escapeXml="true"></c:out><c:if test="${model.brief ==null}">—/马哥/0000000000000马哥Linux高端运维云计算就业班-教学总监老王主讲/
├──1-01-课程架构介绍和计算机基础.mp4 147.51M
├──1-02-计算机硬件组成.mp4 341.69M
├──1-03-操作系统基础.mp4 205.91M
├──1-04-Linux介绍.mp4 361.36M
├──10-1文本处理三剑客之sed.mp4 620.57M
├──11-01-软件管理基础.mp4 216.81M</c:if></textarea>
                                        </div>

                                        <div class="form-group">
                                                <span class="glyphicon glyphicon-star"></span>学习内容
                                                <div id="J_md_text_editor" style="height: 200px;"></div>
                                                <textarea id="J_md_text" name="content" style="display: none;"><c:out value="${model.content }" escapeXml="true"></c:out><c:if test="${model.content ==null}">—/马哥/0000000000000马哥Linux高端运维云计算就业班-教学总监老王主讲/
├──1-01-课程架构介绍和计算机基础.mp4 147.51M
├──1-02-计算机硬件组成.mp4 341.69M
├──1-03-操作系统基础.mp4 205.91M
├──1-04-Linux介绍.mp4 361.36M
├──10-1文本处理三剑客之sed.mp4 620.57M
├──11-01-软件管理基础.mp4 216.81M</c:if></textarea>
                                        </div>



                                        <div class="form-group">
                                                <input id="formSubmit" data-activetext="添加 ››"
                                                       class="btn btn-hero btn-xlg margin-t10 grid50" value="添加"
                                                       type="button">
                                        </div>
                                </form>
                        </div>

                        <br>
                        <br>

                </div>


        </div>
</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>


<script type="text/javascript">
        $(function () {
                // Quill.js 编辑器配置
                const toolbarOptions = [
                        ['bold', 'italic', 'underline', 'strike'],
                        ['blockquote', 'code-block'],
                        [{ 'header': 1 }, { 'header': 2 }],
                        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                        [{ 'script': 'sub'}, { 'script': 'super' }],
                        [{ 'indent': '-1'}, { 'indent': '+1' }],
                        [{ 'direction': 'rtl' }],
                        [{ 'size': ['small', false, 'large', 'huge'] }],
                        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                        [{ 'color': [] }, { 'background': [] }],
                        [{ 'font': [] }],
                        [{ 'align': [] }],
                        ['clean'],
                        ['link', 'image', 'video']
                ];

                // 初始化学习内容编辑器
                const contentEditor = new Quill('#J_md_text_editor', {
                        theme: 'snow',
                        modules: {
                                toolbar: toolbarOptions
                        }
                });

                // 初始化下载地址编辑器
                const pathEditor = new Quill('#J_path_editor', {
                        theme: 'snow',
                        modules: {
                                toolbar: toolbarOptions
                        }
                });

                // 初始化学习预览编辑器
                const briefEditor = new Quill('#J_md_brief_editor', {
                        theme: 'snow',
                        modules: {
                                toolbar: toolbarOptions
                        }
                });

                // 设置初始内容
                const contentTextarea = document.getElementById('J_md_text');
                const pathTextarea = document.getElementById('J_path');
                const briefTextarea = document.getElementById('J_md_brief');

                if (contentTextarea.value.trim()) {
                        contentEditor.root.innerHTML = contentTextarea.value;
                }

                if (pathTextarea.value.trim()) {
                        pathEditor.root.innerHTML = pathTextarea.value;
                }

                if (briefTextarea.value.trim()) {
                        briefEditor.root.innerHTML = briefTextarea.value;
                }

                // 监听编辑器内容变化，同步到隐藏的textarea
                contentEditor.on('text-change', function() {
                        contentTextarea.value = contentEditor.root.innerHTML;
                });

                pathEditor.on('text-change', function() {
                        pathTextarea.value = pathEditor.root.innerHTML;
                });

                briefEditor.on('text-change', function() {
                        briefTextarea.value = briefEditor.root.innerHTML;
                });

                // 提交表单
                $("#formSubmit").click(function () {
                        // 确保在提交前同步编辑器内容到textarea
                        contentTextarea.value = contentEditor.root.innerHTML;
                        pathTextarea.value = pathEditor.root.innerHTML;
                        briefTextarea.value = briefEditor.root.innerHTML;

                        if ($("#J_name").val() && contentTextarea.value && $("#J_color").val() && pathTextarea.value && $("#J_tag").val() && $("#J_tag_code").val()) {
                                $.ajax({
                                        url: "/learning/addItem",
                                        type: "post",
                                        dataType: "json",
                                        data: $('#addItemForm').serialize(),
                                        success: function (msg) {
                                                if (msg.data == "success") {
                                                        art.dialog.tips('添加成功');
                                                        $('#formSubmit').attr("disabled", 'disabled');
                                                }
                                        }
                                });
                        } else {
                                art.dialog.tips('填写必要信息');
                        }
                });
        });
</script>
</body>
</html>
