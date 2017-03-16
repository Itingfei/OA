/**
 * @description		工作首页页面js
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
        //初始化
        init: function () {
            $.each(view.inits,function(i,n){
                n();
            });
        },
        //页面初始化事件
        inits:{
            initData:function () {
                view.data = tool.handler.getSession();
            },
            //初始化页面样式
            initPage:function(){
                tool.handler.getHeader({
                    back:false,
                    title:'工作',
                });
                tool.handler.getFooter('work');
                view.handler.getMenusList();
            },
            //与APP通信
            callApp:function(){
                if(tool.validate.isNull(tool.handler.getAppType().type)){
                    return false;
                }
                /*与OC交互的所有JS方法都要放在此处注册，才能调用通过JS调用OC或者让OC调用这里的JS*/
                setupWebViewJavascriptBridge(function(bridge) {
                    /*
                     * app调用js
                     * param: 方法名，回调事件：（回调参数，回调事件）
                     **/
                    bridge.registerHandler('getJSRegisterInfoHandler',function(data, responseCallback) {
                        var responseData = {'jsRegisterList':[{'scheme':'da-oa-app','target':'changeAppStatusColor','action':'ChangeStatus','transformType':'2'}]};
                        responseCallback(responseData);
                    });
                    /*
                     * js调用app
                     * param: 方法名，回调事件：（回调参数，回调事件）
                     */
                    bridge.callHandler('ChangeStatus', {'color':'1'}, function(response) {});
                });
            }
        },
        //页面绑定事件
        onFun:function(){
            //点击东奥快讯的时候，跳转到快讯列表的页面
            $('.newsletter').on('click',function() {
                tool.handler.changePage('./newsletter_list.html');
            });
            //点击东奥快讯下面的标题，调到对应的页面
            $('.content_newsletter_list>li').on('click',function(){
                tool.handler.changePage('./newsletter.html');
            });
            //点击菜单事件
            $('.thing li').on('click',function() {
                var data = $(this).data();
                if (data.id === 10) {
                    tool.handler.changePage('./purchase.html');
                }else{
                    view.data.approval = {
                        type:data.id,
                        title:data.name
                    };
                    tool.handler.setSession(view.data);
                    tool.handler.changePage('./approval.html');
                }
            });
            //点击我发起的（待审批的）
            $('.approval').on('click',function(){
                var data = $(this).data();
                view.data.approval = {
                    type:data.itemCode,
                    title:data.itemName
                };
                tool.handler.setSession(view.data);
                tool.handler.changePage('./approval.html');
            });
            //点击审批事项(我发起的)里的采购，跳转到对应人员的审批（采购）详情
            $('.content_approval_list li').on('click',function(){
                var data = $(this).data();
                view.data.purchaseDetail = {
                    id:data.id+'',
                    title:'采购详情'
                };
                tool.handler.setSession(view.data);
                tool.handler.changePage('./purchase_detail.html');
            });
        },
        //页面事件处理程序
        handler:{
            //菜单栏
            getMenusList:function(param){
                tool.ajax(param || '',{
                    url:'/app/index',
                    suc:function(data){
                        if(data.result.code === '0'){
                            data = data.body;
                            //获取menu列表
                            view.handler.getMenuList(data);
                            //动态显示icon
                            view.handler.getMoreList(data);
                            view.onFun();
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
            },
            getMenuList:function (data) {
                var html = [];
                $.each(data.menus,function (i,n) {
                    html.push($('<li>',{
                            class:'fl',
                            data:n,
                            html:'\
                                <div class="pic">\
                                    <img src='+n.imgUrl+' alt=""/>\
                                    '+(parseInt(n.messageSize)>0?'<span class="count">'+n.messageSize+'</span>':'')+'\
                                </div>\
                                <div class="name">'+n.name+ '</div>'
                        }
                    ));
                });
                $('.thing>ul').html(html);
            },
            //具体的信息列表
            getMoreList:function(data){
                var appro = $('.approval');
                appro.data(data.itemMenu).show();
                appro.find('span:first').html(data.itemMenu.itemName);
                var html = [];
                if(data.category.length === 0){
                    appro.find('.arrow_right').hide();
                    if(appro.data().itemCode === '8'){
                        html = '<p class="tip">还没有发起的采购申请</p>';
                    }else{
                        html = '<p class="tip">还没有要审批的项目</p>';
                    }
                }else{
                    $.each(data.category,function (i,n) {
                        html.push($('<li>',{
                            data:n,
                            html:'\
                                <span><img src="../static/common/img/purchase.png"></span>\
                                <p class="clerafix approval_title">\
                                    <span>'+n.title+'</span>\
                                    <span>￥'+n.beforeTotalPrice+'</span>\
                                </p>\
                                <p class="approval_detatil">\
                                    <span>'+n.name+' *'+n.needcount+'</span>\
                                    <span>'+(n.size>1?'等'+n.size+'项物品':'')+'</span>\
                                </p>\
                                <p class="approval_date clerafix">\
                                    <span>'+n.expectDate+'</span>\
                                    <span>'+n.status+'</span>\
                                 </p>'
                        }));
                    });
                }
                $('.content_approval_list').html(html);
            }
        }
    };
    $.extend(exports,view);
});