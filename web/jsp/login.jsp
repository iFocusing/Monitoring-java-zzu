<%--
  Created by IntelliJ IDEA.
  User: hanpengyu
  Date: 2016/4/15
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
</head>
<body>


<form name="loginForm" action="${basePath}servlet/loginServlet" method="post">

    <div class="panel-head"><strong>用户登录</strong></div>

    <input type="text" class="input" name="userName" placeholder="登录账号" maxlength="19" data-validate="required:请输入账号,number:请输入数字 ,length#<20:账号不得多于20位" /><br>
    <input type="password" class="input" name="password" placeholder="登录密码" maxlength="19" data-validate="required:请输入密码,number:请输入数字 ,length#<20:密码不得多于20位" /><br>
    <input type="text" class="input" name="code" placeholder="填写右侧的验证码" data-validate="required:请填写右侧的验证码" /><br>
    <input type="submit" value="登录"><br>
</form>

</body>
</html>
