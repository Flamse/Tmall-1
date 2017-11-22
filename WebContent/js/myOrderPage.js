$(function() {
	$("div.orderType div a").click(function() {
		var orderstatus = $(this).attr('orderstatus');
		$('.orderListItemTable').each(function() {
			if ('all' == orderstatus) {
				$(this).show();
			} else if (orderstatus == $(this).attr('orderstatus')) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
		$('.orderType div').each(function() {
			if ('orderTypeLastOne' == $(this).attr('class')) {

			} else {
				$(this).attr('class', '');
			}

		});
		$(this).parent().attr('class', 'selectedOrderType');
	});
	$('.deleteOrderLink').on('click', function() {
		if (confirm("确认删除订单？")) {
			var oid = $(this).attr('oid');
			$.get("front_deleteOrder", {
				"oid" : oid
			}, function(result) {
				if ("success" == result) {
					$("table.orderListItemTable[oid=" + oid + "]").fadeOut();
				}
			});
		}
		return false;
	});
})
