package servlet;

import bean.Role;
import service.RoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/22.
 */
@WebServlet(name = "DeleateRoleServlet",urlPatterns ="/servlet/DeleateRoleServlet")
public class DeleateRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role =new Role();
        RoleService roleService=new RoleService();
        role.setRid(Long.valueOf(request.getParameter("rid").trim()));
        String message = null;
        try {
            roleService.DeleateRole(role);
            message="删除了";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
