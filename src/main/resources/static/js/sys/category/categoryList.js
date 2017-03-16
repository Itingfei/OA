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
    $("#addCategory").click(function () {
        openLevelOneModal("/category/categoryEdit","","添加流程对应表单");
    });
    $(".edit").click(function () {
        var categoryId = $(this).attr("data-id");
        openLevelOneModal("/category/categoryEdit?id="+categoryId,"","编辑流程对应表单");
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
    $(".play").click(function () {
        var categoryId = $(this).attr("data-id");
        // 确认提示框
        var msg = "启动后流程表单则不能被修改,确定要启动吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/category/"+categoryId+"/startApply",
                dataType:"JSON",
                success:function(result){
                    if (result.code != '0') {
                        //失败
                        showToastMsg(result.msg,2);
                    } else {
                        showToastMsg(result.msg,1);
                        //重新加载数据
                        refreshPager("#selectFrom");
                        opentab("/workflow/tasklist","我的任务",1);
                    }
                }
            });
            layer.close(index);
        });
    });
    $(".showRecord").click(function () {
        var categoryId = $(this).attr("data-id");
        opentab('/category/'+categoryId+'/showRecord','查看'+categoryId+'审核记录')
    })
    $(".itemDetail").click(function () {
        var categoryId = $(this).attr("data-id");
        openLevelOneModal("/category/"+categoryId+"/itemDetail","","查看采购明细");
    })
});
function page(n, s) {
    if (n) $("#pageNum").val(n);
    if (s) $("#pageSize").val(s);
    $("#selectFrom").attr("action", "/category");
    $("#selectFrom").submit();
    return false;
}