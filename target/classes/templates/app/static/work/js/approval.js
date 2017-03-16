/**
 * @description		审批页面js
 * @author 			zwr
 * @date:			    2016-8-15
 * @modify:
 * @version 		    1.0.0
 */
define(function(require, exports, module) {
    var tool = require('tool');
    var view = {
        //页面临时数据
        data: {},
        //页面配置项
        conf:{
            header:'待审批'
        },
        //初始化
        init: function () {
            $.each(view.inits,function(i,n){
                n();
            });
            this.onFun();
        },
        //页面初始化事件
        inits:{
            //初始化数据
            initData:function () {
                view.data = tool.handler.getSession();
                view.conf.header = view.data.approval.title;
            },
            //初始化页面样式
            initPage:function(){
                tool.handler.getHeader({
                    title:view.conf.header,
                    list:function(){
                        view.handler.typePanelToggle();
                    }
                });
                if(view.conf.header== "询价" || view.conf.header== "采购" || view.conf.header== "放款" || view.conf.header== "我发起的"){
                    $('.header_title').append('<span class="arrow_down rotate180"><i></i></span>');
                    $('.content_tab').hide();
                    $('.header_list').hide();
                }else{
                    $('.content_tab').css('display','-webkit-box');
                }
                $('.approval_type>li[data-id='+view.data.approval.type+']').addClass('check');
                //动态显示页面的详情内容
                var param = {
                    statusType:view.data.approval.type
                };
                view.handler.getDetailList(param);
                //动态显示顶部审批事项（部门分类）
                view.handler.getCategory();
            }
        },
        //页面事件处理程序
        handler:{
            //返回事件
            headerBack:function(){
                tool.handler.changePage('index.html');
            },
            //ajax获取审批事项（部门分类）内容
            getCategory:function(){
                tool.ajax('',{
                    url:'/app/searchDynamicState',
                    suc:function(data){
                        if(data.result.code === '0'){
                            data = data.body;
                            var html = [];
                            $.each(data.orgs,function (i,n) {
                                html.push($('<span>',{
                                    data:n,
                                    html:n.orgName
                                }));
                            });
                            $('.tab_department_panl>span:first').after(html);
                            html = [];
                            $.each(data.things,function (i,n) {
                                html.push($('<span>',{
                                    data:n,
                                    html:n.name
                                }));
                            });
                            $('.tab_matter_panl>span:first').after(html);
                        }else{
                            tool.box.showTip(data.result.msg);
                        }
                    }
                });
            },
            //ajax页面详情内容
            getDetailList:function(param){
                if(tool.validate.isNull(param)){
                    param = {
                        thingType:[],// 事项类型(采购之类)
                        departType:[],// 部门
                        statusType: ($('.approval_type>li.check').length === 1?$('.approval_type>li.check').data().id:view.data.approval.type) // 审核状态
                    };
                    $('.tab_matter_panl .check').each(function (i,n) {
                        if(!tool.validate.isNull($(n).data().id)){
                            param.thingType.push($(n).data().id);
                        }
                    });
                    $('.tab_department_panl .check').each(function (i,n) {
                        if(!tool.validate.isNull($(n).data().id)){
                            param.departType.push($(n).data().id);
                        }
                    });
                }
                tool.ajax(param,{
                    url:'/app/searchCategory',
                    suc:function(data){
                        if(data.result.code === '0'){
                            //动态创建要显示的详情列表
                            var html = [],approvalList=$('.content_approval'),tip=approvalList.find('.tip');
                            if(data.body.categorys.length == 0){
                                if(tip.length > 0){
                                    tip.show().find('tip>p').html('您还没有'+$('.header_title').text()+'项目哟！');
                                }else{
                                    html = '\
                                    <div class="tip">\
                                        <img src="../static/common/img/nothing.png" alt="">\
                                        <p>您还没有'+$('.header_title').text()+'项目哟！</p>\
                                     </div>';
                                    $('.content_approval').append(html);
                                }
                                $('.content_approval_list').hide();
                            }else{
                                tip.hide();
                                $.each(data.body.categorys,function (i,n) {
                                    html.push($('<li>',{
                                        data:n,
                                        html:'\
                                        <span><img src="../static/common/img/gray_main.png"></span>\
                                        <p class="clerafix approval_title">\
                                            <span>'+n.title+'</span>\
                                            <span>&yen;'+n.totalPrice+'</span>\
                                        </p>\
                                        <p class="approval_detatil">\
                                            <span>'+n.name+' *'+data.body.categorys[i].needcount+'</span>\
                                            <span>'+(n.size>1?'等'+n.size+'项物品':'')+'</span>\
                                        </p>\
                                        <p class="approval_date clerafix">\
                                            <span>'+n.expectDate+'</span>\
                                            <span>'+n.status+'</span>\
                                        </p>'
                                    }));
                                });
                                $('.content_approval_list').html(html).show();
                                //列表左右滑动
                                if($('.header_title').html() === '待审批的'){
                                    $('.content_approval_list>li').touchWipe({
                                        move:function(_this,X,action){
                                            if(action && !$('.content_approval_list').hasClass('list_select')){
                                                var type  = 1;
                                                if(action === 'left'){
                                                    type  = 1;
                                                }else{
                                                    type  = 2;
                                                }
                                                view.handler.liSwipe(_this,type);
                                            }else{
                                                _this.style.WebkitTransform = "translateX(" + (X) + "px)";
                                            }
                                        },
                                        moveEnd:function(_this){
                                            _this.style.transition = "all 0.2s";
                                            _this.style.WebkitTransform = "translateX(" + 0 + "px)";
                                        }
                                    });
                                }
                            }
                        }else{
                            tool.box.showTip(data.result.msg);
                        }
                    },
                    err:function(){
                        tool.box.showTip(data.result.msg);
                    }
                });
            },
            //审批类型选项panel打开事件
            typePanelToggle:function(){
                $('.content_tab>div.cur').click();
                //关闭处理
                if($('.content').hasClass('panel_show')){
                    $('.panel_left_mask').hide();
                    $('.content').removeClass('panel_show');
                    tool.handler.headerList.hide();
                    tool.handler.recoveryPageHeight();
                }else{
                    $('.panel_left_mask').fadeIn(200);
                    $('.content').removeClass('panel_hide').addClass('panel_show');
                    tool.handler.headerList.show();
                    $('.panel_show').height(function () {
                        return $(window).height() - tool.handler.getHeaderHeight();
                    });
                    tool.handler.setPageHeight();
                }
            },
            showBox:function(set){
                var opt = {
                    yes:null,
                    no:null,
                    tip:'是否批量操作'
                };
                $.extend(opt,set);
                var html = $('<div>',{
                    class:'agreeBox',
                    id:'agreeBox',
                    html:function(){
                        var htmlList = [];
                        //提示语
                        htmlList.push($('<p>',{
                            html:opt.tip
                        }));
                        //俩按钮
                        htmlList.push($('<div>',{
                            html:function(){
                                var htmlBtns = [];
                                htmlBtns.push($('<span>',{
                                    html:'取消',
                                    click:function(){
                                        if(opt.no){
                                            opt.no();
                                        }
                                    }
                                }));
                                htmlBtns.push($('<span>',{
                                    html:'确认',
                                    click:function(){
                                        if(opt.yes){
                                            opt.yes();
                                        }
                                    }
                                }));
                                return htmlBtns;
                            }
                        }));
                        return htmlList;
                    }
                });
                tool.box.show(html);
            },
            hideBox:function(){
                tool.box.hide();
            },
            //同意 type:1同意，type:2 驳回
            agreeOrBack:function(type,param){
                tool.ajax(param,{
                    url:'/app/approveTask',
                    suc:function(data){
                        view.handler.hideBox();
                        if(data.result.code == '0'){
                            tool.box.showTip(data.result.msg);
                            //批量同意或驳回
                            if(type === 0){
                                $('.header>.header_back').click();
                            }
                            view.handler.getDetailList();
                        }else{
                            tool.box.showTip(data.result.msg);
                        }
                    }
                });
            },
            //驳回理由
            showCancleReason:function(param){
                var html = $('<div>',{
                    class:'writeCancle',
                    html:function(){
                        var htmls = [];
                        htmls.push('<p>填写驳回理由</p><textarea id="bohuiReson" placeholder="（可选）"></textarea><ul><li class="cancel">取消</li><li class="submit">提交</li></ul>');
                        return htmls;
                    }
                });
                tool.box.show(html);
                html.find('.cancel').on('click',function () {
                    tool.box.hide();
                });
                html.find('.submit').on('click',function () {
                    param.workflowContent[0].comment = $('#bohuiReson').val();
                    view.handler.agreeOrBack(2,param);
                });
            },
            //判断左右滑动 调用相应的事件
            liSwipe:function (_this,type) {
                var param = {
                    workflowContent:[
                        {formId:$(_this).data().id,taskId:$(_this).data().taskId,outcome:'驳回',comment:''}
                    ]
                };
                if(type === 2){
                    view.handler.showCancleReason(param);
                }else{
                    param.workflowContent[0].outcome = '同意';
                    view.handler.agreeOrBack(type,param);
                }
            }
        },
        //页面绑定事件
        onFun:function(){
            $('.header>.header_back').off('click').on('click',function(){
                view.handler.headerBack();
            });
            //页面回顶部
            $(".backTop").on('click',function(){
                $('html,body').animate({scrollTop:0},300);
            });
            $(window).scroll(function(){
                if( $(window).scrollTop() > 100){
                    $(".backTop").fadeIn(100);
                }else{
                    $(".backTop").fadeOut(100);
                };
            });
            //顶部向下箭头点击事件
            $('.header_title').on('click',function(){
                if($('.depart_class').length === 0){
                    if(view.conf.header=== "我发起的"){
                        $('.tab_matter_panl').prepend('<div class="depart_class rotate0">审批事项</div>');
                    }else{
                        $('.tab_department_panl').prepend('<div class="depart_class rotate0">部门分类</div>');
                    }
                }
                if($(this).children('.arrow_down').hasClass('rotate0')){
                    $(this).children('.arrow_down').removeClass('rotate0').addClass('rotate180');
                    $('.content_tab').hide();
                    if(view.conf.header=== "我发起的"){
                        $('.tab_matter_panl').slideUp(300);
                    }else{
                        $('.tab_department_panl').slideUp(300);
                    }
                } else{
                    $(this).children('.arrow_down').removeClass('rotate180').addClass('rotate0');
                    if(view.conf.header=== "我发起的"){
                        $('.tab_matter_panl').slideDown(300);
                    }else{
                        $('.tab_department_panl').slideDown(300);
                    }
                }
            });
            //点击确定，隐藏
            $('.sure').on('click',function(){
                view.handler.getDetailList();
                if(view.conf.header== "询价" || view.conf.header== "采购" || view.conf.header== "放款" || view.conf.header== "我发起的"){
                    $('.header_title').click();
                }else{
                    $('.content_tab>div.cur').click();
                }
            });
            //tab选项卡切换事件
            $('.content_tab>div').on('click',function(){
                if($(this).hasClass('cur')){
                    var children = $(this).toggleClass('cur').children('.arrow_down');
                    if(children.hasClass('rotate180')){
                        children.removeClass('rotate180').addClass('rotate0');
                    }else if(children.hasClass('rotate0')){
                        children.removeClass('rotate0').addClass('rotate180');
                    }else{
                        children.addClass('rotate180');
                    }
                    $('.content_tab_panl>div:eq('+$(this).index()+')').slideToggle(300);
                }else{
                    var children = $(this).addClass('cur').children('.arrow_down');
                    if(children.hasClass('rotate180')){
                        children.removeClass('rotate180').addClass('rotate0');
                    }else if(children.hasClass('rotate0')){
                        children.removeClass('rotate0').addClass('rotate180');
                    }else{
                        children.addClass('rotate180');
                    }
                    children = $(this).siblings('.cur').removeClass('cur').children('.arrow_down');
                    if(children.hasClass('rotate180')){
                        children.removeClass('rotate180').addClass('rotate0');
                    }else if(children.hasClass('rotate0')){
                        children.removeClass('rotate0').addClass('rotate180');
                    }else{
                        children.addClass('rotate180');
                    }
                    $('.content_tab_panl>div:eq('+$(this).index()+')').slideToggle(300).siblings('div').hide();
                }
            });
            //右侧类型选项切换事件
            $('.approval_type>li').on('click',function(){
                $(this).addClass('check').siblings('li').removeClass('check');
                view.handler.typePanelToggle();
                tool.handler.setHeaderTip($(this).children('span').html());
                view.handler.getDetailList();
            });
            //点击审批事项菜单
            $('.content_tab_panl').on('click','span',function(e){
                $(this).toggleClass('check');
                if($(this).hasClass('check')){
                    if($(this).data().type === 'all'){
                        $(this).siblings('span').removeClass('check');
                    }else{
                        $(this).siblings('[data-type=all]').removeClass('check');
                    }
                }
            });
            //未选中状态下点击详情列表,跳转详情页面
            $('.content_approval_list').on('click','li',function(){
                if($(this).parent().hasClass('list_select')){
                    $(this).toggleClass('check');
                }else{
                    view.data.purchaseDetail = {
                        id:$(this).data().id+''
                    };
                    tool.handler.setSession(view.data);
                    tool.handler.changePage('./purchase_detail.html');
                }
            });
            //审批长按事件
            $('.content_approval_list').taphold(function(_this){
                //如果是待审批的任务，才会出现长按同意驳回的功能
                if($('.header_title').html() === '待审批的'){
                    $('.header>.header_back').off('click').on('click',function(){
                        //选项卡 右侧列表icon
                        $('.content_tab').css('display','-webkit-box');
                        $('.header_list').removeClass('v_hide');

                        $(_this).removeClass('list_select');
                        $('.approval_btns').hide();
                        view.handler.hideBox();
                        $(this).off('click').on('click',function(){
                            view.handler.headerBack();
                        });
                    });
                    //选项卡 右侧列表icon
                    $('.content_tab').hide();
                    $('.header_list').addClass('v_hide');

                    $(_this).addClass('list_select');
                    $('.approval_btns').css({display:'-webkit-box'});
                    //同意或者驳回
                    $('.approval_btns>span').on('click',function(){
                        //判断是否选中
                        if($('.content_approval_list>li.check').length > 0){
                            var index = $(this).index(),tip='',tip1 = '是否批量';
                            if(index === 0){
                                tip = '驳回';
                            }else{
                                tip = '同意';
                            }
                            view.handler.showBox({
                                yes:function(){
                                    var param = {
                                        workflowContent:[]
                                    },data = {};
                                    $('.list_select .check').each(function (i,n) {
                                        data = $(n).data();
                                        param.workflowContent.push({
                                            formId: data.id,
                                            taskId: data.taskId,
                                            outcome: tip,
                                            comment: ""
                                        });
                                    });
                                    //同意 type:1同意，type:2 驳回
                                    view.handler.agreeOrBack(0,param);
                                },
                                no:function(){
                                    $('.header>.header_back').click();
                                },
                                tip:tip1 + tip + '！'
                            });
                        }else{
                            tool.box.showTip('请选择至少一项任务办理');
                        }
                    });
                }
            });
            //左侧蒙版点击事件
            $('.panel_left_mask').on('click',function(){
                view.handler.typePanelToggle();
            });
        }
    };
    $.extend(exports,view);
});