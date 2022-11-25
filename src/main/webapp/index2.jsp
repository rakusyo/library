<%@page import="javax.swing.plaf.synth.SynthOptionPaneUI"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="qimo.*"
    import="java.sql.*"%>
    
<%
String sql=(String)request.getAttribute("sql");
String sql1=(String)request.getAttribute("sql1");
Connection con=Db.getConnection();
PreparedStatement ps1=con.prepareStatement(sql);
ResultSet rs1=ps1.executeQuery();
PreparedStatement ps2=con.prepareStatement(sql1);
ResultSet rs2=ps2.executeQuery();
rs2.next();
User u=(User)session.getAttribute("user"); 
Book b=new Book();
%>    
    
    
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>搜索结果</title>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <script type="text/javascript" src="js/libs/modernizr.min.js"></script>
</head>
<body>
<div class="topbar-wrap white">
    <div class="topbar-inner clearfix">
        <div class="topbar-logo-wrap clearfix">
            <h1 class="topbar-logo none"><a href="index.html" class="navbar-brand">后台管理</a></h1>
            <ul class="navbar-list clearfix">
                <li><a class="on" href="index.jsp">首页</a></li>
              
            </ul>
        </div>
        <div class="top-info-wrap">
            <ul class="top-info-list clearfix">
                 <li ><a href="#"> <%if(u==null)out.print("<a href=\" login.jsp \">登录</a>"); else out.print(u.getName()); %></a></li>
                <li <%if(u==null) out.print("hidden");%>><a href="#"><%if(u==null);else {if(u.getSqlk().equals("1"))out.print("管理员"); if(u.getSqlk().equals("2"))out.print("普通用户");} %></a></li>
                <li <%if(u==null) out.print("hidden");%>><a href="outlogin.jsp">退出登录</a></li>
            </ul>
        </div>
    </div>
</div>
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
   
    
    
    
    
    <div class="main-wrap">
        <div class="crumb-wrap">
            <div class="crumb-list"><i class="icon-font">&#xe06b;</i><span>12345</span></div>
        </div>
        <div class="result-wrap">
            <div class="result-title">
                <h1>搜索用</h1>
            </div>
           
            <div class="container">

    <form action="select" class="parent" >

        <input type="text" name="te">
 <select class="dx" name="key">
        <option value ="Book_name">书名</option>
  <option value ="Type_name">类型</option>
  <option value="Book_writer">作者</option>
  <option value="Book_company">出版社</option>
        </select>
        <input type="submit" value="搜索">
       

    </form>

</div>

           
           
           
            </div>
        </div>
        <div class="result-wrap">
            <div class="result-title">
                <h1 style="padding-left:20px" 
                <%int a=rs2.getInt(1);if(a<=0) out.print("hidden"); %>>
                已搜索到<%=a %>条记录</h1>
                <% Db.close(ps2, rs2); %>
            </div>
           <div class="result-content">
                    <table class="result-tab" width="86%">
                        <tr>
                            
                            <th>书籍ID</th>
                            <th>书名</th>
                            <th>类型</th>
                            <th>作者</th>
                            <th>出版社</th>
                            <th>库存</th>
                           
                            <th>简介</th>
                        <th <%if(u==null) out.print("hidden");%>>操作</th>
                        </tr>
                        <% while(rs1.next()){ %>
                        <tr>
                            <td><%=rs1.getString("Book_id") %></td>
                            <td><%=rs1.getString("Book_name") %></td>
                            <td ><%=rs1.getString("Type_name") %></td>
                            <td ><%=rs1.getString("Book_writer") %></td>
                            <td ><%=rs1.getString("Book_company") %></td>
                            <td ><%=rs1.getString("have") %></td>
                            <td ><%=rs1.getString("Book_brief") %></td>
                            <td <%if(u==null) out.print("hidden");else{%>> 
                            <%if(u.getSqlk().equals("2")){ %>
                                <a href="brief.jsp?aa=<%=rs1.getString("Book_id")%>">查看详情</a>
                           <%if(b.retrenew(rs1.getString("Book_id"), Integer.parseInt(u.getId())).equals("是")&b.retex(rs1.getString("Book_id"), Integer.parseInt(u.getId())).equals("否")){ %> <a href="borServlet?bid=<%=rs1.getString("Book_id") %>&sid=<%=u.getId() %>&bflag=2">续借</a><%} %>
                             <%if(b.retex(rs1.getString("Book_id"), Integer.parseInt(u.getId())).equals("否")){ %> <a href="borServlet?bid=<%=rs1.getString("Book_id") %>&sid=<%=u.getId() %>&bflag=0">还书</a><%}else{ %><a href="borrow.jsp?aa=<%=rs1.getString("Book_id") %>">借书</a><%} %>
                                <%} %>
                              <%if(u.getSqlk().equals("1")){ %>
                                <a class="link-update" href="addServlet?bid=<%=rs1.getString("Book_id") %>&have=-1">下架</a>
                                <a class="link-del" href="addServlet?bid=<%=rs1.getString("Book_id") %>&have=1">上架</a></td><%} %>
                                
                                
                                <%} %>
                        </tr>
                        
                     <%} %>
                    </table>
                    
                </div>
        </div>
       
   
</div>
<% Db.close(rs1, ps1, con); %>
</body>
</html>