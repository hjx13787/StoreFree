<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.donglu.carpark.model.SessionInfo" %>
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
	var addFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '添加优惠信息',
			url : sy.contextPath + '/securityJsp/base/SyuserForm.jsp',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var editRow=function(){
		var row = $('#grid').datagrid('getSelected');
		if (row){
			if(row.used=='已使用'){
				$.messager.alert('Info',"不能修改已经使用的优惠信息");
			}else{
				var dialog = parent.sy.modalDialog({
					title : '编辑优惠信息',
					url : sy.contextPath + '/securityJsp/base/SyuserForm.jsp?id=' + row.id+'&freePlateNo='+
					row.freePlateNo+'&freeMoney='+row.freeMoney+'&freeHour='+row.freeHour,
					buttons : [ {
						text : '编辑',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
						}
					} ]
				});
			}
		}

	};
	var editFun = function(id) {
		var dialog = parent.sy.modalDialog({
			title : '编辑用户信息',
			url : sy.contextPath + '/securityJsp/base/SyuserForm.jsp?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : '<%=contextPath%>/StoreServlet?method=searchFree',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '100',
				title : '车牌号码',
				field : 'freePlateNo',
				sortable : false
			}, {
				width : '80',
				title : '免费金额(元)',
				field : 'freeMoney',
				sortable : false
			} ] ],
			columns : [ [ {
				width : '100',
				title : '免费时间(小时)',
				field : 'freeHour',
				sortable : false
			}, {
				width : '100',
				title : '免费类型',
				field : 'freeType',
				sortable : false
			},{
				width : '150',
				title : '创建时间',
				field : 'createTime',
				sortable : false
			}, {
				width : '80',
				title : '是否使用',
				field : 'used',
				
			}] ],
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
								<td>是否使用</td>
								<td><select name="searchUsed" class="easyui-combobox" data-options="panelHeight:'auto',editable:false"><option value="">请选择</option>
										<option value="已使用">已使用</option>
										<option value="未使用">未使用</option></select></td>
								<td>创建时间</td>
								<td><input name="searchStartTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" />-<input name="searchEndTime" class="Wdate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="width: 120px;" /></td>
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
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">添加</a></td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="editRow();">编辑</a></td>
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