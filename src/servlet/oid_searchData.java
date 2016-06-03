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
 * Servlet implementation class oid_searchData
 */
@WebServlet("/oid_searchData")
public class oid_searchData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DataService dataService=new DataService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public oid_searchData() {
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

		long oid = Long.valueOf(request.getParameter("oid"));


		ArrayList<DataDisplay> dataList=new ArrayList<DataDisplay>();
		try
		{

			dataList=(ArrayList<DataDisplay>) dataService.searchAllData(oid);
			duixiang.setDataDisplays(dataList);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"此组织所有时间内的数据："+json);
		} catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}


	}
}


