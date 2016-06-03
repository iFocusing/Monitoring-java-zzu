package servlet;

import service.RegionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 3/29/16.
 */
@WebServlet(name = "SearchRegionServlet", urlPatterns = "/servlet/SearchRegionServlet")
public class SearchRegionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  organizationId = request中的得到的那个输入值organizationId
        Long organizationId = Long.valueOf(request.getParameter("organizationId"));
        /*new一个regionService对象
          返回地区列表，给，绿色的是起的名字
         */
        RegionService regionService = new RegionService();
        try {
           // request.setAttribute("organizationId", organizationId);
            request.setAttribute("regionList", regionService.search(organizationId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //转到这个jsp去
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/SearchRegion.jsp");
        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/SearchRegion.jsp");
        dispatcher.forward(request,response);
        this.doPost(request,response);

    }
}
