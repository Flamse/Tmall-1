<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类属性</title>
<style type='text/css'>
div.main {
	width: 1080px;
	margin: 0 auto;
}

table {
	width: 100%
}

table tr td {
	text-align: center;
	font-size: 16px;
}
div.add-Title {
	line-height: 30px;
	background-color: #C0C0C0;
	margin-bottom: 10px;
}
.addProperty {
	width: 450px;
	margin: 0 auto;
	border: 1px solid #cccccc;
	border-radius: 4px;
}
</style>
</head>
<body>
	<div class='main'>
		<%@include file="../include/admin/adminNavigator.jsp"%>
		<div class='catalog'>
			<ul class="breadcrumb">
				<li>
					<a href="admin_category_list">分类管理</a>
					<span class="divider">/</span>
				</li>
				<li class='active'>分类属性</li>
			</ul>
		</div>
		<table class="table-striped table-hover table-bordered">
			<tr>
				<th>ID</th>
				<th>属性名称</th>
				<th>编辑属性</th>
				<th>删除属性</th>
			</tr>
			<c:forEach items="${requestScope.properties}" begin="0" end="${page.count}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>
						<a href="admin_property_edit?pid=${item.id}" class='icon-edit'></a>
					</td>
					<td>
						<a href="admin_property_delete?pid=${item.id}" class='icon-trash'></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="../include/admin/adminPage.jsp"%>
		<div class="addProperty">
			<div class='add-Title'>新增属性</div>
			<form action="admin_property_add"  method="POST" id="addProperty" class="form-inline form-horizontal">
				<div class="control-group">
					<label class="control-label" for="propertyName">属性名称名称</label>
					<div class="controls">
						<input type="text" id="propertyName" name="cName" placeholder="请输入属性名称">
						<input type="hidden" name='cid' value='${param.cid}'>
					</div>
				</div>
				<div class="text-center control-group">
					<button type="submit" class="btn">添加</button>
				</div>
			</form>
		</div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>