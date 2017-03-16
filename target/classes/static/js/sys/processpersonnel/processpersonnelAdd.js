/**
 * Created by yangjie on 2016/8/13.
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
            taskname: {
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