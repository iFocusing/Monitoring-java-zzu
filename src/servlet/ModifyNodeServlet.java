package servlet;

import bean.Node;
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
@WebServlet(name = "ModifyNodeServlet",urlPatterns = "/servlet/ModifyNodeServlet")

public class ModifyNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NodeService nodeService = new NodeService();
        Node node = new Node();
        node.setNid(Long.valueOf(request.getParameter("nid").trim()));
        node.setPid(Long.valueOf(request.getParameter("pid").trim()));
        String message=null;

        try {
            if (nodeService.modify(node)){
                message="分配成功";
            }else {
                message="失败！数量超限制";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        String basePath = (String)request.getSession().getAttribute("basePath");
//        PrintWriter out = response.getWriter();
//        out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/ShowNode.jsp"+ "';</script>;");
//        out.close();
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowNode.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
