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
import net.sf.json.JSONObject;
import service.AdminRegionService;

/**
 * Servlet implementation class Oid_adminregion
 */
@WebServlet("/Oid_adminregion")
public class Oid_adminregion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Oid_adminregion() {
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

		Long oid = Long.valueOf(request.getParameter("oid"));
//			 Long oid =(Long) request.getAttribute("position");
		System.out.println("1:::"+oid);
		AdminRegionService adminRegionService = new AdminRegionService();
		List<AdminRegion>adminRegions=new ArrayList<AdminRegion>();
		try {

			adminRegions=adminRegionService.searchAdminRegionByOid((long)oid);
			duixiang.setAdminRegions(adminRegions);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"此组织管辖的地区是"+json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2:::");
	}

}
