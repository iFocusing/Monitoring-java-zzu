package servlet;

import bean.Pole;
import service.PoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "ModifyPoleServlet",urlPatterns = "/servlet/ModifyPoleServlet")
public class ModifyPoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pole pole = new Pole();
        PoleService poleService = new PoleService();
        System.out.println("servlet执行了");

        pole.setPid(Long.parseLong(request.getParameter("pid").trim()));
        pole.setLid(Long.parseLong(request.getParameter("lid").trim()));
        pole.setOid(Long.parseLong(request.getParameter("oid").trim()));
        pole.setLocation(Long.parseLong(request.getParameter("location").trim()));
        System.out.println(pole);
        String message=null;
        try {
            poleService.modify(pole);
            message="分配成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ModifyPole.jsp");
//        dispatcher.forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
