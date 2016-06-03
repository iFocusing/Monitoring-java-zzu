package servlet;

import bean.Node;
import bean.User;
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
@WebServlet(name = "AddNodeServlet",urlPatterns = "/servlet/AddNodeServlet")
public class AddNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String basePath = (String)request.getSession().getAttribute("basePath");
        List<Long> fids= (List<Long>) request.getSession().getAttribute("fids");
        String message="您没有这个权限";
        String url = "jsp/ShowNode.jsp";
        for (int i=0;i<fids.size();i++){
//            System.out.println(fids.get(i));
            if (fids.get(i)==21){
                NodeService nodeService = new NodeService();
                String source=request.getParameter("source").trim();
                message=null;
                try {
                    nodeService.add(source);
                    message="节点添加成功";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                message="您没有这个权限";
            }
        }
//        if(message == null){
//            response.sendRedirect(basePath + url);
//        }else{
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        }

//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowNode.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
