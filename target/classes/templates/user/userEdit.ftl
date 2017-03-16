<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加用户</title>
<#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <style type="text/css">
        #priList {
            height: 100px;
            width: 665px;
            padding-left: 10px;
            overflow-y: scroll;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/user/save">
                        <input name="id" type="hidden" value="<#if user??>${user.id}</#if>">
                        <input id="editPositionId" value="<#if user?? && user.positionId??>${user.positionId}</#if>"
                               type="hidden">
                        <input id="editOrgId" value="<#if user?? && user.orgId??>${user.orgId}</#if>" type="hidden">
                    <#if !user??>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">登录名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="userName" placeholder="请填写登录名称"
                                       name="userName" maxlength="50" value="<#if user??>${user.user_name}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户密码:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="password" placeholder="请填写用户密码"
                                       name="password" maxlength="32" value="<#if user??>${user.password}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                    </#if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">真实姓名:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="realName" placeholder="请填写真实姓名"
                                       name="realName" maxlength="50" value="<#if user??>${user.real_name}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">性别:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <select data-placeholder="选择性别..." class="chosen-select" style="width:665px;"
                                            name="gender" id="gender">
                                        <option value="">请选择性别</option>
                                        <option value="1"
                                                <#if user?? && user.gender?? && user.gender == 1>selected="selected"</#if>>
                                            男
                                        </option>
                                        <option value="2"
                                                <#if user?? && user.gender?? && user.gender == 2>selected="selected"</#if>>
                                            女
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生日:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="birthday" placeholder="请填写生日"
                                       name="birthday"
                                       value="<#if user?? && user.birthday??>${user.birthday?string('yyyy-MM-dd')}</#if>"
                                       onclick="laydate({istime: true, format: 'YYYY-MM-DD',festival: true})">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">入职日期:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="entryDate" placeholder="请填写入职日期"
                                       name="entryDate"
                                       value="<#if user?? && user.entry_date??>${user.entry_date?string('yyyy-MM-dd')}</#if>"
                                       onclick="laydate({istime: true, format: 'YYYY-MM-DD',festival: true})">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">昵称:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="realName" placeholder="请填写昵称"
                                       name="nickName" maxlength="50" value="<#if user??>${user.nick_name}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">联系电话:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkPhone" placeholder="请填写联系电话"
                                       name="linkPhone" maxlength="20" value="<#if user??>${user.link_phone}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">座机:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="telephone" placeholder="请填写座机"
                                       name="telephone" maxlength="20"
                                       value="<#if user?? && user.telephone??>${user.telephone}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">邮箱:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="email" placeholder="请填写邮箱" name="email"
                                       maxlength="50" value="<#if user?? && user.email??>${user.email}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">组织机构:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <select data-placeholder="选择组织机构..." class="chosen-select" style="width:665px;"
                                            tabindex="2"  <#if !user??>required="required"</#if> name="orgId"
                                            id="orgId">
                                        <option value="">请选择组织机构</option>
                                    <#list organizations as o>
                                        <option value="${o.id}"
                                                <#if user?? && user.orgId?? && user.orgId == o.id>selected="selected"</#if>>${o.orgName}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">职位:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <select data-placeholder="选择职位..." class="chosen-select" style="width:665px;"
                                            tabindex="2"  <#if !user??>required="required"</#if> name="positionId"
                                            id="positionId">
                                        <option value="">请选择职位</option>
                                    </select>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">选择角色:</label>
                                <div class="col-sm-10">
                                    <div id="priList" class="table-bordered">
                                        <div class="clearfix">
                                            <div class="checkbox">
                                            <#list roleList as role>
                                                <label class="checkbox-inline i-checks">
                                                    <input type="checkbox" name="roles"
                                                           value="${role.id}" <#if userRoles??>
                                                        <#list userRoles as chk><#if role.id == chk.id>
                                                           checked="checked" </#if>
                                                        </#list></#if>
                                                    >${role.name}
                                                </label>
                                            </#list>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <#--<div class="hr-line-dashed"></div>-->
                        <#--<div class="form-group">-->
                        <#--<label class="col-sm-2 control-label">用户头像:</label>-->
                        <#--<div class="col-sm-10">-->
                        <#--<div class="fileinput fileinput-new" data-provides="fileinput">-->
                        <#--<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height: 150px;" id="imgs">-->
                        <#--&lt;#&ndash;<c:if test="${appgood.imagesurl!=null && appgood.imagesurl!=''}">&ndash;&gt;-->
                        <#--&lt;#&ndash;<c:set value="${appgood.imagesurl}" var="image"></c:set>&ndash;&gt;-->
                        <#--&lt;#&ndash;<img alt="" src="${savePath}${image}">&ndash;&gt;-->
                        <#--&lt;#&ndash;</c:if>&ndash;&gt;-->
                        <#--</div>-->
                        <#--<div>-->
                        <#--<span class="btn btn-default btn-file"><span class="fileinput-new">选择图片</span> <span class="fileinput-exists">替换</span>-->
                        <#--<input type="file" name="file">-->
                        <#--</span>-->
                        <#--<a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">移除</a>-->
                        <#--</div>-->
                        <#--</div>-->
                        <#--</div>-->
                        <#--</div>-->
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">办公地点:</label>
                                <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写办公地点"
                                          name="address"><#if user?? && user.address??>${user.address}</#if></textarea>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注:</label>
                                <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写备注"
                                          name="description"><#if user?? && user.description??>${user.description}</#if></textarea>
                                </div>
                            </div>
                    </form>
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
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/sys/user/userAdd.js"></script>
</body>
</html>
