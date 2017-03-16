/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addClassify").click(function () {
        openLevelOneModal("/classify/classifyEdit","","添加采购物品分类");
    });
    $(".edit").click(function () {
        var classifyId = $(this).attr("data-id");
        openLevelOneModal("/classify/classifyEdit?id="+classifyId,"","编辑采购物品分类");
    });
    $(".reset").click(function () {
        $("input[name!='pageNum'][name!='pageSize']").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var classifyId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要删除该采购物品分类吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/classify/"+classifyId+"/delete",
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
});
function page(n, s) {
    if (n) $("#pageNum").val(n);
    if (s) $("#pageSize").val(s);
    $("#selectFrom").attr("action", "/classify");
    $("#selectFrom").submit();
    return false;
}