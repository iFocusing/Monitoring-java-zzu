package servlet;

import bean.Organization;
import service.OrganizationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/19.
 */
@WebServlet(name = "UpOrganizationServlet",urlPatterns =  "/servlet/UpOrganizationServlet")
public class UpOrganizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Organization neworganization=new Organization();
        System.out.println("jinlailas");
        neworganization.setOid(Long.valueOf(request.getParameter("oid").trim()));
        neworganization.setName(request.getParameter("name"));
        neworganization.setAddress(request.getParameter("address"));
        neworganization.setParentId(Long.valueOf(request.getParameter("pid").trim()));

        System.out.println(neworganization.getOid()+neworganization.getAddress()+neworganization.getName()+neworganization.getParentId()+neworganization.getParentIds());

        OrganizationService organizationService = new OrganizationService();
        try {
//message为异常情况的返回信息
            String message ;
//url为跳转路由
            String url ;
            boolean flag= organizationService.UpdateOrganization(neworganization);
            if (flag){
                message = "修改成功啦o(*≧▽≦)ツ";
                url = "jsp/addOrganization.jsp";

            }else {
                message = "修改失败Σ( ° △ °|||)︴";
                url = "jsp/addOrganization.jsp";

            }
            //获取session对象中的basePath
            String basePath = (String)request.getSession().getAttribute("basePath");
            //如果message为空则说明登录成功！
//                response.sendRedirect(basePath + url);
            PrintWriter out = response.getWriter();
            out.print(message.toString());
            out.close();
//            out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/UpOrganization.jsp"+ "';</script>;");

        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/UpOrganization.jsp");
        dispatcher.forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
