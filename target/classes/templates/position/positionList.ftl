<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>职位列表</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css" />
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>职位列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addPosition"><span class="glyphicon glyphicon-plus"></span>新增职位</button>
                    </div>
                </div>
                <form action="/position" role="form" id="selectFrom">
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if position.startCreateDate??>${position.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if position.endCreateDate??>${position.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                              </div>
                              <div class="col-sm-4 m-b-xs">
                                  <input type="text" placeholder="请输入职位名称" class="form-control" name="name" value="<#if position.name??>${position.name}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <#--<div class="input-group">-->
                                      <#--<span class="input-group-btn">-->
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i>搜索</button>
                                        <button type="button" class="btn btn-primary reset"><i class="fa fa-refresh"></i>重置</button>
                                      <#--</span>-->
                                  <#--</div>-->
                              </div>
                          </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>职位名称</th>
                                    <th>是否是领导</th>
                                    <th>职位等级</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>修改人</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list positionList as position>
                                    <tr id="${position.id}" pId="<#if position.parentId==0>0<#else>${position.parentId}</#if>">
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            <a href="/position/${position.id}/update">
                                            <#--</@shiro.hasPermission>-->
                                             ${position.name}
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            </a>
                                            <#--</@shiro.hasPermission>-->
                                        </td>
                                        <td><#if position.isLeader==1>是<#else>否</#if></td>
                                        <td>${position.level}</td>
                                        <td>${position.createBy}</td>
                                        <td>${position.createDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td>${position.updateBy}</td>
                                        <td>${position.updateDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                    <#---<@shiro.hasPermission name="menu:update , menu:delete">--->
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${position.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                                <#--<a href="/menu/${menu.id}/update">修改</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:delete">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${position.id}"><i class="fa fa-trash-o"></i> 删除 </a>
                                                <#--<a href="/menu/${menu.id}/delete" onclick="return confirm('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:create">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary child" data-id="${position.id}" data-name="${position.name}"><i class="fa fa-child"></i>添加子节点</a>
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
<script src="/js/plugins/treeTable/jquery.treeTable.min.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/common/common.js"></script>
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/sys/position/positionList.js"></script>
<#--<script type="text/javascript">-->
<#--//    window.onload=function(){-->
<#--//        $(".spiner-example").fadeOut();-->
<#--//    }-->
<#--</script>-->
</body>
</html>