package servlet;

import bean.User;
import service.PoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "ShowPoleServlet",urlPatterns = "/servlet/ShowPoleServlet")
public class ShowPoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PoleService poleService = new PoleService();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        try {
            request.setAttribute("poleList",poleService.show(loginUser));
//            System.out.println("这句话执行了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowPole.jsp");
        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
