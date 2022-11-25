package qimo;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.text.Document;

import qimo.Db;


public class User {
	private String id;
	private String name;
	private String password;
	private String sex;
	private String telephone;
	private String sqlk;
	public User() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getSqlk() {
		return sqlk;
	}
	public void setSqlk(String string) {
		this.sqlk = string;
	}
	public int login() {
		int ret=-1;
		Connection con=Db.getConnection();
		if(con==null)
			return ret;
		String sql="";
		if("1".equals(sqlk))
		 {sql ="select * from [l1].[dbo].[t_admin1] where Admin_id=? and Admin_pwd=?";
		 try {
				PreparedStatement ps=con.prepareStatement(sql);
				ps.setString(1, this.id);
				ps.setString(2, this.password);
				
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					this.id=rs.getString("Admin_id");
					this.name=rs.getString("Admin_name");
					this.password=rs.getString("Admin_pwd");
					
					
					Db.close(rs, ps, con);
					ret=0;
				}else {
					Db.close(rs, ps, con);
					ret=-1;//用户名或密码不对		
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret=-2;
			}
		 
		 
		 
		 }
		else
		 {sql ="select * from [l1].[dbo].[t_student1] where sid=? and spw=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, this.id);
			ps.setString(2, this.password);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				this.id=rs.getString("sid");
				this.name=rs.getString("sname");
				this.password=rs.getString("spw");
				this.sex=rs.getString("sex");
				this.telephone=rs.getString("phone");
				
				Db.close(rs, ps, con);
				ret=0;
			}else {
				Db.close(rs, ps, con);
				ret=-1;//用户名或密码不对		
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret=-2;
		}}
		
		return ret;
	}
}
