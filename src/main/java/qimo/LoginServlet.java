package qimo;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import qimo.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/LoginServlet" }, 
		initParams = { 
				@WebInitParam(name = "code", value = "utf-8")
		})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=this.getServletConfig().getInitParameter("code");
		if(code!=null)
			request.setCharacterEncoding(code);
		User u=new User();
		
		u.setId(request.getParameter("id"));
		u.setPassword(request.getParameter("password"));
		u.setSqlk(request.getParameter("key"));
		int ret=u.login();
		String url=null;
		if(ret==0) {//��¼�ɹ�
			request.getSession(true).setAttribute("user", u);
			url="index.jsp";
		}else if(ret==-1) {
			request.setAttribute("errMsg","�û��������벻��");
			url="login.jsp";
		}else {
			request.setAttribute("errMsg","ϵͳ�������Ժ�����");
			url="login.jsp";
		}
		RequestDispatcher rd=request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
