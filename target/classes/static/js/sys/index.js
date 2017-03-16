/**
 * Created by yangjie on 2016/8/22.
 */
$(function () {
    var msize =$("#msize").val();
    if(msize<=0)
        $(".msize").html('');
    $(".mtitle").click(function () {
        var size = parseInt($(".msize").html()) -1;
        if(size<=0) {
            $(".msize").html('');
            $("#msize").val(0);
        }
        var messageId = $(this).attr("data-id");
        opentab("/message/myMessage?id="+messageId,"我的消息");
    })
    $(".modifyPass").click(function () {
        $( "#dialog-pass" ).modal("show");
    })
    //成功后处理
    function success(resp) {
        if (resp.code != '0') {
            showToastMsg(resp.msg,2);
        } else {
            showToastMsg(resp.msg,1);
            $('#dialog-pass').modal('hide');
        }
    }
    var validator={
        rules:{
            oldp: {
                required : true
            },
            newp: {
                required : true
            },
            newp2: {
                required : true,
                equalTo:"#newp"
            }
        }
    }
    // 数据验证和提交
    validateAndSubmit("commentForm", validator, success);
    // 保存按钮事件
    $("#btnSave").click(function(event) {
        $("#commentForm").submit();
    });
    $(".userinfo").click(function () {
        $( "#userinfo" ).modal("show");
    })
});