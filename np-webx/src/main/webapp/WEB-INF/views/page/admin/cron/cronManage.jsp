<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>定时任务管理 - NorthPark</title>
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
            border-bottom: 2px solid #337ab7;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        .task-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
            transition: all 0.3s;
        }
        .task-card:hover {
            box-shadow: 0 4px 16px rgba(0,0,0,0.15);
        }
        .task-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .task-title {
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin: 0;
        }
        .task-status {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-active {
            background-color: #d4edda;
            color: #155724;
        }
        .status-inactive {
            background-color: #f8d7da;
            color: #721c24;
        }
        .task-info {
            color: #666;
            font-size: 14px;
            line-height: 1.8;
        }
        .task-info .label {
            display: inline-block;
            width: 100px;
            color: #999;
        }
        .task-actions {
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }
        .log-modal .modal-dialog {
            width: 90%;
            max-width: 1000px;
        }
        .log-table {
            font-size: 13px;
        }
        .log-status {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 3px;
            font-size: 11px;
            font-weight: bold;
        }
        .log-status.running {
            background-color: #d1ecf1;
            color: #0c5460;
        }
        .log-status.success {
            background-color: #d4edda;
            color: #155724;
        }
        .log-status.failed {
            background-color: #f8d7da;
            color: #721c24;
        }
        .log-detail {
            white-space: pre-wrap;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-size: 12px;
            max-height: 400px;
            overflow-y: auto;
        }
        .execute-form {
            margin-top: 10px;
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
    </style>
</head>
<body>
    <div class="user-info">
        <i class="fa fa-user-circle"></i> 
        <strong>${user.username}</strong> (管理员)
    </div>

    <div class="container">
        <div class="page-header">
            <h1>
                <i class="fa fa-clock-o"></i> 定时任务管理
                <small>Cron Task Management</small>
                <button class="btn btn-success btn-sm pull-right" id="J_add_task_btn">
                    <i class="fa fa-plus"></i> 新增任务
                </button>
            </h1>
        </div>

        <c:if test="${empty tasks}">
            <div class="alert alert-info">
                <i class="fa fa-info-circle"></i> 暂无定时任务配置
            </div>
        </c:if>

        <c:forEach items="${tasks}" var="task">
            <div class="task-card" data-task-id="${task.id}">
                <div class="task-header">
                    <h3 class="task-title">
                        <i class="fa fa-tasks"></i> ${task.taskName}
                    </h3>
                    <span class="task-status ${task.status == 1 ? 'status-active' : 'status-inactive'}">
                        ${task.status == 1 ? '启用' : '禁用'}
                    </span>
                </div>

                <div class="task-info">
                    <div><span class="label">任务类：</span><code>${task.taskClass}</code></div>
                    <div><span class="label">方法名：</span><code>${task.taskMethod}</code></div>
                    <div><span class="label">执行规则：</span><code>${task.cronExpression}</code></div>
                    <div><span class="label">任务描述：</span>${task.description}</div>
                    <div><span class="label">支持手动：</span>${task.supportManual == 1 ? '是' : '否'}</div>
                </div>

                <div class="task-actions">
                    <button class="btn btn-warning btn-sm btn-edit-task" 
                            data-task-id="${task.id}" 
                            title="编辑任务">
                        <i class="fa fa-edit"></i> 编辑
                    </button>
                    <button class="btn ${task.status == 1 ? 'btn-danger' : 'btn-success'} btn-sm btn-toggle-status" 
                            data-task-id="${task.id}" 
                            data-task-name="${task.taskName}"
                            data-current-status="${task.status}"
                            title="${task.status == 1 ? '点击禁用此任务' : '点击启用此任务'}">
                        <i class="fa ${task.status == 1 ? 'fa-ban' : 'fa-check'}"></i> 
                        ${task.status == 1 ? '禁用' : '启用'}
                    </button>
                    <button class="btn btn-danger btn-sm btn-delete-task" 
                            data-task-id="${task.id}" 
                            data-task-name="${task.taskName}"
                            title="删除任务">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                    <c:if test="${task.supportManual == 1}">
                        <button class="btn btn-primary btn-sm btn-execute" 
                                data-task-id="${task.id}" 
                                data-task-name="${task.taskName}">
                            <i class="fa fa-play"></i> 立即执行
                        </button>
                        <button class="btn btn-info btn-sm btn-execute-with-page" 
                                data-task-id="${task.id}" 
                                data-task-name="${task.taskName}">
                            <i class="fa fa-play-circle"></i> 执行（指定页码范围）
                        </button>
                    </c:if>
                    <button class="btn btn-success btn-sm btn-view-logs" 
                            data-task-id="${task.id}" 
                            data-task-name="${task.taskName}">
                        <i class="fa fa-list-alt"></i> 查看执行日志
                    </button>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- 新增/编辑任务模态框 -->
    <div class="modal fade" id="taskModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">
                        <i class="fa fa-tasks"></i> <span id="taskModalTitle">新增定时任务</span>
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="taskForm" class="form-horizontal">
                        <input type="hidden" id="taskId" name="taskId">
                        
                        <div class="form-group">
                            <label class="col-sm-3 control-label">任务名称 <span class="text-danger">*</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="taskName" name="taskName" 
                                       placeholder="请输入任务名称" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">任务类 <span class="text-danger">*</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="taskClass" name="taskClass" 
                                       placeholder="例: cn.northpark.task.MyTask" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">方法名 <span class="text-danger">*</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="taskMethod" name="taskMethod" 
                                       placeholder="例: execute" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">Cron表达式 <span class="text-danger">*</span></label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="cronExpression" name="cronExpression" 
                                       placeholder="例: 0 0 * * * ?" required>
                                <small class="form-text text-muted">
                                    格式: 秒 分 时 日 月 周 [年]
                                    <br>例子: 0 0 0 * * ? (每天午夜)
                                </small>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">任务描述</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="description" name="description" 
                                          placeholder="请输入任务描述" rows="3"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">支持手动执行</label>
                            <div class="col-sm-9">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="supportManual" name="supportManual" value="1">
                                        允许手动触发此任务
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">任务状态</label>
                            <div class="col-sm-9">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="status" value="1" checked> 启用
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="status" value="0"> 禁用
                                    </label>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="J_save_task_btn">
                        <i class="fa fa-save"></i> 保存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- 执行日志模态框 -->
    <div class="modal fade log-modal" id="logModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">
                        <i class="fa fa-list-alt"></i> 执行日志 - <span id="logTaskName"></span>
                    </h4>
                </div>
                <div class="modal-body">
                    <div id="logContent">
                        <div class="text-center">
                            <i class="fa fa-spinner fa-spin fa-2x"></i>
                            <p>加载中...</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 日志详情模态框 -->
    <div class="modal fade" id="logDetailModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">
                        <i class="fa fa-file-text-o"></i> 执行详情
                    </h4>
                </div>
                <div class="modal-body">
                    <div id="logDetailContent"></div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
    $(function() {
        // ==================== 新增/编辑任务 ====================
        $('#J_add_task_btn').click(function() {
            resetTaskForm();
            $('#taskId').val('');
            $('#taskModalTitle').text('新增定时任务');
            $('#taskModal').modal('show');
        });

        $('.btn-edit-task').click(function() {
            var taskId = $(this).data('task-id');
            var $taskCard = $(this).closest('.task-card');
            
            $('#taskId').val(taskId);
            $('#taskName').val($taskCard.find('.task-title').text().trim());
            
            // 获取任务详情
            var taskClass = $taskCard.find('.task-info div:eq(0)').find('code').text();
            var taskMethod = $taskCard.find('.task-info div:eq(1)').find('code').text();
            var cronExpression = $taskCard.find('.task-info div:eq(2)').find('code').text();
            var description = $taskCard.find('.task-info div:eq(3)').text().replace('任务描述：', '').trim();
            var supportManual = $taskCard.find('.task-info div:eq(4)').text().indexOf('是') >= 0 ? 1 : 0;
            var status = $taskCard.find('.task-status').hasClass('status-active') ? 1 : 0;
            
            $('#taskClass').val(taskClass);
            $('#taskMethod').val(taskMethod);
            $('#cronExpression').val(cronExpression);
            $('#description').val(description);
            $('#supportManual').prop('checked', supportManual == 1);
            $('input[name="status"][value="' + status + '"]').prop('checked', true);
            
            $('#taskModalTitle').text('编辑定时任务');
            $('#taskModal').modal('show');
        });

        $('#J_save_task_btn').click(function() {
            if (!validateTaskForm()) {
                return;
            }

            var taskId = $('#taskId').val();
            var taskName = $('#taskName').val();
            var taskClass = $('#taskClass').val();
            var taskMethod = $('#taskMethod').val();
            var cronExpression = $('#cronExpression').val();
            var description = $('#description').val();
            var supportManual = $('#supportManual').is(':checked') ? 1 : 0;
            var status = $('input[name="status"]:checked').val();

            var $btn = $(this);
            var originalHtml = $btn.html();
            $btn.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 保存中...');

            var url, method, data;
            
            if (taskId) {
                // 编辑
                url = '/admin/cron/update/' + taskId;
                data = {
                    taskName: taskName,
                    cronExpression: cronExpression,
                    description: description,
                    supportManual: supportManual
                };
            } else {
                // 新增
                url = '/admin/cron/add';
                data = {
                    taskName: taskName,
                    taskClass: taskClass,
                    taskMethod: taskMethod,
                    cronExpression: cronExpression,
                    description: description,
                    supportManual: supportManual,
                    status: status
                };
            }

            $.ajax({
                url: url,
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function(result) {
                    if (result.code === 0) {
                        alert(result.message || '操作成功');
                        location.reload();
                    } else {
                        alert('操作失败: ' + (result.message || result.msg));
                    }
                },
                error: function() {
                    alert('请求失败，请稍后重试');
                },
                complete: function() {
                    $btn.prop('disabled', false).html(originalHtml);
                }
            });
        });

        // ==================== 删除任务 ====================
        $('.btn-delete-task').click(function() {
            var taskId = $(this).data('task-id');
            var taskName = $(this).data('task-name');
            
            if (!confirm('确定要删除任务【' + taskName + '】吗？\n\n删除后无法恢复！')) {
                return;
            }

            var $btn = $(this);
            var originalHtml = $btn.html();
            $btn.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i>');

            $.ajax({
                url: '/admin/cron/delete/' + taskId,
                type: 'POST',
                dataType: 'json',
                success: function(result) {
                    if (result.code === 0) {
                        alert('任务已删除');
                        $btn.closest('.task-card').fadeOut(function() {
                            $(this).remove();
                        });
                    } else {
                        alert('删除失败: ' + (result.message || result.msg));
                        $btn.prop('disabled', false).html(originalHtml);
                    }
                },
                error: function() {
                    alert('请求失败，请稍后重试');
                    $btn.prop('disabled', false).html(originalHtml);
                }
            });
        });

        // ==================== 启用/禁用任务 ====================
        $('.btn-toggle-status').click(function() {
            var taskId = $(this).data('task-id');
            var taskName = $(this).data('task-name');
            var currentStatus = parseInt($(this).data('current-status'));
            var newStatus = currentStatus === 1 ? '禁用' : '启用';

            if (!confirm('确定要' + newStatus + '任务【' + taskName + '】吗？')) {
                return;
            }

            var $btn = $(this);
            var originalHtml = $btn.html();
            $btn.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i>');

            $.ajax({
                url: '/admin/cron/toggle/' + taskId,
                type: 'POST',
                dataType: 'json',
                success: function(result) {
                    if (result.code === 0) {
                        alert(result.message || '状态已更改');
                        location.reload();
                    } else {
                        alert('操作失败: ' + (result.message || result.msg));
                        $btn.prop('disabled', false).html(originalHtml);
                    }
                },
                error: function() {
                    alert('请求失败，请稍后重试');
                    $btn.prop('disabled', false).html(originalHtml);
                }
            });
        });

        // ==================== 执行任务 ====================
        // 立即执行任务
        $('.btn-execute').click(function() {
            var taskId = $(this).data('task-id');
            var taskName = $(this).data('task-name');
            
            if (!confirm('确定要立即执行任务【' + taskName + '】吗？')) {
                return;
            }
            
            executeTask(taskId, null, null, $(this));
        });

        // 带页码范围参数执行
        $('.btn-execute-with-page').click(function() {
            var taskId = $(this).data('task-id');
            var taskName = $(this).data('task-name');
            
            var startPage = prompt('请输入开始页码（正整数）：', '1');
            if (startPage === null) return;
            
            startPage = parseInt(startPage);
            if (isNaN(startPage) || startPage < 1) {
                alert('开始页码必须是大于0的整数');
                return;
            }
            
            var endPage = prompt('请输入结束页码（正整数）：', startPage);
            if (endPage === null) return;
            
            endPage = parseInt(endPage);
            if (isNaN(endPage) || endPage < 1) {
                alert('结束页码必须是大于0的整数');
                return;
            }
            
            if (endPage < startPage) {
                alert('结束页码不能小于开始页码');
                return;
            }
            
            executeTask(taskId, startPage, endPage, $(this));
        });

        // 查看执行日志
        $('.btn-view-logs').click(function() {
            var taskId = $(this).data('task-id');
            var taskName = $(this).data('task-name');
            
            $('#logTaskName').text(taskName);
            $('#logModal').modal('show');
            
            loadTaskLogs(taskId, 1);
        });

        // ==================== 辅助函数 ====================
        function validateTaskForm() {
            var taskName = $('#taskName').val().trim();
            var taskClass = $('#taskClass').val().trim();
            var taskMethod = $('#taskMethod').val().trim();
            var cronExpression = $('#cronExpression').val().trim();

            if (!taskName) {
                alert('请输入任务名称');
                $('#taskName').focus();
                return false;
            }

            if (!taskClass) {
                alert('请输入任务类');
                $('#taskClass').focus();
                return false;
            }

            if (!taskMethod) {
                alert('请输入方法名');
                $('#taskMethod').focus();
                return false;
            }

            if (!cronExpression) {
                alert('请输入Cron表达式');
                $('#cronExpression').focus();
                return false;
            }

            // 简单的Cron表达式验证
            var cronParts = cronExpression.split(' ');
            if (cronParts.length < 6) {
                alert('Cron表达式格式不正确，至少需要6个部分');
                $('#cronExpression').focus();
                return false;
            }

            return true;
        }

        function resetTaskForm() {
            $('#taskForm')[0].reset();
            $('input[name="status"][value="1"]').prop('checked', true);
        }

        // 执行任务
        function executeTask(taskId, startPage, endPage, $btn) {
            var originalHtml = $btn.html();
            $btn.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 执行中...');
            
            var url = '/admin/cron/execute/' + taskId;
            var params = {};
            if (startPage !== null && startPage !== undefined) {
                params.startPage = startPage;
            }
            if (endPage !== null && endPage !== undefined) {
                params.endPage = endPage;
            }
            
            $.ajax({
                url: url,
                type: 'POST',
                data: params,
                dataType: 'json',
                success: function(result) {
                    if (result.code === 0) {
                        alert('任务已提交执行，日志ID: ' + (result.logId || result.logId));
                    } else {
                        alert('执行失败: ' + (result.message || result.msg));
                    }
                },
                error: function() {
                    alert('请求失败，请稍后重试');
                },
                complete: function() {
                    $btn.prop('disabled', false).html(originalHtml);
                }
            });
        }

        // 加载任务执行日志
        function loadTaskLogs(taskId, page) {
            $('#logContent').html('<div class="text-center"><i class="fa fa-spinner fa-spin fa-2x"></i><p>加载中...</p></div>');
            
            $.ajax({
                url: '/admin/cron/logs/' + taskId,
                type: 'GET',
                data: { page: page, pageSize: 20 },
                dataType: 'json',
                success: function(result) {
                    if (result.code === 0) {
                        renderLogs(result.logs, result.page, result.totalPages, taskId);
                    } else {
                        $('#logContent').html('<div class="alert alert-danger">' + (result.message || result.msg) + '</div>');
                    }
                },
                error: function() {
                    $('#logContent').html('<div class="alert alert-danger">加载失败</div>');
                }
            });
        }

        // 渲染日志列表
        function renderLogs(logs, page, totalPages, taskId) {
            if (!logs || logs.length === 0) {
                $('#logContent').html('<div class="alert alert-info">暂无执行日志</div>');
                return;
            }
            
            var html = '<table class="table table-striped table-hover log-table">';
            html += '<thead><tr>';
            html += '<th width="80">ID</th>';
            html += '<th width="100">执行类型</th>';
            html += '<th width="120">参数</th>';
            html += '<th width="150">开始时间</th>';
            html += '<th width="150">结束时间</th>';
            html += '<th width="80">耗时(ms)</th>';
            html += '<th width="80">状态</th>';
            html += '<th width="100">执行人</th>';
            html += '<th>操作</th>';
            html += '</tr></thead><tbody>';
            
            $.each(logs, function(i, log) {
                var statusClass = log.status.toLowerCase();
                var statusText = log.status === 'RUNNING' ? '运行中' : 
                                 log.status === 'SUCCESS' ? '成功' : '失败';
                
                html += '<tr>';
                html += '<td>' + log.id + '</td>';
                html += '<td>' + (log.executeType === 'AUTO' ? '自动' : '手动') + '</td>';
                html += '<td>' + (log.executeParams || '-') + '</td>';
                html += '<td>' + formatDate(log.startTime) + '</td>';
                html += '<td>' + (log.endTime ? formatDate(log.endTime) : '-') + '</td>';
                html += '<td>' + (log.duration || '-') + '</td>';
                html += '<td><span class="log-status ' + statusClass + '">' + statusText + '</span></td>';
                html += '<td>' + (log.createBy || '-') + '</td>';
                html += '<td>';
                if (log.logContent || log.errorMsg) {
                    html += '<button class="btn btn-xs btn-info btn-view-detail" data-log=\'' + 
                            JSON.stringify(log).replace(/'/g, "&apos;") + '\'>查看详情</button>';
                }
                html += '</td>';
                html += '</tr>';
            });
            
            html += '</tbody></table>';
            
            // 分页
            if (totalPages > 1) {
                html += '<nav><ul class="pagination">';
                for (var i = 1; i <= totalPages; i++) {
                    html += '<li' + (i === page ? ' class="active"' : '') + '>';
                    html += '<a href="#" data-page="' + i + '" data-task-id="' + taskId + '">' + i + '</a>';
                    html += '</li>';
                }
                html += '</ul></nav>';
            }
            
            $('#logContent').html(html);
            
            // 绑定分页点击事件
            $('.pagination a').click(function(e) {
                e.preventDefault();
                var page = $(this).data('page');
                var taskId = $(this).data('task-id');
                loadTaskLogs(taskId, page);
            });
            
            // 绑定查看详情事件
            $('.btn-view-detail').click(function() {
                var log = JSON.parse($(this).attr('data-log'));
                showLogDetail(log);
            });
        }

        // 显示日志详情
        function showLogDetail(log) {
            var html = '<div class="form-horizontal">';
            html += '<div class="form-group"><label class="col-sm-2 control-label">任务名称：</label><div class="col-sm-10"><p class="form-control-static">' + log.taskName + '</p></div></div>';
            html += '<div class="form-group"><label class="col-sm-2 control-label">执行状态：</label><div class="col-sm-10"><p class="form-control-static"><span class="log-status ' + log.status.toLowerCase() + '">' + log.status + '</span></p></div></div>';
            
            if (log.errorMsg) {
                html += '<div class="form-group"><label class="col-sm-2 control-label">错误信息：</label><div class="col-sm-10"><p class="form-control-static text-danger">' + log.errorMsg + '</p></div></div>';
            }
            
            if (log.logContent) {
                html += '<div class="form-group"><label class="col-sm-12 control-label">执行日志：</label><div class="col-sm-12"><div class="log-detail">' + escapeHtml(log.logContent) + '</div></div></div>';
            }
            
            html += '</div>';
            
            $('#logDetailContent').html(html);
            $('#logDetailModal').modal('show');
        }

        // 日期格式化
        function formatDate(timestamp) {
            if (!timestamp) return '-';
            var date = new Date(timestamp);
            return date.getFullYear() + '-' + 
                   pad(date.getMonth() + 1) + '-' + 
                   pad(date.getDate()) + ' ' + 
                   pad(date.getHours()) + ':' + 
                   pad(date.getMinutes()) + ':' + 
                   pad(date.getSeconds());
        }

        function pad(n) {
            return n < 10 ? '0' + n : n;
        }

        function escapeHtml(text) {
            var map = {
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                '"': '&quot;',
                "'": '&#039;'
            };
            return text.replace(/[&<>"']/g, function(m) { return map[m]; });
        }
    });
    </script>
</body>
</html>