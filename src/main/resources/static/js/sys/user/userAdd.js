/**
 * Created by yangjie on 2016/8/19.
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
            userName: {
                required : true
            },
            password: {
                required : true
            },
            realName: {
                required : true
            },
            linkPhone: {
                required : true,
                isMobile : true
            },
            email: {
                required : true
            },
            gender : {
                required:true
            }
            // ,
            // orgId : {
            //     required:true
            // },
            // positionId : {
            //     required:true
            // }
        }
    }
    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        /*     var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; */
        /* var mobile=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d$)/; */
        var mobile = /^[\d-]*$/;
        return this.optional(element) || (mobile.test(value));
    }, "请正确填写您的电话号码");
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
    $(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
    $("#orgId").change(function () {
        getPositionByOrgId($(this).val(),"")
    });
    if ($("#editPositionId").val()!="" && $("#editOrgId").val()!=""){
        getPositionByOrgId($("#editOrgId").val(),$("#editPositionId").val());
    }
});
function getPositionByOrgId(orgId,positionId) {
    $.getJSON("/organization/getPositionByOrgId", {
        orgId :orgId
    }, function(json) {
        if (json.positionList != undefined) {
            createPositionSelect(json.positionList,positionId);
        }else{
            $('#positionId').empty();
            var firstOption = $("<option></option>");
            firstOption.attr("value","");
            firstOption.text("请选择职位");
            $("#positionId").prepend(firstOption);
            $(".chosen-select").trigger('chosen:updated');
        }
    });
}
function createPositionSelect(positionList,editPositionId) {
    $('#positionId').empty();
    $(".chosen-select").trigger('chosen:updated');
    var firstOption = $("<option></option>");
    firstOption.attr("value","");
    firstOption.text("请选择职位");
    $("#positionId").prepend(firstOption);
    $.each(positionList, function(i, item) {
        var $option = $("<option></option>");
        if (editPositionId!="" && editPositionId==item.positionId){
            $option.attr("selected","selected");
        }
        $option.attr("value",item.positionId);
        $option.text(item.positionName);
        $('#positionId').append($option);
    });
    $(".chosen-select").trigger('chosen:updated');
}