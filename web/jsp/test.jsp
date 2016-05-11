<%--
  Created by IntelliJ IDEA.
  User: huojingjing
  Date: 16/5/11
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取地区轮廓线</title>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hK24m3To7xn89fF0mwzGCqRPKe3wksh4"></script>
    <style type="text/css">
        body {
            font-size: 13px;
            margin: 10px
        }
        #container {
            width: 800px;
            height: 500px;
            border: 1px solid gray
        }
    </style>
</head>
<body>
<div id="container"></div>
<div id="event_descr"></div>
<br />输入省、直辖市或县名称：
<input type="text" id="districtName" style="width:80px" value="桂林市">
<input type="button" id="button" value="获取轮廓线">



<script type="text/javascript">
    var map = new BMap.Map("container");
    map.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
    map.addControl(new BMap.NavigationControl({
        type: BMAP_NAVIGATION_CONTROL_SMALL
    }));
    map.enableScrollWheelZoom();

    //    var fso = new ActiveXObject("Scripting.FileSystemObject");
    //
    //    var folderName = "/Users/huojingjing/Downloads/";
    //    if (!fso.FolderExists(folderName))   fso.CreateFolder(folderName);


    $.ajax({
        type: "get",
        url: '../js/cityCenter.json',
        dataType: "json",
        success: function (data) {
            if (data.municipalities && data.municipalities.length > 0) {
                for (var j = 0, n = data.municipalities.length; j < n; j++) {
                    var mu = data.municipalities[j];
                    console.info(mu);
//                    map.setCenter(mu.g);
                    var str=mu.g;
                    str=str.substring(0,str.length-3);
                    console.info("去掉尾部的字符串:"+str);
                    var lat="";
                    var lng="";
                    var i=0;
                    for(i=0;i<str.length;i++){
                        if(str.charAt(i)==','){
                            break;
                        }else {
                            lng=lng+str.charAt(i);
                        }
                    }
                    for(i=i+1;i<str.length;i++){
                        lat=lat+str.charAt(i);
                    }
                    $.ajax({
                        type: "post",
                        data:{lat:lat,lng:lng},
                        url: "${basePath}servlet/BaiDuMapProvinceServlet",
                        dataType: "json",
                        success: function (data) {
                        },
                        error: function () {
                            alert("11111直辖市:请求超时，请重试！");
                        }
                    });
                }
            }
// 普通省
            for (var l = 0, n = data.provinces.length; l < n; l++) {
                var pv = data.provinces[l];
                getBoundary(pv.n, "");
                //插入普通省
                var str=pv.g;
                var lat0="";
                var lng0="";
                var i=0;
                str=str.substring(0,str.length-3);
                console.info("字符串:"+str);
                for(i=0;i<str.length;i++){
                    if(str.charAt(i)==','){
                        break;
                    }else {
                        lng0=lng+str.charAt(i);
                    }
                }
                for(i=i+1;i<str.length;i++){
                    lat0=lat+str.charAt(i);
                }


                $.ajax({
                    type: "post",
                    data:{lat:lat0,lng:lng0},
                    url: "${basePath}servlet/BaiDuMapProvinceServlet",
                    dataType: "json",
                    success: function (data) {
                    },
                    error: function () {
                        alert("22222:普通省:请求超时，请重试！");
                    }
                });
                for (var k = 0, m = pv.cities.length; k < m; k++) {
                    console.info(pv.cities[k].n);
                    //插入每个普通省的市
                    var str=pv.cities[k].g;
                    var lat="";
                    var lng="";
                    var i=0;
                    str=str.substring(0,str.length-3);
                    console.info("字符串:"+str);
                    for(i=0;i<str.length;i++){
                        if(str.charAt(i)==','){
                            break;
                        }else {
                            lng=lng+str.charAt(i);
                        }
                    }
                    for(i=i+1;i<str.length;i++){
                        lat=lat+str.charAt(i);
                    }
                    $.ajax({
                        type: "post",
                        data:{lat:lat,lng:lng,lat0:lat0,lng0:lng0},
                        url: "${basePath}servlet/BaiDuMapCityServlet",
                        dataType: "json",
                        success: function (data) {
                        },
                        error: function () {
                            alert("33333:每个普通省的市:请求超时，请重试！");
                        }
                    });


                    getBoundary(pv.cities[j].n, pv.n);
                }

            }


            // 其他地区
            if (data.other && data.other.length > 0) {
                for (var p = 0, n = data.other.length; p < n; p++) {
                    var oth = data.other[p];
                    map.setCenter(oth.g);
                    console.info(map.getCenter());
                    var str=oth.g;
                    var lat="";
                    var lng="";
                    var i=0;
                    str=str.substring(0,str.length-3);
                    console.info("字符串:"+str);
                    for(i=0;i<str.length;i++){
                        if(str.charAt(i)==','){
                            break;
                        }else {
                            lng=lng+str.charAt(i);
                        }
                    }
                    for(i=i+1;i<str.length;i++){
                        lat=lat+str.charAt(i);
                    }

                    $.ajax({
                        type: "post",
                        data:{lat:lat,lng:lng},
                        url: "${basePath}servlet/BaiDuMapProvinceServlet",
                        dataType: "json",
                        success: function (data) {
                        },
                        error: function () {
                            alert("4444:其他地区:请求超时，请重试！");
                        }
                    });

                    getBoundary(oth.n, "");
                }

            }

        },
        error: function () {
            alert("请求超时，请重试！");
        }
    });


    function getBoundary(name, pvName) {
        var bdary = new BMap.Boundary();

        bdary.get(name, function (rs) { //获取行政区域
            map.clearOverlays(); //清除地图覆盖物
            var count = rs.boundaries.length; //行政区域的点有多少个
            for (var i = 0; i < count; i++) {
                var ply = new BMap.Polygon(rs.boundaries[i], {
                    strokeWeight: 2,
                    strokeColor: "#ff0000"
                }); //建立多边形覆盖物
                map.addOverlay(ply); //添加覆盖物
                map.setViewport(ply.getPath()); //调整视野

            }
        });
    }


    //            var fileName = "";
    //            var newFileObject = null;
    //            if (pvName == "") newFileObject = fso.CreateTextFile(folderName + name + ".txt", true)
    //            else newFileObject = fso.CreateTextFile(folderName + pvName + "\\" + name + ".txt", true);
    //
    //            newFileObject.write(rs.boundaries[0]);
    //            newFileObject.Close();
    //            eventsTable.innerHTML = rs.boundaries[0].length +':'+ rs.boundaries[0];
    //        });
    //    }



    //    map.addEventListener("click", function(e){
    //        var pt = e.point;
    //        geoc.getLocation(pt, function(rs){
    //            var addComp = rs.addressComponents;
    //            alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
    //        });
    //    });
</script>

</body>
</html>
