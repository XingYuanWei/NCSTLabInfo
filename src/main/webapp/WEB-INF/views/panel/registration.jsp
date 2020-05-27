<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="_script_head.jsp"%>
    <title>注册</title>
</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" action="index.html">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">Registration</h1>
            <img src="${contextPath}/static/img/panel/login_and_registration/login-logo.png" alt=""/>
        </div>


        <div class="login-wrap">
            <p>Enter your personal details below</p>
            <input type="text" autofocus placeholder="Full Name" class="form-control">
            <input type="text" autofocus placeholder="Address" class="form-control">
            <input type="text" autofocus placeholder="Email" class="form-control">
            <input type="text" autofocus placeholder="City/Town" class="form-control">
            <div class="radios">
                <label for="radio-01" class="label_radio col-lg-6 col-sm-6">
                    <input type="radio" checked="" value="1" id="radio-01" name="sample-radio"> Male
                </label>
                <label for="radio-02" class="label_radio col-lg-6 col-sm-6">
                    <input type="radio" value="1" id="radio-02" name="sample-radio"> Female
                </label>
            </div>

            <p> Enter your account details below</p>
            <input type="text" autofocus placeholder="User Name" class="form-control">
            <input type="password" placeholder="Password" class="form-control">
            <input type="password" placeholder="Re-type Password" class="form-control">
            <button type="submit" class="btn btn-lg btn-login btn-block text-center">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
                已经注册过？
                <a href="login.jsp" class="">
                    登录
                </a>
            </div>
        </div>
    </form>
</div>
<%@ include file="_footer.jsp"%>
<%@ include file="_script_main.jsp"%>
</body>
</html>
