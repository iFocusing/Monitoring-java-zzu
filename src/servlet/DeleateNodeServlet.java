package servlet;

import bean.Node;
import service.NodeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/27.
 */
@WebServlet(name = "DeleateNodeServlet",urlPatterns = "/servlet/DeleateNodeServlet")
public class DeleateNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NodeService nodeService = new NodeService();
        Long nid= Long.valueOf(request.getParameter("nid").trim());
        String message = null;
        try {
            nodeService.deleate(nid);
            message="节点删除了";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        PrintWriter out = response.getWriter();
//        out.write("删除成功");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowNode.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
