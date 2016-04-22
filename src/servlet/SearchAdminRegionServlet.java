package servlet;

import net.sf.json.JSONObject;
import service.AdminRegionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 3/29/16.
 */
@WebServlet(name = "SearchAdminRegionServlet", urlPatterns = "/servlet/SearchAdminRegionServlet")
public class SearchAdminRegionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long oid = Long.valueOf(request.getParameter("oid"));
        System.out.println("1:::"+oid);
        AdminRegionService adminRegionService = new AdminRegionService();
        try {
//            request.setAttribute("oid", oid);
//            request.setAttribute("adminRegionList", adminRegionService.search(oid));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", adminRegionService.search(oid));
            jsonObject.put("total",adminRegionService.search(oid).size());
//            System.out.println(jsonObject);
            PrintWriter out = response.getWriter();
            out.write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("2:::");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/historyData.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/historyData.jsp");
//        dispatcher.forward(request,response);
        this.doPost(request,response);
    }
}
