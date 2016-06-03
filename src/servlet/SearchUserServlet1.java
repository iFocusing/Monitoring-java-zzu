package servlet;

import bean.User;
import net.sf.json.JSONObject;
import service.UserService;

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
 * Created by hanpengyu on 2016/4/23.
 */
@WebServlet(name = "SearchUserServlet1",urlPatterns = "/servlet/SearchUserServlet1")
public class SearchUserServlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        UserService userService=new UserService();
        User user = new User();
//        System.out.println("servlet得到关键字："+request.getParameter("username"));
        if(request.getParameter("username")!=null){
            user.setUsername(request.getParameter("username"));
        }
        if(request.getParameter("oid")!=null &&request.getParameter("oid")!=""){
            user.setOid(Long.parseLong(request.getParameter("oid").trim()));
        }
        if(request.getParameter("rid")!=null &&request.getParameter("rid")!=""){
            user.setRoleid(Long.parseLong(request.getParameter("rid").trim()));
        }
        if(request.getParameter("status")!=null &&request.getParameter("status")!=""){
            user.setStatus(Boolean.valueOf(request.getParameter("status")));
        }
//        System.out.println("yonghuzhuangtai"+user.getStatus());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rows", userService.searchThisUserList(loginUser,user));
//            System.out.println("查到的json数据"+jsonObject);
            jsonObject.put("total",userService.searchThisUserList(loginUser,user).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());

//        try {
//            request.setAttribute("userList",userService.searchThisUserList(loginUser,user));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/SearchUser1.jsp");
//        dispatcher.forward(request,response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
