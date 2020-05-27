// 获学号、专业、姓名文本框的jQuery对象
var $studentIdTBox = $("#student_id");
var $majorTBox = $("#major");
var $realNameTBox = $("#real_name");

// 获取到追加验证学号信息的div
var $verifyStudentIdDiv = $("#verify_student_id");
// 获取到追加验证专业信息的div
var $verifyMajorDiv = $("#verify_major");
// 获取到追加验证真实信息的div
var $verifyRealNameDiv = $("#verify_real_name");

var professionsLocal = [];
$(function () {
    $.each(professionSuggestions, function (i, obj) {
        professionsLocal.push({value: obj['value'], data: obj['id']});
    });
});

// 正则表达式初步过滤
var stuNumberReg = /^\d{12}$/;
var majorReg = /^[\u4e00-\u9fa5]+$/;
var englishNameReg = /^[a-zA-Z ]{1,20}$/;
var chineseNameReg = /^[\u4e00-\u9fa5]{1,6}$/;

// 封装验证函数
function verifyValue(element, regExp) {
    var value = element.val();
    //获取除去前后空格的文本值
    var trimValue = value.trim(value);
    return regExp.test(trimValue);
}
function verifyMajor() {
    var content = $majorTBox.val();
    var ret = false;
    $.each(professionsLocal, function (i, profession) {
        if(profession['value'] === content) {
            ret = true;
            return false;
        }
    });
    return ret;
}


function studentIdVerifyFunc() {
    var status = verifyValue($studentIdTBox, stuNumberReg);
    //如果内容符合正则表达式
    if (status) {
        handleVerifyTip($verifyStudentIdDiv, true);
        updateTextBoxVerifyStyle($studentIdTBox, true);
    } else {
        // 数字没有输入完
        if (/^\d{0,12}$/.test($studentIdTBox.val())) {
            $verifyStudentIdDiv.removeClass();
            $verifyStudentIdDiv.html('');
            $verifyStudentIdDiv.addClass('warn');
            $verifyStudentIdDiv.html('<i class="weui-icon-warn"></i>学工号共有12位')
            updateTextBoxVerifyStyle($studentIdTBox, false);
        } else {
            handleVerifyTip($verifyStudentIdDiv, false);
            updateTextBoxVerifyStyle($studentIdTBox, false);
        }
    }
    return status;
}
function majorVerifyFunc() {
    var status = verifyValue($majorTBox, majorReg) && verifyMajor($majorTBox, professionsLocal);
    if (status) {
        handleVerifyTip($verifyMajorDiv, true);
        updateTextBoxVerifyStyle($majorTBox, true);
    } else {
        handleVerifyTip($verifyMajorDiv, false);
        updateTextBoxVerifyStyle($majorTBox, false);
    }
    return status;
}
function realNameVerifyFunc() {
    var status = verifyValue($realNameTBox, englishNameReg) || verifyValue($realNameTBox, chineseNameReg);
    if (status) {
        handleVerifyTip($verifyRealNameDiv, true);
        updateTextBoxVerifyStyle($realNameTBox, true);
    } else {
        handleVerifyTip($verifyRealNameDiv, false);
        updateTextBoxVerifyStyle($realNameTBox, false);
    }
    return status;
}
// 为验证提示的 div 添加相关样式和提示内容
function handleVerifyTip($appendDiv, flag) {
    if (flag) {
        // 给那个需要追加验证信息的 div 删除样式和文字，以免样式重叠，进而样式混乱出错
        $appendDiv.removeClass();
        $appendDiv.html('');
        // 给那个需要追加验证信息的 div 添加验证正确的样式
        $appendDiv.addClass("correct");
        // 给那个需要追加验证信息的 div 添加提示信息
        $appendDiv.html('<i class="weui-icon-success"></i>完成！');
    } else {
        $appendDiv.removeClass();
        $appendDiv.html('');
        $appendDiv.addClass("error");
        $appendDiv.html('<i class="weui-icon-cancel"></i>请检查您的输入格式');
    }
}

// 为 textBoxDiv 的父元素（class="weui-cells"）更新 shadow 样式（通过修改 class）
function updateTextBoxVerifyStyle($textBoxDiv, flag) {
    var $parentCells = $textBoxDiv.closest(".weui-cells");
    // 格式正确
    if (flag) {
        $parentCells.removeClass("shadowError");
        $parentCells.addClass("shadowCorrect");
    } else {
        $parentCells.removeClass("shadowCorrect");
        $parentCells.addClass("shadowError");
    }
}

//返回某个文本框的内容是否为空
function isNull(textBox) {
    var value = textBox.val();
    var trimText = value.trim(value);
    return trimText === "";
}

//正则验证学号、专业、姓名
$(function () {
    // 给每个文本框的改变事件添加数据验证监听
    $studentIdTBox.keyup(studentIdVerifyFunc);
    $majorTBox.keyup(majorVerifyFunc);
    $realNameTBox.keyup(realNameVerifyFunc);

    //给文本框添加失去焦点事件
    $studentIdTBox.blur(function () {
        //如果文本框的内容是空的 那么在失去焦点的时候则去除提示和显示错误样式
        if (isNull($studentIdTBox)) {
            $studentIdTBox.closest(".weui-cells").removeClass("shadowError");
            $verifyStudentIdDiv.empty();
        }
    });
    $majorTBox.blur(function () {
        if (isNull($majorTBox)) {
            $majorTBox.closest(".weui-cells").removeClass("shadowError");
            $verifyMajorDiv.empty();
        }
    });
    $realNameTBox.blur(function () {
        if (isNull($realNameTBox)) {
            $realNameTBox.closest(".weui-cells").removeClass("shadowError");
            $verifyRealNameDiv.empty();
        }
    });

    // 为专业添加自动完成提示
    var $cells_major = $('.cells-major');
    $('#major').devbridgeAutocomplete({
        lookup: professionsLocal,
        showNoSuggestionNotice: true,
        noSuggestionNotice: '请输入完整专业名称',
        orientation: 'auto',
        appendTo: $cells_major,
        width: $cells_major.width(),
        onSelect: majorVerifyFunc
    });
    $(window).resize(function () {
        $('.autocomplete-suggestions').width($cells_major.width());
    })
});