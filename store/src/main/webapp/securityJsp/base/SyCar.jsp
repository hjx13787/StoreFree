<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.donglu.carpark.model.storemodel.SessionInfo" %>
<%
	String contextPath = request.getContextPath();
	String serverName=request.getServerName();
	SessionInfo info=(SessionInfo)request.getSession().getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var grid;
	var editRow=function(){
		var row = $('#grid').datagrid('getSelected');
		if (row){
				var dialog = parent.sy.modalDialog({
					title : '添加优惠信息',
					url : sy.contextPath + '/securityJsp/base/SyCarForm.jsp?id=' + row.id+'&plateNo='+row.plateNo,
					buttons : [ {
						text : '优惠',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
						}
					} ]
				});
		}

	};
	
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			/* sy.contextPath + '/base/syuser!grid.sy' */
			url : '<%=contextPath%>/StoreServlet?method=searchCarIn',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '100',
				title : '车牌号码',
				field : 'plateNo',
				sortable : false
			}, {
				width : '200',
				title : '进场时间',
				field : 'inTime',
				sortable : false
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
								<td>车牌</td>
								<td><input name="searchPlateNO" style="width: 80px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="grid.datagrid('load',sy.serializeObject($('#searchForm')));">过滤</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置过滤</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="editRow();">优惠</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>