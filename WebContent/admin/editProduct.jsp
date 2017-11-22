<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品编辑</title>
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
				<li>
					<a href="admin_category_list">分类管理</a>
					<span class="divider">/</span>
				</li>
				<li>
					<a href="admin_product_list?cid=${requestScope.product.category.id }">${product.category.name}</a>
					<span class="divider">/</span>
				</li>
				<li class='active'>产品编辑</li>
			</ul>
		</div>
		<div class="text-center editProduct">
			<div class='add-Title'>编辑产品</div>
			<form action="admin_product_update" method="POST" id="editProduct" class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="productName">产品名称</label>
					<div class="controls">
						<input type="text" id="productName" name="pName" value="${product.name }" placeholder="请输入产品名称">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="productKeyWord">产品关键字</label>
					<div class="controls">
						<input type="text" id="productKeyWord" name="pKeyWord" value="${product.subTitle }" placeholder="请输入产品关键字">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="producOriginalPrice">原始价格</label>
					<div class="controls">
						<input type="text" id="producOriginalPrice" name="pOriginalPrice"  value="${product.originalPrice }" placeholder="请输入产品原始价格">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="producPromotePrice">优惠价格</label>
					<div class="controls">
						<input type="text" id="producPromotePrice" name="pPromotePrice" value="${product.promotePrice }" placeholder="请输入产品优惠价格">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="productStock">产品库存</label>
					<div class="controls">
						<input type="text" id="productStock" name="pStock" value="${product.stock }" placeholder="请输入产品库存">
					</div>
				</div>
				<div class="text-center control-group">
					<input type="hidden" name="cid" value="${product.category.id}">
					<input type="hidden" name="pid" value="${product.id}">
					<button type="submit" class="btn">提交</button>
				</div>
			</form>
		</div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>