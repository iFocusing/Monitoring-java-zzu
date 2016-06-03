
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


<%--查询--%>
<script type="text/javascript">
    function query(){
//        alert("11");
//        console.info($('#organization').combobox('getValue')+$('#line').combobox('getValue'));
//        alert("22");
        $('#dg').datagrid('load',{
//                    fenpei:$('#fenpei').val(),
//                    aid:$('#aid').combobox('getValue'),
                    oid:$('#organization').combobox('getValue'),
                    lid:$('#line').combobox('getValue')
                }
        );


    }
</script>
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
                                $.post('${basePath}servlet/DeleatePoleServlet', {pid:rows[i].pid,lid:rows[i].lid,location:rows[i].location},function (text, status) { alert(text); });
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
<%--添加--%>
<script type="text/javascript">
    function addSource(){
        $.post('${basePath}servlet/AddPoleServlet', { longitude: $('#longitude').val(),latitude: $('#latitude').val() }, function (text, status) { alert(text); $('#adddlg').dialog('close');});


    }
    function addSourceC(){
        $('#adddlg').dialog('close');
    }
</script>
<%--分配--%>
<script type="text/javascript">
    function upFenpei(){
        var rows = $('#dg').datagrid('getSelections');
        $.messager.confirm('确定操作', '确定要这样分配吗？', function (flag) {
            if (flag) {
                var ids = [];
                for (var i = 0; i < rows.length; i ++) {
                    console.info(rows[i].pid);
//                                ids.push(rows[i].nid);
                    $.post('${basePath}servlet/ModifyPoleServlet',
                            {   pid:rows[i].pid,
                                location:$('#location').val(),
                                oid:$('#organization1').combobox('getValue'),
                                lid:$('#line1').combobox('getValue') },
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

<body>
<%--add按钮弹出框界面--%>
<div id="adddlg" class="easyui-dialog" title="添加" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:400px;height:200px;padding:10px"
>
    请输入线杆经纬度<br>
    经度:<input type="text" style="width:80px" name="longitude" id="longitude">
    纬度:<input type="text" style="width:80px" name="latitude" id="latitude">

    <div id="dig-buttons">
        <a href="javascript:addSource()" class="easyui-linkbutton">添加</a>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>
</div>
<%--分配按钮弹出界面--%>
<div id="updlg" class="easyui-dialog" title="分配" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:400px;height:200px;padding:10px">

    <%--<span>地区:</span> <input class="easyui-combobox"  style="width:120px" name="region1" id="region1">--%>
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:120px" name="organization1" id="organization1"><br>
    <span> 线  路 :</span> <input class="easyui-combobox"  style="width:120px" name="line1" id="line1"><br>
    <span>相对位置:</span> <input type="text" style="width:120px" name="location" id="location">
    <div id="bb11">
        <a href="javascript:upFenpei()"  class="easyui-linkbutton" >分配</a>
        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--查询--%>
<div id="searchtool" style="padding:5px">
    <span>地区:</span> <input class="easyui-combobox"  style="width:120px" name="region" id="region">
    <span>组织机构:</span> <input class="easyui-combobox"  style="width:120px" name="organization" id="organization">
    <span>线路:</span> <input class="easyui-combobox"  style="width:120px" name="line" id="line">
</select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="$('#adddlg').dialog('open')" >添加</a>
        <%--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>--%>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.up();" >分配</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="obj.remove();" >删除</a>
    </div>
</div>
<table id="dg" title="线杆列表Client Side Pagination" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				loadFilter:pagerFilter,
       url:'${basePath}servlet/SearchPoleServletHPY'">
    <thead>
    <tr>
        <th data-options="field:'pid',width:100,align:'center'">线杆id</th>
        <th data-options="field:'oname',width:100,align:'center'">组织机构</th>
        <th data-options="field:'aname',width:100,align:'center'">地区</th>
        <th data-options="field:'lname',width:100,align:'center'">线路</th>
        <th data-options="field:'location',width:100,align:'center'">相对位置</th>
        <th data-options="field:'longitude',width:100,align:'center'">经度</th>
        <th data-options="field:'latitude',width:100,align:'center'">纬度</th>

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