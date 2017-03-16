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
    <form action="/workflow/catrgory/update" method="post">
        <div class="col-md-12">
            <div class="form-group">
                <label class="col-sm-3 control-label">分类名称：</label>
                <div class="col-sm-9">
                 <input type="hidden" name="id" value="${deploy.id}">
                    <input type="text" name="name" class="form-control" placeholder="请输入名称"
                           data-form-un="1471853294868.6694" value="${deploy.name}"> <span class="help-block m-b-none">请输出分类的名称，必填</span>


                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12 col-sm-offset-3">
                    <#--<input >-->
                    <input type="button" class="btn btn-primary submit" data-form-sbm="1471853294868.6694" value="保存">
                    <button class="btn btn-white">取消</button>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>

</body>
</html>
