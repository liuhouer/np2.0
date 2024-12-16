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
            }
        }
    });
    
    // 添加滚动加载
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() == $(document).height()) {
            loadMoreStories();
        }
    });
})


function beforeSend(XMLHttpRequest) {
    $("#J_progress").show();
}

function complete(XMLHttpRequest, textStatus) {
    $("#J_progress").fadeOut();
}
  

