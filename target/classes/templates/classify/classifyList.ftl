<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>采购物品分类列表</title>
    <#include "/include.ftl"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>采购物品分类列表</h5>
                    <div class="ibox-tools">
                        <button class="btn btn-primary btn-xs" id="addClassify"><span class="glyphicon glyphicon-plus"></span>新增采购物品分类</button>
                    </div>
                </div>
                <form action="/classify" classify="form" id="selectFrom">
                    <input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                  <div class="ibox-content">
                          <div class="row">
                              <div class="col-sm-3 pull-right">
                                  <div class="input-group">
                                      <input type="search" placeholder="请输入分类名称" class="input-sm form-control" name="name" value="<#if classify.name??>${classify.name}</#if>"> <span class="input-group-btn">
                                    <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i> 搜索</button> </span>
                                  </div>
                              </div>
                          </div>
                            <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered" id="treeTable">
                                <thead>
                                <tr>
                                    <th>分类编号</th>
                                    <th>分类名称</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>修改人</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list page.list as classify>
                                    <tr id="${classify.id}">
                                        <td>
                                          ${classify.id}
                                        </td>
                                        <td nowrap>
                                           ${classify.name}
                                        </td>
                                        <td>${classify.createBy}</td>
                                        <td>${classify.createDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td>${classify.updateBy}</td>
                                        <td>${classify.updateDate?string('yyyy-MM-dd HH:mm:ss')!''}</td>
                                        <td nowrap>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary edit" data-id="${classify.id}"><i class="fa fa-pencil"></i> 编辑 </a>
                                            <a href="javascript:" class="btn btn-outline btn-xs btn-primary delete" data-id="${classify.id}"><i class="fa fa-trash-o"></i> 删除 </a>
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
<script src="/js/sys/classify/classifyList.js"></script>
</body>
</html>