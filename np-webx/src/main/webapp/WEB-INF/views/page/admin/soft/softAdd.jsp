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
    <meta name="description" content="NorthPark软件">
    <meta name="keywords" content="NorthPark">
    <meta name="author" content="bruce">
    <meta name="robots" content="index,follow,archive">
    <title>NorthPark / 添加软件</title>

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
                <form method="POST" action="/soft/addItem" accept-charset="UTF-8" role="form" id="addItemForm"
                      style="color: #444;" class="form margin-t20" enctype="multipart/form-data">
                    <div class="clearfix">
                        <h4>
                            <span class="glyphicon glyphicon-plus"></span> 添加软件资源
                        </h4>
                        <hr>
                    </div>
					<input type="hidden" name="id" value="${model.id }"/>
                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star padding-b20"></span> 标题
                        <input id="J_name" placeholder="标题" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="title" type="text" value="${model.title }">
                    </div>
                    <div class="form-group ">
						<span class="glyphicon glyphicon-star padding-b20"></span>采集地址【链接请求地址】
                        <input placeholder="采集地址【链接请求地址】" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="retCode" type="text" value="${model.retCode }">
                    </div>
                    <div class="form-group ">

                         <span class="glyphicon glyphicon-star padding-b20"></span>下载地址
                         <div id="J_path" style="height: 200px;"></div>
                         <input type="hidden" id="J_path_hidden" name="path" value="${model.path}">
                    </div>

                    <div class="form-group">
                    		<span class="glyphicon glyphicon-star padding-b20"></span>软件简介
							<div id="J_brief" style="height: 200px;"></div>
							<input type="hidden" id="J_brief_hidden" name="brief" value="${model.brief}">
                    </div>

                    <div class="form-group">
                    		<span class="glyphicon glyphicon-star padding-b20"></span>软件详情
							<div id="J_md_text" style="height: 200px;"></div>
							<input type="hidden" id="J_md_text_hidden" name="content" value="${model.content}">
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="useMinio" value="1"  ${model.useMinio == 1 ? 'checked' : ''} > 使用Minio
                        </label>
                    </div>


                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star padding-b20 "></span>操作系统
                        <input  placeholder="操作系统" required
                                class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                                name="os" type="text" value="mac">
                    </div>

                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star"></span>软件颜色
                        <input id="J_color" placeholder="软件颜色" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="color" type="text" value="${model.color }">
                    </div>

                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star"></span>置顶编号
                        <input id="J_hotIndex" placeholder="置顶编号" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="hotIndex" type="number" value="${model.hotIndex }">
                    </div>

                    <div class="form-group ">
                        <span class="glyphicon glyphicon-star padding-b20 "></span>标签tips
                        <div id="J_tag_tips" style="height: 200px;"></div>
                        <input type="hidden" id="J_tag_tips_hidden" name="tagTips" value="001 系统、应用软件<div>002 开发、设计软件</div><div>003 媒体软件</div><div>004 网络、安全软件</div><div>005 其他软件</div><div>006 游戏一箩筐</div><div>007 限免软件</div><div>008 疑难杂症</div><div>005 其他软件</div>">
                    </div>

                    <div class="form-group ">
						<span class="glyphicon glyphicon-star padding-b20 "></span>标签
                        <input  placeholder="标签" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="tags" type="text" value="${model.tags }">
                    </div>



                    <div class="form-group ">
						<span class="glyphicon glyphicon-star  padding-b20"></span>英文标签
                        <input  placeholder="英文标签" required
                               class="form-control  input-lg  border-light-1 bg-lyellow grid98 radius-0"
                               name="tagsCode" type="text" value="${model.tagsCode }">
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
        // 初始化多个 Quill 编辑器
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

        var quill3 = new Quill('#J_brief', {
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

        var quill4 = new Quill('#J_tag_tips', {
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
            $('#J_brief_hidden').val(quill3.root.innerHTML);
            $('#J_tag_tips_hidden').val(quill4.root.innerHTML);
        });
            ]
        });



        //追加字符串
        //editor.append('##软件简介');

        //提交表单
        $("#formSubmit").click(function () {
            if ($("#J_name").val() && $("#J_md_text").val()  && $("#J_path").val()) {

                $.ajax({
                    url: "/soft/addItem",
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
