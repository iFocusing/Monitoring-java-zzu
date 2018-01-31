package servlet;

import net.sf.json.JSONObject;
import service.LineService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/5/9.
 */
@WebServlet(name = "SearchLineServletHPY",urlPatterns = "/servlet/SearchLineServletHPY")
public class SearchLineServletHPY extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LineService lineService = new LineService();
        JSONObject jsonObject = new JSONObject();
        String name="";
        if(request.getParameter("name")!=null&&request.getParameter("name")!="" ){
            name=(request.getParameter("name").trim());
        }
        try {
            jsonObject.put("rows", lineService.SearchLine(name.trim()));
            jsonObject.put("total",lineService.SearchLine(name.trim()).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
