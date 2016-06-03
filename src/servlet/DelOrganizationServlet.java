package servlet;

import bean.Organization;
import bean.User;
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
 * Created by hanpengyu on 2016/4/18.
 */
@WebServlet(name = "DelOrganizationServlet",urlPatterns = "/servlet/DelOrganizationServlet")
public class DelOrganizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Organization org = new Organization();
//        测试是否传进来了数据
//      System.out.println(request.getParameter("name"));
        org.setName(request.getParameter("name"));
        OrganizationService organizationService = new OrganizationService();
//把登录的用户放到session。在login中中已经放了了
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
//message为异常情况的返回信息
            String message ;
//url为跳转路由
            String url ;
            boolean flag= organizationService.DeleateOrganization(user,org);
            if (flag){
                message = "删除成功啦o(*≧▽≦)ツ";
                url = "jsp/deleateOrganization.jsp";

            }else {
                message = "删除失败Σ( ° △ °|||)︴";
                url = "jsp/deleateOrganization.jsp";

            }
            //获取session对象中的basePath
            String basePath = (String)request.getSession().getAttribute("basePath");
            //如果message为空则说明登录成功！
//                response.sendRedirect(basePath + url);
            PrintWriter out = response.getWriter();
            out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/deleateOrganization.jsp"+ "';</script>;");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/deleateOrganization.jsp");
        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/deleateOrganization.jsp");
        dispatcher.forward(request,response);
    }

}
