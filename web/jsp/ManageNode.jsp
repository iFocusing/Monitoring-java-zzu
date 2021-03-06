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
//            alert(json);
            $('#organization1').combobox({
                data:json.rows,
                valueField:'oid',
                textField:'name'
            })
        },"json");
    }
</script>
<%--线路下拉列表--%>
<script>
    $(function () {
//        alert("hjj");
        LineList();  //获取数据源信息
    });
    function LineList(){
        var url="${basePath}servlet/SearchJsonLineServlet";
        //组织默认是总组织;
        $.post(url,function (json) {
//            alert(json);
            $('#line').combobox({
                data:json.rows,
                valueField:'lid',
                textField:'name'
            })
        },"json");
        $.post(url,function (json) {
//            alert(json);
            $('#line1').combobox({
                data:json.rows,
                valueField:'lid',
                textField:'name'
            })
        },"json");
    }
</script>
<%--查询节点--%>
<script type="text/javascript">
    function query(){
//        alert("11");
//        console.info($('#organization').combobox('getValue')+$('#line').combobox('getValue'));
//        alert("22");
        $('#dg').datagrid('load',{
//            fenpei:$("input[name='fenpei'][checked]").val(),
            fenpei:$('#fenpei').val(),
            source:$('#source').val(),
//            source:$('#source').val(),
            //id要和下面的组织机构id对应-----------------！！！！！！！！！！！！！！！
            oid:$('#organization').combobox('getValue'),
            lid:$('#line').combobox('getValue')
        }
        );
    }
</script>
<%--添加节点--%>
<script type="text/javascript">
    function addSource(){
        $.post('${basePath}servlet/AddNodeServlet', { source: $('#newSource').val() }, function (text, status) { alert(text); $('#adddlg').dialog('close');});
        <%--$.post('${basePath}servlet/AddNodeServlet', { source: $('#newSource').val() }, function (text, status) { alert(text); });--%>

    }
    function addSourceC(){
        $('#adddlg').dialog('close');
    }
</script>
<%--分配节点--%>
<script type="text/javascript">
    function upFenpei(){
        var rows = $('#dg').datagrid('getSelections');
         $.messager.confirm('确定操作', '确定要这样分配吗？', function (flag) {
            if (flag) {
                var ids = [];
                for (var i = 0; i < rows.length; i ++) {
                    console.info(rows[i].nid);
//                                ids.push(rows[i].nid);
                    $.post('${basePath}servlet/ModifyNodeServlet', {nid:rows[i].nid,pid:$('#pid').val() },function (text, status) { alert(text); });
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
<%--删除节点--%>
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
                                $.post('${basePath}servlet/DeleateNodeServlet', {nid:rows[i].nid},function (text, status) { alert(text); });
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





<body>
<%--add按钮弹出框界面--%>
<%--<div style="margin:20px 0;">--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">Open</a>--%>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">Close</a>--%>
<%--</div>--%>
<div id="adddlg" class="easyui-dialog" title="添加节点" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:400px;height:200px;padding:10px"
>
    请输入节点源地址<br>
    源地址:<input type="text" style="width:80px" name="newSource" id="newSource">

    <div id="dig-buttons">
        <a href="javascript:addSource()" class="easyui-linkbutton">添加</a>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>
</div>
<%--修改节点按钮弹出界面--%>
<div id="updlg" class="easyui-dialog" title="分配节点( 一个线杆最多分配3个节点)" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:400px;height:150px;padding:10px">

    请输入要分配的的线杆：<br>
    <span>线杆id:</span> <input type="text" style="width:80px" name="pid" id="pid">

    <div id="bb11">
        <a href="javascript:upFenpei()"  class="easyui-linkbutton" >分配</a>
        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--按钮之外的界面--%>
<div id="searchtool" style="padding:5px">
    <span>源地址:</span> <input type="text" style="width:80px" name="source" id="source">
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:120px" name="organization" id="organization">
    <span>线路:</span> <input class="easyui-combobox"  style="width:80px" name="line" id="line">
    <span>是否分配:</span>
    <select id="fenpei" name="fenpei" style="width:80px;">
        <option value="1">已分配</option>
        <option value="0">未分配</option>
    </select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="$('#adddlg').dialog('open')">添加</a>
        <%--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>--%>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.up();">修改/分配</a>
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
       url:'${basePath}servlet/SearchNodeServlet'">
    <thead>
    <tr>
        <th data-options="field:'nid',width:100,align:'center'">节点id</th>
        <th data-options="field:'source',width:100,align:'center'">源地址</th>
        <th data-options="field:'pid',width:100,align:'center'">所在线杆</th>
        <th data-options="field:'oname',width:100,align:'center'">组织机构</th>
        <th data-options="field:'lname',width:100,align:'center'">线路</th>

    </tr>
    </thead>
</table>


</body>
<script>
    $('#dg').datagrid({

        onSelect : function (rowIndex, rowData) {
//            console.info(rowIndex);
//            console.info(rowData);
//        if (obj.editRow != undefined) {
//            $('#dg').datagrid('endEdit', obj.editRow);
//        }
//
//        if (obj.editRow == undefined) {
//            $('#save,#redo').show();
//            $('#dg').datagrid('beginEdit', rowIndex);
//            obj.editRow = rowIndex;
//        }
        },
//        加载成功时候可行
//        onLoadSuccess:function(data){
//            console.info(data.rows);
//            alert(data.rows.substring(1,8));
//        },
//        onLoadError:function(){
////            console.info(data.rows);
//            alert("没有权限");
//        }
    });
</script>
</html>
