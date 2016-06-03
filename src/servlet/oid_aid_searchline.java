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
import net.sf.json.JSONObject;
import service.LineService;

/**
 * Servlet implementation class oid_aid_searchline
 */
@WebServlet("/oid_aid_searchline")
public class oid_aid_searchline extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public oid_aid_searchline() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Result duixiang=new Result();
		duixiang.setResult(1);
//		
		Long oid = Long.valueOf(request.getParameter("oid"));
		Long aid = Long.valueOf(request.getParameter("aid"));
		System.out.println(oid+" maya  "+aid);
		LineService lineService= new LineService();
		List<Line>lines=new ArrayList<Line>();
		try {

			lines=lineService.searchLineInOraganizationAdmin(aid,oid);
			duixiang.setLines(lines);

			Gson gson=new Gson();
			String json=gson.toJson(duixiang);
			PrintWriter out = response.getWriter();
			out.write(json);
			System.out.println(+1+"地区拥有的线路有"+json);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
