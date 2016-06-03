package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.DataDisplay;
import service.DataService;

/**
 * Servlet implementation class pid_searchData
 */
@WebServlet("/pid_searchData")
public class pid_searchData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataService dataService=new DataService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public pid_searchData() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Result duixiang=new Result();
		duixiang.setResult(1);

		long pid = Long.valueOf(request.getParameter("pid"));
		String start = request.getParameter("startTime");
		String end = request.getParameter("endTime");
//		

		ArrayList<DataDisplay> dataList=new ArrayList<DataDisplay>();
		try
		{





			Timestamp start1 = Timestamp.valueOf(start);
			Timestamp end1 = Timestamp.valueOf(end);

			dataList=(ArrayList<DataDisplay>) dataService.searchDataByPid(pid,start1,end1);
			duixiang.setDataDisplays(dataList);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"此线杆的数据在此时间段内是："+json);
		} catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}


	}

}


