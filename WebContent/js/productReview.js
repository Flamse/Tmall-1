$(function(){
	
	$('.addReviewButton').on("click",function(){
		var Empty = true;
		//遍历每一个商品评价块
		$('.reviewContent').each(function(){
			Empty = checkEmpty($(this).attr('id'), "评价内容");
			if(!Empty){
				return false;
			}
			var pid = $(this).attr('pid');
			var content = $('.reviewContent[pid='+pid+']').val();
			$.post("front_addReview",{"pid":pid,"content":content},function(result){
				if("success"==result){
					var num = $('.reviewStasticsNumber[pid='+pid+']').text();
					num = Number(num);
					$('.reviewStasticsNumber[pid='+pid+']').text(num+1);
					$('.makeReviewDiv[pid='+pid+']').hide();
					$('.reviewSuccess[pid='+pid+']').show();
				}
			});
		});
		if(!Empty){
			return false;
		}
		//全都评价完后改变订单状态
		var oid = $(this).attr("oid");
		$.post("front_reviewDone",{"oid":oid},function(result){
			if("success"==result){
				location.href='front_myOrder';
			}
		});
		
	})
	
	
	
	
})
