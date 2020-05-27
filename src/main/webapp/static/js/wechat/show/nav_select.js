$(function () {
    var $labItemList = $("a.labItem");
    $labItemList.each(function (i, e) {
        var $element = $(e);
        $element.click(function () {
            shutdownNavigator();
            var labId = $element.attr("id");
            $.post("showPassage", {"id": labId})
                .done(
                    function (data) {
                        var $selected = $("#selected");
                        $selected.html(data);
                        shutdownNavigator();
                        $selected.find(".panel-heading span").text("您想了解的实验室");
                        $(document).scrollTop($selected.offset().top);
                        $('.photoset-grid-lightbox').photosetGrid({
                            width: '100%',
                            gutter: '5px',
                            highresLinks: true,
                            lowresWidth: 200,
                            rel: 'withhearts-gallery',
                            borderActive: true,
                            borderWidth: '3px',
                            borderColor: '#000000',
                            borderRadius: '3px',
                            borderRemoveDouble: false,
                            onComplete: function () {
                                $('.photoset-grid-lightbox')
                                    .attr('style', '')
                                    .css({
                                        'visibility': 'visible'
                                    });
                                $('.photoset-grid-lightbox a').colorbox({
                                    photo: true,
                                    scalePhotos: true,
                                    maxHeight: '100%',
                                    maxWidth: '100%'
                                });
                            }
                        });
                    }
                )
                .fail(
                    function (data) {
                        swal("Oops", "加载时发生了错误", "error");
                    }
                );
        })
    });

    $("#club_sort_list_trigger").click(function () {
        swal({
            title: "Sorry",
            text: "正在施工中，敬请期待！",
            type: "warning",
            showCancelButton: false,
            confirmButtonColor: "#1E90FF",
            confirmButtonText: "好的",
            closeOnConfirm: true
        })
    });

    $("#about_us").click(function () {
        swal({
            title: "关于优米",
            text: "这是一群极不正经的大学生，创极不正经的业，无论是吃瓜吃枣的人民群众，还是心怀梦想的有志少年，我们欢迎你————只要你肯上贼船！",
            type: "info",
            confirmButtonColor: "#65CE6B"
        })
    })
});

function shutdownNavigator() {
    var $divNavbarPart = $("#navbar_part");
    //获取蒙版的div
    var $divMasking = $(".masking");
    //获取按钮对象
    var $buttonExpandBtn = $(".expandBtn");
    //获取按钮里的图标
    var $spanBtnIcon = $("#expandBtn_icon");
    $divNavbarPart.removeClass("toggled");
    $buttonExpandBtn.removeClass("clickedBtn");
    $divMasking.hide();
    //切换按钮样式 并关闭项目列表
    $spanBtnIcon.removeClass("glyphicon glyphicon-remove");
    $spanBtnIcon.addClass("glyphicon glyphicon-align-justify");
    shutdownSort();
    flag = true;
}