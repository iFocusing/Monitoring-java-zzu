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

/**
 * Created by hanpengyu on 2016/4/22.
 */
@WebServlet(name = "ModifyRoleServlet",urlPatterns = "/servlet/ModifyRoleServlet")
public class ModifyRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role =new Role();
        role.setRid(Long.valueOf(request.getParameter("rid").trim()));
//这个name是新名字
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));
//放在role对象中的对应的fuction的ids
        String[] check= request.getParameterValues("functions");
        Long[] functions=new Long[check.length];
        for (int i=0;i<check.length;i++){
            functions[i]= Long.valueOf(check[i]);
        }
//        Long[] functions=new Long[]{
//                Long.valueOf(request.getParameter("1").trim()),
//                Long.valueOf(request.getParameter("2").trim()),
//                Long.valueOf(request.getParameter("3").trim()),
//                Long.valueOf(request.getParameter("4").trim()),
//                Long.valueOf(request.getParameter("5").trim()),
//                Long.valueOf(request.getParameter("6").trim()),
//                Long.valueOf(request.getParameter("7").trim()),
//                Long.valueOf(request.getParameter("8").trim())};
        role.setFunctions(functions);
        RoleService roleService=new RoleService();
        try {
            roleService.ModifyRole(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ManageRole.jsp");
        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
