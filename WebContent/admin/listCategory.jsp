<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类管理</title>
<style type="text/css">
.main {
	width: 1080px;
	margin: 0 auto;
}

div.catalog {
	text-align: left;
}

table {
	width: 100%;
}

table tr td, th {
	text-align: center;
	font-size: 16px;
}

img.categoryImg {
	width: 100px;
	height: 20px;
}

div.add-Title {
	line-height: 30px;
	background-color: #C0C0C0;
	margin-bottom: 10px;
}

.addCategory {
	width: 450px;
	margin: 0 auto;
	border: 1px solid #cccccc;
	border-radius: 4px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#addCategory").submit(function() {
			if (!checkEmpty("categoryName", "分类名称")) {
				return false;
			}
			if (!checkEmpty("categoryImg", "分类图片")) {
				return false;
			}
		});
	});
</script>
</head>
<body>
	<div class='main'>
		<%@include file="../include/admin/adminNavigator.jsp"%>
		<div class='catalog'>
			<ul class="breadcrumb">
				<li>
					<a href="admin_category_list">分类管理</a>
				</li>
			</ul>
		</div>
		<div class='categorydiv'>
			<table class="table-striped table-hover table-bordered">
				<tr>
					<th>ID</th>
					<th>图片</th>
					<th>分类名称</th>
					<th>编辑分类</th>
					<th>分类属性</th>
					<th>分类产品</th>
					<th>删除分类</th>
				</tr>
				<c:forEach items="${requestScope.categories}" begin="0" end="${page.count}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>
							<img class="categoryImg" alt="" src="img/category/${item.id}.jpg">
						</td>
						<td>${item.name}</td>
						<td>
							<a href="admin_category_edit?cid=${item.id}" class='icon-edit'></a>
						</td>
						<td>
							<a href="admin_property_list?cid=${item.id}" class='icon-list'></a>
						</td>
						<td>
							<a href="admin_product_list?cid=${item.id}" class='icon-list'></a>
						</td>
						<td>
							<a href="admin_category_delete?cid=${item.id}" class='icon-trash'></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@include file="../include/admin/adminPage.jsp"%>
		<div class="text-center addCategory">
			<div class='add-Title'>新增属性</div>
			<form action="admin_category_add" enctype="multipart/form-data" method="POST" id="addCategory" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="categoryName">分类名称</label>
					<div class="controls">
						<input type="text" id="categoryName" name="cName" placeholder="请输入分类名称">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="categoryImg">分类图片</label>
					<div class="controls">
						<input type="file" accept="image/*" id="categoryImg" name="cImg" placeholder="图片">
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