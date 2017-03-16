<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加消息</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/message/save">
                        <input name="id" type="hidden" value="<#if message??>${message.id}</#if>">
                        <input name="categoryId" type="hidden" value="<#if message??>${message.categoryId}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">消息类型:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <select data-placeholder="选择消息类型..." class="chosen-select messageType" style="width:665px;" tabindex="0" name="type" id="type">
                                        <#if Session.userInfo.positionName?? && Session.userInfo.positionName=='出纳'><option value="0" <#if message?? && message.type?? && message.type == '0'>selected="selected"</#if>>流程消息</option></#if>
                                        <#if !Session.userInfo.positionName?? || Session.userInfo.positionName!='出纳'><option value="1" <#if message?? && message.type?? && message.type == '1'>selected="selected"</#if>>系统消息</option></#if>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <#--<div class="hr-line-dashed categorydashed"></div>-->
                        <#--<div class="form-group categoryDiv">-->
                            <#--<label class="col-sm-2 control-label">选择流程表单:</label>-->
                            <#--<div class="col-sm-10">-->
                                <#--<div class="input-group">-->
                                    <#--<select data-placeholder="选择选择流程表单..." class="chosen-select" style="width:665px;" tabindex="0" name="categoryId" id="categoryId">-->
                                        <#--<option value="">请选择流程表单</option>-->
                                        <#--<#list categorys as o>-->
                                            <#--<option value="${o.id}" <#if message?? && message.categoryId?? && message.categoryId == o.id>selected="selected"</#if>>${o.title}</option>-->
                                        <#--</#list>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</div>-->
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">消息标题:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写消息标题" name="title" id="title"><#if message??>${message.title}</#if></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">消息内容:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写消息内容" name="content" id="content"><#if message??>${message.content}</#if></textarea>
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
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/sys/message/messageAdd.js"></script>
</body>
</html>
