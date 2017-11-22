<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="homepageCategoryProducts">
		<c:forEach items='${categories}' var='category'>
			<div class="eachHomepageCategoryProducts" style='margin:10px auto;'>
				<div class="left-mark"></div>
				<span class="categoryTitle">${category.name}</span>
				<br>
				<c:forEach items='${category.products }' var='product'>
					<div class="productItem" style='height:270px;'>
						<a href="front_product?pid=${product.id }">
							<img src="http://how2j.cn/tmall/img/productSingle_middle/${product.firstProductImage.id }.jpg">
						</a>
						<a href="front_product?pid=${product.id }" class="productItemDescLink">
							<span class="productItemDesc">[热销] ${product.name} </span>
						</a>
						<span class="productPrice"> ￥${product.promotePrice} </span>
					</div>
				</c:forEach>
				<div style="clear: both"></div>
			</div>
		</c:forEach>
		<img src="http://how2j.cn/tmall/img/site/end.png" class="endpng" id="endpng">
	</div>
</body>
</html>