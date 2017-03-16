<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>菜单列表</title>
<#include "/include.ftl"/>
    <link href="/css/plugins/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
<#--<div class="spiner-example">-->
<#--<div class="sk-spinner sk-spinner-wave">-->
<#--<div class="sk-rect1"></div>-->
<#--<div class="sk-rect2"></div>-->
<#--<div class="sk-rect3"></div>-->
<#--<div class="sk-rect4"></div>-->
<#--<div class="sk-rect5"></div>-->
<#--</div>-->
<#--</div>-->
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>菜单列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="saveSort"><i class="fa fa-sort"></i>保存排序</button>
                        <button class="btn btn-primary btn-xs" id="addmenu"><span class="glyphicon glyphicon-plus"></span>新增菜单</button>
                    </div>
                </div>
                <form action="/menu" role="form" id="selectFrom">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3 pull-right">
                                <div class="input-group">
                                    <input type="search" placeholder="请输入菜单名称" class="input-sm form-control" name="name" value="<#if menu.name??>${menu.name}</#if>"> <span class="input-group-btn">
                                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i> 搜索</button> </span>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>链接</th>
                                    <th>排序</th>
                                    <th>可见</th>
                                    <th>权限标识</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list menuList as menu>
                                <tr id="${menu.id}" pId="<#if menu.parentId==0>0<#else>${menu.parentId}</#if>">
                                    <td nowrap>
                                        <i class="<#if menu.icon??>${menu.icon}<#else> hide</#if>"></i>
                                    <#--<@shiro.hasPermission name="menu:update">-->
                                        <a href="javascript:" class="detail" data-id="${menu.id}">
                                        <#--</@shiro.hasPermission>-->
                                         ${menu.name}
                                        <#--<@shiro.hasPermission name="menu:update">-->
                                        </a>
                                    <#--</@shiro.hasPermission>-->
                                    </td>
                                    <td title="${menu.href}">${menu.href}</td>
                                    <td style="text-align:center;">
                                        <input type="hidden" name="ids" value="${menu.id}"/>
                                        <input  id="sorts${menu.id}" name="sorts" type="number" class="digits required" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
                                    </td>
                                    <td><#if menu.isShow==1>显示<#else>隐藏</#if></td>
                                    <td title="${menu.permission}">${menu.permission}</td>
                                <#---<@shiro.hasPermission name="menu:update , menu:delete">--->
                                    <td nowrap>
                                    <#--<@shiro.hasPermission name="menu:update">-->
                                        <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${menu.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                    <#--<a href="/menu/${menu.id}/update">修改</a>-->
                                    <#--</@shiro.hasPermission>-->
                                    <#--<@shiro.hasPermission name="menu:delete">-->
                                        <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${menu.id}"><i class="fa fa-trash-o"></i> 删除 </a>
                                    <#--<a href="/menu/${menu.id}/delete" onclick="return confirm('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>-->
                                    <#--</@shiro.hasPermission>-->
                                    <#--<@shiro.hasPermission name="menu:create">-->
                                        <a href="javascript:" class="btn btn-outline btn-xs btn-primary child" data-id="${menu.id}" data-name="${menu.name}"><i class="fa fa-child"></i>添加子节点</a>
                                    <#--</@shiro.hasPermission>-->
                                    </td>
                                <#---</@shiro.hasPermission>--->
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="js/plugins/treeTable/jquery.treeTable.min.js"></script>
<script src="js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="js/common/common.js"></script>
<script src="js/sys/menu/menuList.js"></script>
<script type="text/javascript">
    //    window.onload=function(){
    //        $(".spiner-example").fadeOut();
    //    }
</script>
</body>
</html>