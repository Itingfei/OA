<html>
<head>
<#include "/include.ftl"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>职位列表</title>
    <script type="text/javascript">
        function page(n, s) {
            if (n) $("#pageNum").val(n);
            if (s) $("#pageSize").val(s);
            $("#searchForm").attr("action", "/organization/${organizationId}/infoPosition");
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
                    <h5>
                        ${organization.orgName}下职位列表
                    </h5>
                    <div class="ibox-tools">
                        <#--<a class="collapse-link">-->
                            <#--<i class="fa fa-chevron-up"></i>-->
                        <#--</a>-->
                        <#--<a class="dropdown-toggle" data-toggle="dropdown" href="table_data_tables.html#">-->
                            <#--<i class="fa fa-wrench"></i>-->
                        <#--</a>-->
                        <#--<ul class="dropdown-menu dropdown-user">-->
                            <#--<li><a href="table_data_tables.html#">选项1</a>-->
                            <#--</li>-->
                            <#--<li><a href="table_data_tables.html#">选项2</a>-->
                            <#--</li>-->
                        <#--</ul>-->
                    </div>
                </div>
                <div class="ibox-content">
                        <form id="searchForm" modelAttribute="mass" action="/organization/${organizationId}/infoPosition" method="post">
                            <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                            <div class="row">
                                <div class="col-sm-5 pull-right">
                                    <div class="input-group">
                                        <input type="text" placeholder="请输入职位名称" class="input-sm form-control" name="name" value="<#if position.name??>${position.name}</#if>">
                                        <span class="input-group-btn">
                                          <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i> 搜索</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </form>
                    <table class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr>
                            <th>职位ID</th>
                            <th>职位名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if page.list??>
                            <#list page.list as param>
                            <tr class="gradeX">
                                <td>${param.positionId}</td>
                                <td>${param.positionName}</td>
                                <td><a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${param.id}"><i class="fa fa-trash-o"></i> 删除职位</a></td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                        <tfoot>
                        <tr>
                            <th>职位ID</th>
                            <th>职位名称</th>
                        </tr>
                        </tfoot>
                    </table>
                        <#--分页-->
                    <div class="pagination">${page}</div>
                </div>
                <#--<div class="hr-line-dashed"></div>-->
                <#--<div class="form-group">-->
                    <#--<div class="col-xs-2">-->
                        <#--<button type="button" class="btn btn-white" id="cancel">取消</button>-->
                    <#--</div>-->
                <#--</div>-->
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(".delete").click(function () {
            var positionId = $(this).attr("data-id");
            // 确认提示框
            var msg = "要删除该职位吗？";
            confirmMsg(msg,function(index){
                $.ajax({
                    type:"post",
                    url:"/organization/"+positionId+"/deletePosition",
                    dataType:"JSON",
                    success:function(result){
                        if (result.code != '0') {
                            //失败
                            showToastMsg(result.msg,2);
                        } else {
                            showToastMsg(result.msg,1);
                            //重新加载数据
                            refreshPager("#searchForm");
                        }
                    }
                });
                layer.close(index);
            });
        });
    })
</script>
<#--<script src="/js/sys/organization/organizationEdit.js"></script>-->
</body>
</html>
