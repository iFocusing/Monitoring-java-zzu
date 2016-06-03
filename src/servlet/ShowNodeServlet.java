package servlet;

import bean.Line;
import bean.Node;

import bean.Organization;
import bean.User;
import dao.LineDao;
import dao.OrganizationDao;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
import service.NodeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/27.
 */
@WebServlet(name = "ShowNodeServlet",urlPatterns = "/servlet/ShowNodeServlet")
public class ShowNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NodeService nodeService = new NodeService();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", nodeService.show());
            jsonObject.put("total", nodeService.show().size());
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
