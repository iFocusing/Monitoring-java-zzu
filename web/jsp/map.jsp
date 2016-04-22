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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hK24m3To7xn89fF0mwzGCqRPKe3wksh4"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
</head>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<body>
<script type="text/javascript">
    $(function(){
//        alert("加载map线杆");
        $.ajax({
            type: "post",
            url: "${basePath}servlet/SearchMapDataServlet",
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
<div id="container"></div>

<script type="text/javascript">
    var map = new BMap.Map("container", {enableMapClick:false});//构造底图时，关闭底图可点功能
    function GetData(datalines,data){
//        var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
//        map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
        map.centerAndZoom("河南省",12);
        map.enableScrollWheelZoom();//开启鼠标缩放功能

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
        //实现往地图里面添加线杆标志;
        var point1=[];
        $.each(data, function(idx, obj) {
            var pointss = [];
            $.each(obj,function (idx2, obj2) {
                var point =new BMap.Point(obj2.longitude,obj2.latitude);
                point1[idx]=point;
                pointss.push(point);
                var marker = new BMap.Marker(point);
                map.addOverlay(marker);    //增加点
                marker.addEventListener("click",function attribute(e){
//                var p = e.target;
//                alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);
                    this.openInfoWindow(new BMap.InfoWindow('我是第'+ (obj2.location) +'个标注'));
                });
            });
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
