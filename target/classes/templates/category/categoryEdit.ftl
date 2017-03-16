<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加流程对应表单</title>
<#include "/include.ftl"/>
    <link rel="stylesheet" href="/css/plugins/jasny/jasny-bootstrap.min.css">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/category/saveCategory">
                        <input name="itemSize" id="itemSize"
                               value="<#if categoryItems??>${categoryItems?size}<#else>1</#if>" type="hidden">
                        <input value="" name="deleteItemId" type="hidden" class="deleteItemId" id="deleteItemId">
                        <input name="id" type="hidden" value="<#if category??>${category.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="title" placeholder="请填写标题" name="title"
                                       maxlength="50" value="<#if category??>${category.title}</#if>">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <#--<div class="form-group">-->
                            <#--<label class="col-sm-2 control-label">采购员:</label>-->
                            <#--<div class="col-sm-10">-->
                                <#--<div class="input-group">-->
                                    <#--<select data-placeholder="选择采购员..." class="chosen-select" style="width:665px;"-->
                                            <#--tabindex="2" name="buyerId" id="buyerId">-->
                                        <#--<option value="">请选择采购员</option>-->
                                    <#--<#list userList as user>-->
                                        <#--<option value="${user.id}"-->
                                                <#--<#if category.buyerId==user.id>selected="selected"</#if>>${user.realName}</option>-->
                                    <#--</#list>-->
                                    <#--</select>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</div>-->
                        <#--<div class="hr-line-dashed"></div>-->
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用途:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="purpose" placeholder="请填写用途" name="purpose"
                                          maxlength="50"><#if category??>${category.purpose}</#if></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">申请理由:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="reason" placeholder="请填写申请理由" name="reason"
                                          maxlength="50"><#if category??>${category.reason}</#if></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="remarks" placeholder="请填写备注" name="remarks"
                                          maxlength="500"><#if category??>${category.remarks}</#if></textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <fieldset>
                            <legend>填写具体采购项</legend>
                            <div id="item">
                            <#if !category??>
                                <input value="" name="itemId" type="hidden">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="col-sm-4 control-label">采购物品名称:<font color="red">*</font></label>
                                        <div class="col-sm-8">
                                            <input type="text" id="itemName" name="itemName"
                                                   class="form-control bg-danger" maxlength="80" placeholder="请输入采购物品名称"
                                                   value=""/>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <label class="col-sm-4 control-label">需求数量:<font color="red">*</font></label>
                                        <div class="col-sm-8">
                                            <input type="text" id="needcount" name="needcount" class="form-control"
                                                   maxlength="11" placeholder="请输入需求数量" value=""/>
                                        </div>
                                    </div>
                                </div>
                                <#--<div class="hr-line-dashed"></div>-->
                                <#--<div class="row">-->
                                    <#--<div class="col-sm-6">-->
                                        <#--<label class="col-sm-4 control-label">采购数量:</label>-->
                                        <#--<div class="col-sm-8">-->
                                            <#--<input type="text" id="count" name="count" class="form-control bg-danger"-->
                                                   <#--maxlength="11" placeholder="请输入采购数量" value=""/>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="col-sm-6">-->
                                        <#--<label class="col-sm-4 control-label">库存量:</label>-->
                                        <#--<div class="col-sm-8">-->
                                            <#--<input type="text" id="stock" name="stock" class="form-control"-->
                                                   <#--maxlength="11" placeholder="请输入库存量" value=""/>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <div class="hr-line-dashed"></div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <label class="col-sm-4 control-label">型号规格:<font color="red">*</font></label>
                                        <div class="col-sm-8">
                                            <input type="text" id="model" name="model" class="form-control bg-danger"
                                                   maxlength="255" placeholder="请输入型号规格" value=""/>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <label class="col-sm-4 control-label">申请单价:<font color="red">*</font></label>
                                        <div class="col-sm-8">
                                            <input type="text" id="applyPrice" name="applyPrice" class="form-control"
                                                   maxlength="10" placeholder="请输入申请单价" value=""/>
                                        </div>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">物品分类:<font color="red">*</font></label>
                                    <div class="col-sm-10">
                                        <div class="input-group">
                                            <select data-placeholder="选择物品分类..." class="chosen-select" style="width:650px;" name="classifyId" id="classifyId">
                                                <option value="">请选择物品分类</option>
                                                <#list classifys as o>
                                                    <option value="${o.id}">${o.name}</option>
                                                </#list>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <label class="col-sm-2 control-label">备注:</label>
                                        <div class="col-sm-10">
                                            <textarea class="form-control" id="itemRemarks" placeholder="请填写备注"
                                                      name="itemRemarks" maxlength="500"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <#if categoryItems??>
                                <#list categoryItems as item>
                                    <div class="cateitem">
                                    <#--<div class="hr-line-dashed"></div>-->
                                        <input value="${item.id}" name="itemId" type="hidden">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <label class="col-sm-4 control-label">采购物品名称:</label>
                                                <div class="col-sm-8">
                                                    <input type="text" id="itemName" name="itemName"
                                                           class="form-control bg-danger" maxlength="80"
                                                           placeholder="请输入采购物品名称" value="${item.itemName}"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <label class="col-sm-4 control-label">需求数量:</label>
                                                <div class="col-sm-8">
                                                    <input type="text" id="needcount" name="needcount"
                                                           class="form-control" maxlength="11" placeholder="请输入需求数量"
                                                           value="${item.needcount}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <#--<div class="hr-line-dashed"></div>-->
                                        <#--<div class="row">-->
                                            <#--<div class="col-sm-6">-->
                                                <#--<label class="col-sm-4 control-label">采购数量:</label>-->
                                                <#--<div class="col-sm-8">-->
                                                    <#--<input type="text" id="count" name="count"-->
                                                           <#--class="form-control bg-danger" maxlength="11"-->
                                                           <#--placeholder="请输入采购数量" value="${item.count}"/>-->
                                                <#--</div>-->
                                            <#--</div>-->
                                            <#--<div class="col-sm-6">-->
                                                <#--<label class="col-sm-4 control-label">库存量:</label>-->
                                                <#--<div class="col-sm-8">-->
                                                    <#--<input type="text" id="stock" name="stock" class="form-control"-->
                                                           <#--maxlength="11" placeholder="请输入库存量" value="${item.stock}"/>-->
                                                <#--</div>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                        <div class="hr-line-dashed"></div>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <label class="col-sm-4 control-label">型号规格:</label>
                                                <div class="col-sm-8">
                                                    <input type="text" id="model" name="model"
                                                           class="form-control bg-danger" maxlength="255"
                                                           placeholder="请输入型号规格" value="${item.model}"/>
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <label class="col-sm-4 control-label">申请单价:</label>
                                                <div class="col-sm-8">
                                                    <input type="text" id="applyPrice" name="applyPrice"
                                                           class="form-control" maxlength="10" placeholder="请输入申请单价"
                                                           value="${item.applyPrice}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <#--<div class="hr-line-dashed"></div>-->
                                        <#--<div class="row">-->
                                            <#--<div class="col-sm-6">-->
                                                <#--<label class="col-sm-4 control-label">实际单价:</label>-->
                                                <#--<div class="col-sm-8">-->
                                                    <#--<input type="text" id="actualPrice" name="actualPrice"-->
                                                           <#--class="form-control bg-danger" maxlength="10"-->
                                                           <#--placeholder="请输入实际单价"-->
                                                           <#--value="<#if item.actualPrice??>${item.actualPrice?string}</#if>"/>-->
                                                <#--</div>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                        <div class="hr-line-dashed"></div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">物品分类:<font color="red">*</font></label>
                                            <div class="col-sm-10">
                                                <div class="input-group">
                                                    <select data-placeholder="选择物品分类..." class="chosen-select" style="width:650px;" name="classifyId" id="classifyId">
                                                        <option value="">请选择物品分类</option>
                                                        <#list classifys as o>
                                                            <option value="${o.id}" <#if item?? && item.classifyId?? && item.classifyId == o.id>selected="selected"</#if>>${o.name}</option>
                                                        </#list>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="hr-line-dashed"></div>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <label class="col-sm-2 control-label">备注:</label>
                                                <div class="col-sm-10">
                                                    <textarea class="form-control" id="itemRemarks" placeholder="请填写备注"
                                                              name="itemRemarks"
                                                              maxlength="500">${item.itemRemarks}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <#if item_index &gt;0>
                                            <div class="hr-line-dashed"></div>
                                            <div class="form-group">
                                                <div class="col-xs-12 text-right">
                                                    <button type="button" class="btn btn-danger removeItem"
                                                            data-id="${item.id}" onclick="removeItem(this)"><span
                                                            class="glyphicon glyphicon-trash"></span>删除
                                                    </button>
                                                </div>
                                            </div>
                                        </#if>
                                        <#if item_index+1!=categoryItems?size>
                                            <div class="hr-line-dashed"></div></#if>
                                    </div>
                                </#list>
                            </#if>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-xs-12 text-right">
                                    <button type="button" class="btn btn-primary" id="addItem"><span
                                            class="glyphicon glyphicon-plus"></span>添加
                                    </button>
                                </div>
                            </div>
                        </fieldset>
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
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/category/categoryAdd.js"></script>
<script type="text/javascript">
    $("#addItem").click(function () {
        $("#item").append('<div class="cateitem"><input value="" name="itemId" type="hidden"><div class="hr-line-dashed"></div><div class="row"><div class="col-sm-6"><label class="col-sm-4 control-label">采购物品名称:<font color="red">*</font></label><div class="col-sm-8"><input type="text" id="'+Math.round(Math.random()*100000)+'" name="itemName" class="form-control bg-danger" maxlength="80" placeholder="请输入采购物品名称" value=""></div></div><div class="col-sm-6"><label class="col-sm-4 control-label">需求数量:<font color="red">*</font></label><div class="col-sm-8"><input type="text" id="'+Math.round(Math.random()*100000)+'" name="needcount" class="form-control" maxlength="11" placeholder="请输入需求数量" value=""></div></div></div><div class="hr-line-dashed"></div><div class="row"><div class="col-sm-6"><label class="col-sm-4 control-label">型号规格:<font color="red">*</font></label><div class="col-sm-8"><input type="text" id="'+Math.round(Math.random()*100000)+'" name="model" class="form-control bg-danger" maxlength="255" placeholder="请输入型号规格" value=""></div></div><div class="col-sm-6"><label class="col-sm-4 control-label">申请单价:<font color="red">*</font></label><div class="col-sm-8"><input type="text" id="'+Math.round(Math.random()*100000)+'" name="applyPrice" class="form-control" maxlength="10" placeholder="请输入申请单价" value=""></div></div></div><div class="hr-line-dashed"></div><div class="form-group"><label class="col-sm-2 control-label">物品分类:<font color="red">*</font></label><div class="col-sm-10"><div class="input-group"><select data-placeholder="选择物品分类..." class="chosen-select" style="width:650px" name="classifyId" id="'+(parseInt($("#itemSize").val())+1)+'"> <option value="">请选择物品分类</option></select></div></div></div><div class="hr-line-dashed"></div><div class="row"><div class="form-group"><label class="col-sm-2 control-label">备注:</label><div class="col-sm-10"><textarea class="form-control" id="itemRemarks" placeholder="请填写备注" name="itemRemarks" maxlength="500"></textarea></div></div></div><div class="form-group"><div class="col-xs-12 text-right"><button type="button" class="btn btn-danger removeItem" onclick="removeItem(this)"><span class="glyphicon glyphicon-trash"></span>删除</button></div></div></div>');
        <#--<#list classifys as o><option value="${o.id}">${o.name}</option></#list>-->
        var classifysSize = '${classifys?size}';
        if(classifysSize!=0)
        {
            var classifySelect = $("#" + (parseInt($('#itemSize').val())+1));
//           var selectContact = $("#'"+(parseInt($('#itemSize').val())+1)+"'");
            <#--$.each(${classifys}, function(i, item) {-->

            <#list classifys as o >
                 var $option = $("<option></option>");
                $option.attr("value",'${o.id}');
                $option.text('${o.name}');
                $(classifySelect).append($option);
            </#list>

//            });
            <#--<#list classifys as o >-->
                <#--for(var i = 0;i<selectContact.length;i++){-->
                    <#--alert(selectContact);-->
                <#--}-->
            <#--</#list>-->
        }
        var config = {
            ".chosen-select": {},
            ".chosen-select-deselect": {
                allow_single_deselect: !0
            },
            ".chosen-select-no-single": {
                disable_search_threshold: 10
            },
            ".chosen-select-no-results": {
                no_results_text: "Oops, nothing found!"
            },
            ".chosen-select-width": {
                width: "95%"
            }
        };
        for (var selector in config) $(selector).chosen(config[selector]).change(function () {
            var ID = $(this).attr("id");
            if (!$(this).valid()) {
                $("#" + ID + "_chosen a").addClass("error");
            }
            else {
                $("#" + ID + "_chosen a").removeClass("error");
            }
        });
        $("#itemSize").val(parseInt($("#itemSize").val())+1);
    });
</script>
</body>
</html>
