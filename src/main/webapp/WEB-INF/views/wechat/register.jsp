<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title>信息录入</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/lib/sweetalert/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/lib/jquery-weui/lib/weui.min.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/lib/jquery-weui/css/jquery-weui.min.css">
    <%-- iOS 可能需要用到的 toast 提示信息 bug 修复代码 --%>
    <%--<style type="text/css">--%>
    <%--.weui-dialog,--%>
    <%--.weui-toast {--%>
    <%---webkit-transition-duration: .2s;--%>
    <%--transition-duration: .2s;--%>
    <%--opacity: 0;--%>
    <%---webkit-transform: scale(1) translate(-50%, -50%);--%>
    <%--transform: scale(1) translate(-50%, -50%);--%>
    <%---webkit-transform-origin: 0 0;--%>
    <%--transform-origin: 0 0;--%>
    <%--visibility: hidden;--%>
    <%--margin: 0;--%>
    <%--top: 40%;--%>
    <%--z-index: 2000;--%>
    <%--}--%>
    <%--</style>--%>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/wechat/register/style.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/wechat/register/autocomplete.css"/>

    <script src="${contextPath}/lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/sweetalert/sweetalert.min.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/autocomplete/jquery.autocomplete.min.js"></script>
    <%-- iOS fastclick 支持 --%>
    <script type="text/javascript" src="${contextPath}/lib/jquery-weui/lib/fastclick.js">
    </script>
    <script type="text/javascript" src="${contextPath}/lib/jquery-weui/js/jquery-weui.min.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/jquery-weui/js/city-picker.min.js"></script>
    <script type="text/javascript" src="${contextPath}/lib/jquery-weui/js/swiper.min.js"></script>
    <script type="text/javascript">
        $(function() {
            FastClick.attach(document.body);
        });
        var contextPath = "${contextPath}";
    </script>
</head>

<body>

<header>
    <h1 class="titleOne">信息录入</h1>
</header>
<div>
    <%--
        表单信息：
        studentId
        majorId
        realName
        sex
        speciality
        interest
    --%>
    <form id="form1" action="${contextPath}/wechat/register.jsp" method="post">
        <div id="wrapper">
            <div class="moduleDistance"></div>
            <label class="titleTwo">
                基本信息
            </label>
            <div class="tip"><i class="weui-icon-info-circle"></i>
                这些信息将帮助我们为您提供来自实验室和社团的报名、咨询等服务
            </div>
            <div class="weui-cells">
                <div class="weui-cells__title">学号</div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input name="studentId" class="weui-input" type="number" placeholder="您的12位学工号" id="student_id">
                    </div>
                </div>
            </div>
            <div id="verify_student_id"></div>
            <div class="cells-major" style="position: relative">
                <div class="weui-cells">
                    <div class="weui-cells__title">专业</div>
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <input class="weui-input" type="text" placeholder="您的专业"  name="major" id="major">
                        </div>
                    </div>
                </div>
            </div>
            <div id="verify_major"></div>
            <div class="weui-cells">
                <div class="weui-cells__title">姓名</div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="text" placeholder="您的真实姓名" name="realName"  id="real_name">
                    </div>
                </div>
            </div>
            <div id="verify_real_name"></div>
            <div class="weui-cells">
                <div class="weui-cells__title">性别</div>
                <div class="weui-cells_radio">
                    <label class="weui-cell weui-check__label" for="male">
                        <div class="weui-cell__bd">
                            <p>男</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" class="weui-check" name="sex" value="1" id="male" ${userInfoWechat.sex == 1 ? "checked" : ""}>
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="female">
                        <div class="weui-cell__bd">
                            <p>女</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" class="weui-check" name="sex" value="2" id="female" ${userInfoWechat.sex == 2 ? "checked" : ""}>
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="secret">
                        <div class="weui-cell__bd">
                            <p>保密</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" class="weui-check" name="sex" value="0" id="secret" ${userInfoWechat.sex == 0 ? "checked" : ""}>
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                </div>
            </div>
            <div class="moduleDistance"></div>
            <div class="moduleDistance"></div>
            <label class="titleTwo">
                个性专长
            </label>
            <div class="tip"><i class="weui-icon-info-circle"></i>
                告诉我们你的专长和爱好，让你的才华大施拳脚
            </div>
            <div class="moduleDistance"></div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">学科特长</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" name="speciality" id="speciality">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">兴趣爱好</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" name="interest" id="interest">
                </div>
            </div>

            <div class="weui-btn-area">
                <a class="weui-btn weui-btn_warn" id="skip">跳过</a>
            </div>
            <div class="weui-btn-area"  style="margin-bottom: 20px">
                <a class="weui-btn weui-btn_primary" id="submit">提交</a>
            </div>
            <div class="weui-footer">
                <p class="weui-footer__text">优米科技 - 2017 华北理工大学</p>
            </div>
        </div>
    </form>
</div>
<%-- 数据节点展开 --%>
<%--
    colleges 数据节点展开
    包裹在 <div id = "colleges_data" class = "data_nodes"> 中
    <div id = "colleges_data" class = "data_nodes">
        <data id = "1" value = "材料科学与工程学院">
            <data id = "1" value = "材料化学"></data>
            <data id = "2" value = "高分子材料与工程"></data>
            ...
        </data>
        <data ...>
            ...
        </data>
        ...
    </div>
--%>
<div id = "colleges_data" class = "data_nodes">
    <c:forEach items="${colleges}" var="currCollege">
        <data id="${currCollege.key.key}" value="${currCollege.key.value}">
            <c:forEach items="${currCollege.value}" var="currProfession">
                <data id="${currProfession.key}" value="${currProfession.value}"></data>
            </c:forEach>
        </data>
    </c:forEach>
</div>
<%--
    specialities 数据节点展开
    包裹在 <div id = "specialities_data" class = "data_nodes"> 中
    <div id = "specialities_data" class = "data_nodes">
        <data id = "1" value = "计算机编程"></data>
        <data id = "2" value = "数学与应用"></data>
        ...
    </div>
--%>
<div id = "specialities_data" class = "data_nodes">
    <c:forEach items="${specialities}" var="currCollege">
        <data id="${currCollege.key}" value="${currCollege.value}"></data>
    </c:forEach>
</div>
<%--
    domains 数据节点展开
    包裹在 <div id = "domains_data" class = "data_nodes"> 中
    <div id = "domains_data" class = "data_nodes">
        <data id = "1" value = "音乐">
            <data id = "1" value = "铜管"></data>
            <data id = "2" value = "器乐"></data>
            ...
        </data>
        <data id = "2" value = "绘画">
            <data id = "5" value = "国画"></data>
            <data id = "6" value = "写实"></data>
            <data id = "7" value = "现代"></data>
            ...
        </data>
        ...
    </div>
--%>
<div id = "domains_data" class = "data_nodes">
    <c:forEach items="${domains}" var="currCollege">
        <data id="${currCollege.key.key}" value="${currCollege.key.value}">
            <c:forEach items="${currCollege.value}" var="currProfession">
                <data id="${currProfession.key}" value="${currProfession.value}"></data>
            </c:forEach>
        </data>
    </c:forEach>
</div>
<script type="text/javascript" src="${contextPath}/static/js/wechat/register/dataUnwrap.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/wechat/register/main.js"></script>
<script type="text/javascript" src="${contextPath}/static/js/wechat/register/select_submit.js"></script>
</body>
</html>
