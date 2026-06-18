<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>网站统计 - NorthPark</title>
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 30px 20px;
        }
        .container {
            max-width: 1200px;
        }
        .page-header {
            color: white;
            margin-bottom: 40px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        .stat-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            transition: all 0.3s ease;
        }
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.2);
        }
        .stat-icon {
            font-size: 48px;
            color: #667eea;
            margin-bottom: 15px;
        }
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            color: #333;
            margin: 10px 0;
        }
        .stat-label {
            color: #999;
            font-size: 14px;
        }
        .stat-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }
        .section-title {
            color: white;
            font-size: 20px;
            margin-top: 40px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        .refresh-btn {
            float: right;
            background: white;
            color: #667eea;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s;
        }
        .refresh-btn:hover {
            transform: rotate(180deg);
            background: #f0f0f0;
        }
        .loading {
            text-align: center;
            color: white;
            font-size: 18px;
        }
        .back-link {
            color: white;
            text-decoration: none;
            margin-bottom: 20px;
            display: inline-block;
        }
        .back-link:hover {
            color: #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="/" class="back-link">
            <i class="fa fa-arrow-left"></i> 返回首页
        </a>
        
        <div class="page-header">
            <h1>
                <i class="fa fa-bar-chart"></i> 网站数据统计
                <button class="btn btn-sm refresh-btn" onclick="refreshStats()">
                    <i class="fa fa-refresh"></i> 刷新
                </button>
            </h1>
            <p>实时监控网站关键指标</p>
        </div>

        <div id="statsContainer">
            <div class="loading">
                <i class="fa fa-spinner fa-spin"></i> 加载中...
            </div>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>
    function loadStatistics() {
        $.ajax({
            url: '/admin/statistics',
            type: 'GET',
            dataType: 'json',
            success: function(result) {
                if (result.code === 0) {
                    renderStatistics(result.statistics);
                } else {
                    $('#statsContainer').html('<div class="alert alert-danger">' + result.msg + '</div>');
                }
            },
            error: function() {
                $('#statsContainer').html('<div class="alert alert-danger">加载失败，请稍后重试</div>');
            }
        });
    }

    function renderStatistics(stats) {
        var html = '';
        
        // 用户相关统计
        html += '<div class="section-title"><i class="fa fa-users"></i> 用户统计</div>';
        html += '<div class="stat-row">';
        html += '<div class="stat-card">';
        html += '<div class="stat-icon"><i class="fa fa-user"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalUsers || 0) + '</div>';
        html += '<div class="stat-label">总用户数</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #2ecc71;"><i class="fa fa-circle"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.onlineUsers || 0) + '</div>';
        html += '<div class="stat-label">在线用户</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #f39c12;"><i class="fa fa-plus-circle"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.todayNewUsers || 0) + '</div>';
        html += '<div class="stat-label">今日新用户</div>';
        html += '</div>';
        html += '</div>';

        // 资源统计
        html += '<div class="section-title"><i class="fa fa-folder-open"></i> 资源统计</div>';
        html += '<div class="stat-row">';
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #e74c3c;"><i class="fa fa-film"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalMovies || 0) + '</div>';
        html += '<div class="stat-label">电影总数</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #3498db;"><i class="fa fa-apple"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalSofts || 0) + '</div>';
        html += '<div class="stat-label">软件总数</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #9b59b6;"><i class="fa fa-graduation-cap"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalKnowledge || 0) + '</div>';
        html += '<div class="stat-label">知识资源</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #1abc9c;"><i class="fa fa-book"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalNotes || 0) + '</div>';
        html += '<div class="stat-label">树洞笔记</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #e67e22;"><i class="fa fa-heart"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.totalLyrics || 0) + '</div>';
        html += '<div class="stat-label">最爱主题</div>';
        html += '</div>';
        html += '</div>';

        // 今日新增
        html += '<div class="section-title"><i class="fa fa-calendar"></i> 今日新增</div>';
        html += '<div class="stat-row">';
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #f39c12;"><i class="fa fa-plus"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.todayNewMovies || 0) + '</div>';
        html += '<div class="stat-label">新电影</div>';
        html += '</div>';
        
        html += '<div class="stat-card">';
        html += '<div class="stat-icon" style="color: #16a085;"><i class="fa fa-plus"></i></div>';
        html += '<div class="stat-number">' + formatNumber(stats.todayNewComments || 0) + '</div>';
        html += '<div class="stat-label">新评论</div>';
        html += '</div>';
        html += '</div>';

        $('#statsContainer').html(html);
    }

    function formatNumber(num) {
        if (num >= 10000) {
            return (num / 10000).toFixed(1) + '万';
        } else if (num >= 1000) {
            return (num / 1000).toFixed(1) + 'k';
        }
        return num;
    }

    function refreshStats() {
        loadStatistics();
    }

    $(function() {
        loadStatistics();
        // 每30秒自动刷新
        setInterval(loadStatistics, 30000);
    });
    </script>
</body>
</html>