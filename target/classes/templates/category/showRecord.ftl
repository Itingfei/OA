<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看审核记录</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>查看审核记录</h5>
                </div>
                    <input id="pageNum" name="pageNum" type="hidden" value="${categoryItems.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${categoryItems.pageSize}"/>
                    <div class="ibox-content">
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>采购编号</th>
                                    <th>采购标题</th>
                                    <th>用途</th>
                                    <th>申请理由</th>
                                    <th>备注</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr id="${category.id}">
                                        <td nowrap>
                                             ${category.id}
                                        </td>
                                        <td>${category.title}</td>
                                        <td>${category.purpose}</td>
                                        <td>${category.reason}</td>
                                        <td>${category.remarks}</td>
                                        <td>${category.createDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                      <a class="btn btn-primary btn-block showItem" style="display: none"><i class="fa fa-arrow-down"></i> 显示更多</a>
                      <a class="btn btn-primary btn-block hideItem"><i class="fa fa-arrow-up"></i> 隐藏</a>
                      <div class="items">
                       <form action="/category/${category.id}/itemDetail" role="form" id="selectFrom">
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
                                      <#list categoryItems.list as item>
                                          <tr id="${item.id}">
                                              <td nowrap>
                                                 ${item.id}
                                              </td>
                                              <td>${item.itemName}</td>
                                              <td>${item.needcount}</td>
                                              <td <#if Session.userInfo.positionName=='采购专员'>ondblclick="tdclick(this)" title="双击编辑" </#if> data-name="count" data-id="${item.id}">${item.count}</td>
                                              <td <#if Session.userInfo.positionName=='采购专员'>ondblclick="tdclick(this)" title="双击编辑" </#if> data-name="stock" data-id="${item.id}">${item.stock}</td>
                                              <td><#if item.model??>${item.model}</#if></td>
                                              <td><#if item.applyPrice??>${item.applyPrice}</#if></td>
                                              <td <#if Session.userInfo.positionName=='采购专员'>ondblclick="tdclick(this)" title="双击编辑" </#if> data-name="actualPrice" data-id="${item.id}"><#if item.actualPrice??>${item.actualPrice}</#if></td>
                                              <td>${item.itemRemarks}</td>
                                          </tr>
                                      </#list>
                                  </tbody>
                              </table>
                            </div>
                            <div class="pagination">${categoryItems}</div>
                           </form>
                      </div>
                   </div>
            </div>
        </div>
    </div>
  <#if commentList??>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>批注历史</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover table-bordered" id="treeTable">
                            <thead>
                                <tr>
                                    <th>审批时间</th>
                                    <th>审批人</th>
                                    <th>批注信息</th>
                                </tr>
                            </thead>
                            <tbody>
                                 <#list commentList as comment>
                                    <tr>
                                        <td>${comment.time?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td>${comment.userName}</td>
                                        <td width="70%">${comment.fullMessage}</td>
                                    </tr>
                                 </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </#if>
    <div class="col-xs-12 text-right">
        <button type="button" class="btn btn-primary" id="back" data-id="${category.id}">返回</button>
    </div>
</div>
<script src="/js/sys/category/taskForm.js"></script>
</body>
</html>