<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<style>
div#loginDiv {
	width: 1080px;
	margin: 0 auto;
	position: relative;
}

div.simpleLogo {
	margin: 10px 0;
}

img.loginBackgroundImg {
	display: block;
	width: 1080px;
	margin: 0px auto;
}

div.loginSmallDiv1 {
	background-color: white;
	position: absolute;
	left: 600px;
	top: 50px;
	width: 350px;
	height: 300px;
	padding: 60px 25px 80px 25px;
}

div.login_acount_text {
	color: #3C3C3C;
	font-size: 16px;
	font-weight: bold;
}

div.loginInput {
	border: 1px solid #CBCBCB;
	margin: 20px 0px;
	position: relative;
}

div.loginInput span.loginInputIcon {
	background-color: #CBCBCB;
	display: inline-block;
	font-size: 25px;
	width: 40px;
	height: 40px;
	width: 40px;
}

span.loginInputIcon span.icon {
	position: relative;
	left: 9px;
	top: 9px;
	color: #606060;
}

div.loginSmallDiv1 div.loginInput input {
	margin: 0px;
	height: 35px;
	border: none;
	outline: none;
	padding: 0px;
}

body {
	font-size: 12px;
	font-family: Arial;
}

a {
	color: #999;
}

a:hover {
	text-decoration: none;
	color: #C40000;
}

div#loginSmallDiv1 button.redButton {
	color: white;
	width: 300px;
	background-color: #C40000;
	border: 1px solid transparent;
	font-size: 18px;
	font-weight: bold;
}
</style>
<script>
$(function(){
     
    <c:if test="${!empty msg}">
    $("span.errorMessage").html("${msg}");
    $("div.loginErrorMessageDiv").show();       
    </c:if>
     
    $("form.loginForm").submit(function(){
    	var n=$("#name").val().length;
    	var p=$("#password").val().length;
    	console.log(n+'---'+p);
        if(0==n||0==p){
            $("span.errorMessage").html("请输入账号密码");
            $("div.loginErrorMessageDiv").show();           
            return false;
        }
        return true;
    });
     
    $("form.loginForm input").keyup(function(){
        $("div.loginErrorMessageDiv").hide();   
    });
     
    var left = window.innerWidth/2+162;
    $("div.loginSmallDiv").css("left",left);
})
</script>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<div id="loginDiv">
		<div class="simpleLogo">
			<img src="http://how2j.cn/tmall/img/site/simpleLogo.png">
		</div>
		<img src="http://how2j.cn/tmall/img/site/loginBackground.png" class="loginBackgroundImg" id="loginBackgroundImg">
		<div class="loginSmallDiv1" id="loginSmallDiv1" style="left: 650px !important;">
			<div class="loginErrorMessageDiv" style='display: none;'>
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
					<span class="errorMessage"></span>
				</div>
			</div>
			<form action="front_login" class='loginForm' method='POST'>
				<div class="login_acount_text">账户登录</div>
				<div class="loginInput ">
					<span class="loginInputIcon ">
						<span class='icon icon-user'></span>
					</span>
					<input type="text" placeholder="手机/会员名/邮箱" name="name" id="name">
				</div>
				<div class="loginInput ">
					<span class="loginInputIcon ">
						<span class='icon icon-lock'></span>
					</span>
					<input type="password" placeholder="密码" name="password" id="password">
				</div>
				<div>
					<a href="#nowhere" class="notImplementLink">忘记登录密码</a>
					<a class="pull-right" href="normal/regist.jsp">免费注册</a>
				</div>
				<div style="margin-top: 20px; text-align: center;">
					<button type="submit" class=" redButton">登录</button>
				</div>
			</form>
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>