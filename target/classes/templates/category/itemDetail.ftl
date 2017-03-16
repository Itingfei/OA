<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>流程详细项目列表</title>
    <#include "/include.ftl"/>
    <script type="text/javascript">
        function page(n, s) {
            if (n) $("#pageNum").val(n);
            if (s) $("#pageSize").val(s);
            $("#selectFrom").attr("action", "/category/${categoryItem.id}/itemDetail");
            $("#selectFrom").submit();
            return false;
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>流程对应表单明细</h5>
                </div>
                <form action="/category/${categoryItem.categoryId}/itemDetail" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-5 pull-right">
                                  <div class="input-group">
                                      <input type="text" placeholder="请输入采购物品名称" class="input-sm form-control" name="itemName" value="<#if categoryItem.itemName??>${categoryItem.itemName}</#if>"> <span class="input-group-btn">
                                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i> 搜索</button> </span>
                                  </div>
                              </div>
                          </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>采购明细编号</th>
                                    <th>采购物品名称</th>
                                    <th>需求数量</th>
                                    <th>采购数量</th>
                                    <th>库存量</th>
                                    <th>型号规格</th>
                                    <th>申请单价</th>
                                    <th>实际单价</th>
                                    <th>表单项备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as item>
                                    <tr id="${item.id}">
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            <#--</@shiro.hasPermission>-->
                                             ${item.id}
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            <#--</@shiro.hasPermission>-->
                                        </td>
                                        <td>${item.itemName}</td>
                                        <td>${item.needcount}</td>
                                        <td>${item.count}</td>
                                        <td>${item.stock}</td>
                                        <td><#if item.model??>${item.model}</#if></td>
                                        <td><#if item.applyPrice??>${item.applyPrice}</#if></td>
                                        <td><#if item.actualPrice??>${item.actualPrice}</#if></td>
                                        <td>${item.itemRemarks}</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                      <div class="pagination">${page}</div>
                   </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/js/common/modalUtil.js"></script>
<script src="/js/common/formUtil.js"></script>
<script src="/js/common/common.js"></script>
</body>
</html>