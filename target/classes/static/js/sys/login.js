/**
 * Created by yangjie on 2016/8/22.
 */
$(function () {
    //成功后处理
    function success(resp) {
        if (resp.code != '0') {
            showToastMsg(resp.msg,2);
        } else {
            closeAndRefresh(resp.msg,"selectFrom");
        }
    }
    var validator={
        rules:{
            username: {
                required : true
            },
            password: {
                required : true
            }
        }
    }
    // 数据验证和提交
    validateAndSubmit("commentForm", validator, success);
    if (window != top){
        if(window.parent == top) {
            // 主页面
            console.log("main");
            alertMsg("用户已过期", function(index){
                top.location.href = location.href;
            });
        } else {
            // 弹出页
            console.log("pop");
            alertMsg("用户已过期", function(index){
                closeModal();
            });
        }
    }
});