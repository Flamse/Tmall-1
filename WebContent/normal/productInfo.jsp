<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file='/include/admin/adminHeader.jsp'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='css/productInfo.css'>
<script type="text/javascript" src='js/productInfo.js'></script>
<title>${product.name}</title>
</head>
<body>
	<%@ include file='/include/FrontTop.jsp'%>
	<%@ include file='/include/search.jsp'%>
	<div class='main'>
		<div class='imgAndInfo'>
			<div class="imgInimgAndInfo">
				<img class="bigImg" src="img/productSingle/${product.firstProductImage.id }.jpg">
				<div class="smallImageDiv">
					<c:forEach items='${product.productSingleImages }' var="image">
						<img width="100px" class="smallImage" src="img/productSingle/${image.id}.jpg">
					</c:forEach>
				</div>
			</div>
		</div>
		<div class='productInfo'>
			<div class='productTitle'>${product.name }</div>
			<div class='productSecondTitle'>${product.subTitle}</div>
			<div class='juhuasuan'>
				<span class='juhuasuanBig'>聚划算</span>
				<span>
					此商品即将参加聚划算，
					<span class="juhuasuanTime">1天19小时</span>
					后开始!
				</span>
			</div>
			<div class='productPrice'>
				<div class='gouwuquandiv'>
					<img alt="" src="http://how2j.cn/tmall/img/site/gouwujuan.png">
					<span>全站商品通用</span>
				</div>
				<div class='originaldiv'>
					<span class='desc'>价格</span>
					<span class=''>￥</span>
					<span class='originalPrice'>${product.originalPrice }</span>
				</div>
				<div class='promotiondiv'>
					<span class='desc'>促销价</span>
					<span class='sign-yuan'>￥</span>
					<span class='promotionPrice'>${product.promotePrice }</span>
				</div>
			</div>
			<div class='salesVolumeAndEvaluation'>
				<div class='salesVolumeDiv'>
					<span>月销量</span>
					<span class='salesVolume'>${product.saleCount}</span>
				</div>
				<div class='EvaluationDiv'>
					<span>累计评价</span>
					<span class='Evaluation'>${product.reviewCount}</span>
				</div>
			</div>
			<div class='DesiredPurchaseQuantity'>
				<div class='count'>
					<span>数量</span>
					<span>
						<span class="productNumberSettingSpan">
							<input type="text" value="1" class="productNumberSetting">
						</span>
						<span class="arrow">
							<a class="increaseNumber">
								<span class="updown">
									<img src="http://how2j.cn/tmall/img/site/increase.png">
								</span>
							</a>
							<span class="updownMiddle"> </span>
							<a class="decreaseNumber">
								<span class="updown">
									<img src="http://how2j.cn/tmall/img/site/decrease.png">
								</span>
							</a>
						</span>
						<span>件</span>
						<span>
							库存
							<span class='inventory'>${product.stock}</span>
							件
						</span>
					</span>
				</div>
			</div>
			<div class='servicePromise'>
				<span>服务承诺</span>
				<a href="#">正品保证</a>
				<a href="#">急速退款</a>
				<a href="#">附赠运费险</a>
				<a href="#">七天无理由退货</a>
			</div>
			<div class='buyBottom'>
			<input type="hidden" class='pid'  value='${product.id }'>
				<a class='addToCart' href='front_addToCart?pid=${product.id }'>
					<button class='addToCartButton'>
						<span class='glyphicon glyphicon-shopping-cart'></span>
						加入购物车
					</button>
				</a>
				<a class='buyNow' href='front_buyNow?pid=${product.id }'>
					<button>立即购买</button>
				</a>
			</div>
		</div>
		<div style='clear: both;'></div>
		<div class="productDetailDiv" style="display: block;">
			<div class="productDetailTopPart">
				<a class="productDetailTopPartSelectedLink selected">商品详情</a>
				<a class="productDetailTopReviewLink">
					累计评价
					<span class="productDetailTopReviewLinkNumber">${product.reviewCount}</span>
				</a>
			</div>
			<!-- 产品参数 -->
			<div class="productParamterPart">
				<div class="productParamter">产品参数：</div>
				<div class="productParamterList">
					<c:forEach items='${propertyValues}' var='value'>
						<span>${value.property.name }: ${value.value } </span>
					</c:forEach>
				</div>
				<div style="clear: both"></div>
			</div>
			<!-- 商品图片展示 -->
			<div class="productDetailImagesPart">
				<c:forEach items='${product.productDetailImages}' var='image'>
					<img src="img/productDetail/${image.id }.jpg">
				</c:forEach>
			</div>
		</div>
		<!-- 评价信息 -->
		<div class="productReviewContentPart">
			<c:if test="${empty reviews }">
				<div style='font-size: 20px; font-weight: bold; line-height: 40px;'>暂无评价哦</div>
			</c:if>
			<c:forEach items='${reviews}' var='r'>
				<div class="productReviewItem">
					<div class="productReviewItemDesc">
						<div class="productReviewItemContent">${r.content }</div>
						<div class="productReviewItemDate">
							<fmt:formatDate value="${r.createDate}" type="both" />
						</div>
					</div>
					<div class="productReviewItemUserInfo">
						${r.user.anonymousName }
						<span class="userInfoGrayPart">（匿名）</span>
					</div>
					<div style="clear: both"></div>
				</div>
			</c:forEach>
		</div>
	<%@include file='/include/loginModal.jsp'%>
	</div>
	<%@include file='/include/bottom.jsp'%>
	

</body>
</html>