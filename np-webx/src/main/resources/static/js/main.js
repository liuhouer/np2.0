$(function () {
    //加载love;

    $.ajax({
        url: "/dash/getLove",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_love").prepend(data);

                //绑定动态事件
                $("div.blog-post").hover(
                    function () {
                        $(this).find("div.content-hide").slideToggle("fast");
                    },
                    function () {
                        $(this).find("div.content-hide").slideToggle("fast");
                    }
                );
            }
        }
    });


    //加载suisui
    $.ajax({
        url: "/dash/getNote",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_note").prepend(data);

                //激活动作
                $('.flexslider').flexslider({
                    prevText: '',
                    nextText: ''
                });

                $('.testimonails-slider').flexslider({
                    animation: 'slide',
                    slideshowSpeed: 5000,
                    prevText: '',
                    nextText: '',
                    controlNav: false
                });
            }
        }
    });

    //加载romeo
    $.ajax({
        url: "/dash/getRomeo",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_romeo").prepend(data);
                
                // 添加确保等高的处理
                function adjustRomeoHeight() {
                    var maxHeight = 0;
                    $('.romeo-item').each(function() {
                        var height = $(this).height();
                        maxHeight = Math.max(maxHeight, height);
                    });
                    $('.romeo-item').height(maxHeight);
                }
                
                // 页面加载完成后执行一次
                adjustRomeoHeight();
                
                // 窗口大小改变时重新计算
                $(window).resize(function() {
                    adjustRomeoHeight();
                });
            }
        }
    });


    function initMovieSlider() {
        var itemWidth = 200; // 每个电影项的宽度
        var visibleItems = 4; // 可见的电影数量
        var linum = $('.piclist.mainlist li').length;
        var totalWidth = linum * itemWidth;
        
        // 设置列表总宽度
        $('.piclist').css('width', totalWidth + 'px');
        
        // 初始化位置
        $('.mainlist').css({
            'left': '0px'
        });
        
        // 复制内容到交换列表并设置位置
        $('.swaplist').html($('.mainlist').html()).css({
            'left': totalWidth + 'px'
        });

        // 计算最大滚动距离
        var maxScroll = -(totalWidth - visibleItems * itemWidth);
        
        // 绑定点击事件
        $('.og_next').off('click').on('click', function() {
            if ($('.mainlist').is(':animated')) return;
            
            var currentLeft = parseInt($('.mainlist').css('left'));
            var newLeft = currentLeft - (itemWidth * visibleItems);
            
            if (newLeft < maxScroll) {
                newLeft = 0;
            }
            
            $('.mainlist').animate({left: newLeft + 'px'}, 300);
        });
        
        $('.og_prev').off('click').on('click', function() {
            if ($('.mainlist').is(':animated')) return;
            
            var currentLeft = parseInt($('.mainlist').css('left'));
            var newLeft = currentLeft + (itemWidth * visibleItems);
            
            if (newLeft > 0) {
                newLeft = maxScroll;
            }
            
            $('.mainlist').animate({left: newLeft + 'px'}, 300);
        });
    }

    //加载movies
    $.ajax({
        url: "/dash/getMovies",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_movies").prepend(data);
                /*电影轮播*/
                initMovieSlider();

                /***不需要自动滚动，去掉即可***/
                // time = window.setInterval(function () {
                //     //$('.og_next').click();
                // }, 5000);

                // 替换为初始化位置
                $('.mainlist').css('left', '0px');
                $('.swaplist').css('left', '1000px');

                /***不需要自动滚动，去掉即可***/
                linum = $('.piclist.mainlist li').length; //图片数量
                w = linum * 250;//ul宽度
                $('.piclist').css('width', w + 'px');//ul宽度
                $('.swaplist').html($('.mainlist').html());//复制内容

                $('.og_next').click(function () {
                    if ($('.swaplist,.mainlist').is(':animated')) {
                        return;
                    }

                    if ($('.mainlist li').length > 4) { // 修改判断条件
                        var ml = parseInt($('.mainlist').css('left'));
                        var sl = parseInt($('.swaplist').css('left'));
                        var itemWidth = 220;
                        var w = $('.mainlist li').length * itemWidth;
                        var scrollWidth = 880; // 一次滚动4个项目的宽度

                        if (ml <= 0 && ml > w * -1) {
                            $('.swaplist').css({left: '1000px'});
                            $('.mainlist').animate({left: ml - scrollWidth + 'px'}, 300);
                            
                            if (ml == (w - scrollWidth) * -1) {
                                $('.swaplist').animate({left: '0px'}, 300);
                            }
                        } else {
                            $('.mainlist').css({left: '1000px'});
                            $('.swaplist').animate({left: sl - scrollWidth + 'px'}, 300);
                            
                            if (sl == (w - scrollWidth) * -1) {
                                $('.mainlist').animate({left: '0px'}, 300);
                            }
                        }
                    }
                })
                $('.og_prev').click(function () {

                    if ($('.swaplist,.mainlist').is(':animated')) {
                        $('.swaplist,.mainlist').stop(true, true);
                    }

                    if ($('.mainlist li').length > 4) {
                        ml = parseInt($('.mainlist').css('left'));
                        sl = parseInt($('.swaplist').css('left'));
                        if (ml <= 0 && ml > w * -1) {
                            $('.swaplist').css({left: w * -1 + 'px'});
                            $('.mainlist').animate({left: ml + 1000 + 'px'}, 'slow');
                            if (ml == 0) {
                                $('.swaplist').animate({left: (w - 1000) * -1 + 'px'}, 'slow');
                            }
                        } else {
                            $('.mainlist').css({left: (w - 1000) * -1 + 'px'});
                            $('.swaplist').animate({left: sl + 1000 + 'px'}, 'slow');
                            if (sl == 0) {
                                $('.mainlist').animate({left: '0px'}, 'slow');
                            }
                        }
                    }
                })


                $('.og_prev,.og_next').hover(function () {
                    $(this).fadeTo('fast', 1);
                }, function () {
                    $(this).fadeTo('fast', 0.7);
                })


                $(".og_next").click();

                /*电影轮播end*/
            }
        }
    });
});



