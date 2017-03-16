
<!DOCTYPE>

<html>

<head>

    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

    <meta http-equiv="Content-Type" content="text/html; charset=gbk" />

    <title>百度地图</title>

    <style type="text/css">

        html{height:100%}

        body{height:100%;margin:0px;padding:0px}

        #container{height:100%}

    </style>

    <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>

</head>

<body>





<#--<input type="text" id="cityName" value="福州"/>-->

<#--<input type="button" onclick="setCity()" value="查找" />-->

<div id="container" style="width:1024px;height:600px;"></div>





<script type="text/javascript">

    var map = new BMap.Map("container");        //在container容器中创建一个地图,参数container为div的id属性;



    var point = new BMap.Point(116.404, 39.915);    //创建点坐标

    map.centerAndZoom(point, 15);                //初始化地图，设置中心点坐标和地图级别

//    var myP1 = new BMap.Point(116.380967,39.913285);    //起点
//    var myP2 = new BMap.Point(116.424374,39.914668);    //终点

    //创建起点小狐狸
    var pt1 = new BMap.Point(116.380967,39.913285);
    var myIcon1 = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
    var marker2 = new BMap.Marker(pt1,{icon:myIcon1});  // 创建标注
    map.addOverlay(marker2);              // 将标注添加到地图中

    //创建终点小狐狸
    var pt2 = new BMap.Point(116.424374,39.914668);
    var myIcon2 = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
    var marker3 = new BMap.Marker(pt2,{icon:myIcon2});  // 创建标注
    map.addOverlay(marker3);              // 将标注添加到地图中

    map.enableScrollWheelZoom();                //激活滚轮调整大小功能

    map.addControl(new BMap.NavigationControl());    //添加控件：缩放地图的控件，默认在左上角；

    map.addControl(new BMap.MapTypeControl());        //添加控件：地图类型控件，默认在右上方；

    map.addControl(new BMap.ScaleControl());        //添加控件：地图显示比例的控件，默认在左下方；

    map.addControl(new BMap.OverviewMapControl());  //添加控件：地图的缩略图的控件，默认在右下方； TrafficControl



//    var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/Mario.png", new BMap.Size(32, 70), {    //小车图片
//        //offset: new BMap.Size(0, -5),    //相当于CSS精灵
//        imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
//    });
//    var driving2 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});    //驾车实例
//    driving2.search(myP1, myP2);    //显示一条公交线路

//    window.run = function (){
//        var driving = new BMap.DrivingRoute(map);    //驾车实例
//        driving.search(myP1, myP2);
//        driving.setSearchCompleteCallback(function(){
//            var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
//            var paths = pts.length;    //获得有几个点
//
//            var carMk = new BMap.Marker(pts[0],{icon:myIcon});
//            map.addOverlay(carMk);
//            i=0;
//            function resetMkPoint(i){
//                carMk.setPosition(pts[i]);
//                if(i < paths){
//                    setTimeout(function(){
//                        i++;
//                        resetMkPoint(i);
//                    },100);
//                }
//            }
//            setTimeout(function(){
//                resetMkPoint(5);
//            },100)
//
//        });
//    }
//
//
//
//    setTimeout(function(){
//        run();
//    },1500);






</script>

</body>

</html>