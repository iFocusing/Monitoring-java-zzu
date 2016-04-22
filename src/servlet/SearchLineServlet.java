package servlet;

import net.sf.json.JSONObject;
import service.AdminRegionService;
import service.LineService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/3/30.
 */
@WebServlet(name = "SearchLineServlet", urlPatterns = "/servlet/SearchLineServlet")
public class SearchLineServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long oid = Long.valueOf(request.getParameter("oid"));
        Long aid = Long.valueOf(request.getParameter("aid"));
//        System.out.println(oid+" maya  "+aid);
        LineService lineService= new LineService();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", lineService.searchLineInOraganizationAdmin(aid, oid));
            jsonObject.put("total",lineService.searchLineInOraganizationAdmin(aid, oid).size());
//            System.out.println(jsonObject);
            PrintWriter out = response.getWriter();
            out.write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

}
