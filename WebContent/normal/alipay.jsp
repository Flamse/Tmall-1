<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单支付</title>
<style type="text/css">
.aliPayPageDiv {
	text-align: center;
	width: 1080px;
	margin:0 auto;
	padding-bottom:100px;
}

.aliPayPageLogo {
	margin: 50px;
}

.confirmPay {
	height: 30px;
	width: 150px; background-color : #3366FF;
	border: 1px solid transparent;
	background-color: #3366FF;
}

.confirmMoney {
	display: block;
	color:#c40000;
	font-size: 20px;
	font-weight: bold;
	margin:5px;
}
</style>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<div class="aliPayPageLogo">
		<img class="pull-left" src="img/site/simpleLogo.png">
		<div style="clear: both"></div>
	</div>
	<div class="aliPayPageDiv">


		<div>
			<span class="confirmMoneyText">扫一扫付款（元）</span>
			<span class="confirmMoney">
				￥
				<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2" />
			</span>

		</div>
		<div>
			<img class="aliPayImg" src="img/site/alipay2wei.png">
		</div>

		<div>
			<a href="front_payed?oid=${param.oid}&total=${param.total}">
				<button class="confirmPay">确认支付</button>
			</a>
		</div>
		<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
</body>
</html>