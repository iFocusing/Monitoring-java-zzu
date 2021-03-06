<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看实时数据</title>
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script src="../js/highchart/highcharts.js"></script>
    <script src="../js/highchart/modules/exporting.js"></script>
</head>

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



<script type="text/javascript">
    //获取数据源信息
    //////////////////////////////////////////////////////////////////////
    function GetseriesValue() {
        var pid=$('#pole').combobox('getValue');
//        alert(pid);
        $.ajax({
            type: "post",
            url: "${basePath}servlet/SearchChartPreviousCurrentDataServlet",
            data: {pid:pid},
            dataType: "json",
            cache: false,
            success: function (result) {
                if(result != null && result != "null"){
//                    alert(result);
                    GetData(result,pid);
                }
//
            },
            error: function () {
                alert("请求超时，请重试！");
            }
        });
    };


    //绑定获取数据信息操作 //绑定数据信息
    //////////////////////////////////////////////////////////////////////
    function GetData(dataTmp,pid) {
        var intervalTime = -1;
        $(function () {
            $(document).ready(function() {
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                // 绑定该标签的关闭事件，将动态更新停止/////成功了,但是有问题存在\并且换个线杆后上一个还在自动刷新;哈哈哈哈哈;;
                var tab = parent.$('#tabs').tabs('getSelected');
                var index = parent.$('#tabs').tabs('getTabIndex',tab);
                parent.$('#tabs').tabs('getSelected').tabs({
                    onBeforeClose: function(title,index){
                        var target = this;
                        $.messager.confirm('确认','你确认想要关闭'+title,function(r){
                            if (r){
                                if(intervalTime!=-1){
                                    clearInterval(intervalTime);
                                }
                            }
                        });
                        return false;  // 阻止关闭
                    }
                });
//            var closeTime = setInterval(function() {
//                // 如果当前页面不是该标签，则停止更新
//                if(!$("a.currentLiNav").parent().hasClass("selected")){
//                    alertMsg.info("已停止更新数据！");
//                    clearInterval(intervalTime);
//                    clearInterval(closeTime);
//                }
//            }, 5);//5毫秒检查一次
                var chart;
                $('#currentChartContainer').highcharts({
                    chart: {
                        type: 'spline',
                        animation: Highcharts.svg, // don't animate in old IE
                        marginRight: 10,
                        events: {
                            load: function() {
                                var timesRun = 0;
                                var tags =["0","1","2","3","4","5"];

                                var all = new Array(tags.length);
                                for(var i=0;i<tags.length;i++){
                                    all[i] = this.series[i];
//                                    console.info("下标:"+i+this.series[i].name);
//                                    console.info("下标:"+i+all[i]);
                                }

                                intervalTime = setInterval(function() {
                                    timesRun += 1;
                                    // 最多更新60次
                                    if(timesRun == 60){
                                        alertMsg.warn("最多更新60次！");
                                        clearInterval(intervalTime);
                                    }

                                    $.ajax({
                                        type: "post",
                                        url: "${basePath}servlet/SearchChartCurrentDataServlet",
                                        data: {pid:pid},
                                        dataType: "json",
                                        cache: false,
                                        success: function (result) {
                                            if(result.total != 0 ){
                                               $.each(result.rows,function (index,obj) {
                                                   var newPoint={
                                                       x:Number(result.rows[index].time),
                                                       y:Number(result.rows[index].value)
                                                   }
                                                    getMoreDate(newPoint,all[index]);
                                               })
                                            }else{
//                                                alert("近期5s没有数据");
                                            }
                                        },
                                        error: function () {
                                            alert("请求超时，请重试！");
                                        }
                                    });
                                }, 5000);// 5s更新一次
                            }
                        }
                    },
                    title: {
                        text: '实时数据'
                    },
                    xAxis: {
                        type: 'datetime',
                        tickPixelInterval: 150
                    },
                    yAxis: {
                        title: {
                            text: 'Value'
                        },
                        plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b>'+ this.series.name +'</b><br/>'+
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                                    Highcharts.numberFormat(this.y, 4);
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        borderWidth: 0
                    },
                    exporting: {
                        enabled: false
                    },
                    series:
                     dataTmp.rows
                });
            });
        });
    }
    function getMoreDate(newPoint,series) {
        series.addPoint(newPoint,true,true,true);
    }
</script>


<body>
        <div title="查看实时数据">
            <div class="cs-home-remark">
                <div id="searchtool" style="padding:5px">
                <span>组织:</span> <input class="easyui-combobox" style="width:80px" name="organization" id="organization">
                <span>地区:</span> <input class="easyui-combobox" style="width:80px" name="region" id="region">
                <span>线路:</span> <input class="easyui-combobox" style="width:80px" name="line" id="line">
                <span>线杆:</span> <input id="pole" name="pole" style="width:80px;" class="easyui-combobox">

                <a href="javascript:GetseriesValue()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                </div>
            </div>

                <div id="currentChartContainer" style="min-width:700px;height:350px"></div>
        </div>
</body>
</html>
