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
<title>${article.title} | Mac软件下载 | NorthPark</title>
<meta name="keywords"
	content="${article.title},${article.title}下载,${article.title}破解版,${article.tags},Mac软件,macOS应用,${article.os}软件,免费下载,NorthPark">
<meta name="description" content="${article.title} for Mac免费下载 - ${soft_desc}。支持${article.os}系统，提供安装教程和使用指南，NorthPark为您提供安全可靠的Mac软件下载服务。">

<!-- 动态canonical链接 -->
<link rel="canonical" href="https://northpark.cn/soft/${article.retCode}.html" />

<!-- Open Graph 标签 -->
<meta property="og:title" content="${article.title} | Mac软件下载">
<meta property="og:description" content="下载${article.title}。${soft_desc}">
<meta property="og:type" content="article">
<meta property="og:url" content="https://northpark.cn/soft/${article.retCode}.html">
<meta property="og:image" content="https://minioapi.northpark.cn/pic/mac-banner.jpg">
<meta property="article:published_time" content="${article.postDate}">
<meta property="article:tag" content="${article.tags}">

<!-- Twitter Card 标签 -->
<meta name="twitter:card" content="summary_large_image">
<meta name="twitter:title" content="${article.title}">
<meta name="twitter:description" content="Mac软件下载 - ${article.title}">
<meta name="twitter:image" content="https://minioapi.northpark.cn/pic/mac-banner.jpg">
<%@ include file="/WEB-INF/views/page/common/common.jsp"%>
<style>
	.bold-line {
		border-top: 2px solid;
		color: #ebeff0;
	}
</style>


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
							<li><a href="/soft/${article.os}">软件下载</a></li>
							<li class="active">${article.title}</li>
						</ol>

						<!-- 结构化数据 -->
						<script type="application/ld+json">
						{
							"@context": "https://schema.org",
							"@type": "SoftwareApplication",
							"name": "${article.title}",
							"description": "${soft_desc}",
							"operatingSystem": "${article.os}",
							"applicationCategory": "${article.tags}",
							"datePublished": "${article.postDate}",
							"url": "https://northpark.cn/soft/${article.retCode}.html",
							"publisher": {
								"@type": "Organization",
								"name": "NorthPark",
								"logo": {
									"@type": "ImageObject",
									"url": "/static/img/logo.png"
								}
							}
						}
						</script>

						<div class="clearfix bg-white margin-t10 margin-b10 padding20 "
							id="J_white_div" itemscope itemtype="http://schema.org/SoftwareApplication">
							<div class="row margin10 post_article">
								<div class="border-0 center">
									<p>
										<small class="green-text">
											<h1><font size="5">
												<strong itemprop="name">${article.title}</strong></font>
											</h1>
										</small>
									</p>
									<meta itemprop="operatingSystem" content="${article.os}">
									<meta itemprop="applicationCategory" content="${article.tags}">
									<meta itemprop="datePublished" content="${article.postDate}">



									<div class="clearfix visible-xs">
										<hr>
									</div>
								</div>
								<p>
									发表于： <strong><a class="common-a-right"
										title="${article.postDate}"
										href="/soft/date/${article.postDate }">
											${article.postDate} </a></strong> <strong><a
										class="common-a-right" title="${article.tags}"
										href="/soft/tag/${article.tagsCode }"> ${article.tags} </a></strong> <strong><a
										class="common-a-right" title="${article.os}"
										href="/soft/${article.os }"> ${article.os} </a></strong>

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
												<a class="common-a" title="编辑"
												   href="/soft/edit/${article.id}">快速编辑</a>
											</c:if>
										</c:if>
								</p>
								<p id="content_${article.id}">${article.content }</p>
<!-- 下载部分 -->
								<div class="download-section">
									<!-- 下载地址 -->
									<h4 class="download-title">
									  <i class="fa fa-download"></i> 下载地址
									</h4>

									<c:if test="${user==null}">
									  <div class="text-center">
										<p class="hidden-content">
										  本文隐藏内容
										  <a target="_blank" class="btn btn-primary" id="J_login_see">
											<i class="fa fa-sign-in"></i> 登录
										  </a>
										  后查看
										</p>
									  </div>
									</c:if>

									<c:if test="${user!=null}">
									  <p id="J_show_path">${article.path}</p>
									</c:if>

									<!-- 历史版本 -->
									<h4 class="download-title">
									  <i class="fa fa-history"></i> 历史版本
									</h4>

									<c:if test="${user != null}">
									  <table class="table table-bordered download-table">
										<thead>
										  <tr>
											<th width="60%">版本</th>
											<th width="40%">下载</th>
										  </tr>
										</thead>
										<tbody>
										  <c:forEach var="merge" items="${soft_merge_list}">
											<tr>
											  <td>${merge.title}</td>
											  <td>${merge.path}</td>
											</tr>
										  </c:forEach>
										</tbody>
									  </table>
									</c:if>

									<!-- 反馈区 -->
									<div class="feedback-section text-center">
									  <small class="text-muted">
										<i class="fa fa-info-circle"></i>
										资源失效、缺失、错误不要慌
									  </small>
									  <button id="J_feedback_btn" class="btn feedback-btn">
										<i class="fa fa-exclamation-circle"></i>
										反馈资源失效
									  </button>
									</div>
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
							</div>


							<!-- 反馈失效的资源 -->
							<div class="col-md-10 margin-b20" id="J_container_feed"></div>

							<div class="clearfix visible-xs">
								<hr>
							</div>

							<!-- northpark评论模块 -->
							<div id="comment" class="col-md-10" >
							    <hr>
								<%--展示评论详情--%>
								<div class="clearfix" id="stuffCommentBox_${article.id}">

								</div>

								<div id="J_progress" class="center padding-t20"></div>

								<div class="form-group clearfix note-comment margin-t10 " id="comment_${article.id}">
                                <textarea id="input_cm_${article.id}" placeholder="说点什么吧..."
										  class="form-control bg-lyellow"
										  rows="3"></textarea>

							    <button title="发布评论"

												class="btn btn-hero margin-t5 click2save"
												topic-id="${article.id}"
												topic-type="3"
												from-uid="${user.id}"
												from-uname="${user.username}"
												data-input="#input_cm_${article.id}">
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

	<%@ include file="/WEB-INF/views/page/common/container.jsp"%>



	<script type="text/javascript"   >
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
					var spanID = '${article.id}';
					var title = '${article.title}';
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
			loadComment('${article.id}', 3);

		});

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

		//1、随机色的函数-rgb
		function getRandomColor() {
			var rgb = 'rgb(' + Math.floor(Math.random() * 255) + ','
					+ Math.floor(Math.random() * 255) + ','
					+ Math.floor(Math.random() * 255) + ')';
			console.log(rgb);
			return rgb;
		}

		function handup(id) {
			$.ajax({
				url : "/soft/handup",
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
				url : "/soft/hideup",
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
