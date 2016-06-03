package servlet;

import bean.User;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "DeleateUserServlet",urlPatterns ="/servlet/DeleateUserServlet")
public class DeleateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setUid(Long.parseLong(request.getParameter("uid").trim()));
        String message = null;
        UserService userService=new UserService();
        try {
            userService.deleate(user);
            message="删除了";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/DeleateUser.jsp");
//        dispatcher.forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
