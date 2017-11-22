<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类产品列表</title>
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

div.add-Title {
	line-height: 30px;
	background-color: #C0C0C0;
	margin-bottom: 10px;
}

.addProduct {
	width: 450px;
	margin: 0 auto;
	border: 1px solid #cccccc;
	border-radius: 4px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#addProduct").submit(function() {
			if (!checkEmpty("productName", "产品名称")) {
				return false;
			}
			if (!checkNumber("producOriginalPrice", "原始价格")) {
				return false;
			}
			if (!checkNumber("producPromotePrice", "优惠价格")) {
				return false;
			}
			if (!checkInt("productStock", "产品库存")) {
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
					<span class="divider">/</span>
				</li>
				<li class="active">${category.name}</li>
			</ul>
		</div>
		<div class='categorydiv'>
			<table class="table-striped table-hover table-bordered">
				<tr>
					<th>ID</th>
					<th>图片</th>
					<th>产品名称</th>
					<th>产品关键字</th>
					<th>原价格</th>
					<th>优惠价格</th>
					<th>产品库存</th>
					<th>编辑产品</th>
					<th>图片管理</th>
					<th>属性管理</th>
					<th>删除</th>
				</tr>
				<c:forEach items="${requestScope.products}" begin="0" end="${requestScope.page.count}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>
							<img class="categoryImg" style='width:80px;height:60px;' src="img/productSingle/${item.firstProductImage.id}.jpg">
						</td>
						<td>${item.name}</td>
						<td >${item.subTitle}</td>
						<td width=70px>${item.originalPrice}</td>
						<td width=70px>${item.promotePrice}</td>
						<td width=70px>${item.stock}</td>
						<td width=70px>
							<a href="admin_product_edit?pid=${item.id}" class='icon-list'></a>
						</td>
						<td width=70px>
							<a href="admin_productImages_list?id=${item.id}" class='icon-edit'></a>
						</td>

						<td width=70px>
							<a href="admin_product_editPropertyValue?pid=${item.id}" class='icon-list'></a>
						</td>
						<td width=40px >
							<a href="admin_product_delete?pid=${item.id}&cid=${item.category.id}" class='icon-trash'></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@include file="../include/admin/adminPage.jsp"%>
		<div class="text-center addProduct">
			<div class='add-Title'>新增产品</div>
			<form action="admin_product_add" method="POST" id="addProduct" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="productName">产品名称</label>
					<div class="controls">
						<input type="text" id="productName" name="pName" placeholder="请输入产品名称">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="productKeyWord">产品关键字</label>
					<div class="controls">
						<input type="text" id="productKeyWord" name="pKeyWord" placeholder="请输入产品关键字">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="producOriginalPrice">原始价格</label>
					<div class="controls">
						<input type="text" id="producOriginalPrice" name="pOriginalPrice" placeholder="请输入产品原始价格">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="producPromotePrice">优惠价格</label>
					<div class="controls">
						<input type="text" id="producPromotePrice" name="pPromotePrice" placeholder="请输入产品优惠价格">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="productStock">产品库存</label>
					<div class="controls">
						<input type="text" id="productStock" name="pStock" placeholder="请输入产品库存">
					</div>
				</div>
				<div class="text-center control-group">
					<input type="hidden" name="cid" value="${category.id}">
					<button type="submit" class="btn">提交</button>
				</div>
			</form>
		</div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>