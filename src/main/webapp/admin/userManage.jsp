<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="common.jsp" %>
<script type="text/javascript">
	var url;
	function searchUser(){
		$('#dg').datagrid('load',{
			"userName":$("#s_userName").val()
		});
	}
	
	function openUserAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加用户信息");
		url="${pageContext.request.contextPath}/admin/user/saveUser.do";
	}
	
	function saveUser(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result = eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#userName").val("");
		$("#password").val("");
		$("#email").val("");
		$("#signature").val("");
		$("#rank").val(-1);
	}
	
	function closeUserDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openUserModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑用户信息");
		/* $("#userName").val(row.userName);
		$("#password").val(row.password);
		$("#email").val(row.email);
		$("#signature").val(row.signature); */
		$("#fm").form("load",row);
		url="${pageContext.request.contextPath}/admin/user/saveUser.do?id="+row.id;
	}
	
	function deleteUser(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/admin/user/delUser.do",{ids:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","数据已成功删除！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
	function formatRank(val,row){
		if(val==0){
			return "管理员";
		}else{
			return "普通用户";
		}
	}
	function formatImage(val,row){
		return "<img src='"+val+"' width='50' height='50' />";
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="用户管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/admin/user/list.do" fit="true"
 toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="50" align="center">编号</th>
    		<th field="userName" width="100" align="center">用户名</th>
    		<th field="password" width="100" align="center">用户密码</th>
    		<th field="email" width="100" align="center">邮箱</th>
    		<th field="rank" width="100" align="center" formatter="formatRank">用户级别</th>
    		<th field="image" width="100" align="center" formatter="formatImage">用户头像</th>
    		<th field="signature" width="100" align="center">签名</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;用户名：&nbsp;<input type="text" name="userName"  id="s_userName" value="${s_user.userName }" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 500px;height: 300px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post" enctype="multipart/form-data">
  	<table cellspacing="8px;">
  		<tr>
  			<td>用户名：</td>
  			<td><input type="text" id="userName" name="userName" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>密码：</td>
  			<td><input type="text" id="password" name="password" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>邮件：</td>
  			<td><input type="text" id="email" name="email" class="easyui-validatebox"  validType="email" required="true"/></td>
  		</tr>
  		<tr>
  			<td>级别：</td>
  			<td>
  				<select id="rank" name="rank">
  					<option value="-1">请选择..</option>
  					<option value="0">管理员</option>
  					<option value="1">普通用户</option>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<td>用户图片:</td>
  			<td><input type="file" id="proPic" name="proPic" /></td>
  		</tr>
  		<tr>
  			<td>签名：</td>
  			<td><input type="text" id="signature" name="signature" class="easyui-validatebox" required="true" style="width: 300px;"/></td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeUserDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>