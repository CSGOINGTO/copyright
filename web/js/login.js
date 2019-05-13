var isTest = true;
var reg_phone=/^[1][3,4,5,7,8][0-9]{9}$/;
var serverPath = "localhost:8080";
function onLoginClicked() {
    var box_username = document.getElementById("username");
    var box_password = document.getElementById("password");
    var username = box_username.value;
    var password = box_password.value;
    if (!reg_phone.test(username) && isTest == false) {
        alert("请输入正确的手机号码！");
        box_username.value = "";
    } else {
        // VOID
    }
    if (password.length < 6){
        alert("请输入正确密码！");
        box_password.value = "";
    }

    var json = {};
    json.username = username;
    json.password = password;
    var JsonStr = JSON.stringify(json);
    console.log(JsonStr);
    $.ajax({
        async:false,
        type:"post",
        contentType: "application/json; charset=utf-8",
        url:"http://localhost:8080/designer_login_action.action",
        data:JsonStr,
        dataType:"json",

        success:function(data){
            if(data.res_code == 0){
                alert('成功' + data.res_str);
            }else{
                alert('失败' + data.res_str);
            }
        },
        error:function(data){
            alert(data.result + "woqu");
        }
    })
}