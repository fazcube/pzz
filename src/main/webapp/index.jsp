<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div>
    <h1>首页</h1>
    <ul>
        <shiro:hasAnyRoles name="merchant,admin">
            <li><a href="javascript:;">用户管理</a>
                <ul>
                    <li><a href="javascript:;">增加用户</a></li>
                    <li><a href="javascript:;">删除用户</a></li>
                    <li><a href="javascript:;">查找用户</a></li>
                    <li><a href="javascript:;">修改用户</a></li>
                </ul>
            </li>
            <li><a href="javascript:;">商品管理</a></li>
        </shiro:hasAnyRoles>
        <shiro:hasRole name="admin">
            <li><a href="javascript:;">订单管理</a></li>
            <li><a href="javascript:;">物流管理</a></li>
        </shiro:hasRole>
    </ul>
</div>
</body>
</html>