package servlet;

import bean.Pole;
import bean.User;
import net.sf.json.JSONObject;
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
 * Created by hanpengyu on 2016/4/27.
 */
@WebServlet(name = "SearchPoleServletHPY", urlPatterns = "/servlet/SearchPoleServletHPY")
public class SearchPoleServletHPY extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PoleService poleService = new PoleService();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        Pole pole = new Pole();
//        System.out.println("servletz执行了");
        if(request.getParameter("oid")!=null &&request.getParameter("oid")!=""){
            pole.setOid(Long.parseLong(request.getParameter("oid").trim()));
        }
        if (request.getParameter("aid")!=null &&request.getParameter("aid")!=""){
            pole.setAid(Long.parseLong(request.getParameter("aid").trim()));
        }
        if (request.getParameter("lid")!=null &&request.getParameter("lid")!=""){
            pole.setLid(Long.parseLong(request.getParameter("lid")));
        }
//        System.out.println("jsp上的lid"+request.getParameter("lid"));
//        System.out.println("jsp上的oid"+request.getParameter("oid"));
//        System.out.println(pole.getLid());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", poleService.search(loginUser,pole));
            jsonObject.put("total", poleService.search(loginUser,pole).size());
//            request.setAttribute("poleList",poleService.search(loginUser,pole));
//            System.out.println("查询方法执行了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());
//        System.out.println(jsonObject);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ShowPole.jsp");
//        dispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
