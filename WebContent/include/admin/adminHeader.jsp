<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery/2.0.0/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
	function checkEmpty(id, name) {
		var value = $("#" + id).val();
		if (value.length == 0) {
			alert(name + "不能为空");
			$("#" + id)[0].focus();
			return false;
		}
		return true;
	}
	function checkNumber(id, name) {
		var value = $("#" + id).val();
		if (value.length == 0) {
			alert(name + "不能为空");
			$("#" + id)[0].focus();
			return false;
		}
		if (isNaN(value)) {
			alert(name + "必须是数字");
			$("#" + id)[0].focus();
			return false;
		}

		return true;
	}
	function checkInt(id, name) {
		var value = $("#" + id).val();
		if (value.length == 0) {
			alert(name + "不能为空");
			$("#" + id)[0].focus();
			return false;
		}
		if (parseInt(value) != value) {
			alert(name + "必须是整数");
			$("#" + id)[0].focus();
			return false;
		}

		return true;
	}

</script>
</head>
<body>
</body>
</html>
