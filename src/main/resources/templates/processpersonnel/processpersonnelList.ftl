<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>审批人关系列表</title>
    <#include "/include.ftl"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>审批人关系列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addPersonnel"><span class="glyphicon glyphicon-plus"></span>新增审批关系</button>
                    </div>
                </div>
                <form action="/processpersonnel" role="form" id="selectFrom">
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if processpersonnel.startCreateDate??>${processpersonnel.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if processpersonnel.endCreateDate??>${processpersonnel.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <input type="text" placeholder="请输入任务名称" class="form-control" name="taskname" value="<#if processpersonnel.taskname??>${processpersonnel.taskname}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <input type="text" placeholder="请输入流程定义key" class="form-control" name="processKey" value="<#if processpersonnel.processKey??>${processpersonnel.processKey}</#if>">
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
                                    <th>关系编号</th>
                                    <th>组织机构</th>
                                    <th>职位</th>
                                    <th>任务名称</th>
                                    <th>流程定义key</th>
                                    <th>分支变量</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>修改人</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as processpersonnel>
                                    <tr>
                                        <td>${processpersonnel.id}</td>
                                        <td>
                                             ${processpersonnel.orgName}
                                        </td>
                                        <td>${processpersonnel.positionName}</td>
                                        <td>${processpersonnel.taskname}</td>
                                        <td>${processpersonnel.processKey}</td>
                                        <td>${processpersonnel.branchVariable}</td>
                                        <td>${processpersonnel.createBy}</td>
                                        <td>${processpersonnel.createDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td>${processpersonnel.updateBy}</td>
                                        <td>${processpersonnel.updateDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td nowrap>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${processpersonnel.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${processpersonnel.id}"><i class="fa fa-trash-o"></i> 删除 </a>
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
<script src="/js/sys/processpersonnel/processpersonnelList.js"></script>
</body>
</html>