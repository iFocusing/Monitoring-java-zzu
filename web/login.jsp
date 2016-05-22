<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
    <TITLE>用户登录</TITLE>
    <LINK href="images/Default.css" type=text/css rel=stylesheet>
    <LINK href="images/xtree.css" type=text/css rel=stylesheet>
    <LINK href="images/User_Login.css" type=text/css rel=stylesheet>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <META content="MSHTML 6.00.6000.16674" name=GENERATOR>
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</HEAD>
<script type="text/javascript">
    document.onkeydown = function(e){
        var event = e || window.event;
        var code = event.keyCode || event.which || event.charCode;
        if (code == 13) {
            login();
        }
    }
    $(function(){
        $("input[name='TxtUserName']").focus();
    });
    function login(){
        if($("input[name='TxtUserName']").val()=="" || $("input[name='TxtPassword']").val()==""){
            $("#showMsg").html("用户名或密码为空，请输入");
            $("input[name='TxtUserName']").focus();
        }else{
            //ajax异步提交
            jQuery.ajax({
                url: "${basePath}UserServlet",
                type: "post",
                data: { "TxtUserName": $("#TxtUserName").val() ,"TxtPassword":$("#TxtPassword").val()},
                dataType: "json",
                success: function(data) {
                    if(data!=null){
                        window.location="jsp/index.jsp"
                    }else{
                        alert("用户名或者密码错误,请重新登录");
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
//                complete: function(XMLHttpRequest, textStatus) {
//                    this; // 调用本次AJAX请求时传递的options参数
//                }
            });
        }
    }
</script>
<BODY id=userlogin_body>
<DIV></DIV>

<DIV id=user_login>
<DL>
  <DD id=user_top>
  <UL>
    <LI class=user_top_l></LI>
    <LI class=user_top_c></LI>
    <LI class=user_top_r></LI></UL>
  <DD id=user_main>
  <UL>
    <LI class=user_main_l></LI>
    <LI class=user_main_c>
    <DIV class=user_main_box>
    <UL>
      <LI class=user_main_text>用户名： </LI>
      <LI class=user_main_input>
          <INPUT class=TxtUserNameCssClass id=TxtUserName maxLength=20 name=TxtUserName>
      </LI>
    </UL>
    <UL>
      <LI class=user_main_text>密 码： </LI>
      <LI class=user_main_input>
          <INPUT class=TxtPasswordCssClass id=TxtPassword type=password name=TxtPassword>
      </LI>
    </UL>
        <UL><LI style="padding:5px 0;text-align: center;color: red;" id="showMsg"></LI></UL>
    <UL>
      <LI class=user_main_text>Cookie： </LI>
      <LI class=user_main_input>
          <SELECT id=DropExpiration name=DropExpiration>
            <OPTION value=None selected>不保存</OPTION>
            <OPTION value=Day>保存一天</OPTION>
            <OPTION value=Month>保存一月</OPTION>
            <OPTION value=Year>保存一年</OPTION>
        </SELECT>
      </LI>
    </UL>
    </DIV>
    </LI>
    <LI class=user_main_r>
        <a class="easyui-linkbutton"  href="javascript:void(0)" onclick="login()">
            <input  style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
                    type="image" src="images/user_botton.gif">
        </a>
    </LI>
  </UL>
  <DD id=user_bottom>
  <UL>
    <LI class=user_bottom_l></LI>
      <LI class=user_bottom_c></LI>
    <LI class=user_bottom_r></LI>
  </UL>
</DD>
</DL>
</DIV>
        <SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN>
        <SPAN id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN>
        <SPAN id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
<DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV>
<DIV></DIV>


</BODY></HTML>
