<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>消息列表</title>
    <#include "/include.ftl"/>
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>消息列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addMessage"><span class="glyphicon glyphicon-plus"></span>新增消息</button>
                    </div>
                </div>
                <form action="/message" role="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-6 m-b-xs">
                                  <input class="form-control layer-date" value="<#if message.startCreateDate??>${message.startCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  placeholder="请输入创建开始时间" id="startCreateDate" name="startCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#startCreateDate'});"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input class="form-control layer-date" placeholder="请输入创建结束时间" id="endCreateDate" value="<#if message.endCreateDate??>${message.endCreateDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" name="endCreateDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                                  <label class="laydate-icon inline demoicon" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',elem: '#endCreateDate'});"></label>
                             </div>
                              <div class="col-sm-4 m-b-xs">
                                  <div class="row">
                                      <input type="text" placeholder="请输入消息标题" class="form-control" name="title" value="<#if message.title??>${message.title}</#if>">
                                  </div>
                              </div>
                              <div class="col-sm-2 m-b-xs">
                                  <div class="input-group">
                                      <select data-placeholder="选择消息状态..." class="chosen-select" style="width: 180px;" tabindex="2" name="status" id="status">
                                          <option value="">请选择消息状态</option>
                                          <option value="0" <#if message?? && message.status?? && message.status ==0>selected="selected"</#if>>未读</option>
                                          <option value="1" <#if message?? && message.status?? && message.status ==1>selected="selected"</#if>>已读</option>
                                      </select>
                                  </div>
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
                                    <th>消息编号</th>
                                    <th>消息标题</th>
                                    <th>消息类型</th>
                                    <th>流程表单</th>
                                    <th>状态</th>
                                    <th>接收者</th>
                                    <th>发送者</th>
                                    <th>内容</th>
                                    <th>发送时间</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as message>
                                    <tr id="${message.id}">
                                        <td nowrap>
                                             ${message.id}
                                        </td>
                                        <td width="8%">${message.title}</td>
                                        <td><#if message.type==0>流程消息<#else>系统消息</#if></td>
                                        <td>${message.ctitle}</td>
                                        <td><#if message.status==0>未读<#else>已读</#if></td>
                                        <td>${message.recipientName}</td>
                                        <td>${message.senderName}</td>
                                        <td width="20%">${message.content}</td>
                                        <td><#if message.send_time??>${message.send_time?string('yyyy-MM-dd HH:mm:ss')!''}</#if></td>
                                        <td>${message.create_by}</td>
                                        <td>${message.create_date?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td nowrap>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${message.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${message.id}"><i class="fa fa-trash-o"></i> 删除 </a>
                                        </td>
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
<script src="/js/plugins/layer/laydate/laydate.js"></script>
<script src="/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/js/sys/message/messageList.js"></script>
</body>
</html>