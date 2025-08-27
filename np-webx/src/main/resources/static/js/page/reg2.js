$(function () {
    $('#login #newPassword').focus(function () {
        $('#owl-login').addClass('password');
    }).blur(function () {
        $('#owl-login').removeClass('password');
    });
});

function em(email) {

    var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/;

    if (Regex.test(email)) {

        return true;

    } else {
        return false;
    }

}

$(document).ready(function () {

    code_draw();

    // 点击后刷新验证码
    $("#canvas").on('click', function () {
        code_draw();
    });

    var emailVerified = false;
    var countdown = 0;

    // 发送邮箱验证码
    $("#sendEmailCode").click(function () {
        var email = $("#newAccount").val();
        if (!em(email)) {
            art.dialog.tips('请输入正确的邮箱地址', 3);
            return;
        }

        if (countdown > 0) {
            return;
        }

        $.ajax({
            url: "/cm/sendRegisterEmail",
            type: "post",
            dataType: "json",
            data: { email: email },
            beforeSend: function(XMLHttpRequest) {
                $("#showResult").append("<div><img src='/static/img/loading.gif' style='width:32px;height:32px;' /></div>");
                $("#sendEmailCode").attr('disabled', true).text('发送中...');
            },
            complete: function(XMLHttpRequest, textStatus) {
                $("#showResult").empty();
            },
            success: function (msg) {
                if (msg.result) {
                    art.dialog.tips('验证码已发送到您的邮箱', 3);
                    startCountdown();
                } else {
                    art.dialog.tips('发送失败：' + msg.message, 3);
                    $("#sendEmailCode").removeAttr('disabled').text('发送验证码');
                }
            },
            error: function () {
                art.dialog.tips('发送失败，请稍后重试', 3);
                $("#sendEmailCode").removeAttr('disabled').text('发送验证码');
            }
        });
    });

    // 验证邮箱验证码
    $("#emailCode").blur(function () {
        var email = $("#newAccount").val();
        var code = $(this).val();

        if (!em(email) || !code) {
            return;
        }

        $.ajax({
            url: "/cm/verifyEmailCode",
            type: "post",
            dataType: "json",
            data: { email: email, code: code },
            success: function (msg) {
                if (msg.result) {
                    emailVerified = true;
                    $("#emailCode").css('border-color', '#5cb85c');
                    art.dialog.tips('邮箱验证成功', 2);
                    checkFormValid();
                } else {
                    emailVerified = false;
                    $("#emailCode").css('border-color', '#d9534f');
                    art.dialog.tips('验证失败：' + msg.message, 3);
                    checkFormValid();
                }
            }
        });
    });

    // 检查表单有效性
    function checkFormValid() {
        if (emailVerified && $('#newPassword').val().length >= 6 && em($('#newAccount').val())) {
            $('#formSubmit').removeAttr('disabled');
        } else {
            $('#formSubmit').attr('disabled', true);
        }
    }

    // 倒计时功能
    function startCountdown() {
        countdown = 60;
        var btn = $("#sendEmailCode");
        btn.attr('disabled', true);

        var timer = setInterval(function () {
            btn.text('重新发送(' + countdown + ')');
            countdown--;

            if (countdown < 0) {
                clearInterval(timer);
                btn.text('发送验证码').removeAttr('disabled');
                countdown = 0;
            }
        }, 1000);
    }

    $('#signupForm').on('keyup', '#newPassword', function (event) {
        checkFormValid();
    });

    $("#newAccount").change(function () {
        emailVerified = false;
        $("#emailCode").val('').css('border-color', '#ccc');
        checkFormValid();
    });

    //signup
    $("#formSubmit").click(function () {
        if (!emailVerified) {
            art.dialog.tips('请先完成邮箱验证', 3);
            return;
        }

        //校验验证码
        var val = $("#code").val().toLowerCase();
        var num = $('#canvas').attr('data-code');
        if (val == '') {
            art.dialog.tips('请输入验证码...', 3);
            return;
        } else if (val == num) {
            $.ajax({
                url: "/cm/signup",
                type: "post",
                beforeSend: beforeSend,
                complete: complete,
                dataType: "json",
                data: $("#signupForm").serialize(),
                success: function (msg) {
                    if (msg.result) {
                        $('#formSubmit').attr('disabled', true);
                        art.dialog.tips(msg.data + ' | 正在跳转..', 3);
                        var uri = $("#redirectURI").val();
                        if (uri.trim()) {
                            window.location.href = uri;
                        } else {
                            window.location.href = "/";
                        }
                    } else {
                        art.dialog.tips('注册异常：' + msg.message);
                    }
                }
            });
        } else {
            art.dialog.tips('验证码错误！请重新输入！', 3);
            return;
        }
    });

    function beforeSend(XMLHttpRequest) {
        $("#showResult").append("<div><img src='/static/img/loading.gif' style='width:32px;height:32px;' /><div>");
    }

    function complete(XMLHttpRequest, textStatus) {
        $("#showResult").empty();
    }

    // Google注册
    $('#googleSignup').click(function() {
        var redirectURI = $("#redirectURI").val() || '/';
        window.location.href = '/auth/google?redirectURI=' + encodeURIComponent(redirectURI);
    });

    // GitHub注册
    $('#githubSignup').click(function() {
        var redirectURI = $("#redirectURI").val() || '/';
        window.location.href = '/auth/github?redirectURI=' + encodeURIComponent(redirectURI);
    });

});