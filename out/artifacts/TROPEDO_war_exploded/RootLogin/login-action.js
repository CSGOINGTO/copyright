/**
 * Created by mac on 2018/7/7.
 */
function mylogin() {
    var username = document.getElementById("lUsername");
    var password = document.getElementById("lPassword");

    var json = {};
    json.username = username.value;
    json.password = password.value;
    var JsonStr = JSON.stringify(json);

    var xhr = new XMLHttpRequest();
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
                alert('失败');
            }
        },
        error:function(data){
            alert(data.result + "woqu");
        }
    })
}