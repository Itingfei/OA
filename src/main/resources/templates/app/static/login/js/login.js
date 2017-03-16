/**
 * @description		登录页面js
 * @author 			zwr
 * @date:			    2016-8-15
 * @modify:
 * @version 		    1.0.0
 */
define(function(require, exports, module) {
    var tool = require('tool');
    var login = {
        //页面临时数据
        data: {},
        //页面配置项
        conf:{
            tip:{
                t01:'请输入用户名',
                t02:'请输入登录密码',
                t03:'密码格式不正确',
                t04:'用户名或密码有误',
                t05:'用户名格式不正确',
                t06:''
            }
        },
        //初始化
        init: function () {
            $.each(login.intes,function(i,n){
                n();
            });
            this.onFun();
        },
        //页面初始化事件
        intes:{
            //初始化页面样式
            initStyle:function(){
                $('style:first').append('.tip_box_theme_a{top:'+($('.login_title').offset().top+5)+'px;width:11rem;}');
            },
            //初始化页面数据
            initData:function(){
                var local = tool.handler.getLocal(tool.conf.name.local);
                if(!tool.validate.isNull(local) && !tool.validate.isNull(local.date)){
                    var date = (+new Date());
                    if((date - local.date) < 1000*86400*15 ){
                        $('input[type=text]').val(local.userName);
                        $('input[type=password]').val(local.password);
                        $('.checkbox:first').addClass('checkbox_check');
                        $('.login_btn_submit_no').removeClass('login_btn_submit_no');
                    }
                }
            },
            //与APP通信
            callApp:function(){
                if(tool.handler.getAppType().type != 'iphone'){
                    return false;
                }
                /*与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS*/
                setupWebViewJavascriptBridge(function(bridge) {
                    /*
                     * app调用js
                     * param: 方法名，回调事件：（回调参数，回调事件）
                     **/
                    //ios改变header栏
                    bridge.registerHandler('getJSRegisterInfoHandler',function(data, responseCallback) {
                        var responseData = {'jsRegisterList':[{'scheme':'da-oa-app','target':'changeAppStatusColor','action':'ChangeStatus','transformType':'2'}]};
                        responseCallback(responseData);
                    });
                    /*
                     * js调用app
                     * param: 方法名，回调事件：（回调参数，回调事件）
                     */
                    bridge.callHandler('ChangeStatus', {'color':'0'}, function(response) {});
                });
            }
        },
        //页面绑定事件
        onFun:function(){
            $('input').on('input',function(){
                login.handler.input();
            });
            $('#login_btn').on('click',function(){
                login.handler.submit();
            });
        },
        //页面事件处理程序
        handler:{
            //input事件
            input:function (){
                var count = 0;
                $('input').each(function(i,n){
                    if(tool.validate.isNull(this.value)){
                        count ++;
                    }
                });
                if(count === 2){
                    $('#login_btn').addClass('login_btn_submit_no');
                }else{
                    $('#login_btn').removeClass('login_btn_submit_no');
                }
            },
            submit:function (){
                var param = {
                    userName : $('input[type=text]').val(),
                    password : $('input[type=password]').val()
                };
                if(tool.validate.isNull(param.userName)){
                    tool.box.showTip(login.conf.tip.t01);
                }
                // else if(param.userName.length < 6){
                //     tool.box.showTip(login.conf.tip.t05);
                // }
                else if(tool.validate.checkPassword(param.password,[login.conf.tip.t02,login.conf.tip.t03]) != true){
                    tool.box.showTip(login.conf.tip.t03);
                }else{
                    tool.ajax(param,{
                        url:'/appLogin',
                        suc:function(data){
                            //存储用户的信息
                            if(data.result.code === '0'){
                                if($('.checkbox:first').hasClass('checkbox_check')){
                                    param.date = (+new Date());
                                    tool.handler.setLocal(param);
                                }
                                login.data.userInfo = data.body;
                                tool.handler.setSession(login.data);
                                tool.handler.changePage('../work/index.html');
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
        }
    };
    $.extend(exports,login);
});