/**
 * Created by yangjie on 2016/8/31.
 */
$(document).ready(function() {
    $(".manage").click(function () {
        var taskId = $(this).attr("data-id");
        opentab('/category/'+taskId+'/viewTaskForm','办理'+taskId+'任务')
        // openLevelOneModal("/category/categoryEdit?id="+categoryId,"","编辑流程对应表单");
    });
    $(".delete").click(function () {
        var categoryId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要删除该采购流程对应表单吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/category/"+categoryId+"/delete",
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
    $("#selectFrom").attr("action", "/category");
    $("#selectFrom").submit();
    return false;
}