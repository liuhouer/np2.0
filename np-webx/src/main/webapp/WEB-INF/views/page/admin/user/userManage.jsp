<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理 - NorthPark</title>
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body { background-color: #f5f5f5; padding-top: 20px; }
        .container { max-width: 1200px; }
        .page-header { border-bottom: 2px solid #5cb85c; padding-bottom: 10px; margin-bottom: 30px; }
        .card { background: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 20px; margin-bottom: 20px; }
        .avatar { width: 36px; height: 36px; border-radius: 50%; object-fit: cover; background: #eee; }
        .avatar-placeholder { width: 36px; height: 36px; border-radius: 50%; background: #45d0c6; color: white; display: inline-flex; align-items: center; justify-content: center; font-size: 14px; font-weight: bold; }
        .status-badge { display: inline-block; padding: 3px 10px; border-radius: 10px; font-size: 12px; font-weight: bold; }
        .status-normal { background: #d4edda; color: #155724; }
        .status-banned { background: #f8d7da; color: #721c24; }
        .login-type { display: inline-block; padding: 2px 8px; border-radius: 3px; font-size: 11px; background: #e9ecef; color: #495057; }
        .user-info { position: fixed; top: 10px; right: 20px; background: white; padding: 8px 15px; border-radius: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); font-size: 13px; z-index: 1000; }
        .search-bar { display: flex; gap: 10px; margin-bottom: 20px; }
        .search-bar input { flex: 1; }
        .table th { background-color: #f8f9fa; }
        .table td { vertical-align: middle; }
        .nav-bar { display: flex; align-items: center; margin-bottom: 20px; gap: 10px; }
        .pagination-wrap { margin-top: 15px; text-align: center; }
        /* Detail Modal */
        .detail-row { display: flex; margin-bottom: 10px; border-bottom: 1px solid #f5f5f5; padding-bottom: 10px; }
        .detail-label { width: 100px; color: #999; font-size: 13px; flex-shrink: 0; }
        .detail-value { flex: 1; font-size: 13px; word-break: break-all; }
    </style>
</head>
<body>
    <div class="user-info">
        <i class="fa fa-user-circle"></i>
        <strong>${user.username}</strong> (管理员)
    </div>

    <div class="container">

        <!-- 导航栏 -->
        <div class="nav-bar">
            <a href="/" class="btn btn-default btn-sm">
                <i class="fa fa-home"></i> 返回首页
            </a>
            <a href="/admin/cron" class="btn btn-default btn-sm">
                <i class="fa fa-clock-o"></i> 定时任务
            </a>
            <a href="/admin/stat" class="btn btn-default btn-sm">
                <i class="fa fa-bar-chart"></i> 系统概览
            </a>
            <a href="/admin/envcfg" class="btn btn-default btn-sm">
                <i class="fa fa-sliders"></i> 环境配置
            </a>
        </div>

        <div class="page-header">
            <h1>
                <i class="fa fa-users"></i> 用户管理
                <small>User Management</small>
                <span class="pull-right" style="font-size:14px; color:#999; font-weight:normal; margin-top:8px; display:inline-block;">
                    共 <strong id="J_total">-</strong> 名用户
                </span>
            </h1>
        </div>

        <!-- 搜索栏 -->
        <div class="card" style="padding: 15px 20px;">
            <div class="search-bar">
                <input type="text" class="form-control" id="J_keyword" placeholder="搜索用户名或邮箱…" onkeydown="if(event.key==='Enter')doSearch()">
                <button class="btn btn-primary" onclick="doSearch()">
                    <i class="fa fa-search"></i> 搜索
                </button>
                <button class="btn btn-default" onclick="clearSearch()">
                    <i class="fa fa-refresh"></i> 重置
                </button>
            </div>
        </div>

        <!-- 用户表格 -->
        <div class="card">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th width="60">ID</th>
                        <th width="44">头像</th>
                        <th width="150">用户名</th>
                        <th>邮箱</th>
                        <th width="90">登录方式</th>
                        <th width="120">注册时间</th>
                        <th width="120">最后登录</th>
                        <th width="70">状态</th>
                        <th width="140">操作</th>
                    </tr>
                </thead>
                <tbody id="J_userBody">
                    <tr><td colspan="9" class="text-center" style="padding:40px; color:#999;"><i class="fa fa-spinner fa-spin"></i> 加载中...</td></tr>
                </tbody>
            </table>
            <!-- 分页 -->
            <div class="pagination-wrap" id="J_pagination"></div>
        </div>
    </div>

    <!-- 用户详情 Modal -->
    <div class="modal fade" id="userDetailModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                    <h4 class="modal-title">用户详情</h4>
                </div>
                <div class="modal-body" id="J_detailBody">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
    var currentPage = 1;
    var pageSize    = 20;
    var currentKeyword = '';

    function loadUsers(page, keyword) {
        currentPage = page || 1;
        currentKeyword = keyword !== undefined ? keyword : currentKeyword;

        $.ajax({
            url: '/admin/users/list',
            data: { page: currentPage, pageSize: pageSize, keyword: currentKeyword },
            success: function(r) {
                if (r.code !== 0) { showError(r.msg); return; }
                renderTable(r.users);
                renderPagination(r.total, currentPage, pageSize);
                $('#J_total').text(r.total);
            },
            error: function() { showError('加载失败，请稍后重试'); }
        });
    }

    function renderTable(users) {
        if (!users || users.length === 0) {
            $('#J_userBody').html('<tr><td colspan="9" class="text-center" style="padding:40px; color:#999;">暂无数据</td></tr>');
            return;
        }
        var html = '';
        users.forEach(function(u) {
            var avatarHtml = u.avatar_url
                ? '<img src="' + u.avatar_url + '" class="avatar" onerror="this.style.display=\'none\'">'
                : '<span class="avatar-placeholder">' + (u.username || '?').charAt(0).toUpperCase() + '</span>';

            var statusClass = (u.is_del === 1) ? 'status-banned' : 'status-normal';
            var statusText  = (u.is_del === 1) ? '已封禁' : '正常';

            var loginType = u.login_type || 'email';
            var typeColors = { github: '#24292e', google: '#4285f4', qq: '#12b7f5', email: '#45d0c6' };
            var typeColor  = typeColors[loginType] || '#6c757d';

            var banBtn = (u.is_del === 1)
                ? '<button class="btn btn-xs btn-success" onclick="toggleBan(' + u.id + ', 0)"><i class="fa fa-unlock"></i> 解封</button>'
                : '<button class="btn btn-xs btn-warning" onclick="toggleBan(' + u.id + ', 1)"><i class="fa fa-ban"></i> 封禁</button>';

            html += '<tr>';
            html += '<td>' + u.id + '</td>';
            html += '<td>' + avatarHtml + '</td>';
            html += '<td><strong>' + (u.username || '') + '</strong></td>';
            html += '<td>' + (u.email || '') + '</td>';
            html += '<td><span class="login-type" style="background:' + typeColor + '; color:white;">' + loginType + '</span></td>';
            html += '<td style="font-size:12px; color:#666;">' + (u.date_joined || '').substring(0, 10) + '</td>';
            html += '<td style="font-size:12px; color:#666;">' + (u.last_login || '').substring(0, 10) + '</td>';
            html += '<td><span class="status-badge ' + statusClass + '">' + statusText + '</span></td>';
            html += '<td>';
            html += '<button class="btn btn-xs btn-info" onclick="showDetail(' + u.id + ')"><i class="fa fa-eye"></i></button> ';
            html += banBtn;
            html += '</td>';
            html += '</tr>';
        });
        $('#J_userBody').html(html);
    }

    function renderPagination(total, page, size) {
        var totalPages = Math.ceil(total / size);
        if (totalPages <= 1) { $('#J_pagination').html(''); return; }

        var html = '<ul class="pagination pagination-sm">';
        html += '<li class="' + (page <= 1 ? 'disabled' : '') + '"><a href="#" onclick="loadUsers(' + (page - 1) + ');return false;">&laquo;</a></li>';

        var start = Math.max(1, page - 3);
        var end   = Math.min(totalPages, page + 3);
        if (start > 1) html += '<li><a href="#" onclick="loadUsers(1);return false;">1</a></li><li class="disabled"><a>…</a></li>';
        for (var i = start; i <= end; i++) {
            html += '<li class="' + (i === page ? 'active' : '') + '"><a href="#" onclick="loadUsers(' + i + ');return false;">' + i + '</a></li>';
        }
        if (end < totalPages) html += '<li class="disabled"><a>…</a></li><li><a href="#" onclick="loadUsers(' + totalPages + ');return false;">' + totalPages + '</a></li>';
        html += '<li class="' + (page >= totalPages ? 'disabled' : '') + '"><a href="#" onclick="loadUsers(' + (page + 1) + ');return false;">&raquo;</a></li>';
        html += '</ul>';
        $('#J_pagination').html(html);
    }

    function showDetail(id) {
        $.get('/admin/users/detail', { id: id }, function(r) {
            if (r.code !== 0) { alert(r.msg); return; }
            var u = r.user;
            var html = '';
            var fields = [
                ['ID', u.id], ['用户名', u.username], ['邮箱', u.email],
                ['注册时间', u.dateJoined], ['最后登录', u.lastLogin],
                ['登录方式', u.loginType], ['个人主页', u.blogSite],
                ['位置', u.location], ['公司', u.company],
                ['个人简介', u.bio], ['状态', u.isDel === 1 ? '已封禁' : '正常']
            ];
            fields.forEach(function(f) {
                if (f[1]) {
                    html += '<div class="detail-row"><span class="detail-label">' + f[0] + '</span><span class="detail-value">' + f[1] + '</span></div>';
                }
            });
            $('#J_detailBody').html(html);
            $('#userDetailModal').modal('show');
        });
    }

    function toggleBan(id, ban) {
        var action = ban === 1 ? '封禁' : '解封';
        if (!confirm('确定' + action + '该用户？')) return;
        var url = ban === 1 ? '/admin/users/ban' : '/admin/users/unban';
        $.post(url, { id: id }, function(r) {
            if (r.code === 0) {
                loadUsers(currentPage);
            } else {
                alert(r.msg || action + '失败');
            }
        });
    }

    function doSearch() {
        loadUsers(1, $.trim($('#J_keyword').val()));
    }

    function clearSearch() {
        $('#J_keyword').val('');
        loadUsers(1, '');
    }

    function showError(msg) {
        $('#J_userBody').html('<tr><td colspan="9" class="text-center text-danger" style="padding:40px;"><i class="fa fa-exclamation-circle"></i> ' + msg + '</td></tr>');
    }

    $(function() { loadUsers(1); });
    </script>
</body>
</html>
