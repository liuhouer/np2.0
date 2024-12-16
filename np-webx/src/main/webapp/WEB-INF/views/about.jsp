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
    <link rel="shortcut icon" href="https://northpark.cn/statics/img/favicon.ico">
    <title>关于NorthPark - 发现生活中的美好 | 免费资源分享社区</title>
    <meta name="keywords" content="NorthPark,北方公园,免费资源,影视资源,Mac资源,学习资源,情商提升,社区互动">
    <meta name="description" content="NorthPark是一个充满温度的网络社区,这里有免费的影视资源、Mac资源、学习资源,在这里你可以分享生活点滴、提升情商,与志同道合的朋友一起进步。">
    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>

    <style type="text/css">
        body {
            background-color: #f8f9fa;
            color: #495057;
            line-height: 1.8;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        #J_maincontent {
            flex: 1;
            min-height: calc(100vh - 200px); /* 减去header和footer的高度 */
            padding-bottom: 50px; /* 确保内容和footer之间有足够间距 */
        }
        
        .about-section {
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,.05);
            padding: 30px;
            margin-bottom: 30px;
        }
        
        .about-section h4 {
            color: #212529;
            font-weight: 600;
            margin-bottom: 20px;
        }
        
        .about-content p {
            text-indent: 2em;
            margin-bottom: 15px;
        }
        
        .contact-box {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 25px;
            margin-bottom: 20px;
        }
        
        .contact-box h4 {
            border-bottom: 2px solid #dee2e6;
            padding-bottom: 10px;
        }
        
        .feature-link {
            color: #007bff;
            text-decoration: none;
            transition: color 0.3s;
        }
        
        .feature-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }
    </style>
</head>

<body>
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<div class="clearfix maincontent grayback" id="J_maincontent">
    <div class="container" id="J_container">
        <div class="mainbody padding-t20" style="margin-top:70px;">
            <div class="row">
                <div class="col-sm-7">
                    <div class="about-section">
                        <h4>关于 NorthPark</h4>
                        <div class="about-content">
                            <p>NorthPark诞生于一个夏夜。</p>
                            <p>2014年的一个酷热难耐的夏夜，
                                <a target="_blank"  class="text-color-X"  href="/people/jeey">小布</a>
                                忙碌了一天，朋友正好做好了香喷喷的饭菜，吃完饭又去买了个冰西瓜，感到浑身透凉。
                            </p>
                            <p>突然外面下起了一阵暴雨。小布躺在床上，看着窗外倾泻的暴雨、闻着清透的泥土气息，闭上眼，突然感到一种特殊的惬意。
                                原来，生活中充满了让人惬意的东西，比如开着窗欣赏暴雨，比如下雨天了举着小伞走到小巷，比如巷子里的孩子嬉戏的童年。
                            </p>
                            <p>可是，小布知道，这些转瞬即逝的思绪，往往到了第二天就彻底忘记了。
                                人类总是忘记生活中的美好点滴，只记住了悲伤无趣的记忆，所以人类才会这么不开心。
                            </p>
                            <p>想到这里，他起身坐到电脑桌旁敲起了代码，用两天时间完成了一个初版，包括
                                <a target="_blank" class="text-color-9" href="/love">主题</a>，
                                <a target="_blank" class="text-color-8"  href="/note">树洞</a>
                                的功能。
                            </p>
                            <p>很喜欢看南方公园，里面的小动漫故事透露出形形色色的社会现象，我注册北方公园-NorthPark域名，
                                寓指美好的互动公园，这里的一切都是那么美好。
                                <a target="_blank" class="text-color-7" href="/movies">免费的影视资源</a>，
                                <a target="_blank" class="text-color-6" href="/soft/mac">免费的Mac资源</a>，
                                <a target="_blank" class="text-color-5" href="/learning">顶级的学习资源</a>，
                                <a target="_blank" class="text-color-P" href="/romeo">情商提升</a>。
                                在这里和美好的大家一起进步，一起快乐的玩耍。
                            </p>
                            <p>是的
                            </p>
                            <p>再悲伤的人，也一定会有让他感到美好的东西。
                            </p>
                            <p>再不幸的人，也有资格来NorthPark找到精神角落。
                            </p>
                        </div>
                    </div>
                </div>
                
                <div class="col-sm-offset-1 col-sm-4">
                    <div class="contact-box">
                        <h4>联系合作</h4>
                        <p><i class="fa fa-envelope"></i> 邮箱：<a href="mailto:zhangyang.z@iCloud.com" class="feature-link">zhangyang.z@iCloud.com</a></p>
                        <p><i class="fa fa-qq"></i> QQ：654714226</p>
                    </div>
                    
                    <div class="contact-box">
                        <h4>文章投稿</h4>
                        <p><i class="fa fa-edit"></i> QQ：654714226</p>
                    </div>
                    
                    <div class="contact-box">
                        <h4>版权声明</h4>
                        <p>本站资源均来自互联网，仅供学习交流使用。如有侵权请联系我们删除。</p>
                        <p><i class="fa fa-envelope"></i> 邮箱：<a href="mailto:qhdsoftware@163.com" class="feature-link">qhdsoftware@163.com</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

</body>
</html>
