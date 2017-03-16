<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看菜单</title>
    <#--<link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">-->
    <#--<link href="/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">-->
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <#--<link href="/css/animate.min.css" rel="stylesheet">-->
    <#--<link href="/css/style.min862f.css?v=4.1.0" rel="stylesheet">-->
    <#include "/include.ftl"/>
    <link href="/css/plugins/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #EEEEEE;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
               <#-- <div class="ibox-title">
                    <h5>${op}菜单</h5>
				&lt;#&ndash;<div class="ibox-tools">&ndash;&gt;
				&lt;#&ndash;<a class="collapse-link">&ndash;&gt;
				&lt;#&ndash;<i class="fa fa-chevron-up"></i>&ndash;&gt;
				&lt;#&ndash;</a>&ndash;&gt;
				&lt;#&ndash;<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">&ndash;&gt;
				&lt;#&ndash;<i class="fa fa-wrench"></i>&ndash;&gt;
				&lt;#&ndash;</a>&ndash;&gt;
				&lt;#&ndash;<ul class="dropdown-menu dropdown-user">&ndash;&gt;
				&lt;#&ndash;<li><a href="form_basic.html#">选项1</a>&ndash;&gt;
				&lt;#&ndash;</li>&ndash;&gt;
				&lt;#&ndash;<li><a href="form_basic.html#">选项2</a>&ndash;&gt;
				&lt;#&ndash;</li>&ndash;&gt;
				&lt;#&ndash;</ul>&ndash;&gt;
				&lt;#&ndash;<a class="close-link">&ndash;&gt;
				&lt;#&ndash;<i class="fa fa-times"></i>&ndash;&gt;
				&lt;#&ndash;</a>&ndash;&gt;
				&lt;#&ndash;</div>&ndash;&gt;
                </div>-->
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="<#if menu.id??>/menu/${menu.id}/update<#else>${request.contextPath}/menu/${parent.id}/appendChild</#if>">
                        <input name="id" type="hidden" value="<#if menu.id??>${menu.id}</#if>">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上级菜单:</label>
                            <div class="col-sm-10">
                                <input type="hidden" id="parentMenuId" name="parentId" value="${menu.parentId}" readonly />
                                <#--<div class="input-group">-->
                                    <input type="text" id="parentMenuName" name="parentMenuName" class="form-control required" value="${parent.name}" readonly />
                                    <#--<span class="input-group-btn">-->
										<#--<button type="button" id="menuBtn" class="btn btn-primary">选择</button>-->
									<#--</span>-->
                                <#--</div>-->
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">名称:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name"  placeholder="请填写菜单名称" name="name" maxlength="30" value="<#if menu.name??>${menu.name}</#if> " readonly >
								<span class="help-block m-b-none">用于菜单前台显示</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">链接:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" placeholder="请添加菜单链接" class="form-control" name="href" id="href" maxlength="50" value="<#if menu.href??>${menu.href}</#if>" readonly>
                                <span class="help-block m-b-none">点击菜单跳转的页面路径，不需要时请填"#"</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">图标:</label>
                            <div class="col-sm-10">
                                <#--<div class="input-group">-->
                                    <i id="iconIcon" class="<#if (menu.icon) ?? && (menu.icon)!='' >${menu.icon}<#else> hide</#if>"></i>&nbsp;<label id="iconIconLabel"><#if (menu.icon) ?? && (menu.icon)!=''>${menu.icon}<#else>无</#if></label>&nbsp;
                                    <input id="icon" name="icon" type="hidden" value="<#if menu.icon ?? && menu.icon!=''>${menu.icon}</#if>"/>
                              	    <#--<a  class="btn btn-primary" id="selectIcon" href="javascript:">选择</a>-->
								<#--</div>-->
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">排序:<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control digits" placeholder="请填写排序顺序" name="sort"  maxlength="4" value="${menu.sort?default('1')}" readonly/>
                                <span class="help-block m-b-none">排列顺序，升序。</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">可见:<font color="red">*</font></label>
                            <div class="col-sm-10">
								<div class="row">
                                    <div class="radio i-checks" style="float: left">
                                        <label class="">
										<div style="position: relative;" class="iradio_square-green"><input style="position: absolute; opacity: 0;"  value="1" <#if menu.isShow?? && menu.isShow==1>checked="checked"</#if> id="isShow" name="isShow" type="radio" readonly disabled><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i>显示</label>
                                    </div>
									<div class="radio i-checks"  style="float: left">
										<label class="">
										<div style="position: relative;" class="iradio_square-green"><input style="position: absolute; opacity: 0;"  value="0" <#if menu.isShow?? && menu.isShow==0>checked="checked"<#elseif !menu.isShow?? >checked="checked"</#if> name="isShow" type="radio" readonly disabled><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i> 隐藏</label>
									</div>
                                </div>
                                <span class="help-block m-b-none">该菜单或操作是否显示到系统菜单中</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">权限标识:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" placeholder="请填写权限标识" name="permission" maxlength="50" value="<#if menu.permission??>${menu.permission}</#if>" readonly>
                                <span class="help-block m-b-none">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">备注:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control text-area" placeholder="请填写备注" name="remarks" readonly><#if menu.remarks??>${menu.remarks}</#if></textarea>
                            </div>
                        </div>
                    </form>
                    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                        <ul id="tree" class="ztree" style="margin-left:-5px;margin-top: -19px; width: 1000px;"></ul>
                    </div>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
        <#--<div class="form-group">-->
        <#--<div class="col-sm-4 col-sm-offset-2">-->
        <#--<button class="btn btn-primary" type="button" id="btnSave">保存内容</button>-->
        <#--<button class="btn btn-white" type="button" id="cancel">取消</button>-->
        <#--</div>-->
        <#--</div>-->
            <div class="form-group">
                <div class="col-xs-2">
                    <button type="button" class="btn btn-white" id="cancel">取消</button>
                </div>
                <#--<div class="col-xs-10 text-right">-->
                    <#--<button type="submit" class="btn btn-primary" id="btnSave">确定</button>-->
                <#--</div>-->
            </div>
        </div>
    </div>
</div>
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/plugins/zTree/jquery.ztree.all-3.5.min.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/contabs.min.js"></script>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/sys/menu/menuAdd.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
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
            $("#parentMenuId").val(id);
            $("#parentMenuName").val(name);
            hideMenu();
        };
        function showMenu() {
            var cityObj = $("#parentMenuName");
            var cityOffset = $("#parentMenuName").offset();
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
</html>
