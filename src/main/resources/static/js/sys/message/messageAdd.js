/**
 * Created by yangjie on 2016/8/16.
 */
$(document).ready(function() {
    var config = {
        ".chosen-select": {},
        ".chosen-select-deselect": {
            allow_single_deselect: !0
        },
        ".chosen-select-no-single": {
            disable_search_threshold: 10
        },
        ".chosen-select-no-results": {
            no_results_text: "Oops, nothing found!"
        },
        ".chosen-select-width": {
            width: "95%"
        }
    };
    for (var selector in config) $(selector).chosen(config[selector]);
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
            title: {
                required : true
            },
            content: {
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
    // 取消按钮事件
    $("#cancel").click(function(event) {
        closeModal();
    });
    // $(".messageType").change(function () {
    //     if($(this).val()=="0"){ //流程表单消息
    //         $(".categorydashed").show();
    //         $(".categoryDiv").show();
    //     }else{
    //         $(".categorydashed").hide();
    //         $(".categoryDiv").hide();
    //     }
    // })
});