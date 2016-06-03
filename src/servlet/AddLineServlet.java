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
import java.io.PrintWriter;

/**
 * Created by hanpengyu on 2016/4/23.
 */
@WebServlet(name = "AddLineServlet",urlPatterns ="/servlet/AddLineServlet")
public class AddLineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Line line = new Line();
        LineService lineService = new LineService();
        line.setName(request.getParameter("name"));
        String message=null;
        if(line.getName() == null || line.getName().trim().equals("")){
            message = "线路名不能为空！请检查后重试！";
        }else {
            try {

                if (lineService.AddLine(line)){
                    message="添加成功啦o(*≧▽≦)ツ";
                }else {
                    message = "有重名线路添加失败Σ( ° △ °|||)︴";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        String basePath = (String)request.getSession().getAttribute("basePath");
//        PrintWriter out = response.getWriter();
//        out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/ShowLine.jsp"+ "';</script>;");
//        out.close();
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../servlet/ShowLineServlet");
//        dispatcher.forward(request,response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
