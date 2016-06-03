<%--
  Created by IntelliJ IDEA.
  User: hanpengyu
  Date: 2016/5/7
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>线路管理</title>
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>

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
<%--添加--%>
    <script type="text/javascript">
        function addSource(){
            $.post('${basePath}servlet/AddLineServlet', { name: $('#newname').val() }, function (text, status) { alert(text); $('#adddlg').dialog('close');});
        }
        function addSourceC(){
            $('#adddlg').dialog('close');
        }
    </script>
    <%--查询--%>
    <script type="text/javascript">
        function query(){
            $('#dg').datagrid('load',{
                        name:$('#linename').val(),
                    }
            );
        }
    </script>
    <%--删除节点--%>
    <script>
        $(function () {
            obj = {
                editRow: undefined,
                <%--remove: function () {--%>
                    <%--var rows = $('#dg').datagrid('getSelections');--%>
                    <%--console.info(rows);--%>
                    <%--if (rows.length > 0) {--%>
                        <%--$.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {--%>
                            <%--if (flag) {--%>
                                <%--var ids = [];--%>
                                <%--for (var i = 0; i < rows.length; i ++) {--%>
<%--//                                console.info(rows[i].nid);--%>
<%--//                                ids.push(rows[i].nid);--%>
                                    <%--$.post('${basePath}servlet/DeleateNodeServlet', {nid:rows[i].nid},function (text, status) { alert(text); });--%>
<%--//                                console.info(rows[i].nid);--%>
                                <%--}--%>
<%--//                            console.log(ids.join(','));--%>
                            <%--}--%>
                        <%--});--%>
                    <%--} else {--%>
                        <%--$.messager.alert('提示', '请选择要删除的记录！', 'info');--%>
                    <%--}--%>
                <%--},--%>
                up: function () {
                    var rows = $('#dg').datagrid('getSelections');
                    console.info(rows);
                    if (rows.length > 0) {
                        $('#updlg').dialog('open')

                    } else {
                        $.messager.alert('提示', '请选择要修改的线路！', 'info');
                    }
                },


            };
        })
    </script>
    <%--修改--%>
    <script type="text/javascript">
        function upFenpei(){
            var rows = $('#dg').datagrid('getSelections');
            $.messager.confirm('确定操作', '确定要这样修改吗？', function (flag) {
                if (flag) {
                    var ids = [];
                    for (var i = 0; i < rows.length; i ++) {
                        console.info(rows[i].nid);
//                                ids.push(rows[i].nid);
                        $.post('${basePath}servlet/ModifyLineServlet', {lid:rows[i].lid,name:$('#upname').val() },function (text, status) { alert(text); });
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
</head>
<body>
<div id="adddlg" class="easyui-dialog" title="添加线路" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:400px;height:200px;padding:10px"
>
    请输入线路名<br>
    线路名:<input type="text" style="width:80px" name="newname" id="newname">

    <div id="dig-buttons">
        <a href="javascript:addSource()" class="easyui-linkbutton">添加</a>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>
</div>
<%--修改按钮弹出界面--%>
<div id="updlg" class="easyui-dialog" title="修改线路" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:400px;height:150px;padding:10px">

    请输入新名字：<br>
    <span>线路名:</span> <input type="text" style="width:80px" name="upname" id="upname">

    <div id="bb11">
        <a href="javascript:upFenpei()"  class="easyui-linkbutton" >确定</a>
        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--按钮之外的界面--%>
<div id="searchtool" style="padding:5px">
    <span>线路名:</span> <input type="text" style="width:80px" name="linename" id="linename">
</select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="$('#adddlg').dialog('open')">添加</a>

        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.up();">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="obj.remove();">删除</a>
    </div>
</div>
<table id="dg" title="节点列表Client Side Pagination" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				loadFilter:pagerFilter,
       <%--url:'${basePath}servlet/ShowNodeServlet'">--%>
       url:'${basePath}servlet/SearchLineServletHPY'">
    <thead>
    <tr>
        <th data-options="field:'lid',width:100,align:'center'">线路id</th>
        <th data-options="field:'name',width:100,align:'center'">线路名称</th>
    </tr>
    </thead>
</table>


</body>
<script>
    $('#dg').datagrid({

        onSelect : function (rowIndex, rowData) {
        }
    });
</script>
</html>
