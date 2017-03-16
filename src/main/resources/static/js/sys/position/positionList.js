/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addPosition").click(function () {
        openLevelOneModal("/position/0/appendChild","","添加职位");
    });
    $(".edit").click(function () {
        var positionId = $(this).attr("data-id");
        openLevelOneModal("/position/"+positionId+"/update","","编辑职位");
    });
    $(".detail").click(function () {
        var positionId = $(this).attr("data-id");
        openLevelOneModal("/position/"+positionId+"/detail","","查看职位");
    });
    $(".reset").click(function () {
        $(":input").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var positionId = $(this).attr("data-id");
        // 确认提示框
        var msg = "要删除该职位及所有子职位项吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/position/"+positionId+"/delete",
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
        var positionId = $(this).attr("data-id");
        var positionName = $(this).attr("data-name");
        openLevelOneModal("/position/"+positionId+"/appendChild","",positionName+"添加子节点");
    });
    $("#treeTable").treeTable({expandLevel : 3}).show();
});