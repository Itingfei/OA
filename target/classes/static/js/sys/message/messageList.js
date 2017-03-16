/**
 * Created by yangjie on 2016/8/14.
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
    $("#addMessage").click(function () {
        openLevelOneModal("/message/messageEdit","","添加消息");
    });
    $(".edit").click(function () {
        var messageId = $(this).attr("data-id");
        openLevelOneModal("/message/messageEdit?id="+messageId,"","编辑消息");
    });
    $(".credit").click(function () {
        var categoryId = $(this).attr("data-id");
        openLevelOneModal("/message/messageEdit?categoryId="+categoryId,"","放款通知");
    });
    $(".complete").click(function () {
        var categoryId = $(this).attr("data-id");
        openLevelOneModal("/message/messageEdit?categoryId="+categoryId,"","采购完成通知");
    });
    $(".reset").click(function () {
        $("input[name!='pageNum'][name!='pageSize']").val('');
        //重新加载数据
        refreshPager("#selectFrom");
    })
    $(".delete").click(function () {
        var messageId = $(this).attr("data-id");
        // 确认提示框
        var msg = "确定要删除该消息吗？";
        confirmMsg(msg,function(index){
            $.ajax({
                type:"post",
                url:"/message/"+messageId+"/delete",
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
    $("#selectFrom").attr("action", "/message");
    $("#selectFrom").submit();
    return false;
}