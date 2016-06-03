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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import bean.Organization;
import bean.User;
import dao.OrganizationDao;
import service.OrganizationService;
import service.UserService;

/**
 * Servlet implementation class organazationQuanbu
 */
@WebServlet("/organazationQuanbu")
public class organazationQuanbu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public organazationQuanbu() {
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
		OrganizationService organizationService=new OrganizationService();
		List<Organization>organizations=new ArrayList<Organization>();
		try
		{
			organizations=organizationService.QuanOrganization();
			duixiang.setOrganizations(organizations);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println("所有组织结构为"+json);
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}




	}

}
