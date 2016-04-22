package servlet;

import net.sf.json.JSONObject;
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
 * Created by Administrator on 2016/4/10.
 */
@WebServlet(name = "SearchPoleServlet", urlPatterns = "/servlet/SearchPoleServlet")
public class SearchPoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long lid= Long.valueOf(request.getParameter("lid"));
        Long oid= Long.valueOf(request.getParameter("oid"));
        Long aid= Long.valueOf(request.getParameter("aid"));
        PoleService poleService=new PoleService();
        try {
//            request.setAttribute("lid",lid);
//            request.setAttribute("poleList",poleService.searchPoleByLine(lid));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", poleService.searchPoleByLine(oid,aid,lid));
            jsonObject.put("total",poleService.searchPoleByLine(oid,aid,lid).size());
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
