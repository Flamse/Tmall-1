<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付成功</title>
<style type="text/css">
.payedDiv {
	border: 1px solid #cccccc;
	margin: 100px;
}

.payedTextDiv {
	background-color: #ECFFDC;
	line-height: 50px;
	font-size: 18px;
	font-weight: bold;
}

.payedAddressInfo ul li {
	margin: 5px;
}

.payedInfoPrice {
	color: #c40000;
	font-size: 16px;
	font-weight: bold;
}

.paedCheckLinkDiv {
	margin: 25px;
}

.warningDiv {
	margin: 5px;
}
</style>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
		<%@ include file='/include/search.jsp'%>
	<div class="payedDiv">
		<div class="payedTextDiv">
			<img src="img/site/paySuccess.png">
			<span>您已成功付款</span>
		</div>
		<div class="payedAddressInfo">
			<ul>
				<li>收货地址：${order.address} ${order.receiver} ${order.mobile }</li>
				<li>
					实付款：
					<span class="payedInfoPrice">
						￥
						<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2" />
				</li>
				<li>预计08月08日送达</li>
			</ul>

			<div class="paedCheckLinkDiv">
				您可以
				<a class="payedCheckLink" href="forebought">查看已买到的宝贝</a>
				<a class="payedCheckLink" href="forebought">查看交易详情 </a>
			</div>

		</div>

		<div class="payedSeperateLine"></div>

		<div class="warningDiv">
			<img src="img/site/warning.png">
			<b>安全提醒：</b>
			下单后，
			<span class="redColor boldWord">用QQ给您发送链接办理退款的都是骗子！</span>
			天猫不存在系统升级，订单异常等问题，谨防假冒客服电话诈骗！
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>