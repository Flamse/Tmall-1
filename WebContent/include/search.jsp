<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body {
	
}

img.search {
	position: absolute;
	top: 20px;
	left: 150px;
	height: 90px;
	width: 300px;
	z-index: -1;
}

.search {
	width: 400px;
	height: 36px;
	margin: 0px auto;
	padding: 1px;
	display: block;
}

.keyword {
	width: 275px;
	height: 36px;
	border: 1px solid transparent;
	outline: none !important;
}

.searchtmallred {
	background-color: #C40000 !important;
	padding: 2px 2px;
}

.searchbutton {
	width: 110px;
	border: 1px solid transparent;
	background-color: #C40000;
	color: white;
	font-size: 20px;
	font-weight: bold;
	margin: 3px 0px;
}

.search div span {
	color: #999;
}

.searchBelow a {
	color: #999;
}

div.searchBelow {
	margin-top: 3px;
	margin-left: -20px;
}

div.searchBelow a {
	padding: 0px 20px 0px 20px;
	font-size: 14px;
}

.searchBelow a:hover {
	text-decoration: none;
	color: #C40000;
}
</style>
</head>
<body>
	<div style='height: 80px; margin: 10px 0px; padding-left: 150px'>
		<a href="#">
			<img class='search' alt="" src="http://how2j.cn/tmall/img/site/logo.gif">
		</a>
		<form action="front_search">
			<div class="search searchtmallred">
				<input style='padding: 2px; height: 30px;' name='keyWord' value='${param.keyWord }' type="text" class="pull-left keyword" placeholder="篮球鞋">
				<button type="submit" class='searchbutton'>搜索</button>
				<div class='pull-left searchBelow'>
					<c:forEach items="${categories}" var="c" varStatus="st">
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
			</div>
		</form>
	</div>
</body>
</html>