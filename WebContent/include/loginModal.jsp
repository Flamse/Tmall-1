<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
/*模态登录窗口*/
div#loginModal {
	positon: absolute;
	top: 150px;
	left: 50%;
}

.errorDiv {
	text-align: center;
	background-color: #ff3300;
	dispaly: none;
}

span.loginInputIcon {
	width: 30px;
	height: 30px;
}
</style>
<script type="text/javascript">
	$(function() {
		//用户登录
		$("button.loginSubmitButton").click(function() {
			var name = $("#modal_name").val();
			var password = $("#modal_password").val();
			if (0 == name.length || 0 == password.length) {
				$("span.errorMessage").html("请输入账号密码");
				$("div.loginErrorMessageDiv").show();
				return false;
			}
			var page = "front_loginAjax";
			$.get(page, {
				"name" : name,
				"password" : password
			}, function(data) {
				if ("success" == data) {
					location.reload();
				} else {
					$("span.errorMessage").html("账号密码错误");
					$("div.loginErrorMessageDiv").show();
				}
			});
			return true;
		});
	});
</script>
</head>
<body>
	<!-- Modal -->
	<div id="loginModal" class="modal hide fade" style='width: 300px; margin-left: -140px;' tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class='errorDiv'>
			<span class='errorMessage'></span>
		</div>
		<div class="modal-body">
			<p>用户登录</p>
			<div class="loginInput " style='height:30px;border:none;'>
				<span class="loginInputIcon" style='width:30px;height:30px;'>
					<span class='icon icon-user'></span>
				</span>
				<input type="text" placeholder="手机/会员名/邮箱" name="name" id="modal_name">
			</div>
			<div class="loginInput " style='height:30px;border:none;'>
				<span class="loginInputIcon " style='width:30px;height:30px;'>
					<span class='icon icon-lock'></span>
				</span>
				<input type="password" placeholder="密码" name="password" id="modal_password">
			</div>
			<div>
				<a href="#nowhere" class="notImplementLink">忘记登录密码</a>
				<a class="pull-right" href="normal/regist.jsp">免费注册</a>
			</div>
			<div style="margin-top: 20px; text-align: center;">
				<button type="submit" class="loginSubmitButton redButton">登录</button>
			</div>
		</div>
		<div class="modal-footer">
			<button class=" btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	</div>
</body>
</html>