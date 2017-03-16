/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addmenu").click(function () {
        openLevelOneModal("/menu/0/appendChild","","添加菜单");
    });
    $(".edit").click(function () {
        var menuId = $(this).attr("data-id");
        openLevelOneModal("/menu/"+menuId+"/update","","编辑菜单");
    });
    $(".detail").click(function () {
        var menuId = $(this).attr("data-id");
        openLevelOneModal("/menu/"+menuId+"/detail","","查看菜单");
    });
    $(".delete").click(function () {
        var menuId = $(this).attr("data-id");
        // 确认提示框
        var msg = "要删除该菜单及所有子菜单项吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/menu/"+menuId+"/delete",
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
        var menuId = $(this).attr("data-id");
        var menuName = $(this).attr("data-name");
        openLevelOneModal("/menu/"+menuId+"/appendChild","",menuName+"添加子菜单");
    });
    $("#saveSort").click(function () {
        var ids =[];
        var sorts=[];
        $.each($("[name='ids']"), function(i,obj){
            ids.push($(obj).val());
        });
        $.each($("[name='sorts']"), function(i,obj){
            sorts.push(($(obj).val()));
        });
        $.ajax({
            type:"post",
            url:"/menu/updateSort",
            data:{ids:ids,sorts:sorts}, //发送数据
            dataType:"JSON",
            traditional: true,
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
    });
    $("#treeTable").treeTable({expandLevel : 3}).show();
});