package qimo;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	
	public static Connection getConnection() {
		try {
			String URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=l1";
		     Class.forName("com.mic"+ "rosoft.sqlserver.jdbc.SQLServerDriver");
			String username="xls";
			String password="12345";
			Connection con=DriverManager.getConnection(URL, username, password);
			return con;
		} catch (ClassNotFoundException e) {
			System.out.println("11");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("11");
			return null;
		}
		
	}
	
	
	public static ResultSet getResultset(Connection con,String sql) {
		try {
			PreparedStatement ps1=con.prepareStatement(sql);
			ResultSet rs1=ps1.executeQuery();
			
			return rs1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void close(Statement stat, ResultSet rs) {
		try {
			stat.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stat, Connection con) {
		try {
			stat.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs,Statement stat, Connection con) {
		try {
			rs.close();
			stat.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
