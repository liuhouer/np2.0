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
    <title>NorthPark / 添加电影</title>

    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
    <!-- Quill.js 富文本编辑器 -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

</head>

<body>
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>


<div class="clearfix maincontent">
    <div class="container">
        <div class="mainbody" style="margin-top: 5em;">
            <div class="align-center bg-white radius-5 padding10 max-width-700 min-width-300">
                <form method="POST" action="/movies/addItem" accept-charset="UTF-8" role="form" id="addItemForm"
                      style="color: #444;" class="form margin-t20" enctype="multipart/form-data">
                    <div class="clearfix">
                        <h4>
                            <span class="glyphicon glyphicon-plus"></span> 添加电影资源
                        </h4>
                        <hr>
                    </div>
					<input type="hidden" name="id" value="${model.id }"/>
                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star"></span> 电影名
                        <input id="J_name" placeholder="电影名" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="movieName" type="text" value="${model.movieName }">
                    </div>
                    <div class="form-group ">

                         <span class="glyphicon glyphicon-star"></span>下载地址
                         <div id="J_path" style="height: 200px;"></div>
                         <input type="hidden" id="J_path_hidden" name="path" value="${model.path}">
                    </div>
					<div class="form-group ">
						<span class="glyphicon glyphicon-star"></span>电影颜色
                        <input id="J_color" placeholder="电影颜色" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="color" type="text" value="${model.color }">
                    </div>
                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star"></span>电影标签
                        <input id="J_tag" placeholder="电影标签" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="tag" type="text" value="${model.tag }">
                    </div>
                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star"></span>电影标签-英文
                        <input id="J_tag_code" placeholder="电影标签-英文" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="tagCode" type="text" value="${model.tagCode }">
                    </div>
                    <div class="form-group">
                        <span class="glyphicon glyphicon-star"></span>电影内容
							<div id="J_md_text" style="height: 200px;"></div>
							<input type="hidden" id="J_md_text_hidden" name="movieDesc" value="${model.movieDesc}">
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


<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/jquery.min.js"></script>
<script src="https://cdn.quilljs.com/1.3.6/quill.min.js"></script>
<script type="text/javascript">
    $(function () {
        var quill1 = new Quill('#J_md_text', {
            theme: 'snow',
            modules: {
                toolbar: [
                    [{ 'header': [1, 2, 3, false] }],
                    ['bold', 'italic', 'underline'],
                    [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                    ['link', 'blockquote', 'code-block'],
                    ['clean']
                ]
            }
        });

        var quill2 = new Quill('#J_path', {
            theme: 'snow',
            modules: {
                toolbar: [
                    [{ 'header': [1, 2, 3, false] }],
                    ['bold', 'italic', 'underline'],
                    [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                    ['link', 'blockquote', 'code-block'],
                    ['clean']
                ]
            }
        });

        // 表单提交时同步内容
        $('form').on('submit', function() {
            $('#J_md_text_hidden').val(quill1.root.innerHTML);
            $('#J_path_hidden').val(quill2.root.innerHTML);
        });

        //追加字符串
        //editor.append('##电影简介');

        //提交表单
        $("#formSubmit").click(function () {
            if ($("#J_name").val() && $("#J_md_text").val() && $("#J_color").val() && $("#J_path").val() && $("#J_tag").val() && $("#J_tag_code").val() ) {

                $.ajax({
                    url: "/movies/addItem",
                    type: "post",
                    dataType: "json",
                    data: $('#addItemForm').serialize(),// 要提交的表单 ,
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
