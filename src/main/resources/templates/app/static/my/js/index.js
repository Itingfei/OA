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
            header:'我的',
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
                tool.handler.getFooter('my');
            },
            //初始化页面数据
            initInfo:function(){
                view.data = tool.handler.getSession();
                var $pro = $('.product');
                $pro.find('.name').html(view.data.userInfo.roleName);
                $pro.find('.home').html(view.data.userInfo.orgName);
            }
        },
        //页面绑定事件
        onFun:function(){
            //点击我的资料
            $('.myProfile').click(function(){
                view.data.userDetailId = view.data.userInfo.userId;
                tool.handler.setSession(view.data);
                tool.handler.changePage('profile.html');
            });
            //点击修改密码
            $('.changepwd').click(function(){
                tool.handler.changePage('../login/changepwd.html');
            });
            //点击清楚缓存事件
            //$('.clear').click(function(){
            //    tool.box.show('<div class="clearTip" style="position:absolute; bottom: 0; width: 100%">\
            //        <p>确认清理</p>\
            //        <p class="cancel">取消</p>\
            //        </div>');
            //    //点击取消隐藏
            //    $('.cancel').click(function(){
            //        tool.box.hide();
            //    })
            //})
            //退出登录
            $('.quit').on('click',function(){
                tool.handler.setSession({});
                tool.handler.changePage('../login/login.html')
            })
        },
        //页面事件处理程序
        handler:{
        }
    };
    $.extend(exports,view);
});