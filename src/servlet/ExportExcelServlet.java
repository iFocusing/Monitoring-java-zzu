package servlet;

import bean.Data;
import bean.DataDisplay;
import bean.Pole;
import jxl.write.WriteException;
import service.DataService;
import service.PoleService;
import util.ExportExcelUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huojingjing on 16/5/4.
 */
@WebServlet(name = "ExportExcelServlet" ,urlPatterns = {"/servlet/ExportDataToExcel"})
public class ExportExcelServlet extends HttpServlet {
    DataService dataService=new DataService();
    PoleService poleService=new PoleService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path=request.getServletPath();
        System.out.println("servlet的路径!!!!!!!!!!!!!"+path);

        /*
        * 历史数据导出excel;
        */
        if("/servlet/ExportDataToExcel".equals(path)) {
            ExportExcelUtil eutil=new ExportExcelUtil();
            List<String> lList=new ArrayList<String>();
            List<List<DataDisplay>> listList = null;
            response.setContentType("application/DOWLOAD");
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
            System.out.println("1111111111111111");
            System.out.println("组织"+request.getParameter("oid")+"地区"+request.getParameter("aid")+"线路"+request.getParameter("lid"));

            System.out.println(btime);
            System.out.println(request.getParameter("startTime")+request.getParameter("endTime"));
            String[] pids = new String[10];
            //查询条件:pid不为空,时间不为空
            if(bpid && btime){
                try {
                    String startTime = request.getParameter("startTime");
                    String endTime = request.getParameter("endTime");
                    String pid = request.getParameter("pid");
                    pids[0] = pid;
                    lList.add(pid);
                    listList = dataService.searchChartData(pids, startTime, endTime);
                    System.out.println(listList);
                    File exportFile =eutil.createExcelFile();
                    System.out.println(exportFile);
                    eutil.exportDataToExcel(exportFile, lList, listList);
                    eutil.download(exportFile, response);

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

                    List<Pole> poleList=poleService.searchPoleByLine(oid,aid,lid);
                    for(int i=0;i<poleList.size();i++){
                        lList.add(poleList.get(i).getPid()+"");
                    }
                    File exportFile =eutil.createExcelFile();
                    eutil.exportDataToExcel(exportFile, lList, listList);
                    eutil.download(exportFile, response);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
