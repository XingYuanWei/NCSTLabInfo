$(document).ready(function () {
    //导航条显示隐藏动画
    switchLabAndClubSortList();
    switchList();
    switchNavbar();
    shutDownAll();
});

//封装打开其中各个分类列表功能
function switchList() {
    //获取科联和社团菜单下的各个种类标题对象
    var $pmenuHead = $(".dropdown-menu-head");
    $pmenuHead.click(function () {
        //保证每次只能有一个列表被下拉
        $(this).toggleClass("currentList").next("div.dropdown-menu-list").slideToggle(300).siblings("div.dropdown-menu-list").slideUp("slow");
        $(this).siblings().removeClass("currentList");
    });
}

//展开实验室和社团分类功能
function switchLabAndClubSortList() {
    //获取科联菜单的对象
    var $liLabSortListTrigger = $("#lab_sort_list_trigger");
    //获取社团菜单对象
    var $liClubListTrigger = $("#club_sort_list_trigger");
    $liLabSortListTrigger.click(function () {
        openSortLab();
        shutdownSort();
    });
    $liClubListTrigger.click(function () {
        openSortClub();
        shutdownSort();
    });
}

//封装打开实验室分类列表功能
function openSortLab() {
    //获取要显现实验室分类的div
    var $divLabSortList = $("#lab_sort_list");
    //获取要关闭社团分类的div
    var $divClubSortList = $("#club_sort_list");
    $divLabSortList.slideToggle(300);
    $divClubSortList.slideUp("slow");
}

//封装社团打开分类列表功能
function openSortClub() {
    //获取要关闭实验室分类的div
    var $divLabSortList = $("#lab_sort_list");
    //获取要显示社团分类的div
    var $divClubSortList = $("#club_sort_list");
    $divClubSortList.slideToggle(300);
    $divLabSortList.slideUp("slow");
}

//封装关闭实验室和社团分类列表功能
function shutdownSort() {
    //获取显示具体各个列表的div
    var $divMenuList = $("div.dropdown-menu-list");
    //获取科联和社团菜单下的各个种类标题 是一个p标签
    var $pmenuHead = $(".dropdown-menu-head");
    //如果关闭展开分类列表 那么里面所有的显示面板都也要关闭  并且去除样式
    $divMenuList.slideUp("slow");
    $pmenuHead.removeClass("currentList");
}

//显现、隐藏导航条 并改变按钮样式
//这个flag是用于记录什么时候要用蒙版 什么时候没用
var flag = true;

function switchNavbar() {
    var $buttonExpandBtn = $(".expandBtn");
    $buttonExpandBtn.click(function () {
        if (flag) {
            openNavbar();
        } else {
            shutdownNavbar();
        }
    });
}

//封装打开导航条功能
function openNavbar() {
    var $divNavbarPart = $("#navbar_part");
    //获取蒙版的div
    var $divMasking = $(".masking");
    //获取按钮对象
    var $buttonExpandBtn = $(".expandBtn");
    //获取按钮里的图标
    var $spanBtnIcon = $("#expandBtn_icon");
    //显示隐藏的导航
    $divNavbarPart.toggleClass("toggled");
    //改变按钮样式
    $buttonExpandBtn.toggleClass("clickedBtn");
    //蒙版
    $divMasking.show();
    //切换按钮的图标
    $spanBtnIcon.removeClass("glyphicon glyphicon-align-justify");
    $spanBtnIcon.addClass("glyphicon glyphicon-remove");
    flag = false;
}

//封装关闭导航条功能 同时也要关闭里面有展开的分类项目列表
function shutdownNavbar() {
    var $divNavbarPart = $("#navbar_part");
    //获取蒙版的div
    var $divMasking = $(".masking");
    //获取按钮对象
    var $buttonExpandBtn = $(".expandBtn");
    //获取按钮里的图标
    var $spanBtnIcon = $("#expandBtn_icon");
    $divNavbarPart.toggleClass("toggled");
    $buttonExpandBtn.toggleClass("clickedBtn");
    $divMasking.hide();
    //切换按钮样式 并关闭项目列表
    $spanBtnIcon.removeClass("glyphicon glyphicon-remove");
    $spanBtnIcon.addClass("glyphicon glyphicon-align-justify");
    shutdownSort();
    flag = true;
}

//当焦点到了body上的任何元素 即导航条失去焦点时候 关闭导航条所有
function shutDownAll() {
    var $divMasking = $(".masking");
    $divMasking.click(function () {
        shutdownNavbar();
        shutdownSort()
    })
}