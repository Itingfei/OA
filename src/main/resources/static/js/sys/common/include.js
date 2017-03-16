/**
 * Created by fengjifei on 2016/8/17.
 */
/**
 * Created by yangjie on 2016/8/14.
 */
var activepageurl = parent.parent.$(".page-tabs-content").find("a").filter(".active").first().attr("data-id");
$(document).ready(function () {
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
    for (var selector in config) $(selector).chosen(config[selector]).change(function () {
        var ID = $(this).attr("id");
        if (!$(this).valid()) {
            $("#" + ID + "_chosen a").addClass("error");
        }
        else {
            $("#" + ID + "_chosen a").removeClass("error");
        }
    });
    $(".update").click(function () {
        // var menuId = $(this).attr("data-id");
        var url = $(this).attr("data-url");
        openpopup(url, $(this).text(), '', '', '1')
    });
    $(".delete").click(function () {
        var url = $(this).attr("data-url");
        // 确认提示框
        var msg = "要删除吗？";
        confirmMsg(msg, function (index) {
            $.ajax({
                type: "post",
                url: url,
                dataType: "JSON",
                success: function (result) {
                    if (result.code != '0') {
                        //失败
                        parent.showmessage(result.msg, 2);
                    } else {
                        parent.showmessage(result.msg, 1);
                        window.location.href = activepageurl;
                    }
                }
            });
            layer.close(index);
        });
    });
    $(".submit").click(function () {
        var url = $(this).parents("form").attr("action");
        var method = $(this).parents("form").attr("method");
        var content = "{";
        $(this).parents("form").find("input").not(":button").each(function () {
            // alert($(this).attr("class"));
            if ($(this).val() != "") {
                content += '"' + $(this).attr("name") + '":"' + $(this).val() + '",';
            }
        });
        $(this).parents("form").find("select").each(function () {
            if ($(this).val() != "") {
                content += '"' + $(this).attr("name") + '":"' + $(this).val() + '",';
            }
        });
        if (content != "") {
            content = content.substring(0, content.length - 1) + "}";
        }
        content = jQuery.parseJSON(content)
        $.ajax({
            type: method,
            url: url,
            dataType: "JSON",
            data: content,//{"test":5,"param1":1,"test2":3},
            success: function (result) {
                if (result.code != '0') {
                    //失败
                    parent.parent.showmessage(result.msg, 2);
                } else {
                    parent.parent.showmessage(result.msg, 1);
                    closeModal();
                }
            }
        });

    });
});
var width = 900;
var height = 480;
function openpopup(url, title, width, height, flag,aurl) {

    if (width == "" || typeof(width) == "undefined") {
        width = this.width
    }
    if (height == "" || typeof(height) == "undefined") {
        height = this.height;
    }
    if (flag == 1) {
        if(aurl!="" && typeof(aurl)!="undefined"){
            // parent.jcloseall();
            openModal(url, width, height, "", title, "", aurl);
        }else{
            openModal(url, width, height, "", title, "", activepageurl);
        }


    } else {
        openModal(url, width, height, "", title, "", "");
    }
}

function findIndexByhref(href) {
    parent.parent.$(".J_iframe").each(function (k) {
        if ($(this).attr("src") == href) {
            return $(this).attr("name").substring(6);
        }
    })
}
function refreshByhref(href) {
    parent.parent.$(".J_iframe").each(function (k) {
        if ($(this).attr("src") == href) {
            $(this).attr('src', href);
        }
    })
}
function getNewIndex(){
    var menuArray=new Array();
    var tabArray=new Array();
    parent.parent.$(".J_menuItem").each(function (k) {

        menuArray[k] =  $(this).attr("data-index");
    })
    menuArray.sort(function(a,b){
        return b-a});
    parent.parent.$(".J_iframe").each(function (k) {
        tabArray[k] = $(this).attr("name").substring(6);
    })
    tabArray.sort(function(a,b){
        return b-a});
    if(menuArray[0]>tabArray[0]){
        return menuArray[0]+1;
    }else{
        return tabArray[0]+1;
    }
}
function opentab(href,text,type){
    opentabs(href,getNewIndex(),text,true);
    if(type!="" && typeof(type)!="undefined"){
        refreshByhref(href);
    }
}
function findwidthByhref(href) {
    parent.parent.$(".J_menuTab").each(function (k) {
        if ($(this).data("id") == href) {
            return $(this).width();
        }
    })
}
function closetab(href){
    closetabs(href,findwidthByhref(href));
}



