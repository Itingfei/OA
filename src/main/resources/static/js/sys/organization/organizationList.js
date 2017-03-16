/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addOrganization").click(function () {
        openLevelOneModal("/organization/0/appendChild","","添加组织机构");
    });
    $(".edit").click(function () {
        var organizationId = $(this).attr("data-id");
        openLevelOneModal("/organization/"+organizationId+"/update","","编辑组织机构");
    });
    $(".detail").click(function () {
        var organizationId = $(this).attr("data-id");
        openLevelOneModal("/organization/"+organizationId+"/detail","","查看组织机构");
    });
    $(".reset").click(function () {
        $(":input").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var organizationId = $(this).attr("data-id");
        // 确认提示框
        var msg = "要删除该职位及所有子职位项吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/organization/"+organizationId+"/delete",
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
    $(".child").click(function () {
        var organizationId = $(this).attr("data-id");
        var organizationName = $(this).attr("data-name");
        openLevelOneModal("/organization/"+organizationId+"/appendChild","",organizationName+"添加子节点");
    });
    $(".addPosition").click(function () {
        var organizationId = $(this).attr("data-id");
        var organizationName = $(this).attr("data-name");
        openLevelOneModal("/organization/"+organizationId+"/addPosition","",organizationName+"添加职位");
    });
    $(".infoPosition").click(function () {
        var organizationId = $(this).attr("data-id");
        var organizationName = $(this).attr("data-name");
        openLevelOneModal("/organization/"+organizationId+"/infoPosition","","查看"+organizationName+"下职位");
    });
    $("#treeTable").treeTable({expandLevel : 3}).show();
});