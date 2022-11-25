<%@page import="javax.swing.plaf.synth.SynthOptionPaneUI"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="qimo.*"
    import="java.sql.*"%>
    
<%
User u=(User)session.getAttribute("user"); 
Book b=new Book();
String sql="select  *   FROM [l1].[dbo].[t_borrow_student1] where sid='"+u.getId()+"'";
String sql1="select count(*)   FROM [l1].[dbo].[t_borrow_student1] where sid='"+u.getId()+"'" ;
Connection con=Db.getConnection();
PreparedStatement ps1=con.prepareStatement(sql);
ResultSet rs1=ps1.executeQuery();
PreparedStatement ps2=con.prepareStatement(sql1);
ResultSet rs2=ps2.executeQuery();
rs2.next();

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
<div class="topbar-wrap white">
    <div class="topbar-inner clearfix">
        <div class="topbar-logo-wrap clearfix">
            <h1 class="topbar-logo none"><a href="index.html" class="navbar-brand">最初</a></h1>
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
    
    
    
    
  
        <div class="result-title">
                <h1 style="padding-left: 15px;">借书记录</h1>
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
                            
                            <th>书籍编号</th>
                            <th>书名</th>
                            <th>借出时间</th>
                            <th>预定返还时间</th>
                            <th>剩余天数</th>
                            <th>功能</th>
                          
                        </tr>
                        <% while(rs1.next()){ %>
                        <tr>
                            <td><%=rs1.getString("Book_id") %></td>
                         <td><%=b.getbookname(rs1.getString("Book_id")) %></td>
                            <td ><%=rs1.getDate("brotime") %></td>
                            <td ><%=rs1.getDate("rettime") %></td>
                            <td ><% int time=b.lestime(rs1.getString("Book_id"),Integer.parseInt(u.getId()));
                                    out.print(time);%></td>
                            
                            <td>
                             <a href="brief.jsp?aa=<%=rs1.getString("Book_id")%>">查看详情</a>
                           <%if(rs1.getString("renew").equals("是")&rs1.getString("Expired").equals("否")){ %> <a href="borServlet?bid=<%=rs1.getString("Book_id") %>&sid=<%=u.getId() %>&bflag=2">续借</a><%}else %>不可续借
                             <%if(rs1.getString("Expired").equals("否")){ %> <a href="borServlet?bid=<%=rs1.getString("Book_id") %>&sid=<%=u.getId() %>&bflag=0">还书</a><%}else %>已还
                        
                            </td>
                                    
                                    
                            
                            
                        </tr>
                        
                     <%} %>
                     
                      <tr>
                                 <th></th>
                                <th>
  									<a style="text-align:left;">
  									<% request.setCharacterEncoding("utf-8");
  									   String errMsg=(String)request.getAttribute("errMsgc");
   									   if(errMsg!=null&&!"".equals(errMsg)){
	   								   out.println("<b style='color:red;'>"+errMsg+"</b>");
                                       }%>				</a>
  									</th>
                                </tr>
                    </table>
                    
                </div>
        </div>
       
   
</div>
<% Db.close(rs1, ps1, con); %>
</body>
</html>