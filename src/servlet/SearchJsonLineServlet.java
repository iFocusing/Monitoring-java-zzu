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
 * Created by hanpengyu on 2016/5/4.
 */
@WebServlet(name = "SearchJsonLineServlet",urlPatterns = "/servlet/SearchJsonLineServlet")
public class SearchJsonLineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LineService lineService = new LineService();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", lineService.ShowLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());
//        System.out.println(jsonObject);
//        System.out.println("11");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
