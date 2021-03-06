package servlet;

import bean.Role;
import bean.User;
import com.google.gson.Gson;
import dao.UserDao;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import service.AdminRegionService;
import service.PoleService;
import service.RoleService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName=request.getParameter("TxtUserName");
        String password=request.getParameter("TxtPassword");
        System.out.println(userName+"  ****  "+password);
        User currentUser=new User();
        currentUser.setPassword(password);
        currentUser.setUsername(userName);
        UserService userService=new UserService();
        try {
            User user=userService.verifyUser(currentUser);
            if (user == null) {
                Gson gson=new Gson();
                String json=gson.toJson(user);
                PrintWriter out = response.getWriter();
                out.write(json);
                System.out.println("没有查到用户"+json);
            } else {

                Gson gson=new Gson();
                String json=gson.toJson(user);
                PrintWriter out = response.getWriter();
                out.write(json);
                System.out.println("查到用户了"+json);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);//给session设置参数,用于验证用户是否登录
                session.setAttribute("oid", user.getOid());

            //HPY的代码:
                UserDao us =new UserDao();
//                User user1 =null;
//                try{
//                    user1 =us.findOneUser(userName);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }

                try {
                    List<Long> fids;
                    fids=us.findFids(userName);
                    request.getSession().setAttribute("fids",fids);
                    for (int i=0;i<fids.size();i++){
                        System.out.println("loginservelet该用户权限有"+fids.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RoleService roleService=new RoleService();
                try {
                    List<Role> roleList=roleService.ShowRole("","");
                    request.getSession().setAttribute("roleList",roleList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
