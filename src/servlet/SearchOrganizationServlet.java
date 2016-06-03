package servlet;

import bean.Organization;
import bean.User;
import net.sf.json.JSONObject;
import service.OrganizationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/19.
 */
@WebServlet(name = "SearchOrganizationServlet",urlPatterns = "/servlet/SearchOrganizationServlet")
public class SearchOrganizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Organization org = new Organization();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        OrganizationService organizationService = new OrganizationService();
//        测试是否传进来了数据
//        System.out.println(request.getParameter("address"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("parentId"));
        org.setName("");
        org.setAddress("");
        if(request.getParameter("name")!=null){
            org.setName(request.getParameter("name"));
        }
        if(request.getParameter("address")!=null){
            org.setAddress(request.getParameter("address"));
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", organizationService.search(user,org));
            jsonObject.put("total", organizationService.search(user,org).size());
//            request.setAttribute("poleList",poleService.search(loginUser,pole));
//            System.out.println("查询方法执行了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());

//        try{
//            request.setAttribute("organizationList", organizationService.search(user,org));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowOrganization.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
