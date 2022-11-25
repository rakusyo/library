<%@page import="javax.swing.plaf.synth.SynthOptionPaneUI"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="qimo.*"
    import="java.sql.*"%>
   
<script type="text/javascript">
function beforeSubmit(form){
if(form.bid.value==''){
alert('书籍编号不能为空！');
form.bid.focus();
return false;
}
if(form.bname.value==''){
alert('密码不能为空！');
form.bname.focus();
return false;
}
if(form.bwriter.value==''){
	alert('作者栏不能为空！');
	form.bname.focus();
	return false;
	}
	
if(form.have.value==''){
	alert('上架数量不能为空！');
	form.have.focus();
	return false;
	}
if(isNaN(form.have.value)){
	alert('上架数量只能填数字！');
	form.have.focus();
	return false;
	}
return true;
}
</script>
   
   
   
    
<%

Book b=new Book();
User u=(User)session.getAttribute("user"); 
if(u==null||!u.getSqlk().equals("2"))pageContext.forward("index.jsp");
String ab = request.getParameter("aa");
if(ab!=null){
	String sql="select  *   FROM [l1].[dbo].[t_book1] where "+" [Book_id]="+"'"+ab+"'";
	Connection con=Db.getConnection();
	PreparedStatement ps1=con.prepareStatement(sql);
	 ResultSet rs1=ps1.executeQuery();
	rs1.next();

	b.setBname(rs1.getString("Book_name"));
	b.setBwriter(rs1.getString("Book_writer"));
	b.setBid(rs1.getString("Book_id"));
	b.setTname(rs1.getString("Type_name"));
	
}

%>    
    
    
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>上架页面</title>
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
                <li><a class="on" href="index.jsp">上架新图书</a></li>
              
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
                <h1 style="padding-left:20px">图书上架 </h1>
                
            </div>
        	
         <div class="config-items">
                 
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
               <%if(ab==null){}
               
               else {%>  
                 
                 
                 
                 
                    <div class="result-content" >
                    <form action="borServlet" method="post" onSubmit="return beforeSubmit(this);">
                        <table width="86%" class="insert-tab">
                        	
                            <tr>
                            <th width="15%">书籍编号</th>
                                <td ><span class="res-info" size="85" ><%=b.getBid() %> <input type="hidden" name="bid" value="<%=b.getBid() %>"></span></td>
                            </tr>
                               
                              <input type="hidden" name="sid" value="<%=u.getId() %>"> 
                              <input type="hidden" name="bflag" value=1>   
                               <tr>
                            <th width="15%">书籍类型</th>
                                <td><span class="res-info" size="85"><%=b.getTname() %></span></td>
                            </tr>
                               
                                 <tr>
                            <th width="15%">书名</th>
                                <td><span class="res-info" size="85"><%=b.getBname() %></span></td>
                            </tr>
                               
                                 <tr>
                            <th width="15%">书籍作者</th>
                                <td><span class="res-info" size="85"><%=b.getBwriter() %></span></td>
                            </tr>

                               
                                <tr>
                                    <th>出借时间</th>
                                    <td>
                                    <select class="dx" name="now" >
                                    
        							<option value =1>1个月</option>
        							<option value =3>3个月</option>	
       								 </select>
                                    </td>
                                </tr>
                       
                               
                                
                          		<tr>
                                    <th>出是否续借</th>
                                    <td>
                                    <select class="dx" name="xj" >
                                    
        							<option value ="是">是</option>
        							<option value ="否">否</option>	
       								 </select>
                                    </td>
                                </tr>
                              

		
                                <tr>
                                    <th></th>
                                    <td>
                                        
                                        <input type="submit" value="提交" class="btn btn-primary btn6 mr10">
                                        <input type="button" value="返回" onclick="history.go(-1)" class="btn btn6">
                                    </td>
                              
                                </tr>
                                <tr>
                                 <th></th>
                                <th>
  									<a style="text-align:left;">
  									<% request.setCharacterEncoding("utf-8");
  									   String errMsg=(String)request.getAttribute("errMsgb");
   									   if(errMsg!=null&&!"".equals(errMsg)){
	   								   out.println("<b style='color:red;'>"+errMsg+"</b>");
                                       }%>				</a>
  									</th>
                                </tr>
                        </table>
                       </form>
                    </div><%} %>
                </div>
        
        
        
        </div>
       
       
   
</div>

</body>
</html>