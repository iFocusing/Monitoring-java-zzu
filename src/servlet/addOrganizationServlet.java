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
 * Created by hanpengyu on 2016/4/14.
 */
@WebServlet(name = "addOrganizationServlet", urlPatterns = "/servlet/addOrganizationServlet")

public class addOrganizationServlet extends HttpServlet {
    //怎么得到这个session中的值，在loginServlet中放入的

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Organization org = new Organization();
//        测试是否传进来了数据
//        System.out.println(request.getParameter("address"));
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getParameter("parentId"));
            org.setName(request.getParameter("name"));
            org.setAddress(request.getParameter("address"));
            org.setParentId(Long.valueOf(request.getParameter("parentId")));
            OrganizationService organizationService = new OrganizationService();
//把登录的用户放到session。在login中中已经放了了,这里取出来
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        String message =null;
            try {
//message为异常情况的返回信息

//url为跳转路由
                String url ;
                boolean flag= organizationService.add(user,org);
                if (flag){
                    message = "添加成功啦o(*≧▽≦)ツ";
                    url = "jsp/addOrganization.jsp";

                }else {
                    message = "添加失败Σ( ° △ °|||)︴";
                    url = "jsp/addOrganization.jsp";

                }
//                //获取session对象中的basePath
//                String basePath = (String)request.getSession().getAttribute("basePath");
//                //如果message为空则说明登录成功！
////                response.sendRedirect(basePath + url);
//                PrintWriter out = response.getWriter();
//                out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/addOrganization.jsp"+ "';</script>;");
//                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//            RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/addOrganization.jsp");
//            dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/addOrganization.jsp");
            dispatcher.forward(request,response);
    }
}

