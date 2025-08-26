<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <meta name="theme-color" content="#45d0c6">
    <meta name="format-detection" content="telephone=no">
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">

    <!-- canonical链接 -->
    <link rel="canonical" href="https://northpark.cn/poem/enjoy/${poem_enjoy.id}.html" />

    <!-- Open Graph 标签 -->
    <meta property="og:title" content="${poem_enjoy.title} - ${poem_enjoy.author} | 诗词赏析">
    <meta property="og:description" content="${poem_enjoy.title} - ${poem_enjoy.author}(${poem_enjoy.years})：${fn:substring(poem_enjoy.content1,0,150)}">
    <meta property="og:type" content="article">
    <meta property="og:url" content="https://northpark.cn/poem/enjoy/${poem_enjoy.id}.html">
    <meta property="og:image" content="https://northpark.cn/statics/img/poem-default.jpg">
    <meta property="article:author" content="${poem_enjoy.author}">
    <meta property="article:published_time" content="${poem_enjoy.years}">

    <!-- Twitter Card 标签 -->
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="${poem_enjoy.title} - ${poem_enjoy.author}">
    <meta name="twitter:description" content="${fn:substring(poem_enjoy.content1,0,150)}">
    <meta name="twitter:image" content="https://northpark.cn/statics/img/poem-default.jpg">

    <!-- 结构化数据 -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "CreativeWork",
      "name": "${poem_enjoy.title}",
      "author": {
        "@type": "Person",
        "name": "${poem_enjoy.author}"
      },
      "dateCreated": "${poem_enjoy.years}",
      "text": "${poem_enjoy.content1}",
      "inLanguage": "zh-CN",
      "genre": "诗词",
      "publisher": {
        "@type": "Organization",
        "name": "NorthPark诗词秀"
      }
    }
    </script>
    <title>${poem_enjoy.title} - ${poem_enjoy.author} | 诗词赏析鉴赏 | NorthPark诗词秀</title>
    <meta name="keywords" content="${poem_enjoy.title},${poem_enjoy.author},${poem_enjoy.years},${poem_enjoy.title}赏析,${poem_enjoy.author}诗词,诗词鉴赏,古诗文,${poem_enjoy.years}诗词,NorthPark诗词秀">
    <meta name="description"
          content="${poem_enjoy.title} - ${poem_enjoy.author}(${poem_enjoy.years})：${fn:substring(poem_enjoy.content1,0,100)}。NorthPark诗词秀为您提供详细的诗词赏析、作者介绍和创作背景，深度解读经典诗词之美。">

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
