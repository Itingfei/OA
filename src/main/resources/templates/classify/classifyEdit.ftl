<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加采购物品分类</title>
    <#include "/include.ftl"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/classify/save">
                        <input name="id" type="hidden" value="<#if classify??>${classify.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">分类名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name"  placeholder="请填写采购物品分类名称" name="name" maxlength="100" value="<#if classify??>${classify.name}</#if>" >
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
<script src="/js/sys/classify/classifyAdd.js"></script>
</body>
</html>
