//菜单显示
function showProductAsideCategorys(cid) {
	$("div.eachCategory[cid=" + cid + "]").css("background-color", "white");
	$("div.eachCategory[cid=" + cid + "]").css("color", "#87CEFA");
	$("div.productsAsideCategorys[cid=" + cid + "]").show();
}
// 菜单隐藏
function hideProductsAsideCategorys(cid) {
	$("div.eachCategory[cid=" + cid + "]").css("background-color", "#e2e2e3");
	$("div.eachCategory[cid=" + cid + "]").css("color", "#000");
	$("div.productsAsideCategorys[cid=" + cid + "]").hide();
}
$(function() {
	// 猫耳朵效果
	$(".rightMenu span").mouseenter(function() {
		var left = $(this).position().left;
		var top = $(this).position().top;
		var width = $(this).css('width');
		var destLeft = parseInt(left) + parseInt(width) / 2;
		$(".catear").css("left", destLeft);
		$(".catear").css("top", top - 20);
		$(".catear").fadeIn(500);
	});
	$(".rightMenu span").mouseleave(function() {
		$(".catear").hide();
	});
	// 菜单显示
	$("div.eachCategory").mouseenter(function() {
		var cid = $(this).attr("cid");
		showProductAsideCategorys(cid);
	});
	// 菜单隐藏
	$("div.eachCategory").mouseleave(function() {
		var cid = $(this).attr("cid");
		hideProductsAsideCategorys(cid);
	});
	$("div.productsAsideCategorys").mouseenter(function() {
		var cid = $(this).attr("cid");
		showProductAsideCategorys(cid);
	});
	$("div.productsAsideCategorys").mouseleave(function() {
		var cid = $(this).attr("cid");
		hideProductsAsideCategorys(cid);
	});
	//开启轮播
	$('.carousel').carousel({
		interval : 3000
	});
	//随机项目标红
	$("div.productsAsideCategorys div.row a").each(function() {
		var v = Math.round(Math.random() * 6);
		if (v == 1)
			$(this).css("color", "#87CEFA");
	});

});