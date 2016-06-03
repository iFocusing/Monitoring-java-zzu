package servlet;

import net.sf.json.JSONObject;
import service.NodeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/27.
 */
@WebServlet(name = "SearchNodeNotUseServlet",urlPatterns = "/servlet/SearchNodeNotUseServlet")
public class SearchNodeNotUseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            NodeService nodeService = new NodeService();
            String source=request.getParameter("source").trim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", nodeService.searchNotUse(source));
            jsonObject.put("total", nodeService.searchNotUse(source).size());
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
