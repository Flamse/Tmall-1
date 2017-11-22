<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品图片管理</title>
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

.type_single, .type_detail {
	float: left;
	width: 400px;
	margin: 20px 30px;
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
	<div class='main '>
		<%@include file="../include/admin/adminNavigator.jsp"%>
		<div class='catalog'>
			<ul class="breadcrumb">
				<li>
					<a href="admin_category_list">分类管理</a>
					<span class="divider">/</span>
				</li>
				<li>
					<a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a>
					<span class="divider">/</span>
				</li>
				<li class="active">
					${product.name}
					<span class="divider">/</span>
				</li>
				<li class="active">产品图片管理</li>
			</ul>
		</div>
		<div class='type_single'>
			<form action="admin_productImages_add" method="post" enctype='multipart/form-data'>
				<fieldset>
					<legend>添加展示图片</legend>
					<input type="file" name='singleImage'>
					<input type="hidden" name='type' value='type_single'>
					<input type="hidden" name='productId' value='${product.id}'>
					<span class="help-block">此图片用于产品展示</span>
					<button type="submit" class="btn">提交</button>
				</fieldset>
			</form>
			<table class='table text-center'>
				<caption>产品展示图片</caption>
				<tr>
					<th>序号</th>
					<th>缩略图</th>
					<th>删除</th>
				</tr>
				<c:forEach items="${requestScope.singleImages }" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td >
							<img src='img/productSingle/${item.id}.jpg' style='width: 60px; height: 60px;'>
						</td>
						<td>
							<a href='admin_productImages_delete?id=${item.id }&pid=${product.id}' class='icon-trash'></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class='type_detail'>
			<form action="admin_productImages_add" method="post" enctype='multipart/form-data'>
				<fieldset>
					<legend>添加商品详情图片</legend>
					<input type="file" name='detailImage'>
					<input type="hidden" name='type' value='type_detail'>
					<input type="hidden" name='productId' value='${product.id}'>
					<span class="help-block">此图片用于商品详情.</span>
					<button type="submit" class="btn">提交</button>
				</fieldset>
			</form>
			<table class='table'>
				<caption>产品详情图片</caption>
				<tr>
					<th>序号</th>
					<th>缩略图</th>
					<th>删除</th>
				</tr>
				<c:forEach items="${requestScope.detailImages }" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td >
							<img src='img/productDetail/${item.id}.jpg' style='width: 60px; height: 60px;'>
						</td>
						<td>
							<a href='admin_productImages_delete?id=${item.id }&pid=${product.id}' class='icon-trash'></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style='clear: both;'></div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>