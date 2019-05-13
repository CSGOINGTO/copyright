
var userindex = {};
var allschoolHtml = "";// 全部学校html
var allSchoolJson = "";// 全部学校json
var hideSchoolList = null;
var allschoolHtmlTeacher = "";// 全部学校html
var allSchoolJsonTeacher = "";// 全部学校json
var hideSchoolListTeacher = null;
userindex.isShowSimplePass = true;
$(function(){
	
	// 点击发送验证码
	$("#btn-send-sms").click(function(){
		var mobile = $("#username").val() ;
		var msg = verifyMobile(mobile) ;
		if(msg) {
			alert(msg) ;
			return ;
		}
		// 发送验证码
		$.post("/user/send/" + mobile ,{captcha : "xxxx"} ,function(data){
			if(data.status && data.status == 1) {
				// 成功回调
				console.log('手机：%s，发送验证码：%s' ,mobile ,data.code) ;
			} else {
				// 失败回调
				console.log("failure ~") ;
			}
		}) ;
	}) ;
	
	// 提交注册表单
//	$("#btn-sign_up").click() ;
	$(".empty-ico").click(function(){
		$(this).prev().val("");
		$(this).hide();
	});
	//
	$("#revisePassword-div input").keyup(function(){
		var value = $.trim($(this).val());
		if(value !=null && value!=""){
			$(this).next().show();
		}else {
			$(this).next().hide();
		}
	});
	
}) ;

function sign_up_mobile_or_email(){
	
	var mobile = $("#rUsername").val();
	var code = "";
	//根据@符号判断是邮箱账号还是手机账号
	if(mobile.indexOf("@") == -1){
		code = $("#rMobileCode").val() ;
		if(code == null || code == "" || code == "undefined"){
			$("#form-ipt-error-r-mobile-code").addClass("is-visible");
			$("#form-ipt-error-r-mobile-code").html("请输入验证码");
			return false;
		}
		if(!code || !/^\d{4}$/.test(code)) {
			$("#form-ipt-error-r-mobile-code").addClass("is-visible");
			$("#form-ipt-error-r-mobile-code").html("请输入4位数字验证码");
			return false;
		}
	}else{
		code = $("#rEmailCode").val() ;
		if(code == null || code == "" || code == "undefined"){
			$("#form-ipt-error-r-email-code").addClass("is-visible");
			$("#form-ipt-error-r-email-code").html("请输入验证码");
			return false;
		}
		if(!code || !/^\d{4}$/.test(code)) {
			$("#form-ipt-error-r-email-code").addClass("is-visible");
			$("#form-ipt-error-r-email-code").html("请输入4位数字验证码");
			return false;
		}
	}
	var pwd = $("#rPassword").val();
	// 注册用户事件
	signUp(mobile ,pwd ,code ,function(){
		// 提交登录表单
//		console.log("success ...") ;
		var shref = location.href;
		if(shref.indexOf("mode=layer") > 0){
			$("#sign_up").attr("action" ,"https://passport.zhihuishu.com/login").submit() ;
			$("body" ,window.parent.document).append($("#sign_up").prop("outerHTML"));
			$("#sign_up" ,window.parent.document).hide() ;
			$("#rUsername" ,window.parent.document).val($("#rUsername").val());
			$("#rPassword" ,window.parent.document).val($("#rPassword").val());
			$("#sign_up" ,window.parent.document).submit();
		}else{
			$("#sign_up").attr("action" ,"/login?service=http://online.zhihuishu.com/onlineSchool").submit() ;
		}
	} ,function(status){
		// TODO 失败回调
		if(status == -2){
			if(mobile.indexOf("@") == -1){
				$("#form-ipt-error-r-mobile-code").addClass("is-visible");
				$("#form-ipt-error-r-mobile-code").html("验证码输入错误");
			}else{
				$("#form-ipt-error-r-email-code").addClass("is-visible");
				$("#form-ipt-error-r-email-code").html("验证码输入错误");
			}
			return false;
		}else{
			alert("服务异常，请稍后再试！");
			return false;
		}
	}) ;
	
}


function sign_up_mobile_or_email_pop(){
	
	var mobile = $("#rUsername").val();
	var code = "";
	//根据@符号判断是邮箱账号还是手机账号
	if(mobile.indexOf("@") == -1){
		code = $("#rMobileCodePop").val() ;
		if(code == null || code == "" || code == "undefined"){
			$("#form-ipt-error-mobile-code-pop").addClass("is-visible");
			$("#form-ipt-error-mobile-code-pop").html("请输入验证码");
			return false;
		}
		if(!code || !/^\d{4}$/.test(code)) {
			$("#form-ipt-error-mobile-code-pop").addClass("is-visible");
			$("#form-ipt-error-mobile-code-pop").html("请输入4位数字验证码");
			return false;
		}
	}else{
		code = $("#rEmailCodePop").val() ;
		if(code == null || code == "" || code == "undefined"){
			$("#form-ipt-error-email-code-pop").addClass("is-visible");
			$("#form-ipt-error-email-code-pop").html("请输入验证码");
			return false;
		}
		if(!code || !/^\d{4}$/.test(code)) {
			$("#form-ipt-error-email-code-pop").addClass("is-visible");
			$("#form-ipt-error-email-code-pop").html("请输入4位数字验证码");
			return false;
		}
	}
	var pwd = $("#rPassword").val();
	// 注册用户事件
	signUp(mobile ,pwd ,code ,function(){
		// 提交登录表单
//		console.log("success ...") ;
		$("#sign_up").attr("action" ,"https://passport.zhihuishu.com/login").submit() ;
		$("body" ,window.parent.document).append($("#sign_up").prop("outerHTML"));
		$("#sign_up" ,window.parent.document).hide() ;
		$("#rUsername" ,window.parent.document).val($("#rUsername").val());
		$("#rPassword" ,window.parent.document).val($("#rPassword").val());
		$("#sign_up" ,window.parent.document).submit();
	} ,function(status){
		// TODO 失败回调
		if(status == -2){
			if(mobile.indexOf("@") == -1){
				$("#form-ipt-error-mobile-code-pop").addClass("is-visible");
				$("#form-ipt-error-mobile-code-pop").html("验证码输入错误");
			}else{
				$("#form-ipt-error-email-code-pop").addClass("is-visible");
				$("#form-ipt-error-email-code-pop").html("验证码输入错误");
			}
			return false;
		}else{
			alert("服务异常，请稍后再试！");
			return false;
		}
	}) ;
	
}

/**
 * 注册逻辑，方法调用成功，执行成功回调
 * @param mobile
 * @param pwd
 * @param code
 * @param suc
 * @param fai
 */
function signUp(mobile ,pwd ,code ,suc ,fai) {
	
	console.log("注册信息：%s、%s、%s" ,mobile ,pwd ,code) ;
	$.post("/user/sign_up" ,{
		mobile : mobile ,
		password : pwd ,
		code : code
	} ,function(data){
		if(data.status && data.status == 1) {
			// 成功回调
			if(suc && typeof suc === 'function') suc() ;
		} else {
			// 失败回调
			if(fai && typeof fai === 'function') fai(data.status) ;
		}
	}) ;
}

/**
 * 验证手机号码，返回错误消息，消息空表示验证通过
 * @param mobile
 */
function verifyMobile(mobile) {
	if(!mobile) {
		return "手机号码不能为空" ;
	}
	if(!/^1\d{10}$/.test(mobile)) {
		return "手机号码格式不正确" ;
	}
	return null ;
}

/**
 * 获取图片验证码
 * @param imgId
 */
function getvalidateCode(imgId){
	var timenow = new Date().getTime();
	$("#"+imgId).attr("src", "/user/imageCaptcha?d=" + timenow);
}

/**
 * 注册验证账号输入是否正确
 */
function validateAccount(){
	var account = $("#rUsername").val();
	if(account == null || account == "" || account == "undefined"){
		$("#form-ipt-error-r-username").addClass("is-visible");
		$("#form-ipt-error-r-username").html("请填写手机号");
		return false;
	}
	var flag = 1;
	//根据@符号判断是邮箱账号还是手机账号
	if(account.indexOf("@") == -1){
		//手机账号
		if(!(/^1\d{10}$/.test(account))){
			$("#form-ipt-error-r-username").html("请填写正确的手机号");
			$("#form-ipt-error-r-username").addClass("is-visible");
			return false;
		}
	}else{
		//邮箱账号
		if(!/^\w+([-\.]\w+)*@\w+([\.-]\w+)*\.\w{2,4}$/.test(account)){
			$("#form-ipt-error-r-username").html("请填写正确的邮箱");
			$("#form-ipt-error-r-username").addClass("is-visible");
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
				$("#form-ipt-error-r-username").html("账号已被注册");
				$("#form-ipt-error-r-username").addClass("is-visible");
				isTrue = false;
			}else if(Number(msg) == 1){
				isTrue = true;
			}else{
				var info = "服务器异常，请稍后再试";
				alert(info);
				isTrue = false;
			}
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
 * 校验密码输入是否正确
 */
function validatePassword(passId){
	
	var password = $("."+passId).val();
	if(password == null || password == "" || password == "undefined"){
		$("#form-ipt-error-r-password").addClass("is-visible");
		$("#form-ipt-error-r-password").html("请填写密码");
		return false;
	}
	
	if(password.length < 6 || password.length > 40){
		$("#form-ipt-error-r-password").addClass("is-visible");
		$("#form-ipt-error-r-password").html("6-40字符组成");
		return false;
	}
	
	if(password == "123456"){
		$("#form-ipt-error-r-password").addClass("is-visible");
		$("#form-ipt-error-r-password").html("您输入的密码过于简单，请重新输入");
		return false;
	}
	$("#rPassword").val(password);
	return true;
}

/**
 * 注册校验图片验证码
 */
function validateImgCode(){
	var imgCode = $("#imgCode").val();
	if(imgCode == null || imgCode == "" || imgCode == "undefined"){
		$("#form-ipt-error-r-imgcode").addClass("is-visible");
		$("#form-ipt-error-r-imgcode").html("请填写验证码");
		return false;
	}
	
	if(imgCode.length != 4){
		$("#form-ipt-error-r-imgcode").addClass("is-visible");
		$("#form-ipt-error-r-imgcode").html("请填写4位验证码");
		return false;
	}
	return true;
}

function registerZHS(){
	var accountFlag = validateAccount();
	var cssDisplay = $(".ipt-password-show").css("display");
	var passFlag = false;
	if(cssDisplay == 'none'){
		passFlag = validatePassword('ipt-password-hide');
	}else{
		passFlag = validatePassword('ipt-password-show');
	}
	var codeFlag = validateImgCode();
	if(!accountFlag || !passFlag || !codeFlag){
		return false;
	}
	
	var username = $("#rUsername").val();
	var shref = location.href;
	//根据@符号判断是邮箱账号还是手机账号
	if(username.indexOf("@") == -1){
		if(shref.indexOf("mode=layer") > 0){
			registerZHSMobilePop();
		}else{
			registerZHSMobile();
		}
	}else{
		if(shref.indexOf("mode=layer") > 0){
			registerZHSEmailPop();
		}else{
			registerZHSEmail();
		}
	}
	
}

var mobileInterval = null;
var mobileTagTime = 60;
function registerZHSMobile(){
	
	var mobile = $("#rUsername").val();
	var captcha = $("#imgCode").val();
	
	mobileTagTime = 60;
	$(".send-msg-code-btn").removeAttr("onclick");
	if(mobileInterval != null && mobileInterval != 'undefined'){
		clearInterval(mobileInterval);
	}
	mobileInterval = setInterval(function() {
		mobileTagTime--;
		$(".send-msg-code-btn").html(mobileTagTime + "s 后重发");
		$(".send-msg-code-btn").removeAttr("onclick");
		if (mobileTagTime <= 0) {
			clearInterval(mobileInterval);
			$(".send-msg-code-btn").html("获取验证码");
			$(".send-msg-code-btn").attr("onclick", "javascript:sendSmsOpen();");
		}
	}, '1000');
	
	var flag = sendSms();
	if(!flag){
		clearInterval(mobileInterval);
		isTrue = false;
		return false;
	}
	
	$("#rAccountVi").val($("#rUsername").val());
	
	layer.open({
		title:false,
		type: 1,
		fixed: false,
		skin:"zhs-wall",
		shade:[0.65, '#00c896'],
		closeBtn:"1",
		content:$(".msg-code-ipt-pop"),
		area:['364px','300px'],
		cancel:function(){
			$("#rMobileCode").val("");
			$("#form-ipt-error-r-mobile-code").removeClass("is-visible");
		}
	})
	
}

//弹框
var mobileIntervalPop = null;
var mobileTagTimePop = 60;
function registerZHSMobilePop(){
	
	var mobile = $("#rUsername").val();
	
	mobileTagTimePop = 60;
	$("#send-msg-code-btn-pop").removeAttr("onclick");
	if(mobileIntervalPop != null && mobileIntervalPop != 'undefined'){
		clearInterval(mobileIntervalPop);
	}
	mobileIntervalPop = setInterval(function() {
		mobileTagTimePop--;
		$("#send-msg-code-btn-pop").html(mobileTagTimePop + "s 后重发");
		$("#send-msg-code-btn-pop").removeAttr("onclick");
		if (mobileTagTimePop <= 0) {
			clearInterval(mobileIntervalPop);
			$("#send-msg-code-btn-pop").html("获取验证码");
			$("#send-msg-code-btn-pop").attr("onclick", "javascript:sendSmsOpenPop();");
		}
	}, '1000');
	
	var flag = sendSms();
	if(!flag){
		clearInterval(mobileIntervalPop);
		isTrue = false;
		return false;
	}
	
	$("#rpopAccountVi").val($("#rUsername").val());
	$("#validate-telephone-overlay").addClass("active");
	
}

var emailInterval = null;
var emailTagTime = 60;
function registerZHSEmail(){
	
	var mobile = $("#rUsername").val();
	var captcha = $("#imgCode").val();
	
	emailTagTime = 60;
	$(".send-msg-email-code-btn").removeAttr("onclick");
	if(emailInterval != null && emailInterval != 'undefined'){
		clearInterval(emailInterval);
	}
	emailInterval = setInterval(function() {
		emailTagTime--;
		$(".send-msg-email-code-btn").html(emailTagTime + "s 后重发");
		$(".send-msg-email-code-btn").removeAttr("onclick");
		if (emailTagTime <= 0) {
			clearInterval(emailInterval);
			$(".send-msg-email-code-btn").html("获取验证码");
			$(".send-msg-email-code-btn").attr("onclick", "javascript:sendSmsOpenEmail();");
		}
	}, '1000');
	var isTrue = false;
	
	var flag = sendEmailCode();
	if(!flag){
		clearInterval(emailInterval);
		isTrue = false;
		return false;
	}
	
	$("#rAccountViEmail").val($("#rUsername").val());
	
	layer.open({
		title:false,
		type: 1,
		fixed: false,
		skin:"zhs-wall",
		shade:[0.65, '#00c896'],
		closeBtn:"1",
		content:$(".msg-code-ipt-pop-email"),
		area:['364px','300px'],
		cancel:function(){
			$("#rEmailCode").val("");
			$("#form-ipt-error-r-email-code").removeClass("is-visible");
		}
	})
}

var emailIntervalPop = null;
var emailTagTimePop = 60;
function registerZHSEmailPop(){
	
	var mobile = $("#rUsername").val();
	
	emailTagTimePop = 60;
	$("#send-msg-code-btn-pop-email").removeAttr("onclick");
	if(emailIntervalPop != null && emailIntervalPop != 'undefined'){
		clearInterval(emailIntervalPop);
	}
	emailIntervalPop = setInterval(function() {
		emailTagTimePop--;
		$("#send-msg-code-btn-pop-email").html(emailTagTimePop + "s 后重发");
		$("#send-msg-code-btn-pop-email").removeAttr("onclick");
		if (emailTagTimePop <= 0) {
			clearInterval(emailIntervalPop);
			$("#send-msg-code-btn-pop-email").html("获取验证码");
			$("#send-msg-code-btn-pop-email").attr("onclick", "javascript:sendSmsOpenEmailPop();");
		}
	}, '1000');
	var isTrue = false;
	
	var flag = sendEmailCode();
	if(!flag){
		clearInterval(emailIntervalPop);
		isTrue = false;
		return false;
	}
	
	$("#rpopEmailVi").val($("#rUsername").val());
	$("#validate-telephone-overlay-email").addClass("active");
	
}

function sendSmsOpenEmail(){
	
	var mobile = $("#rUsername").val();
	var captcha = $("#imgCode").val();
	
	emailTagTime = 60;
	$(".send-msg-email-code-btn").removeAttr("onclick");
	if(emailInterval != null && emailInterval != 'undefined'){
		clearInterval(emailInterval);
	}
	emailInterval = setInterval(function() {
		emailTagTime--;
		$(".send-msg-email-code-btn").html(emailTagTime + "s 后重发");
		$(".send-msg-email-code-btn").removeAttr("onclick");
		if (emailTagTime <= 0) {
			clearInterval(emailInterval);
			$(".send-msg-email-code-btn").html("获取验证码");
			$(".send-msg-email-code-btn").attr("onclick", "javascript:sendSmsOpenEmail();");
		}
	}, '1000');
	var isTrue = false;
	
	var flag = sendEmailCode();
	if(!flag){
		clearInterval(emailInterval);
		isTrue = false;
		return false;
	}
	
}

//弹框
function sendSmsOpenEmailPop(){
	
	var mobile = $("#rUsername").val();
	
	emailTagTimePop = 60;
	$("#send-msg-code-btn-pop-email").removeAttr("onclick");
	if(emailIntervalPop != null && emailIntervalPop != 'undefined'){
		clearInterval(emailIntervalPop);
	}
	emailIntervalPop = setInterval(function() {
		emailTagTimePop--;
		$("#send-msg-code-btn-pop-email").html(emailTagTimePop + "s 后重发");
		$("#send-msg-code-btn-pop-email").removeAttr("onclick");
		if (emailTagTimePop <= 0) {
			clearInterval(emailIntervalPop);
			$("#send-msg-code-btn-pop-email").html("获取验证码");
			$("#send-msg-code-btn-pop-email").attr("onclick", "javascript:sendSmsOpenEmailPop();");
		}
	}, '1000');
	var isTrue = false;
	
	var flag = sendEmailCode();
	if(!flag){
		clearInterval(emailIntervalPop);
		isTrue = false;
		return false;
	}
	
}


function sendSmsOpen(){
	var mobile = $("#rUsername").val();
	
	mobileTagTime = 60;
	$(".send-msg-code-btn").removeAttr("onclick");

	mobileInterval = setInterval(function() {
		mobileTagTime--;
		$(".send-msg-code-btn").html(mobileTagTime + "s 后重发");
		$(".send-msg-code-btn").removeAttr("onclick");
		if (mobileTagTime <= 0) {
			clearInterval(mobileInterval);
			$(".send-msg-code-btn").html("获取验证码");
			$(".send-msg-code-btn").attr("onclick", "javascript:sendSmsOpen();");
		}
	}, '1000');
	
	var flag = sendSms();
	if(!flag){
		clearInterval(mobileInterval);
		isTrue = false;
		return false;
	}
}

//弹框
function sendSmsOpenPop(){
	var mobile = $("#rUsername").val();
	
	mobileTagTimePop = 60;
	$("#send-msg-code-btn-pop").removeAttr("onclick");

	mobileIntervalPop = setInterval(function() {
		mobileTagTimePop--;
		$("#send-msg-code-btn-pop").html(mobileTagTimePop + "s 后重发");
		$("#send-msg-code-btn-pop").removeAttr("onclick");
		if (mobileTagTimePop <= 0) {
			clearInterval(mobileIntervalPop);
			$("#send-msg-code-btn-pop").html("获取验证码");
			$("#send-msg-code-btn-pop").attr("onclick", "javascript:sendSmsOpenPop();");
		}
	}, '1000');
	
	var flag = sendSms();
	if(!flag){
		clearInterval(mobileIntervalPop);
		isTrue = false;
		return false;
	}
}

function popCloseValidateMobile(id){
	$("#"+id).removeClass("active");
	$("#rMobileCodePop").val("");
	$("#form-ipt-error-mobile-code-pop").removeClass("is-visible");
	$("#rEmailCodePop").val("");
	$("#form-ipt-error-email-code-pop").removeClass("is-visible");
}


/**
 * 发送短信验证码
 */
function sendSms(){
	
	var mobile = $("#rUsername").val();
	var captcha = $("#imgCode").val();
	
	var isTrue = false;
	$.ajax({  
        url : "/user/send/" + mobile,
        data:{captcha : captcha},
        async : false, // 注意此处需要同步  
        type : "POST",  
        dataType : "json",  
        success : function(data) {  
        	if(data.status && data.status == 1) {
    			// 成功回调
    			//console.log('手机：%s，发送验证码：%s' ,mobile ,data.code) ;
        		isTrue = true;
    		}else if(data.status == -2){
    			$("#form-ipt-error-r-imgcode").addClass("is-visible");
    			$("#form-ipt-error-r-imgcode").html("验证码错误");
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
function sendEmailCode(){
	
	var email = $("#rUsername").val();
	var captcha = $("#imgCode").val();
	
	var isTrue = false;
	$.ajax({  
        url : "/user/sendEmail",
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
    			$("#form-ipt-error-r-imgcode").addClass("is-visible");
    			$("#form-ipt-error-r-imgcode").html("验证码错误");
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
 * 隐藏提示错误信息
 * @param labId
 */
function hideErrorInfo(labId){
	$("#"+labId).removeClass("is-visible");
}
/**
 * 过滤关键字和非法字符
 * 
 */
function inpuFilter(arr,btnEle){
    for(var i = 0;i < arr.length;i++){
        // console.log(arr[i]);
        var text = arr[i];
        var pattern = new RegExp("[`~!#$^&*()=|{}':;',\\[\\]<>/?~！#￥……&*（）——|/{}【】‘；：”“'。，、？%]");
        var vkeyWords = /and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|or/i;
        var result = text.match(pattern);
        var title=text;
        if(vkeyWords.test(title) && result){
            alert('请勿非法输入'+ vkeyWords.exec(title)[0] +result);
            return false;
        };
        if(vkeyWords.test(title) || result){
            if(vkeyWords.exec(title) == null){
                alert("请勿非法输入"+ result);
            }else if(result == null){
                alert('请勿非法输入'+ vkeyWords.exec(title)[0]);
            }
            return false;
        }
    }
}

/**
 * 登录
 */
function formSignUp(){
	var shref = location.href;
	if(shref.indexOf("#studentID") > 0){
		//学号登录
		var clCode = $.trim($("#clCode").val());
		var clCodeFlag = true;
		var clPasswordFlag = true;
		if(clCode == null || clCode == ""){
			$("#form-ipt-error-cl-code").addClass("is-visible");
			$("#form-ipt-error-cl-code").html("请填写学号");
			clCodeFlag = false;
		}
		var clpassword = $.trim($("#clPassword").val());
		if(clpassword == null || clpassword == ""){
			$("#form-ipt-error-cl-password").addClass("is-visible");
			$("#form-ipt-error-cl-password").html("请填写密码");
			clPasswordFlag = false;
		}
		var clSchoolId = $.trim($("#clSchoolId").val());
		var schoolName = $("#quickSearch").val();
		if(schoolName == null || schoolName == "" || schoolName == "undefined" || clSchoolId == null || clSchoolId == "" || clSchoolId == 'undefined'){
			$("#form-ipt-error-c-school").addClass("is-visible");
			$("#form-ipt-error-c-school").html("请填写学校");
			return false;
		}
		inpuFilter([clCode,clpassword,schoolName],$(this));
		if(!clCodeFlag || !clPasswordFlag){
			return false;
		}
		
		hideErrorInfo('form-ipt-error-cl-code');
		var tpwdErrorShow = $("#tpwdErrorShow").val()
		var tcaptcha = $("#j-captcha-code").val();
		if(tpwdErrorShow != null && tpwdErrorShow != ""){
			if(tcaptcha == null || tcaptcha == ""){
				$("#form-ipt-code-img-error").addClass("is-visible");
				$("#form-ipt-code-img-error").html("请填写验证码");
				return false;
			}
		}
		
		
		$.ajax({  
	        url : "/user/validateCodeAndPassword",
	        data:{code : clCode,password : clpassword,schoolId:clSchoolId ,captcha : tcaptcha},
	        type : "POST",  
	        dataType : "json",  
	        success : function(data) {
	        	if(data == null || data == ""){
	        		//程序异常
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("未知异常");
	    			return false;
	        	}
	        	if(data.status == 1) {
	        		var service = getQueryString("service");
	        		if(service == null || service == "" || service == "undefined"){
	        			window.location = "https://passport.zhihuishu.com/login?pwd=" + data.pwd;
	        		}else{
	        			window.location = "https://passport.zhihuishu.com/login?pwd=" + data.pwd + "&service="+service;
	        		}
	        		return true;
	    		}else if(data.status == -1){
	    			//参数异常
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("账号或密码错误");
	    			return false;
	    		}else if(data.status == -2){
	    			//程序异常
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("未知异常");
	    			return false;
	    		}else if(data.status == -3){
	    			//用户不存在
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("用户不存在");
	    			return false;
	    		}else if(data.status == -4){
	    			//学号或密码错误
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("账号或密码错误");
	    			var pwdError = data.pwdErrorCount;
	    			if(pwdError != null && pwdError != "" && pwdError != "undefined"){
	    				if(parseInt(pwdError) >= 3){
	    					$("#tPwdError").show();
	    					$("#tpwdErrorShow").val("1");
	    					getvalidateCode('j-captcha-img-tcode');
	    				}
	    			}
	    			getvalidateCode('j-captcha-img-code');
	    			return false;
	    		}else if(data.status == -5){
	    			//无导入信息
//	    			$("#sjzxErrorInfo1").addClass("blur");
	    			
	    			if(clSchoolId == 134){
	    				$("#wclRealNameMatch").val("");
	    				//无导入信息输入姓名
	    				layer.open({
	    					title:false,
	    					type: 1,
	    					fixed: false,
	    			        closeBtn:"1",
	    					skin:"zhs-wall",
	    					shade:[0.65, '#00c896'],
	    					content:$(".nameMatch-pop-w"),
	    					area:'305px',
	    				})
	    			}else{
	    				layer.open({
		    				title:false,
		    				type: 1,
		    				fixed: false,
		    		        closeBtn:"1",
		    				skin:"zhs-wall",
		    				shade:[0.65, '#00c896'],
		    				content:$(".notImport-pop"),
		    				area:'400px',
		    			});
	    			}
	    			return false;
	    		}else if(data.status == -6){
	    			//有导入信息验证姓名
	    			layer.open({
						title:false,
						type: 1,
						fixed: false,
	                    closeBtn:"1",
						skin:"zhs-wall",
						shade:[0.65, '#00c896'],
						content:$(".nameMatch-pop"),
						area:'305px',
					})
					$("#clRealNameMatch").val("");
	    			$("#nameMatchErrorShow").removeClass("is-visible");
	    			$("#clRealNameMatch").removeClass("notMatch-sty-bc");
	    			$("#clRealNameMatch").removeClass("notMatch-sty-bg");
					var realName = data.realname;
	    			$("#clRealName").val(realName);
	    			realName = realName.substring(1);
					$("#realnameMatch").html(realName);
	    			return false;
	    		}else if(data.status == -7){
	    			$("#chFlag").val("2");
	    			$("#chPwd").val(data.pwd);
	    			$("#chService").val("&service=" + getQueryString("service"));
	    			$("#nameMatchForward").submit();
	    			return false;
	    		}else if(data.status == -8){
	    			$("#sjzxErrorInfo").addClass("blur");
	    			return false;
	    		}else if(data.status == -9){
	    			$("#form-ipt-code-img-error").addClass("is-visible");
					$("#form-ipt-code-img-error").html("验证码错误");
	    			return false;
	    		}else if(data.status == -10){
	    			layer.open({
						title:false,
						type: 1,
						fixed: false,
	                    closeBtn:"1",
						skin:"zhs-wall",
						shade:[0.65, '#00c896'],
						content:$(".infoTips-pop-t"),
						area:'305px',
					})
	    			return false;
	    		}else{
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("账号或密码错误");
	    			return false;
	    		}
	        }  
	    });
		return ;
	}
	if(shref.indexOf("#teacherID") > 0){
		//教工号登录
		//学号登录
		var tlCode = $.trim($("#tlCode").val());
		var tlCodeFlag = true;
		var tlPasswordFlag = true;
		if(tlCode == null || tlCode == ""){
			$("#form-ipt-error-tl-code").addClass("is-visible");
			$("#form-ipt-error-tl-code").html("请填写教工号");
			tlCodeFlag = false;
		}
		var tlpassword = $.trim($("#tlPassword").val());
		if(tlpassword == null || tlpassword == ""){
			$("#form-ipt-error-tl-password").addClass("is-visible");
			$("#form-ipt-error-tl-password").html("请填写密码");
			tlPasswordFlag = false;
		}
		var tlSchoolId = $.trim($("#tlSchoolId").val());
		var schoolName = $("#quickSearchTeacher").val();
		if(schoolName == null || schoolName == "" || schoolName == "undefined" || tlSchoolId == null || tlSchoolId == "" || tlSchoolId == 'undefined'){
//			$("#certifyNext").removeClass("disable");
//			$("#certifyNext").attr("onclick","userCertify.certifyNext();");form-ipt-error-c-school
			$("#form-ipt-error-t-school").addClass("is-visible");
			$("#form-ipt-error-t-school").html("请填写学校");
			return false;
		}
		inpuFilter([tlCode,tlpassword,schoolName],$(this));
		if(!tlCodeFlag || !tlPasswordFlag){
			return false;
		}
		hideErrorInfo('form-ipt-error-tl-code');
		
		var tcpwdErrorShow = $("#tcpwdErrorShow").val();
		var captcha = $("#j-captcha-tcode").val();
		if(tcpwdErrorShow != null && tcpwdErrorShow != ""){
			if(captcha == null || captcha == ""){
				$("#form-ipt-tcode-img-error").addClass("is-visible");
				$("#form-ipt-tcode-img-error").html("请填写验证码");
				return false;
			}
		}
		
		$.ajax({  
	        url : "/user/validateCodeAndPasswordTeacher",
	        data:{code : tlCode,password : tlpassword,schoolId:tlSchoolId ,captcha : captcha},
	        type : "POST",  
	        dataType : "json",  
	        success : function(data) {
	        	if(data == null || data == ""){
	        		//程序异常
	    			$("#form-ipt-error-tl-code").addClass("is-visible");
	    			$("#form-ipt-error-tl-code").html("未知异常");
	    			return false;
	        	}
	        	if(data.status == 1) {
	        		var service = getQueryString("service");
	        		if(service == null || service == "" || service == "undefined"){
	        			window.location = "https://passport.zhihuishu.com/login?pwd=" + data.pwd;
	        		}else{
	        			window.location = "https://passport.zhihuishu.com/login?pwd=" + data.pwd + "&service="+service;
	        		}
	        		return true;
	    		}else if(data.status == -1){
	    			//参数异常
	    			$("#form-ipt-error-tl-code").addClass("is-visible");
	    			$("#form-ipt-error-tl-code").html("账号或密码错误");
	    			return false;
	    		}else if(data.status == -2){
	    			//程序异常
	    			$("#form-ipt-error-tl-code").addClass("is-visible");
	    			$("#form-ipt-error-tl-code").html("未知异常");
	    			return false;
	    		}else if(data.status == -3){
	    			//用户不存在
	    			$("#form-ipt-error-tl-code").addClass("is-visible");
	    			$("#form-ipt-error-tl-code").html("用户不存在");
	    			return false;
	    		}else if(data.status == -4){
	    			//教工号或密码错误
	    			$("#form-ipt-error-tl-code").addClass("is-visible");
	    			$("#form-ipt-error-tl-code").html("账号或密码错误");
	    			var pwdError = data.pwdErrorCount;
	    			if(pwdError != null && pwdError != "" && pwdError != "undefined"){
	    				if(parseInt(pwdError) >= 3){
	    					getvalidateCode('j-captcha-img-tcode');
	    					$("#tcPwdError").show();
	    					$("#tcpwdErrorShow").val("1");
	    				}
	    			}
	    			return false;
	    		}else if(data.status == -7){
	    			if("123456" == tlpassword){
	    				$("#chInitPwd").val("1");
	    			}
	    			$("#chFlag").val("2");
	    			$("#chLoginType").val("teacher");
	    			$("#chPwd").val(data.pwd);
	    			$("#chService").val("&service=" + getQueryString("service"));
	    			$("#nameMatchForward").submit();
	    			return false;
	    		}else if(data.status == -8){
	    			$("#form-ipt-tcode-img-error").addClass("is-visible");
					$("#form-ipt-tcode-img-error").html("验证码错误");
					return false;
	    		}else{
	    			$("#form-ipt-error-cl-code").addClass("is-visible");
	    			$("#form-ipt-error-cl-code").html("账号或密码错误");
	    			return false;
	    		}
	        }
		});
		
		return ;
	}

	

	var username = $.trim($("#lUsername").val());
	var usernameFlag = true;
	var passwordFlag = true;
	if(username == null || username == ""){
		$("#form-ipt-error-l-username").addClass("is-visible");
		$("#form-ipt-error-l-username").html("请填写手机号或邮箱");
		usernameFlag = false;
	}
	var password = $.trim($("#lPassword").val());
	if(password == null || password == ""){
		$("#form-ipt-error-l-password").addClass("is-visible");
		$("#form-ipt-error-l-password").html("请填写密码");
		passwordFlag = false;
	}
	inpuFilter([username,password],$(this));
	if(!usernameFlag || !passwordFlag){
		return false;
	}
	hideErrorInfo('form-ipt-error-l-username');
	var lpwdErrorShow = $("#lpwdErrorShow").val();
	var captcha = $("#j-captcha-mobile").val();
	if(lpwdErrorShow != null && lpwdErrorShow != ""){
		if(captcha == null || captcha == ""){
			$("#form-ipt-mobile-img-error").addClass("is-visible");
			$("#form-ipt-mobile-img-error").html("请填写验证码");
			return false;
		}
	}
	
	$.ajax({  
        url : "/user/validateAccountAndPassword",
        data:{account : username,password : password, captcha : captcha},
        type : "POST",  
        dataType : "json",  
        success : function(data) {  
        	if(data.status && data.status == 1) {
        		if(shref.indexOf("mode=layer") > 0){
        			$("body" ,window.parent.document).append($("#f_sign_up").prop("outerHTML"));
        			$("#f_sign_up" ,window.parent.document).hide() ;
        			$("#lUsername" ,window.parent.document).val($("#lUsername").val());
        			$("#lPassword" ,window.parent.document).val($("#lPassword").val());
        			$("#f_sign_up" ,window.parent.document).submit();
        		}else{
        			$("#lUsername").val(username);
        			$("#lPassword").val(password)
        			$("#f_sign_up").submit();
        		}
//        		submitSignUp(shref);
        		return ;
    		}else if(data.status && data.status == -5){
    			showSimplePass();
    		}else if(data.status == -4){
    			$("#form-ipt-mobile-img-error").addClass("is-visible");
    			$("#form-ipt-mobile-img-error").html("验证码错误");
    			return false;
    		}else{
    			var count = data.pwdErrorCount;
    			if(count != null && count != "" && count != "undefined"){
    				if(parseInt(count) >= 3){
    					$("#j-captcha-mobile").val("");
    					$("#lPwdError").show();
    					$("#lpwdErrorShow").val("1");
    					getvalidateCode('j-captcha-img');
    				}
    			}
    			$("#form-ipt-error-l-username").addClass("is-visible");
				$("#form-ipt-error-l-username").html("账号或密码错误");
    			return false;
    		}
        }  
    });
	
}
/**
 * 提交登录form
 */
function submitSignUp(shref){
	layer.closeAll();
	var shref = shref||location.href;
	if(shref.indexOf("mode=layer") > 0){
		$("body" ,window.parent.document).append($("#f_sign_up").prop("outerHTML"));
		$("#f_sign_up" ,window.parent.document).hide() ;
		$("#lUsername" ,window.parent.document).val($("#lUsername").val());
		$("#lPassword" ,window.parent.document).val($("#lPassword").val());
		$("#f_sign_up" ,window.parent.document).submit();
	}else{
//		$("#lUsername").val(username);
//		$("#lPassword").val(password)
		$("#f_sign_up").submit();
	}
}
/**
 * 显示密码过于简单
 */
function showSimplePass() {
	//layer.closeAll();
	if(userindex.isShowSimplePass) {
		
		layer.open({
			title:false,
			type: 1,
			fixed: false,
			skin:"zhs-wall",
			shade:[0.65, '#00c896'],
			closeBtn:"1",
			content:$("#simplePassword-div"),
			area:['364px'],
			success:function(){
				//$(".layui-layer-content").css("height","auto");
				userindex.isShowSimplePass = false;
			},
			cancel:function(){
				userindex.isShowSimplePass = true;
			}
		})
	}
}
/**
 * 显示修改密码弹窗
 */
function showModifySimplePass() {
	layer.closeAll();
	userindex.isShowSimplePass = true;
	clearModifyPass();
	layer.open({
		title:false,
		type: 1,
		fixed: false,
		skin:"zhs-wall",
		shade:[0.65, '#00c896'],
		closeBtn:"1",
		content:$("#revisePassword-div"),
		area:['364px'],
		success:function(){
			//$(".layui-layer-content").css("height","auto");
		},
		cancel:function(){
//			$(".error-password").html(""); //清空错误提示
			clearModifyPass();
		}
	})
}
/**
 * 校验密码输入是否正确
 */
userindex.validateModifyPassword = function(passId){
	
	var password = $("#"+passId).val();
	if(password == null || password == "" || password == "undefined"){
//		$("#form-ipt-error-find-password").addClass("is-visible");
		$(".error-password").html("请填写新密码");
		return false;
	}
	
	if(password.length < 6 || password.length > 16){
//		$("#form-ipt-error-find-password").addClass("is-visible");
		$(".error-password").html("密码6-16字符组成");
		return false;
	}
	if(!/^[a-zA-Z0-9]{6,16}$/.test(password)){
//		$("#form-ipt-error-find-password").addClass("is-visible");
		$(".error-password").html("密码仅支持数字或英文");
		return false;
	}

	
	return true;
}
function clearModifyPass(){
	$("#oldPassword").val("");
	$("#newPassword").val("");
	$("#newPasswordOnce").val("");
	$(".error-password").html(""); //清空错误提示
}
/**
 * 修改密码
 */
function modifyPassword(){
	//验证填写原密码
	var password = $("#oldPassword").val();
	if(password == null || password == "" || password == "undefined"){
//		$("#form-ipt-error-find-password").addClass("is-visible");
		$(".error-password").html("请填写原密码");
		return false;
	}
	//验证新密码是否合法
	if(userindex.validateModifyPassword("newPassword") &&
			userindex.validateModifyPassword("newPasswordOnce")) {
		//验证两次新密码是否相同
		var newPass = $.trim($("#newPassword").val());
		var newPassOnce = $.trim($("#newPasswordOnce").val());
		if(newPass != newPassOnce){
			$(".error-password").html("对不起，您输入的新密码不一致");
			return false;
		}
		if(newPass == password){
			$(".error-password").html("修改后密码不能与原密码相同");
			return false;
		}
	}
	var username = $.trim($("#lUsername").val());
	//var password = $.trim($("#lPassword").val());
	//保存新密码
	$.ajax({  
        url : "/user/modifySimplePass",
        data:{account : username,password : password,newPassword:newPass,newPasswordOnce:newPassOnce},
        type : "POST",  
        dataType : "json",  
        success : function(data) {  
        	//-1参数错误，-2账号密码错误，-3账号密码错误、 -4验证码错误、-5密码过于简单、-6新密码和原密码相同、-7其它异常  1成功
        	if(data.status && data.status == 1) {
//        		if(shref.indexOf("mode=layer") > 0){
//        			$("body" ,window.parent.document).append($("#f_sign_up").prop("outerHTML"));
//        			$("#f_sign_up" ,window.parent.document).hide() ;
//        			$("#lUsername" ,window.parent.document).val($("#lUsername").val());
//        			$("#lPassword" ,window.parent.document).val($("#lPassword").val());
//        			$("#f_sign_up" ,window.parent.document).submit();
//        		}else{
//        			$("#lUsername").val(username);
//        			$("#lPassword").val(password)
//        			$("#f_sign_up").submit();
//        		}
        		$("#lPassword").val(newPass);//新密码
        		var shref = location.href;
        		submitSignUp(shref);
        		return ;
    		}else if(data.status && data.status == -1){
    			$(".error-password").html("参数错误");
    		}else if(data.status == -2 || data.status == -3){
    			$(".error-password").html("对不起，您输入的旧密码错误");
    			return false;
    		}else if(data.status && data.status == -4){ //暂时没加验证码验证
    			$(".error-password").html("验证码错误");
    		}else if(data.status && data.status == -5){ 
    			$(".error-password").html("新密码过于简单");
    		}else if(data.status && data.status == -6){ 
    			$(".error-password").html("修改后密码不能与原密码相同");
    		}else{
    			$(".error-password").html("其它异常");
    			return false;
    		}
        }  
    });
}
userindex.selectAllSchoolByName = function(){
	var param = {};
	param.key = "";
	$("#schoolListCode").html("");
	$.ajax({
		url : "/user/getAllSchool?date=" + new Date(),
		async : false,
		type : "POST",
		dataType : "json",
		success : function(data) {
			allschoolHtml = "";
			var list = data.listSchool;
			if(list != null && list != 'undefined'){
				allSchoolJson = list;
				$.each(list, function(i, n) {
					var name = n.name;
					var id = n.schoolId;
					
					if (param.key) {
						name = name.replaceAll(param.key, "<b><font color='red'>" + param.key + "</font></b>");
					}
					allschoolHtml += "<li value='" + id + "' >" + name + "</li>";
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载学校列表失败，请稍后再试！");
			return false;
		}

	});
};

userindex.selectAllSchoolByNameTeacher = function(){
	var param = {};
	param.key = "";
	$("#schoolListTeacher").html("");
	$.ajax({
		url : "/user/getAllSchool?date=" + new Date(),
		async : false,
		type : "POST",
		dataType : "json",
		success : function(data) {
			allschoolHtmlTeacher = "";
			var list = data.listSchool;
			if(list != null && list != 'undefined'){
				allSchoolJsonTeacher = list;
				$.each(list, function(i, n) {
					var name = n.name;
					var id = n.schoolId;
					
					if (param.key) {
						name = name.replaceAll(param.key, "<b><font color='red'>" + param.key + "</font></b>");
					}
					allschoolHtmlTeacher += "<li value='" + id + "' >" + name + "</li>";
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载学校列表失败，请稍后再试！");
			return false;
		}

	});
};


userindex.hideLabel = function(){
	$("#schoolSearchHolderCode").hide();
	$("#quickSearch").focus();
    $("#validataSchoolListDropCode").fadeIn(300);
    $("#nanoCode").nanoScroller({alwaysVisible: true});
	userindex.selectSchoolByName();
	$("#quickSearch").focus();
}

userindex.hideLabelTeacher = function(){
	$("#schoolSearchHolderTeacher").hide();
	$("#quickSearchTeacher").focus();
    $("#validataSchoolListDropTeacher").fadeIn(300);
    $("#nanoTeacher").nanoScroller({alwaysVisible: true});
	userindex.selectSchoolByNameTeacher();
	$("#quickSearchTeacher").focus();
}

/**
 * 学校模糊查询
 */
userindex.selectSchoolByName = function() {
	$("#clSchoolId").val("");
	var param = {};
	param.key = $.trim($("#quickSearch").val());
	$("#schoolListCode").html("");
	if (param.key) {

		var list = userindex.schoolNameRetrieval(param.key);
		if (list == null || list.length == 0) {
			$("#validataSchoolListDropCode").show();
			$("#clSchoolId").val("");
			$("#schoolListCode").html("<li>未找到相关数据！</li>").show();
			return;
		}
		var html = "";
		$.each(list, function(i, n) {
			var name = n.name;
			var id = n.schoolId;
			var isShow=true;
			
			if (param.key) {
				name = name.replaceAll(param.key, "<b><font color='red'>" + param.key + "</font></b>");
			}
			html += "<li value='" + id + "' slogo='"+n.image+"'>" + name + "</li>";
			
			$("#validataSchoolListDropCode").show();
			$("#schoolListCode").html(html);
			$("#validataSchoolListDropCode").slideDown(300);
			$("#nanoCode").nanoScroller({alwaysVisible: true});
			$("#clSchoolId").val("");
			$("#schoolListCode li").click(function() {
				userindex.selectSchool($(this));
			});
		});
	} else {
		if (!allschoolHtml) {
			userindex.selectAllSchoolByName();
		}
		var html = allschoolHtml;
		$("#validataSchoolListDropCode").show();
		$("#schoolListCode").html(html);
		$("#validataSchoolListDropCode").slideDown(300);
		$("#nanoCode").nanoScroller({alwaysVisible: true});
		$("#clSchoolId").val("");
		$("#schoolListCode li").click(function() {
			userindex.selectSchool($(this));
		});
	}
};

/**
 * 学校模糊查询
 */
userindex.selectSchoolByNameTeacher = function() {
	$("#tlSchoolId").val("");
	var param = {};
	param.key = $.trim($("#quickSearchTeacher").val());
	$("#schoolListTeacher").html("");
	if (param.key) {

		var list = userindex.schoolNameRetrievalTeacher(param.key);
		if (list == null || list.length == 0) {
			$("#validataSchoolListDropTeacher").show();
			$("#tlSchoolId").val("");
			$("#schoolListTeacher").html("<li>未找到相关数据！</li>").show();
			return;
		}
		var html = "";
		$.each(list, function(i, n) {
			var name = n.name;
			var id = n.schoolId;
			var isShow=true;
			
			if (param.key) {
				name = name.replaceAll(param.key, "<b><font color='red'>" + param.key + "</font></b>");
			}
			html += "<li value='" + id + "' slogo='"+n.image+"'>" + name + "</li>";
			
			$("#validataSchoolListDropTeacher").show();
			$("#schoolListTeacher").html(html);
			$("#validataSchoolListDropTeacher").slideDown(300);
			$("#nanoTeacher").nanoScroller({alwaysVisible: true});
			$("#tlSchoolId").val("");
			$("#schoolListTeacher li").click(function() {
				userindex.selectSchoolTeacher($(this));
			});
		});
	} else {
		if (!allschoolHtmlTeacher) {
			userindex.selectAllSchoolByNameTeacher();
		}
		var html = allschoolHtmlTeacher;
		$("#validataSchoolListDropTeacher").show();
		$("#schoolListTeacher").html(html);
		$("#validataSchoolListDropTeacher").slideDown(300);
		$("#nanoTeacher").nanoScroller({alwaysVisible: true});
		$("#tlSchoolId").val("");
		$("#schoolListTeacher li").click(function() {
			userindex.selectSchoolTeacher($(this));
		});
	}
};

/**
 * 根据关键字搜索学校
 */
userindex.schoolNameRetrieval = function(keyword) {
	var schoolByNameJson = [];
	if (allSchoolJson) {

	} else {
		userindex.selectAllSchoolByName();
	}
	if (keyword) {
		$.each(allSchoolJson, function(i, n) {
			var name = n.name;
			if (name.indexOf(keyword) >= 0) {
				schoolByNameJson.push(n);
			}
		});
		return schoolByNameJson;
	} else {
		return allSchoolJson;
	}
};

/**
 * 根据关键字搜索学校
 */
userindex.schoolNameRetrievalTeacher = function(keyword) {
	var schoolByNameJson = [];
	if (allSchoolJsonTeacher) {

	} else {
		userindex.selectAllSchoolByNameTeacher();
	}
	if (keyword) {
		$.each(allSchoolJsonTeacher, function(i, n) {
			var name = n.name;
			if (name.indexOf(keyword) >= 0) {
				schoolByNameJson.push(n);
			}
		});
		return schoolByNameJson;
	} else {
		return allSchoolJsonTeacher;
	}
};

/**
 * 点击选择学校
 * 
 * @param obj
 * @return
 */
userindex.selectSchool = function(obj) {
	$("#schoolSearchHolderCode").hide();
	$("#quickSearch").val(obj.text());
	$("#clSchoolId").val(obj.val());
	var img = obj.attr("slogo");
	if(img == null || img == "" || img == "undefined" || img == "null"){
	}else{
//		$("#schoolLogo").attr("src",img);
	}
	$("#quickSearch").focus();
	$("#validataSchoolListDropCode").hide();
//	userindex.validateSchool();
}

/**
 * 点击选择学校
 * 
 * @param obj
 * @return
 */
userindex.selectSchoolTeacher = function(obj) {
	$("#schoolSearchHolderTeacher").hide();
	$("#quickSearchTeacher").val(obj.text());
	$("#tlSchoolId").val(obj.val());
	var img = obj.attr("slogo");
	if(img == null || img == "" || img == "undefined" || img == "null"){
	}else{
//		$("#schoolLogo").attr("src",img);
	}
	$("#quickSearchTeacher").focus();
	$("#validataSchoolListDropTeacher").hide();
//	userindex.validateSchool();
}

/**
 * 关键字加深
 * 
 * @param s1
 * @param s2
 * @return
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "ig"), s2);
};


userindex.cancelerrorpop = function(){
	$("#sjzxErrorInfo").removeClass("blur");
};



userindex.nameMatch = function(){
	var clRealNameMatch = $.trim($("#clRealNameMatch").val());
	if(clRealNameMatch == null || clRealNameMatch == ""){
		$("#nameMatchErrorShow").html("请填写姓名");
		$("#nameMatchErrorShow").addClass("is-visible");
		$("#clRealNameMatch").removeClass("notMatch-sty-bg");
		$("#clRealNameMatch").addClass("notMatch-sty-bc");
		return false;
	}
//	$("#nameMatchErrorShow").html("");
	$("#nameMatchErrorShow").removeClass("is-visible");
	$("#clRealNameMatch").removeClass("notMatch-sty-bc");
	$("#clRealNameMatch").removeClass("notMatch-sty-bg");
	var realname = $.trim($("#clRealName").val());
	var realNameMatch = clRealNameMatch + $.trim($("#realnameMatch").html());
	if(realname == realNameMatch){
		//验证成功
		$("#chRealName").val(realname);
		$("#chCode").val($.trim($("#clCode").val()));
		$("#chSchoolId").val($.trim($("#clSchoolId").val()));
		$("#chFlag").val("1");
		$("#nameMatchForward").submit();
	}else{
		//验证失败
		$("#nameMatchErrorShow").html("姓名和账户不匹配");
		$("#nameMatchErrorShow").addClass("is-visible");
		$("#clRealNameMatch").removeClass("notMatch-sty-bc");
		$("#clRealNameMatch").addClass("notMatch-sty-bg");
	}
		
}

userindex.forwardRegister = function (){
	
	window.location = "http://user.zhihuishu.com/zhsuser/register";
	
}

userindex.backSignin = function(){
	$("#qSignin").click();
}

userindex.clearErrorNameMatch = function(){
	$("#nameMatchErrorShow").removeClass("is-visible");
	$("#clRealNameMatch").removeClass("notMatch-sty-bc");
	$("#clRealNameMatch").removeClass("notMatch-sty-bg");
}


function getQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


userindex.toPerfectInfo = function (){
//	$("#sjzxErrorInfo1").removeClass("blur");
	layer.closeAll();
	$("#wclRealNameMatch").val("");
	//无导入信息输入姓名
	layer.open({
		title:false,
		type: 1,
		fixed: false,
        closeBtn:"1",
		skin:"zhs-wall",
		shade:[0.65, '#00c896'],
		content:$(".nameMatch-pop-w"),
		area:'305px',
	})
};

userindex.nameMatchW = function(){
	var wclRealNameMatch = $.trim($("#wclRealNameMatch").val());
	if(wclRealNameMatch == null || wclRealNameMatch == ""){
		$("#nameMatchErrorShowW").html("请填写姓名");
		$("#nameMatchErrorShowW").addClass("is-visible");
		$("#wclRealNameMatch").addClass("notMatch-sty-bc");
		return false;
	}
	
	var schoolId = $.trim($("#clSchoolId").val());
	if(schoolId == 134){
		var realname = encodeURI(wclRealNameMatch);
		//验证北大是否匹配
		$.ajax({
			url : "/user/validateBeida?schoolId=" + schoolId + "&code=" + $.trim($("#clCode").val()) + "&realname=" + realname,
			async : false,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.status == -1 || data.status == -2){
					//程序异常
	    			$("#nameMatchErrorShowW").addClass("is-visible");
	    			$("#nameMatchErrorShowW").html("未知异常");
	    			return false;
				}else if(data.status == -3){
					//不匹配
					layer.open({
    					title:false,
    					type: 1,
    					fixed: false,
    			        closeBtn:"1",
    					skin:"zhs-wall",
    					shade:[0.65, '#00c896'],
    					content:$(".infoTips-pop-t"),
    					area:'305px',
    				})
    				return false;
				}else if(data.status == 1){
					$("#chRealName").val(wclRealNameMatch);
					$("#chCode").val($.trim($("#clCode").val()));
					$("#chSchoolId").val(schoolId);
					$("#chFlag").val("1");
					$("#nameMatchForward").submit();
				}else{
					//程序异常
	    			$("#nameMatchErrorShowW").addClass("is-visible");
	    			$("#nameMatchErrorShowW").html("未知异常");
	    			return false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("服务异常，请稍后再试！");
				return false;
			}

		});
	}else{
		$("#chRealName").val(wclRealNameMatch);
		$("#chCode").val($.trim($("#clCode").val()));
		$("#chSchoolId").val(schoolId);
		$("#chFlag").val("1");
		$("#nameMatchForward").submit();
	}

	
}


userindex.clearErrorNameMatchW = function (){
	$("#nameMatchErrorShowW").removeClass("is-visible");
	$("#wclRealNameMatch").removeClass("notMatch-sty-bc");
};

userindex.clearTeacherLoginText = function(){
	$("#quickSearchTeacher").val("");
	hideErrorInfo('form-ipt-error-t-school');
	$("#schoolSearchHolderTeacher").show();
	$("#tlCode").val("");
	hideErrorInfo('form-ipt-error-tl-code');
	$("#tlPassword").val("");
	hideErrorInfo('form-ipt-error-tl-password');
	$("#j-captcha-tcode").val("");
	hideErrorInfo('form-ipt-tcode-img-error');
}

userindex.closeShujuliandong = function (){
	layer.closeAll();
};


