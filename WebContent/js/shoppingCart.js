$(function() {
	function calculatePrice() {
		var oiid = $('.cartProductItemSmallSumPrice').attr('oiid');
		$("tr.cartProductItemTR[oiid=" + oiid + "]")

	}
	// 格式化金额
	function formatprice(price) {
		// 去除数字和小数点以外的东西,解析为浮点数
		var number = parseFloat((price + "").replace(/[^\d\.]/g, ''));
		// 判断是否为NaN
		if (isNaN(number)) {
			number = 0;
		}
		// 取绝对值并四舍五入两位小数
		number = Math.abs(number).toFixed(2);
		// 按小数点分割成两部分
		var s = (number + "").split(".");
		// 将整数部分分割并翻转顺序
		var is = s[0].split("").reverse();
		// 小数部分
		var j = s[1];

		// 每隔3个数字插入一个逗号
		var t = '';
		for (var i = 0; i < is.length; i++) {
			// 如果下一个是第4位且本位非最后一个，则本次添加数字后额外添加一个逗号，否则只添加数字
			if ((i + 1) % 3 == 0 && (i + 1) != is.length) {
				t += is[i] + ",";
			} else {
				t += is[i];
			}

		}
		// 将以上字符串分割、翻转、join转化为字符串并接上小数部分。
		t = t.split('').reverse().join('') + '.' + j;
		return t;
	}

	// 结算按钮切换
	function syncCreateOrderButton() {
		var isAnySelected = false;
		// 遍历所有商品行的选择标志
		$('.cartProductItemIfSelected').each(function() {
			if ('selectit' == $(this).attr('selectit')) {
				isAnySelected = true;
			}
			if (isAnySelected) {
				$('button.createOrderButton').removeAttr('disabled');
				$('button.createOrderButton').css('background-color', '#c40000');
				return;
			} else {
				$('button.createOrderButton').attr('disabled', 'disabled');
				$('button.createOrderButton').css('background-color', '#AAAAAA');
			}
		});
	}
	// 全选状态
	function isAllSelected() {
		var isAllSelected = true;
		$('.cartProductItemIfSelected').each(function() {
			if ('false' == $(this).attr('selectit')) {
				isAllSelected = false;
			}
		});
		if (isAllSelected) {
			$('img.selectAllItem').attr('src', 'http://how2j.cn/tmall/img/site/cartSelected.png');
			return true;
		} else {
			$("img.selectAllItem").attr("src", "http://how2j.cn/tmall/img/site/cartNotSelected.png");
			return false;
		}
	}
	// 计算所有订单项的商品总数量与价格
	function productNumAndPrice() {

		var sum = 0;
		var totalprice = 0;
		$("img.cartProductItemIfSelected[selectit='selectit']").each(function() {
			var oiid = $(this).attr('oiid');
			// 获取商品数量
			productsum = parseInt($("input.orderItemNumberSetting[oiid=" + oiid + "]").val());
			// 获取商品单价
			var price = parseFloat($("tr.cartProductItemTR[oiid=" + oiid + "] span.orderItemPromotePrice").text());
			// 计算并更新结算总价
			sum += productsum;
			totalprice += productsum * price;
		});
		var totalPirce = formatprice(totalprice);
		$('span.cartSumPrice').text("￥" + totalPirce);
		$('span.cartTitlePrice').text("￥" + totalPirce);
		$('span.cartSumNumber').text(sum);
	}
	// 计算单品数量与价格
	function calcSingleNumAndPrice() {
		var oiid;
		var price;
		var productsum;
		var singleTotalPrice;
		$("input.orderItemNumberSetting").each(function() {
			oiid = $(this).attr('oiid');
			// 获取商品数量
			productsum = parseInt($(this).val());
			// 获取商品单价
			price = parseFloat($("tr.cartProductItemTR[oiid=" + oiid + "] span.orderItemPromotePrice").text());
			// 计算并更新商品总价
			singleTotalPrice = formatprice(price * productsum);
			$(".cartProductItemSmallSumPrice[oiid=" + oiid + "]").text("￥" + singleTotalPrice);
		});
	}
	// 页面加载时计算一次
	calcSingleNumAndPrice();

	// 勾选订单项时触发
	$('.cartProductItemIfSelected').click(function() {
		var selectit = $(this).attr("selectit");
		var oiid = $(this).attr('oiid');
		// 如果已经选中，切换
		if ("selectit" == selectit) {
			$(this).attr("src", "http://how2j.cn/tmall/img/site/cartNotSelected.png");
			$(this).attr("selectit", "false");
			$('tr.cartProductItemTR[oiid=' + oiid + ']').css("background-color", "#fff");
			// 获取所有父元素，括号内为从得到的父元素中筛选
			// $(this).parents("tr.cartProductItemTR").css("background-color",
			// "#fff");
		} else {
			$(this).attr("src", "http://how2j.cn/tmall/img/site/cartSelected.png");
			$(this).attr("selectit", "selectit");
			$('tr.cartProductItemTR[oiid=' + oiid + ']').css("background-color", "#FFF8E1");
			// $(this).parents("tr.cartProductItemTR").css("background-color",
			// "#FFF8E1");
		}
		isAllSelected();
		productNumAndPrice();
		syncCreateOrderButton();

	})
	// 全选操作
	$('.selectAllItem').click(function() {
		// 利用前面的是否全选功能判断要全选还是取消全选
		var alreadyAllSelect = isAllSelected();
		if (!alreadyAllSelect) {// 没有全选--全选
			$('.cartProductItemIfSelected').each(function() {
				$(this).attr('selectit', 'selectit');
				$(this).attr("src", "http://how2j.cn/tmall/img/site/cartSelected.png");
				var oiid = $(this).attr('oiid');
				$('tr.cartProductItemTR[oiid=' + oiid + ']').css("background-color", "#FFF8E1");
				// $(this).parents("tr.cartProductItemTR").css("background-color",
				// "#FFF8E1");
			});
			// 切换全选图片
			$('img.selectAllItem').attr('src', 'http://how2j.cn/tmall/img/site/cartSelected.png');
		} else {// 已经全选--取消全选
			$('.cartProductItemIfSelected').each(function() {
				$(this).attr('selectit', 'false');
				$(this).attr("src", "http://how2j.cn/tmall/img/site/cartNotSelected.png");
				var oiid = $(this).attr('oiid');
				$('tr.cartProductItemTR[oiid=' + oiid + ']').css("background-color", "#fff");
				// $(this).parents("tr.cartProductItemTR").css("background-color",
				// "#fff");
			});
			$("img.selectAllItem").attr("src", "http://how2j.cn/tmall/img/site/cartNotSelected.png");
		}
		productNumAndPrice();// 同步数量与价格
		syncCreateOrderButton();// 结算块激活
	});

	// 产品数量调整--增加
	$('.numberPlus').click(function() {
		var pid = $(this).attr('pid');
		var stock = parseInt($(this).attr('stock'));

		var sum = parseInt($(".orderItemNumberSetting[pid=" + pid + "]").val()) + 1;
		if (sum > stock) {
			$(".orderItemNumberSetting[pid=" + pid + "]").val(stock);
		} else {
			$(".orderItemNumberSetting[pid=" + pid + "]").val(sum)
		}
		// 修改数量
		var oiid = $(this).attr('oiid');
		$.post('front_updateOrderItem', {
			"oiid" : oiid,
			"sum" : sum
		}, function(result) {
			if ('success' != result) {
				location.href = 'front_home';
			}
		});
		calcSingleNumAndPrice();// 更新产品总价
		productNumAndPrice();// 更新订单总数与金额
		syncCreateOrderButton();// 结算块激活
	})
	// 产品数量调整--减少
	$('.numberMinus').click(function() {
		var pid = $(this).attr('pid');
		var sum = parseInt($(".orderItemNumberSetting[pid=" + pid + "]").val()) - 1;
		if (sum <= 0) {
			sum = 1;
			$(".orderItemNumberSetting[pid=" + pid + "]").val(sum);
		} else {
			$(".orderItemNumberSetting[pid=" + pid + "]").val(sum)
		}
		// 修改数量
		var oiid = $(this).attr('oiid');
		$.post('front_updateOrderItem', {
			"oiid" : oiid,
			"sum" : sum
		}, function(result) {
			if ('success' != result) {
				location.href = 'front_home';
			}
		});
		calcSingleNumAndPrice();// 更新产品总价
		productNumAndPrice();// 更新订单总数与金额
		syncCreateOrderButton();// 结算块激活
	})
	// 直接输入产品数量
	$(".orderItemNumberSetting").keyup(function() {

		var sum = parseInt($(this).val());
		var pid = $(this).attr('pid');
		var stock = parseInt($("span.orderItemStock[pid=" + pid + "]").text());
		if (isNaN(sum) || sum <= 0) {
			sum = 1;
		} else if (sum > stock) {
			sum = stock;
		}
		$(this).val(sum);
		// 修改数量
		var oiid = $(this).attr('oiid');
		$.post('front_updateOrderItem', {
			"oiid" : oiid,
			"sum" : sum
		}, function(result) {
			if ('success' != result) {
				location.href = 'front_home';
			}
		});
		calcSingleNumAndPrice();// 更新产品总价
		productNumAndPrice();// 更新订单总数与金额
		syncCreateOrderButton();// 结算块激活
	});

	function deleteOrderItem() {
		if (confirm("确定删除？")) {
			var oiid = $(this).attr('oiid');
			$.post('front_deleteOrderItem', {
				"oiid" : oiid
			}, function(result) {
				if ("success" == result) {
					$('tr.cartProductItemTR[oiid=' + oiid + ']').fadeOut();
				}
			});
		}
		return false;
	}
	// 删除订单项
	$('.deleteOrderItem').on({
		click : deleteOrderItem,
	});

	// 结算，生成订单
	$(".createOrderButton").on("click", function() {

		var oiids = new Array();
		$('.cartProductItemIfSelected[selectit=selectit]').each(function() {
			var oiid = $(this).attr('oiid');
			oiids.push(oiid);
		})
		oiids = oiids.join();
		console.log(oiids);

		$('.oiids').val(oiids);
		alert(oiids);
	})
})
