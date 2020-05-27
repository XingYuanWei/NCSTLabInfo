<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- left side start-->
<div class="left-side sticky-left-side">

    <div class="logo">
        <a href="index.html"><img src="${contextPath}/static/img/panel/dashboard/logo.png" alt=""></a>
    </div>
    <div class="logo-icon text-center">
        <a href="index.html"><img src="${contextPath}/static/img/panel/dashboard/logo-icon.png" alt=""></a>
    </div>

    <div class="left-side-inner">
        <!-- visible to small devices only -->
        <div class="visible-xs hidden-sm hidden-md hidden-lg">
            <div class="media logged-user">
                <img alt="" src="images/some_user.jpg" class="media-object">
                <div class="media-body">
                    <h4><a href="#">
                        <%-- TODO 用户名字 --%>
                    </a></h4>
                </div>
            </div>

            <h5 class="left-nav-title">账户信息（为了更好的体验，请使用PC浏览器）</h5>
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li><a href="#"><i class="fa fa-user"></i> <span>预置信息</span></a></li>
                <li><a href="#"><i class="fa fa-cog"></i> <span>设置</span></a></li>
                <li><a href="#"><i class="fa fa-sign-out"></i> <span>退出登录</span></a></li>
            </ul>
        </div>

        <!--sidebar nav start-->
        <ul class="nav nav-pills nav-stacked custom-nav">
            <li><a href="index.jsp"><i class="fa fa-home"></i><span> 主页</span></a></li>
            <li class="menu-list <%-- TODO active nav --%> nav-active"><a href=""><i class="fa fa-columns"></i><span> 发布文章</span></a>
                <ul class="sub-menu-list">
                    <li <%-- TODO active 位置 --%> class="active"><a href="editor.jsp"> 编辑器</a></li>
                </ul>
            </li>
            <li><a href="${contextPath}/shiro/logout"><i class="fa fa-sign-in"></i> <span>重新登陆</span></a></li>
        </ul>
        <!--sidebar nav end-->
    </div>
</div>
<!-- left side end-->