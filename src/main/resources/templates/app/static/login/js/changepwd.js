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
            header:'修改密码',
            tip:{
                t01: '请输入当前登录密码',
                t02: '请输入新密码',
                t03: '新密码请输入6-16个字符',
                t04: '您两次输入的密码不一致',
                t05: '当前登录密码错误',
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
            }
        },
        //页面绑定事件
        onFun:function(){
            //点击确认修改
            $('.changepwd_btn').on('click',function(){
                view.handler.submit();
            });
            $('input').on('input',function(){
                view.handler.input();
            });
            $('input').on('keyup',function(){
                view.handler.keyup();
                if($(this).val()===""){
                    $(this).siblings('.close').hide();
                }
            });
            $('.close').on('click',function(){
                $(this).siblings('input').val('');
                $(this).hide();
            });
            $('input').on('click',function(){
                $('.close').hide();
                if($(this).val()!==""){
                    $(this).siblings('.close').show();
                }
            });
        },
        //页面事件处理程序
        handler:{
            //input事件
            input:function(){
                var count = 0;
                $('input').each(function(k,v){
                    if(!tool.validate.isNull(this.value)){
                        count ++;
                    }
                });
                if(count === 3){
                    $('.btn_blue').removeClass('changepwd_btn');
                }else{
                    $('.btn_blue').addClass('changepwd_btn');
                }
            },
            keyup:function(){
                $('input').each(function(k,v){
                    if(!tool.validate.isNull(this.value)){
                        $(this).parent().siblings().find('.close').hide();
                        $(this).siblings('.close').show();
                    }
                });
            },
            //提交事件
            submit: function () {
                var againpwd = $('.againpwd>input').val();
                var param = {
                    passwordOld: $('.currentpwd>input').val(),
                    passwordNew: $('.newpwd>input').val()
                };
                if (tool.validate.isNull(param.passwordOld)) {
                    tool.box.showTip(view.conf.tip.t01);
                } else if (tool.validate.isNull(param.passwordNew)) {
                    tool.box.showTip(view.conf.tip.t02);
                } else if (tool.validate.checkPassword(param.passwordNew, [view.conf.tip.t02, view.conf.tip.t03]) != true) {
                    tool.box.showTip(view.conf.tip.t03);
                } else if (param.passwordNew !== againpwd) {
                    tool.box.showTip(view.conf.tip.t04);
                } else {
                    tool.ajax(param, {
                        url: '/app/modifyPass',
                        suc: function (data) {
                            //修改密码成功
                            if (data.result.code === "0") {
                                //更新缓存密码
                                var local = tool.handler.getLocal(tool.conf.name.local);
                                if(!tool.validate.isNull(local) && !tool.validate.isNull(local.date)){
                                    var date = (+new Date());
                                    if((date - local.date) < 1000*86400*15 ){
                                        local.password = param.passwordNew;
                                        tool.handler.setLocal(local);
                                    }
                                }
                                //提示用户更新成功
                                tool.box.showTip(data.result.msg);
                                setTimeout(function(){
                                    tool.handler.changePage('../my/index.html')
                                },1000);
                            }else if(data.result.code === "3" || data.result.code === "8"){
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
        }
    };
    $.extend(exports,view);
});