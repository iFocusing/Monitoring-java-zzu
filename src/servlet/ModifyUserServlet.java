package servlet;

import bean.User;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanpengyu on 2016/4/26.
 */
@WebServlet(name = "ModifyUserServlet",urlPatterns = "/servlet/ModifyUserServlet")
public class ModifyUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        User newUser = new User();
        String message = null;
        System.out.println("modifyservlet执行了");
        newUser.setUid(Long.parseLong(request.getParameter("uid")));
        newUser.setUsername(request.getParameter("username"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setAddress(request.getParameter("address"));
        newUser.setTel(request.getParameter("tel"));
        newUser.setOid(Long.valueOf(request.getParameter("oid").trim()));
//获得boolean类型的性别属性
        Long a= Long.valueOf(request.getParameter("sex"));
        boolean b;
        if (a==1){
            b=true;
        }else {
            b=false;
        }
        newUser.setSex(b);
//获得日期属性
        SimpleDateFormat sim=new SimpleDateFormat( "yyyy-MM-dd");
        String str=request.getParameter("birthday").trim();
        newUser.setBirthdayString(str);
        try {
            Date d= sim.parse(str);
            newUser.setBirthday(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//获得状态属性
        Long a1= Long.valueOf(request.getParameter("status"));
        if (a1==1){
            b=true;
        }else {
            b=false;
        }
        newUser.setStatus(b);
        String roleValues = request.getParameter("role11");
        System.out.println(roleValues);
        String[] roles = roleValues.split(",");
//        System.out.println("00000");

        if (request.getParameter("username") == null || request.getParameter("username").trim().equals("")){

            message = "用户名为空或存在非法字符！请检查后重试！";
        }else if (request.getParameter("password") == null || request.getParameter("password").trim().equals("")){
            message = "密码为空或存在非法字符！请检查后重试！";

        }else if (request.getParameter("address") == null || request.getParameter("address").trim().equals("")){
            message = "地址为空或存在非法字符！请检查后重试！";
        }else if (request.getParameter("tel") == null || request.getParameter("tel").trim().equals("")){
            message = "联系方式名为空或存在非法字符！请检查后重试！";
        }else if (request.getParameter("sex") == null || request.getParameter("sex").trim().equals("")){
            message = "性别为空或存在非法字符！请检查后重试！";
        }else if (request.getParameter("oid") == null || request.getParameter("oid").trim().equals("")){
            message = "未选择组织机构！请检查后重试！";
        }else if (request.getParameter("status") == null || request.getParameter("status").trim().equals("")){
            message = "状态为空或存在非法字符！请检查后重试！";
        }
        else if (request.getParameter("birthday") == null || request.getParameter("birthday").trim().equals("")){
            message = "出生年月为空或存在非法字符！请检查后重试！";
        }else{

//            System.out.println("111111");


            UserService userService=new UserService();
            try {
//message为异常情况的返回信息
                boolean flag= userService.modify(newUser,roles);
                if (flag){
                    message = "用户修改成功啦o(*≧▽≦)ツ";
                }else {
                    message = "用户修改失败Σ( ° △ °|||)︴";
                }
//                String basePath = (String)request.getSession().getAttribute("basePath");
//                PrintWriter out = response.getWriter();
//                out.print("<script type='text/javascript'>alert('" + message + "');window.location.href='"+ basePath +"/jsp/ModifyUser.jsp"+ "';</script>;");
//                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.write(message.toString());
//        RequestDispatcher dispatcher = request.getRequestDispatcher("../jsp/ModifyUser.jsp");
//        dispatcher.forward(request,response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}

