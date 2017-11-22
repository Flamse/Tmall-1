<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类属性编辑</title>
<style type="text/css">
div.main {
	width: 1080px;
	margin: 0 auto;
}

.editProperty {
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
				<li>
					<a href="admin_category_list">分类管理</a>
					<span class="divider">/</span>
				</li>
				<li>
					<a href="admin_property_list?cid=${requestScope.property.category.id }">分类属性</a>
					<span class="divider">/</span>
				</li>
				<li class='active'>分类属性编辑</li>
			</ul>
		</div>
		<div class="editProperty">
			<div class='add-Title'>
				<span>编辑属性</span>
			</div>
			<form action="admin_property_update" method="POST" id="addProperty" class="form-inline form-horizontal">
				<div class="control-group">
					<label class="control-label" for="propertyName">属性名称</label>
					<div class="controls">
						<input type="text" id="propertyName" name="pName" value='${requestScope.property.name }' placeholder="请输入属性名称">
						<input type="hidden" name='pid' value='${requestScope.property.id }'>
						<input type="hidden" name='cid' value='${requestScope.property.category.id}'>
					</div>
				</div>
				<div class="text-center control-group">
					<button type="submit" class="btn">提交</button>
				</div>
			</form>
		</div>

		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>