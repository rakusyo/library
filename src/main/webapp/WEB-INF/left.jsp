<%@page import="javax.swing.plaf.synth.SynthOptionPaneUI"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="qimo.*"
    import="java.sql.*"%>
    
<%
String sql="select top 5 *   FROM [l1].[dbo].[t_book1] order by newid()";
String sql1="select count(*)   FROM [l1].[dbo].[t_book1] " ;
Connection con=Db.getConnection();
PreparedStatement ps1=con.prepareStatement(sql);
ResultSet rs1=ps1.executeQuery();
PreparedStatement ps2=con.prepareStatement(sql1);
ResultSet rs2=ps2.executeQuery();
rs2.next();
User u=(User)session.getAttribute("user"); 
%>    
    
    
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <script type="text/javascript" src="js/libs/modernizr.min.js"></script>
</head>
<body>


<div class="container clearfix">
    <div class="sidebar-wrap">
        <div class="sidebar-title">
            <h1>菜单</h1>
        </div>
        <div class="sidebar-content">
            <ul class="sidebar-list">
                <li>
                    <a ><i class="icon-font">&#xe003;</i>常用操作</a>
                    <ul class="sub-menu" <%if(u==null) out.print("hidden");else{%>>
                     <%if(u.getSqlk().equals("1")){ %>
                        <li><a href="add.jsp"><i class="icon-font">&#xe005;</i>添加图书</a></li>
                        <li><a href="usebrief.jsp"><i class="icon-font">&#xe006;</i>查看用户</a></li>
                     <%} %>
                        <%if(u.getSqlk().equals("2")){ %>
                        <li><a href="borrow.jsp"><i class="icon-font">&#xe008;</i>借书</a></li>
                        <li><a href="retbook.jsp"><i class="icon-font">&#xe005;</i>借书历史</a></li>
                        <li><a href="pribrief.jsp?aa=<%=u.getId() %>"><i class="icon-font">&#xe006;</i>个人页面</a></li>
                     <%} %><%} %>

                  
                </li>
             
            </ul>
        </div>
    </div>
   
    
    
    
</div>
<% Db.close(rs1, ps1, con); %>
</body>
</html>