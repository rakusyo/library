 <%@page contentType="text/html; charset=gbk"  %>
  <%@ page import="java.sql.*"%>
   <%@ page import="qimo.*"%>

  <%
  User u=(User)session.getAttribute("user"); 
  session.removeAttribute("user");
  pageContext.forward("index.jsp");
 %>
