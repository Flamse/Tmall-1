<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的购物车</title>
<link rel='stylesheet' href='css/shoppingCart.css'>
<script type="text/javascript" src='js/shoppingCart.js'></script>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/search.jsp'%>
	<div class='page'>
		<div class="cartDiv">
			<div class="cartTitle pull-right">
				<span>已选商品 (不含运费)</span>
				<span class="cartTitlePrice">￥0.00</span>
				<button class="createOrderButton" style="background-color: rgb(170, 170, 170);" disabled="disabled">结 算</button>
			</div>
			<div style='clear: both;'></div>
			<div class="cartProductList">
				<table class="cartProductTable">
					<thead>
						<tr>
							<th class="selectAndImage">
								<img src="http://how2j.cn/tmall/img/site/cartNotSelected.png" class="selectAllItem" selectit="false">
								全选
							</th>
							<th>商品信息</th>
							<th>单价</th>
							<th>数量</th>
							<th width="120px">金额</th>
							<th class="operation">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items='${ois }' var='oi'>
							<tr class="cartProductItemTR" oiid="${oi.id }">
								<td>
									<img src="http://how2j.cn/tmall/img/site/cartNotSelected.png" class="cartProductItemIfSelected" oiid="${oi.id }" selectit="false">
									<a href="#nowhere" style="display: none">
										<img src="http://how2j.cn/tmall/img/site/cartSelected.png">
									</a>
									<img width="40px" src="http://how2j.cn/tmall/img/productSingle/${oi.product.firstProductImage.id }.jpg" class="cartProductImg">
								</td>
								<td class='cartProductLink'>
									<div class="cartProductLinkOutDiv">
										<a class="cartProductLink" href="front_product?pid=${oi.product.id }">${oi.product.name }</a>
										<div class="cartProductLinkInnerDiv">
											<img title="支持信用卡支付" src="http://how2j.cn/tmall/img/site/creditcard.png">
											<img title="消费者保障服务,承诺7天退货" src="http://how2j.cn/tmall/img/site/7day.png">
											<img title="消费者保障服务,承诺如实描述" src="http://how2j.cn/tmall/img/site/promise.png">
										</div>
									</div>
								</td>
								<td>
									<span class="cartProductItemOringalPrice">
										￥
										<fmt:formatNumber minFractionDigits="2" type='number' value='${oi.product.originalPrice }'></fmt:formatNumber>
									</span>
									<span class="cartProductItemPromotionPrice">
										￥
										<fmt:formatNumber minFractionDigits="2" type='number' value=' ${oi.product.promotePrice }'></fmt:formatNumber>
									</span>
								</td>
								<td>
									<div class="cartProductChangeNumberDiv">
										<span pid="${oi.product.id }" class="hidden orderItemStock ">${oi.product.stock }</span>
										<span pid="${oi.product.id }" class="hidden orderItemPromotePrice "> ${oi.product.promotePrice }' </span>
										<button class="numberMinus" oiid="${oi.id }" pid="${oi.product.id }">-</button>
										<input value="${oi.number }" autocomplete="off" class="orderItemNumberSetting" oiid="${oi.id }" pid="${oi.product.id }">
										<button class="numberPlus" oiid="${oi.id }" pid="${oi.product.id }" stock="${oi.product.stock }">+</button>
									</div>
								</td>
								<td>
									<span pid="${oi.product.id }" oiid="${oi.id }" class="cartProductItemSmallSumPrice">
										￥
										<fmt:formatNumber minFractionDigits="2" type='number' value='${oi.number*oi.product.promotePrice}'></fmt:formatNumber>
									</span>
								</td>
								<td>
									<a href="front_deleteOrderItem" oiid="${oi.id }" class="deleteOrderItem">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="cartFoot">
				<img src="http://how2j.cn/tmall/img/site/cartNotSelected.png" class="selectAllItem" selectit="false">
				<span>全选</span>
				<!--         <a href="#">删除</a> -->
				<div class=" cartFoot-right pull-right">
					<span>
						已选商品
						<span class="cartSumNumber">0</span>
						件
					</span>
					<span>合计 (不含运费): </span>
					<span class="cartSumPrice">￥0.00</span>
					<div class="createOrderButtonDiv">
						<form action="front_buy" method="post">
						<input class='oiids' name='oiids' value='' type="hidden">
							<button class="createOrderButton" type="submit" style='height: 40px;' disabled="disabled">结 算</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>