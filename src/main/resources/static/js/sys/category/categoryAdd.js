/**
 * Created by yangjie on 2016/8/19.
 */
$(document).ready(function() {
    //成功后处理
    function success(resp) {
        if (resp.code != '0') {
            showToastMsg(resp.msg,2);
        } else {
            showToastMsg(resp.msg,1);
            closeAndRedirect("/category","流程表单列表");
        }
    }
    function closeAndRedirect(url,newtabname) {
        parent.refreshByhref("/workflow/deploystart");
        parent.opentab(url,newtabname,1);
       
    }
    var validator={
        rules:{
            title: {
                required : true
            },
            purpose:{
                required : true
            },
            reason:{
                required : true
            },
            itemName:{
                required : true
            },
            needcount:{
                required : true,
                digits:true
            },
            model:{
                required : true
            },
            applyPrice:{
                required : true
            },
            classifyId:{
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
var deleteIdArr = [];
function removeItem(obj) {
    $(obj).parents('.cateitem').remove();
    $("#itemSize").val(parseInt($("#itemSize").val())-1);
    var deleteId = $(obj).attr("data-id");
    if(deleteId!=undefined)
         deleteIdArr.push(deleteId);
    $(".deleteItemId").val(deleteIdArr)
}