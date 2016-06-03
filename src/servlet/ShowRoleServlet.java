package servlet;

import bean.Role;
import dao.RoleDao;
import net.sf.json.JSONObject;
import service.RoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/21.
 */
@WebServlet(name = "ShowRoleServlet",urlPatterns = "/servlet/ShowRoleServlet")
public class ShowRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleService roleService=new RoleService();
        String roleName="";
        String description="";
        if(request.getParameter("roleName")!=null &&request.getParameter("roleName")!=""){
            roleName=request.getParameter("roleName").trim();
        }
        if(request.getParameter("description")!=null &&request.getParameter("description")!=""){
             description=request.getParameter("description").trim();
        }
//        System.out.println(roleName);
//        System.out.println(description);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", roleService.ShowRole(roleName,description));
            jsonObject.put("total", roleService.ShowRole(roleName,description).size());
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
