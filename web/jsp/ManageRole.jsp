<%--
  Created by IntelliJ IDEA.
  User: hanpengyu
  Date: 2016/5/7
  Time: 10:30
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
<%--&lt;%&ndash;角色下拉列表&ndash;%&gt;--%>
<%--<script>--%>
    <%--$(function () {--%>
<%--//        alert("hjj");--%>
        <%--LineList();  //获取数据源信息--%>
    <%--});--%>
    <%--function LineList(){--%>
        <%--var url="${basePath}servlet/ShowRoleServlet";--%>
        <%--//组织默认是总组织;--%>
        <%--$.post(url,function (json) {--%>
<%--//            alert(json);--%>
            <%--$('#rolelist').combobox({--%>
                <%--data:json.rows,--%>
                <%--valueField:'rid',--%>
                <%--textField:'name'--%>
            <%--})--%>
        <%--},"json");--%>


    <%--}--%>
<%--</script>--%>
<%--查询--%>
<script type="text/javascript">
    function query(){
        $('#dg').datagrid('load',{
            roleName:$('#roleName').val(),
            description:$('#description').val(),
        });
    }
</script>
<script type="text/javascript">
    function addSourceC(){
        $('#adddlg').dialog('close');
    }
</script>
<%--添加节点--%>
<%--<script type="text/javascript">--%>
    <%--$('#ff').form('submit', {--%>

            <%--onSubmit: function(){--%>
        <%--//进行表单验证--%>
        <%--//如果返回false阻止提交--%>
    <%--},--%>
        <%--success:function(data){--%>
        <%--alert(data)--%>
    <%--}--%>
    <%--});--%>


    <%--function addSourceC(){--%>
        <%--$('#adddlg').dialog('close');--%>
    <%--}--%>
<%--</script>--%>
<%--删除--%>
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
                                $.post('${basePath}servlet/DeleateRoleServlet', {rid:rows[i].rid},function (text, status) { alert(text); });
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
                console.info(rows);
                if (rows.length > 0) {
                    $('#updlg').dialog('open')

                } else {
                    $.messager.alert('提示', '请选择要分配的节点！', 'info');
                }
            },


        };
    })
</script>
<%--分配--%>
<script type="text/javascript">

    function upFenpeiC(){
        $('#updlg').dialog('close');
    }
</script>
<body>
<%--add按钮弹出框界面--%>
<div id="adddlg" class="easyui-dialog" title="添加" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:460px;height:350px;padding:10px"
>
    <form action="${basePath}servlet/AddRoleServlet" method="post">
        角色名 <input type="text" name="name"  value="${name}"><br>
        描  述 <input type="text" name="description" value="${description}"><br>
        选择系统功能:<br>
        <label> <input type="checkbox" name="functions" value="1">组织机构新增</label>
        <label><input type="checkbox" name="functions" value="2">组织机构删除</label>
        <label><input type="checkbox" name="functions" value="3">组织机构修改</label>
        <label><input type="checkbox" name="functions" value="4">组织机构查看<br></label>
        <label><input type="checkbox" name="functions" value="5">用户新增 </label>
        <label><input type="checkbox" name="functions" value="6">用户删除</label>
        <label><input type="checkbox" name="functions" value="7">用户修改</label>
        <label><input type="checkbox" name="functions" value="8">用户查看<br></label>
        <label><input type="checkbox" name="functions" value="9">角色新增</label>
        <label><input type="checkbox" name="functions" value="10">角色删除</label>
        <label><input type="checkbox" name="functions" value="11">角色修改</label>
        <label><input type="checkbox" name="functions" value="12">角色查看<br></label>
        <label><input type="checkbox" name="functions" value="13">线路新增</label>
        <label><input type="checkbox" name="functions" value="14">线路删除</label>
        <label><input type="checkbox" name="functions" value="15">线路修改</label>
        <label><input type="checkbox" name="functions" value="16">线路查看<br></label>
        <label><input type="checkbox" name="functions" value="17">线杆新增</label>
        <label><input type="checkbox" name="functions" value="18">线杆删除</label>
        <label><input type="checkbox" name="functions" value="19">线杆修改</label>
        <label><input type="checkbox" name="functions" value="20">线杆查看<br></label>
        <label><input type="checkbox" name="functions" value="21">节点新增</label>
        <label><input type="checkbox" name="functions" value="22">节点删除</label>
        <label><input type="checkbox" name="functions" value="23">节点修改</label>
        <label><input type="checkbox" name="functions" value="24">节点查询<br></label>
        <input type="submit" value="添加">
    </form>
        <%--<form id="ff" method="post" action="${basePath}servlet/AddRoleServlet">--%>
                <%--角色名<input class="easyui-validatebox" type="text" name="roleName1" data-options="required:true" /><br>--%>
                <%--描述<input class="easyui-validatebox" type="text" name="description1" data-options="required:true" /><br>--%>

                <%--<input type="submit" value="添加">--%>
        <%--</form>--%>
    <div id="dig-buttons">
        <%--<a href="javascript:addSource()" class="easyui-linkbutton">添加</a>--%>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>

</div>
<div id="updlg" class="easyui-dialog" title="修改" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:460px;height:350px;padding:10px">

    <form action="${basePath}servlet/ModifyRoleServlet" method="post">
        旧角色id <input type="text" name="rid"  value="${rid}"><br>
        新角色名 <input type="text" name="name"  value="${name}"><br>
        新描述 <input type="text" name="description" value="${description}"><br>
        重新选择系统功能:<br>
        <label> <input type="checkbox" name="functions" value="1">组织机构新增</label>
        <label><input type="checkbox" name="functions" value="2">组织机构删除</label>
        <label><input type="checkbox" name="functions" value="3">组织机构修改</label>
        <label><input type="checkbox" name="functions" value="4">组织机构查看<br></label>
        <label><input type="checkbox" name="functions" value="5">用户新增 </label>
        <label><input type="checkbox" name="functions" value="6">用户删除</label>
        <label><input type="checkbox" name="functions" value="7">用户修改</label>
        <label><input type="checkbox" name="functions" value="8">用户查看<br></label>
        <label><input type="checkbox" name="functions" value="9">角色新增</label>
        <label><input type="checkbox" name="functions" value="10">角色删除</label>
        <label><input type="checkbox" name="functions" value="11">角色修改</label>
        <label><input type="checkbox" name="functions" value="12">角色查看<br></label>
        <label><input type="checkbox" name="functions" value="13">线路新增</label>
        <label><input type="checkbox" name="functions" value="14">线路删除</label>
        <label><input type="checkbox" name="functions" value="15">线路修改</label>
        <label><input type="checkbox" name="functions" value="16">线路查看<br></label>
        <label><input type="checkbox" name="functions" value="17">线杆新增</label>
        <label><input type="checkbox" name="functions" value="18">线杆删除</label>
        <label><input type="checkbox" name="functions" value="19">线杆修改</label>
        <label><input type="checkbox" name="functions" value="20">线杆查看<br></label>
        <label><input type="checkbox" name="functions" value="21">节点新增</label>
        <label><input type="checkbox" name="functions" value="22">节点删除</label>
        <label><input type="checkbox" name="functions" value="23">节点修改</label>
        <label><input type="checkbox" name="functions" value="24">节点查询<br></label>
        <input type="submit" value="修改">
    </form>
    <div id="bb11">

        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--正常界面--%>
<div id="searchtool" style="padding:5px">
    <span>角色名:</span> <input type="text" style="width:80px" name="roleName" id="roleName">
    <span>描述:</span> <input type="text" style="width:80px" name="description" id="description">
    </select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="$('#adddlg').dialog('open')" >添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.up();" >修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="obj.remove();" >删除</a>
    </div>
</div>
<table id="dg" title="角色列表Client Side Pagination" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				loadFilter:pagerFilter,
       url:'${basePath}servlet/ShowRoleServlet'">
    <thead>
    <tr>
        <th data-options="field:'rid',width:40,align:'center'">角色id</th>
        <th data-options="field:'name',width:80,align:'center'">角色名</th>
        <th data-options="field:'description',width:140,align:'left'">描述</th>
        <th data-options="field:'functionsName',width:600,align:'left'">拥有的系统功能</th>

    </tr>
    </thead>
</table>
</body>
<script>
    $('#dg').datagrid({
        onSelect : function (rowIndex, rowData) {
        },
//        onLoadError:function(){
//            alert("没有得到数据");
//        }
    });
</script>
</html>
