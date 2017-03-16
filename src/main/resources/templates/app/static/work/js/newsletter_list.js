/**
 * Created by dongao on 2016/9/14.
 */
/**
 * @description		时讯详情页面js
 * @author
 * @date:
 * @modify
 * @version 		    1.0.0
 */
define(function(require, exports, module) {
    var tool = require('tool');
    var view = {
        //页面临时数据
        data: {},
        //页面配置项
        conf:{
            title:'东奥快讯'
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
                    title:view.conf.title,
                });
                //tool.handler.getContent('wait');
                /*setTimeout(function(){
                 tool.handler.contentHide();
                 },3000);*/
            },
            //初始化页面数据
            resize:function(){
            }
        },
        //页面绑定事件
        onFun:function(){
            //列表，进入快讯详情页面
            $('.content_nav_list>li').click(function(){
                tool.handler.changePage('newsletter.html');
            })

        },
        //页面事件处理程序
        handler:{
            /*
             * 加载时讯
             */
            getNewsletter:function(){

            },

        }
    };
    $.extend(exports,view);
});