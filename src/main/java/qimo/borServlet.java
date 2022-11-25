package qimo;

import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

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
		urlPatterns = { "/borServlet" }, 
		initParams = { 
				@WebInitParam(name = "code", value = "utf-8")
		})
public class borServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borServlet() {
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
		
		int sid=Integer.parseInt(request.getParameter("sid"));
		String bid=request.getParameter("bid");
		int ret=99;
		Book b=new Book();
		
		int flag=99;
	      flag=Integer.parseInt(request.getParameter("bflag"));
		if(flag==1) {
			long id=System.currentTimeMillis();//��ȡʱ���
			java.util.Date d=new java.util.Date();
			java.util.Date dt1;
	        Date date=new Date(d.getTime());//����ʱ��
	        Calendar rightNow = Calendar.getInstance();
	        rightNow.setTime(date);
			int ha=Integer.parseInt(request.getParameter("now"));//ȷ�Ͻ��ã�1���»���3����
			
			String xj=request.getParameter("xj");//�Ƿ�����
			if(ha>1) {
		        rightNow.add(Calendar.MONTH,3);
				dt1=rightNow.getTime();
			}
			else {
				 rightNow.add(Calendar.MONTH,1);
				 
				 dt1=rightNow.getTime();
			}
			//�����ǽ������������bor�ǽ��麯�����Լ�ȥbook�࿴˵��
			ret=b.bor(bid, sid, date, new Date(dt1.getTime()), xj);}
		else if(flag==0) {ret=b.retbook(bid,sid);}
		else if(flag==2) {ret=b.rebook(bid, sid);}
		else {
			ret=1;
			
		}
		
		
		

		
		
		
		
		
		String url=null;
		if(ret==0) {//�ɹ����
			 b.ahave(bid,1);
			url="borrow.jsp?aa="+bid;
			request.setAttribute("errMsgb","����ɹ�");
		}else if(ret==-1) {
			request.setAttribute("errMsgb","����ʧ��");
			url="borrow.jsp?aa="+bid;
		}else if(ret==-3) {
			request.setAttribute("errMsgb","���Ϊ0���޷�����");
			url="borrow.jsp?aa="+bid;
		}else if(ret==-4) {
			request.setAttribute("errMsgb","�ѽ����ͼ��");
			url="borrow.jsp?aa="+bid;
		}else if(ret==4) {
			request.setAttribute("errMsgc","����ɹ�");
			url="retbook.jsp?aa="+bid;
		}else if(ret==5) {
			request.setAttribute("errMsgc","����ɹ�,�鼮"+b.getbookname(bid)+"����黹ʱ��Ϊ"+b.retime(bid, sid));
			url="retbook.jsp?aa="+bid;
		}else if(ret==-7) {
			request.setAttribute("errMsgc","���������ʧ��");
			url="retbook.jsp?aa="+bid;
		}else if(ret==-8) {
			request.setAttribute("errMsgc","���������SQL����");
			url="retbook.jsp?aa="+bid;
		}else {
			request.setAttribute("errMsgb","ϵͳ�������Ժ�����");
			System.out.print(ret);
			url="retbook.jsp?aa="+bid;
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
