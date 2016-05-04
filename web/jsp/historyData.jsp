<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 3/29/16
  Time: 7:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>adminRegion</title>
</head>
<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../themes/icon.css">
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<%--表格数据加载的js--%>
<%--如果是由百度地图转过来的,会直接查单独一个线杆的数据,如果不是的话就是全部数据;--%>
<script>
    var thisURL = document.URL;
    var  getval =thisURL.split('?')[1];
    var showval= getval.split("=")[1];
    function  showvalf(){
//        alert(showval);
        if(showval!=null || showval!=""){
            $("#pole").combobox("setValue",showval);
            FindData();
        }
    }
</script>

<script>
    function FindData(){
//        alert("组织"+$('#organization').combobox('getValue')+"地区"+$('#region').combobox('getValue')+"线路"+$('#line').combobox('getValue')+"线杆"+$('#pole').combobox('getValue'));
        $('#dg').datagrid('load',{
            oid:$('#organization').combobox('getValue'),
            aid:$('#region').combobox('getValue'),
            lid:$('#line').combobox('getValue'),
            pid:$('#pole').combobox('getValue'),
            startTime:$('#startTime').datetimebox('getValue'),
            endTime:$('#endTime').datetimebox('getValue')}
        );

    }
</script>

<script type="text/javascript">
    function query(){
       var startTime= $('#startTime').datetimebox('getValue');
       var endTime= $('#endTime').datetimebox('getValue');
       var varify=true;//用于查询验证,验证开始时间是否小于结束时间
       if(startTime>endTime){
           varify=false;
       }else{
           varify=true;
       }
       if(varify){
           FindData();
       }else{
           $.messager.alert('警告','结束时间要大于开始时间','warning');
       }
       }
</script>

<%--四级联动的js+前两个选框设置默认值--%>
<script type="text/javascript">
    $(function () {
        sijiliandong();  //获取数据源信息
    });

    function sijiliandong(){
        var oid=${user.oid};
        var url="${basePath}servlet/SearchOrganizationSubServlet";
        //组织默认是总组织;
        $.post(url,{oid:oid},function (json) {
            $('#organization').combobox({
                data:json.rows,
                valueField:'oid',
                textField:'name',
                //组织加载成功后选择第一个为默认选项;
                onLoadSuccess: function () {
                    var data = $('#organization').combobox('getData');
                    if (data.length > 0) {
                        $('#organization').combobox('select', data[0].name);
                        $('#organization').combobox('setValue', data[0].oid);
                        var oid=data[0].oid;
                        var url0 = "${basePath}servlet/SearchAdminRegionServlet";
                        $.post(url0, {oid:oid},function(json) {
                                    $('#region').combobox({
                                        data : json.rows,
                                        valueField:'aid',
                                        textField:'name',
                                        //地区加载成功后选择第一个为默认选项;
                                        onLoadSuccess: function () {
                                            var data = $('#region').combobox('getData');
                                            if (data.length > 0) {
                                                $('#region').combobox('select', data[0].name);
                                                $('#region').combobox('setValue', data[0].aid);
                                                var aid=data[0].aid;
                                                var url1 = "${basePath}servlet/SearchLineServlet";
                                                $.post(url1, {oid:oid,aid:data[0].aid},function(json) {
                                                    $('#line').combobox({
                                                        data: json.rows,
                                                        valueField: 'lid',
                                                        textField: 'name',
                                                        //线路选框-选择执行函数-请求位于该地区所有的线杆;
                                                        onSelect: function(record){
                                                            var url2='${basePath}servlet/SearchPoleServlet';
                                                            var lid=record.lid;
//                            alert("查pole所要的oid.aid.lid:"+oid+".."+aid+".."+lid);
                                                            $.post(url2, {oid:oid,aid:aid,lid:lid},function(json) {
                                                                $('#pole').combobox({
                                                                    data: json.rows,
                                                                    valueField: 'pid',
                                                                    textField: 'location'
                                                                })
                                                            },"json");
                                                        }
                                                    });
                                                },"json");
                                            }
                                        },
                                        //地区选框-选择执行函数-请求位于该地区所有的线路;
                                        onSelect: function(rec){
                                            var url1 = "${basePath}servlet/SearchLineServlet";
                                            var aid=rec.aid;
                                            $.post(url1, {oid:oid,aid:aid},function(json) {
                                                $('#line').combobox({
                                                    data: json.rows,
                                                    valueField: 'lid',
                                                    textField: 'name',
                                                    onSelect: function(record){
                                                        var url2='${basePath}servlet/SearchPoleServlet';
                                                        var lid=record.lid;
//                            alert("查pole所要的oid.aid.lid:"+oid+".."+aid+".."+lid);
                                                        $.post(url2, {oid:oid,aid:aid,lid:lid},function(json) {
                                                            $('#pole').combobox({
                                                                data: json.rows,
                                                                valueField: 'pid',
                                                                textField: 'location'
                                                            })
                                                        },"json");
                                                    }
                                                });
                                            },"json");
                                        }
                                    });
                        },"json");
                    }
                },
                //组织选框-选择执行函数-请求属于该组织线杆所在的所有地区;
                onSelect:function(rec){
                    var oid=rec.oid;
                    var url0 = "${basePath}servlet/SearchAdminRegionServlet";
                    $.post(url0, {oid:oid},function(json) {
                        $('#region').combobox({
                            data : json.rows,
                            valueField:'aid',
                            textField:'name',
                            onSelect: function(rec){
                                var url1 = "${basePath}servlet/SearchLineServlet";
                                var aid=rec.aid;
                                $.post(url1, {oid:oid,aid:aid},function(json) {
                                    $('#line').combobox({
                                        data: json.rows,
                                        valueField: 'lid',
                                        textField: 'name',
                                        onSelect: function(record){
                                            var url2='${basePath}servlet/SearchPoleServlet';
                                            var lid=record.lid;
//                            alert("查pole所要的oid.aid.lid:"+oid+".."+aid+".."+lid);
                                            $.post(url2, {oid:oid,aid:aid,lid:lid},function(json) {
                                                $('#pole').combobox({
                                                    data: json.rows,
                                                    valueField: 'pid',
                                                    textField: 'location'
                                                })
                                            },"json");
                                        }
                                    });
                                },"json");
                            }
                        });
                    },"json");
                }
            })
        },"json");
    }
</script>

<%--数据转图形时数据项选择的js--%>
<script type="text/javascript">
    $(function(){
        $('#cc').combo({
            required:false,
            editable:true
        });
        $('#sp').appendTo($('#cc').combo('panel'));
        $('#sp input').click(function(){
            var v = $(this).val();
            var s = $(this).next('span').text();
            $('#cc').combo('setValue', v).combo('setText', s).combo('hidePanel');
        });
    });
</script>


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

<%--转折线图创建标签页的js--%>
<script>
    function loadpage(text, url) {
        var option=$("#cc").combobox('getText');
        alert(option);
        if($('#line').combobox('getValue')==null || $('#line').combobox('getValue')==""){
            alert("请选择一条线路或者一根线杆!");
        }else if(option =="" ||option ==null ||option =="线表温度" ||option =="弧度" ||option =="室外温度" ||option =="电流" ||option =="电压" ||option =="湿度"){
        var url="../jsp/chart.jsp";
        text="折线图显示数据";
        alert(url+text);
        var jq = top.jQuery;
        if (jq("#tabs").tabs('exists', text)) {
            jq("#tabs").tabs('select', text);
        } else {
            jq("#tabs").tabs('add', {
                title: text,
                content: '<iframe  src="' + url + '" frameBorder="0" border="0"  style="width: 100%; height: 100%;" noResize/>',
                closable: true
                //href: url
            });
        }
        }else{
            alert("请在数据项选框里选择正确的选项!");
        }
    }
</script>

<%--设置时间默认值--%>
<script>
    $(function(){
        //设置时间
        $("#startTime").datebox("setValue",'3/1/2016 00:00:00');
        $("#endTime").datebox("setValue",'6/1/2016 00:00:00');
    });
</script>

<script>
    function exportExcel(){
        var oid=$('#organization').combobox('getValue');
        var aid=$('#region').combobox('getValue');
        var lid=$('#line').combobox('getValue');
        var pid=$('#pole').combobox('getValue');
        var startTime=$('#startTime').datetimebox('getValue');
        var endTime=$('#endTime').datetimebox('getValue');
        var url = "${basePath}servlet/ExportDataToExcel";
           url += "?oid="+oid
           +"&aid="+aid
           +"&lid="+lid
           +"&pid="+pid
           +"&startTime="+startTime
           +"&endTime="+endTime;
        window.open(url);
    }
</script>


<body onload="showvalf()">
<div id="searchtool" style="padding:5px">
    <span>组织:</span> <input class="easyui-combobox" style="width:80px" name="organization" id="organization">
    <span>地区:</span> <input class="easyui-combobox" style="width:80px" name="region" id="region">
    <span>线路:</span> <input class="easyui-combobox" style="width:80px" name="line" id="line">
    <span>线杆:</span> <input id="pole" name="pole" style="width:80px;" class="easyui-combobox">
    <span>Date From:</span> <input class="easyui-datetimebox" style="width:150px" id="startTime" name="startTime" value="${startTime}">
    <span>To:</span> <input class="easyui-datetimebox" style="width:150px"  id="endTime" name="endTime" value="${endTime}" validType="md['#startTime']">

    <a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
       <%-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>--%>
        <span>数据项:</span>
        <select id="cc" style="width:150px"></select>
        <div id="sp">
            <div style="color:#99BBE8;background:#fafafa;padding:5px;">选择数据项</div>
            <input type="radio" name="lang" value="01"><span>线表温度</span><br/>
            <input type="radio" name="lang" value="02"><span>弧度</span><br/>
            <input type="radio" name="lang" value="03"><span>室外温度</span><br/>
            <input type="radio" name="lang" value="04"><span>电流</span><br/>
            <input type="radio" name="lang" value="05"><span>电压</span><br/>
            <input type="radio" name="lang" value="06"><span>湿度</span>
        </div>
        <input type="button" value="折线图显示数据" class="cs-navi-tab" onclick="loadpage()">
        <input type="button" value="导出Excel表格" class="cs-navi-tab" onclick="exportExcel()">
    </div>
    <div>
    </div>
</div>
<table id="dg" title="Client Side Pagination" style="width:1000px;height:400px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10,
				loadFilter:pagerFilter,
       url:'${basePath}servlet/SearchDataServlet'">
    <thead>
    <tr>
        <th data-options="field:'pid',width:40,align:'center'">线杆编号</th>
        <%--<th data-options="field:'did',width:40,align:'center'">数据序号</th>--%>
        <th data-options="field:'samplingTime',width:140,align:'center'">收集时间</th>
        <th data-options="field:'outTemperature',width:100,align:'center'">室外温度</th>
        <th data-options="field:'wireTemperature',width:100,align:'center'">线表温度</th>
        <th data-options="field:'sag',width:100,align:'center'">弧垂</th>
        <th data-options="field:'electricity',width:120,align:'center'">电流</th>
        <th data-options="field:'voltage',width:100,align:'center'">电压</th>
        <th data-options="field:'humidity',width:100,align:'center'">湿度</th>
        <th data-options="field:'nid',width:40,align:'center'">节点序号</th>
        <th data-options="field:'location',width:40,align:'center'">线杆位置</th>
        <th data-options="field:'name',width:50,align:'center'">线路名称</th>
        <th data-options="field:'source',width:40,align:'center'">节点组属地址</th>
    </tr>
    </thead>
</table>

</body>
</html>
