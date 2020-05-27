<%--
  科联实验室展示页面
  全局 ContextPath 变量 contextPath
  requestScope 属性：
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${contextPath}/lib/bootstrap/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${contextPath}/static/css/wechat/show/style.css" type="text/css">
    <link rel="stylesheet" href="${contextPath}/lib/photoset-grid/css/colorbox.css" type="text/css">
    <link rel="stylesheet" href="${contextPath}/static/css/wechat/show/style.css" type="text/css">
    <link rel="stylesheet" href="${contextPath}/static/css/wechat/show/navigator.css" type="text/css">
    <link rel="stylesheet" href="${contextPath}/lib/sweetalert/sweetalert.css" type="text/css">

    <script type="text/javascript" src="${contextPath}/lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/photoset-grid/js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/photoset-grid/jquery.photoset-grid.min.js"></script>
    <script type="text/javascript" src="${contextPath}/static/js/wechat/show/imageGrid.js"></script>

    <title>优科优社</title>
</head>

<body>
<div>
    <%-- 导航栏 --%>
    <%@ include file="_show_navigator.jsp"%>
    <!--主内容部分-->
    <div class="mainContent" id="main_content">
        <button type="button" class="expandBtn" id="expand_Btn">
            <span class="glyphicon glyphicon-align-justify" id="expandBtn_icon"></span>
        </button>
        <div class="head" id="welcome">
            <img src="${contextPath}/static/img/wechat/show/lab_club_head.png" alt="优科优社">
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-12" id="overview">
                    <%@ include file="passages/overview.jsp"%>
                </div>
                <%--<div class="col-lg-6" id="recommended">
                    <%@ include file="passages/21.jsp"%>
                </div>--%>
            </div>
            <div class="row">
                <div class="col-lg-12" id="selected">
                </div>
            </div>
        </div>
    </div>
    <footer>
    </footer>
    <div class="copyright">
        <p><a href="http://www.umi361.com" target="_blank">优米科技</a> - 2017 华北理工大学</p>
    </div>
</div>


<script type="text/javascript" src="${contextPath}/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/wechat/show/navigator.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/wechat/show/nav_select.js"></script>
<script type="text/javascript" src="${contextPath}/lib/sweetalert/sweetalert.min.js"></script>

</body>
</html>
