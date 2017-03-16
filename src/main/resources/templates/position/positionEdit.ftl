<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加职位</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #EEEEEE;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="<#if position??>/position/${position.id}/appendChild<#else>/position/0/appendChild</#if>">
                        <input name="id" type="hidden" value="<#if position??>${position.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">职位名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name"  placeholder="请填写职位名称" name="name" maxlength="30" value="<#if position??>${position.name}</#if>" >
								<#--<span class="help-block m-b-none">用于职位分级显示</span>-->
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否是领导:</label>
                            <div class="col-sm-10">
                                <div class="row">
                                    <div class="radio i-checks" style="float: left">
                                        <label class="">
                                            <div style="position: relative;" class="iradio_square-green"><input style="position: absolute; opacity: 0;"  value="1" <#if position.isLeader?? && position.isLeader==1>checked="checked"</#if> id="isLeader" name="isLeader" type="radio"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i>领导</label>
                                    </div>
                                    <div class="radio i-checks"  style="float: left">
                                        <label class="">
                                            <div style="position: relative;" class="iradio_square-green"><input style="position: absolute; opacity: 0;"  value="0" <#if position.isLeader?? && position.isLeader==0>checked="checked"<#elseif !position.isLeader?? >checked="checked"</#if> name="isLeader" type="radio"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i> 下属</label>
                                    </div>
                                </div>
                                <#--<span class="help-block m-b-none"></span>-->
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上级职位:</label>
                            <div class="col-sm-10">
                                <input type="hidden" id="parentPositionId" name="parentId" value="<#if parent??>${parent.id}<#else>0</#if>" readonly />
                                <div class="input-group">
                                    <input type="text" id="parentPositionName" name="parentPositionName" class="form-control" value="<#if parent??>${parent.name}</#if>" readonly/>
                                    <span class="input-group-btn">
                                      <button type="button" id="menuBtn" class="btn btn-primary">选择</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                        <ul id="tree" class="ztree" style="margin-left:-5px;margin-top: -19px; width: 660px;"></ul>
                    </div>
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
<script src="/js/plugins/zTree/jquery.ztree.all-3.5.min.js"></script>
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/position/positionAdd.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
<script type="text/javascript">
	$(function () {
        /**菜单树设置*/
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };
        var zNodes =[
        <#list menuList as o>
        <#--- <#if o.parentId==0>
            { id:${o.id}, pId:${o.parentId}, name:"${o.name}"},
        </#if> -->
            { id:${o.id}, pId:${o.parentId}, name:"${o.name}"},
        </#list>
        ];
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                    nodes = zTree.getSelectedNodes(),
                    id = "",
                    name = "";
            nodes.sort(function compare(a,b){return a.id-b.id;});
            for (var i=0, l=nodes.length; i<l; i++) {
                id += nodes[i].id + ",";
                name += nodes[i].name + ",";
            }
            if (id.length > 0 ) id = id.substring(0, id.length-1);
            if (name.length > 0 ) name = name.substring(0, name.length-1);
            $("#parentPositionId").val(id);
            $("#parentPositionName").val(name);
            hideMenu();
        };
        function showMenu() {
            var cityObj = $("#parentPositionName");
            var cityOffset = $("#parentPositionName").offset();
            $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }
        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#menuBtn").click(showMenu);
    })
</script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/form_basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:15 GMT -->
</html>
