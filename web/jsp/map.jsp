<%--
  Created by IntelliJ IDEA.
  User: huojingjing
  Date: 16/4/21
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>百度地图数据展示</title>
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #container{height:100%}
        #r-result{width:100%;margin-top:5px;}
        p{margin:5px; font-size:14px;}
    </style>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hK24m3To7xn89fF0mwzGCqRPKe3wksh4"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
    <script type="text/javascript" src="http://developer.baidu.com/map/custom/stylelist.js"></script>
</head>
<body>
<script type="text/javascript">
    $(function(){
//        alert("加载map线杆");
        $.ajax({
            type: "post",
            url: "${basePath}servlet/SearchMapLineServlet",
            data: { oid:${oid} },
            dataType: "json",
            cache: false,
            success: function (data) {
//                alert("加载map线杆数据");
                GetData(data.lines,data.rows);
            },
            error: function () {
                alert("请求超时，请重试！");
            }
        });
    });
</script>
<div id="r-result">
    <input type="button" onclick="add_control1();" value="添加地图类型控件" />
    <input type="button" onclick="delete_control1();" value="删除地图类型控件" />
</div>
<div id="r-result1">
    <div class="optionpanel">
        <label>选择主题</label>
        <select id="stylelist" onchange="changeMapStyle(this.value)"></select>
    </div>
</div>
<div id="container"></div>
<script>
    function changeMapStyle(style){
        map.setMapStyle({style:style});
        $('#desc').html(mapstyles[style].desc);
    }
</script>

<script>
    function moreData(data){
        var url="../jsp/historyData.jsp?pid=";
        url=url+data;
        text="查看历史数据";
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
//
    }
</script>
<script type="text/javascript">



    var map = new BMap.Map("container", {enableMapClick:false});//构造底图时，关闭底图可点功能
    function GetData(datalines,data){
//        var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
//        map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
        map.centerAndZoom("河南省",12);
        map.enableScrollWheelZoom();//开启鼠标缩放功能
        //百度地图API功能:初始化模板选择的下拉框
        var sel = document.getElementById('stylelist');
        for(var key in mapstyles){
            var style = mapstyles[key];
            var item = new  Option(style.title,key);
            sel.options.add(item);
        }
        window.map = map;

        changeMapStyle('midnight')
        sel.value = 'midnight';
        //百度地图API功能:单击获取点击的经纬度
//        map.addEventListener("click",function(e){
//            alert(e.point.lng + "," + e.point.lat);
//        });
// 百度地图API功能:画3点的可编辑弧线
//        var zhengzhouPosition=new BMap.Point(113.584781,34.746844),
//            luoyangPosition=new BMap.Point(112.475194,34.630968),
//            anhuiPosition=new BMap.Point(116.888821,31.505883);
//        var points = [zhengzhouPosition,luoyangPosition, anhuiPosition];
//        var curve = new BMapLib.CurveLine(points, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //创建弧线对象
//
//        map.addOverlay(curve); //添加到地图中
//        curve.enableEditing(); //开启编辑功能

        if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
        //实现往地图里面添加线杆标志;
        var point1=[];//这是为了显示线路;
        //遍历线路,obj是指其中一条线路
        $.each(data, function(idx, obj) {
            var pointss = [];
            //遍历一条线路上的线杆, obj2是指其中一根线杆
            $.each(obj,function (idx2, obj2) {
                //取该线杆的最新3条数据:
//                alert(obj2.dataDisplay[0].samplingTime);
                var point =new BMap.Point(obj2.longitude,obj2.latitude);
                point1[idx]=point;
                pointss.push(point);
            });
            var options = {
                size: '30*30px',
                shape: BMAP_POINT_SHAPE_STAR,
                color: '#d340c3'
            }
            var pointCollection = new BMap.PointCollection(pointss, options);  // 初始化PointCollection
            pointCollection.addEventListener('mouseover', function (e) {// 监听点击事件
                var longitude=e.point.lng;
                var latitude=e.point.lat;
                var point = new BMap.Point(longitude, latitude);
                $.ajax({
                    type: "post",
                    url: "${basePath}servlet/SearchMapDataServlet",
                    data: {longitude:longitude,latitude:latitude},
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        GetMapData(data.rows);
                    },
                    error: function () {
                        alert("请求超时，请重试！");
                    }
                });
                function GetMapData(data) {
                    var content=
                            "<div>" +
                            "线杆"+data[0].pid+"的最新数据:"+
                            "<a href='javascript:void(0);'"+"onclick='moreData("+ data[0].pid+
                            ")'>"+
                            "更多数据</a>"+
                            "</div>"+
                            "<div>"+
                            "<table style='font-size: 15px;' border='1' align='center'> "+
                            " <tr> "+
                            " <th>线杆编号</th> "+
                            " <th>收集时间</th> "+
                            " <th>室外温度</th> "+
                            " <th>线表温度</th> "+
                            " <th>弧垂</th> "+
                            " <th>电流</th> "+
                            " <th>湿度</th> "+
                            " <th>节点序号</th> "+
                            " <th>线杆位置</th> "+
                            " <th>线路名称</th> "+
                            " <th>节点组属地址</th> "+
                            " </tr> "
                    var titles="";
                    $.each(data, function(idx, obj) {
                        content=content+"<tr>" +
                                "<td>"+obj.pid+"</td>" +
                                "<td>"+obj.samplingTime+"</td>" +
                                "<td>"+obj.outTemperature+"</td>" +
                                "<td>"+obj.wireTemperature+"</td>" +
                                "<td>"+obj.sag+"</td>" +
                                "<td>"+obj.electricity+"</td>" +
                                "<td>"+obj.voltage+"</td>" +
                                "<td>"+obj.nid+"</td>" +
                                "<td>"+obj.location+"</td>" +
                                "<td>"+obj.name+"</td>" +
                                "<td>"+obj.source+"</td>" +
                                "</tr>";
                    });
                    content=content+" </table></div> ";



                    var opts = {
                        width : 1000,     // 信息窗口宽度
                    };
                    var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
                    map.openInfoWindow(infoWindow,point); //开启信息窗口
                }


            });
            map.addOverlay(pointCollection);  // 添加Overlay
            var polyline = new BMap.Polyline(pointss, {strokeColor:"blue", strokeWeight:5, strokeOpacity:0.5});   //创建折线
            map.addOverlay(polyline);   //增加折线
        });




//      给每条线路添加标签;
        $.each(point1,function (idxfirst,objfirst) {
            var sContent ="该线路名称为:"+datalines[idxfirst].name;
            var opts = {
                position : objfirst,    // 指定文本标注所在的地理位置
                offset   : new BMap.Size(30, -30)    //设置文本偏移量
            }
            var label = new BMap.Label(sContent, opts);  // 创建文本标注对象
            map.addOverlay(label);
        })
        } else {
            alert('请在chrome、safari、IE8+以上浏览器查看本示例');
        }

    }



    //        map.enableInertialDragging();
    //        map.enableContinuousZoom();
    //      添加城市列表控件;
    var size = new BMap.Size(10, 20);
    map.addControl(new BMap.CityListControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        offset: size,
        // 切换城市之间事件
        // onChangeBefore: function(){
        //    alert('before');
        // },
        // 切换城市之后事件
        // onChangeAfter:function(){
        //   alert('after');
        // }
    }));

    //点击地图类型控件切换普通地图、卫星图、三维图、混合图（卫星图+路网），右下角是缩略图，点击按钮查看效果
    var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
    var mapType2 = new BMap.MapTypeControl({anchor: BMAP_ANCHOR_TOP_LEFT});

    var overView = new BMap.OverviewMapControl();
    var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
    //添加地图类型和缩略图
    function add_control1(){
        map.addControl(mapType1);          //2D图，卫星图
        map.addControl(mapType2);          //左上角，默认地图控件
        map.setCurrentCity("北京");        //由于有3D图，需要设置城市哦
        map.addControl(overView);          //添加默认缩略地图控件
        map.addControl(overViewOpen);      //右下角，打开
    }
    //移除地图类型和缩略图
    function delete_control1(){
        map.removeControl(mapType1);   //移除2D图，卫星图
        map.removeControl(mapType2);
        map.removeControl(overView);
        map.removeControl(overViewOpen);
    }
</script>


</body>
</html>
