<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<link rel="stylesheet" href='css/myOrderPage.css'>
<script type="text/javascript" src='js/myOrderPage.js'></script>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/search.jsp'%>
	<div class="boughtDiv">
		<div class="orderType">
			<div class="selectedOrderType">
				<a class='' orderstatus="all">所有订单</a>
			</div>
			<div class="">
				<a orderstatus="waitPay">待付款</a>
			</div>
			<div class="">
				<a orderstatus="waitDelivery">待发货</a>
			</div>
			<div class="">
				<a orderstatus="waitConfirm">待收货</a>
			</div>
			<div class="">
				<a class="noRightborder" orderstatus="waitReview">待评价</a>
			</div>
			<div class="orderTypeLastOne">
				<a class="noRightborder"> </a>
			</div>
		</div>
		<div style="clear: both"></div>
		<div class="orderListTitle">
			<table class="orderListTitleTable">
				<tbody>
					<tr>
						<td>宝贝</td>
						<td width="100px">单价</td>
						<td width="100px">数量</td>
						<td width="120px">实付款</td>
						<td width="100px">交易操作</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="orderListItem">
			<c:forEach items='${orders}' var='order'>
				<table oid="${order.id }" orderstatus="${order.status }" class="orderListItemTable" style="display: table;">
					<tbody>
						<tr class="orderListItemFirstTR">
							<td colspan="2">
								<b>
									<fmt:formatDate value="${order.createDate }" type="both" />
								</b>
								<span>订单号: ${order.orderCode } </span>
							</td>
							<td colspan="2">
								<img width="13px" src="http://how2j.cn/tmall/img/site/orderItemTmall.png">
								天猫商场
							</td>
							<td colspan="1">
								<a href="#nowhere" class="wangwanglink">
									<div class="orderItemWangWangGif"></div>
								</a>
							</td>
							<td class="orderItemDeleteTD">
								<a  oid="${order.id }" class="deleteOrderLink">
									<span class="orderListItemDelete glyphicon icon-trash"></span>
								</a>
							</td>
						</tr>
						<c:forEach items='${order.orderItems }' var='oi' varStatus='st'>
							<tr class="orderItemProductInfoPartTR" >
								<td class="orderItemProductInfoPartTD">
									<img width="80" height="80" src="http://how2j.cn/tmall/img/productSingle/${oi.product.firstProductImage.id }.jpg">
								</td>
								<td class="orderItemProductInfoPartTD">
									<div class="orderListItemProductLinkOutDiv">
										<a href="front_product?pid=${oi.product.id }">${oi.product.name }</a>
										<div class="orderListItemProductLinkInnerDiv">
											<img title="支持信用卡支付" src="http://how2j.cn/tmall/img/site/creditcard.png">
											<img title="消费者保障服务,承诺7天退货" src="http://how2j.cn/tmall/img/site/7day.png">
											<img title="消费者保障服务,承诺如实描述" src="http://how2j.cn/tmall/img/site/promise.png">
										</div>
									</div>
								</td>
								<td width="50px" valign="top" class="orderListItemNumberTD " rowspan="1">
									<span class="orderListItemNumber">${oi.number}</span>
								</td>
								<td width="100px" class="orderItemProductInfoPartTD itemTotal">
									<div class="orderListItemProductOriginalPrice">
										￥
										<fmt:formatNumber minFractionDigits="2" value="${oi.number*oi.product.originalPrice }"></fmt:formatNumber>
									</div>
									<div class="orderListItemProductPrice">
										￥
										<fmt:formatNumber minFractionDigits="2" value="${oi.number*oi.product.promotePrice }"></fmt:formatNumber>
									</div>
								</td>

								<td width="120px" valign="top" class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD" rowspan="1">
									<c:if test="${st.count==1 }">
										<div class="orderListItemProductRealPrice">
											￥
											<fmt:formatNumber minFractionDigits="2" value="${order.total}"></fmt:formatNumber>
										</div>
										<div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
									</c:if>
								</td>
								<td width="100px" valign="top" class="orderListItemButtonTD orderItemOrderInfoPartTD" rowspan="1">
									<c:if test="${st.count==1 }">
									<c:if test="${order.status=='waitPay' }">
											<a href="front_alipay?oid=${order.id }&total=${order.total}">
												<button class="orderListItemReview">付款</button>
											</a>
										</c:if>
										<c:if test="${order.status=='waitDelivery' }">
											<span>等待卖家发货</span>
										</c:if>
										<c:if test="${order.status=='waitConfirm' }">
											<a href="front_confirmPay?oid=${order.id }">
												<button class="orderListItemReview">确认收货</button>
											</a>
										</c:if>
										<c:if test="${order.status=='waitReview' }">
											<a href="front_review?oid=${order.id }">
												<button class="orderListItemReview">评价</button>
											</a>
										</c:if>
										<c:if test="${order.status=='finish' }">
											<span>订单已完成</span>
										</c:if>
									</c:if>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:forEach>
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>