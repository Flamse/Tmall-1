<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单确认</title>
<link rel='stylesheet' href='css/buy.css'>
<script type="text/javascript" src='js/buy.js'></script>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<div class="buyPageDiv">
		<%@ include file='/include/search.jsp'%>
		<div class="buyFlow">
			<img src="http://how2j.cn/tmall/img/site/simpleLogo.png" class="pull-left">
			<img src="http://how2j.cn/tmall/img/site/buyflow.png" class="pull-right">
			<div style="clear: both"></div>
		</div>
		<form action="front_createOrder" method='POST'>
			<div class="address">
				<div class="addressTip">输入收货地址</div>
				<div>
					<table class="addressTable">
						<tbody>
							<tr>
								<td class="firstColumn">
									详细地址
									<span class="redStar">*</span>
								</td>
								<td>
									<textarea placeholder="建议您如实填写详细收货地址，例如接到名称，门牌好吗，楼层和房间号等信息" name="address"></textarea>
								</td>
							</tr>
							<tr>
								<td>邮政编码</td>
								<td>
									<input type="text" placeholder="如果您不清楚邮递区号，请填写000000" name="post">
								</td>
							</tr>
							<tr>
								<td>
									收货人姓名
									<span class="redStar">*</span>
								</td>
								<td>
									<input type="text" placeholder="长度不超过25个字符" name="receiver">
								</td>
							</tr>
							<tr>
								<td>
									手机号码
									<span class="redStar">*</span>
								</td>
								<td>
									<input type="text" placeholder="请输入11位手机号码" name="mobile">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="productList">
				<div class="productListTip">确认订单信息</div>
				<table class="productListTable">
					<thead>
						<tr>
							<th class="productListTableFirstColumn" colspan="2">
								<img src="http://how2j.cn/tmall/img/site/tmallbuy.png" class="tmallbuy">
								<a href="#" class="marketLink">店铺：天猫店铺</a>
								<a href="#" class="wangwanglink">
									<span class="wangwangGif"></span>
								</a>
							</th>
							<th>单价</th>
							<th>数量</th>
							<th>小计</th>
							<th>配送方式</th>
						</tr>
						<tr class="rowborder">
							<td colspan="2"></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</thead>
					<tbody class="productListTableTbody">
						<c:forEach items='${ois }' var='oi' varStatus="st">
							<tr class="orderItemTR">
								<td class="orderItemFirstTD">
									<img width="20px" src="http://how2j.cn/tmall/img/productSingle/${oi.product.firstProductImage.id }.jpg" class="orderItemImg">
								</td>
								<td class="orderItemProductInfo">
									<a class="orderItemProductLink" href="front_product?pid=${oi.product.id }"> ${oi.product.name} </a>
									<img title="支持信用卡支付" src="http://how2j.cn/tmall/img/site/creditcard.png">
									<img title="消费者保障服务,承诺7天退货" src="http://how2j.cn/tmall/img/site/7day.png">
									<img title="消费者保障服务,承诺如实描述" src="http://how2j.cn/tmall/img/site/promise.png">
								</td>
								<td>
									<span class="orderItemProductPrice">￥<fmt:formatNumber value="${oi.product.promotePrice }" type="number" minFractionDigits="2"></fmt:formatNumber></span>
								</td>
								<td>
									<span class="orderItemProductNumber">${oi.number }</span>
								</td>
								<td>
									<span class="orderItemUnitSum"> ￥<fmt:formatNumber value="${oi.number*oi.product.promotePrice}" type="number" minFractionDigits="2"></fmt:formatNumber> </span>
								</td>
								<c:if test="${st.count==1 }">
									<td class="orderItemLastTD" rowspan="5">
										<label class="orderItemDeliveryLabel">
											<input type="radio" checked="checked" value="">
											普通配送
										</label>
										<select class="orderItemDeliverySelect">
											<option>快递 免邮费</option>
											<option>航天配送</option>
										</select>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="orderItemSumDiv">
					<div class="pull-left">
						<span class="leaveMessageText">给卖家留言:</span>
						<span>
							<img src="http://how2j.cn/tmall/img/site/leaveMessage.png" class="leaveMessageImg">
						</span>
						<span class="leaveMessageTextareaSpan" style="display: none;">
							<textarea class="leaveMessageTextarea" name="userMessage"></textarea>
							<div>
								<span>还可以输入200个字符</span>
							</div>
						</span>
					</div>
					<span class="pull-right">店铺合计(含运费): ￥${total }</span>
				</div>
			</div>
			<div class="orderItemTotalSumDiv">
				<div class="pull-right">
					<span>实付款：</span>
					<span class="orderItemTotalSumSpan">￥${total }</span>
				</div>
			</div>
			<div class="submitOrderDiv">
				<button class="submitOrderButton" type="submit">提交订单</button>
			</div>
		</form>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>