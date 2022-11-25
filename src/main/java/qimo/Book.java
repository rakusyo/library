package qimo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.print.DocFlavor.STRING;
import javax.swing.text.Document;

import qimo.Db;


public class Book {
	private String bid;
	private String tid;
	private String tname;
	private String bname;
	private String bwriter;
	private String bcom="";
	private Date bdate;
	private String brief="";
	private String price="";
	private String have="";
	public Book() {
		
	}
	
	public String getBid() {

		return bid;
	}




	public void setBid(String bid) {

		this.bid = bid;
	}




	public String getTid() {
		return tid;
	}




	public void setTid(String tid) {
		this.tid = tid;
	}




	public String getTname() {
		return tname;
	}




	public void setTname(String tname) {
		this.tname = tname;
	}

public void setTname(){
		
		String sql="select  *   FROM [l1].[dbo].[t_book1] where "+" [Type_id]="+"'"+this.tid+"'";
		try {
		Connection con=Db.getConnection();
		PreparedStatement ps1=con.prepareStatement(sql);
		ResultSet rs1=ps1.executeQuery();
		 rs1.next();
		 this.tname = rs1.getString("Type_name");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}}


	public String getBname() {
		return bname;
	}




	public void setBname(String bname) {
		this.bname = bname;
	}




	public String getBwriter() {
		return bwriter;
	}




	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}




	public String getBcom() {
		return bcom;
	}




	public void setBcom(String bcom) {
		this.bcom = bcom;
	}




	public Date getBdate() {
		return bdate;
	}




	public void setBdate(Date date) {
		this.bdate = date;
	}




	public String getBrief() {
		return brief;
	}




	public void setBrief(String brief) {
		this.brief = brief;
	}




	public String getPrice() {
		return price;
	}




	public void setPrice(String price) {
		this.price = price;
	}




	public String getHave() {
		return have;
	}




	public void setHave(String have) {
		this.have = have;
	}

 

	


	public int login() {//入库新书
		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		String sql="";
		
		
		 sql ="insert into t_book1 values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, this.bid);
			ps.setString(2, this.tid);
			ps.setString(3, this.tname);
			ps.setString(4, this.bname);
			ps.setString(5, this.bwriter);
			ps.setString(6, this.bcom);
			ps.setDate(7, this.bdate);
			ps.setString(8, this.brief);
			ps.setString(9, this.price);
			ps.setString(10, this.have);
			
			int rs=ps.executeUpdate();
			if(rs>0) {
			
				
				Db.close(ps, con);
				ret=0;
			}else {
				Db.close (ps, con);
				ret=-1;//用户名或密码不对		
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-2;
		}
		
		return ret;
	}
	
	public int bortest(String bid,int sid) {//借书测试，检测是否借过该书，借过则不允许借出
		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select * from [t_borrow_student1] where "+" [Book_id]= '"+bid+"'"+"and"+" [sid]= '"+sid+"'"+"and"+" Expired= '否'";
		
		
		 try {
				 con=Db.getConnection();
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				
				 if(rs1.next()) {ret=-4;}
				 else {ret=3;}
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ret=-5;
				}
		return ret;
	}
	
	public int bor(String bid,int sid,Date now,Date r,String xj) {//借书功能，插入借书记录



		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		String sql="";
		int a=bortest(bid, sid);
		int b=havetest(bid);
		if(a!=-4&&b!=-3) {
		sql ="insert into [t_borrow_student1] values(?,?,?,?,?,?)";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, bid);
			ps.setInt(2, sid);
			ps.setDate(3,now);
			ps.setDate(4, r);
			ps.setString(5, xj);
			ps.setString(6, "否");
			
			
			int rs=ps.executeUpdate();
			if(rs>0) {
				sql="exec dbo.upbornumber";//自动更新借书数量
				 ps=con.prepareStatement(sql);
				 ps.execute();
				
				Db.close(ps, con);
				ret=0;
			}else {
				Db.close (ps, con);
				ret=-1;//用户名或密码不对		
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-2;
		}}
		else if(a==-4&&b!=-3) {ret=a;}
		else if(a!=-4&&b==-3)ret=b; 
		
		return ret;
	}
	
	
	public int havetest(String bid) {//检测库存，为0不允许借出



		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select have from [l1].[dbo].[t_book1] where "+" [Book_id]= '"+bid+"'";
		
		
		 try {
				 con=Db.getConnection();
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				rs1.next();
				int a=rs1.getInt("have");
				
				 if(a<1) {ret=-3;}
				 else {ret=3;}
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ret=-5;
				}
		return ret;
	}
	
	public int ahave(String bid,int a) {//更新书籍库存，还书+1，借书-1，通过第二参数，1为借书，0为还书
		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		
		try 
		 {String sql="";
		if(a==1)	
		{sql="update  t_book1  set [have]=have-"+"'"+1+"'"
		 		+" "+ " where "+" [Book_id]="+"'"+bid+"'";}
		else {sql="update  t_book1  set [have]=have+"+"'"+1+"'"
		 		+" "+ " where "+" [Book_id]="+"'"+bid+"'";}
		PreparedStatement ps=con.prepareStatement(sql);
	
		int rs=ps.executeUpdate();
		if(rs>0) {
			sql="exec dbo.upbornumber";//自动更新借书数量
			 ps=con.prepareStatement(sql);
			 ps.execute();
			Db.close(ps, con);
			ret=0;
		}else {
			Db.close (ps, con);
			ret=-1;
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-2;
		}
		
		
		return ret;
	}
	
	public String getbookname(String bid) {//用书籍编号获取书名
		String ret=null;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select Book_name from [l1].[dbo].[t_book1] where "+" [Book_id]= '"+bid+"'";
		
		
		 try {
				 con=Db.getConnection();
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				rs1.next();
				ret=rs1.getString("Book_name");
				
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		return ret;
	}
	
	public int lestime(String bid,int sid){//获取预计还书日-借书日=剩余天数<0判断需交罚款
		int ret=0;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select DATEDIFF(DAY,brotime,rettime)as a from t_borrow_student1 where"+" [Book_id]= '"+bid+"' and "+" sid= '"+sid+"'"+"  order by id  desc";
		
		 try {
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				rs1.next();
				ret=rs1.getInt("a");
				
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		return ret;
	}
	
	public int retbook(String bid,int sid) {//还书，调整库存+标志该条借书记录为已还记录
		int ret=0;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
	
		
		try 
		 {String sql="";
	
		sql="update t_borrow_student1  set Expired="+"'是'"
		 		+" "+ " where "+" [Book_id]="+"'"+bid+"' and "+" sid= '"+sid+"'";
		PreparedStatement ps=con.prepareStatement(sql);
	
		int rs=ps.executeUpdate();
		if(rs>0) {
			ahave(bid, 0);
			sql="exec dbo.upbornumber";//自动更新借书数量
			 ps=con.prepareStatement(sql);
			 ps.execute();
			Db.close(ps, con);
			ret=4;
		}else {
			Db.close (ps, con);
			ret=-7;
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-8;
		}
		
		
		return ret;
	}
	
	public int rebook(String bid,int sid) {//续借功能，仅允许续借1次，且仅允许续借多1个月
int ret=0;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
	
		
		try 
		 {String sql="";
	
		sql="update t_borrow_student1  set renew='否',rettime=DATEADD(MONTH,1,rettime)"
		 		+" "+ " where "+" [Book_id]="+"'"+bid+"' and "+" sid= '"+sid+"'";
		PreparedStatement ps=con.prepareStatement(sql);
	
		int rs=ps.executeUpdate();
		if(rs>0) {
			ahave(bid, 0);
			sql="exec dbo.upbornumber";//自动更新借书数量
			 ps=con.prepareStatement(sql);
			 ps.execute();
			Db.close(ps, con);
			ret=5;
		}else {
			Db.close (ps, con);
			ret=-7;
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-8;
		}
		
		
		return ret;
		
		
	}
	
	public Date retime(String bid,int sid) {//返回特定条件的预定还书时间
		Date ret = null;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select rettime from t_borrow_student1 where"+" [Book_id]= '"+bid+"' and "+" sid= '"+sid+"'";
		
		 try {
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				rs1.next();
				ret=rs1.getDate("rettime");
				
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		return ret;
		
		
		
	}
	
	public String retrenew(String bid,int sid) {//返回特定条件的续借权限
		String ret = null;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select renew from t_borrow_student1 where"+" [Book_id]= '"+bid+"' and "+" sid= '"+sid+"'"+" order by id  desc ";
	
		 try {
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				
				if(rs1.next()) {ret=rs1.getString("renew");}
				else
				ret="否";
				
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		return ret;	
		
	}
	
	public String retex(String bid,int sid) {//返回特定条件的是否已经还书情况
		String ret = null;
		
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		 String sql ="select Expired from t_borrow_student1 where"+" [Book_id]= '"+bid+"' and "+" sid= '"+sid+"'"+" order by id  desc ";

		 try {
				PreparedStatement ps1=con.prepareStatement(sql);
				ResultSet rs1=ps1.executeQuery();
				if(rs1.next())
				{ret=rs1.getString("Expired");
				}
				else ret="是";
				
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		return ret;	
		
	}
	/*
0借书成功
-1借书失败
-2借书SQL出错
-3库存不足，不允许借书
3库存充足允许借书
4还书成功
-4已有同本图书，不允许借书
*/
	
	
		
		
	}
	

