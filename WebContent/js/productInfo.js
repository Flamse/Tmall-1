$(function() {
	// 图片切换
	$('.smallImage').mouseenter(function() {
		var imgURL = $(this).attr('src');
		$('.bigImg').attr('src', imgURL);
	});
	// 购买数量增加
	$('.increaseNumber').click(function() {
		var value = parseInt($('.productNumberSetting').prop('value'));
		var inventory = parseInt($('.inventory').text());
		if (isNaN(value) || value < 0) {
			value = 1;
		} else if (value + 1 > inventory) {
			value = inventory;
		} else {
			value = value + 1;
		}
		$('input.productNumberSetting').val(value);
	});
	// 购买数量减少
	$('.decreaseNumber').click(function() {
		var value = parseInt($('.productNumberSetting').prop('value'));
		var inventory = parseInt($('.inventory').text());
		if (isNaN(value) || value - 2 < 0) {
			value = 1;
		} else {
			value = value - 1;
		}
		$('input.productNumberSetting').val(value);
	});
	// 购买数量监控
	$('input.productNumberSetting').keyup(function() {
		var value = parseInt($('.productNumberSetting').prop('value'));
		var inventory = parseInt($('.inventory').text());
		if (isNaN(value) || value < 0) {
			value = 1;
		} else if (value + 1 > inventory) {
			value = inventory;
		}
		$('input.productNumberSetting').val(value);
	});
	// 商品详情与商品评价切换
	$('.productDetailTopPart a').click(function() {
		var aClass = $(this).attr('class');
		var s = aClass.split(' ');
		if (s[0] == 'productDetailTopPartSelectedLink') {
			$('.productReviewContentPart').hide();
			var aClass = s[0] + ' ' + 'selected';
			$(this).attr("class", aClass);
			$('.productDetailImagesPart').show();
			$('.productParamterPart').show();

			// 去掉另一个的selected
			$('.productDetailTopReviewLink').attr("class", 'productDetailTopReviewLink');
		} else if (s[0] == 'productDetailTopReviewLink') {
			$('.productDetailImagesPart').hide();
			$('.productParamterPart').hide();
			var aClass = s[0] + ' ' + 'selected';
			$(this).attr("class", aClass);
			$('.productReviewContentPart').show();

			// 去掉另一个的selected
			$('.productDetailTopPartSelectedLink').attr("class", 'productDetailTopPartSelectedLink');
		}

	});
	// 监听立刻购买按钮，判断登录状态
	$('.buyNow').click(function() {
		$.get('front_checkLogin', function(data) {
			if (data == 'success') {
				var num = $('.productNumberSetting').val();
				location.href = $('.buyNow').attr('href') + "&num=" + num;

			} else {
				$('#loginModal').modal('show');
			}
		});
		return false;
	});
	// 监听加入购物车按钮，判断登录状态
	$('.addToCart').click(function() {
		$.get('front_checkLogin', function(data) {
			if (data == 'success') {
				var number = $('.productNumberSetting').val();
				var pid = $('.pid').val();
				$.get('front_addToCart', {
					"pid" : pid,
					"number" : number
				}, function(data) {
					if (data == 'success') {
						$('.addToCartButton').html('已加入购物车');
						$('.addToCartButton').attr("disabled", "disabled");
						$(".addCartButton").css("background-color", "lightgray");
						$(".addCartButton").css("border-color", "lightgray");
						$(".addCartButton").css("color", "black");
					} else {

					}
				});

			} else {
				$('#loginModal').modal('show');
			}
		});
		return false;
	});
})