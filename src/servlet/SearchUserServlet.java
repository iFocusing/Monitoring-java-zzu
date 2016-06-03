package servlet;
import bean.Organization;
import service.RegionService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
 * Created by hanpengyu on 2016/4/12.
 */

    @WebServlet(name = "SearchUserServlet", urlPatterns = "/servlet/SearchUserServlet")
    public class SearchUserServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Organization organization=new Organization();
            organization.setOid(Long.valueOf(request.getParameter("organizationId").trim()));
            UserService userService =  new UserService();
            try {
                request.setAttribute("userList", userService.search(organization));
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/SearchUser.jsp");
            dispatcher.forward(request,response);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doPost(request,response);
        }
    }