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
            padding: 40px 30px;
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
        }
        
        .form-control:focus {
            border-color: #667eea;
            background: white;
            box-shadow: none;
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
                <div class="gender-option active" data-gender="male">
                    <span class="gender-icon">♂</span>
                    <span>男</span>
                </div>
                <div class="gender-option" data-gender="female">
                    <span class="gender-icon">♀</span>
                    <span>女</span>
                </div>
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
    let selectedGender = 'male';
    let currentTab = 'pan';
    let baziResult = null;
    let openId = null;
    
    // 初始化下拉框
    initSelectors();
    
    // 获取 openId（从微信公众号授权）
    initOpenId();
    
    // 性别选择
    $('.gender-option').click(function() {
        $('.gender-option').removeClass('active');
        $(this).addClass('active');
        selectedGender = $(this).data('gender');
    });
    
    // 标签页切换
    $('.tab-item').click(function() {
        $('.tab-item').removeClass('active');
        $(this).addClass('active');
        currentTab = $(this).data('tab');
        
        if (currentTab === 'pan') {
            $('#panContent').removeClass('hidden');
            $('#yunContent').addClass('hidden');
        } else {
            $('#panContent').addClass('hidden');
            $('#yunContent').removeClass('hidden');
        }
    });
    
    // 提交排盘
    $('#submitBtn').click(function() {
        submitBazi();
    });
    
    // 保存结果
    $('#saveBtn').click(function() {
        saveResult();
    });
    
    // 分享结果
    $('#shareBtn').click(function() {
        shareResult();
    });
    
    // 初始化下拉框
    function initSelectors() {
        const currentYear = new Date().getFullYear();
        
        // 年份
        for (let i = currentYear; i >= 1900; i--) {
            $('#year').append(`<option value="${i}">${i}</option>`);
        }
        $('#year').val(currentYear);
        
        // 月份
        for (let i = 1; i <= 12; i++) {
            $('#month').append(`<option value="${i}">${i}</option>`);
        }
        $('#month').val(1);
        
        // 日期
        for (let i = 1; i <= 31; i++) {
            $('#day').append(`<option value="${i}">${i}</option>`);
        }
        $('#day').val(1);
        
        // 小时
        for (let i = 0; i <= 23; i++) {
            $('#hour').append(`<option value="${i}">${i.toString().padStart(2, '0')}</option>`);
        }
        $('#hour').val(12);
        
        // 分钟
        for (let i = 0; i <= 59; i++) {
            $('#minute').append(`<option value="${i}">${i.toString().padStart(2, '0')}</option>`);
        }
        $('#minute').val(0);
    }
    
    // 初始化 openId（从 URL 参数或微信授权）
    function initOpenId() {
        // 从 URL 参数获取 openId
        const urlParams = new URLSearchParams(window.location.search);
        openId = urlParams.get('openId');
        
        if (!openId) {
            // 从 localStorage 获取缓存的 openId
            openId = localStorage.getItem('wechat_openId');
        }
        
        if (!openId) {
            // 需要微信授权获取 openId
            // 这里应该跳转到微信授权页面
            console.warn('未获取到 openId，需要微信授权');
        }
    }
    
    // 提交排盘
    function submitBazi() {
        const name = $('#name').val() || '用户';
        const year = parseInt($('#year').val());
        const month = parseInt($('#month').val());
        const day = parseInt($('#day').val());
        const hour = parseInt($('#hour').val());
        const minute = parseInt($('#minute').val());
        const gender = selectedGender;
        
        if (!openId) {
            art.dialog.alert('请先通过微信授权登录');
            return;
        }
        
        // 显示加载
        $('#loadingOverlay').css('display', 'flex');
        
        $.ajax({
            url: '/api/bazi/reading',
            type: 'POST',
            headers: {
                'token': '@WOAICAOBI@'
            },
            data: {
                open_id: openId,
                year: year,
                month: month,
                day: day,
                hour: hour,
                minute: minute,
                gender: gender,
                name: name
            },
            success: function(response) {
                $('#loadingOverlay').hide();
                
                if (response.code === 200 && response.data) {
                    baziResult = response.data;
                    displayResult(response.data);
                    $('#resultSection').removeClass('hidden');
                    
                    // 滚动到结果区域
                    $('html, body').animate({
                        scrollTop: $('#resultSection').offset().top - 100
                    }, 500);
                } else {
                    const msg = response.message || response.msg || '排盘失败';
                    art.dialog.alert(msg);
                }
            },
            error: function(xhr) {
                $('#loadingOverlay').hide();
                const response = xhr.responseJSON;
                const msg = response ? (response.message || response.msg || '排盘失败') : '网络请求失败';
                art.dialog.alert(msg);
            }
        });
    }
    
    // 显示结果
    function displayResult(data) {
        const { panVO, panText, yunText } = data;
        
        if (panVO && panVO.basicInfo) {
            const info = panVO.basicInfo;
            const xingzuo = panVO.xingZuoEtc || {};
            
            $('#basicInfo').html(`
                <div class="info-item">
                    <div class="info-label">姓名</div>
                    <div class="info-value">${info.name || '未知'}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">性别</div>
                    <div class="info-value">${info.gender || '未知'}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">公历</div>
                    <div class="info-value">${info.solarYear}-${String(info.solarMonth).padStart(2, '0')}-${String(info.solarDay).padStart(2, '0')}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">时间</div>
                    <div class="info-value">${String(info.solarHour).padStart(2, '0')}:${String(info.solarMinute).padStart(2, '0')}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">农历</div>
                    <div class="info-value">${info.lunarYear}年${info.lunarMonth}${info.lunarDay}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">星座</div>
                    <div class="info-value">${xingzuo.xingZuo || '未知'}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">生肖</div>
                    <div class="info-value">${xingzuo.shengXiao || '未知'}</div>
                </div>
                <div class="info-item">
                    <div class="info-label">周几</div>
                    <div class="info-value">星期${info.weekDay || '未知'}</div>
                </div>
            `);
        }
        
        // 显示排盘分析（将 \n 转为 <br>）
        if (panText) {
            const formattedPanText = panText.replace(/\\n/g, '<br>').replace(/\n/g, '<br>');
            $('#panText').html(formattedPanText);
        }
        
        // 显示运势分析（将 \n 转为 <br>）
        if (yunText) {
            const formattedYunText = yunText.replace(/\\n/g, '<br>').replace(/\n/g, '<br>');
            $('#yunText').html(formattedYunText);
        }
    }
    
    // 保存结果
    function saveResult() {
        if (!baziResult) {
            art.dialog.tips('没有排盘结果');
            return;
        }
        
        const isYun = currentTab === 'yun';
        const htmlText = isYun ? (baziResult.yunText || '') : (baziResult.panText || '');
        const plainText = htmlText.replace(/<[^>]+>/g, '').replace(/\\n/g, '\n').replace(/\n/g, '\n');
        
        const name = $('#name').val() || '用户';
        const date = `${$('#year').val()}-${$('#month').val()}-${$('#day').val()}`;
        const gender = selectedGender === 'male' ? '男' : '女';
        
        const content = `【${isYun ? '运势分析' : '八字排盘'}】${name} ${gender} ${date}\n${plainText}`;
        
        // 保存到 localStorage
        const savedList = JSON.parse(localStorage.getItem('baziList') || '[]');
        savedList.unshift({
            id: Date.now(),
            content: content,
            time: new Date().toLocaleString('zh-CN')
        });
        localStorage.setItem('baziList', JSON.stringify(savedList));
        
        art.dialog.tips('已保存到本地');
    }
    
    // 分享结果
    function shareResult() {
        if (!baziResult) {
            art.dialog.tips('没有排盘结果');
            return;
        }
        
        const isYun = currentTab === 'yun';
        const htmlText = isYun ? (baziResult.yunText || '') : (baziResult.panText || '');
        const plainText = htmlText.replace(/<[^>]+>/g, '').replace(/\\n/g, '\n').replace(/\n/g, '\n');
        
        const name = $('#name').val() || '用户';
        const date = `${$('#year').val()}-${$('#month').val()}-${$('#day').val()}`;
        const gender = selectedGender === 'male' ? '男' : '女';
        
        const content = `【${isYun ? '运势分析' : '八字排盘'}】${name} ${gender} ${date}\n\n${plainText}`;
        
        // 复制到剪贴板
        const textarea = document.createElement('textarea');
        textarea.value = content;
        document.body.appendChild(textarea);
        textarea.select();
        
        try {
            document.execCommand('copy');
            art.dialog.tips('已复制到剪贴板');
        } catch (err) {
            art.dialog.alert('复制失败，请手动复制');
        }
        
        document.body.removeChild(textarea);
    }
});
</script>

</body>
</html>
