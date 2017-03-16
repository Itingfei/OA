/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addRole").click(function () {
        openLevelOneModal("/role/roleEdit","","添加角色");
    });
    $(".edit").click(function () {
        var roleId = $(this).attr("data-id");
        openLevelOneModal("/role/roleEdit?id="+roleId,"","编辑角色");
    });
    $(".detail").click(function () {
        var organizationId = $(this).attr("data-id");
        openLevelOneModal("/organization/"+organizationId+"/detail","","查看组织机构");
    });
    $(".reset").click(function () {
        $("input[name!='pageNum'][name!='pageSize']").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var roleId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要删除该角色吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/role/"+roleId+"/delete",
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
    initTree();// 初始化 菜单树
    $(".grantMenus").click(function () {
         var id = $(this).attr("data-id");
         var name = $(this).attr("data-name");
        $('#dialog-menus #role_name').text(name);
        $("#dialog-menus #role_id").val(id);
        var zTreeObj = $.fn.zTree.getZTreeObj("menu_tree");//树对象
        zTreeObj.cancelSelectedNode();//清空所有的选中项
        zTreeObj.checkAllNodes(false);//清空所有的复选框
        zTreeObj.expandAll(false);//折叠所有
        // 获取该角色下的所有 菜单
        var data = {"id":id};
        $.getJSON("/role/getMenusByRoleId",data,function(data){
            if(data.chk){
                $("#firstAdd").val(0);
                $.each(data.chk,function(i,item){
                    var node = zTreeObj.getNodeByParam("id",item.id);
                    node.checked = true;
                    zTreeObj.updateNode(node);
                });
                // $.each(data.chk,function(i,chk){
                //     var node = zTreeObj.getNodeByParam("id",chk.id,null);
                //     zTreeObj.checkNode(node,true,true);
                // });
            }else{
                $("#firstAdd").val(1);
            }
        });
        $('#dialog-menus').modal('show');
    });
    $(".saveMenu").click(function () {
        var zTreeObj = $.fn.zTree.getZTreeObj("menu_tree");//树对象
        var nodes = zTreeObj.getCheckedNodes(true);
        var role_id = $("#dialog-menus #role_id").val();
        var menu_ids = "";
        $.each(nodes,function(i,node){
            menu_ids += "&menu_id="+node.id;
        });
        if($("#firstAdd").val()!=1 || menu_ids.length>0){
            var data = "&role_id="+role_id+menu_ids;
            $.getJSON("/role/saveRoleMenus",data,function(data){
                if (data.code != '0') {
                    //失败
                    showToastMsg(data.msg,2);
                } else {
                    showToastMsg(data.msg,1);
                    //重新加载数据
                    refreshPager("#selectFrom");
                }
                $('#dialog-menus').modal('hide');
            });
        }else{
            showToastMsg("请选择要授权的菜单!",2);
        }
    });
});
// 初始化菜单树
function initTree(){
    var setting  = {
        data: {
            key:{ name:'name' },
            simpleData: {enable: true, idKey:'id', pIdKey:'parent_id', rootPid:1 }
        },
        check:{ enable: true, autoCheckTrigger: true}
    };
    $.getJSON("/role/menuTree",'{}',function(data){
        if(data.flag){
            $.fn.zTree.init($("#menu_tree"), setting, data.tree);
        }else{
            alert(data.msg);
        }
    });
}
function page(n, s) {
    if (n) $("#pageNum").val(n);
    if (s) $("#pageSize").val(s);
    $("#selectFrom").attr("action", "/role");
    $("#selectFrom").submit();
    return false;
}