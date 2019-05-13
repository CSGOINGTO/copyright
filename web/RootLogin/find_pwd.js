var findpwd = {};

/**
 * 找回密码验证账号输入是否正确
 */
findpwd.validateAccount = function(){
	var account = $("#findUsername").val();
	if(account == null || account == "" || account == "undefined"){
		$("#form-ipt-error-find").addClass("is-visible");
		$("#form-ipt-error-find").html("请填写手机号或邮箱");
		return false;
	}
	var flag = 1;
	//根据@符号判断是邮箱账号还是手机账号
	if(account.indexOf("@") == -1){
		//手机账号
		if(!(/^1\d{10}$/.test(account))){
			$("#form-ipt-error-find").html("请填写正确的手机号或邮箱");
			$("#form-ipt-error-find").addClass("is-visible");
			return false;
		}
	}else{
		//邮箱账号
		if(!/^\w+([-\.]\w+)*@\w+([\.-]\w+)*\.\w{2,4}$/.test(account)){
			$("#form-ipt-error-find").html("请填写正确的手机号或邮箱");
			$("#form-ipt-error-find").addClass("is-visible");
			return false;
		}
		flag = 2;
	}
	var isTrue = false;
	$.ajax( {
		url : '/user/validateMobileIsExists',
		async : false,
		type : "POST",
		dataType : "json",
		data : {
			"account" : account,
			"flag" : flag
		},
		success : function(data) {
			var msg = data.status;
			if(Number(msg) == -2){
				isTrue = true;
			}else if(Number(msg) == 1){
				$("#form-ipt-error-find").html("该账号尚未注册智慧树");
				$("#form-ipt-error-find").addClass("is-visible");
				isTrue = false;
			}else{
				var info = "服务器异常，请稍后再试";
				alert(info);
				isTrue = false;
			}
//			isTrue = true;
		},
		error : function(data) {
			var info = "服务器异常，请稍后再试";
			alert(info);
			isTrue = false;
		}
	});
	
	return isTrue;
}

/**
 * 注册校验图片验证码
 */
findpwd.validateImgCode = function(){
	var imgCode = $("#findImgCode").val();
	if(imgCode == null || imgCode == "" || imgCode == "undefined"){
		$("#form-ipt-error-code-find").addClass("is-visible");
		$("#form-ipt-error-code-find").html("请填写验证码");
		return false;
	}
	
	if(imgCode.length != 4){
		$("#form-ipt-error-code-find").addClass("is-visible");
		$("#form-ipt-error-code-find").html("请填写4位验证码");
		return false;
	}
	return true;
}


findpwd.sendMsg = function(){
	
	var accountFlag = findpwd.validateAccount();
	
	var codeFlag = findpwd.validateImgCode();
	if(!accountFlag || !codeFlag){
		return false;
	}
	
	var username = $("#findUsername").val();
	var isTrue = false;
	//根据@符号判断是邮箱账号还是手机账号
	if(username.indexOf("@") == -1){
		isTrue = findpwd.sendMsgMobile();
	}else{
		isTrue = findpwd.sendMsgEmail();
	}
	if(isTrue){
		$(".find-msg-code-ipt-pop").hide();
		$(".find-msg-code-ipt-pop").eq(1).show();
	}
	
}

var mobileIntervalFind = null;
var mobileTagTimeFind = 60;
findpwd.sendMsgMobile = function(){
	var mobile = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	mobileTagTimeFind = 60;
	$("#send-msg-code-btn-find").removeAttr("onclick");
	if(mobileIntervalFind != null && mobileIntervalFind != 'undefined'){
		clearInterval(mobileIntervalFind);
	}
	mobileIntervalFind = setInterval(function() {
		mobileTagTimeFind--;
		$("#send-msg-code-btn-find").html(mobileTagTimeFind + "s 后重发");
		$("#send-msg-code-btn-find").removeAttr("onclick");
		if (mobileTagTimeFind <= 0) {
			clearInterval(mobileIntervalFind);
			$("#send-msg-code-btn-find").html("获取验证码");
			$("#send-msg-code-btn-find").attr("onclick", "javascript:findpwd.sendSmsOpen();");
		}
	}, '1000');
	
	var flag = findpwd.sendSms();
	if(!flag){
		clearInterval(mobileIntervalFind);
		isTrue = false;
		return false;
	}
	
	$("#fUsernamePop").val($("#findUsername").val());
	
	$(".find-msg-code-ipt-pop").hide();
	$(".find-msg-code-ipt-pop").eq(1).show();
	
}

findpwd.sendSmsOpen = function(){
	var mobile = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	mobileTagTimeFind = 60;
	$("#send-msg-code-btn-find").removeAttr("onclick");
	if(mobileIntervalFind != null && mobileIntervalFind != 'undefined'){
		clearInterval(mobileIntervalFind);
	}
	mobileIntervalFind = setInterval(function() {
		mobileTagTimeFind--;
		$("#send-msg-code-btn-find").html(mobileTagTimeFind + "s 后重发");
		$("#send-msg-code-btn-find").removeAttr("onclick");
		if (mobileTagTimeFind <= 0) {
			clearInterval(mobileIntervalFind);
			$("#send-msg-code-btn-find").html("获取验证码");
			$("#send-msg-code-btn-find").attr("onclick", "javascript:sendSmsOpen();");
		}
	}, '1000');
	
	var flag = findpwd.sendSms();
	if(!flag){
		clearInterval(mobileIntervalFind);
		isTrue = false;
		return false;
	}
	return true;

}

var emailIntervalFind = null;
var emailTagTimeFind = 60;
findpwd.sendMsgEmail = function(){
	var mobile = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	emailTagTimeFind = 60;
	$("#send-msg-code-btn-find").removeAttr("onclick");
	if(emailIntervalFind != null && emailIntervalFind != 'undefined'){
		clearInterval(emailIntervalFind);
	}
	emailIntervalFind = setInterval(function() {
		emailTagTimeFind--;
		$("#send-msg-code-btn-find").html(emailTagTimeFind + "s 后重发");
		$("#send-msg-code-btn-find").removeAttr("onclick");
		if (emailTagTimeFind <= 0) {
			clearInterval(emailIntervalFind);
			$("#send-msg-code-btn-find").html("获取验证码");
			$("#send-msg-code-btn-find").attr("onclick", "javascript:findpwd.sendSmsOpenEmail();");
		}
	}, '1000');
	
	var flag = findpwd.sendEmailCode();
	if(!flag){
		clearInterval(emailInterval);
		isTrue = false;
		return false;
	}
	
	$("#fUsernamePop").val($("#findUsername").val());
	
	$(".find-msg-code-ipt-pop").hide();
	$(".find-msg-code-ipt-pop").eq(1).show();
}

findpwd.sendSmsOpenEmail = function(){
	var mobile = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	emailTagTimeFind = 60;
	$("#send-msg-code-btn-find").removeAttr("onclick");
	if(emailIntervalFind != null && emailIntervalFind != 'undefined'){
		clearInterval(emailIntervalFind);
	}
	emailIntervalFind = setInterval(function() {
		emailTagTimeFind--;
		$("#send-msg-code-btn-find").html(emailTagTimeFind + "s 后重发");
		$("#send-msg-code-btn-find").removeAttr("onclick");
		if (emailTagTimeFind <= 0) {
			clearInterval(emailIntervalFind);
			$("#send-msg-code-btn-find").html("获取验证码");
			$("#send-msg-code-btn-find").attr("onclick", "javascript:findpwd.sendSmsOpenEmail();");
		}
	}, '1000');
	
	var flag = findpwd.sendEmailCode();
	if(!flag){
		clearInterval(emailInterval);
		isTrue = false;
		return false;
	}
	
	return true;
}

/**
 * 发送短信验证码
 */
findpwd.sendSms = function(){
	
	var mobile = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	var isTrue = false;
	$.ajax({  
        url : "/user/send/" + mobile,
        data:{captcha : captcha},
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",  
        dataType : "json",  
        success : function(data) {  
        	if(data.status && data.status == 1) {
    			// 成功回调
    			//console.log('手机：%s，发送验证码：%s' ,mobile ,data.code) ;
        		isTrue = true;
    		}else if(data.status == -2){
    			$("#form-ipt-error-code-find").addClass("is-visible");
    			$("#form-ipt-error-code-find").html("验证码填写错误");
    			isTrue = false;
    		}else if(data.status == -4){
    			var info = "对不起，当天手机号只能获取" + 5 + "次短信码，重新获取，请更换手机号";
				alert(info);
				isTrue = false;
    		}else {
    			alert("短信码发送失败，请稍后再试！");
    			isTrue = false;
    		}
        }  
    });
	return isTrue;
}

/**
 * 发送邮箱验证码
 */
findpwd.sendEmailCode = function(){
	
	var email = $("#findUsername").val();
	var captcha = $("#findImgCode").val();
	
	var isTrue = false;
	$.ajax({  
        url : "/user/findPwdSendEmal",
        data:{captcha : captcha,email : email},
        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
        type : "POST",  
        dataType : "json",  
        success : function(data) {  
        	if(data.status && data.status == 1) {
    			// 成功回调
    			//console.log('手机：%s，发送验证码：%s' ,mobile ,data.code) ;
        		isTrue = true;
    		}else if(data.status == -2){
    			$("#form-ipt-error-code-find").addClass("is-visible");
    			$("#form-ipt-error-code-find").html("验证码填写错误");
    			isTrue = false;
    		}else {
    			alert("邮件验证码发送失败，请稍后再试！");
    			isTrue = false;
    		}
        }  
    });
	return isTrue;
}


/**
 * 校验密码输入是否正确
 */
findpwd.validatePassword = function(passId){
	
	var password = $("#"+passId).val();
	if(password == null || password == "" || password == "undefined"){
		$("#form-ipt-error-find-password").addClass("is-visible");
		$("#form-ipt-error-find-password").html("请填写密码");
		return false;
	}
	
	if(!/^[a-zA-Z0-9]{6,16}$/.test(password)){
		$("#form-ipt-error-find-password").addClass("is-visible");
		$("#form-ipt-error-find-password").html("密码仅支持数字或英文");
		return false;
	}
	
	if(password.length < 6 || password.length > 16){
		$("#form-ipt-error-find-password").addClass("is-visible");
		$("#form-ipt-error-find-password").html("6-16字符组成");
		return false;
	}
	
	if(password == "123456"){
		$("#form-ipt-error-find-password").addClass("is-visible");
		$("#form-ipt-error-find-password").html("您输入的密码过于简单，请重新输入");
		return false;
	}
	$("#findPassword").val(password);
	return true;
}

/**
 * 验证手机验证码或邮箱验证码
 */
findpwd.validateMobileCode = function(){
	var code = $("#findMobileCode").val() ;
	if(code == null || code == "" || code == "undefined"){
		$("#form-ipt-error-find-mobile-code").addClass("is-visible");
		$("#form-ipt-error-find-mobile-code").html("请填写验证码");
		return false;
	}
	if(!code || !/^\d{4}$/.test(code)) {
		$("#form-ipt-error-find-mobile-code").addClass("is-visible");
		$("#form-ipt-error-find-mobile-code").html("请输入4位数字验证码");
		return false;
	}
	return true;
}


/**
 * 重设密码
 */
findpwd.resetPassword = function(){
	var account = $("#findUsername").val();
	var code = $("#findMobileCode").val() ;
	
	var cssDisplay = $("#ipt-password-show").css("display");
	var passFlag = false;
	if(cssDisplay == 'none'){
		passFlag = findpwd.validatePassword('ipt-password-hide');
	}else{
		passFlag = findpwd.validatePassword('ipt-password-show');
	}
	
	var codeFlag = findpwd.validateMobileCode();
	if(!passFlag || !codeFlag){
		return false;
	}
	
	
	var pwd = $("#findPassword").val();
	
	$.ajax( {
		url : '/user/resetPassword',
		async : false,
		type : "POST",
		dataType : "json",
		data : {
			"account" : account,
			"code" : code,
			"password" : pwd
		},
		success : function(data) {
			var msg = data.status;
			if(Number(msg) == -2){
				$("#form-ipt-error-find-mobile-code").html("验证码填写错误");
				$("#form-ipt-error-find-mobile-code").addClass("is-visible");
				isTrue = false;
			}else if(Number(msg) == 1){
				isTrue = true;
//				window.location.href = "http://passport.zhihuishu.com/login?mode=original#signin";
				window.location.href = "https://passport.zhihuishu.com/login?mode=original";
			}else{
				var info = "服务器异常，请稍后再试";
				alert(info);
				isTrue = false;
			}
			isTrue = true;
		},
		error : function(data) {
			var info = "服务器异常，请稍后再试";
			alert(info);
			isTrue = false;
		}
	});
	
}
