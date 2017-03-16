/**
 * Created by yangjie on 2016/8/19.
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
    $("#addUser").click(function () {
        openLevelOneModal("/user/userEdit","","添加用户");
    });
    $(".edit").click(function () {
        var userId = $(this).attr("data-id");
        openLevelOneModal("/user/userEdit?id="+userId,"","编辑用户");
    });
    $(".detail").click(function () {
        var organizationId = $(this).attr("data-id");
        openLevelOneModal("/organization/"+organizationId+"/detail","","查看组织机构");
    });
    $(".reset").click(function () {
        $("input[name!='pageNum'][name!='pageSize']").val('');
        $(":selected").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var userId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要删除该用户吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/user/"+userId+"/delete",
                dataType:"JSON",
                success:function(result){
                    if (result.code != '0') {
                        //失败
                        showToastMsg(result.msg,2);
                    } else {
                        showToastMsg(result.msg,1);
                        //重新加载数据
                        refreshPager("#selectFrom");
                    }
                }
            });
            layer.close(index);
        });
    });
    $(".lock").click(function () {
        var userId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要锁定该用户吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/user/"+userId+"/lock/?status=1",
                dataType:"JSON",
                success:function(result){
                    if (result.code != '0') {
                        //失败
                        showToastMsg(result.msg,2);
                    } else {
                        showToastMsg(result.msg,1);
                        //重新加载数据
                        refreshPager("#selectFrom");
                    }
                }
            });
            layer.close(index);
        });
    });
    $(".unlock").click(function () {
        var userId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要解锁该用户吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/user/"+userId+"/lock/?status=0",
                dataType:"JSON",
                success:function(result){
                    if (result.code != '0') {
                        //失败
                        showToastMsg(result.msg,2);
                    } else {
                        showToastMsg(result.msg,1);
                        //重新加载数据
                        refreshPager("#selectFrom");
                    }
                }
            });
            layer.close(index);
        });
    });
    $("#orgId").change(function () {
        getPositionByOrgId($(this).val(),"")
    });
    if($("#searshPositionId").val()!="" && $("#searshOrgId").val()!=""){
        getPositionByOrgId($("#searshOrgId").val(),$("#searshPositionId").val());
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
function page(n, s) {
    if (n) $("#pageNum").val(n);
    if (s) $("#pageSize").val(s);
    $("#selectFrom").attr("action", "/user");
    $("#selectFrom").submit();
    return false;
}