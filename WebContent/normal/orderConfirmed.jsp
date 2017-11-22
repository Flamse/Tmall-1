<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认收货成功</title>
<style type="text/css">
div.registerSuccessDiv {
	text-align: center;
	width:1080px;
	margin:100px auto;
	background-color: #F3FDF6;
	font-weight: bold;
}
</style>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/search.jsp'%>
	<div class="registerSuccessDiv">
		<img src="img/site/registerSuccess.png">
		确认收货成功，卖家将收到您的货款
		<%@include file='/include/loginModal.jsp'%>
	</div>
	
	<%@ include file='/include/bottom.jsp'%>
</body>
</html>