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
import bean.Organization;
import dao.AdminRegionDao;
import dao.OrganizationDao;

/**
 * Servlet implementation class name_searchAdm
 */
@WebServlet("/name_searchAdm")
public class name_searchAdm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public name_searchAdm() {
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

		String adm_name=request.getParameter("adm_name");

		AdminRegionDao admDao=new AdminRegionDao();
		List<AdminRegion>adminRegions=new ArrayList<>();
		try
		{
			adminRegions=admDao.name_searchAdm(adm_name);

			duixiang.setAdminRegions(adminRegions);

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
