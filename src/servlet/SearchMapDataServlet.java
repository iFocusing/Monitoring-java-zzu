package servlet;

import net.sf.json.JSONObject;
import service.DataService;
import service.LineService;
import service.PoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huojingjing on 16/4/21.
 */
@WebServlet(name = "SearchMapDataServlet", urlPatterns = {"/servlet/SearchMapDataServlet"})
public class SearchMapDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long oid= Long.valueOf(request.getParameter("oid"));
        PoleService poleService=new PoleService();
        LineService lineService=new LineService();
        DataService dataService=new DataService();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lines",lineService.searchLineInOraganization(oid));
            jsonObject.put("rows",dataService.searchDataMapByOid(oid));
            jsonObject.put("total",dataService.searchDataMapByOid(oid).size());
            System.out.println(jsonObject);
            PrintWriter out = response.getWriter();
            out.write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
