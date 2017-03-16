<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/ by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:16:41 GMT -->
<head>
<#include "/include.ftl"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>OA管理系统</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        showmessage = function (message, icon) {
//                    $.messager.popup(message);
            showToastMsg(message, icon);
        }
    </script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" src="/static/img/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                <span class="block m-t-xs"><strong class="font-bold">${Session.admin.userName}</strong></span>
                                <span class="text-muted text-xs block">${Session.admin.realName}<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem modifyPass" href="">修改密码</a>
                                </li>
                                <li><a class="J_menuItem userinfo" href="">个人资料</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                  <#list admin_menu_root as root>
                    <li>
                        <a href="#">
                            <i class="${root.icon}"></i>
                            <span class="nav-label">${root.name}</span>
                            <span class="fa arrow"></span>
                        </a>
                        <#list admin_menu_level?keys as mKey>
                             <#if mKey==root.id>
                                <#assign item = admin_menu_level[mKey]>
                                <#list item as itemValue>
                                    <ul class="nav nav-second-level">
                                        <li>
                                            <a class="J_menuItem" href="${itemValue.href}" data-index="${itemValue.id}"> ${itemValue.name}</a>
                                        </li>
                                    </ul>
                                 </#list>
                             </#if>
                        </#list>
                    </li>
                  </#list>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <#--<div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>-->
                        <#--<form role="search" class="navbar-form-custom" method="post" action="http://www.zi-han.net/theme/hplus/search_results.html">-->
                            <#--<div class="form-group">-->
                                <#--<input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">-->
                            <#--</div>-->
                        <#--</form>-->
                    <#--</div>-->
                    <ul class="nav navbar-top-links navbar-right">
                        <input type="hidden" value="${messages?size}" id="msize">
                        <#if messages?? && (messages?size >0)>
                            <li class="dropdown">
                                <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                    <i class="fa fa-envelope"></i> <span class="label label-warning msize">${messages?size}</span>
                                </a>
                                <ul class="dropdown-menu dropdown-messages">
                                 <#list messages as message>
                                    <li class="m-t-xs">
                                        <div class="dropdown-messages-box">
                                            <a href="profile.html" class="pull-left">
                                                <img alt="image" class="img-circle" src="img/a7.jpg">
                                            </a>
                                            <div class="media-body">
                                                <small class="pull-right"><#if message.sendTime??>${message.sendTime?string('yyyy-MM-dd HH:mm:ss')!''}</#if></small>
                                                <strong class="mtitle" data-id="${message.id}" style="cursor: pointer">${message.title}</strong>
                                                <br>
                                                <small class="text-muted">${message.createDate?string('yyyy-MM-dd')!''}</small>
                                            </div>
                                        </div>
                                    </li>
                                    <#if (messages?size>1)><li class="divider"></li></#if>
                                 </#list>
                                    <#--<li>-->
                                        <#--<div class="dropdown-messages-box">-->
                                            <#--<a href="profile.html" class="pull-left">-->
                                                <#--<img alt="image" class="img-circle" src="img/a4.jpg">-->
                                            <#--</a>-->
                                            <#--<div class="media-body ">-->
                                                <#--<small class="pull-right text-navy">25小时前</small>-->
                                                <#--<strong>国民岳父</strong> 如何看待“男子不满自己爱犬被称为狗，刺伤路人”？——这人比犬还凶-->
                                                <#--<br>-->
                                                <#--<small class="text-muted">昨天</small>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                    <#--</li>-->
                                    <#--<li class="divider"></li>-->
                                    <#--<li>-->
                                        <#--<div class="text-center link-block">-->
                                            <#--<a class="J_menuItem" href="mailbox.html">-->
                                                <#--<i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>-->
                                            <#--</a>-->
                                        <#--</div>-->
                                    <#--</li>-->
                                </ul>
                            </li>
                        </#if>
                        <#--<li class="dropdown">-->
                            <#--<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">-->
                                <#--<i class="fa fa-bell"></i> <span class="label label-primary">8</span>-->
                            <#--</a>-->
                            <#--<ul class="dropdown-menu dropdown-alerts">-->
                                <#--<li>-->
                                    <#--<a href="mailbox.html">-->
                                        <#--<div>-->
                                            <#--<i class="fa fa-envelope fa-fw"></i> 您有16条未读消息-->
                                            <#--<span class="pull-right text-muted small">4分钟前</span>-->
                                        <#--</div>-->
                                    <#--</a>-->
                                <#--</li>-->
                                <#--<li class="divider"></li>-->
                                <#--<li>-->
                                    <#--<a href="profile.html">-->
                                        <#--<div>-->
                                            <#--<i class="fa fa-qq fa-fw"></i> 3条新回复-->
                                            <#--<span class="pull-right text-muted small">12分钟钱</span>-->
                                        <#--</div>-->
                                    <#--</a>-->
                                <#--</li>-->
                                <#--<li class="divider"></li>-->
                                <#--<li>-->
                                    <#--<div class="text-center link-block">-->
                                        <#--<a class="J_menuItem" href="notifications.html">-->
                                            <#--<strong>查看所有 </strong>-->
                                            <#--<i class="fa fa-angle-right"></i>-->
                                        <#--</a>-->
                                    <#--</div>-->
                                <#--</li>-->
                            <#--</ul>-->
                        <#--</li>-->
                        <li class="hidden-xs">
                            <#--<a href="index_v1.html" class="J_menuItem" data-index="0"><i class="fa fa-cart-arrow-down"></i> 购买</a>-->
                        </li>
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="<#--index_v148b2.html?v=4.0-->" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy;  <a href="http://www.dongao.com/" target="_blank">东奥集团</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
        <div id="right-sidebar">
            <div class="sidebar-container">

                <ul class="nav nav-tabs navs-1">

                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                    <#--<li class=""><a data-toggle="tab" href="#tab-2">-->
                        <#--通知-->
                    <#--</a>-->
                    <#--</li>-->
                    <#--<li><a data-toggle="tab" href="#tab-3">-->
                        <#--项目进度-->
                    <#--</a>-->
                    <#--</li>-->
                </ul>

                <div class="tab-content">
                    <div id="tab-1" class="tab-pane active">
                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                        </div>
                        <div class="skin-setttings">
                            <div class="title">主题设置</div>
                            <div class="setings-item">
                                <span>收起左侧菜单</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                        <label class="onoffswitch-label" for="collapsemenu">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定顶部</span>

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                        <label class="onoffswitch-label" for="fixednavbar">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>
                        固定宽度
                    </span>

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                        <label class="onoffswitch-label" for="boxedlayout">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="title">皮肤选择</div>
                            <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                            </div>
                            <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                            </div>
                            <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                            </div>
                        </div>
                    </div>
                    <#--<div id="tab-2" class="tab-pane">-->

                        <#--<div class="sidebar-title">-->
                            <#--<h3> <i class="fa fa-comments-o"></i> 最新通知</h3>-->
                            <#--<small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>-->
                        <#--</div>-->

                        <#--<div>-->

                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a1.jpg">-->

                                        <#--<div class="m-t-xs">-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->

                                        <#--据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">今天 4:21</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a2.jpg">-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->
                                        <#--HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">昨天 2:45</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a3.jpg">-->

                                        <#--<div class="m-t-xs">-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->
                                        <#--写的好！与您分享-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">昨天 1:10</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a4.jpg">-->
                                    <#--</div>-->

                                    <#--<div class="media-body">-->
                                        <#--国外极限小子的炼成！这还是亲生的吗！！-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">昨天 8:37</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a8.jpg">-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->

                                        <#--一只流浪狗被收留后，为了减轻主人的负担，坚持自己觅食，甚至......有些东西，可能她比我们更懂。-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">今天 4:21</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a7.jpg">-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->
                                        <#--这哥们的新视频又来了，创意杠杠滴，帅炸了！-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">昨天 2:45</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a3.jpg">-->

                                        <#--<div class="m-t-xs">-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                            <#--<i class="fa fa-star text-warning"></i>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->
                                        <#--最近在补追此剧，特别喜欢这段表白。-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">昨天 1:10</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<div class="sidebar-message">-->
                                <#--<a href="#">-->
                                    <#--<div class="pull-left text-center">-->
                                        <#--<img alt="image" class="img-circle message-avatar" src="img/a4.jpg">-->
                                    <#--</div>-->
                                    <#--<div class="media-body">-->
                                        <#--我发起了一个投票 【你认为下午大盘会翻红吗？】-->
                                        <#--<br>-->
                                        <#--<small class="text-muted">星期一 8:37</small>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</div>-->
                        <#--</div>-->

                    <#--</div>-->
                    <#--<div id="tab-3" class="tab-pane">-->

                        <#--<div class="sidebar-title">-->
                            <#--<h3> <i class="fa fa-cube"></i> 最新任务</h3>-->
                            <#--<small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>-->
                        <#--</div>-->

                        <#--<ul class="sidebar-list">-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>市场调研</h4> 按要求接收教材；-->

                                    <#--<div class="small">已完成： 22%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 22%;" class="progress-bar progress-bar-warning"></div>-->
                                    <#--</div>-->
                                    <#--<div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>可行性报告研究报上级批准 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对-->

                                    <#--<div class="small">已完成： 48%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 48%;" class="progress-bar"></div>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>立项阶段</h4> 东风商用车公司 采购综合综合查询分析系统项目进度阶段性报告武汉斯迪克科技有限公司-->

                                    <#--<div class="small">已完成： 14%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 14%;" class="progress-bar progress-bar-info"></div>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<span class="label label-primary pull-right">NEW</span>-->
                                    <#--<h4>设计阶段</h4>-->
                                    <#--<!--<div class="small pull-right m-t-xs">9小时以后</div>&ndash;&gt;-->
                                    <#--项目进度报告(Project Progress Report)-->
                                    <#--<div class="small">已完成： 22%</div>-->
                                    <#--<div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>拆迁阶段</h4> 科研项目研究进展报告 项目编号: 项目名称: 项目负责人:-->

                                    <#--<div class="small">已完成： 22%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 22%;" class="progress-bar progress-bar-warning"></div>-->
                                    <#--</div>-->
                                    <#--<div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>建设阶段 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对-->

                                    <#--<div class="small">已完成： 48%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 48%;" class="progress-bar"></div>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</li>-->
                            <#--<li>-->
                                <#--<a href="#">-->
                                    <#--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                    <#--<h4>获证开盘</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对-->

                                    <#--<div class="small">已完成： 14%</div>-->
                                    <#--<div class="progress progress-mini">-->
                                        <#--<div style="width: 14%;" class="progress-bar progress-bar-info"></div>-->
                                    <#--</div>-->
                                <#--</a>-->
                            <#--</li>-->

                        <#--</ul>-->

                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--修改密码弹出层begin-->
    <div class="modal inmodal" id="dialog-pass" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content animated flipInY">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body">
                    <form role="form" action="/user/modifyPass" method="post" id="commentForm">
                        <div class="form-group">
                            <label>原始密码<font color="red">*</font></label>
                            <input type="password" id="oldp" name="oldp" class="form-control"  placeholder="请输入原始密码" />
                        </div>
                        <div class="form-group">
                            <label>新密码<font color="red">*</font></label>
                            <input type="password" id="newp" name="newp" class="form-control"  placeholder="请输入新密码" />
                        </div>
                        <div class="form-group">
                            <label>再次输入新密码<font color="red">*</font></label>
                            <input type="password" id="newp2" name="newp2" class="form-control" placeholder="请再次输入新密码" />
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary btnSave" id="btnSave">保存</button>
                </div>
            </div>
        </div>
    </div>
    <!--修改密码弹出层end-->
    <!--个人资料弹出层begin-->
    <div class="modal inmodal" id="userinfo" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content animated flipInY">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">个人资料</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <label class="col-xs-2">登录名称:</label>
                        <div class="col-xs-3">${Session.userInfo.user_name}</div>
                        <label class="col-xs-2">真实姓名:</label>
                        <div class="col-xs-3">${Session.userInfo.real_name}</div>
                    </div>
                    <div class="row">
                        <label class="col-xs-2">所属部门:</label>
                        <div class="col-xs-3">${Session.userInfo.orgName}</div>
                        <label class="col-xs-2">职位:</label>
                        <div class="col-xs-3">${Session.userInfo.positionName}</div>
                    </div>
                    <div class="row">
                        <label class="col-xs-2">直属领导:</label>
                        <div class="col-xs-3">${Session.userInfo.leaderName}</div>
                        <label class="col-xs-2">联系方式:</label>
                        <div class="col-xs-3">${Session.userInfo.link_phone}</div>
                    </div>
                    <div class="row">
                        <label class="col-xs-2">邮箱:</label>
                        <div class="col-xs-3">${Session.userInfo.email}</div>
                        <label class="col-xs-2">办公地点:</label>
                        <div class="col-xs-3">${Session.userInfo.address}</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
    <!--个人资料弹出层end-->
<script src="/js/jquery.form.js"></script>
<script src="/static/js/sys/index.js"></script>
</body>
</html>
