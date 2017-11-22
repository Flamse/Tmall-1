<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑分类属性</title>
<style type="text/css">
div.main {
	width: 1080px;
	margin: 0 auto;
}

.catalog {
	
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
				<li class='active'>分类编辑</li>
			</ul>
		</div>
		<div class=" offset3 span6 addCategory">
			<form action="admin_category_update" enctype="multipart/form-data" method="POST" id="addCategory" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="categoryName">分类名称</label>
					<div class="controls">
						<input type="text" id="categoryName" name="cName" value="${requestScope.category.name}">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="categoryImg">分类图片</label>
					<div class="controls">
						<input type="file" accept="image/*" id="categoryImg" name="cImg" placeholder="图片">
					</div>
				</div>
				<div class="control-group">
					<input type="hidden" name="cid" value="${requestScope.category.id}">
					<button type="submit" class="btn">提交</button>
				</div>
			</form>
		</div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>

</body>
</html>