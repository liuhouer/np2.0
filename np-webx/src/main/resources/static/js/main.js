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


    // 立即初始化首页顶部大轮播（不依赖 getNote 异步响应）
    if (typeof $.fn.flexslider === 'function' && $('.flexslider').length > 0) {
        $('.flexslider').flexslider({
            prevText: '',
            nextText: ''
        });
    }

    //加载suisui
    $.ajax({
        url: "/dash/getNote",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_note").prepend(data);

                if (typeof $.fn.flexslider === 'function') {
                    $('.testimonails-slider').flexslider({
                        animation: 'slide',
                        slideshowSpeed: 5000,
                        prevText: '',
                        nextText: '',
                        controlNav: false
                    });
                }
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
            }
        }
    });


    function initMovieSlider() {
        var $mainlist = $('.piclist.mainlist');
        var $items = $mainlist.find('li');
        var count = $items.length;
        if (count <= 4) {
            $('.og_prev, .og_next').hide();
            return;
        }

        var itemWidth = 220; // 每个项目的宽度
        var totalWidth = (count + 1) * itemWidth;

        $mainlist.css({
            'width': totalWidth + 'px',
            'left': '0px'
        });

        var currentIndex = 0;
        var maxIndex = Math.max(0, count - 4);

        function updatePosition() {
            var targetLeft = -(currentIndex * itemWidth);
            if ($mainlist[0]) {
                $mainlist[0].style.setProperty('left', targetLeft + 'px', 'important');
            }
        }

        function slide(direction) {
            if (direction === 'next') {
                if (currentIndex >= maxIndex) {
                    currentIndex = 0;
                } else {
                    currentIndex = Math.min(currentIndex + 4, maxIndex);
                }
            } else {
                if (currentIndex <= 0) {
                    currentIndex = maxIndex;
                } else {
                    currentIndex = Math.max(currentIndex - 4, 0);
                }
            }
            updatePosition();
        }

        // 绑定左右点击切换
        $('.og_next').off('click').on('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            slide('next');
        });

        $('.og_prev').off('click').on('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            slide('prev');
        });

        // 自动轮播（每4.5秒自动向右切换一组）
        var autoTimer = null;
        function startAutoSlide() {
            if (!autoTimer) {
                autoTimer = setInterval(function () {
                    slide('next');
                }, 4500);
            }
        }

        function stopAutoSlide() {
            if (autoTimer) {
                clearInterval(autoTimer);
                autoTimer = null;
            }
        }

        startAutoSlide();

        // 鼠标移入轮播卡片时暂停，移出时恢复自动轮播
        $('.moviebox').off('mouseenter mouseleave').hover(stopAutoSlide, startAutoSlide);
    }

    //加载movies
    $.ajax({
        url: "/dash/getMovies",
        type: "get",
        success: function (data) {
            if (data) {
                $("#J_container_movies").prepend(data);
                /* 电影轮播初始化与自动播放 */
                initMovieSlider();
            }
        }
    });
});



