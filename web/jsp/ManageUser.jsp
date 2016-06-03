<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hanpengyu
  Date: 2016/4/28
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic DataGrid - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
</head>
<%--表格数据分页--%>
<script>
    function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize){
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh',{
                    pageNumber:pageNum,
                    pageSize:pageSize
                });
                dg.datagrid('loadData',data);
            }
        });
        if (!data.originalRows){
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }
</script>
<%--表格上一些按钮的js--%>
<script>
    $(function () {
        var pager = $('#dg').datagrid().datagrid('getPager');	// get the pager of datagrid
        pager.pagination({
            buttons:[{
                iconCls:'icon-search',
                handler:function(){
                    alert('search');
                }
            },{
                iconCls:'icon-add',
                text:'添加',
                handler:function(){
                    alert('add');
                }
            },{
                iconCls:'icon-edit',
                handler:function(){
                    alert('edit');
                }
            }]
        });
    })
</script>
<%--组织机构下拉列表--%>
<script>
    $(function () {
//        alert("hjj");
        OrganizationList();  //获取数据源信息
    });
    function OrganizationList(){
        var url="${basePath}servlet/SearchJsonOrganizationServlet";
        //组织默认是总组织;
        $.post(url,function (json) {
//            alert(json);
            $('#organization').combobox({
                data:json.rows,
                valueField:'oid',
                textField:'name'
            })
        },"json");
        $.post(url,function (json) {
            $('#organization1').combobox({
                data:json.rows,
                valueField:'oid',
                textField:'name',
                onLoadSuccess: function () { //数据加载完毕事件
                    var data = $('#organization1').combobox('getData');
                    if (data.length > 0) {
                        $("#organization1").combobox('select', data[0].oid);
                    }
                }
            })
        },"json");
        $.post(url,function (json) {
//            alert(json);
            $('#organization11').combobox({
                data:json.rows,
                valueField:'oid',
                textField:'name',
                onLoadSuccess: function () { //数据加载完毕事件
                    var data = $('#organization11').combobox('getData');
                    if (data.length > 0) {
                        $("#organization11").combobox('select', data[0].oid);
                    }
                }
            })
        },"json");

    }
</script>
<%--角色下拉列表--%>
<script>
    $(function () {
//        alert("hjj");
        RoleList();  //获取数据源信息
    });
    function RoleList(){
        var url="${basePath}servlet/ShowRoleServlet";
        //组织默认是总组织;
        $.post(url,function (json) {
//            alert(json);
            $('#role').combobox({
                data:json.rows,
                valueField:'rid',
                textField:'name',

            })
        },"json");
        $.post(url,function (json) {
//            alert(json);
            $('#role1').combobox({
                data:json.rows,
                valueField:'rid',
                textField:'name',
                onLoadSuccess: function () { //数据加载完毕事件
                    var data = $('#role1').combobox('getData');
                    if (data.length > 0) {
                        $("#role1").combobox('select', data[0].rid);
                    }
                }

            })
        },"json");
        $.post(url,function (json) {
//            alert(json);
            $('#role11').combobox({
                data:json.rows,
                valueField:'rid',
                textField:'name',
                onLoadSuccess: function () { //数据加载完毕事件
                    var data = $('#role11').combobox('getData');
                    if (data.length > 0) {
                        $("#role11").combobox('select', data[0].rid);
                    }
                }
            })
        },"json");
    }
</script>
<%--查询--%>
<script type="text/javascript">
    function query(){
        $('#dg').datagrid('load',{
                    status:$('#status').val(),
                    username:$('#username').val(),
                    oid:$('#organization').combobox('getValue'),
                    rid:$('#role').combobox('getValue')
                }
        );
    }
</script>
<%--添加--%>
<script type="text/javascript">
    function addSource(){
//        var val=$('#role1').combobox('getValues');
        var roleValues = $('#role1').combobox('getValues');
        var strRoleValues="";
        for (var i=0;i<roleValues.length;i++){
            strRoleValues+=roleValues[i]+",";
        }

        $.post('${basePath}servlet/AddUserServlet', {
            username: $('#name1').val() ,
            password: $('#password1').val() ,
            address: $('#address1').val() ,
            sex: $('#sex1').val() ,
            status:$('#status1').val(),
            tel: $('#tel1').val() ,
            oid:$('#organization1').combobox('getValue'),
            birthday:$('#birthday1').datebox('getValue'),
            role1:strRoleValues
        }, function (text, status) { alert(text); $('#adddlg').dialog('close');});
    }
    function addSourceC(){
//        $('#adddlg').dialog('close');
        console.info(  $('#name1').val() ,
//                $('#password1').val() ,
//                 $('#address1').val() ,
//                $('#sex1').val() ,
//                $('#status1').val(),
//                 $('#tel1').val() ,
//                $ ('#organization1').combobox('getValue'),
//                $('#birthday1').combobox('getValue'),
                $('#role1').combobox('getValues'))
    }
</script>
<%--修改分配--%>
<script type="text/javascript">

    function upFenpei(){
        var rows = $('#dg').datagrid('getSelections');
        $.messager.confirm('确定操作', '确定要这样分配吗？', function (flag) {
            if (flag) {

                var roleValues = $('#role11').combobox('getValues');
                var strRoleValues="";
                for (var i=0;i<roleValues.length;i++){
                    strRoleValues+=roleValues[i]+",";
                }
                for (var i = 0; i < rows.length; i ++) {
//                    console.info("修改分配uid："+rows[i].uid);
//                                ids.push(rows[i].nid);
                    $.post('${basePath}servlet/ModifyUserServlet', {
                                uid:rows[i].uid,
                                username: $('#name11').val() ,
                                password: $('#password11').val() ,
                                address: $('#address11').val() ,
                                sex: $('#sex11').val() ,
                                status:$('#status11').val(),
                                tel: $('#tel11').val() ,
                                oid:$('#organization11').combobox('getValue'),
                                birthday:$('#birthday11').datebox('getValue'),
                                role11:strRoleValues,

                    },
                        function (text, status) { alert(text); });
//                                console.info(rows[i].nid+'---'+$('#pid').val());
                    $('#updlg').dialog('close');
                }
            }
        });
    }
    function upFenpeiC(){
        $('#updlg').dialog('close');
    }
</script>
<%--删除和弹窗控制--%>
<script>
    $(function () {
        obj = {
            editRow: undefined,
            remove: function () {
                var rows = $('#dg').datagrid('getSelections');

                console.info(rows);
                if (rows.length > 0) {
                    $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                        if (flag) {
                            var ids = [];
                            for (var i = 0; i < rows.length; i ++) {
//                                console.info(rows[i].nid);
//                                ids.push(rows[i].nid);
                                $.post('${basePath}servlet/DeleateUserServlet', {uid:rows[i].uid},function (text, status) { alert(text); });
//                                console.info(rows[i].nid);
                            }
//                            console.log(ids.join(','));
                        }
                    });
                } else {
                    $.messager.alert('提示', '请选择要删除的记录！', 'info');
                }
            },
            up: function () {
                var rows = $('#dg').datagrid('getSelections');

                if (rows.length > 0) {
                    $("#name11").val(rows[0].username);
                    $("#password11").val(rows[0].password);
                    $("#sex11").val(rows[0].sex);
                    $("#address11").val(rows[0].address);
                    $("#tel11").val(rows[0].tel);
                    $("#birthday11").val(rows[0].birthday);
                    $("#status11").val(rows[0].status);
//                    $("#organization11").val(rows[0].organizationName);
//                    $("#role11").val(rows[0].roleNames);

                    $('#updlg').dialog('open')


                } else {
                    $.messager.alert('提示', '请选择要修改的用户！', 'info');
                }
            },
        };
    })
</script>
<%--日期格式化--%>
<script>
    $(function () {
        $('#birthday1').datebox({
           formatter : function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+"-"+m+"-"+d;
            },
        })
        $('#birthday11').datebox({
            formatter : function(date){
                var y = date.getFullYear();
                var m = date.getMonth()+1;
                var d = date.getDate();
                return y+"-"+m+"-"+d;
            },
        })

    })

</script>

<body>
<%--add按钮弹出框界面--%>
<div id="adddlg" class="easyui-dialog" title="添加" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:400px;height:350px;padding:10px"
>
    <span>用户名:</span><input type="text" style="width:80px" name="name1" id="name1" required="true"><br>
    <span>密码:</span><input type="text" style="width:80px" name="passsword1" id="password1"><br>
    <span>性别:</span>
    <select id="sex1" name="sex1" style="width:80px;">
        <option value="1">男</option>
        <option value="0">女</option>
    </select><br>
    <span>住址:</span><input type="text" style="width:80px" name="address1" id="address1"><br>
    <span>联系方式:</span><input type="text" style="width:80px" name="tel1" id="tel1"><br>
    <span>出生日期:</span><input class="easyui-datebox" editable="false" value="1970-11-11" style="width:150px" id="birthday1" name="birthday1" ><br>
    <span>账号状态:</span>
    <select id="status1" name="status1" style="width:80px;">
        <option value="1">正常</option>
        <option value="0">锁死</option>
    </select><br>
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:150px" name="organization1" id="organization1"><br>
    <span>角色:</span> <input class="easyui-combobox" editable="false" multiple="true" separator="," style="width:150px" name="role" id="role1"><br>
    <div id="dig-buttons">
        <a href="javascript:addSource()" class="easyui-linkbutton">添加</a>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>
</div>
<%--修改按钮弹出界面--%>
<div id="updlg" class="easyui-dialog" title="修改/重分配" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:400px;height:450px;padding:10px">
    <span>用户名:</span><input   type="text" style="width:80px" name="name11" id="name11" required="true"><br>
    <span>密码:</span><input type="text" style="width:80px" name="password11" id="password11"><br>
    <span>性别:</span>
    <select id="sex11" name="sex11" style="width:80px;">
        <option value="1">男</option>
        <option value="0">女</option>
    </select><br>
    <span>住址:</span><input type="text" style="width:80px" name="address1" id="address11"><br>
    <span>联系方式:</span><input type="text" style="width:80px" name="tel1" id="tel11"><br>
    <span>出生日期:</span><input class="easyui-datebox" editable="false" value="1970-11-11" style="width:150px" id="birthday11" name="birthday11" ><br>
    <span>账号状态:</span>
    <select id="status11" name="status11" style="width:80px;">
        <option value="1">正常</option>
        <option value="0">锁死</option>
    </select><br>
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:150px" name="organization11" id="organization11"><br>
    <span>角色:</span> <input class="easyui-combobox" editable="false" multiple="true" separator="," style="width:150px" name="role11" id="role11"><br>

    <div id="bb11">
        <a href="javascript:upFenpei()"  class="easyui-linkbutton" >分配</a>
        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--按钮之外的界面--%>
<%--查询div--%>
<div id="searchtool" style="padding:5px">
    <span>用户名:</span> <input type="text" style="width:80px" name="username" id="username">
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:120px" name="organization" id="organization">
    <span>角色:</span> <input class="easyui-combobox"  style="width:80px" name="role" id="role">
    <span>用户状态:</span> <select id="status" name="status" style="width:80px;">
    <option value="1">正常</option>
    <option value="0">锁死</option>
</select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>
<%--增加修改删除div--%>
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true"  onclick="$('#adddlg').dialog('open')">添加</a>
        <%--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>--%>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.up();">修改重分配</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="obj.remove();">删除</a>
    </div>
</div>
<%--列表table--%>
<table id="dg" title="用户列表" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				loadFilter:pagerFilter,
				loadFilter:pagerFilter,
       <%--url:'${basePath}servlet/ShowNodeServlet'">--%>
       url:'${basePath}servlet/SearchUserServlet1'">
    <thead>
    <tr>
        <th data-options="field:'uid',width:50,align:'center'">用户id</th>
        <th data-options="field:'username',width:80,align:'center'">用户名</th>
        <th data-options="field:'organizationName',width:100,align:'left'">组织机构</th>
        <th data-options="field:'roleNames',width:300,align:'left'">拥有角色</th>
        <th data-options="field:'sexString',width:50,align:'center'">性别</th>
        <th data-options="field:'tel',width:100,align:'center'">联系方式</th>
        <th data-options="field:'birthdayString',width:100,align:'center'">出生日期</th>
        <th data-options="field:'address',width:100,align:'left'">家庭住址</th>
        <th data-options="field:'statusString',width:50,align:'center'">账号状态</th>
        <th data-options="field:'password',width:30,align:'center'">密码</th>



    </tr>
    </thead>
</table>
</body>
<script>
    $('#dg').datagrid({
        onSelect : function (rowIndex, rowData) {
        },
    });
</script>
</html>

