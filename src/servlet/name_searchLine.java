package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.AdminRegion;
import bean.Line;
import dao.AdminRegionDao;
import dao.LineDao;

/**
 * Servlet implementation class name_searchLine
 */
@WebServlet("/name_searchLine")
public class name_searchLine extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public name_searchLine() {
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

		String lin_name=request.getParameter("lin_name");

		LineDao linDao=new LineDao();
		List<Line>lines=new ArrayList<Line>();
		try
		{
			lines=linDao.name_searchLine(lin_name);

			duixiang.setLines(lines);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"此组织管辖的地区是"+json);
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
