<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="_script_head.jsp"%>
    <%@ include file="_lib_fragment/iCheck_styles.jsp"%>
    <title>登录</title>
</head>

<body class="login-body">

<div class="container">
    <form class="form-signin" action="${contextPath}/shiro/login">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">sign in</h1>
            <img src="${contextPath}/static/img/panel/login_and_registration/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <input type="text" name="username" class="form-control" placeholder="您的12位学工号" autofocus>
            <input type="password" name="password" class="form-control" placeholder="密钥">

            <button class="btn btn-lg btn-login btn-block" type="submit">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
                尚未加入我们？
                <a class="" href="registration.jsp">
                    申请注册：我是管理员
                </a>
            </div>
            <label class="checkbox">
                <div class="icheck">
                    <div class="single-row">
                        <div class="polaris">
                            <input type="checkbox" name="rememberMe">
                            <label style="padding: 5px 0 0 0;">记住我</label>
                        </div>
                        <div class="pull-right">
                            <span style="padding-top: 5px; float: right"><a data-toggle="modal" href="#myModal"> 忘记密码？</a></span>
                        </div>
                    </div>
                </div>
            </label>

        </div>

        <!-- Modal -->
        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">找回管理员密钥</h4>
                    </div>
                    <div class="modal-body">
                        <p>很抱歉，这项服务暂时无法使用，您可以直接通过 QQ 和微信与管理员取得联系</p>
                        <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        <button class="btn btn-primary" type="button">Submit</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal -->
    </form>
</div>
<jsp:include page="_footer.jsp"/>
<%@ include file="_script_main.jsp"%>
<%@ include file="_lib_fragment/iCheck_js.jsp"%>
</body>
</html>

