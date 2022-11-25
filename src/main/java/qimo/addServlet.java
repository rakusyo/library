package qimo;

import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.jasper.tagplugins.jstl.core.Param;

import qimo.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/addServlet" }, 
		initParams = { 
				@WebInitParam(name = "code", value = "utf-8")
		})
public class addServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addServlet() {
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
		int ret=-5;
		int reh=0;
		String reha=null;
		int ha=Integer.parseInt(request.getParameter("have"));
		long id=System.currentTimeMillis();//获取时间戳
		java.util.Date d=new java.util.Date();
        Date date=new Date(d.getTime());
		boolean re=false;
		String sql="select  *   FROM [l1].[dbo].[t_book1] where "+" [Book_id]="+"'"+request.getParameter("bid")+"'"
				+"or [Book_name]="+"'"+request.getParameter("bname")+"'";
		try {
		Connection con=Db.getConnection();
		PreparedStatement ps1=con.prepareStatement(sql);
		ResultSet rs1=ps1.executeQuery();
		 re=rs1.next();
		 if(re) {
		 reh=rs1.getInt("have"); 
		 reha=rs1.getString("have");}
		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-3;
		}
		
		if(re) {
			if(reh+ha>=0) {
			try 
				 {
				 sql="update  t_book1  set [have]=have+"+"'"+request.getParameter("have")+"'"
				 		+" "+ " where "+" [Book_id]="+"'"+request.getParameter("bid")+"'"
							+"or [Book_name]="+"'"+request.getParameter("bname")+"'";;
				Connection con=Db.getConnection();
				PreparedStatement ps=con.prepareStatement(sql);
				
				int rs=ps.executeUpdate();
				if(rs>0) {	
					Db.close(ps, con);
					ret=1;
				}else {
					Db.close (ps, con);
					ret=-4;
					}
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ret=-3;
				}
			}
			
			else if(reh+ha<0) {
				ret=-6;
			}
			
			
			else {ret=-5;}
		}
		
		
		
		
		
		
		
		else {
		Book b=new Book();
		b.setBid(request.getParameter("bid"));
		b.setTid(request.getParameter("key"));
		b.setTname();
		b.setBname(request.getParameter("bname"));
		b.setBwriter(request.getParameter("bwriter"));
		b.setBcom(request.getParameter("bcom"));
		b.setBdate(date);
		b.setBrief(request.getParameter("brief"));
		b.setPrice(request.getParameter("price"));
		b.setHave(request.getParameter("have"));
		
		ret=b.login();
		}
		String url=null;
		if(ret==0) {//成功添加
			
			url="add.jsp";
			request.setAttribute("errMsga","增加新书"+request.getParameter("bname"));
		}else if(ret==-1) {
			request.setAttribute("errMsga","增加新书失败");
			url="add.jsp";
		}else if(ret==1) {
			request.setAttribute("errMsga",request.getParameter("bid")+"库存成功"+request.getParameter("have")+"本");
			url="add.jsp";
		}else if(ret==-4) {
			request.setAttribute("errMsga","已存在图书"+request.getParameter("bid")+"改为增加库存"+request.getParameter("have"));
			url="add.jsp";
		}else if(ret==-3) {
			request.setAttribute("errMsga","增加库存报错");
			url="add.jsp";
		}else if(ret==-5) {
			request.setAttribute("errMsga","书库存为0");
			url="add.jsp";
		}else if(ret==-6) {
			request.setAttribute("errMsga","库存有"+reh+"库存不足"+ha);
			url="add.jsp";
		}else {
			request.setAttribute("errMsga","系统出错，请稍后再试");
			url="add.jsp";
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
