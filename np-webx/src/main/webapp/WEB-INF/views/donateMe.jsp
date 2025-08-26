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
			<link rel="shortcut icon" href="/static/img/favicon.ico">
			<title>
				赞助本站 | NorthPark
			</title>
			<meta name="keywords" content="赞助NorthPark,打赏NorthPark,支持NorthPark,NorthPark捐赠">
			<meta name="description" content="NorthPark中文网是一个免费分享MAC应用和影视资源的网站,目前注册用户5万+。您的支持将帮助我们提供更好的服务!">
			<%@ include file="/WEB-INF/views/page/common/common.jsp" %>
			<style>
				hr{
					border: 1px solid #dedede
				}
				.well {
					background-color: #f9f9f9;
					border-radius: 3px;
				}

				.panel {
					border-radius: 3px;
					box-shadow: 0 1px 3px rgba(0,0,0,.1);
				}

				.nav-pills > li > a {
					border-radius: 3px;
				}

				.list-unstyled li {
					padding: 5px 0;
				}

				.margin-t20 {
					margin-top: 20px;
				}

				.text-center img {
					margin: 0 auto;
				}

				.payment-section {
					margin: 20px 0;
					padding: 20px;
					background: #fff;
					border-radius: 4px;
					box-shadow: 0 1px 3px rgba(0,0,0,.05);
				}

				.payment-section h4 {
					margin-bottom: 20px;
					padding-bottom: 10px;
					border-bottom: 1px solid #eee;
				}

				.payment-qrcode {
					text-align: center;
					padding: 15px;
					background: #f9f9f9;
					border-radius: 4px;
					margin: 15px 0;
				}

				.payment-qrcode img {
					max-width: 200px;
					margin: 0 auto;
				}

				.payment-tip {
					color: #666;
					font-size: 14px;
					line-height: 1.6;
					margin: 10px 0;
				}

				.contact-section {
					margin-top: 30px;
					padding: 20px;
					background: #f9f9f9;
					border-radius: 4px;
				}

				.contact-section a {
					color: #337ab7;
				}

				.contact-section a:hover {
					text-decoration: underline;
				}

				.comment-input-wrapper {
					background: #fff;
					padding: 15px;
					border-radius: 4px;
					box-shadow: 0 1px 3px rgba(0,0,0,.05);
				}

				.comment-input-wrapper textarea {
					border: 1px solid #e8e8e8;
					resize: vertical;
					transition: all .3s;
				}

				.comment-input-wrapper textarea:focus {
					border-color: #337ab7;
					box-shadow: none;
				}

				.btn-hero {
					background: #337ab7;
					color: #fff;
					border: none;
					padding: 8px 20px;
					border-radius: 4px;
					transition: all .3s;
				}

				.btn-hero:hover {
					background: #286090;
					color: #fff;
				}
			</style>
			<%-- 引入捐助相关样式 --%>
			<link href="/static/css/donate.css" rel="stylesheet">
		</head>
		<body style="">
			<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>
				<!-- 页面标题 -->
				<div class="clearfix maincontent grayback">
					<div class="container mainbody">
						<div class="col-sm-7" style="color:#666">
							<div class="content margin-b20">
								<h1 class="h4" style="color:#333">赞助NorthPark</h1>
								<br>
								<div class="well well-sm">
									<p><i class="fa fa-heart"></i> NorthPark中文网为出于兴趣所作,所有资源均免费分享</p>
									<p><i class="fa fa-users"></i> 目前注册用户5万+,每日活跃用户数千人</p>
									<p class="text-danger"><i class="fa fa-exclamation-circle"></i> 服务器、带宽等支出压力较大,需要您的支持!</p>
								</div>
								<div class="payment-section">
									<h4><i class="fa fa-star"></i> 我们的特色</h4>
									<div class="row">
										<div class="col-sm-6">
											<ul class="list-unstyled">
												<li><i class="fa fa-check text-success"></i> 资源永久免费</li>
												<li><i class="fa fa-check text-success"></i> 高效及时响应</li>
												<li><i class="fa fa-check text-success"></i> 高速下载通道</li>
											</ul>
										</div>
										<div class="col-sm-6">
											<ul class="list-unstyled">
												<li><i class="fa fa-check text-success"></i> 每日资源更新</li>
												<li><i class="fa fa-check text-success"></i> 安全无后门</li>
												<li><i class="fa fa-check text-success"></i> 终生技术支持</li>
											</ul>
										</div>
									</div>
								</div>
								<br>
								<div class="payment-section" style="margin-top: -10px;">
									<h4><i class="fa fa-alipay"></i> 支付宝支付</h4>
									<p class="payment-tip">
										<i class="fa fa-info-circle"></i> 在手机上使用【支付宝钱包】，使用【扫一扫】功能扫描下方二维码进行赞助
									</p>
									<div class="payment-qrcode">
										<img src="https://minioapi.northpark.cn/pic/alipay.png" alt="支付宝支付">
									</div>
								</div>
								<div class="payment-section">
									<h4><i class="fa fa-weixin"></i> 微信支付</h4>
									<p class="payment-tip">
										<i class="fa fa-info-circle"></i> 打开微信，使用【扫一扫】功能扫描下方二维码进行赞助
									</p>
									<div class="payment-qrcode">
										<img src="https://minioapi.northpark.cn/pic/wxpay.png" alt="微信支付">
									</div>
								</div>
								<div class="payment-section">
									<h4><i class="fa fa-paypal"></i> PayPal支付</h4>
									<p class="payment-tip">
										<i class="fa fa-info-circle"></i> 点击下方链接跳转到PayPal进行赞助
									</p>
									<p style="text-align:center">
										<a href="https://paypal.me/liuhouer" target="_blank" rel="nofollow" class="btn btn-primary">
											<i class="fa fa-paypal"></i> 前往PayPal赞助
										</a>
									</p>
								</div>
								<div class="contact-section">
									<h4><i class="fa fa-envelope"></i> 联系我们</h4>
									<p class="payment-tip">
										如果您有任何问题或建议，欢迎通过以下方式联系我们:
									</p>
									<p>
										<i class="fa fa-envelope-o"></i> Email:
										<a href="mailto:zhangyang.z@icloud.com">zhangyang.z@icloud.com</a>
									</p>
								</div>
							</div>

							<!-- northpark评论模块 -->
							<div id="comment" class="row payment-section">
								<h4><i class="fa fa-comments"></i> 留言板</h4>
								<%--展示评论详情--%>
								<div class="clearfix" id="stuffCommentBox_999999">


								</div>


								<div id="J_progress" class="center padding-t20"></div>


								<div class="form-group clearfix note-comment margin-t10 " id="comment_999999">
									<div class="comment-input-wrapper">
										<textarea id="input_cm_999999" placeholder="说点什么吧..."
											class="form-control"
											rows="3"></textarea>


										<button title="发布评论"


												class="btn btn-hero margin-t5 click2save"
												topic-id="999999"
												topic-type="7"
												from-uid="${user.id}"
												from-uname="${user.username}"
												data-input="#input_cm_999999">
											<span class="fa fa-floppy-o padding5"></span>发布评论</button>


									</div>
								</div>


							</div>
							<!-- northpark评论模块 -->

						</div>
						<div class="col-sm-offset-1 col-sm-4">
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
					</div>
				</div>
				<%@ include file="/WEB-INF/views/page/common/container.jsp" %>
					<script src="/static/js/page/forget.js">
					</script>
					<script  >
						loadDonates(1);
						//展示全文和评论详情-- northpark评论模块 --
						loadComment(999999, 7);

						function beforeSend(XMLHttpRequest) {
							$("#J_progress").append("<div><img src='/static/img/loading.gif' style='width:48px;height:48px;' /></div>");
						}


						function complete(XMLHttpRequest, textStatus) {
							$("#J_progress").empty();
						}
					</script>
		</body>

	</html>
