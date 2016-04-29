package servlet;

import bean.Data;
import bean.DataDisplay;
import bean.User;
import service.DataService;
import util.HChartUtil;
import util.TransferTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
/**
 * Created by Administrator on 2016/4/10.
 */
@WebServlet(name = "SearchDataServlet",urlPatterns = {"/servlet/SearchDataServlet","/servlet/SearchChartDataServlet","/servlet/SearchChartCurrentDataServlet","/servlet/SearchChartPreviousCurrentDataServlet"})
public class SearchDataServlet extends HttpServlet {
    DataService dataService=new DataService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        System.out.println("servlet的路径"+path);

        /*
        * 查询历史数据表格展示;
        */
        if("/servlet/SearchDataServlet".equals(path)) {
            ArrayList<DataDisplay> dataList=new ArrayList<DataDisplay>();
            //System.out.println("到查询数据表格展示");
            //System.out.println("组织"+request.getParameter("oid")+"地区"+request.getParameter("aid")+"线路"+request.getParameter("lid")+"线杆"+request.getParameter("pid"));
            boolean boid=false;
            boolean baid=false;
            boolean blid=false;
            boolean bpid=false;
            boolean btime=false;
            if(request.getParameter("oid")!=null &&request.getParameter("oid")!=""){
                boid=true;
            }
            if(request.getParameter("aid")!=null &&request.getParameter("aid")!=""){
                baid=true;
            }
            if(request.getParameter("lid")!=null && request.getParameter("lid")!=""){
                blid=true;
            }
            if(request.getParameter("pid")!=null &&request.getParameter("pid")!=""){
                bpid=true;
            }
            if(request.getParameter("startTime")!=null &&request.getParameter("endTime")!=null){
                btime=true;
            }
            //System.out.println("组织"+boid+"地区"+baid+"线路"+blid+"线杆"+bpid);
            //查询条件:pid不为空,时间不为空
            if(bpid && btime){
            long pid = Long.parseLong(request.getParameter("pid"));
            String start = request.getParameter("startTime");
            String end = request.getParameter("endTime");
            TransferTime transferTime = new TransferTime();
            Timestamp startTime = transferTime.tansfer(start);
            Timestamp endTime = transferTime.tansfer(end);
            try {
                dataList = (ArrayList<DataDisplay>) dataService.searchDataByPid(pid, startTime, endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            }else if (!bpid && btime && blid && baid && boid){
                //查询条件:pid为空,组织\地区\线路\时间都不为空
                Long oid=Long.valueOf(request.getParameter("oid"));
                Long aid=Long.valueOf(request.getParameter("aid"));
                Long lid=Long.valueOf(request.getParameter("lid"));
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                    dataList = (ArrayList<DataDisplay>) dataService.searchData(oid,aid,lid,startTime,endTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && baid && boid){
                //查询条件:pid\线路为空,组织\地区\时间不为空
                Long oid=Long.valueOf(request.getParameter("oid"));
                Long aid=Long.valueOf(request.getParameter("aid"));
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                    dataList = (ArrayList<DataDisplay>) dataService.searchData(oid,aid,startTime,endTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && !baid && boid){
                //查询条件:pid\线路\地区为空,组织\时间不为空
                Long oid=Long.valueOf(request.getParameter("oid"));
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                    dataList = (ArrayList<DataDisplay>) dataService.searchDataByOid(oid,startTime,endTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && !baid && !boid){
                //查询条件:pid\线路\地区\组织为空,时间不为空--查询本组织在某个时间段的数据
                User user=(User) request.getSession().getAttribute("user");
                Long oid=user.getOid();
                //System.out.println("从session里面获取的oid;");
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                    dataList = (ArrayList<DataDisplay>) dataService.searchDataByOid(oid,startTime,endTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(!bpid && !btime && !blid && !baid && !boid){
                //查询条件:pid\线路\地区\组织\时间都为空--查询该组织的所有数据
                try {
                    User user= (User) request.getSession().getAttribute("user");
                    Long oid=user.getOid();
                    dataList = (ArrayList<DataDisplay>) dataService.searchAllData(oid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rows", dataList);
            jsonObject.put("total", dataList.size());
            PrintWriter out = response.getWriter();
            out.write(jsonObject.toString());
        }


        //尚未完成的业务:
        /*
        * 模糊查询;
        */
        /*
        * 排序;
        */



        /*
        * 查询历史数据图形展示;
        */
        if("/servlet/SearchChartDataServlet".equals(path)) {
            //System.out.println("组织"+request.getParameter("oid")+"地区"+request.getParameter("aid")+"线路"+request.getParameter("lid")+"线杆"+request.getParameter("pid"));
            boolean boid=false;
            boolean baid=false;
            boolean blid=false;
            boolean bpid=false;
            boolean btime=false;
            if(request.getParameter("oid")!=null &&request.getParameter("oid")!=""){
                boid=true;
            }
            if(request.getParameter("aid")!=null &&request.getParameter("aid")!=""){
                baid=true;
            }
            if(request.getParameter("lid")!=null && request.getParameter("lid")!=""){
                blid=true;
            }
            if(request.getParameter("pid")!=null &&request.getParameter("pid")!=""){
                bpid=true;
            }
            if(request.getParameter("startTime")!=null &&request.getParameter("endTime")!=null){
                btime=true;
            }
            //System.out.println("组织"+boid+"地区"+baid+"线路"+blid+"线杆"+bpid);
            System.out.println("到查询数据图形展示");
            String[] pids = new String[10];
            HChartUtil hChartUtil = new HChartUtil();
            List<List<DataDisplay>> listList = null;
            //查询条件:pid不为空,时间不为空
            if(bpid && btime){
                try {
                    String startTime = request.getParameter("startTime");
                    String endTime = request.getParameter("endTime");
                    String pid = request.getParameter("pid");
                    pids[0] = pid;
                    listList = dataService.searchChartData(pids, startTime, endTime);
                    JSONObject jsonobject = new JSONObject();
                    jsonobject.put("total", pids.length);
                    jsonobject.put("rows", hChartUtil.getHistoryData(listList));
//                    System.out.println("rows::"+hChartUtil.getHistoryData(listList));
                    jsonobject.put("timelist", hChartUtil.getHistoryTimeList(listList));
                    PrintWriter out = response.getWriter();
                    System.out.print("jsonobject:"+jsonobject);
                    out.write(jsonobject.toString());
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (!bpid && btime && blid && baid && boid){
                //查询条件:pid为空,组织\地区\线路\时间都不为空
                System.out.println("查询条件:pid为空,组织\\地区\\线路\\时间都不为空\n");
                System.out.println("组织"+request.getParameter("oid")+"地区"+request.getParameter("aid")+"线路"+request.getParameter("lid"));
                Long oid=Long.valueOf(request.getParameter("oid"));
                Long aid=Long.valueOf(request.getParameter("aid"));
                Long lid=Long.valueOf(request.getParameter("lid"));
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                try {
                    listList = dataService.searchChartData1(oid,aid,lid, startTime, endTime);
                    JSONObject jsonobject = new JSONObject();
                    jsonobject.put("total", pids.length);
                    jsonobject.put("rows", hChartUtil.getHistoryDataByPids(listList));
                    System.out.println("pids---rows::"+hChartUtil.getHistoryDataByPids(listList));
                    jsonobject.put("timelist", hChartUtil.getPidList(listList));
                    PrintWriter out = response.getWriter();
                    System.out.print("jsonobject:"+jsonobject);
                    out.write(jsonobject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && baid && boid){
                //查询条件:pid\线路为空,组织\地区\时间不为空
                Long oid=Long.valueOf(request.getParameter("oid"));
                Long aid=Long.valueOf(request.getParameter("aid"));
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && !baid && boid){
                //查询条件:pid\线路\地区为空,组织\时间不为空
                Long oid=Long.valueOf(request.getParameter("oid"));
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!bpid && btime && !blid && !baid && !boid){
                //查询条件:pid\线路\地区\组织为空,时间不为空--查询本组织在某个时间段的数据
                User user=(User) request.getSession().getAttribute("user");
                Long oid=user.getOid();
                //System.out.println("从session里面获取的oid;");
                String start = request.getParameter("startTime");
                String end = request.getParameter("endTime");
                TransferTime transferTime = new TransferTime();
                Timestamp startTime = transferTime.tansfer(start);
                Timestamp endTime = transferTime.tansfer(end);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(!bpid && !btime && !blid && !baid && !boid){
                //查询条件:pid\线路\地区\组织\时间都为空--查询该组织的所有数据
                try {
                    User user= (User) request.getSession().getAttribute("user");
                    Long oid=user.getOid();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if("/servlet/SearchChartPreviousCurrentDataServlet".equals(path)) {
            //查询条件:线路\地区\组织\时间都为空--查询pid的所有数据
            System.out.println("实时数据展示");
            HChartUtil hChartUtil = new HChartUtil();
            try {
                System.out.println("查询条件:线路\\地区\\组织\\时间都为空--查询pid的所有数据");
                Long pid =Long.valueOf( request.getParameter("pid"));
                List<DataDisplay> displayList = null;
                displayList = dataService.searchChartPreviousData(pid);
                JSONObject jsonobject = new JSONObject();
                jsonobject.put("total", displayList.size());
                jsonobject.put("rows", hChartUtil.getCurrentPreviousData(displayList));
//                    System.out.println("rows::"+hChartUtil.getHistoryData(listList));
                PrintWriter out = response.getWriter();
                System.out.println("jsonobject:"+jsonobject);
                out.write(jsonobject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if("/servlet/SearchChartCurrentDataServlet".equals(path)) {
            System.out.println("实时更新(5s)数据展示");
            Long pid=Long.valueOf(request.getParameter("pid"));
            HChartUtil hChartUtil = new HChartUtil();
            DataDisplay dataDisplay = null;
            //查询条件:pid不为空
            try {
                dataDisplay =dataService.searchCurrentDataByPid(pid);
                JSONObject jsonobject = new JSONObject();
                System.out.println(dataDisplay);
                if (dataDisplay!=null){
                    System.out.println("有数据???");
                    jsonobject.put("total", 1);
                    jsonobject.put("rows", hChartUtil.getCurrentData(dataDisplay));
                }else{
                    jsonobject.put("total", 0);
                }
                PrintWriter out = response.getWriter();
                System.out.print("jsonobject:"+jsonobject);
                out.write(jsonobject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
