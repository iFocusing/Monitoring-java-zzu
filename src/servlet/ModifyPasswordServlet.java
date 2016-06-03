package servlet;

import bean.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/5/11.
 */
@WebServlet(name = "ModifyPasswordServlet",urlPatterns = "/servlet/ModifyPasswordServlet")
public class ModifyPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        UserService userService = new UserService();
        String message=null;
        String newPassword=request.getParameter("newPassword");
        if (user.getPassword().equals(request.getParameter("oldPassword").trim())){
            try {
                userService.modify(user,newPassword);
                message="修改成功！";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            message="原始密码不正确";
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
