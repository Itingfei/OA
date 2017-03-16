//获取窗口索引
var index = parent?parent.layer.getFrameIndex(window.name):layer.getFrameIndex(window.name); 
var loadTitle = false;
var dtb = ''; 
var rfbtn = '';
// 默认关闭按钮
$(document).ready(function() {
  var btnClose = $("#btnClose");
  if (btnClose.length > 0) {
    $(btnClose).click(function(event) {
      closeModal();
    });
  } 
});

/**
 * 弹出一级模态窗口.
 * @param url 提交的url
 * @param refreshButton 父页面刷新button
 * @param title 标题
 * @param detailTab 详情tab
 */
function openLevelOneModal(url,refreshButton,title,detailTab) {
	var width = 900, height = 450;
	openModal(url, width, height,refreshButton,title,detailTab);
}

/**
 * 弹出二级模态窗口.
 * @param url 提交的url
 * @param refreshButton 父页面刷新button
 * @param title 标题
 * @param detailTab 详情tab
 */
function openLevelTwoModal(url,refreshButton,title,detailTab) {
	var width = 800, height = 400;
	openModal(url, width, height,refreshButton,title,detailTab);
}

/**
 * 打开模态窗口.
 * @param url 提交的url
 * @param width 宽度
 * @param height 高度
 * @param refreshButton 刷新button
 * @param title 标题
 * @param detailTab 详情tab
 * @param activepageurl 刷新弹出层后面URL
 */
// var index ;
function openModal(url, width, height,refreshButton,title,detailTab,activepageurl) {
  var wNumeric = $.isNumeric(width);
  var wHeight = $.isNumeric(width);
  var w = wNumeric ? width + 'px' : width;
  var h = wHeight ? height + 'px' : height;
  // 无title时默认3个空白
  var t = title || '   ';
  rfbtn = refreshButton || '';
  if (t == '   ') {
      loadTitle = true;
  }
  dtb = detailTab || '#base-tab';
   layer.open({
        type: 2,
        'title': t,
        shadeClose: false,
        closeBtn :2,
        maxmin:false,
        shade: 0.8,
        area: [w, h],
        content: [url/*, 'no'*/] ,//iframe的url
        success: function (layero,index) {
          layerReady(layer,index,loadTitle,rfbtn,dtb);
        },
      end: function () {
          // closeModal();
          if(activepageurl!="" && typeof(activepageurl)!="undefined"){
              window.location.href=activepageurl;
          }
      }
    });
}  
/**
 * 弹出层初始化后事件.
 * @param layer layer引用
 * @param index layer索引
 * @param loadTitle 加载title
 * @param rfbtn 刷新button
 * @param dtb 详情tab
 */
function layerReady(layer,index,loadTitle,rfbtn,dtb) {
   var body = layer.getChildFrame('body', index);
   if (loadTitle) {
     var nt = $("#_title_",body)
     if (nt.length > 0) {
    	 nt = $(nt[0]).html();
     } else {
    	 nt = '';
     }
     layer.title(nt,index);
   }
   //默认刷新按钮
   if (rfbtn != '') {
      var btnRefresh = $('#_refresh_button_');
      if(btnRefresh.length  > 0) {
          btnRefresh.val(rfbtn);
      } else {
          $('<input />').attr({
              type: 'hidden',
              id: '_refresh_button_',
              value:rfbtn
          }).appendTo(body);
      }
   }
   //详情显示tab
   var tab = $(dtb,body);
   if (tab.length > 0) {
     tab[0].click();
   }
}

/**
 * 关闭弹出框.
 */
function closeModal(){
    parent.layer.close(index);
}
/**
 * 显示消息.
 */
function showMsg(msg) {
	// 父页面
	// if(parent && typeof parent.layer != 'undefined' && index != 'undefined') {
    if(index != 'undefined'){
		parent.layer.msg(msg);
	} else {
		layer.msg(msg);
	}
}
/**
 * 成功时显示消息.
 */
function showToastMsg(msg,icon) {
    if(index != undefined){
        parent.layer.msg(msg,{
           icon: icon,
           skin: 'layer-ext-moon'
       });
	} else {
        layer.msg(msg,{
            icon: icon,
            skin: 'layer-ext-moon'
        });
	}
}

/**
 * yangjie
 * 关闭窗口并刷新.
 * @param msg 显示消息
 * @param formId 刷新的表单Id
 * @param buttonId 按钮
 * @param callback(parentDocument) 回调函数
 * @param loadTitle 重新加载title
 * @param isRefreshPager 是否刷新分页
 */
function closeAndRefresh(msg,formId,buttonId, callback,loadTitle,isRefreshPager) {
	if (msg != null && msg != undefined) {
		//parent.layer.msg(msg);
        showToastMsg(msg,1);
	}
    if (formId.indexOf("#") == 0) {
        formId = formId;
    } else {
        formId = "#" + formId;
    }
    var btnRefreshId;
    if (buttonId && buttonId != '') {
        btnRefreshId = buttonId;
    } else {
        btnRefreshId = $('#_refresh_button_');
        if (btnRefreshId.length == 0) {
           btnRefreshId = "#btnSearch";
        } else {
           btnRefreshId = btnRefreshId.val();
        }
    }

    if (typeof callback == 'function') {
      callback(parent.document);
    }
    if(loadTitle && parent.parent && parent.parent.layer) {
      var nt = $("#_title_", parent.document);
      if (nt.length > 0) {
     	 nt = $(nt[0]).text();
      } else {
     	 nt = '';
      }
      var index = parent.parent.layer.getFrameIndex(parent.window.name);
      parent.parent.layer.title(nt,index);
    }
    // 刷新（ btnSearch）或者明确说明要刷新分页的
    if (btnRefreshId === "#btnSearch" || isRefreshPager) {
    	parent.refreshPager(formId);
    } else {
	    var btnRefresh = $(btnRefreshId, parent.document);
	    if (btnRefresh.length > 0) {
	        btnRefresh[0].click();
	    }
    }
    closeModal();
}
//刷新当前分页数据
function refreshPager(form){
    $(form).submit();
	// var girdId = $('.ui-jqgrid', document).attr('id');
	// if (girdId && girdId.indexOf('gbox_') == 0) {
	//    var tablelistId = "#"+girdId.substring(5);
	//    var page = $(tablelistId,document).getGridParam().page;
	//    $(tablelistId,document).setGridParam(page).trigger("reloadGrid");
	// }
}
