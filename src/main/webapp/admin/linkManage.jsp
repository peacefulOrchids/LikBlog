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
	function searchLink(){
		$("#dg").datagrid('load',{
			"linkname":$("#LinkName").val()
		});
	}
	function openLinkAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加标签");
		url="${pageContext.request.contextPath}/admin/link/saveLink.do";
	}
	
	function saveLink(){
		$("#fm").form("submit",{
			url:url,
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
		$("#linkname").val("");	
		$("#linkurl").val("");	
	}
	
	function closeLinkDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openLinkModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑商品信息");
		$("#fm").form("load",row);
		//alert(row.content);
		url="${pageContext.request.contextPath}/admin/link/saveLink.do?id="+row.id;
	}
	
	function deleteLink(){
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
				$.post("${pageContext.request.contextPath}/admin/link/delLink.do",{ids:ids},function(result){
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
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="标签管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/admin/link/list.do" fit="true"
 toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="50" align="center">编号</th>
    		<th field="linkname" width="50" align="center">链接名字</th>
    		<th field="linkurl" width="50" align="center">url</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openLinkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openLinkModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteLink()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;标签名字：&nbsp;<input type="text" name="Link.name" value="${s_Link.linkname }" id="LinkName"  size="20" onkeydown="if(event.keyCode==13) searchLink()"/>
		<a href="javascript:searchLink()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 330px;height: 200px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="8px;">
  		<tr>
  			<td>链接名字:</td>
  			<td><input type="text" id="linkname" name="linkname" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>url:</td>
  			<td><input type="text" id="linkurl" value="http://" name="linkurl" class="easyui-validatebox" required="true"/></td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveLink()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeLinkDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>