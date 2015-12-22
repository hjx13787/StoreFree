<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.donglu.carpark.model.SessionInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <%	
    	SessionInfo info=new SessionInfo();
    	info.setUserName(request.getParameter("userName"));
    	info.setLoginName(request.getParameter("loginName"));
    	info.setStoreName(request.getParameter("storeName"));
   		
    	request.getSession().setAttribute("sessionInfo", info);
    %>
    <script type="text/javascript">
 		window.location.href='<%=path%>/index.jsp'
    </script>
  </body>
</html>
