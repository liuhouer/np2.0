var lrcid = $("#J_lrcid").val();
var uid = $("#J_uid").val();
var yizan = $("#J_yizan").val();
$(document).ready(function () {


    //获取所有点赞人填充
    $("#J_lovers_box").click(function () {

        // 隐藏按钮
        $(this).hide();

        $.ajax({

            url: "/lyrics/getMoreZan",

            type: "post",

            data: {"lyricsid": lrcid},

            success: function (data) {
                //填充结果
                $("#J_zan_div").empty().prepend(data.replace(/(^")|("$)/g, ''));
            }

        });
    })

    // 评论提交功能已移至 JSP 文件中的 Quill.js 初始化代码


    //添加到最爱
    $("#J_gz_btn").click(function () {
        //把userid的判断转为后台判断
        let uri = window.location.href;
        if (yizan == 'yizan') {
            return ;
        }

        let loveDate = $("#loveDate").val();
        if(!loveDate){
            art.dialog.tips("完善爱上时间");
            return ;
        }
        $.ajax({
            url: "/cm/loginFlag",
            type: "get",
            dataType: "json",
            success: function (msg) {
                if (msg.data == "1") {//已登录
                    var userid = uid;
                    $.ajax({
                        url: "/zanAction/zan",
                        type: "post",
                        dataType: "json",
                        data: {"lyricsid": lrcid, "userid": userid,"loveDate": loveDate},
                        beforeSend: beforeSendZAN, //发送请求
                        complete: completeZAN,
                        success: function (data) {
                            if (data.data == "success") {
                                art.dialog.tips("已爱上~");
                                //$("#J_gz_btn").text('已爱上~');
                                window.location.href = window.location.href;
                            }
                        }
                    });

                } else if (msg.data == "0") {//没有登录

                    window.location.href = "/login?redirectURI=" + uri;
                }

            }
        });


    });


    //load comment
    loadcmt();


    //加载更多
    $("#loadStuffCommentBtn").click(function () {
        var pagenow = $("#comment_id_from").val();
        $("#comment_id_from").val(parseInt(pagenow) + 1);
        loadcmt();

    })


});


// wangEditor 已替换为 Quill.js，相关初始化代码已移至 JSP 文件中


//load data...
function loadcmt() {
    var pagenow = $("#comment_id_from").val();
    var lrcid = $("#J_lrc_id").val();
    $.ajax({
        url: "/lyrics/commentQuery",
        type: "post",
        data: {"currentPage": pagenow, "lrcid": lrcid},
        beforeSend: beforeSend, //发送请求
        complete: complete,
        success: function (data) {
            if (data) {
                $("#stuffCommentBox").append(data);
                var tail = $("#J_tail").val();
                if (tail == 'tail') {
                    $("#loadStuffCommentBtn").remove();
                }
            }
        }
    });

}


function beforeSend(XMLHttpRequest) {
    $("#loadingAnimation").show();
}

function complete(XMLHttpRequest, textStatus) {
    $("#loadingAnimation").hide();
}

function beforeSendZAN(XMLHttpRequest) {
    $("#showResult").append("<div><img src='/static/img/loading.gif' style='width:32px;height:32px;' /></div>");
}

function completeZAN(XMLHttpRequest, textStatus) {
    $("#showResult").empty();
}




  
  
