<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<style type="text/css">
div.main {
	width: 1080px;
	margin: 0 auto;
}

.editProduct {
	width: 450px;
	margin: 0 auto;
	border: 1px solid #cccccc;
	border-radius: 4px;
}

div.add-Title {
	line-height: 30px;
	background-color: #C0C0C0;
	margin-bottom: 10px;
}
</style>


</head>
<body>
	<div class='main'>
		<%@include file="../include/admin/adminNavigator.jsp"%>
				<div class='catalog'>
			<ul class="breadcrumb">
				<li class='active'>用户管理</li>
			</ul>
		</div>
		<table class="table table-bordered table-hover">
			<tr>
				<th>用户id</th>
				<th>用户名</th>
			</tr>
			<c:forEach items='${ requestScope.users}' var='item'>
				<tr>
					<td>${item.id }</td>
					<td>${item.name }</td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="../include/admin/adminPage.jsp"%>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>