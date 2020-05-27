$(function () {
    // jquery-weui select 组件的数据注册
    var specialitiesLocal = [];
    $.each(specialitySuggestions, function (i, obj) {
        specialitiesLocal.push({title: obj['value'], value: obj['id']});
    });
    $("#speciality").select({
        title: "学科特长",
        multi: true,
        max: 3,
        split: " ",
        items: specialitiesLocal
    });
    var interestsLocal = [];
    $.each(interestSuggestions, function (i, obj) {
        interestsLocal.push({title: obj['value'], value: obj['id']});
    });
    $("#interest").select({
        title: "兴趣爱好",
        multi: true,
        max: 3,
        split: " ",
        items: interestsLocal
    });

    // sweetalert 提交组件
    $("#skip").click(function () {
        swal({
                title: "确认跳过吗",
                text: "继续完善信息能帮助我们为您提供更好的服务，点击取消可以返回继续填写",
                type: "warning",
                showCancelButton: true,
                cancelButtonText: "取消",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认跳过",
                closeOnConfirm: false
            },
            function () {
                swal({
                    title: "完成",
                    text: "已经跳过该环节，现在进入主页面",
                    type: "success",
                    confirmButtonColor: "#3cc51f"
                }, function () {
                    window.location.href = contextPath + "/wechat/show.jsp?bypass=true"
                });
            });
    });
    $("#submit").click(function () {
        var basicVerify = studentIdVerifyFunc() && majorVerifyFunc() && realNameVerifyFunc();
        if (!basicVerify) {
            swal({
                title: "请正确填写各项内容",
                text: "您有部分基本信息尚未填写，或者仍然存在格式错误",
                type: "warning",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "好的"
            })
        } else {
            swal({
                title: "确认您的信息无误",
                text: "请确保您已经正确填写了各项信息，然后按确定提交",
                type: "info",
                showCancelButton: true,
                cancelButtonText: "返回",
                confirmButtonText: "确定",
                closeOnConfirm: false,
                showLoaderOnConfirm: true
            }, function () {
                // $("#form1").submit();
                $.post(contextPath + "/wechat/register.jsp", {
                    studentId: $("#student_id").val(),
                    majorId: getMajorIdByName($("#major").val()),
                    realName: $("#real_name").val(),
                    sex: $("input[name='sex'][checked]").val(),
                    speciality: $("#speciality").attr("data-values"),
                    interest: $("#interest").attr("data-values")
                })
                    .done(function (data) {
                        swal({
                            title: "完成",
                            text: "马上进入主页面！",
                            type: "success",
                            confirmButtonColor: "#3cc51f"
                        }, function () {
                            // bypass 的值表示是否是通过跳过注册进入主页面的
                            window.location.href = contextPath + "/wechat/show.jsp?bypass=false"
                        });
                    })
                    .fail(function (data) {
                        swal("很抱歉(；д；)", "似乎暂时无法连接服务器", "error")
                    })
            });
        }
    });
});

function getMajorIdByName(name) {
    var ret = -1;
    $.each(professionSuggestions, function (i, obj) {
        if (obj['value'] === name) {
            ret = obj['id'];
            return;
        }
    });
    return ret;
}