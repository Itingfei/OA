/**
 * Created by yangjie on 2016/8/16.
 */
$(document).ready(function() {
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
            orgName: {
                required : true
            }
        }
    }
    // 数据验证和提交
    validateAndSubmit("commentForm", validator, success);
    // 保存按钮事件
    $("#btnSave").click(function(event) {
        $("#commentForm").submit();
    });
    // 保存按钮事件
    $("#cancel").click(function(event) {
        closeModal();
    });
});