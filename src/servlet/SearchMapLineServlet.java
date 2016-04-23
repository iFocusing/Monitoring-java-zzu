package servlet;

import net.sf.json.JSONObject;
import service.DataService;
import service.LineService;
import service.PoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by huojingjing on 16/4/21.
 */
@WebServlet(name = "SearchMapLineServlet", urlPatterns = {"/servlet/SearchMapLineServlet"})
public class SearchMapLineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long oid= Long.valueOf(request.getParameter("oid"));
        PoleService poleService=new PoleService();
        LineService lineService=new LineService();
        DataService dataService=new DataService();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lines",lineService.searchLineInOraganization(oid));
            jsonObject.put("rows",dataService.searchDataMapByOid(oid));
            /*
            * 这里有个问题可以优化一下:就是我只需要返回一个List<List<Pole>>就可以了,
            * 而不需要再定义一个数据展示的bean,也不用在数据库中拼如此麻烦的数据,
            * 因为这里只是用来在地图上显示线路,添加线杆的;
            * 至于线杆的数据是由前台异步加载得到的;(之所以我这样做是因为想要将线路和数据一起加载,这样并不合理;)
            */
            jsonObject.put("total",dataService.searchDataMapByOid(oid).size());
            System.out.println(jsonObject);
            PrintWriter out = response.getWriter();
            out.write(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
