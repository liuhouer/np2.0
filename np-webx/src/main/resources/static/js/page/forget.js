$(function () {
    var currentEmail = '';
    var countdown = 0;
    var countdownTimer = null;

    // 邮箱输入验证
    $("#J_email").change(function () {
        var em = $("#J_email").val();
        if (em && isValidEmail(em)) {
            $.ajax({
                url: "/cm/emailFlag",
                type: "post",
                dataType: "json",
                data: { "email": em },
                success: function (msg) {
                    if (msg.result) {
                        if (msg.data == "exist") {
                            $("#J_tip").text("邮箱验证通过").css("color", "green");
                            $('#sendCodeBtn').removeAttr('disabled').val($('#sendCodeBtn').data('activetext'));
                        } else {
                            $("#J_tip").text("邮箱账号未注册").css("color", "red");
                            $('#sendCodeBtn').attr('disabled', true);
                        }
                    } else {
                        $("#J_tip").text("请求异常").css("color", "red");
                    }
                }
            });
        } else if (em) {
            $("#J_tip").text("请输入有效的邮箱地址").css("color", "red");
            $('#sendCodeBtn').attr('disabled', true);
        } else {
            $("#J_tip").text("");
            $('#sendCodeBtn').attr('disabled', true);
        }
    });

    // 发送验证码
    $("#sendCodeBtn").click(function () {
        var em = $("#J_email").val();
        if (!em || !isValidEmail(em)) {
            $("#J_tip").text("请输入有效的邮箱地址").css("color", "red");
            return;
        }

        $(this).attr('disabled', true).val('发送中...');

        $.ajax({
            url: "/cm/sendForgetCode",
            type: "post",
            dataType: "json",
            data: { "email": em },
            success: function (msg) {
                if (msg.result) {
                    if (msg.data == "ok") {
                        currentEmail = em;
                        $("#J_tip").text("验证码已发送到您的邮箱，请查收").css("color", "green");
                        $("#step1").hide();
                        $("#step2").show();
                        startCountdown();
                    } else {
                        $("#J_tip").text("发送失败：" + (msg.message || "请检查邮箱地址")).css("color", "red");
                        $('#sendCodeBtn').removeAttr('disabled').val('发送验证码');
                    }
                } else {
                    $("#J_tip").text("发送失败：" + (msg.message || "请求异常")).css("color", "red");
                    $('#sendCodeBtn').removeAttr('disabled').val('发送验证码');
                }
            },
            error: function () {
                $("#J_tip").text("网络错误，请重试").css("color", "red");
                $('#sendCodeBtn').removeAttr('disabled').val('发送验证码');
            }
        });
    });

    // 验证验证码
    $("#verifyCodeBtn").click(function () {
        var code = $("#J_code").val();
        if (!code || code.length !== 6) {
            $("#J_tip").text("请输入6位验证码").css("color", "red");
            return;
        }

        $(this).attr('disabled', true).val('验证中...');

        $.ajax({
            url: "/cm/verifyForgetCode",
            type: "post",
            dataType: "json",
            data: { "email": currentEmail, "code": code },
            success: function (msg) {
                if (msg.result) {
                    if (msg.data == "ok") {
                        $("#J_tip").text("验证成功，请设置新密码").css("color", "green");
                        $("#step2").hide();
                        $("#step3").show();
                        clearCountdown();
                    } else {
                        $("#J_tip").text("验证失败：" + (msg.message || "验证码错误或已过期")).css("color", "red");
                        $('#verifyCodeBtn').removeAttr('disabled').val('验证验证码');
                    }
                } else {
                    $("#J_tip").text("验证失败：" + (msg.message || "请求异常")).css("color", "red");
                    $('#verifyCodeBtn').removeAttr('disabled').val('验证验证码');
                }
            },
            error: function () {
                $("#J_tip").text("网络错误，请重试").css("color", "red");
                $('#verifyCodeBtn').removeAttr('disabled').val('验证验证码');
            }
        });
    });

    // 重新发送验证码
    $("#resendCodeBtn").click(function () {
        if (countdown > 0) {
            return;
        }

        $(this).attr('disabled', true).val('发送中...');

        $.ajax({
            url: "/cm/sendForgetCode",
            type: "post",
            dataType: "json",
            data: { "email": currentEmail },
            success: function (msg) {
                if (msg.result && msg.data == "ok") {
                    $("#J_tip").text("验证码已重新发送").css("color", "green");
                    startCountdown();
                } else {
                    $("#J_tip").text("重新发送失败").css("color", "red");
                }
                $('#resendCodeBtn').removeAttr('disabled').val('重新发送验证码');
            },
            error: function () {
                $("#J_tip").text("网络错误，请重试").css("color", "red");
                $('#resendCodeBtn').removeAttr('disabled').val('重新发送验证码');
            }
        });
    });

    // 重置密码
    $("#resetPasswordBtn").click(function () {
        var newPassword = $("#J_newPassword").val();
        var confirmPassword = $("#J_confirmPassword").val();

        if (!newPassword || newPassword.length < 6) {
            $("#J_tip").text("密码长度至少6位").css("color", "red");
            return;
        }

        if (newPassword !== confirmPassword) {
            $("#J_tip").text("两次输入的密码不一致").css("color", "red");
            return;
        }

        $(this).attr('disabled', true).val('重置中...');

        $.ajax({
            url: "/cm/resetPassword",
            type: "post",
            dataType: "json",
            data: {
                "email": currentEmail,
                "newPassword": newPassword
            },
            success: function (msg) {
                if (msg.result) {
                    if (msg.data == "ok") {
                        $("#J_tip").text("密码重置成功，正在跳转到登录页面...").css("color", "green");
                        setTimeout(function () {
                            window.location.href = "/login";
                        }, 2000);
                    } else {
                        $("#J_tip").text("重置失败：" + (msg.message || "请重试")).css("color", "red");
                        $('#resetPasswordBtn').removeAttr('disabled').val('重置密码');
                    }
                } else {
                    $("#J_tip").text("重置失败：" + (msg.message || "请求异常")).css("color", "red");
                    $('#resetPasswordBtn').removeAttr('disabled').val('重置密码');
                }
            },
            error: function () {
                $("#J_tip").text("网络错误，请重试").css("color", "red");
                $('#resetPasswordBtn').removeAttr('disabled').val('重置密码');
            }
        });
    });

    // 倒计时功能
    function startCountdown() {
        countdown = 60;
        updateCountdownDisplay();
        countdownTimer = setInterval(function () {
            countdown--;
            if (countdown <= 0) {
                clearCountdown();
            } else {
                updateCountdownDisplay();
            }
        }, 1000);
    }

    function clearCountdown() {
        if (countdownTimer) {
            clearInterval(countdownTimer);
            countdownTimer = null;
        }
        countdown = 0;
        $("#resendCodeBtn").removeAttr('disabled').val('重新发送验证码');
    }

    function updateCountdownDisplay() {
        $("#resendCodeBtn").attr('disabled', true).val('重新发送(' + countdown + 's)');
    }

    // 邮箱格式验证
    function isValidEmail(email) {
        var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return regex.test(email);
    }

    // 处理页面消息
    var msg = $("#J_msg").val();
    if (msg == 'success') {
        $("#J_tip").text("密码重置成功，正在跳转...").css("color", "green");
        setTimeout(function () {
            window.location.href = "/login";
        }, 2000);
    } else if (msg == 'is_old') {
        $("#J_tip").text("验证码已过期或失效，请重新获取").css("color", "red");
    } else if (msg == 'exception') {
        $("#J_tip").text("系统异常，请稍后重试").css("color", "red");
    }
})