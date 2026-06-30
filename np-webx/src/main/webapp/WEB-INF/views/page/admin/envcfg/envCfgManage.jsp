<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>环境配置管理 - NorthPark</title>
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            padding-top: 20px;
        }
        .container {
            max-width: 1200px;
        }
        .page-header {
            border-bottom: 2px solid #5bc0de;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        .card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .status-badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 10px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-on  { background: #d4edda; color: #155724; }
        .status-off { background: #f8d7da; color: #721c24; }
        .cfg-value {
            font-family: 'Courier New', monospace;
            background: #f8f9fa;
            padding: 2px 6px;
            border-radius: 3px;
            font-size: 13px;
            word-break: break-all;
        }
        .user-info {
            position: fixed;
            top: 10px;
            right: 20px;
            background: white;
            padding: 8px 15px;
            border-radius: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            font-size: 13px;
            z-index: 1000;
        }
        .nav-bar {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            gap: 10px;
        }
        .table th { background-color: #f8f9fa; }
        .table td { vertical-align: middle; }
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
            <a href="/admin/users" class="btn btn-default btn-sm">
                <i class="fa fa-users"></i> 用户管理
            </a>
        </div>

        <div class="page-header">
            <h1>
                <i class="fa fa-sliders"></i> 环境配置管理
                <small>Environment Config</small>
                <button class="btn btn-primary btn-sm pull-right" onclick="openAddModal()">
                    <i class="fa fa-plus"></i> 新增配置
                </button>
            </h1>
        </div>

        <div class="card">
            <table class="table table-hover" id="J_cfgTable">
                <thead>
                    <tr>
                        <th width="60">ID</th>
                        <th width="200">配置名称</th>
                        <th>配置值</th>
                        <th width="200">描述</th>
                        <th width="70">状态</th>
                        <th width="130">最后修改</th>
                        <th width="120">操作</th>
                    </tr>
                </thead>
                <tbody id="J_cfgBody">
                    <c:forEach items="${cfgList}" var="cfg">
                    <tr data-id="${cfg.lCfgId}">
                        <td>${cfg.lCfgId}</td>
                        <td><code>${cfg.vcCfgName}</code></td>
                        <td><span class="cfg-value">${cfg.vcCfgValue}</span></td>
                        <td>${cfg.vcDesc}</td>
                        <td>
                            <span class="status-badge ${cfg.cStatus == '1' ? 'status-on' : 'status-off'}">
                                ${cfg.cStatus == '1' ? '启用' : '禁用'}
                            </span>
                        </td>
                        <td><fmt:formatDate value="${cfg.dMdfTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <button class="btn btn-xs btn-warning" onclick="openEditModal(${cfg.lCfgId})">
                                <i class="fa fa-pencil"></i> 编辑
                            </button>
                            <button class="btn btn-xs btn-danger" onclick="deleteCfg(${cfg.lCfgId})">
                                <i class="fa fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty cfgList}">
                <div class="alert alert-info">
                    <i class="fa fa-info-circle"></i> 暂无配置项
                </div>
            </c:if>
        </div>
    </div>

    <!-- 新增/编辑 Modal -->
    <div class="modal fade" id="cfgModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                    <h4 class="modal-title" id="cfgModalTitle">新增配置</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="cfgId">
                    <div class="form-group">
                        <label>配置名称 <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="cfgName" placeholder="如: admin_emails">
                    </div>
                    <div class="form-group">
                        <label>配置值 <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="cfgValue" rows="3" placeholder="配置的具体值"></textarea>
                    </div>
                    <div class="form-group">
                        <label>描述</label>
                        <input type="text" class="form-control" id="cfgDesc" placeholder="说明该配置的用途">
                    </div>
                    <div class="form-group">
                        <label>状态</label>
                        <select class="form-control" id="cfgStatus">
                            <option value="1">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="saveCfg()">
                        <i class="fa fa-save"></i> 保存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
    function openAddModal() {
        $('#cfgModalTitle').text('新增配置');
        $('#cfgId').val('');
        $('#cfgName').val('').prop('disabled', false);
        $('#cfgValue').val('');
        $('#cfgDesc').val('');
        $('#cfgStatus').val('1');
        $('#cfgModal').modal('show');
    }

    function openEditModal(id) {
        $.get('/admin/envcfg/get', { lCfgId: id }, function(r) {
            if (r.code !== 0) { alert(r.msg); return; }
            var c = r.cfg;
            $('#cfgModalTitle').text('编辑配置');
            $('#cfgId').val(c.lCfgId);
            $('#cfgName').val(c.vcCfgName).prop('disabled', false);
            $('#cfgValue').val(c.vcCfgValue);
            $('#cfgDesc').val(c.vcDesc);
            $('#cfgStatus').val(c.cStatus);
            $('#cfgModal').modal('show');
        });
    }

    function saveCfg() {
        var id     = $('#cfgId').val();
        var name   = $.trim($('#cfgName').val());
        var value  = $.trim($('#cfgValue').val());
        var desc   = $('#cfgDesc').val();
        var status = $('#cfgStatus').val();

        if (!name) { alert('配置名称不能为空'); return; }
        if (!value) { alert('配置值不能为空'); return; }

        var url    = id ? '/admin/envcfg/update' : '/admin/envcfg/add';
        var params = { vcCfgName: name, vcCfgValue: value, vcDesc: desc, cStatus: status };
        if (id) params.lCfgId = id;

        $.post(url, params, function(r) {
            if (r.code === 0) {
                $('#cfgModal').modal('hide');
                location.reload();
            } else {
                alert(r.msg || '操作失败');
            }
        });
    }

    function deleteCfg(id) {
        if (!confirm('确定删除该配置项？此操作不可恢复。')) return;
        $.post('/admin/envcfg/delete', { lCfgId: id }, function(r) {
            if (r.code === 0) {
                location.reload();
            } else {
                alert(r.msg || '删除失败');
            }
        });
    }
    </script>
</body>
</html>
