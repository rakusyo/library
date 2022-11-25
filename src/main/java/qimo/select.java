package qimo;

import java.io.IOException;
import qimo.Db;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class select
 */
@WebServlet(
		urlPatterns = { "/select" }, 
		initParams = { 
				@WebInitParam(name = "code", value = "utf-8")
		}
		)
public class select extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public select() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code=this.getServletConfig().getInitParameter("code");
		String key=request.getParameter("key");
		String te=request.getParameter("te");
		String sql="select top 5 *   FROM [l1].[dbo].[t_book1] where "+" "+key+" like '%"+te+"%'";
		String sql1="select  count(*)   FROM [l1].[dbo].[t_book1] where "+" "+key+" like '%"+te+"%'";
		request.setAttribute("sql1", sql1);
		request.setAttribute("sql", sql);
		
		RequestDispatcher rd=request.getRequestDispatcher("index2.jsp");
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
