package servlet;

import bean.Organization;
import net.sf.json.JSONObject;
import service.OrganizationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huojingjing on 16/4/19.
 */
@WebServlet(name = "SearchOrganizationSubServlet",urlPatterns ="/servlet/SearchOrganizationSubServlet")
public class SearchOrganizationSubServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        System.out.println("servlet的路径"+path);
        OrganizationService organizationService =new OrganizationService();
         /*
        * 查询本组织及其子组织;
        * 返回值中包含本组织和它的子组织;第一个是本组织;
        */
        if("/servlet/SearchOrganizationSubServlet".equals(path)) {
            System.out.println("1--");
            long oid = Long.parseLong(request.getParameter("oid"));
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rows", organizationService.searchOrganizationSub(oid));
                jsonObject.put("total",organizationService.searchOrganizationSub(oid).size());
                PrintWriter out = response.getWriter();
                out.write(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
