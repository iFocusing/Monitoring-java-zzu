package servlet;

import bean.User;
import com.google.gson.Gson;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import service.AdminRegionService;
import service.PoleService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
@WebServlet(name = "SearchUserServlet",urlPatterns = "/SearchUserServlet")
public class SearchUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("TxtUserName");
        String password=request.getParameter("TxtPassword");
        System.out.println(username+"  ****  "+password);
        User currentUser=new User();
        currentUser.setPassword(password);
        currentUser.setUsername(username);
        UserService userService=new UserService();
        try {
            User user=userService.verifyUser(currentUser);
            if (user == null) {
                Gson gson=new Gson();
                String json=gson.toJson(user);
                PrintWriter out = response.getWriter();
                out.write(json);
                System.out.println("没有查到用户"+json);
            } else {

                Gson gson=new Gson();
                String json=gson.toJson(user);
                PrintWriter out = response.getWriter();
                out.write(json);
                System.out.println("查到用户了"+json);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);//给session设置参数,用于验证用户是否登录
                session.setAttribute("oid", user.getOid());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
