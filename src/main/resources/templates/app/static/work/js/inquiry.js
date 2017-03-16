/**
 * @description		采购页面js
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
        deleteId:[],
        //页面配置项
        conf:{
            header:'询价',
            tip:{
                t01:'请填写库存状态',
                t02:'请填写采购渠道',
                t03:'请填写实际单价',
                t04:'请填写采购数量',
                t05:'请填写库存数量'
            },
        },
        //初始化
        init: function () {
            this.onFun();
            $.each(view.inits,function(i,n){
                n();
            });
        },
        //页面初始化事件
        inits:{
            //初始化页面样式
            initPage:function(){
                tool.handler.getHeader({
                    title:view.conf.header
                });
            },
            //获取申请采购类型
            initData:function () {
                view.data = tool.handler.getSession();
            },
            //修改返回  回显页面数据
            reloadPage:function () {
                if(!tool.validate.isNull(view.data.purchaseDetail) && !tool.validate.isNull(view.data.purchaseDetail.info)){
                    view.handler.reloadPage(view.data.purchaseDetail.info.category);
                }
            }
        },
        //页面事件处理程序
        handler:{
            //回显页面数据
            reloadPage:function (data) {
                $('.btn_submit').data({id:data.id});
                $('textarea').val(data.reason);
                $('.purchase_date').val(data.expectDate).change();
                $('.afterTotalPrice').val('￥'+Number(data.beforeTotalPrice).toFixed(2));
                var mt1 = {};
                $.each(data.categoryItems,function(i,n){
                    mt1 = $('.mt1:eq('+i+')');
                    if(mt1.length === 0){
                        $('.add_detail').click();
                        mt1 = $('.mt1:eq('+i+')');
                    }
                    mt1.data(n);
                    mt1.find('input.itemName').val(n.itemName);
                    mt1.find('.invoiceTypes').data({id:n.classifyId});
                    mt1.find('.invoiceTypes').prev('.placeholder').html(n.classifyName);
                    mt1.find('input.model').val(n.model);
                    mt1.find('input.applyPrice').val('￥'+n.applyPrice);
                    mt1.find('input.needcount').val(n.needcount);
                    mt1.find('input.itemRemarks').val(n.itemRemarks);
                });
            },
            //物品分类调用后台接口
            showTypePanel:function (_this) {
                if(tool.validate.isNull(view.data.classIfyList)){
                    tool.ajax('',{
                        url:'/app/classifyList',
                        suc:function(data){
                            if(data.result.code === '0'){
                                view.data.classIfyList = data.body;
                                view.handler.drawClassList(_this);
                            }else{
                                tool.box.showTip(data.result.msg);
                            }
                        },
                        err:function(data){
                            tool.box.showTip(data.result.msg);
                        },
                        tot:function (data) {
                            tool.box.showTip(data.result.msg);
                        }
                    });
                }else{
                    view.handler.drawClassList(_this);
                }
            },
            //展示库存提示框
            showInventoryPanel:function (_this) {
                var invents = [{id:0,name:'库存不足'},{id:1,name:'库存充足'}];
                var html = $('<div>',{
                    class:'invoiceTypePanel',
                    html:function(){
                        var htmls = [];
                        htmls.push('<p>请选择</p>');
                        htmls.push($('<ul>',{
                            html:function(){
                                var h = [];
                                $.each(invents,function(i,n){
                                    h.push($('<li>',{
                                        class:$(_this).data().id === n.id?'check':'',
                                        html:n.name,
                                        data:n,
                                        click:function(){
                                            $(this).addClass('check').siblings().removeClass('check');
                                            $(_this).data(n).prev('.placeholder').html(n.name);
                                            tool.box.hide();
                                        }
                                    }));
                                });
                                return h;
                            }
                        }));
                        return htmls;
                    }
                });
                tool.box.show(html);
            },
            //绘制弹出框
            drawClassList:function(_this){
                var html = $('<div>',{
                    class:'invoiceTypePanel',
                    html:function(){
                        var htmls = [];
                        htmls.push('<p>请选择</p>');
                        htmls.push($('<ul>',{
                            html:function(){
                                var h = [];
                                $.each(view.data.classIfyList,function(i,n){
                                    h.push($('<li>',{
                                        class:$(_this).data().id === n.id?'check':'',
                                        html:n.name,
                                        data:n,
                                        click:function(){
                                            $(this).addClass('check').siblings().removeClass('check');
                                            $(_this).data(n).prev('.placeholder').html(n.name);
                                            tool.box.hide();
                                        }
                                    }));
                                });
                                return h;
                            }
                        }));
                        return htmls;
                    }
                });
                tool.box.show(html);
            },
            //提交申请
            submit:function (){
                var param = {
                    workflowContent:[{
                        formId:view.data.purchaseDetail.id,
                        taskId: view.data.purchaseDetail.info.taskId,
                        outcome: "同意",
                        comment: ""
                    }],
                    categoryContent:{
                        id:$('.btn_submit').data().id,
                        afterTotalPrice: $('.afterTotalPrice').val().substr(1),
                        inventoryStatus: $('.inventoryItatus').data().id,
                        channels: $('.channels').val(),
                        payment: $('.payment').val(),
                        categoryItems:[]
                    }
                }
                if(tool.validate.isNull(param.categoryContent.inventoryStatus)){
                    tool.box.showTip(view.conf.tip.t01);
                }else if(tool.validate.isNull(param.categoryContent.channels)){
                    tool.box.showTip(view.conf.tip.t02);
                }else{
                    var purcha = {},tip='',isPass=true;
                    $('.mt1').each(function(i,v){
                        v = $(v);
                        purcha = {
                            actualPrice:v.find('.actualPrice').val(),
                            count:v.find('.needcount').val(),
                            stock:v.find('.stock').val()
                        };
                        if(tool.validate.isNull(purcha.actualPrice) || purcha.actualPrice === '0'){
                            tip = view.conf.tip.t03;
                        }else if(tool.validate.isNull(purcha.count)){
                            tip = view.conf.tip.t04;
                        }else if(tool.validate.isNull(purcha.stock)){
                            tip = view.conf.tip.t05;
                        }
                        if(tool.validate.isNull(tip)){
                            purcha = $.extend(v.data(),purcha);
                            param.categoryContent.categoryItems.push(purcha);
                        }else{
                            tip ='采购物品（'+(i+1)+'）<br/>' + tip;
                            return false;
                        }
                    });
                    if(!tool.validate.isNull(tip)){
                        tool.box.showTip(tip);
                        return false;
                    }else{
                        tool.ajax(param,{
                            url:'/app/approveTask',
                            suc:function(data){
                                if(data.result.code === '0'){
                                    var html = '\
                                        <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_suc.png"/></p>\
                                        <p>'+data.result.msg+'</p>';
                                    tool.box.showTip(html);
                                    view.data.approval = {
                                        type:'4',
                                        title:'询价'
                                    };
                                    tool.handler.setSession(view.data);
                                    setTimeout(function () {
                                        tool.handler.changePage('../work/approval.html');
                                    },1000);
                                }else{
                                    var html = '\
                                        <p class="tip_box_theme_a_icon"><img src="../static/common/img/submit_err.png"/></p>\
                                        <p>'+data.result.msg+'</p>';
                                    tool.box.showTip(data.result.msg);
                                }
                            },
                            err:function(data){
                                tool.box.showTip(data.result.msg);
                            },
                            tot:function (data) {
                                tool.box.showTip(data.result.msg);
                            }
                        });
                    }
                }
            },
            //计算总价
            calcTotalPrice:function () {
                //在点击-的时候，更新总价的数据
                var totalPrice = 0,price=0,num=0;
                $('.mt1').each(function(i,n){
                    price = $(n).find('input.actualPrice').val();
                    num = $(n).find('input.needcount').val();
                    totalPrice += Number(tool.validate.isNull(price)?$(n).find('input.applyPrice').val().slice(1):price) * Number(tool.validate.isNull(num)?0:num);
                });
                //采购总价
                $('.afterTotalPrice').val( '￥' + totalPrice.toFixed(2));
            }
        },
        //页面绑定事件
        onFun:function(){
            //点击+或者-事件
            $('.page').on('click','.calc_btn',function(e){
                if(e.target.tagName === 'SPAN'){
                    var input = $(this).find('input');
                    var val = input.val();
                    if($(e.target).html() === '-'){
                        val = parseInt(input.val())-1;
                        if(val < 0){
                            val = 0;
                        }
                    }else if($(e.target).html() === '+'){
                        val = parseInt(input.val())+1;
                        if(val > 999){
                            val = 999;
                        }
                    }
                    input.val(val);
                    view.handler.calcTotalPrice();
                }
            });
            //绑定数字input校验事件
            $('.page').on('keyup','input[type=number]',function () {
                if(isNaN(this.value)){
                    this.value = this.value.slice(0,-1);
                }else{
                    var value = Number(this.value);
                    if(value > 1 && this.value.slice(0,1) === '0'){
                        this.value = value;
                    }
                    if(this.value.indexOf('.') > -1 && this.value.slice(this.value.indexOf('.')+1).length > 2){
                        this.value = this.value.slice(0,-1);
                    }
                    if($(this).hasClass('num') && value > 999){
                        this.value = 999;
                    }else if(value > 99999999){
                        this.value = 99999999;
                    }
                }
                view.handler.calcTotalPrice();
            });
            //增加明细功能
            $('.add_detail').on('click',function(){
                var $lastMt1 = $('<ul>',{
                    class:'mt1',
                    html:$('.mt1:first').html()
                });
                $('.box').append($lastMt1);
                $lastMt1.find('.placeholder').html('（必填）');
                $lastMt1.find('.detatil_title>.fl').html('采购物品（'+$('.mt1').length+'）').after($('<p>',{
                    class:'delete',
                    html:'删除',
                    click:function () {
                        if(!tool.validate.isNull($lastMt1.data().id)){
                            view.deleteId.push($lastMt1.data().id);
                        }
                        $lastMt1.remove();
                        view.handler.calcTotalPrice();
                        $('.mt1 .detatil_title>.fl').each(function (i,n) {
                            $(this).html('采购物品（'+(i+1)+'）');
                        });
                    }
                }));
            });
            $('.page').on('blur','input[type=number]',function () {
                if(tool.validate.isNull(this.value)){
                    this.value = 0;
                }
            });
            //类型
            $('.page').on('click','.invoiceTypes',function(){
                view.handler.showTypePanel(this);
            });
            //库存状态
            $('.inventoryItatus').on('click',function () {
                view.handler.showInventoryPanel(this);
            });
            //点击确定，跳转到审批预览页面
            $('.btn_submit').on('click',function(){
                view.handler.submit();
            });
        }
    };
    $.extend(exports,view);
});