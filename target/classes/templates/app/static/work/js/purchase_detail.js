/**
 * @description		采购详情页js
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
            header:'采购详情',
            tip:{}
        },
        //初始化
        init: function () {
            $.each(view.inits,function(i,n){
                n();
            });
        },
        //页面初始化事件
        inits:{
            //初始化数据
            initData:function () {
                view.data = tool.handler.getSession();
                //赋值title
                if(!tool.validate.isNull(view.data.purchaseDetail) && !tool.validate.isNull(view.data.purchaseDetail.title)){
                    view.conf.header = view.data.purchaseDetail.title;
                }
            },
            //初始化页面样式
            initPage:function(){
                tool.handler.getHeader({
                    title:view.conf.header
                });
                view.handler.getDetail(view.data.purchaseDetail.id);
            }
        },
        //页面事件处理程序
        handler:{
            showCancleReason:function(){
                var html = $('<div>',{
                    class:'writeCancle',
                    html:function(){
                        var htmls = [];
                        htmls.push('<p>填写驳回理由</p><textarea placeholder="（可选）"></textarea><ul><li class="cancel">取消</li><li class="submit">提交</li></ul>');
                        return htmls;
                    }
                });
                tool.box.show(html);
            },
            getDetail:function (id) {
                var param = {
                    id: id
                };
                tool.ajax(param,{
                    url:'/app/categoryDetail',
                    suc:function(data){
                        if(data.result.code === '0'){
                            data = data.body;
                            view.data.purchaseDetail.info = data;
                            //申请头像
                            $('.user_icon>img').attr('src',data.realName);
                            //申请人用户名
                            $('.purchase_proc>span:first').html(data.category.createBy);
                            //申请人等待谁审批
                            if(!tool.validate.isNull(data.assignee)){
                                if(data.category.status === '7'){
                                    $('.purchase_proc>span:last').html('采购已完成');
                                }else{
                                    $('.purchase_proc>span:last').html('等待'+data.assignee+'审批');
                                }
                            }
                            // 审批编号、所在部门、申请理由、期望日期
                            var contentTitle = $('.content_title:first').show();
                            contentTitle.find('li:eq(1) span:last').html(data.category.createDate);
                            contentTitle.find('li:eq(2) span:last').html(data.applDepar);
                            contentTitle.find('li:eq(3) span:last').html(data.category.reason);
                            contentTitle.find('li:eq(4) span:last').html(data.category.expectDate);
                            $('.content_title:first').show();
                            //询价前展示
                            var invBefore = $('.purchase_price_before').show();
                            invBefore.children('.c_y').html('¥'+data.category.beforeTotalPrice);

                            var purchaseListHtml = view.handler.getPurchaseDetail(data.category.categoryItems);
                            invBefore.next('.content_purchase_list').html(purchaseListHtml[0]).show();
                            //询价后展示
                            if(!tool.validate.isNull(data.category.afterTotalPrice)){
                                // 库存状态、采购总价、采购渠道、支付方式、备注说明
                                contentTitle = $('.content_title:last').show();
                                contentTitle.find('li:eq(0) span:last').html(data.category.inventoryStatus===0?'库存不足':'库存充足');
                                contentTitle.find('li:eq(1) span:last').html(data.category.afterTotalPrice);
                                contentTitle.find('li:eq(2) span:last').html(data.category.channels);
                                if(tool.validate.isNull(data.category.payment)){
                                    contentTitle.find('li:eq(3)').hide();
                                }else{
                                    contentTitle.find('li:eq(3) span:last').html(data.category.payment);
                                }
                                if(tool.validate.isNull(data.category.remarks)){
                                    contentTitle.find('li:eq(4)').hide();
                                }else{
                                    contentTitle.find('li:eq(4) span:last').html(data.category.remarks);
                                }
                                invBefore = $('.purchase_price_after').show();
                                invBefore.children('.c_y').html('¥'+data.category.afterTotalPrice);
                                invBefore.next('.content_purchase_list').html(purchaseListHtml[1]).show();
                            }
                            //审批进度
                            view.handler.getPurchaseProgress(data.comments);
                            //绑定页面事件
                            view.onFun();
                            //底部按钮
                            var html = [];
                            //状态为未发起  并且申请人id不等于用户id
                            if(data.category.status === '0' && data.category.userId === view.data.userInfo.userId){
                                html.push(view.handler.getSpan('修改申请',view.handler.updateApplication,'bg_fc'));
                                html.push(view.handler.getSpan('提交申请',view.handler.submitApplication));
                            }
                            //分管领导
                            else if((data.category.status === '1' || data.category.status === '2' || data.category.status === '3') && data.category.userId != view.data.userInfo.userId){
                                html.push(view.handler.getSpan('驳回',view.handler.approveTask,'bg_fc'));
                                html.push(view.handler.getSpan('同意',view.handler.approveTask));
                            }
                            //采购专员
                            else if(data.category.status === '4' && view.data.userInfo.positionName === '采购专员'){
                                html.push(view.handler.getSpan('询价',view.handler.toInquiry));
                            }
                            //财务
                            else if(data.category.status === '5' && view.data.userInfo.positionName === '出纳'){
                                html.push(view.handler.getSpan('通知领款',view.handler.sendMessage));
                            }
                            //出纳通知采购专员
                            else if(data.category.status === '6' && view.data.userInfo.positionName === '采购专员'){
                                //html.push(view.handler.getSpan('已放款，去采购',view.handler.sendMessage));
                                html.push(view.handler.getSpan('采购完成，通知申请人',view.handler.sendMessage));
                            }
                            //采购专员发起 通知领款人
                            else if(data.category.status === '7' && view.data.userInfo.positionName === '采购专员'){
                                html.push(view.handler.getSpan('采购完成，通知申请人',view.handler.sendMessage));
                            }
                            $('.btn_submit').html(html).data({id:data.category.id,taskId:data.taskId});
                        }else{
                            tool.box.showTip(data.result.msg);
                        }
                    },
                    err:function(){
                        tool.box.showTip(data.result.msg);
                    }
                });
            },
            //采购物品详细信息
            getPurchaseDetail:function(param){
                var html = [],html1=[],liHtml='',liHtmlModel = '\
                        <div class="clerafix b_n">\
                            <i class="rotate0"></i>\
                            <p>#itemName#</p>\
                            <span>&times;#needcount#</span>\
                        </div>\
                        <ul class="content_purchase_detail">\
                            <li>\
                                <span>物品名称：</span>\
                                <span>#itemName#</span>\
                            </li>\
                            <li>\
                                <span>物品分类：</span>\
                                <span>#classifyId#</span>\
                            </li>\
                            <li>\
                                <span>规格：</span>\
                                <span>#model#</span>\
                            </li>\
                            <li>\
                                <span>物品单价：</span>\
                                <span>#applyPrice#</span>\
                            </li>\
                            <li>\
                                <span>物品数量：</span>\
                                <span>#needcount#</span>\
                            </li>#count#\
                            <li>\
                                <span>备注说明：</span>\
                                <span>#itemRemarks#</span>\
                            </li>\
                        </ul>';
                $.each(param,function (i,n) {
                    liHtml = liHtmlModel.replace('#itemName#',n.itemName)
                        .replace('#needcount#',n.needcount)
                        .replace('#itemName#',n.itemName)
                        .replace('#classifyId#',n.classifyName)
                        .replace('#model#',n.model)
                        .replace('#applyPrice#',n.applyPrice)
                        .replace('#needcount#',n.needcount)
                        .replace('#count#','')
                        .replace('#itemRemarks#',n.itemRemarks);
                    html.push($('<li>',{html: liHtml}));
                    if(!tool.validate.isNull(n.actualPrice)){
                        liHtml = liHtmlModel.replace('#itemName#',n.itemName)
                            .replace('#needcount#',n.count)
                            .replace('#itemName#',n.itemName)
                            .replace('#classifyId#',n.classifyName)
                            .replace('#model#',n.model)
                            .replace('#applyPrice#',n.actualPrice)
                            .replace('#needcount#',n.needcount)
                            .replace('#count#','\
                                <li>\
                                    <span>库存数量：</span>\
                                    <span>'+n.stock+'</span>\
                                </li>')
                            .replace('#itemRemarks#',n.itemRemarks);
                        html1.push($('<li>',{html: liHtml}));
                    }
                });
                return [html,html1];
            },
            //审批进程
            getPurchaseProgress:function(param){
                var html = [];
                $.each(param,function (i,n) {
                    html.push($('<li>',{
                        html: '\
                            <p class="clerafix">\
                                <span>'+n.userName+'</span>\
                                <span>'+(i === 0?'':n.time)+'</span>\
                                </p>\
                            <p>'+n.status+'</p>'
                    }));
                });
                $('.content_purchase_progress').html(html);
            },
            //获取对应html
            getSpan:function (html,click,_class) {
                return $('<span>',{
                    html: html,
                    class: tool.validate.isNull(_class) ? '' : _class,
                    click: function () {
                        if (click) {
                            click(this,html);
                        }
                    }
                });
            },
            //提交申请
            submitApplication:function (_this,html) {
                var param = {
                    formId: $(_this).parent().data().id,
                    outcome: html,
                    comment: ""
                };
                tool.ajax(param,{
                    url:'/app/submitCateGoryTask',
                    suc:function(data){
                        if(data.result.code === '0'){
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_suc.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                            view.data.approval = {
                                type:'8',
                                title:'我发起的'
                            };
                            tool.handler.setSession(view.data);
                            setTimeout(function () {
                                tool.handler.changePage('../work/approval.html');
                            },1000);
                        }else{
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_error.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                        }
                    }
                });
            },
            //同意驳回
            approveTask:function(_this,html){
                if(html === '驳回'){
                    view.handler.showCancleReason();
                    //点击取消
                    $('.cancel').on('click',function(){
                        $('#boxMask').hide()
                    });
                    //点击提交,把驳回理由传到后台
                    $('.submit').on('click',function(){
                        if($('textarea').val()!==''){
                            view.handler.agreeUpdate(html,_this);
                        }else{
                            tool.box.showTip('请输入驳回理由');
                        }
                    });
                }else{
                    view.handler.agreeUpdate(html,_this);
                }
            },
            //同意或者驳回事件
            agreeUpdate:function (html,_this) {
                var param = {workflowContent:[{
                    formId: $(_this).parent().data().id,
                    outcome: html,
                    taskId:$(_this).parent().data().taskId,
                    comment: $('textarea').val() || ""
                }]};
                tool.ajax(param,{
                    url:'/app/approveTask',
                    suc:function(data){
                        if(data.result.code === '0'){
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_suc.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                            view.data.approval = {
                                type:'1',
                                title:'待审批'
                            };
                            tool.handler.setSession(view.data);
                            setTimeout(function () {
                                tool.handler.changePage('../work/approval.html');
                            },1000);
                        }else{
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_error.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                        }
                    }
                });
            },
            //修改申请
            updateApplication:function () {
                tool.handler.setSession(view.data);
                tool.handler.changePage('purchase.html');
            },
            //采购专员去询价
            toInquiry:function () {
                tool.handler.setSession(view.data);
                tool.handler.changePage('inquiry.html');
            },
            //发送通知
            sendMessage:function () {
                var param = {
                    id:view.data.purchaseDetail.info.category.id,
                    status:view.data.purchaseDetail.info.category.status
                };
                tool.ajax(param,{
                    url:'/app/sendMessage',
                    suc:function(data){
                        if(data.result.code === '0'){
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_suc.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                            view.data.approval = {
                                type:'6',
                                title:'采购'
                            };
                            tool.handler.setSession(view.data);
                            setTimeout(function () {
                                tool.handler.changePage('../work/approval.html');
                            },1000);
                        }else{
                            var html = '\
                                <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_error.png"/></p>\
                                <p>'+data.result.msg+'</p>';
                            tool.box.showTip(html);
                        }
                    }
                });
            }
        },
        //页面绑定事件
        onFun:function(){
            $('.content_purchase_list>li>div').on('click',function(){
                var i = $(this).children('i');
                if(i.hasClass('rotate90')){
                    i.parent().removeClass('b_n');
                    i.removeClass('rotate90').addClass('rotate0');
                    $(this).next('.content_purchase_detail').slideUp(300);
                }else if(i.hasClass('rotate0')){
                    i.parent().addClass('b_n');
                    i.removeClass('rotate0').addClass('rotate90');
                    $(this).next('.content_purchase_detail').slideDown(300);
                }else{
                    i.parent().addClass('b_n');
                    i.addClass('rotate90');
                    $(this).next('.content_purchase_detail').slideDown(300);
                }
            });
        }
    };
    $.extend(exports,view);
});