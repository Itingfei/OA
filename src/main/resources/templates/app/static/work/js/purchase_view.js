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
            header:'审批预览',
            tip:{
                t01 : "采购申请已提交"
            }
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
            //初始化页面样式
            initPage:function(){
                tool.handler.getHeader({
                    title:view.conf.header
                });
                //ajax
                var param = {
                    id:sessionStorage.getItem('formInfo')
                }
                view.data.formId = param.id;
                console.log(param);
                tool.ajax(param,{
                    url:'/app/categoryDetail',
                    suc:function(data){
                        console.log(data);
                        view.data.taskId = data.body.taskId;
                        var categoryItems = data.body.category;
                        var goodList = data.body.category.categoryItems[0];
                        if(data.result.code === '0'){
                            $('.purchase_proc span').html(data.body.realName);
                            $('.content_title li:eq(1) span:last').html(data.body.applDepar);
                            $('.content_title li:eq(2) span:last').html(data.body.category.reason);
                            $('.content_title li:eq(3) span:last').html(data.body.category.expectDate);
                            $('.purchase_price_before>span:last').html('￥'+data.body.category.beforeTotalPrice);
                            var arr = data.body.category.categoryItems;
                            var html = '';
                            for (var i = 0; i < arr.length; i++) {
                                html +='<li>\
                                            <div class="clerafix b_n">\
                                                <i class="rotate90"></i>\
                                                <p>'+arr[i].itemName+'</p>\
                                                <span>&times;'+arr[i].needcount+'</span>\
                                            </div>\
                                            <ul class="content_purchase_detail">\
                                                <li>\
                                                <span>物品名称：</span>\
                                                <span>'+arr[i].itemName+'</span>\
                                                </li>\
                                                <li>\
                                                <span>物品分类：</span>\
                                                <span>'+arr[i].classifyId+'</span>\
                                                </li>\
                                                <li>\
                                                <span>规格：</span>\
                                                <span>'+arr[i].model+'</span>\
                                                </li>\
                                                <li>\
                                                <span>物品单价：</span>\
                                                <span>'+arr[i].applyPrice+'</span>\
                                                </li>\
                                                <li>\
                                                <span>物品数量：</span>\
                                                <span>'+arr[i].needcount+'</span>\
                                                </li>\
                                                <li>\
                                                <span>备注说明：</span>\
                                                <span>'+arr[i].itemRemarks+'</span>\
                                                </li>\
                                            </ul>\
                                        </li>'
                            }
                            $('.content_purchase_list').append(html);
                            $('.content_purchase_list>li').on('click',function(){
                                var i = $(this).children('div:first').children('i');
                                if(i.hasClass('rotate90')){
                                    i.parent().removeClass('b_n');
                                    i.removeClass('rotate90').addClass('rotate0');
                                    $(this).children('.content_purchase_detail').slideUp(300);
                                }else if(i.hasClass('rotate0')){
                                    i.parent().addClass('b_n');
                                    i.removeClass('rotate0').addClass('rotate90');
                                    $(this).children('.content_purchase_detail').slideDown(300);
                                }else{
                                    i.parent().addClass('b_n');
                                    i.addClass('rotate90');
                                    $(this).children('.content_purchase_detail').slideDown(300);
                                }
                            });
                            // $('.content_purchase_list').append(html);
                        }else if(data.result.code === '7' || data.result.code === '8'){
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
        },
        //页面事件处理程序
        handler:{

        },
        //页面绑定事件
        onFun:function(){
            // $('.content_purchase_list>li').on('click',function(){
            //     var i = $(this).children('div:first').children('i');
            //     if(i.hasClass('rotate90')){
            //         i.parent().removeClass('b_n');
            //         i.removeClass('rotate90').addClass('rotate0');
            //         $(this).children('.content_purchase_detail').slideUp(300);
            //     }else if(i.hasClass('rotate0')){
            //         i.parent().addClass('b_n');
            //         i.removeClass('rotate0').addClass('rotate90');
            //         $(this).children('.content_purchase_detail').slideDown(300);
            //     }else{
            //         i.parent().addClass('b_n');
            //         i.addClass('rotate90');
            //         $(this).children('.content_purchase_detail').slideDown(300);
            //     }
            // });
            //点击修改申请，返回到申请采购页面
            $('.btn_submit span:first').on('click',function(){
                // tool.handler.changePage('./purchase.html')
                window.history.back();
            });
            //点击提交申请，跳转到采购详情页面
            $('.btn_submit span:last').on('click',function(){
                var param = {
                    outcome: $(this).html(),
                    formId: view.data.formId,
                    comment:''
                }
                console.log(param);
                tool.ajax(param,{
                    url:'/app/submitCateGoryTask',
                    suc:function(data){
                        console.log(data);
                        //缓存id
                        sessionStorage.setItem('categoryId',data.body);
                        if(data.result.code === '0'){
                            tool.handler.changePage('./purchase_detail.html');
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
            });
        }
    };
    $.extend(exports,view);
});