<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评价商品</title>
<link rel='stylesheet' href='css/productReview.css'>
<script type="text/javascript" src='js/productReview.js'></script>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<div class="reviewDiv">
		<c:forEach items='${order.orderItems }' var="oi">
			<div class='productReview' pid="${oi.product.id }">
				<div class="reviewProductInfoDiv">
					<div class="reviewProductInfoImg">
						<img class="reviewProductImg" width="100px" height="100px" src="http://how2j.cn/tmall/img/productSingle/${oi.product.firstProductImage.id }.jpg">
					</div>
					<div class="reviewProductInfoRightDiv">
						<div class="reviewProductInfoRightText">${oi.product.name }</div>
						<table class="reviewProductInfoTable">
							<tbody>
								<tr>
									<td width="75px">商品价格:</td>
									<td>
										<span class="reviewProductInfoTablePrice">
											￥
											<fmt:formatNumber value="${oi.product.promotePrice }"></fmt:formatNumber>
										</span>
										元
									</td>
								</tr>
								<tr>
									<td>配送</td>
									<td>快递: 0.00</td>
								</tr>
								<tr>
									<td>月销量:</td>
									<td>
										<span class="reviewProductInfoTableSellNumber">${oi.product.saleCount }</span>
										件
									</td>
								</tr>
							</tbody>
						</table>
						<div class="reviewProductInfoRightBelowDiv">
							<span class="reviewProductInfoRightBelowImg">
								<img1 src="http://how2j.cn/tmall/img/site/reviewLight.png"></img1>
							</span>
							<span class="reviewProductInfoRightBelowText">
								现在查看的是 您所购买商品的信息 于
								<fmt:formatDate value="${order.createDate }" pattern="yyyy年MM月dd日" />
								下单购买了此商品
							</span>
						</div>
					</div>
					<div style="clear: both"></div>
				</div>
				<div class="reviewStasticsDiv" pid='${oi.product.id }'>
					<div class="reviewStasticsLeft">
						<div class="reviewStasticsLeftTop"></div>
						<div class="reviewStasticsLeftContent">
							累计评价
							<span class="reviewStasticsNumber" pid='${oi.product.id }'> ${oi.product.reviewCount }</span>
						</div>
						<div class="reviewStasticsLeftFoot"></div>
					</div>
					<div class="reviewStasticsRight">
						<div class="reviewStasticsRightEmpty"></div>
						<div class="reviewStasticsFoot"></div>
					</div>
					<div style='clear: both;'></div>
				</div>
				<div class="makeReviewDiv" pid='${oi.product.id }'>
					<div class="makeReviewText">其他买家需要你的建议哦！</div>
					<table class="makeReviewTable">
						<tbody>
							<tr>
								<td class="makeReviewTableFirstTD">评价商品</td>
								<td>
									<textarea name="content" id='reviewContent' class='reviewContent' pid='${oi.product.id }'></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="reviewSuccess" pid='${oi.product.id }' style='display: none;'>
					<span>评价完成！</span>
				</div>
			</div>
		</c:forEach>
		<div class="makeReviewButtonDiv">
			<button type="buttom" class='addReviewButton' pid='${oi.product.id }' oid='${order.id }'>提交评价</button>
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>