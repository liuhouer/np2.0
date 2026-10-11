<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimal-ui">
<meta name="theme-color" content="#45d0c6">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<meta http-equiv="Content-Language" content="zh-CN">

<meta name="author" content="NorthPark">
<meta name="robots" content="index,follow,archive">
<link rel="shortcut icon"
	href="/static/img/favicon.ico">
<title>${model.title} - NorthPark学习频道 | 在线课程与知识分享</title>
<meta name="keywords"
	content="${model.title},${model.tags},在线学习,免费教程,技术分享,学习笔记,${model.tags}教程,NorthPark学习,知识分享,技能提升">
<meta name="description" content="${model.title} - ${fn:substring(model.brief, 0, 120)}。NorthPark学习频道为您提供详细的${model.tags}相关教程和学习资源，助力您快速掌握新技能，提升职场竞争力。">

<!-- 动态canonical链接 -->
<link rel="canonical" href="https://northpark.cn/learning/post-${model.id}.html" />

<!-- Open Graph 标签 -->
<meta property="og:title" content="${model.title} - NorthPark学习频道">
<meta property="og:description" content="${fn:substring(model.brief, 0, 150)}">
<meta property="og:type" content="article">
<meta property="og:url" content="https://northpark.cn/learning/post-${model.id}.html">
<meta property="og:image" content="${model.briefImg != null && model.briefImg != '' ? model.briefImg : '/static/img/learning-default.jpg'}">
<meta property="article:published_time" content="${model.postDate}">
<meta property="article:tag" content="${model.tags}">

<!-- Twitter Card 标签 -->
<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:title" content="${model.title}">
<meta name="twitter:description" content="${fn:substring(model.brief, 0, 150)}">
<meta name="twitter:image" content="${model.briefImg != null && model.briefImg != '' ? model.briefImg : '/static/img/learning-default.jpg'}">

<%@ include file="/WEB-INF/views/page/common/common.jsp"%>

<script type="application/ld+json">
{
  "@context": "https://schema.org",
  "@type": "Article",
  "headline": "${model.title}",
  "datePublished": "${model.postDate}",
  "description": "${fn:substring(model.brief, 0, 150)}",
  "keywords": "${model.tags}",
  "author": {
    "@type": "Organization",
    "name": "NorthPark"
  },
  "publisher": {
    "@type": "Organization",
    "name": "NorthPark",
    "logo": {
      "@type": "ImageObject",
      "url": "/static/img/favicon.ico"
    }
  }
}
</script>
</head>

<body>

	<%@ include file="/WEB-INF/views/page/common/navigation.jsp"%>

	<div class="clearfix maincontent grayback">
		<div class="container mainbody">
			<div class="row">

				<div class="col-md-12">
					<div class="col-sm-10  col-md-offset-1 ">

						<!-- 面包导航 -->
						<ol class="breadcrumb" style="background-color: transparent">
							<li><a href="/"><i class="fa fa-home"></i> 首页</a></li>
							<li><a href="/learning">学习</a></li>
							<li class="active">${model.title}</li>
						</ol>

						<div class="clearfix bg-white margin-t10 margin-b10 padding20"
							id="J_white_div">
							<div class="row margin10 post_article">

								<div class="border-0 center">
									<p oid="${model.id }">
									<h1>
										<small class="green-text"><font size="5"><strong>${model.title}</strong></font>
										</small>
									</h1>
									</p>

									<c:if test="${user!=null }">
										<c:if
											test="${user.email == '654714226@qq.com' || user.email == 'qhdsoft@126.com' || user.email == 'woaideni@qq.com'}">
											<span class=" glyphicon glyphicon-arrow-up margin10"></span>
											<a class="common-a-right" title="置顶" href=""
												onclick="handup('${model.id}')">置顶</a>
											<span class=" glyphicon glyphicon-eye-close margin10"></span>
											<a class="common-a-right" title="隐藏" href=""
												onclick="hideup('${model.id}')">大尺度隐藏</a>
											<span class="glyphicon glyphicon-pencil margin10"></span>
											<a class="common-a-right" title="编辑"
												href="/learning/edit/${model.id}">快速编辑</a>
										</c:if>
									</c:if>


									<div class="clearfix visible-xs">
										<hr>
									</div>


								</div>

								<div class="margin20">

									<p id="brief_${model.id }">${model.content }</p>

									<c:if test="${model.path!=null && model.path!=''}">
										<p class="col-md-12">
										<div class="dashed center col-md-10 padding-b20">

											<c:if test="${user==null }">
												<p class="center hidden-content">
													本文隐藏内容 <a target="_blank" class="flatbtn"
														id="J_login_see"><i class="fa fa-sign-in padding5"></i>登录</a>
													后才可以浏览
												</p>
											</c:if>
											<c:if test="${user!=null }">
												<p id="J_show_path">${model.path }</p>
											</c:if>

									        <small class="text-muted padding-l10">资源失效、缺失、错误 不要慌 </small>
											<span class="fa fa-hand-o-right padding-l10"></span>
											<input id="J_feedback_btn" class="btn tag-node" style="width:150px;" type="button" value="戳我,失效反馈">


										</div>
										</p>
									</c:if>

									<div class="clearfix visible-xs">
										<hr>
									</div>


									<!-- 打赏 -->
									<div class="col-md-10">
										<div
											style="padding: 10px 0; margin: 20px auto; width: 90%; text-align: center">
											<div class="margin10">生活不止苟且,还有我喜爱的海岸.</div>
											<button id="rewardButton" ,="" disable="enable"
												onclick="var qr = document.getElementById('QR'); if (qr.style.display === 'none') {qr.style.display='block';} else {qr.style.display='none'}"
												style="cursor: pointer; border: 0; outline: 0; border-radius: 100%; padding: 0; margin: 0; letter-spacing: normal; text-transform: none; text-indent: 0px; text-shadow: none">
												<span
													onmouseover="this.style.color='rgb(236,96,0)';this.style.background='rgb(204,204,204)'"
													onmouseout="this.style.color='#fff';this.style.background='rgb(236,96,0)'"
													style="display: inline-block; width: 70px; height: 70px; border-radius: 100%; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: 400; font-stretch: normal; font-size: 35px; line-height: 75px; font-family: microsofty; background: rgb(236, 96, 0);">赏</span>
											</button>
											<div id="QR" style="display: none;">

												<div id="wechat"
													style="display: inline-block; margin-right: 20px;">
													<a
														href="https://minioapi.northpark.cn/pic/wxpay.png"
														class="fancybox" rel="group"><img id="wechat_qr"
														src="https://minioapi.northpark.cn/pic/wxpay.png"
														alt="Bruce WeChat Pay"
														style="width: 200px; height: 200px; max-width: 100%; display: inline-block"></a>
													<p>微信打赏</p>
												</div>


												<div id="alipay"
													style="display: inline-block; margin-right: 20px;">
													<a
														href="https://minioapi.northpark.cn/pic/alipay.png"
														class="fancybox" rel="group"><img id="alipay_qr"
														src="https://minioapi.northpark.cn/pic/alipay.png"
														alt="Bruce Alipay"
														style="width: 200px; height: 200px; max-width: 100%; display: inline-block"></a>
													<p>支付宝打赏</p>
												</div>

											</div>
										</div>
									</div>
									<!-- 打赏 -->


									<div class="clearfix visible-xs">
										<hr>
									</div>

									<!-- 反馈失效的资源 -->
									<div class="col-md-10" id="J_container_feed"></div>

									<div class="clearfix visible-xs">
										<hr>
									</div>

								<!-- northpark评论模块 -->
								<div id="comment" class="col-md-10" >
									<hr>
									<%--展示评论详情--%>
									<div class="clearfix" id="stuffCommentBox_${model.id }">


									</div>


									<div id="J_progress" class="center padding-t20"></div>


									<div class="form-group clearfix note-comment margin-t10 " id="comment_${model.id }">
                         			<textarea id="input_cm_${model.id }" placeholder="说点什么吧..."
											  class="form-control bg-lyellow" rows="3"></textarea>

										<button title="发布评论"
												class="btn btn-hero margin-t5 click2save"
												topic-id="${model.id }"
												topic-type="8"
												from-uid="${user.id}"
												from-uname="${user.username}"
												data-input="#input_cm_${model.id }">
											<span class="fa fa-floppy-o padding5"></span>发布评论</button>


									</div>


								</div>
								<!-- northpark评论模块 -->

								</div>


							</div>


						</div>


					</div>
				</div>
			</div>


		</div>
	</div>

	<%@ include file="/WEB-INF/views/page/common/container.jsp"%>
	<script type="text/javascript">
		$(function() {

			//设置标签颜色
			$(".tag-node").each(function () {
				$(this).css("backgroundColor", getRandomColor());
				$(this).css("color", "#fff");
			});



			//设置定向uri
			$("#J_login_see").attr("href",
					"/login?redirectURI=" + window.location.href);

			//feedback
			$("#J_feedback_btn").click(function() {
				var u = '${user.id}';
				if (u) {
					var spanID = '${model.id}';
					var title = '${model.title}';
					var href = window.location.href;
					var data = {
						"spanID" : spanID,
						"title" : title,
						"href" : href,
						"uID"  : u
					};

					$.ajax({
						url : "/movies/resFeedBack",
						type : "post",
						data : JSON.stringify(data),
						dataType : "json",//返回结果按照json接收
						contentType : "application/json",//提交的数据类型
						success : function(msg) {
							console.log(msg);
							if (msg.data == "success") {
								art.dialog.tips('反馈成功，站长接下来会更新资源');
								//list feedback
								feedbackList();
							} else {
								art.dialog.tips('error happened.');
							}
						}
					});

				} else {
					art.dialog.tips('登录后再试.');
				}
			});

			//list feedback
			feedbackList();

			//展示全文和评论详情-- northpark评论模块 --
			loadComment('${model.id }', 8);


		})

		//1、随机色的函数-rgb
		function getRandomColor() {
			var rgb = 'rgb(' + Math.floor(Math.random() * 255) + ','
					+ Math.floor(Math.random() * 255) + ','
					+ Math.floor(Math.random() * 255) + ')';
			console.log(rgb);
			return rgb;
		}

		/* get feed back */
		function feedbackList() {
			$.ajax({
				url : "/movies/getFeedBack",
				type : "get",
				success : function(data) {
					$("#J_container_feed").empty().append(data);
				}
			});

		}

		function handup(id) {
			$.ajax({
				url : "/learning/handup",
				type : "post",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(msg) {
					if (msg.data == "success") {
						art.dialog.tips('置顶成功');
					} else {
						art.dialog.tips('error happened.');
					}
				}
			});

		}

		function hideup(id) {
			$.ajax({
				url : "/learning/hideup",
				type : "post",
				data : {
					"id" : id
				},
				dataType : "json",
				success : function(msg) {
					if (msg.data == "success") {
						art.dialog.tips('隐藏成功');
						window.location.href = window.location.href;
					} else {
						art.dialog.tips('error happened.');
					}
				}
			});

		}

		function beforeSend(XMLHttpRequest) {
			$("#J_progress").append("<div><img src='/static/img/loading.gif' style='width:48px;height:48px;' /></div>");
		}

		function complete(XMLHttpRequest, textStatus) {
			$("#J_progress").empty();
		}

	</script>


</body>
</html>
