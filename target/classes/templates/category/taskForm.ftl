<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>办理任务</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>办理任务</h5>
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
                                    <th>申请人</th>
                                    <th>用途</th>
                                    <th>申请理由</th>
                                    <th>库存状态</th>
                                    <th>采购渠道</th>
                                    <th>支付方式</th>
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
                                        <td>${category.userName}</td>
                                        <td>${category.purpose}</td>
                                        <td>${category.reason}</td>
                                        <td>
                                            <div class="row">
                                                <div class="radio i-checks" style="float: left">
                                                    <label class="">
                                                        <div style="position: relative;" class="iradio_square-green" onclick="setInventoryStatus(this)"><input style="position: absolute; opacity: 0;"  value="0" <#if category.inventoryStatus?? && category.inventoryStatus==0>checked="checked"</#if> name="inventoryStatus" type="radio"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i>库存不足</label>
                                                </div>
                                                <div class="radio i-checks"  style="float: left;margin-top:10px">
                                                    <label class="">
                                                        <div style="position: relative;" class="iradio_square-green" onclick="setInventoryStatus(this)"><input style="position: absolute; opacity: 0;"  value="1" <#if category.inventoryStatus?? && category.inventoryStatus==1>checked="checked"</#if> name="inventoryStatus" type="radio"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div> <i></i>库存充足</label>
                                                </div>
                                            </div>
                                            <#--<#if category.inventoryStatus?? && category.inventoryStatus==0>库存不足<#elseif category.inventoryStatus?? && category.inventoryStatus==1>库存充足</#if>-->
                                        </td>
                                        <td <#if Session.userInfo.positionName=='采购专员'>ondblclick="tdclick(this)" title="双击编辑" </#if> data-name="channels" data-id="${category.id}">${category.channels}</td>
                                        <td <#if Session.userInfo.positionName=='采购专员'>ondblclick="tdclick(this)" title="双击编辑" </#if> data-name="payment" data-id="${category.id}">${category.payment}</td>
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
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form method="post" class="form-horizontal" id="commentForm" action="/category/submitCateGoryTask">
                        <#list categoryItems.list as item>
                            <input value="${item.id}" name="itemId" type="hidden">
                            <input value="${item.itemName}" name="itemName" type="hidden">
                            <input value="${item.needcount}" name="needcount" type="hidden">
                            <input value="${item.count}" name="count" type="hidden" id="${item.id}">
                            <input value="${item.stock}" name="stock" type="hidden" id="${item.id}">
                            <input value="${item.model}" name="model" type="hidden">
                            <input value="${item.applyPrice}" name="applyPrice" type="hidden">
                            <input value="${item.actualPrice}" name="actualPrice" type="hidden" id="${item.id}">
                            <input value="${item.itemRemarks}" name="itemRemarks" type="hidden">
                        </#list>
                        <input name="itemSize" id="itemSize" value="<#if categoryItems.list??>${categoryItems.list?size}<#else>1</#if>" type="hidden">
                        <input name="formId" value="${category.id}" type="hidden">
                        <input name="taskId" value="${taskId}" type="hidden">
                        <input name="inventoryStatus" value="${category.inventoryStatus}" type="hidden" id="inventoryStatus">
                        <input name="channels" value="${category.channels}" type="hidden" id="${category.id}">
                        <input name="payment" value="${category.payment}" type="hidden" id="${category.id}">
                        <input name="positionName" value="${Session.userInfo.positionName}" type="hidden" id="positionName">
                        <div class="form-group">
                            <label class="col-sm-1 control-label">批注:</label>
                            <div class="col-sm-11">
                                <textarea class="form-control" id="comment" placeholder="请填写批注" name="comment"
                                          maxlength="500"></textarea>
                            </div>
                        </div>
                        <input name="outcome" id="outcome" value="" type="hidden">
                        <div class="hr-line-dashed"></div>
                    </form>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <#--<#if outcomeList??>-->
                    <#--<#list outcomeList as b>-->
                        <#--<button type="button" class="btn btn-white text-right" id="cancel">${b}</button>-->
                    <#--</#list>-->
                <#--</#if>-->
                <div class="col-xs-12 text-right">
                    <#if Session.admin?? && Session.admin.id!=category.userId>
                        <button type="button" class="btn btn-white agree" id="cancel">驳回</button>
                        <button type="button" class="btn btn-primary agree">同意</button>
                 <#else>
                    <button type="button" class="btn btn-primary agree" id="btnSave">提交申请</button>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery.form.js"></script>
<script src="/js/plugins/iCheck/icheck.min.js"></script>
<script src="/js/sys/category/taskForm.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
</body>
</html>