<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>流程对应表单列表</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>流程对应表单</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addCategory"><span class="glyphicon glyphicon-plus"></span>新增流程对应表单</button>
                    </div>
                </div>
                <form action="/category" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-4 m-b-xs">
                                  <input type="text" placeholder="请输入采购标题" class="form-control" name="title" value="<#if category.title??>${category.title}</#if>">
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <div class="input-group">
                                      <select data-placeholder="选择审批状态..." class="chosen-select" style="width: 200px;" tabindex="2" name="status" id="status">
                                          <option value="">请选择审批状态</option>
                                          <option value="0" <#if category.status?? && category.status=='0'>selected="selected"</#if>>未发起</option>
                                          <option value="1" <#if category.status?? && category.status=='1'>selected="selected"</#if>>待审核</option>
                                          <option value="2" <#if category.status?? && category.status=='2'>selected="selected"</#if>>驳回</option>
                                          <option value="3" <#if category.status?? && category.status=='3'>selected="selected"</#if>>同意</option>
                                          <option value="4" <#if category.status?? && category.status=='4'>selected="selected"</#if>>询价中</option>
                                          <option value="5" <#if category.status?? && category.status=='5'>selected="selected"</#if>>待放款</option>
                                          <option value="6" <#if category.status?? && category.status=='6'>selected="selected"</#if>>已放款</option>
                                          <option value="7" <#if category.status?? && category.status=='7'>selected="selected"</#if>>采购完成</option>
                                      </select>
                                  </div>
                              </div>
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if category.startCreateDate??>${category.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if category.endCreateDate??>${category.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                              </div>
                              <div class="col-sm-2 m-b-xs pull-right">
                                  <button type="button" class="btn btn-primary reset"><i class="fa fa-refresh"></i>重置</button>
                                  <button type="submit" class="btn btn-primary pull-right"><i class="fa fa-search"></i>搜索</button>
                              </div>
                          </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>采购编号</th>
                                    <th>采购标题</th>
                                    <th>申请人</th>
                                    <th>当前审批人</th>
                                    <th>流程ID</th>
                                    <th>采购专员</th>
                                    <th>审批状态</th>
                                    <th>用途</th>
                                    <th>申请理由</th>
                                    <th>备注</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as category>
                                    <tr id="${category.id}">
                                        <td nowrap>
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            <a href="#">
                                            <#--</@shiro.hasPermission>-->
                                             ${category.id}
                                            <#--<@shiro.hasPermission name="menu:update">-->
                                            </a>
                                            <#--</@shiro.hasPermission>-->
                                        </td>
                                        <td>${category.title}</td>
                                        <td>${category.applyUserName}</td>
                                        <td>${category.assigneeName}</td>
                                        <td>${category.deployment_id}</td>
                                        <td><#if category.buyerName??>${category.buyerName}</#if></td>
                                        <td><#if category.status==0>未发起<#elseif category.status==1>待审核<#elseif category.status==2>驳回
                                        <#elseif category.status==3>同意<#elseif category.status==4>询价中<#elseif category.status==5>待放款<#elseif category.status==6>已放款<#else>采购完成</#if></td>
                                        <td><#if category.purpose??>${category.purpose}</#if></td>
                                        <td><#if category.reason??>${category.reason}</#if></td>
                                        <td><#if category.remarks??>${category.remarks}</#if></td>
                                        <td>${category.create_date?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td nowrap>
                                            <#if category.status==0>
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${category.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${category.id}"><i class="fa fa-trash-o"></i> 删除 </a>
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary play" data-id="${category.id}"><i class="fa fa-play"></i> 发起申请 </a>
                                            </#if>
                                            <#if category.status!=0>
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary showRecord" data-id="${category.id}"><i class="fa fa-align-justify"></i> 查看审核记录</a>
                                            </#if>
                                                <a href="javascript:" class="btn btn-outline btn-xs btn-primary itemDetail" data-id="${category.id}"><i class="fa fa-info"></i> 查看明细</a>
                                                <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary" onclick="findIndexByhref('/category')"><i class="fa fa-trash-o"></i> 测试 </a>-->
                                                <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary" onclick="refreshByhref('/category')"><i class="fa fa-trash-o"></i> 刷新 </a>-->
                                                <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary" onclick="getNewIndex()"><i class="fa fa-trash-o"></i> 获取index </a>-->
                                                <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary" onclick="opentab('/menu','垃圾列表')"><i class="fa fa-trash-o"></i> 打开窗口 </a>-->
                                                <#--<a href="javascript:" class="btn btn-outline btn-xs btn-primary" onclick="closetab('/menu')"><i class="fa fa-trash-o"></i> 关闭窗口 </a>-->
                                        </td>
                                    <#---</@shiro.hasPermission>--->
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
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/sys/category/categoryList.js"></script>
</body>
</html>