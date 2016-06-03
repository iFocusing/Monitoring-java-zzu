package servlet;

import bean.Pole;
import bean.User;
import service.PoleService;

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
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "AddPoleServlet",urlPatterns ="/servlet/AddPoleServlet")
public class AddPoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pole pole = new Pole();
        PoleService poleService = new PoleService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String message = null;

        pole.setLongitude(Double.parseDouble(request.getParameter("longitude").trim()));
        pole.setLatitude(Double.parseDouble(request.getParameter("latitude").trim()));
        pole.setOid(user.getOid());
//地区id应该根据经纬度来得到
//        pole.setAid(Long.parseLong(request.getParameter("aid").trim()));
        pole.setAid(5L);
        try {
            poleService.add(pole);
            message="添加成功";

        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/AddPole.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
