<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimal-ui">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <meta http-equiv="Content-Language" content="zh-CN">

    <meta name="author" content="NorthPark">
    <meta name="robots" content="index,follow,archive">
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    <title>${poem_enjoy.title }-${poem_enjoy.author }:诗词秀 | NorthPark</title>
    <meta name="keywords" content="${poem_enjoy.title},${poem_enjoy.author},${poem_enjoy.years},诗词赏析,古诗文,NorthPark">
    <meta name="description"
          content="${poem_enjoy.title} - ${poem_enjoy.author}：${poem_enjoy.content1}">

    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
    <style>
        body {
            background: #f5f5f5;
            color: #333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .poem-container {
            flex: 1;
            max-width: 800px;
            margin: 120px auto 40px;
            padding: 30px;
            background: #fff;
            box-shadow: 0 2px 12px rgba(0,0,0,0.1);
            border-radius: 8px;
            position: relative;
            overflow: hidden;
        }

        .poem-container::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(to right, #ff6b6b, #ff8787);
        }

        .poem-title {
            font-size: 32px;
            color: #333;
            text-align: center;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .poem-author {
            font-size: 18px;
            color: #666;
            text-align: center;
            margin-bottom: 30px;
            font-style: italic;
        }

        .poem-content {
            font-size: 20px;
            line-height: 2;
            color: #444;
            text-align: center;
            margin: 30px 0;
            letter-spacing: 1px;
            white-space: pre-line;
        }

        .poem-divider {
            width: 50px;
            height: 2px;
            background: #ff6b6b;
            margin: 30px auto;
        }

        footer {
            margin-top: auto;
        }
    </style>
</head>

<body>

<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<!-- <div id="mydiv" style="height:500px;"></div> -->


<div class="poem-container">
    <h1 class="poem-title">${poem_enjoy.title}</h1>
    <div class="poem-divider"></div>
    <div class="poem-author">${poem_enjoy.author}</div>
    <div class="poem-content">${poem_enjoy.content1}</div>
    <div class="poem-divider"></div>
    <div class="poem-content">${poem_enjoy.enjoys}</div>
</div>


<%@ include file="/WEB-INF/views/page/common/container.jsp" %>
<script type="text/javascript">
    $(function() {
        function adjustHeight() {
            var windowHeight = $(window).height();
            var headerHeight = $('header').outerHeight();
            var footerHeight = $('footer').outerHeight();
            var minContentHeight = windowHeight - headerHeight - footerHeight;
            $('.poem-container').css('min-height', minContentHeight + 'px');
        }

        $(window).on('resize load', adjustHeight);
    });
</script>


</body>
</html>
