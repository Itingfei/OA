/**
 * Created by yangjie on 2016/8/13.
 */
$(document).ready(function() {
    //成功后处理
    function success(resp) {
        if (resp.code != '0') {
            showMsg(resp.msg);
        } else {
            closeAndRefresh(resp.msg);
        }
    }
    var validator={
        rules:{
            name: {
                required : true
            },
            href: {
                required : true
            },
            sorts: {
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

});