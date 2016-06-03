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

import bean.Line;
import bean.Pole;
import service.LineService;
import service.PoleService;

/**
 * Servlet implementation class oid_aid_lid_searchpole
 */
@WebServlet("/oid_aid_lid_searchpole")
public class oid_aid_lid_searchpole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public oid_aid_lid_searchpole() {
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
		// TODO Auto-generated method stub

		Result duixiang=new Result();
		duixiang.setResult(1);

		Long lid= Long.valueOf(request.getParameter("lid"));
		Long oid= Long.valueOf(request.getParameter("oid"));
		Long aid= Long.valueOf(request.getParameter("aid"));

		PoleService poleService=new PoleService();
		List<Pole>poles=new ArrayList<Pole>();

		try {
			poles=poleService.searchPoleByLine(oid,aid,lid);
			duixiang.setPoles(poles);


			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"此线路拥有的线杆有"+json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
