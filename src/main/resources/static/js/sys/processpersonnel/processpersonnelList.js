/**
 * Created by yangjie on 2016/8/14.
 */
$(document).ready(function() {
    $("#addPersonnel").click(function () {
        openLevelOneModal("/processpersonnel/addPersonnel","","添加审批关系");
    });
    $(".edit").click(function () {
        var processpersonnelId = $(this).attr("data-id");
        openLevelOneModal("/processpersonnel/addPersonnel?id="+processpersonnelId,"","编辑审批关系");
    });
    $(".reset").click(function () {
        $(":input").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var processpersonnelId = $(this).attr("data-id");
        // 确认提示框
        var msg = "要删除该审批关系吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/processpersonnel/"+processpersonnelId+"/delete",
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
    $("#selectFrom").attr("action", "/processpersonnel");
    $("#selectFrom").submit();
    return false;
}