package servlet;

import bean.Organization;
import bean.User;
import dao.OrganizationDao;
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
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/28.
 */
@WebServlet(name = "SearchJsonOrganizationServlet",urlPatterns = "/servlet/SearchJsonOrganizationServlet")
public class SearchJsonOrganizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrganizationDao organizationDao=new OrganizationDao();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", organizationDao.ShowOrganization(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());
//        System.out.println(jsonObject);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
