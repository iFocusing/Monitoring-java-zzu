package servlet;

import net.sf.json.JSONObject;
import service.DataService;
import service.LineService;
import service.PoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huojingjing on 16/4/23.
 */
@WebServlet(name = "SearchMapDataServlet" ,urlPatterns = {"/servlet/SearchMapDataServlet","/servlet/SearchMapMoreDataServlet"})
public class SearchMapDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        System.out.println("servlet的路径" + path);

        if ("/servlet/SearchMapDataServlet".equals(path)) {
            Double longitude = Double.valueOf(request.getParameter("longitude"));
            Double latitude = Double.valueOf(request.getParameter("latitude"));
            DataService dataService = new DataService();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rows", dataService.searchData(longitude, latitude));
                jsonObject.put("total", dataService.searchData(longitude, latitude).size());
                System.out.println(jsonObject);
                PrintWriter out = response.getWriter();
                out.write(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("/servlet/SearchMapMoreDataServlet".equals(path)) {
            Long pid = Long.valueOf(request.getParameter("pid"));
            DataService dataService = new DataService();
            try {
                request.setAttribute("pid", pid);
//            request.setAttribute("adminRegionList", adminRegionService.search(oid));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rows", dataService.searchData(pid));
                jsonObject.put("total", dataService.searchData(pid).size());
                System.out.println(jsonObject);
                PrintWriter out = response.getWriter();
                out.write(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/historyData.jsp");
//        dispatcher.forward(request,response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
