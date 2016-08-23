<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.donglu.carpark.model.storemodel.SessionInfo" %>
<%
	String contextPath = request.getContextPath();
	String serverName=request.getServerName();
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			/* sy.contextPath + '/base/syuser!grid.sy' */
			url : '<%=contextPath%>/StoreServlet?method=searchPay',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [  {
				width : '80',
				title : '操作员',
				field : 'operaName',
				sortable : false
			} ] ],
			columns : [ [ {
				width : '80',
				title : '充值类型',
				field : 'payType',
				sortable : false
			}, {
				width : '100',
				title : '充值金额(元)',
				field : 'payMoney',
				sortable : false
			}, {
				width : '120',
				title : '账户增加金额(元)',
				field : 'freeMoney',
				sortable : false,
			},  {
				width : '120',
				title : '账户增加时间(小时)',
				field : 'freeHours',
			}, {
				title : '充值时间',
				field : 'createTime',
				width : '150',
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', sy.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>操作员</td>
								<td><input name="searchOperaName" style="width: 80px;" /></td>
								<td>创建时间</td>
								<td><input name="searchStartTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" />-<input name="searchEndTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>