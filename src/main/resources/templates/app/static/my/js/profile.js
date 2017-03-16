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
            header:'我的资料',
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
            initInfo:function(){
                //从登录页面里取出用户的信息
                view.data = tool.handler.getSession();
                if(view.data.userDetailId === view.data.userInfo.userId){
                    view.handler.initInfo(view.data.userInfo)
                }else{
                    view.handler.getUserInfo(view.data.userDetailId);
                }
            }
        },
        //页面绑定事件
        onFun:function(){

        },
        //页面事件处理程序
        handler:{
            initInfo:function (data) {
                $('.info>li:eq(0)>span:last').html(data.real_name);
                $('.info>li:eq(1)>span:last').html(data.gender);
                $('.info>li:eq(2)>span:last').html(data.orgName);
                $('.info>li:eq(3)>span:last').html(data.positionName);
                $('.info>li:eq(4)>span:last').html(data.birthday);
                $('.other_info>li:eq(0)>span:last').html(data.link_phone);
                $('.other_info>li:eq(1)>span:last').html(data.email);
                $('.other_info>li:eq(2)>span:last').html(data.entryDate);
            },
            getUserInfo:function (id) {
                tool.ajax({id:id},{
                    url:'/app/userDetail',
                    suc:function(data){
                        if(data.result.code === '0'){
                            view.handler.initInfo(data.body);
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
            }
        }
    };
    $.extend(exports,view);
});