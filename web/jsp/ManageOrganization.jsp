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

        }
    </script>
    <%--添加--%>
    <script type="text/javascript">
        function addSource(){
            var rows = $('#dg').datagrid('getSelections');
            for (var i = 0; i < rows.length; i ++) {
                console.info(rows[i].nid);
                $.post('${basePath}servlet/addOrganizationServlet', { parentId:rows[i].oid,name: $('#newName').val(),address: $('#newAddress').val() }, function (text, status) { alert(text); $('#adddlg').dialog('close');});
            }

        }
        function addSourceC(){
            $('#adddlg').dialog('close');
        }
    </script>
    <%--查询--%>
    <script type="text/javascript">
        function query(){
            $('#dg').datagrid('load',{
                name:$('#name').val(),
                address:$('#address').val(),
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
                        $.messager.alert('提示', '请选择要修改的组织机构！', 'info');
                    }
                },
                add: function () {
                    var rows = $('#dg').datagrid('getSelections');
                    console.info(rows);
                    if (rows.length > 0) {
                        $('#adddlg').dialog('open')

                    } else {
                        $.messager.alert('提示', '请选择一个组织机构', 'info');
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
                        $.post('${basePath}servlet/UpOrganizationServlet', {oid:rows[i].oid,pid:rows[i].parentId,name:$('#newName1').val(),address:$('#newAddress1').val() },function (text, status) { alert(text); });
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
<div id="adddlg" class="easyui-dialog" title="添加节点" closed="true" buttons="#dig-buttons"  data-options="iconCls:'icon-add'" style="width:400px;height:200px;padding:10px"
>
    请输入:<br>
    新组织机构名:<input type="text" style="width:80px" name="newName" id="newName">
    所在地:<input type="text" style="width:80px" name="newAddress" id="newAddress">


    <div id="dig-buttons">
        <a href="javascript:addSource()" class="easyui-linkbutton">添加</a>
        <a href="javascript:addSourceC()" class="easyui-linkbutton">取消</a>
    </div>
</div>
<%--修改按钮弹出界面--%>
<div id="updlg" class="easyui-dialog" title="修改重配置组织机构" closed="true" buttons="#bb11" data-options="iconCls:'icon-edit'" style="width:400px;height:150px;padding:10px">

    请输入修改后的信息啊：<br>
    组织机构名:<input type="text" style="width:80px" name="newName1" id="newName1">
    所在地:<input type="text" style="width:80px" name="newAddress1" id="newAddress1">
    <%--<span>重配置上级组织机构:</span> <input class="easyui-combobox"  style="width:120px" name="organization" id="organization">--%>
    <div id="bb11">
        <a href="javascript:upFenpei()"  class="easyui-linkbutton" >确定</a>
        <a href="javascript:upFenpeiC()"  class="easyui-linkbutton" >取消</a>
    </div>
</div>
<%--按钮之外的界面--%>
<div id="searchtool" style="padding:5px">
    <span>组织机构名:</span> <input type="text" style="width:80px" name="lname" id="name">
    <span>所在地:</span> <input type="text" style="width:80px" name="address" id="address">
    </select>

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a id="add" href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true" onclick="obj.add();">添加</a>

        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.up();">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="obj.remove();">删除</a>
    </div>
</div>
<table id="dg" title="组织机构列表" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:15,
				pageList:[10,15,20,30,40,50],
				loadFilter:pagerFilter,
       <%--url:'${basePath}servlet/ShowNodeServlet'">--%>
       url:'${basePath}servlet/SearchOrganizationServlet'">
    <thead>
    <tr>
        <th data-options="field:'oid',width:150,align:'center'">组织机构id</th>
        <th data-options="field:'name',width:250,align:'left'">组织机构名称</th>
        <th data-options="field:'address',width:250,align:'left'">所在地</th>
        <th data-options="field:'parentId',width:150,align:'center'">上级组织机构id</th>
        <th data-options="field:'parentIds',width:160,align:'left'">上级组织机构ids</th>
    </tr>
    </thead>
</table>
<table id="tt">
<thead>
<tr>
    <th data-options="field:'name',width:100,align:'center'">组织机构名称</th>
</tr>
</thead>

</table>


</body>

<script type="text/javascript">

    var jsontree=
    {
        success:true,
        root:[
            {id:"001",name:"aaa",text:"aaa",leaf:false,children:[
                {id:"003",name:"ccc",text:"ccc",leaf:false,children:[
                    {id:"006",name:"fff",text:"fff",leaf:true}
                ]},
                {id:"004",name:"ddd",text:"ddd",leaf:true}
            ]},
            {id:"002",name:"bbb",text:"bbb",leaf:false,children:[
                {id:"005",name:"eee",text:"eee",leaf:true}
            ]}
        ]
    };

    $('#tt').treegrid({
        url:jsontree,
        idField:'id',
        treeField:'name',
        columns:[[
            {title:'name',field:'name',width:180},
            {field:'id',title:'id',width:60,align:'right'},

        ]]
    });
    var jsonobj1 =
    {
        success : true,
        root : [
            {
                id : "001",
                name : "aaa",
                parent : null
            },
            {
                id : "002",
                name : "bbb",
                parent : null
            },
            {
                id : "003",
                name : "ccc",
                parent : "001"
            },
            {
                id : "004",
                name : "ddd",
                parent : "003"
            },
            {
                id : "005",
                name : "eee",
                parent : "004"
            },
            {
                id : "006",
                name : "fff",
                parent : "003"
            }
        ]
    };

    var convert = function (jsonobj)
    {
        var result = {};
        for ( var p in jsonobj)
        {
            if (p != 'root')
            {
                result[p] = jsonobj[p];
            }
        }
        result.root = [];
        var root = jsonobj.root;
        for ( var i = 0; i < root.length; i++)
        {
            var ri = root[i];
            ri.text = ri.name;
            for ( var j = 0; j < root.length; j++)
            {
                root[j].leaf = true;
                for ( var k = 0; k < root.length; k++)
                {
                    if (root[k].parent == root[j].id)
                    {
                        root[j].leaf = false;
                        break;
                    }
                }
            }

            if (ri.parent != null && ri.parent != 'null')
            {
                for ( var j = 0; j < root.length; j++)
                {
                    var rj = root[j];
                    if (rj.id == ri.parent)
                    {
                        rj.children = !rj.children ? [] : rj.children;
                        rj.children.push (ri);
                        break;
                    }
                }
            }

            if (ri.parent == null || ri.parent == 'null')
            {
                result.root.push (ri);
            }
        }

        return result;
    }

    var result = convert (jsonobj1);
    console.log (result);
</script>

<script>
    $('#dg').datagrid({
        onSelect : function (rowIndex, rowData) {
        }
    });
</script>
</html>
