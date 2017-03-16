<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_data_tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:01 GMT -->
<head>
<#include "/include.ftl"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>H+ 后台主题UI框架 - 数据表格</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
    <script type="text/javascript">
        function page(n, s) {
            if (n) $("#pageNum").val(n);
            if (s) $("#pageSize").val(s);
            $("#searchForm").attr("action", "/workflow/showUploadForm");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body class="gray-bg">

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>流程定义
                        <small>列表</small>
                    </h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="table_data_tables.html#">选项1</a>
                            </li>
                            <li><a href="table_data_tables.html#">选项2</a>
                            </li>
                        </ul>
                        <#--<a class="close-link">-->
                            <#--<i class="fa fa-times"></i>-->
                        <#--</a>-->
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <form id="searchForm" modelAttribute="mass" action="/workflow/showUploadForm" method="post">
                            <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                            <div class="col-sm-2d5">
                                <label>id：</label>
                                <label><input type="text" class="form-control input-sm"
                                             aria-controls="DataTables_Table_0"></label>
                            </div>
                            <div class="col-sm-2d5">
                                <label>id：</label>
                                <label><input type="text" class="form-control input-sm"
                                             aria-controls="DataTables_Table_0"></label>
                            </div>
                            <div class="col-sm-2d5">
                                <label>id：</label>
                                <label><input type="text" class="form-control input-sm"
                                              aria-controls="DataTables_Table_0"></label>
                            </div>
                            <div class="col-sm-2d5">
                                <label>id：</label>
                                <label><input type="text" class="form-control input-sm"
                                              aria-controls="DataTables_Table_0"></label>
                            </div>
                            <div class="col-sm-2d5">
                                <label>id：</label>
                                <label><input type="text" class="form-control input-sm"
                                              aria-controls="DataTables_Table_0"></label>
                            </div>
                            <div class="col-sm-2d5">
                                <label><input type="button" class="btn btn-w-m btn-success" value="提交查询"  onclick="return page();"
                                              aria-controls="DataTables_Table_0"></label>
                            </div>
                        </form>
                    </div>
                    <table class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr>
                            <th>渲染引擎</th>
                            <th>浏览器</th>
                            <th>平台</th>
                            <th>引擎版本</th>
                            <th>CSS等级</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list page.list as param>
                        <tr class="gradeX">
                            <td>${param.id}</td>
                            <td>Internet Explorer 4.0
                            </td>
                            <td>Win 95+</td>
                            <td class="center">4</td>
                            <td class="center">X</td>
                        </tr>
                        </#list>


                        </tbody>
                        <tfoot>
                        <tr>
                            <th>渲染引擎</th>
                            <th>浏览器</th>
                            <th>平台</th>
                            <th>引擎版本</th>
                            <th>CSS等级</th>
                        </tr>
                        </tfoot>
                    </table>
                <#--分页-->
                    <div class="pagination">${page}</div>
                </div>
            </div>
        </div>
    </div>

</div>


<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>


<!-- Mirrored from www.zi-han.net/theme/hplus/table_data_tables.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:02 GMT -->
</html>
