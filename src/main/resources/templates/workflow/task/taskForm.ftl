<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>办理任务</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>办理任务</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addCategory"><span class="glyphicon glyphicon-plus"></span>新增流程对应表单</button>
                    </div>
                </div>
                <form action="/workflow/tasklist" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>采购编号</th>
                                    <th>采购标题</th>
                                    <th>用途</th>
                                    <th>申请理由</th>
                                    <th>备注</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#--<#list page.list as task>-->
                                    <#--<tr id="${task.id}">-->
                                        <#--<td nowrap>-->
                                             <#--${task.id}-->
                                        <#--</td>-->
                                        <#--<td>${task.name}</td>-->
                                        <#--<td>${task.createTime?string('yyyy-MM-dd HH:mm:ss')!''}</td>-->
                                        <#--<td>${task.assignee}</td>-->
                                        <#--<td nowrap>-->
                                            <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary manage" data-id="${task.id}"><i class="fa fa-pencil"></i> 办理任务 </a>-->
                                            <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary eys" data-id="${task.id}"><i class="fa fa-eye"></i> 查看流程图 </a>-->
                                        <#--</td>-->
                                    <#--</tr>-->
                                <#--</#list>-->
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
<script src="/js/workFlow/task/taskList.js"></script>
</body>
</html>