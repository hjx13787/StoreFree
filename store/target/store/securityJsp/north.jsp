<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.donglu.carpark.model.SessionInfo"%>
<%
	String contextPath = request.getContextPath();
	String serverName=request.getServerName();
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<script type="text/javascript" charset="utf-8">
	var logoutFun = function() {
		$.post('http://<%=serverName%>:8899/store/?method=loginout', function(result) {
			location.replace(sy.contextPath + '/loginout.jsp');
		}, 'json');
	};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	<%
		if (sessionInfo != null) {
			out.print("欢迎您，"+sessionInfo.getUserName());
		}
	%>
</div>
<!-- <div style="position: absolute; right: 0px; bottom: 0px;">
	 <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
</div> 
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div> -->