<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimal-ui">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">

        <!-- SEO优化 -->
        <title>小布静听 - 轻量级音乐播放器 | NorthPark</title>
        <meta name="keywords" content="小布静听,音乐播放器,MP3播放器,桌面歌词,NorthPark中文网,开源音乐播放器">
        <meta name="description" content="小布静听是一款轻量级的音乐播放器,支持桌面歌词、均衡器调节、ID3标签编辑等功能,界面简洁美观,操作简单易用。">
        <meta name="author" content="NorthPark">
        <meta name="robots" content="index,follow">

        <link rel="shortcut icon" href="/static/img/favicon.ico">

        <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

            <!-- 自定义样式 -->
            <style>
                .version-title {
                    margin: 30px 0 20px;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #eee;
                    font-size: 24px;
                    font-weight: bold;
                    color: #333;
                }

                .version-info {
                    margin-bottom: 15px;
                    color: #666;
                    font-size: 14px;
                }

                .version-features {
                    margin: 0 0 30px;
                    padding-left: 20px;
                }

                .version-features li {
                    margin: 8px 0;
                    color: #555;
                    line-height: 1.6;
                }

                .download-btn {
                    display: inline-block;
                    margin: 10px 0;
                    padding: 8px 20px;
                    border-radius: 4px;
                    background: #337ab7;
                    color: #fff;
                    text-decoration: none;
                }

                .download-btn:hover {
                    background: #286090;
                }

                .product-column {
                    padding: 0 15px;
                    border: 2px solid #eee;
                }

                .product-header {
                    text-align: center;
                    margin: 40px 0 30px;
                    padding: 20px;
                    border: 1px solid #eee;
                    border-radius: 8px;
                    background: #f9f9f9;
                }

                .product-header h2 {
                    margin: 0 0 10px;
                    color: #333;
                    font-size: 28px;
                }

                .product-header .lead {
                    margin: 0;
                    color: #666;
                    font-size: 16px;
                }

                .screenshot-container {
                    margin: 20px 0;
                    text-align: center;
                }

                .screenshot-container img {
                    max-width: 100%;
                    height: auto;
                    margin: 10px 0;
                    border: 1px solid #ddd;
                    border-radius: 4px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }

                .features-list {
                    background: #f8f9fa;
                    padding: 20px;
                    border-radius: 6px;
                    margin: 20px 0;
                }

                .features-list h4 {
                    margin: 0 0 15px;
                    color: #333;
                    font-size: 18px;
                }

                .features-list ul {
                    margin: 0;
                    padding-left: 20px;
                }

                .features-list li {
                    margin: 8px 0;
                    color: #555;
                    line-height: 1.6;
                }

                .github-link {
                    display: inline-block;
                    margin: 15px 0;
                    padding: 10px 20px;
                    background: #28a745;
                    color: #fff;
                    text-decoration: none;
                    border-radius: 4px;
                    font-weight: bold;
                }

                .github-link:hover {
                    background: #218838;
                    color: #fff;
                    text-decoration: none;
                }
            </style>
    </head>

    <body>
        <%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

            <div class="clearfix maincontent">
                <div class="container">
                    <div class="row" style="margin-top: 80px;">
                        <!-- 左侧列 - NorthPark Music Player -->
                        <div class="col-md-6 product-column" >
                            <div class="product-header">
                                <h2>NorthPark Music Player</h2>
                                <p class="lead">基于 Electron 的现代化本地音乐播放器</p>
                            </div>

                            <div class="features-list">
                                <h4>主要特性</h4>
                                <ul>
                                    <li>基于 Electron 技术，跨平台支持</li>
                                    <li>歌词自动搜索与动态展示</li>
                                    <li>实时频谱可视化效果</li>
                                    <li>支持深色和浅色主题切换</li>
                                    <li>现代化的用户界面设计</li>
                                    <li>本地音乐文件管理</li>
                                </ul>
                            </div>

                            <div class="screenshot-container">
                                <img src="https://minioapi.northpark.cn/pic/northpark-music-player-dark.png"
                                    alt="NorthPark Music Player - 深色主题" />
                                <img src="https://minioapi.northpark.cn/pic/northpark-music-player-light.png"
                                    alt="NorthPark Music Player - 浅色主题" />
                            </div>

                            <div class="text-center">
                                <a href="https://github.com/liuhouer/northpark-music-player/releases/"
                                    class="github-link" target="_blank">
                                    <i class="fa fa-github"></i>
                                    GitHub 下载
                                </a>
                            </div>
                        </div>

                        <!-- 右侧列 - 小布静听 -->
                        <div class="col-md-6 product-column">
                            <div class="product-header">
                                <h2>小布静听</h2>
                                <p class="lead">一款轻量级的音乐播放器,让音乐播放更简单</p>
                            </div>

                            <!-- 最新版本 -->
                            <div class="version-title">
                                最新版本 v1.9.4.1 (2016-5-2)
                            </div>

                            <div class="version-info">
                                <p>作者: Bruce</p>
                            </div>

                            <ul class="version-features">
                                <li>全新牛奶皮肤界面设计</li>
                                <li>用listview重写歌词显示,支持居中显示和自动滚屏</li>
                                <li>优化不支持码率文件的处理</li>
                                <li>改进播放列表文件定位功能</li>
                                <li>修复切换曲目的算法问题</li>
                                <li>其他bug修复和用户体验改进</li>
                            </ul>

                            <div class="text-center">
                                <a href="http://pan.baidu.com/s/1c01Vp5I" class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i>
                                    下载最新版
                                </a>
                            </div>

                            <!-- 历史版本 -->
                            <div class="version-title">历史版本</div>

                            <!-- v1.9.2.1 -->
                            <div class="version-info">
                                <h3>v1.9.2.1 (2016-4-20)</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>去掉N个时钟控件,去掉每秒优化内存的冗余功能</li>
                                <li>去掉关于页面的水波效果</li>
                                <li>修改Tag标签修改不成功的bug</li>
                                <li>去掉时钟控制的磁性窗体,目前只用obcontrol一种方式控制</li>
                                <li>歌词自动检索 | 歌词搜索点击后设置时间间隔保存,以防止程序锁死</li>
                                <li>此版本主要祛除了所有冗余|耗cpu的功能</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85293&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载此版本
                                </a>
                            </div>

                            <!-- v1.9.0.1 -->
                            <div class="version-info">
                                <h3>v1.9.0.1 (2014-7-10)</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>添加文件夹,会扫描目录</li>
                                <li>全面解析lrc歌词以及桌面歌词的一些操作逻辑</li>
                                <li>重构文件结构,去除冗余代码</li>
                                <li>tag信息的部分完善</li>
                                <li>添加/编辑歌词的操作逻辑调整</li>
                                <li>部分细节调整</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/s/1c01Vp5I" class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载此版本
                                </a>
                            </div>

                            <!-- v1.8.5 -->
                            <div class="version-info">
                                <h3>v1.8.5 (2012-10-28)</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>调整界面布局,修改滚动信息的算法</li>
                                <li>重新编译,删除冗余资源,减小cpu消耗</li>
                                <li>修改歌词秀界面代码,减少bug</li>
                                <li>优化隐藏播放列表切换歌曲的bug</li>
                                <li>优化文件不存在时处理异常的交互信息</li>
                                <li>优化id3v1处理tag标签相关功能</li>
                                <li>此版本已极度稳定</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85307&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载此版本
                                </a>
                            </div>

                            <!-- v1.8.4 -->
                            <div class="version-info">
                                <h3>v1.8.4 (2012-10-23)</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>修正标签修改后不立即显示的bug</li>
                                <li>修改桌面歌词滚动的算法</li>
                                <li>添加一些小优化</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=89446&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载此版本
                                </a>
                            </div>

                            <!-- v1.8.3 -->
                            <div class="version-info">
                                <h3>v1.8.3 (2012-10-1)</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>加入读取id3标签,可以自己定义艺术家、专辑、年代等,一键定义,特简易</li>
                                <li>加入识别操作系统版本,进而来控制窗体的磁性效果</li>
                                <li>加入桌面歌词保持最前端功能</li>
                                <li>修正一些bug</li>
                                <li>注意: 不要多开,只运行一个,否则读取播放列表文件时会提示资源正在占用</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85311&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载此版本
                                </a>
                            </div>

                            <!-- v1.8.0-1.8.2 -->
                            <div class="version-info">
                                <h3>v1.8.0-1.8.2 系列版本</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>添加类似qq的靠近侧边自动隐藏伸缩功能</li>
                                <li>三种桌面歌词风格,随你喜好</li>
                                <li>所有版本都是单文件可执行程序,无需额外DLL</li>
                                <li>1.8.2版本桌面歌词添加了显示图片功能(需要bruce.png文件)</li>
                                <li>Win7下显示效果最佳</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85308&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.8.2
                                </a>
                                <a href="http://pan.baidu.com/share/link?shareid=85308&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.8.1
                                </a>
                                <a href="http://pan.baidu.com/share/link?shareid=85307&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.8.0
                                </a>
                            </div>

                            <!-- 早期版本 -->
                            <div class="version-info">
                                <h3>早期版本</h3>
                                <p>作者: Bruce</p>
                            </div>
                            <ul class="version-features">
                                <li>v1.7.x 系列: 基础功能实现</li>
                                <li>v1.0-1.6: 初期开发版本</li>
                            </ul>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85305&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.7.2
                                </a>
                                <a href="http://pan.baidu.com/share/link?shareid=85304&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.7.1
                                </a>
                                <a href="http://pan.baidu.com/share/link?shareid=85302&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载v1.7.0
                                </a>
                            </div>

                            <!-- 皮肤下载 -->
                            <div class="version-title">皮肤下载</div>
                            <div class="version-info">
                                <p>适用于v2.0-6.0版本的皮肤包</p>
                            </div>
                            <div class="text-center">
                                <a href="http://pan.baidu.com/share/link?shareid=85298&uk=3860164064"
                                    class="download-btn" target="_blank">
                                    <i class="fa fa-download"></i> 下载皮肤包
                                </a>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="/WEB-INF/views/page/common/container.jsp" %>

    </body>

    </html>
