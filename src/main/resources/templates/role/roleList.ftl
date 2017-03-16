<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色列表</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/zTreeStyle/zTreeStyle.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>角色列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addRole"><span class="glyphicon glyphicon-plus"></span>新增角色</button>
                    </div>
                </div>
                <form action="/role" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if role.startCreateDate??>${role.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if role.endCreateDate??>${role.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                              </div>
                              <div class="col-sm-3 m-b-xs">
                                  <div class="row">
                                      <input type="text" placeholder="请输入角色名称" class="form-control" name="name" value="<#if role.name??>${role.name}</#if>">
                                  </div>
                              </div>
                              <div class="col-sm-3 m-b-xs">
                                  <input type="text" placeholder="请输入英文名称" class="form-control" name="enname" value="<#if role.enname??>${role.enname}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs pull-right">
                                      <button type="button" class="btn btn-primary reset"><i class="fa fa-refresh"></i>重置</button>
                                      <button type="submit" class="btn btn-primary pull-right"><i class="fa fa-search"></i>搜索</button>
                              </div>
                          </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>角色名称</th>
                                    <th>英文名字</th>
                                    <th>备注</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>修改人</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as role>
                                    <tr id="${role.id}">
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            <a href="/role/${role.id}/update">
                                            <#--</@shiro.hasPermission>-->
                                             ${role.name}
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            </a>
                                            <#--</@shiro.hasPermission>-->
                                        </td>
                                        <td width="8%">${role.enname}</td>
                                        <td width="10%">${role.remarks}</td>
                                        <td>${role.createBy}</td>
                                        <td>${role.createDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td>${role.updateBy}</td>
                                        <td>${role.updateDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                    <#---<@shiro.hasPermission name="menu:update , menu:delete">--->
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${role.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                                <#--<a href="/menu/${menu.id}/update">修改</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:delete">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${role.id}"><i class="fa fa-trash-o"></i> 删除 </a>
                                                <#--<a href="/menu/${menu.id}/delete" onclick="return confirm('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:create">-->
                                            <#--</@shiro.hasPermission>-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary grantMenus"  data-id="${role.id}" data-name="${role.name}"><i class="fa fa-key"></i>授权菜单</a>
                                        </td>
                                    <#---</@shiro.hasPermission>--->
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                      <div class="pagination">${page}</div>
                   </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal inmodal" id="dialog-menus" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="role_id">
    <input type="hidden" id="firstAdd">
    <div class="modal-dialog">
        <div class="modal-content animated flipInY">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">【<span id="role_name"></span>】 授权菜单</h4>
                <#--<small class="font-bold">这里可以显示副标题。-->
            </div>
            <div class="modal-body">
                <ul id="menu_tree" class="ztree" style="width: 400px;height: 300px;overflow: auto;margin: auto;border: 1px solid #999;"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary saveMenu">保存</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/common/common.js"></script>
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/plugins/zTree/jquery.ztree.all-3.5.min.js"></script>
<script src="/js/sys/role/roleList.js"></script>
</body>
</html>