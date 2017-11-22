<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	font-size: 12px;
	color: #999;
}

img.simpleLogo {
	position: absolute;
	z-index: -1;
	margin-top: 25px;
}

div.simpleSearchDiv {
	width: 320px;
	height: 40px;
	margin: 10px 20px 40px;
	background-color: #C40000;
	display: block;
	padding: 1px;
}

div.simpleSearchDiv input {
	width: 210px;
	height: 26px;
	margin: 2px;
	outline: none;
}

div.simpleSearchDiv button {
	background-color: #C40000;
	border: 1px solid transparent;
	color: white;
	width: 60px;
	font-size: 16px;
	margin: 5px 11px;
}

.searchBelow {
	margin-top: 10px;
}

.searchBelow span {
	color: #999;
}

.searchBelow span a {
	margin-right: 5px;
	color: #999;
	text-decoration: none;
}

.searchBelow span a:hover {
	color: #C40000;
	text-decoration: none;
}
</style>

</head>
<body>
	<a href='#'>
		<img alt="" src="http://how2j.cn/tmall/img/site/simpleLogo.png" class='simpleLogo'>
	</a>
	<div class='pull-right simpleSearchDiv'>
		<form action="front_search">
			<input type="text" class="pull-left keyword" name='keyWord' value='${param.keyWord }' placeholder="篮球鞋">
			<button type="submit" class='searchbutton'>搜索</button>
			<div class="searchBelow">
				<c:forEach items="${cs}" var="c" varStatus="st">
					<c:if test="${st.count>=8 and st.count<=11}">
						<span>
							<a href="front_category?cid=${c.id}"> ${c.name} </a>
							<c:if test="${st.count!=11}">
								<span>|</span>
							</c:if>
						</span>
					</c:if>
				</c:forEach>
			</div>
		</form>
	</div>
</body>
</html>