<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OA系统 - 登录</title>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<#include "/include.ftl"/>
    <script>
     if(window.top !== window.self){
         alertMsg("用户已过期", function(index){
             window.top.location = window.location ;
         });
         window.top.location = window.location;
   }
    </script>
</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">OA</h1>
        </div>
        <h3>欢迎使用OA管理系统</h3>
        <div  class="help-block" style="color:red;">
        ${message}
        </div>
        <form class="m-t" role="form" action="/login" method="post" id="commentForm">
            <div class="form-group">
                <input type="name" class="form-control" placeholder="用户名" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" name="password">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
        </form>
    </div>
</div>
<script src="/static/js/sys/login.js"></script>
</body>
</html>
