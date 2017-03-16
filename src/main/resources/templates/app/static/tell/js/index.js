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
            header:'通讯录',
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
                    back:false,
                    title:view.conf.header
                });
                tool.handler.getFooter('tell');
                //获取通讯录列表
                view.handler.getUserList({name:''});
            },
            initData:function () {
                view.data = tool.handler.getSession();
            }
        },
        //页面绑定事件
        onFun:function(){
            //点击字母，对应分组的的通讯名单置顶
            $('.wordList').on('click',function(e){
                var range = e.pageY - $(this).offset().top;
                var index = parseInt(range / ($(this).height() / 23));
                var words = ['A','B','C','D','F','G', 'H','J','K','L','M', 'Q','R','S','T','W','X','Y','Z','#'];
                var word = words[index];
                $('.word .group').each(function (k,v) {
                    if (word == v.innerHTML) {
                        $('body').animate({scrollTop: $(v).offset().top - 60 }, 300);
                    }
                })
            });
            //tab切换
            $('.sort>div').on('click',function() {
                $(this).addClass('current').siblings().removeClass('current');
                var index = $(this).index();
                $(".list>div").eq(index).addClass("selected").siblings().removeClass("selected");
                if(index > 0){
                    view.handler.getDepUserList();
                }
            });
            //点击部门，对应的人员显示
            $('.depart').on('click','.title',function() {
                $(this).next().slideToggle();
            });
            //输入内容，搜索按钮亮起
            $('.search_input input').on({
                input:function() {
                    if($(this).val() !== ""){
                        $('.search_text').css('color','#31A5E0')
                    }else{
                        $('.search_text').css('color','#A0A0A4')
                    }
                },
                keydown:function (e) {
                    if(e.keyCode === 13){
                        $(this).blur();
                        var param = {
                            name: this.value
                        };
                        view.handler.getUserList(param);
                    }
                }
            });
            //点击返回到当前页面
            $('.arrow').on('click',function() {
                view.handler.hideSearchPanel();
            });
            //搜索栏键盘按下事件
            /*$('.search input').on('keyup',function() {
                var that = this;
                //获取文本框的值
                var result = $(this).val();
                var param = {
                    name: result
                };
                tool.ajax(param,{
                    url:'/app/bookSortLetters',
                    suc:function(data){
                        if(data.result.code === '0'){
                            //如果没有检索到数据，不生成div
                            if(data.body.length == 0) {
                                return;
                            }
                            //如果文本框内没有内容，不生成div
                            if($(that).val().length == 0) {
                                $(".search_border").hide();
                                return;
                            }
                            if(data.body.length>0){
                                var e = data.body[0].name;
                                e = e.split(",");
                                //模糊搜索
                                // var popDiv = $(".search_border");
                                // // 判断页面上是否存在pop的div
                                // if(!$(".search_border").length==0) {
                                //     //如果存在，删除div继续往后执行
                                //     $(".content").remove($(".search_border"));
                                // }
                                //遍历数据，生成列表
                                var ul_ = document.createElement('ul');
                                $('.content').append(ul_);
                                $('.content>ul').addClass('search_border');
                                for(var i = 0; i < e.length; i++) {
                                    var li_ = document.createElement("li");
                                    $(li_).html(e[i]);
                                    $('.search_border').append(li_);
                                }
                                //点击的人，把它放到文本框里
                                $('.search_border>li').on('click',function(){
                                    $('.search_input>input').val($(this).html());
                                    $('.search_border').hide();
                                })
                            }

                        }else if(data.result.code === '7' || data.result.code === '8'){
                            tool.box.showTip(data.result.msg);
                        }
                    },
                    err:function(){
                    }
                });
            });*/
            //文本框获取焦点，显示返回按钮和搜索按钮
            $('.search_input input').on('focus',function() {
                view.handler.showSearchPanel();
            });
            $('.search_input>p>span').on('click',function () {
                view.handler.showSearchPanel();
            });
            $('.search_text').on('click',function(){
                var result = $('.search_input input').val();
                var param = {
                    name: result
                };
                view.handler.getUserList(param);
            });
            $('.list').on('click','.name',function () {
                view.data.userDetailId = $(this).data().userId;
                tool.handler.setSession(view.data);
                tool.handler.changePage('../my/profile.html');
            });
        },
        //页面事件处理程序
        handler:{
            //页面显示为搜索状态
            showSearchPanel:function () {
                $('.search_input>p').show();
                $('.search').addClass('search_focus');
                //下面的内容隐藏
                $('.sort').hide();
                $('.list').hide();
            },
            //页面显示为搜索状态
            hideSearchPanel:function () {
                if(tool.validate.isNull($('.search_input input').val())){
                    $('.search_input>p').show();
                }else{
                    $('.search_input>p').hide();
                }
                $('.search').removeClass('search_focus');
                //下面的内容隐藏
                $('.sort').show();
                $('.list').show();
            },
            /*
            * 加载用户列表  按字母分组
            */
            getUserList:function (param) {
                tool.ajax(param || '',{
                    url:'/app/bookSortLetters',
                    suc:function(data){
                        console.log(data);
                        if(data.result.code === '0'){
                            var html = [],lis=[];
                            $.each(data.body,function (i,n) {
                                html.push($('<div>',{
                                    class:'group',
                                    html:i
                                }));
                                html.push($('<ul>',{
                                    html:function(){
                                        lis = [];
                                        $.each(n,function(j,k){
                                            lis.push($('<li>',{
                                                class:'name',
                                                html:'<span class="photo"><img src="'+k.imgUrl+'" alt=""></span>\
                                                      <span class="txt">'+k.realName+'</span>',
                                                data:k
                                            }));
                                        });
                                        return lis;
                                    }
                                }));
                            });
                            $('.word>div:first').html(html);
                            if(!tool.validate.isNull(param)){
                                view.handler.hideSearchPanel();
                            }
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
            },
            getDepUserList:function () {
                if($('.depart').children().length > 0){
                    return false;
                }
                tool.ajax('',{
                    url:'/app/bookDepartment',
                    suc:function(data){
                        if(data.result.code === '0'){
                            var html = [],wrap=[],lis=[];
                            $.each(data.body,function (i,n) {
                                html.push($('<div>',{
                                    class:'wrap',
                                    html:function () {
                                        wrap = [];
                                        wrap.push($('<div>',{class:'title',html:i}));
                                        wrap.push($('<ul>',{
                                            html:function(){
                                                lis = [];
                                                $.each(n,function(j,k){
                                                    lis.push($('<li>',{
                                                        class:'name',
                                                        html:'<span class="photo"><img src="'+k.imgUrl+'" alt=""></span>\
                                                              <span class="txt">'+k.realName+'</span>',
                                                        data:k
                                                    }));
                                                });
                                                return lis;
                                            }
                                        }));
                                        return wrap;
                                    }
                                }));
                            });
                            $('.depart').html(html);
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