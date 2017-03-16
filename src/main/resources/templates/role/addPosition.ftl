<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>组织机构添加职位</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/organization/addPosition">
                        <input name="orgId" type="hidden" value="<#if organization??>${organization.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">组织机构名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="orgName"  placeholder="请填写组织机构名称" name="orgName" maxlength="30" value="<#if organization??>${organization.orgName}</#if>" readonly>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">职位:</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <select data-placeholder="选择职位..." class="chosen-select" style="width:680px;" tabindex="2" name="positionId" id="positionId">
                                        <option value="">请选择职位</option>
                                        <#list positions as p>
                                            <option value="${p.id}">${p.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-xs-2">
                    <button type="button" class="btn btn-white" id="cancel">取消</button>
                </div>
                <div class="col-xs-10 text-right">
                    <button type="button" class="btn btn-primary" id="btnSave">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<#--<script src="/js/demo/form-advanced-demo.min.js"></script>-->
<script src="/js/jquery.form.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/organization/addPosition.js"></script>
</body>
<!-- Mirrored from www.zi-han.net/theme/hplus/form_basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:15 GMT -->
</html>
