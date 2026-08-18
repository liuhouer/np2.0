<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <meta name="robots" content="index,follow">
    <link rel="shortcut icon" href="/static/img/favicon.ico">
    <title>八字排盘 | NorthPark</title>
    <meta name="description" content="NorthPark 八字排盘 - 精准解读命运轨迹">
    <meta name="keywords" content="八字排盘,生辰八字,命理分析,运势查询,NorthPark">

    <%@ include file="/WEB-INF/views/page/common/common.jsp" %>
    
    <style>
        /* 八字排盘专属样式 */
        .bazi-container {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            padding-bottom: 40px;
        }
        
        .bazi-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 120px 30px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        
        .bazi-title {
            font-size: 36px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .bazi-subtitle {
            font-size: 18px;
            opacity: 0.9;
        }
        
        .bazi-form {
            background: white;
            margin: 30px 20px;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            /* 不能设 overflow:hidden，否则 select 下拉列表会被截断 */
            overflow: visible;
        }
        
        .form-title {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-label {
            display: block;
            font-size: 16px;
            color: #666;
            margin-bottom: 8px;
            font-weight: 500;
        }
        
        .form-control {
            height: 45px;
            font-size: 16px;
            border-radius: 8px;
            border: 2px solid #e0e0e0;
            background: #f9f9f9;
            /* 确保下拉框可点击，不被父级 overflow 截断 */
            position: relative;
            z-index: 10;
            pointer-events: auto;
            -webkit-appearance: menulist;
            appearance: menulist;
        }
        
        .form-control:focus {
            border-color: #667eea;
            background: white;
            box-shadow: none;
            outline: none;
        }
        
        /* select 专属：确保下拉列表不被裁剪 */
        select.form-control {
            overflow: visible;
            cursor: pointer;
        }
        
        /* 强制覆盖 Bootstrap 对 select 的 appearance 重置，恢复原生下拉行为 */
        select.form-control,
        select.form-control:focus {
            -webkit-appearance: menulist !important;
            -moz-appearance: menulist !important;
            appearance: menulist !important;
            background-image: none !important;
            padding-right: 8px;
        }
        
        .gender-selector {
            display: flex;
            gap: 15px;
        }
        
        .gender-option {
            flex: 1;
            padding: 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            text-align: center;
            background: #f9f9f9;
            cursor: pointer;
            transition: all 0.3s;
            /* 确保点击事件不被遮挡 */
            position: relative;
            z-index: 5;
            user-select: none;
            -webkit-user-select: none;
        }
        
        .gender-option:hover {
            border-color: #667eea;
        }
        
        .gender-option.active {
            border-color: #667eea;
            background: #f0f4ff;
            color: #667eea;
        }
        
        .gender-icon {
            font-size: 32px;
            display: block;
            margin-bottom: 8px;
        }
        
        .submit-btn {
            width: 100%;
            padding: 15px 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 20px;
            font-weight: bold;
            margin-top: 20px;
            cursor: pointer;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }
        
        .submit-btn:hover {
            opacity: 0.9;
        }
        
        .submit-btn:disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }
        
        .result-section {
            margin: 30px 20px;
        }
        
        .tab-bar {
            display: flex;
            background: white;
            border-radius: 12px 12px 0 0;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        }
        
        .tab-item {
            flex: 1;
            padding: 15px;
            text-align: center;
            font-size: 18px;
            color: #999;
            border-bottom: 4px solid transparent;
            cursor: pointer;
        }
        
        .tab-item.active {
            color: #667eea;
            border-bottom-color: #667eea;
            background: #f8f9ff;
        }
        
        .tab-content {
            background: white;
            border-radius: 0 0 12px 12px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        }
        
        .bazi-card {
            background: white;
            border-radius: 12px;
            padding: 24px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #f0f0f0;
        }
        
        .card-title {
            font-size: 22px;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #f0f0f0;
        }
        
        .info-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }
        
        .info-item {
            flex: 1;
            min-width: 140px;
            padding: 16px;
            background: #f9f9f9;
            border-radius: 8px;
            border-left: 4px solid #667eea;
        }
        
        .info-label {
            font-size: 14px;
            color: #999;
            margin-bottom: 8px;
        }
        
        .info-value {
            font-size: 16px;
            color: #333;
            font-weight: 500;
        }
        
        .action-buttons {
            display: flex;
            gap: 16px;
            margin-top: 30px;
        }
        
        .action-btn {
            flex: 1;
            padding: 12px 0;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }
        
        .save-btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .share-btn {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 9999;
        }
        
        .loading-spinner {
            background: white;
            padding: 30px;
            border-radius: 12px;
            text-align: center;
        }
        
        .hidden {
            display: none;
        }

        /* AI 解读专属样式 */
        .ai-btn {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }

        .ai-question-btn {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }

        .ai-content {
            line-height: 1.8;
            color: #444;
            font-size: 15px;
        }

        .ai-content h3 {
            color: #667eea;
            margin: 20px 0 10px;
            font-size: 18px;
        }

        .ai-content p {
            margin-bottom: 12px;
        }

        .ai-generate-box {
            text-align: center;
            padding: 40px 20px;
        }

        .ai-generate-box p {
            color: #999;
            margin-bottom: 20px;
        }

        .ai-question-box {
            margin-top: 20px;
            padding: 20px;
            background: #f8f9ff;
            border-radius: 12px;
            border: 1px solid #e0e5ff;
        }

        .ai-question-box label {
            display: block;
            font-size: 14px;
            color: #666;
            margin-bottom: 8px;
        }

        .ai-question-input {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 15px;
            margin-bottom: 12px;
            resize: vertical;
            min-height: 60px;
        }

        .ai-question-input:focus {
            border-color: #667eea;
            outline: none;
        }

        .ai-answer-card {
            margin-top: 20px;
            padding: 20px;
            background: #fff9f0;
            border-radius: 12px;
            border-left: 4px solid #ff9f43;
        }

        .ai-answer-card .answer-title {
            font-weight: bold;
            color: #ff9f43;
            margin-bottom: 10px;
            font-size: 16px;
        }

        .ai-answer-card .answer-content {
            line-height: 1.8;
            color: #555;
            white-space: pre-wrap;
        }
    </style>
</head>

<body>
<%@ include file="/WEB-INF/views/page/common/navigation.jsp" %>

<div class="clearfix maincontent bazi-container">
    <!-- 顶部 Header -->
    <div class="bazi-header">
        <div class="bazi-title">八字排盘</div>
        <div class="bazi-subtitle">精准解读命运轨迹</div>
    </div>

    <!-- 输入表单 -->
    <div class="bazi-form">
        <div class="form-title">请输入您的出生信息</div>
        
        <div class="form-group">
            <label class="form-label">姓名（可选）</label>
            <input type="text" class="form-control" id="name" placeholder="请输入姓名或备注">
        </div>
        
        <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="form-group">
                    <label class="form-label">出生年</label>
                    <select class="form-control" id="year"></select>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="form-group">
                    <label class="form-label">出生月</label>
                    <select class="form-control" id="month"></select>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4">
                <div class="form-group">
                    <label class="form-label">出生日</label>
                    <select class="form-control" id="day"></select>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-6">
                <div class="form-group">
                    <label class="form-label">出生时</label>
                    <select class="form-control" id="hour"></select>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-6">
                <div class="form-group">
                    <label class="form-label">出生分</label>
                    <select class="form-control" id="minute"></select>
                </div>
            </div>
        </div>
        
        <div class="form-group">
            <label class="form-label">性别</label>
            <div class="gender-selector">
                <label class="gender-option active" id="genderMale">
                    <input type="radio" name="genderRadio" value="male" checked style="display:none;">
                    <span class="gender-icon">♂</span>
                    <span>男</span>
                </label>
                <label class="gender-option" id="genderFemale">
                    <input type="radio" name="genderRadio" value="female" style="display:none;">
                    <span class="gender-icon">♀</span>
                    <span>女</span>
                </label>
            </div>
        </div>
        
        <button class="submit-btn" id="submitBtn">开始排盘</button>
    </div>

    <!-- 排盘结果 -->
    <div class="result-section hidden" id="resultSection">
        <!-- 标签页 -->
        <div class="tab-bar">
            <div class="tab-item active" data-tab="pan">排盘分析</div>
            <div class="tab-item" data-tab="yun">运势分析</div>
            <div class="tab-item" data-tab="ai">AI 解读</div>
        </div>
        
        <!-- 排盘分析内容 -->
        <div class="tab-content" id="panContent">
            <div class="bazi-card">
                <div class="card-title">基本信息</div>
                <div class="info-grid" id="basicInfo"></div>
            </div>
            
            <div class="bazi-card">
                <div class="card-title">排盘详细分析</div>
                <div id="panText"></div>
            </div>
        </div>
        
        <!-- 运势分析内容 -->
        <div class="tab-content hidden" id="yunContent">
            <div class="bazi-card">
                <div class="card-title">运势详细分析</div>
                <div id="yunText"></div>
            </div>
        </div>

        <!-- AI 解读内容 -->
        <div class="tab-content hidden" id="aiContent">
            <div class="bazi-card">
                <div class="card-title">AI 命理大师解读</div>
                <div id="aiInterpretBox" class="ai-generate-box">
                    <p>基于您的八字排盘数据，AI 命理大师将为您生成深度解读报告</p>
                    <button class="action-btn ai-btn" id="aiInterpretBtn" style="width:auto;padding:12px 30px;">✨ 生成 AI 深度解读</button>
                </div>
                <div id="aiInterpretResult" class="ai-content hidden"></div>
            </div>

            <div class="bazi-card">
                <div class="card-title">AI 运势问答</div>
                <div class="ai-question-box">
                    <label>向 AI 命理大师提问（如：今年适合跳槽吗？）</label>
                    <textarea class="ai-question-input" id="aiQuestionInput" placeholder="请输入您的问题，最多200字..."></textarea>
                    <button class="action-btn ai-question-btn" id="aiAdviceBtn" style="width:auto;padding:10px 24px;">🎯 获取建议</button>
                </div>
                <div id="aiAdviceResult"></div>
            </div>
        </div>

        <!-- 操作按钮 -->
        <div class="action-buttons">
            <button class="action-btn save-btn" id="saveBtn">💾 保存结果</button>
            <button class="action-btn share-btn" id="shareBtn">📋 分享结果</button>
        </div>
    </div>
</div>

<!-- 加载遮罩 -->
<div class="loading-overlay" id="loadingOverlay">
    <div class="loading-spinner">
        <img src="/static/img/loading.gif" width="40" height="40">
        <div style="margin-top: 10px;">排盘中...</div>
    </div>
</div>

<%@ include file="/WEB-INF/views/page/common/container.jsp" %>

<script>
$(function() {
    let currentTab = 'pan';
    let baziResult = null;
    let openId = null;

    // 初始化下拉框选项
    initSelectors();

    // 方案一：生成或读取 UUID 作为用户唯一标识
    initBaziUid();

    // 自动加载历史记录
    loadLatestRecord();

    // 性别切换：用原生 radio change 事件，彻底绕开 Bootstrap 事件干扰
    $('input[name="genderRadio"]').on('change', function() {
        var val = $(this).val();
        if (val === 'male') {
            $('#genderMale').addClass('active');
            $('#genderFemale').removeClass('active');
        } else {
            $('#genderFemale').addClass('active');
            $('#genderMale').removeClass('active');
        }
    });

    // 标签页切换
    $(document).on('click', '.tab-item', function(e) {
        e.preventDefault();
        $('.tab-item').removeClass('active');
        $(this).addClass('active');
        currentTab = $(this).data('tab');
        $('#panContent, #yunContent, #aiContent').addClass('hidden');
        if (currentTab === 'pan') {
            $('#panContent').removeClass('hidden');
        } else if (currentTab === 'yun') {
            $('#yunContent').removeClass('hidden');
        } else if (currentTab === 'ai') {
            $('#aiContent').removeClass('hidden');
        }
    });

    // 提交排盘
    $('#submitBtn').on('click', function() {
        submitBazi();
    });

    // 保存结果
    $('#saveBtn').on('click', function() {
        saveResult();
    });

    // 分享结果
    $('#shareBtn').on('click', function() {
        shareResult();
    });

    // AI 深度解读
    $('#aiInterpretBtn').on('click', function() {
        aiInterpret();
    });

    // AI 运势问答
    $('#aiAdviceBtn').on('click', function() {
        aiAdvice();
    });

    // 初始化年月日时分下拉框
    function initSelectors() {
        var currentYear = new Date().getFullYear();
        var i, opt;

        for (i = currentYear; i >= 1900; i--) {
            $('#year').append('<option value="' + i + '">' + i + '</option>');
        }
        $('#year').val(currentYear);

        for (i = 1; i <= 12; i++) {
            $('#month').append('<option value="' + i + '">' + i + '</option>');
        }
        $('#month').val(1);

        for (i = 1; i <= 31; i++) {
            $('#day').append('<option value="' + i + '">' + i + '</option>');
        }
        $('#day').val(1);

        for (i = 0; i <= 23; i++) {
            opt = i < 10 ? '0' + i : '' + i;
            $('#hour').append('<option value="' + i + '">' + opt + '</option>');
        }
        $('#hour').val(12);

        for (i = 0; i <= 59; i++) {
            opt = i < 10 ? '0' + i : '' + i;
            $('#minute').append('<option value="' + i + '">' + opt + '</option>');
        }
        $('#minute').val(0);
    }

    // Cookie 辅助函数（作为 localStorage 的备份，更不容易被误清除）
    function setCookie(name, value, days) {
        var expires = '';
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = '; expires=' + date.toUTCString();
        }
        document.cookie = name + '=' + encodeURIComponent(value) + expires + '; path=/; SameSite=Lax';
    }

    function getCookie(name) {
        var nameEQ = name + '=';
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(nameEQ) === 0) return decodeURIComponent(c.substring(nameEQ.length));
        }
        return null;
    }

    // 方案一：生成或读取 UUID（localStorage + Cookie 双备份，保证同一设备尽量不变）
    function initBaziUid() {
        // 优先级1：URL 参数传入的真实 openId（小程序跳转场景）
        var urlParams = new URLSearchParams(window.location.search);
        var urlOpenId = urlParams.get('openId');
        if (urlOpenId && /^[a-zA-Z0-9_\-]{1,64}$/.test(urlOpenId)) {
            openId = urlOpenId;
            localStorage.setItem('bazi_uid', openId);
            setCookie('bazi_uid', openId, 365);
            return;
        }

        // 优先级2：localStorage
        var uid = localStorage.getItem('bazi_uid');

        // 优先级3：Cookie（localStorage 被清除时兜底）
        if (!uid) {
            uid = getCookie('bazi_uid');
        }

        // 都没有则生成新的
        if (!uid) {
            uid = 'h5_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
        }

        // 双备份写入
        localStorage.setItem('bazi_uid', uid);
        setCookie('bazi_uid', uid, 365);
        openId = uid;
    }

    // 方案三备用：从 URL 参数或 localStorage 读取微信真实 openId
    function initOpenId() {
        var urlParams = new URLSearchParams(window.location.search);
        openId = urlParams.get('openId') || localStorage.getItem('wechat_openId');
        if (openId) {
            localStorage.setItem('wechat_openId', openId);
        }
    }

    // 自动加载最近一条历史排盘记录
    function loadLatestRecord() {
        if (!openId) return;
        $.ajax({
            url: '/api/bazi/latest',
            type: 'GET',
            headers: { 'token': '@WOAICAOBI@' },
            data: { open_id: openId },
            success: function(response) {
                if (response.code === 200 && response.data) {
                    var data = response.data;
                    // 回填表单
                    if (data.name) $('#name').val(data.name);
                    if (data.birthYear) $('#year').val(data.birthYear);
                    if (data.birthMonth) $('#month').val(data.birthMonth);
                    if (data.birthDay) $('#day').val(data.birthDay);
                    if (data.birthHour !== undefined) $('#hour').val(data.birthHour);
                    if (data.birthMinute !== undefined) $('#minute').val(data.birthMinute);

                    // 回填性别
                    var genderVal = (data.gender === 1 || data.gender === '1') ? 'male' : 'female';
                    $('input[name="genderRadio"][value="' + genderVal + '"]').prop('checked', true).trigger('change');

                    // 渲染排盘结果
                    baziResult = data;
                    displayResult(data);
                    $('#resultSection').removeClass('hidden');

                    // 若已有 AI 解读缓存，直接展示
                    if (data.aiInterpret) {
                        $('#aiInterpretBox').addClass('hidden');
                        $('#aiInterpretResult').removeClass('hidden').html(formatAiText(data.aiInterpret));
                        $('#aiInterpretBtn').text('✨ 重新生成解读');
                    }
                    // 若已有 AI 建议缓存，直接展示
                    if (data.aiAdvice) {
                        $('#aiAdviceResult').html(
                            '<div class="ai-answer-card">' +
                            '<div class="answer-title">💡 AI 运势建议</div>' +
                            '<div class="answer-content">' + formatAiText(data.aiAdvice) + '</div>' +
                            '</div>'
                        );
                    }
                }
            }
        });
    }

    // 提交排盘
    function submitBazi() {
        var name   = $('#name').val() || '用户';
        var year   = parseInt($('#year').val());
        var month  = parseInt($('#month').val());
        var day    = parseInt($('#day').val());
        var hour   = parseInt($('#hour').val());
        var minute = parseInt($('#minute').val());
        // 读取当前选中的 radio 值
        var gender = $('input[name="genderRadio"]:checked').val() || 'male';

        $('#loadingOverlay').css('display', 'flex');

        $.ajax({
            url: '/api/bazi/reading',
            type: 'POST',
            headers: { 'token': '@WOAICAOBI@' },
            data: {
                open_id: openId,
                year: year, month: month, day: day,
                hour: hour, minute: minute,
                gender: gender, name: name
            },
            success: function(response) {
                $('#loadingOverlay').hide();
                if (response.code === 200 && response.data) {
                    baziResult = response.data;
                    displayResult(response.data);
                    $('#resultSection').removeClass('hidden');
                    $('html, body').animate({ scrollTop: $('#resultSection').offset().top - 100 }, 500);
                } else {
                    art.dialog.alert(response.message || response.msg || '排盘失败');
                }
            },
            error: function(xhr) {
                $('#loadingOverlay').hide();
                var r = xhr.responseJSON;
                art.dialog.alert(r ? (r.message || r.msg || '排盘失败') : '网络请求失败');
            }
        });
    }

    // 渲染排盘结果
    function displayResult(data) {
        var panVO = data.panVO, panText = data.panText, yunText = data.yunText;

        if (panVO && panVO.basicInfo) {
            var info = panVO.basicInfo;
            var xz   = panVO.xingZuoEtc || {};
            var pad  = function(n) { return n < 10 ? '0' + n : '' + n; };

            $('#basicInfo').html(
                '<div class="info-item"><div class="info-label">姓名</div><div class="info-value">' + (info.name || '未知') + '</div></div>' +
                '<div class="info-item"><div class="info-label">性别</div><div class="info-value">' + (info.gender || '未知') + '</div></div>' +
                '<div class="info-item"><div class="info-label">公历</div><div class="info-value">' + info.solarYear + '-' + pad(info.solarMonth) + '-' + pad(info.solarDay) + '</div></div>' +
                '<div class="info-item"><div class="info-label">时间</div><div class="info-value">' + pad(info.solarHour) + ':' + pad(info.solarMinute) + '</div></div>' +
                '<div class="info-item"><div class="info-label">农历</div><div class="info-value">' + info.lunarYear + '年' + info.lunarMonth + info.lunarDay + '</div></div>' +
                '<div class="info-item"><div class="info-label">星座</div><div class="info-value">' + (xz.xingZuo || '未知') + '</div></div>' +
                '<div class="info-item"><div class="info-label">生肖</div><div class="info-value">' + (xz.shengXiao || '未知') + '</div></div>' +
                '<div class="info-item"><div class="info-label">周几</div><div class="info-value">星期' + (info.weekDay || '未知') + '</div></div>'
            );
        }

        if (panText) {
            $('#panText').html(panText.replace(/\\n/g, '<br>').replace(/\n/g, '<br>'));
        }
        if (yunText) {
            $('#yunText').html(yunText.replace(/\\n/g, '<br>').replace(/\n/g, '<br>'));
        }
    }

    // 保存结果到 localStorage
    function saveResult() {
        if (!baziResult) { art.dialog.tips('没有排盘结果'); return; }
        var isYun    = currentTab === 'yun';
        var htmlText = isYun ? (baziResult.yunText || '') : (baziResult.panText || '');
        var plain    = htmlText.replace(/<[^>]+>/g, '');
        var name     = $('#name').val() || '用户';
        var date     = $('#year').val() + '-' + $('#month').val() + '-' + $('#day').val();
        var genderTxt = $('input[name="genderRadio"]:checked').val() === 'female' ? '女' : '男';
        var content  = '【' + (isYun ? '运势分析' : '八字排盘') + '】' + name + ' ' + genderTxt + ' ' + date + '\n' + plain;
        var list = JSON.parse(localStorage.getItem('baziList') || '[]');
        list.unshift({ id: Date.now(), content: content, time: new Date().toLocaleString('zh-CN') });
        localStorage.setItem('baziList', JSON.stringify(list));
        art.dialog.tips('已保存到本地');
    }

    // 复制结果到剪贴板
    function shareResult() {
        if (!baziResult) { art.dialog.tips('没有排盘结果'); return; }
        var isYun    = currentTab === 'yun';
        var htmlText = isYun ? (baziResult.yunText || '') : (baziResult.panText || '');
        var plain    = htmlText.replace(/<[^>]+>/g, '');
        var name     = $('#name').val() || '用户';
        var date     = $('#year').val() + '-' + $('#month').val() + '-' + $('#day').val();
        var genderTxt = $('input[name="genderRadio"]:checked').val() === 'female' ? '女' : '男';
        var content  = '【' + (isYun ? '运势分析' : '八字排盘') + '】' + name + ' ' + genderTxt + ' ' + date + '\n\n' + plain;
        var ta = document.createElement('textarea');
        ta.value = content;
        document.body.appendChild(ta);
        ta.select();
        try { document.execCommand('copy'); art.dialog.tips('已复制到剪贴板'); }
        catch(e) { art.dialog.alert('复制失败，请手动复制'); }
        document.body.removeChild(ta);
    }

    // AI 深度解读
    function aiInterpret() {
        if (!baziResult || !baziResult.recordId) {
            art.dialog.alert('请先完成排盘');
            return;
        }
        var $btn = $('#aiInterpretBtn');
        $btn.prop('disabled', true).text('解读中...');
        $('#loadingOverlay').css('display', 'flex').find('div div').text('AI 命理大师解读中...');

        $.ajax({
            url: '/api/bazi/ai-interpret',
            type: 'POST',
            headers: { 'token': '@WOAICAOBI@' },
            data: { recordId: baziResult.recordId },
            success: function(response) {
                $('#loadingOverlay').hide();
                $btn.prop('disabled', false).text('✨ 重新生成解读');
                if (response.code === 200 && response.data) {
                    $('#aiInterpretBox').addClass('hidden');
                    $('#aiInterpretResult').removeClass('hidden').html(formatAiText(response.data));
                } else {
                    art.dialog.alert(response.message || response.msg || 'AI 解读失败');
                }
            },
            error: function(xhr) {
                $('#loadingOverlay').hide();
                $btn.prop('disabled', false).text('✨ 生成 AI 深度解读');
                var r = xhr.responseJSON;
                art.dialog.alert(r ? (r.message || r.msg || 'AI 解读失败') : '网络请求失败');
            }
        });
    }

    // AI 运势问答
    function aiAdvice() {
        if (!baziResult || !baziResult.recordId) {
            art.dialog.alert('请先完成排盘');
            return;
        }
        var question = $('#aiQuestionInput').val().trim();
        if (!question) {
            art.dialog.alert('请输入您的问题');
            return;
        }
        var $btn = $('#aiAdviceBtn');
        $btn.prop('disabled', true).text('思考中...');
        $('#loadingOverlay').css('display', 'flex').find('div div').text('AI 命理大师分析中...');

        $.ajax({
            url: '/api/bazi/ai-advice',
            type: 'POST',
            headers: { 'token': '@WOAICAOBI@' },
            data: { recordId: baziResult.recordId, question: question },
            success: function(response) {
                $('#loadingOverlay').hide();
                $btn.prop('disabled', false).text('🎯 获取建议');
                if (response.code === 200 && response.data) {
                    $('#aiAdviceResult').html(
                        '<div class="ai-answer-card">' +
                        '<div class="answer-title">💡 针对「' + escapeHtml(question) + '」的建议</div>' +
                        '<div class="answer-content">' + formatAiText(response.data) + '</div>' +
                        '</div>'
                    );
                } else {
                    art.dialog.alert(response.message || response.msg || '获取建议失败');
                }
            },
            error: function(xhr) {
                $('#loadingOverlay').hide();
                $btn.prop('disabled', false).text('🎯 获取建议');
                var r = xhr.responseJSON;
                art.dialog.alert(r ? (r.message || r.msg || '获取建议失败') : '网络请求失败');
            }
        });
    }

    // 格式化 AI 返回的文本（简单分段）
    function formatAiText(text) {
        if (!text) return '';
        var lines = text.split('\n');
        var html = '';
        for (var i = 0; i < lines.length; i++) {
            var line = lines[i].trim();
            if (!line) continue;
            if (/^\x23{1,3}\s/.test(line)) {
                html += '<h3>' + escapeHtml(line.replace(/^\x23{1,3}\s/, '')) + '</h3>';
            } else if (/^\d+\.\s/.test(line)) {
                html += '<p><strong>' + escapeHtml(line) + '</strong></p>';
            } else {
                html += '<p>' + escapeHtml(line) + '</p>';
            }
        }
        return html;
    }

    // HTML 转义
    function escapeHtml(str) {
        if (!str) return '';
        return str.replace(/&/g, '&amp;')
                  .replace(/</g, '&lt;')
                  .replace(/>/g, '&gt;')
                  .replace(/"/g, '&quot;');
    }
});
</script>

</body>
</html>
