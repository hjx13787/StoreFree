<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.donglu.carpark.model.storemodel.SessionInfo" %>
<%
	String contextPath = request.getContextPath();
	String serverName=request.getServerName();
%>
<%	
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitNow = function($dialog, $grid, $pjq) {
		var url;
			/* sy.contextPath + '/base/syuser!save.sy' */
		url = '<%=contextPath%>/StoreServlet?method=add';
		$.post(url, sy.serializeObject($('form')), function(result) {
			parent.sy.progressBar('close');//关闭上传进度条

			if (result.success) {
				$pjq.messager.alert('提示', result.msg, 'info');
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	var submitForm = function($dialog, $grid, $pjq) {
		submitNow($dialog, $grid, $pjq);
	};
	$(function() {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post('<%=contextPath%>/StoreServlet?method=getInOutById&id=<%=id%>', {
				id : $(':input[name="id"]').val()
			}, function(result) {
				$('form').form('load', {
					'plateNo' : result.freePlateNo,
					'freeType': result.freeType,
				});
				parent.$.messager.progress('close');
			}, 'json');
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>优惠基本信息</legend>
			<table class="table" style="width: 100%;">
				 <tr>
					<th>编号</th>
					<td><input name="id" value="" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>车牌号码</th>
					<td><input name="plateNo" value="" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				
				<tr>
					<th>优惠金额(元)</th>
					<td><input name="freeMoney" value="" /></td>
				</tr>
				<tr>
					<th>优惠时间(小时)</th>
					<td><input name="freehours" value="" /></td>
				</tr>
				<tr>
					<th>优惠类型</th>
					<td><select class="easyui-combobox" name="freeType" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="false">优惠</option>
							<option value="true">全免</option>
					</select></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>