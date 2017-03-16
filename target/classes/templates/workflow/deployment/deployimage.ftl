<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_data_tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:01 GMT -->
<head>
<#include "/include.ftl"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="/static/js/content.min.js?v=1.0.0"></script>
    <title>H+ 后台主题UI框架 - 数据表格</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <#--<div class="ibox-title">-->
                 <#---->
                    <#--<div class="ibox-tools">-->
                    <#--&lt;#&ndash;<a class="collapse-link">&ndash;&gt;-->
                    <#--&lt;#&ndash;<i class="fa fa-chevron-up"></i>&ndash;&gt;-->
                    <#--&lt;#&ndash;</a>&ndash;&gt;-->
                        <#--<a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">-->
                            <#--<i class="fa fa-wrench"></i>-->
                        <#--</a>-->
                        <#--<ul class="dropdown-menu dropdown-user">-->
                            <#--<li><a href="table_data_tables.html#">选项1</a>-->
                            <#--</li>-->
                            <#--<li><a href="table_data_tables.html#">选项2</a>-->
                            <#--</li>-->
                        <#--</ul>-->
                    <#--&lt;#&ndash;<a class="close-link">&ndash;&gt;-->
                    <#--&lt;#&ndash;<i class="fa fa-times"></i>&ndash;&gt;-->
                    <#--&lt;#&ndash;</a>&ndash;&gt;-->
                    <#--</div>-->
                <#--</div>-->


                    <img style="position: absolute; top: 0px; left: 0px;"
                         src="/workflow/viewImage?id=${deploymentId}&resourceName=${imageName}">
                    <div
                            style="position: absolute;border:3px solid red;top:${acs.y}px;left: ${acs.x}px;width: ${acs.width}px;height:${acs.height}px;"></div>

            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $(".dataTables-example").dataTable();
        var oTable = $("#editable").dataTable();
        oTable.$("td").editable("http://www.zi-han.net/theme/example_ajax.php", {
            "callback": function (sValue, y) {
                var aPos = oTable.fnGetPosition(this);
                oTable.fnUpdate(sValue, aPos[0], aPos[1])
            }, "submitdata": function (value, settings) {
                return {"row_id": this.parentNode.getAttribute("id"), "column": oTable.fnGetPosition(this)[2]}
            }, "width": "90%", "height": "100%"
        })
    });
    function fnClickAddRow() {
        $("#editable").dataTable().fnAddData(["Custom row", "New row", "New row", "New row", "New row"])
    };
</script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>

</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_data_tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:02 GMT -->
</html>
