/**
 * @description		+首页页面js
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
            header:'发起审批',
            tip:{}
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
            },
            //初始化页面数据
            resize:function(){

            }
        },
        //页面绑定事件
        onFun:function(){
            $('.purchase').on('click',function(){
                tool.handler.changePage('../work/purchase.html');
            });
        },
        //页面事件处理程序
        handler:{
            /*
            * 加载时讯
            */
            getNewsletter:function(){

            }
        }
    };
    $.extend(exports,view);
});