<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加角色</title>
    <#include "/include.ftl"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/role/save">
                        <input name="id" type="hidden" value="<#if role??>${role.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">角色名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name"  placeholder="请填写角色名称" name="name" maxlength="100" value="<#if role??>${role.name}</#if>" >
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">英文名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="enname"  placeholder="请填写英文名称" name="enname" maxlength="255" value="<#if role??>${role.enname}</#if>" >
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写备注" name="remarks"><#if role??>${role.remarks}</#if></textarea>
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
                    <button type="button" class="btn btn-primary" id="btnSave">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery.form.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/role/roleAdd.js"></script>
</body>
</html>
