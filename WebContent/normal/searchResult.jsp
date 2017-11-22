<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果</title>
<link rel="stylesheet" href='css/searchResult.css'>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/search.jsp'%>
	<div class='main'>
		<div class="categoryProducts">
			<c:forEach items='${products }' var='product'>
				<div price="${product.promotePrice }" class="productUnit">
					<div class="productUnitFrame">
						<a href="front_product?pid=${product.id }">
							<img width="100px" src="img/productSingle/${product.firstProductImage.id }.jpg" class="productImage">
						</a>
						<span class="productPrice">¥${product.promotePrice }</span>
						<a href="front_product?pid=${product.id }" class="productLink"> ${product.name } </a>
						<a href="front_home" class="tmallLink">天猫专卖</a>
						<div class="show1 productInfo">
							<span class="monthDeal ">
								月成交
								<span class="productDealNumber">${product.saleCount }笔</span>
							</span>
							<span class="productReview">
								评价
								<span class="productReviewNumber">${product.reviewCount }</span>
							</span>
							<span class="wangwang">
								<a class="wangwanglink">
									<img src="http://how2j.cn/tmall/img/site/wangwang.png">
								</a>
							</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<%@include file='/include/loginModal.jsp'%>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>