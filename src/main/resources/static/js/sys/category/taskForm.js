/**
 * Created by yangjie on 2016/8/19.
 */
$(document).ready(function() {
    //成功后处理
    function success(resp) {
        if (resp.code != '0') {
            showToastMsg(resp.msg,2);
        } else {
            closeAndRedirect("/category/"+resp.result+"/viewTaskForm","办理"+resp.result+"任务");
        }
    }
    function closeAndRedirect(url,newtabname) {
        parent.refreshByhref("/workflow/tasklist");
        parent.opentab("/workflow/tasklist","我的任务");
        parent.closetab(url);

    }
    var validator={

    }
    // 数据验证和提交
    validateAndSubmit("commentForm", validator, success);
    $(".showItem").click(function () {
        $(this).hide();
        $(".items").show();
        $(".hideItem").show();
    })
    $(".hideItem").click(function () {
        $(this).hide();
        $(".items").hide();
        $(".showItem").show();
    })
    $(".agree").click(function () {
        var countArr = [];
        var stockArr = [];
        var actualPriceArr = [];
        $("#outcome").val($(this).text());
        var positionName = $("#positionName").val();
        var itemSize = $("#itemSize").val();
        var inventoryStatus = $("#inventoryStatus").val();
        var channels = $("[name='channels']").val();
        var payment = $("[name='payment']").val();
        if(positionName!="" && positionName=='采购专员'){
            //验证库存,采购数,实际单价必填
            var counts = $("input[name='count']");
            var stocks = $("input[name='stock']");
            var actualPrices = $("input[name='actualPrice']");
            $.each(counts, function(i, obj) {
                if(obj.value!=""){
                    countArr.push(obj.value);
                }
            });
            $.each(stocks, function(i, obj) {
                if(obj.value!=""){
                    stockArr.push(obj.value);
                }
            });
            $.each(actualPrices, function(i, obj) {
                if(obj.value!=""){
                    actualPriceArr.push(obj.value);
                }
            });
            if(actualPriceArr.length>0 && actualPriceArr.length ==itemSize && countArr.length>0 && countArr.length ==itemSize && stockArr.length>0 && stockArr.length ==itemSize
              && inventoryStatus!='' && channels!='' && payment!=''){
                $("#commentForm").submit();
            }else{
                layer.alert("请填写库存状态,采购渠道,支付方式,采购数,库存和实际单价",{
                    icon:0
                })
            }
        }else {
             $("#commentForm").submit();
        }
    })
    $("#back").click(function () {
        var categoryId = $(this).attr("data-id");
        parent.opentab('/category','流程表单列表');
        parent.closetab('/category/'+categoryId+'/showRecord');
    })
});
function tdclick(tdobject){
    var td=$(tdobject);
    td.attr("ondblclick", "tdclick(this)");
    //1,取出当前td中的文本内容保存起来
    var text=td.text();
    var name = td.attr("data-name");
    var id = td.attr("data-id");
    //2,清空td里面的内容
    td.html(""); //也可以用td.empty();
    //3，建立一个文本框，也就是input的元素节点
    var input=$("<input>");
    //4，设置文本框的值是保存起来的文本内容
    input.attr("value",text);
    input.attr("name",name);
    input.bind("blur",function(){
        var inputnode=$(this);
        var inputtext=inputnode.val();
        var tdNode=inputnode.parent();
        tdNode.html(inputtext);
        tdNode.click(tdclick);
        td.attr("ondblclick", "tdclick(this)");
        $("input[name="+name+"]"+"[id="+id+"]").val(inputtext);
    });
    input.keyup(function(event){
        var myEvent =event||window.event;
        var kcode=myEvent.keyCode;
        if(kcode==13){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            $("input[name="+name+"]"+"[id="+id+"]").val(inputtext);
        }
    });

    //5，将文本框加入到td中
    td.append(input);
    var t =input.val();
    input.val("").focus().val(t);
//              input.focus();

    //6,清除点击事件
    td.unbind("click");
}
function setInventoryStatus(obj) {
    var inventoryStatus = $(obj).children().children().val();
    $("#inventoryStatus").val(inventoryStatus)
}
// function page(n, s) {
//     if (n) $("#pageNum").val(n);
//     if (s) $("#pageSize").val(s);
//     $("#selectFrom").attr("action", "/category");
//     $("#selectFrom").submit();
//     return false;
// }