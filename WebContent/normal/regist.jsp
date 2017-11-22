<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<style>
div.registerDiv {
	width: 1080px;
	margin: 50px auto;
	padding-top: 50px;
	text-align: center;
}

table.registerTable {
	color: #3C3C3C;
	font-size: 16px;
	table-layout: fixed;
	margin-top: 50px;
}

table.registerTable td {
	padding: 10px 30px;
}

table.registerTable input {
	border: 1px solid #DEDEDE;
	width: 213px;
	height: 36px;
	font-size: 14px;
}

table.registerTable button {
	width: 170px;
	height: 36px;
	border-radius: 2px;
	color: white;
	background-color: #C40000;
	border-width: 0px;
}

td.registerTip {
	font-weight: bold;
}

td.registerTableLeftTD {
	width: 300px;
	text-align: right;
}

td.registerTableRightTD {
	width: 300px;
	text-align: left;
}

td.registerButtonTD {
	text-align: center;
}
</style>
<script type="text/javascript">
$(function(){
    
    <c:if test="${!empty msg}">
    $("span.errorMessage").html("${msg}");
    $("div.registerErrorMessageDiv").css("visibility","visible");       
    </c:if>
     
    $(".registerForm").submit(function(){
        if(0==$("#name").val().length){
            $("span.errorMessage").html("请输入用户名");
            $("div.registerErrorMessageDiv").css("visibility","visible"); 
            $(".registerErrorMessageDiv").show();
            return false;
        }       
        if(0==$("#password").val().length){
            $("span.errorMessage").html("请输入密码");
            $("div.registerErrorMessageDiv").css("visibility","visible");  
            $(".registerErrorMessageDiv").show();
            return false;
        }       
        if(0==$("#repeatpassword").val().length){
            $("span.errorMessage").html("请输入重复密码");
            $("div.registerErrorMessageDiv").css("visibility","visible");   
            $(".registerErrorMessageDiv").show();
            return false;
        }       
        if($("#password").val() !=$("#repeatpassword").val()){
            $("span.errorMessage").html("重复密码不一致");
            $("div.registerErrorMessageDiv").css("visibility","visible");  
            $(".registerErrorMessageDiv").show();
            return false;
        }       
 
        return true;
    });
})
</script>
</head>
<body>
<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/simpleSearch.jsp'%>
	<div class="registerDiv">
		<div class="registerErrorMessageDiv" style='display: none;'>
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>
		<form action="front_regist" method='post' class='registerForm'>
			<table align="center" class="registerTable">
				<tbody>
					<tr>
						<td class="registerTip registerTableLeftTD">设置会员名</td>
						<td></td>
					</tr>
					<tr>
						<td class="registerTableLeftTD">登陆名</td>
						<td class="registerTableRightTD">
							<input placeholder="会员名一旦设置成功，无法修改" name="name" id="name">
						</td>
					</tr>
					<tr>
						<td class="registerTip registerTableLeftTD">设置登陆密码</td>
						<td class="registerTableRightTD">登陆时验证，保护账号信息</td>
					</tr>
					<tr>
						<td class="registerTableLeftTD">登陆密码</td>
						<td class="registerTableRightTD">
							<input type="password" placeholder="设置你的登陆密码" name="password" id="password">
						</td>
					</tr>
					<tr>
						<td class="registerTableLeftTD">密码确认</td>
						<td class="registerTableRightTD">
							<input type="password" placeholder="请再次输入你的密码" id="repeatpassword">
						</td>
					</tr>
					<tr>
						<td class="registerButtonTD" colspan="2">
							<a href="#nowhere">
								<button type="submit">提 交</button>
							</a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<jsp:include page="/include/bottom.jsp"></jsp:include>
</body>
</html>