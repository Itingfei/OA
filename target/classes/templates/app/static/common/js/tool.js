/**
 * @description		工具类js  依赖jquery
 * @author 			zwr
 * @date:			    2016-8-3
 * @modify:
 * @version 		    1.0.0
 */
define(function(require, exports, module) {
    require('jquery');
    var tool = {
        //版本号
        version:'1.0.0',
        /*
         * 公共配置选项
         */
        conf:{
            tip:{
                t00:'系统繁忙，请稍后再试',
                t01:'ajax请求参数为空',
                t02:'ajax请求出错',
                t03:'ajax请求超时',
            },
            name:{
                local:'data_dongao',
                session:'data_dongao'
            },
            loadtip:{
                net:{
                    tip:'无法连接网络',
                    url:'../static/common/img/load_net.png'
                },
                wait:{
                    tip:'努力加载中<span></span>',
                    url:'../static/common/img/load_wait.png'
                },
                error:{
                    tip:'数据加载失败',
                    url:'../static/common/img/load_error.png'
                },
                noData:{
                    tip:'暂无东奥时讯，敬请期待！',
                    url:'../static/common/img/load_no_data.png'
                }
            },
            footer:{
                work:{
                    tip:'工作',
                    click:function(_this){
                        tool.handler.footerBtnClick(_this,'../work/index.html');
                    }
                },
                news:{
                    tip:'消息',
                    click:function(_this){
                        tool.handler.footerBtnClick(_this,'../news/index.html');
                    }
                },
                tell:{
                    tip:'通讯录',
                    click:function(_this){
                        tool.handler.footerBtnClick(_this,'../tell/index.html');
                    }
                },
                my:{
                    tip:'我的',
                    click:function(_this){
                        tool.handler.footerBtnClick(_this,'../my/index.html');
                    }
                }
            }
        },
        //页面临时数据
        data:{},
        //初始化
        init:function(){
            $.each(tool.inits,function(i,n){
                n();
            });
        },
        /*
         * 公共ajax请求
         */
        ajax:function (param,setting){
            var opt = {
                url:'',           //请求路径
                param:param,         //入参
                bef:function(){},//请求发送前
                tot:function(data){tool.box.showTip(data.result.msg);},//请求超时
                err:function(data){tool.box.showTip(data.result.msg);},//请求错误
                suc:function(){},//请求成功
                cop:function(){},//请求完成
                loadding:true,   //是否展示loadding框
                async:true,      //是否异步
                type:'POST',     //请求类型
                dataType:'json',//交互数据类型
                timeout:60*1000, //请求超时时间
                cache:false      //是否启用缓存
            };
            if(typeof opt.param === 'object'){
                opt.param = JSON.stringify(opt.param);
            }
            $.extend(opt,setting);
            if(tool.validate.isNull(opt.url)){
                tool.box.showTip(tool.conf.tip.t01);
                return false;
            }
            $.ajax({
                url:opt.url,
                data:opt.param,
                dataType:opt.dataType,
                async:opt.async,
                timeout:opt.timeout,
                type:opt.type,
                cache:opt.cache,
                beforeSend:function(XMLHttpRequest){
                    if(opt.loadding){
                        tool.loadding.show();
                    }
                    opt.bef(XMLHttpRequest);
                },
                success:function(data, textStatus){
                    if(data.result.code === '8'){
                        tool.handler.changePage('../login/login.html');
                    }else{
                        opt.suc(data, textStatus);
                    }
                },
                error:function(data, textStatus){
                    data = {
                        result:{
                            code:'t02',
                            msg:tool.conf.tip.t02
                        }
                    };
                    opt.err(data, textStatus);
                },
                complete:function(XMLHttpRequest, textStatus){
                    if(opt.loadding){
                        tool.loadding.hide();
                    }
                    if(textStatus === 'timeout'){
                        var data = {
                            result:{
                                code:'t03',
                                msg:tool.conf.tip.t03
                            }
                        };
                        opt.tot(data, textStatus);
                    }
                    opt.cop(XMLHttpRequest, textStatus);
                }
            });
        },
        /*
         * 提示框
         */
        box:{
            showTip:function(tip,obj){
                var opt = {
                        time:2000,
                        class:'tip_box_theme_a'
                    },
                    box = $('#tipBox');
                $.extend(opt,obj);
                if(box.length === 0){
                    box = $('<div>',{
                        id:'tipBox',
                        html:tip,
                        class:'tip_box_theme_a '+(opt.class === 'tip_box_theme_a'?'':opt.class)
                    });
                    $('body').append(box);
                }else{
                    box.html(tip);
                }
                box.css({
                    'margin-top':-box.innerHeight()/2,
                    'margin-left':-box.innerWidth()/2
                });
                box.fadeIn(300);
                var t = setTimeout(function(){
                    clearTimeout(t);
                    tool.box.hideTip();
                },opt.time);
            },
            //隐藏消息框
            hideTip:function(){
                $('#tipBox').fadeOut(500);
            },
            hide:function(){
                tool.handler.recoveryPageHeight();
                $(document).scrollTop($('#boxMask').hide().data().scrollTop);
            },
            show:function(html){
                var top = $(document).scrollTop();
                tool.handler.setPageHeight();
                var mask = $('#boxMask');
                if(mask.length === 0){
                    mask = $('<div>',{
                        data:{scrollTop:top},
                        id:'boxMask',
                        html:'',
                        class:'box_theme_a',
                        click:function(){
                            tool.box.hide();
                        }
                    });
                    $('body').append(mask);
                    mask.on('click','>div',function(event){
                        event.stopPropagation();
                    });
                }
                mask.html(html).css({
                    display:'-webkit-box'
                });
            }
        },
        /*
         * loadding等待
         */
        loadding:{
            //显示loadding
            show:function(){
                var box = $('#loadding');
                if(box.length === 0){
                    box = $('<div>',{
                        id:'loadding',
                        class:'loadding',
                        html:'<div class="loadding_mask">\
                                   <img src="../static/common/img/load_more.gif"/>\
                               </div>'
                    });
                    $('body').append(box);
                }
                box.css({
                    display:'-webkit-box'
                });
            },
            //隐藏loadding
            hide:function(){
                $('#loadding').hide();
            }
        },
        //初始化执行程序
        inits:{
            //页面公共设置
            setPage:function(){
                document.body.style.webkitUserSelect='none';
                if(tool.handler.getAppType().type === 'iphone'){
                    $('style:first').append('.header{padding-top:2.1rem;}');
                }
            },
            forApp:function () {
                /*这段代码是固定的，必须要放到js中*/
                window.setupWebViewJavascriptBridge = function(callback) {
                    if (window.WebViewJavascriptBridge) {
                        return callback(WebViewJavascriptBridge);
                    }
                    if (window.WVJBCallbacks) {
                        return window.WVJBCallbacks.push(callback);
                    }
                    if(tool.handler.getAppType().type === 'aphone'){
                        document.addEventListener('WebViewJavascriptBridgeReady',function() {
                            callback(WebViewJavascriptBridge)
                        },false);
                        return ;
                    }
                    window.WVJBCallbacks = [callback];
                    var WVJBIframe = document.createElement('iframe');
                    WVJBIframe.style.display = 'none';
                    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
                    document.documentElement.appendChild(WVJBIframe);
                    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 10);
                }
                if(tool.handler.getAppType().type  != 'aphone'){
                    return false;
                }
                //设置安卓返回事件
                setupWebViewJavascriptBridge(function(bridge) {
                    bridge.init(function(message, responseCallback) {
                        responseCallback({});
                    });
                    //安卓返回键
                    bridge.registerHandler("systemBack", function(data, responseCallback) {
                        var h5Back = $('.header .header_back');
                        if(h5Back.length > 0){
                            if(h5Back.hasClass('v_hide')){
                                if(window.location.href.indexOf('work/index.html') > -1){
                                    //退出系统
                                    window.WebViewJavascriptBridge.callHandler('appExit','', function(responseData) {});
                                }else{
                                    window.history.back();
                                }
                            }else{
                                h5Back.click();
                            }
                        }else if(window.location.href.indexOf('login/login.html') > -1){
                            //退出系统
                            window.WebViewJavascriptBridge.callHandler('appExit','', function(responseData) {});
                        }else{
                            window.history.back();
                        }
                    });
                });
            },
            onFun:function(){
                //checkbox事件
                $('.checkbox').on('click',function(){
                    $(this).toggleClass('checkbox_check');
                });
                //长按
                $.fn.taphold = function(fn) {
                    var _this = this;
                    var point = {};
                    var timeout = undefined;
                    this[0].addEventListener('touchstart', function(event) {
                        point.x = event.touches[0].pageX;
                        point.y = event.touches[0].clientY;
                        timeout = setTimeout(function(){
                            fn(_this);
                        }, 1000);
                    }, false);
                    this[0].addEventListener('touchmove', function(event) {
                        point.curX = event.touches[0].pageX;
                        point.curY = event.touches[0].clientY;
                        point.moveX = point.curX - point.x;
                        point.moveY = point.curY - point.y;
                        if(Math.abs(point.moveX) > 10 || Math.abs(point.moveY) > 10){
                            clearTimeout(timeout);
                        }
                    }, false);
                    this[0].addEventListener('touchend', function(event) {
                        clearTimeout(timeout);
                    }, false);
                }
                //左右滑动
                $.fn.touchWipe = function(option) {
                    var opt = {
                        width:60, //滑动距离时触发事件
                        move:null,//滑动
                        moveEnd:null//滑动结束
                    };
                    $.extend(opt,option); //配置选项
                    var action = null;//滑动方向
                    var point = {
                        x:0,
                        y:0,
                        curX:0,
                        curY:0,
                        moveX:0,
                        moveY:0
                    };
                    $(this).on('touchstart', function(e) {
                        point.x = e.originalEvent.targetTouches[0].pageX;
                        point.y = e.originalEvent.targetTouches[0].clientY;
                        action = null;
                        $(this).on('touchmove', function(event) {
                            if(action){
                                return false;
                            }
                            point.curX = event.originalEvent.targetTouches[0].pageX;
                            point.curY = event.originalEvent.targetTouches[0].clientY;
                            point.moveX = point.curX - point.x;
                            point.moveY = point.curY - point.y;
                            if(Math.abs(point.moveY) < 10){
                                if (Math.abs(point.moveX)>= opt.width) {
                                    action = point.moveX>0?'right':'left'
                                }
                                if(Math.abs(point.moveX)>30 && opt.move){
                                    opt.move(this,point.moveX,action);
                                }
                            }
                        });
                    });
                    $(this).on('touchend', function(event) {
                        if(opt.moveEnd){
                            opt.moveEnd(this);
                        }
                    });
                    //链式返回
                    return this;
                };
            }
        },
        /*
         * 处理程序
         */
        handler:{
            /*
             * 保存session信息
             */
             setSession:function(data){
                 sessionStorage.setItem(tool.conf.name.session,JSON.stringify(data));
             },
             /*
             * 获取session信息
             */
             getSession:function(){
                return JSON.parse(sessionStorage.getItem(tool.conf.name.session));
             },
            /*
             * 保存session信息
             */
            setLocal:function(data){
                localStorage.setItem(tool.conf.name.local,JSON.stringify(data));
            },
            /*
             * 保存session信息
             */
            getLocal:function(){
                return JSON.parse(localStorage.getItem(tool.conf.name.local));
            },
            /*
             * 跳转页面
             */
            changePage:function(url,data,show){
                if(!((tool.validate.isNull(data) && typeof data === 'boolean' && data === false) || (tool.validate.isNull(show) || !show))){
                    tool.loadding.show();
                }
                var param = tool.validate.isNull(data)?'':'?data='+JSON.stringify(data);
                setTimeout(function(){
                    window.location.href = url + param;
                },10);
            },
            /*
             * 获取页面头部
             */
            getHeader:function(set){
                var opt = {
                    back:{//值为false时 不显示，默认显示
                        call:function(){},//回调事件
                        def:true          //是否使用默认事件
                    },
                    close:false,//值为false时不显示，为true时，显示；为function时 显示并绑定该事件
                    title:'',//内容
                    list:false//值为false时不显示，为function时 显示并绑定该事件
                };
                $.extend(opt,set);
                //设置头栏文字颜色
                if($('.page:first>.header').length === 0){
                    var header = $('<div>',{
                        class:'header clerafix',
                        html:function(){
                            var html = [];
                            //返回按钮
                            html.push($('<span>',{
                                class:'header_back'+(opt.back === false?' v_hide':''),
                                html:'<i></i>',
                                click:function(){
                                    if(opt.back){
                                        if(opt.back.call){
                                            opt.back.call();
                                        }
                                        if(opt.back.def){
                                            window.history.back();
                                        }
                                    }
                                }
                            }));
                            //关闭按钮
                            html.push($('<span>',{
                                class:'header_close'+(opt.close === false?' v_hide':''),
                                html:'<i></i><i></i>',
                                click:function(){
                                    if(typeof opt.close === 'function'){
                                        opt.close();
                                    }
                                }
                            }));
                            //title
                            html.push($('<p>',{
                                class:'header_title',
                                html:opt.title
                            }));
                            //右侧icon
                            html.push($('<span>',{
                                class:'header_list'+(opt.list === false?' v_hide':''),
                                html:'<i></i><i></i>',
                                click:function(){
                                    $('.page:first>.header>.header_list').toggleClass('show');
                                    if(typeof opt.list === 'function'){
                                        opt.list();
                                    }
                                }
                            }));
                            return html;
                        }
                    });
                    $('.page:first').prepend(header);
                    header.siblings('.content').css('margin-top',0);
                }
            },
            /*
             * 获取头部高度
             */
            getHeaderHeight:function(){
                return $('.page>.header').height();
            },
            /*
             * 修改头部显示
             */
            setHeaderTip:function(tip){
                return $('.page>.header>.header_title').html(tip);
            },
            /*
             * 改变icon状态
             */
            headerList:{
                //显示状态
                show:function(){
                    $('.header .header_list').addClass('show');
                },
                //隐藏状态
                hide:function(){
                    $('.header .header_list').removeClass('show');
                }
            },
            /*
             * 获取页面内容  主要为  加载中，加载失败，超时等情况
             * type 类型：,callback：回调事件,notCon：是否不为content
             */
            getContent:function(type,callback,notCon){
                if(type){
                    var content = $('<div>',{
                        id:'loadContent',
                        class:notCon === true?'':'content',
                        html:function(){
                            return '\
                            <div class="con_err_icon">\
                                <img src="'+tool.conf.loadtip[type].url+'"/>\
                            </div>\
                            <p class="con_err_tip">'+tool.conf.loadtip[type].tip+'</p>';
                        }
                    });
                    if(type === 'noData'){
                        return content;
                    }
                    if(callback){
                        content.append($('<p>',{
                            html:'点击屏幕重试',
                            class:'con_err_tip_click',
                            click:function(){
                                callback();
                            }
                        }));
                    }
                    $('.page:first').append(content);
                    content.siblings('.content').hide();
                    if(type === 'wait'){
                        var count = 0,
                            point = ['','.','..','...'];
                        tool.conf.timer = setInterval(function(){
                            $('#loadContent>.con_err_tip>span').html(point[(count%4)]);
                            count++;
                        },300);
                    }
                }
            },
            contentHide:function(){
                clearInterval(tool.conf.timer);
                $('#loadContent').hide().siblings('.content').show();
            },
            /*
             * 获取页面底部
             */
            getFooter:function(type,callback){
                if(type){
                    var footer = $('<ul>',{
                        class:'footer',
                        html:function(){
                            var html = [],
                                li='';
                            $.each(tool.conf.footer,function(i,n){
                                html.push($('<li>',{
                                    class:function(){
                                        if(i === type){
                                            return 'check';
                                        }
                                    },
                                    html:function(){
                                        return '<div><span></span><p>'+ n.tip+'</p></div>';
                                    },
                                    click:function(){
                                        if(callback){
                                            callback(this);
                                        }
                                        n.click(this);
                                    }
                                }));
                            });
                            return html;
                        }
                    });
                    $('.page:first').append(footer).css('padding-bottom','59px');
                }
            },
            /*
             * 页面底部按钮单击事件
             */
            footerBtnClick:function(_this,url){
                if(!$(_this).hasClass('check')){
                    if(!$(_this).hasClass('main')){
                        var cur =  $(_this).siblings('li.check').removeClass('check').find('img:first');
                        if(cur.length > 0){
                            cur.attr('src',cur.attr('src').replace('blue_','gray_'));
                            cur = $(_this).addClass('check').find('img:first');
                            cur.attr('src',cur.attr('src').replace('gray_','blue_'));
                        }
                    }
                    tool.handler.changePage(url);
                }
            },
            //设置页面为一屏高度
            setPageHeight:function(){
                $('.page:first').css({
                    height:$(window).height()-20,
                    'overflow-y':'hidden'
                });
            },
            //恢复页面高度
            recoveryPageHeight:function(){
                $('.page:first').css({
                    height:'auto',
                    'overflow-y':'auto'
                });
            },
            //获取用户终端类型：iphone  aphone
            getAppType:function(){
                var un = navigator.userAgent;
                if(un.indexOf("dongao/") > -1){
                    un = un.slice(un.indexOf("dongao")+7).split('/');
                }else{
                    un = ['',''];
                }
                return {
                    type:un[0],
                    version:un[1]
                }
            }
        },
        /*
         * 检验处理程序
         */
        validate:{
            /*
             * 是否为空字符串 或者空对象或者空数组
             * @param o:对象或字符串或数组
             * @returns {boolean}
             */
            isNull:function(o){
                var isNull = false;
                isNull = $.isEmptyObject(o);
                if(typeof o === 'number'){
                    o = o+'';
                }
                if(typeof o === 'string'){
                    isNull = $.isEmptyObject(o.replace(/[ ]/g, ""));
                }
                return  isNull;
            },
            /*
             * 验证是否为手机号
             * @param no
             */
            isMobileNO:function (no){
                return /13|15|18|17\d{9}/.test(no);
            },
            /*
             * 验证电子邮箱格式
             * @param email
             */
            isEmail:function(email){
                var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                return reg.test(email);
            },
            /*
             * 判断密码是否符合要求
             * @param v t
             * t[0]密码为空 t[1]长度不合理 t[2]密码中含有非法字符 t[3]"密码必须含有数字" t[4]"密码必须含有字母" t[5]"密码必须含有特殊字符"
             * @returns
             */
            checkPassword:function(v,t){
                var numasc = 0;
                var charasc = 0;
                var otherasc = 0;
                if(0 == v.length){
                    return t[0];
                }else if(v.length < 6 || v.length > 20){
                    return t[1];
                }else{
                    var tmp = true;
                    for(var i = 0; i < v.length; i++) {
                        var yes = false;
                        var asciiNumber = v.substr(i, 1).charCodeAt();
                        if(asciiNumber >= 48 && asciiNumber <= 57) {
                            numasc += 1;
                            yes = true;
                        }
                        if((asciiNumber >= 65 && asciiNumber <= 90) || (asciiNumber >= 97 && asciiNumber <= 122)) {
                            charasc += 1;
                            yes = true;
                        }
                        if((asciiNumber >= 33 && asciiNumber <= 47) || (asciiNumber >= 58 && asciiNumber <= 64)
                            || (asciiNumber >= 91 && asciiNumber <= 96) || (asciiNumber >= 123 && asciiNumber <= 126)) {
                            otherasc += 1;
                            yes = true;
                        }
                        if(!yes){
                            tmp = false;
                            break;
                        }
                    }
                    if(t[2] && !tmp){
                        return t[2];
                    }else if(t[3] && 0 == numasc)  {
                        return t[3];
                    }else if(t[4] && 0 == charasc){
                        return t[4];
                    }else if(t[5] && 0 == otherasc){
                        return t[5];
                    }
                }
                return true;
            },
            /*
             * 判断是否为 微信浏览器打开
             * @returns {Boolean} true  是:false  否
             */
            isWeiXin:function(){
                var ua = window.navigator.userAgent.toLowerCase();
                return ua.match(/MicroMessenger/i) == 'micromessenger';
            },
            /*
             * 判断是否为汉字
             * @returns {Boolean} true  是:false  否
             */
            isChiChar:function(val){
                return /^[\u4E00-\u9FA5]+$/.test(val);
            }
        }
    };
    tool.init();
    $.extend(exports,tool);
});