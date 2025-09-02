//load data...
$(function () {
    var pagenow = parseInt($("#pagenow").val()) ;
    
    // 添加加载动画
    $("#J_progress").html(`
        <div class="loading-wrapper">
            <div class="loading-spinner"></div>
            <div class="loading-text">正在加载精彩内容...</div>
        </div>
    `);
    
    $.ajax({
        url: "/note/storyquery",
        type: "post",
        data: {"currentPage": pagenow},
        beforeSend: beforeSend, //发送请求
        complete: complete,
        success: function (data) {
            if (data) {
                // 添加淡入效果
                $("#J_maincontent").hide().append(data).fadeIn(800);
                // 初始化展开/收起功能
                initExpandButtons();
            }
        }
    });
})

// 初始化展开按钮功能
function initExpandButtons() {
    // 检查每个内容区域是否需要展开按钮
    $('.story-content-text').each(function() {
        var $contentWrapper = $(this);
        var $textContent = $contentWrapper.find('.story-text-content');
        var $expandBtn = $contentWrapper.siblings('.story-actions').find('.story-expand-btn');
        
        // 检查内容是否超过限制高度
        var originalHeight = $textContent[0].scrollHeight;
        var maxHeight = 120; // 对应CSS中的max-height
        
        if (originalHeight > maxHeight) {
            // 内容超长，显示展开按钮并添加截断样式
            $textContent.addClass('truncated');
            $expandBtn.show();
        } else {
            // 内容不长，隐藏展开按钮
            $expandBtn.hide();
        }
    });
    
    // 绑定展开按钮点击事件
    $(document).off('click', '.story-expand-btn').on('click', '.story-expand-btn', function(e) {
        e.preventDefault();
        
        var $btn = $(this);
        var targetId = $btn.data('target');
        var $content = $(targetId);
        var $textContent = $content.find('.story-text-content');
        var $expandText = $btn.find('.expand-text');
        var $expandIcon = $btn.find('.expand-icon');
        
        if ($textContent.hasClass('expanded')) {
            // 收起
            $textContent.removeClass('expanded').addClass('truncated');
            $expandText.text('展开');
            $btn.removeClass('expanded');
            
            // 平滑滚动到卡片顶部
            $('html, body').animate({
                scrollTop: $content.closest('.story-card').offset().top - 80
            }, 300);
        } else {
            // 展开
            $textContent.removeClass('truncated').addClass('expanded');
            $expandText.text('收起');
            $btn.addClass('expanded');
        }
    });
}


function beforeSend(XMLHttpRequest) {
    $("#J_progress").show();
}

function complete(XMLHttpRequest, textStatus) {
    $("#J_progress").fadeOut();
}
  

