<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加审批关系</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/processpersonnel/save">
                        <input name="id" type="hidden" value="<#if personnel??>${personnel.id}</#if>">
                        <input id="editPositionId" value="<#if personnel?? && personnel.positionId??>${personnel.positionId}</#if>" type="hidden">
                        <input id="editOrgId" value="<#if personnel?? && personnel.orgId??>${personnel.orgId}</#if>" type="hidden">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择组织机构:</label>
                            <div class="col-sm-10">
                                <select data-placeholder="选择组织机构..." class="chosen-select" style="width:665px;" tabindex="1" name="orgId" id="orgId">
                                    <option value="">请选择组织机构</option>
                                    <#list organizations as o>
                                        <option value="${o.id}" <#if personnel?? && personnel.orgId?? && personnel.orgId == o.id>selected="selected"</#if>>${o.orgName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择职位:</label>
                            <div class="col-sm-10">
                                <select data-placeholder="选择职位..." class="chosen-select" style="width:665px;" tabindex="2" name="positionId" id="positionId">
                                    <option value="">请选择职位</option>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">选择流程:</label>
                            <div class="col-sm-10">
                                <select data-placeholder="选择流程..." class="chosen-select" style="width:665px;" tabindex="3" name="processKey" id="processKey">
                                    <option value="">请选择流程</option>
                                    <#list pdList as param>
                                        <option value="${param.key}" <#if personnel?? && personnel.processKey?? && personnel.processKey == param.key>selected="selected"</#if>>${param.key}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">任务名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="taskname"  placeholder="请填写任务名称" name="taskname" maxlength="80" value="<#if personnel??>${personnel.taskname}</#if>" >
                                <span class="help-block m-b-none">和流程图中框中名称保持一致</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">分支变量:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="branchVariable"  placeholder="请填写分支变量" name="branchVariable" maxlength="50" value="<#if personnel??>${personnel.branchVariable}</#if>" >
                                <span class="help-block m-b-none">用于判断执行流程分支</span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-xs-2">
                    <button type="button" class="btn btn-white" id="cancel">取消</button>
                </div>
                <div class="col-xs-10 text-right">
                    <button type="submit" class="btn btn-primary" id="btnSave">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/processpersonnel/processpersonnelAdd.js"></script>
</body>
</html>
