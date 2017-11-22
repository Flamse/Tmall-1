<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
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
		$("button.showDetail").click(function() {
			var oid = $(this).attr('oid');
			$(".orderDetail[oid=" + oid + "]").toggle();
		});
	});
</script>
</head>
<body>
	<div class='main'>
		<%@include file="../include/admin/adminNavigator.jsp"%>
		<div class='catalog'>
			<ul class="breadcrumb">
				<li class="active">订单管理</li>
			</ul>
		</div>
		<div class='categorydiv'>
			<table class="table-hover table table-bordered" style='border-collapse: collapse;'>
				<tr>
					<th>ID</th>
					<th>状态</th>
					<th>订单金额</th>
					<th>商品数量</th>
					<th>买家名称</th>
					<th>创建时间</th>
					<th>支付时间</th>
					<th>发货时间</th>
					<th>确认收货时间</th>
					<th>订单操作</th>
				</tr>
				<c:forEach items="${requestScope.orders}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>${item.statusDesc}</td>
						<td>
							<fmt:formatNumber value="${item.total}" minFractionDigits="2"></fmt:formatNumber>
						</td>
						<td>${item.totalNumber}</td>
						<td>${item.user.name}</td>
						<td>
							<fmt:formatDate value="${item.createDate}" type="both" />
						</td>
						<td>
							<fmt:formatDate value="${item.payDate}" type="both" />
						</td>
						<td>
							<fmt:formatDate value="${item.deliveryDate}" type="both" />
						</td>
						<td>
							<fmt:formatDate value="${item.confirmDate}" type="both" />
						</td>
						<td>
							<button oid="${item.id }" class='showDetail'>订单详情</button>
							<c:if test="${item.status=='waitDelivery' }">
								<a href="admin_product_editPropertyValue?pid=${item.id}">
									<button>发货</button>
								</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="10" oid="${item.id }" class="orderDetail" style='display: none;'>
							<c:forEach var="oi" items="${item.orderItems }">
								<table>
									<tr>
										<td style='border: none;'>
											<img alt="" style='width:60px;height:60px;' src="img/productSingle/${oi.product.firstProductImage.id}.jpg">
										</td>
										<td style='border: none;'>${oi.product.name }</td>
										<td style='border: none;'>${oi.number }个</td>
										<td style='border: none; color: gray;' class='text-left'>单价￥${oi.product.promotePrice }</td>
									</tr>
								</table>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<%@include file="../include/admin/adminPage.jsp"%>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>