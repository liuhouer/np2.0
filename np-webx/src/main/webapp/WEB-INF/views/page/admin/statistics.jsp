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
        <div style="display:flex; align-items:center; gap:10px; margin-bottom:20px;">
            <a href="/" class="back-link" style="margin-bottom:0;">
                <i class="fa fa-home"></i> 返回首页
            </a>
            <a href="/admin/cron" class="back-link" style="margin-bottom:0;">
                <i class="fa fa-clock-o"></i> 定时任务
            </a>
            <a href="/admin/users" class="back-link" style="margin-bottom:0;">
                <i class="fa fa-users"></i> 用户管理
            </a>
            <a href="/admin/envcfg" class="back-link" style="margin-bottom:0;">
                <i class="fa fa-sliders"></i> 环境配置
            </a>
        </div>

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
        loadCharts();
    }

    $(function() {
        loadStatistics();
        loadCharts();
        // 每30秒自动刷新
        setInterval(loadStatistics, 30000);
    });
    </script>

    <!-- ===== 图表区域 ===== -->
    <style>
        .chart-section {
            margin-top: 20px;
        }
        .chart-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.15);
            margin-bottom: 25px;
        }
        .chart-title {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            margin-bottom: 18px;
            padding-bottom: 10px;
            border-bottom: 2px solid #667eea;
        }
        .chart-title i {
            color: #667eea;
            margin-right: 6px;
        }
        /* 折线图 canvas */
        #regTrendChart { width: 100% !important; }

        /* 排行榜 */
        .rank-list { list-style: none; padding: 0; margin: 0; }
        .rank-item {
            display: flex;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #f5f5f5;
        }
        .rank-item:last-child { border-bottom: none; }
        .rank-no {
            width: 28px;
            height: 28px;
            border-radius: 50%;
            background: #e9ecef;
            color: #666;
            font-size: 13px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
            margin-right: 12px;
        }
        .rank-no.top1 { background: #f39c12; color: white; }
        .rank-no.top2 { background: #95a5a6; color: white; }
        .rank-no.top3 { background: #e67e22; color: white; }
        .rank-avatar {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 10px;
            flex-shrink: 0;
        }
        .rank-avatar-placeholder {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background: #667eea;
            color: white;
            font-size: 14px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 10px;
            flex-shrink: 0;
        }
        .rank-info { flex: 1; min-width: 0; }
        .rank-name {
            font-size: 14px;
            font-weight: bold;
            color: #333;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .rank-meta { font-size: 12px; color: #999; margin-top: 2px; }
        .rank-badge {
            font-size: 11px;
            padding: 2px 8px;
            border-radius: 10px;
            background: #e9ecef;
            color: #495057;
            flex-shrink: 0;
        }
        .chart-loading {
            text-align: center;
            color: #999;
            padding: 40px 0;
            font-size: 14px;
        }
    </style>

    <div class="container chart-section">
        <div class="section-title" style="margin-top: 0;">
            <i class="fa fa-line-chart"></i> 数据趋势
        </div>

        <!-- 注册趋势图 -->
        <div class="chart-card">
            <div class="chart-title">
                <i class="fa fa-line-chart"></i> 近 30 天用户注册趋势
            </div>
            <div id="regTrendLoading" class="chart-loading">
                <i class="fa fa-spinner fa-spin"></i> 加载中...
            </div>
            <canvas id="regTrendChart" height="90" style="display:none;"></canvas>
        </div>

        <!-- 活跃用户排行 -->
        <div class="chart-card">
            <div class="chart-title">
                <i class="fa fa-trophy"></i> 活跃用户 Top 10
                <small style="font-size:12px; color:#999; font-weight:normal; margin-left:8px;">按最近登录时间</small>
            </div>
            <div id="activeUsersLoading" class="chart-loading">
                <i class="fa fa-spinner fa-spin"></i> 加载中...
            </div>
            <ul class="rank-list" id="activeUsersList" style="display:none;"></ul>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
    <script>
    var regChart = null;

    function loadCharts() {
        $.ajax({
            url: '/admin/chartData',
            type: 'GET',
            dataType: 'json',
            success: function(result) {
                if (result.code === 0) {
                    renderRegTrend(result.regTrend || []);
                    renderActiveUsers(result.activeUsers || []);
                } else {
                    showChartError('regTrendLoading', result.msg);
                    showChartError('activeUsersLoading', result.msg);
                }
            },
            error: function() {
                showChartError('regTrendLoading', '加载失败，请稍后重试');
                showChartError('activeUsersLoading', '加载失败，请稍后重试');
            }
        });
    }

    function renderRegTrend(data) {
        $('#regTrendLoading').hide();

        // reg_date 可能是毫秒时间戳(Number)或 yyyy-MM-dd 字符串，统一转成 yyyy-MM-dd key
        function toDateKey(val) {
            if (!val) return '';
            if (typeof val === 'number' || /^\d{10,}$/.test(String(val))) {
                // Unix 毫秒时间戳 → 本地 yyyy-MM-dd
                var d = new Date(Number(val));
                var y = d.getFullYear();
                var m = ('0' + (d.getMonth() + 1)).slice(-2);
                var day = ('0' + d.getDate()).slice(-2);
                return y + '-' + m + '-' + day;
            }
            // 已经是字符串格式，截取前10位
            return String(val).substring(0, 10);
        }

        // 补全近30天日期（无数据的天填0）
        var dateMap = {};
        data.forEach(function(d) { dateMap[toDateKey(d.reg_date)] = parseInt(d.reg_count, 10); });

        var labels = [], values = [];
        for (var i = 29; i >= 0; i--) {
            var d = new Date();
            d.setDate(d.getDate() - i);
            var y = d.getFullYear();
            var m = ('0' + (d.getMonth() + 1)).slice(-2);
            var day = ('0' + d.getDate()).slice(-2);
            var key = y + '-' + m + '-' + day;
            labels.push(m + '-' + day); // MM-DD
            values.push(dateMap[key] || 0);
        }

        var canvas = document.getElementById('regTrendChart');
        $(canvas).show();

        if (regChart) { regChart.destroy(); }

        regChart = new Chart(canvas.getContext('2d'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: '注册人数',
                    data: values,
                    borderColor: '#667eea',
                    backgroundColor: 'rgba(102,126,234,0.10)',
                    borderWidth: 2,
                    pointRadius: 3,
                    pointBackgroundColor: '#667eea',
                    fill: true,
                    tension: 0.3
                }]
            },
            options: {
                responsive: true,
                legend: { display: false },
                scales: {
                    xAxes: [{ gridLines: { display: false }, ticks: { fontSize: 11, maxRotation: 45 } }],
                    yAxes: [{ ticks: { beginAtZero: true, precision: 0, fontSize: 11 }, gridLines: { color: 'rgba(0,0,0,0.05)' } }]
                },
                tooltips: {
                    callbacks: {
                        label: function(item) { return '注册 ' + item.yLabel + ' 人'; }
                    }
                }
            }
        });
    }

    function renderActiveUsers(users) {
        $('#activeUsersLoading').hide();
        var $list = $('#activeUsersList');
        if (!users.length) {
            $list.html('<li style="text-align:center; color:#999; padding:30px 0;">暂无数据</li>').show();
            return;
        }

        var typeColors = { github: '#24292e', google: '#4285f4', qq: '#12b7f5', email: '#45d0c6' };
        var rankClasses = ['top1', 'top2', 'top3'];
        var html = '';

        users.forEach(function(u, idx) {
            var rankClass = rankClasses[idx] || '';
            var avatarHtml = u.avatar_url
                ? '<img src="' + u.avatar_url + '" class="rank-avatar" onerror="this.parentNode.innerHTML=\'<span class=\\\"rank-avatar-placeholder\\\">' + (u.username || '?').charAt(0).toUpperCase() + '</span>\'">'
                : '<span class="rank-avatar-placeholder">' + (u.username || '?').charAt(0).toUpperCase() + '</span>';

            var loginType = u.login_type || 'email';
            var typeColor = typeColors[loginType] || '#6c757d';
            
            // 格式化最后登录日期：如果是时间戳（数字），转为 yyyy-MM-dd
            var lastLogin = '';
            if (u.last_login) {
                if (typeof u.last_login === 'number') {
                    var d = new Date(u.last_login);
                    var y = d.getFullYear();
                    var m = ('0' + (d.getMonth() + 1)).slice(-2);
                    var day = ('0' + d.getDate()).slice(-2);
                    lastLogin = y + '-' + m + '-' + day;
                } else if (typeof u.last_login === 'string') {
                    lastLogin = u.last_login.substring(0, 10);
                }
            }

            html += '<li class="rank-item">';
            html += '<span class="rank-no ' + rankClass + '">' + (idx + 1) + '</span>';
            html += avatarHtml;
            html += '<div class="rank-info">';
            html += '<div class="rank-name">' + (u.username || '') + '</div>';
            html += '<div class="rank-meta">' + (u.email || '') + ' · 最近登录 ' + lastLogin + '</div>';
            html += '</div>';
            html += '<span class="rank-badge" style="background:' + typeColor + '; color:white;">' + loginType + '</span>';
            html += '</li>';
        });

        $list.html(html).show();
    }

    function showChartError(loadingId, msg) {
        $('#' + loadingId).html('<i class="fa fa-exclamation-circle"></i> ' + (msg || '加载失败'));
    }
    </script>
</body>
</html>