<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分类管理</title>
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

img.categoryImg {
	width: 100px;
	height: 20px;
}

div.add-Title {
	line-height: 30px;
	background-color: #C0C0C0;
	margin-bottom: 10px;
}

.addCategory {
	width: 450px;
	margin: 0 auto;
	border: 1px solid #cccccc;
	border-radius: 4px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#addCategory").submit(function() {
			if (!checkEmpty("categoryName", "分类名称")) {
				return false;
			}
			if (!checkEmpty("categoryImg", "分类图片")) {
				return false;
			}
		});
		$('input.editValue').blur(function() {
			var value = $(this).val();
			var pvid = $(this).attr('pvid');

			var xmlHttp;
			if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else {
				xmlHttp = new ActiveXObject("Mcrosoft.XMLHTTP");
			}
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readystate == 4 && xmlHttp.status == 200) {
					alert('修改成功');
				}
			}
			xmlHttp.open("GET", "admin_product_updatePropertyValue?value=" + value+"&id="+pvid, true);
			xmlHttp.send();
		});

	});
</script>
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
					<a href="admin_product_list?cid=${product.category.id}">${product.category.name}</a>
					<span class="divider">/</span>
				</li>
				<li class="active">
					${product.name}
					<span class="divider">/</span>
				</li>
				<li class="active">产品属性编辑</li>
			</ul>
		</div>
		<div class='categorydiv'>
			<c:forEach items="${requestScope.propertyValues}" var="item" varStatus="status">
				<span>${item.property.name} :</span>
				<input type="text" name='${item.property.name}' value='${item.value}' pvid="${item.id}" class='editValue'>
				<c:if test="${status.count%3==0}">
					<br />
				</c:if>
			</c:forEach>
		</div>
		<%@include file="../include/admin/adminFooter.jsp"%>
	</div>
</body>
</html>