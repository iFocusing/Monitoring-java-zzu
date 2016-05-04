<%--
  Created by IntelliJ IDEA.
  User: huojingjing
  Date: 16/4/17
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>折线图显示数据</title>
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script src="../js/highchart/highcharts.js"></script>
    <script src="../js/highchart/modules/exporting.js"></script>
</head>
<script type="text/javascript">
    $(function(){
        var tab = parent.$('#tabs').tabs('getSelected');
        var index = parent.$('#tabs').tabs('getTabIndex',tab);
//        alert(index);//这个获得的是当前的iframe索引,是2;
    });

    $(function () {
        GetseriesValue();  //获取数据源信息
    });

    //获取数据源信息
    //////////////////////////////////////////////////////////////////////
    function GetseriesValue() {
        var tab1 = parent.$('#tabs').tabs('getTab',"查看历史数据");//通过选项板title获取特定的选项板;
        var oid=tab1.panel("body").find("iframe")[0].contentWindow.$("#organization").combobox('getValue');
        var aid=tab1.panel("body").find("iframe")[0].contentWindow.$("#region").combobox('getValue');
        var lid=tab1.panel("body").find("iframe")[0].contentWindow.$("#line").combobox('getValue');
        var lname=tab1.panel("body").find("iframe")[0].contentWindow.$("#line").combobox('getText');
        var pid=tab1.panel("body").find("iframe")[0].contentWindow.$("#pole").combobox('getValue');
        var location =tab1.panel("body").find("iframe")[0].contentWindow.$("#pole").combobox('getText');
        var startTime=tab1.panel("body").find("iframe")[0].contentWindow.$('#startTime').datetimebox('getValue');
        var endTime=tab1.panel("body").find("iframe")[0].contentWindow.$('#endTime').datetimebox('getValue');
//        alert(pid+startTime+endTime);
        var option=tab1.panel("body").find("iframe")[0].contentWindow.$("#cc").combobox('getText');
        alert(option);
        $.ajax({
            type: "post",
            url: "${basePath}servlet/SearchChartDataServlet",
            data: { oid:oid,aid: aid,lid:lid,pid:pid, startTime:startTime, endTime:endTime,option:option},
            dataType: "json",
            cache: false,
            success: function (data) {
                var text="";
                var title="";
                if(pid!=null && pid!=""){
                    text=startTime+"到"+endTime+"的数据显示(线杆编号:"+pid+")";
                    title="该线杆位于"+lname+"上,偏移位置为"+location;
                }else{
                    text=lname;
                    title=lname+"上的所有线杆连成线的最新一条数据";
                }
                GetData(data,text,title);
            },
            error: function () {
                alert("请求超时，请重试！");
            }
        });



    };


    //绑定获取数据信息操作 //绑定数据信息
    //////////////////////////////////////////////////////////////////////
    function GetData(dataTmp,text,title) {
            $('#container').highcharts({
                chart: {
                    backgroundColor: {
                        linearGradient: {x1: 0, y1: 0, x2: 1, y2: 1},
                        stops: [
                            [0, 'rgb(255, 255, 255)'],
                            [1, 'rgb(240, 240, 255)']
                        ]
                    },
                    borderWidth: 2,
                    plotBackgroundColor: 'rgba(255, 255, 255, .9)',
                    plotShadow: true,
                    plotBorderWidth: 1
                },
                title: {
                    text: text,
                    x: -20
                },
                subtitle: {
                    text: title,
                    x: -20
                },
                lang: {
                    printChart: '打印图表',
                    downloadPNG: '下载JPEG 图片',
                    downloadJPEG: '下载JPEG文档',
                    downloadPDF: '下载PDF 文件',
                    downloadSVG: '下载SVG 矢量图',
                    contextButtonTitle: '下载图片'
                },
                /*这个是x轴,所有的表格x轴都是时间,用categories定制类别轴;*/
                xAxis: {
                    gridLineWidth: 1,
                    lineColor: '#000',
                    tickColor: '#000',
                    categories:dataTmp.timelist
                },
                /*这个是y轴,每个表格不一样,有线表温度,室外温度,电压,电流,弧垂,湿度*/
//                yAxis: {
//                    minorTickInterval: 'auto',
//                    lineColor: '#000',
//                    lineWidth: 1,
//                    tickWidth: 1,
//                    tickColor: '#000',
//                    min: 0,
//                    labels: {
//                        formatter: function () {  //设置纵坐标值的样式
//                            return this.value + '/°C';
//                        }
//                    },
//                    title: {
//                        text: 'Temperature (°C)'
//                    },
//                    plotLines: [{
//                        value: 0,
//                        width: 1,
//                        color: '#808080'
//                    }]
//                },
                /*数据点提示框:这个是鼠标放上折线的点,自己不定义的话有默认的;*/
                tooltip: {
                    backgroundColor: {
                        linearGradient: [0, 0, 0, 60],
                        stops: [
                            [0, '#FFFFFF'],
                            [1, '#E0E0E0']
                        ]
                    },
                    borderWidth: 1,
                    borderColor: '#AAA'
                },
//                tooltip: {
//                    formatter: function () {
//                        return '<b>' + this.series.name + '</b><br/>' + this.x + ': ' + this.y + yAxisLabels;
//                    }
//                },
//                legend: {
//                    itemStyle: {
//                        font: '9pt Trebuchet MS, Verdana, sans-serif',
//                        color: 'black'
//                    },
//                    itemHoverStyle: {
//                        color: '#039'
//                    },
//                    itemHiddenStyle: {
//                        color: 'gray'
//                    },
//                    layout: 'vertical',
//                    align: 'right',
//                    verticalAlign: 'middle',
//                    borderWidth: 0
//                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true
                        },
                        enableMouseTracking: false
                    }
                },
                /*这个是表格中的所有数据的纵坐标*/
            series: dataTmp.rows
            });

    }
</script>
<body>
<div id="mainPanle" region="center" border="true" border="false">
    <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
        <div title="折线图显示数据">
            <div class="cs-home-remark">
                <div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
