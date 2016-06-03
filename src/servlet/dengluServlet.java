package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.Gson;

import bean.User;
import service.UserService;

/**
 * Servlet implementation class dengluServlet
 */
@WebServlet("/dengluServlet")
public class dengluServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public dengluServlet() {
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

		String name=request.getParameter("name");//����ӷ�������nameȡ��������name
		String mima=request.getParameter("mima");




		User currentUser=new User();
		currentUser.setPassword(mima);
		currentUser.setUsername(name);

		UserService userService=new UserService();
		try {
			User user=userService.verifyUser(currentUser);

			if (user == null) {
				Gson gson=new Gson();
				String json=gson.toJson(user);

				PrintWriter out = response.getWriter();
				out.write(json);

				System.out.println("û�в鵽�û�"+json);
			} else {

				Gson gson=new Gson();
				String json=gson.toJson(user);

				PrintWriter out = response.getWriter();
				out.write(json);


				System.out.println("�鵽�û���"+json);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter dayin=response.getWriter();//��ӡ������servlet��׺��β�ĺ��
		dayin.println("name="+name+" mima="+mima);




		System.out.println("name:"+name);
		System.out.println("mima:"+mima);



	}

}
