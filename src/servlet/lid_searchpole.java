package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.DataDisplay;
import bean.Pole;
import service.DataService;
import service.PoleService;

/**
 * Servlet implementation class lid_searchpole
 */
@WebServlet("/lid_searchpole")
public class lid_searchpole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public lid_searchpole() {
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
		{
			Result duixiang=new Result();
			duixiang.setResult(1);

			Long lid= Long.valueOf(request.getParameter("lid"));


			PoleService poleService=new PoleService();
			List<Pole>poles=new ArrayList<Pole>();

			try {
				poles=poleService.searchPoleByLine(lid);
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

	}}

