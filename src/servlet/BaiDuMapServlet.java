package servlet;

import service.BaiDuService;
import util.BaiDuMapUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huojingjing on 16/5/10.
 */
@WebServlet(name = "BaiDuMapServlet",urlPatterns = {"/servlet/BaiDuMapProvinceServlet","/servlet/BaiDuMapCityServlet"})
public class BaiDuMapServlet extends HttpServlet {
    BaiDuMapUtil  baiDuMapUtil=new BaiDuMapUtil();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        System.out.println("servlet的路径!!!!!!!!!!!!!"+path);

        /*
        *
        */
        if("/servlet/BaiDuMapProvinceServlet".equals(path)) {
            String lat=request.getParameter("lat");
            String lng=request.getParameter("lng");
            String name=baiDuMapUtil.getProvince(lat,lng);
            String adcode=baiDuMapUtil.getAdcode(lat,lng);
            System.out.println("******插入省:"+name+adcode+","+lat+"--"+lng);
            BaiDuService bauDuService=new BaiDuService();
            try {
                bauDuService.insertProvince(name,adcode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

         /*
        *
        */
        if("/servlet/BaiDuMapCityServlet".equals(path)) {
            String lat=request.getParameter("lat");
            String lng=request.getParameter("lng");
            String lat0=request.getParameter("lat0");
            String lng0=request.getParameter("lng0");
            System.out.println(lat0+"----------"+lng0);

            String name=baiDuMapUtil.getCity(lat,lng);
            String adcode=baiDuMapUtil.getAdcode(lat,lng);
            String provinceAdcode=baiDuMapUtil.getAdcode(lat0,lng0);
            String provinceName=baiDuMapUtil.getProvince(lat0,lng0);
            System.out.println("插入:"+provinceAdcode+provinceName+"中的市"+name+adcode+",省已经在上一个servlet总插入了;");
            BaiDuService bauDuService=new BaiDuService();
            try {
                bauDuService.insertCity(name,adcode,provinceAdcode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
