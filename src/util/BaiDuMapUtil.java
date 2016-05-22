package util;
import net.sf.json.JSONObject;

/**
 * 百度地图工具类
 * Created by huojingjing on 16/5/10.
 */

public class BaiDuMapUtil {
    public  String getCity(String lat, String lng) {
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
                .getJSONObject("addressComponent");
        return obj.getString("city");
    }

    public  String getAdcode(String lat, String lng) {
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
                .getJSONObject("addressComponent");
        return obj.getString("adcode");
    }

    public  JSONObject getLocationInfo(String lat, String lng) {
        String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","
                + lng + "&output=json&ak=" + "hK24m3To7xn89fF0mwzGCqRPKe3wksh4"+"&pois=0";
        JSONObject obj = JSONObject.fromObject(HttpUtil.sendGet(url,"utf-8"));
        return obj;
    }

    public  String getProvince(String lat, String lng) {
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
                .getJSONObject("addressComponent");
        return obj.getString("province");
    }
    /*
    * 给定经纬度,可以获得具体城市和行政区域编号*/
    public static void main(String[] args) {
        System.out.println("11");
        BaiDuMapUtil baiDuMapUtil=new BaiDuMapUtil();
        System.out.println(baiDuMapUtil.getCity("39.929986", "116.395645"));
        System.out.println(baiDuMapUtil.getAdcode("39.929986", "116.395645"));
        System.out.println(baiDuMapUtil.getCity("31.249162", "121.487899"));
        System.out.println(baiDuMapUtil.getAdcode("31.249162", "121.487899"));
        System.out.println(baiDuMapUtil.getProvince("31.859252", "117.216005"));


    }
}