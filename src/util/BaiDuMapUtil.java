package util;
import net.sf.json.JSONObject;

/**
 * 百度地图工具类
 * Created by huojingjing on 16/5/10.
 */

public class BaiDuMapUtil {
    public static String getCity(String lat, String lng) {
        System.out.println("22");
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
                .getJSONObject("addressComponent");
        return obj.getString("city");
    }

    public static String getAdcode(String lat, String lng) {
        System.out.println("22");
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result")
                .getJSONObject("addressComponent");
        return obj.getString("adcode");
    }

    public static JSONObject getLocationInfo(String lat, String lng) {
        String url = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + ","
                + lng + "&output=json&ak=" + "hK24m3To7xn89fF0mwzGCqRPKe3wksh4"+"&pois=0";
        System.out.println(url);

        JSONObject obj = JSONObject.fromObject(HttpUtil.sendGet(url,"utf-8"));
        System.out.println(obj);

        return obj;
    }

    /*
    * 给定经纬度,可以获得具体城市和行政区域编号*/
    public static void main(String[] args) {
        System.out.println("11");
        System.out.println(BaiDuMapUtil.getCity("39.929986", "116.395645"));
        System.out.println(BaiDuMapUtil.getAdcode("39.929986", "116.395645"));
        System.out.println(BaiDuMapUtil.getCity("31.249162", "121.487899"));
        System.out.println(BaiDuMapUtil.getAdcode("31.249162", "121.487899"));



    }
}