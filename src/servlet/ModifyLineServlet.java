package servlet;

import bean.Line;
import service.LineService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "ModifyLineServlet",urlPatterns ="/servlet/ModifyLineServlet")
public class ModifyLineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Line line = new Line();
        LineService lineService = new LineService();

        line.setLid(Long.valueOf(request.getParameter("lid").trim()));
        line.setName(request.getParameter("name"));
        try {
            lineService.ModifyLine(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/SearchLine.jsp");
        dispatcher.forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
