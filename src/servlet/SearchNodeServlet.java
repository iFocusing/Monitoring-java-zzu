package servlet;

import bean.Line;
import bean.Node;
import bean.Organization;
import bean.User;
import dao.LineDao;
import dao.OrganizationDao;
import net.sf.json.JSONObject;
import service.NodeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by hanpengyu on 2016/4/27.
 */
@WebServlet(name = "SearchNodeServlet",urlPatterns = "/servlet/SearchNodeServlet")
public class SearchNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        NodeService nodeService = new NodeService();
        Node node = new Node();

//        List<Long> fids= (List<Long>) request.getSession().getAttribute("fids");
//        String message="您没有这个权限";
//        for (int i=0;i<fids.size();i++){
//
//            if (fids.get(i)==24){
                if (request.getParameter("fenpei")==null||Long.valueOf(request.getParameter("fenpei"))!=0L){
                    if(request.getParameter("source")!=null&&request.getParameter("source")!="" ){
                        node.setSource(request.getParameter("source").trim());
                        //            System.out.println("__"+node.getSource());
                    } else{ node.setSource("");
                        //            System.out.println("源地址为空");
                    }

                    if(request.getParameter("oid")!=null &&request.getParameter("oid")!=""){
                        node.setOid(Long.parseLong(request.getParameter("oid").trim()));
                    }else node.setOid(null);
                    if (request.getParameter("lid")!=null &&request.getParameter("lid")!=""){
                        node.setLid(Long.parseLong(request.getParameter("lid").trim()));
                    }else node.setLid(null);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rows", nodeService.search(node));
                        jsonObject.put("total", nodeService.search(node).size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PrintWriter out = response.getWriter();
                    out.write(jsonObject.toString());

//                    out.write(message.toString());
//            if (fenpei.trim()=="已经分配"||fenpei.trim()==null){

//查询节点的时候直接输入源地址source关键字
//        node.setSource(request.getParameter("source").trim());

//查询节点时候在下拉列表里选择 组织机构

//        System.out.println("nollll");
//        try {
//            List<Organization> organizationList =organizationDao.ShowOrganization(user);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("rows", organizationDao.ShowOrganization(user));
//            PrintWriter out = response.getWriter();
//            out.write(jsonObject.toString());
//            System.out.println(jsonObject);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////查询节点时候在下拉列表里选择 线路
//        LineDao lineDao=new LineDao();
//        try {
//            List<Line> lineList=lineDao.ShowLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
                } else {
                    if(request.getParameter("source")!=null&&request.getParameter("source")!="" ){
                        node.setSource(request.getParameter("source").trim());
                        //            System.out.println("__"+node.getSource());
                    } else{ node.setSource("");
                        //            System.out.println("源地址为空");
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rows", nodeService.searchNotUse(node.getSource().trim()));
                        jsonObject.put("total", nodeService.searchNotUse(node.getSource().trim()).size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PrintWriter out = response.getWriter();
                    out.write(jsonObject.toString());


                }

//            }
//            else {
//                message="您没有这个权限";
////                PrintWriter out = response.getWriter();
////                out.write(message.toString());
//
////llhelp没有权限时候
////                JSONObject jsonObject = new JSONObject();
////                jsonObject.put("total",0);
////                jsonObject.put("rows","{"+message+"}");
////
////                PrintWriter out = response.getWriter();
////                out.write(jsonObject.toString());
//            }
        }



//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
