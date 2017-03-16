<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户列表</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addUser"><span class="glyphicon glyphicon-plus"></span>新增用户</button>
                    </div>
                </div>
                <form action="/user" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <input id="searshPositionId" name="searshPositionId" type="hidden" value="<#if positionAndOrganization?? && positionAndOrganization.positionId??>${positionAndOrganization.positionId}</#if>"/>
                    <input id="searshOrgId" name="searshOrgId" type="hidden" value="<#if positionAndOrganization?? && positionAndOrganization.orgId??>${positionAndOrganization.orgId}</#if>"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-2 m-b-xs">
                                  <input type="text" placeholder="请输入登录名称" class="form-control" name="userName" value="<#if user.userName??>${user.userName}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <input type="text" placeholder="请输入真实姓名" class="form-control" name="realName" value="<#if user.realName??>${user.realName}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <input type="text" placeholder="请输入联系方式" class="form-control" name="linkPhone" value="<#if user.linkPhone??>${user.linkPhone}</#if>">
                              </div>
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if user.startCreateDate??>${user.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if user.endCreateDate??>${user.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <div class="input-group">
                                      <select data-placeholder="选择组织机构..." class="chosen-select" style="width: 180px;" tabindex="2" name="orgId" id="orgId">
                                          <option value="">请选择组织机构</option>
                                          <#list organizations as o>
                                              <option value="${o.id}" <#if user?? && user.orgId?? && user.orgId == o.id>selected="selected"</#if>>${o.orgName}</option>
                                          </#list>
                                      </select>
                                  </div>
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <div class="input-group">
                                      <select data-placeholder="选择职位..." class="chosen-select" style="width: 180px;" tabindex="2" name="positionId" id="positionId">
                                          <option value="">选择职位</option>
                                      </select>
                                  </div>
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <div class="input-group">
                                      <select data-placeholder="选择锁定状态..." class="chosen-select" style="width: 180px;" tabindex="2" name="lockStatus" id="lockStatus">
                                          <option value="">选择锁定状态</option>
                                          <option value="0" <#if user?? && user.lockStatus?? && user.lockStatus == 0>selected="selected"</#if>>未锁定</option>
                                          <option value="1" <#if user?? && user.lockStatus?? && user.lockStatus == 1>selected="selected"</#if>>已锁定</option>
                                      </select>
                                  </div>
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
                                    <th>用户编号</th>
                                    <th>登录名称</th>
                                    <th>真实姓名</th>
                                    <th>性别</th>
                                    <th>直属领导</th>
                                    <th>组织机构</th>
                                    <th>职位</th>
                                    <th>角色</th>
                                    <th>联系方式</th>
                                    <th>邮箱</th>
                                    <th>锁定状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as user>
                                    <tr id="${user.userId}">
                                        <td>${user.userId}</td>
                                        <td nowrap>
                                             ${user.user_name}
                                        </td>
                                        <td>${user.real_name}</td>
                                        <td>${user.gender}</td>
                                        <td>${user.leaderName}</td>
                                        <td><#if user.orgName??>${user.orgName}</#if></td>
                                        <td><#if user.positionName??>${user.positionName}</#if></td>
                                        <td><#if user.roleName??>${user.roleName}</#if></td>
                                        <td><#if user.link_phone??>${user.link_phone}</#if></td>
                                        <td><#if user.email??>${user.email}</#if></td>
                                        <td><#if user.lock_status==1>已锁定<#else>未锁定</#if></td>
                                        <td>${user.create_date?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                    <#---<@shiro.hasPermission name="menu:update , menu:delete">--->
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${user.userId}"><i class="fa fa-pencil"></i> 编辑 </a>
                                                <#--<a href="/menu/${menu.id}/update">修改</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:delete">-->
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${user.userId}"><i class="fa fa-trash-o"></i> 删除 </a>
                                                <#if user.lock_status==0>
                                                    <a href="javascript:" class="btn btn-outline btn-xs btn-primary lock" data-id="${user.userId}"><i class="fa fa-lock"></i> 锁定 </a>
                                                <#else>
                                                    <a href="javascript:" class="btn btn-outline btn-xs btn-primary unlock" data-id="${user.userId}"><i class="fa fa-unlock"></i> 解锁 </a>
                                                </#if>
                                                <#--<a href="/menu/${menu.id}/delete" onclick="return confirm('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>-->
                                            <#--</@shiro.hasPermission>-->
                                            <#--<@shiro.hasPermission name="menu:create">-->
                                            <#--</@shiro.hasPermission>-->
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
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/common/common.js"></script>
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/sys/user/userList.js"></script>
</body>
</html>