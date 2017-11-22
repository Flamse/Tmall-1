<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% String path1 = request.getRequestURL().toString(); %>
</head>
<body>
	<nav class="text-center pagination">
	<ul>
		<li <c:if test="${!page.hasPreviousPage}">class="disabled"</c:if>>
			<a href="admin_${servletName }_${method}?page.start=0">
				<span aria-hidden="true">&laquo;</span>
			</a>
		</li>
		<li <c:if test="${!page.hasPreviousPage}">class="disabled"</c:if>>
			<a href="admin_${servletName }_${method}?page.start=${page.start-page.count}${page.param}" aria-label="Previous">
				<span aria-hidden="true"><</span>
			</a>
		</li>
		<c:forEach begin="0" end="${(page.totalPage-1)>0?(page.totalPage-1):0 }" varStatus="status">
		<!-- 判断当前是否为当前页，通过当前的page.start与分页计算出的page.satrt是否相等 -->
			<li <c:if test="${page.start==status.index*page.count}">class="active"</c:if>>
				<a href="admin_${servletName }_${method}?page.start=${status.index*page.count}${page.param}">${status.count}</a>
			</li>
		</c:forEach>
		<li <c:if test="${!page.hasNextPage}">class="disabled"</c:if>>
			<a href="admin_${servletName }_${method}?page.start=${page.start+page.count}${page.param}" aria-label="Next">
				<span aria-hidden="true">></span>
			</a>
		</li>
		<li <c:if test="${!page.hasNextPage}">class="disabled"</c:if>>
			<a href="admin_${servletName }_${method}?page.start=${page.lastStart}${page.param}" aria-label="Next">
				<span aria-hidden="true">&raquo;</span>
			</a>
		</li>
	</ul>
	</nav>
</body>
</html>