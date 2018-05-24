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
	function searchMessage(){
		$("#dg").datagrid('load',{
			"msgName":$("#MessageName").val(),
			"content":$("#MessageContent").val()
		});
	}
	function saveMessage(){
		//alert($("#replyContent").val());
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
		$("#cid").val("");
		$("#content").val("");
		$("#replyContent").val("");
		$("#createTime").val("");
		$("#msgName").val("");
	}
	
	function closeMessageDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openMessageModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","回复留言");
		$("#content").val(row.content);
		url="${pageContext.request.contextPath}/admin/message/reply.do?id="+row.id;
	}
	
	function deleteMessage(){
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
				$.post("${pageContext.request.contextPath}/admin/message/delMessage.do",{ids:ids},function(result){
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
	function conversionReply(val){
		if(val=='' || val==null){
			return "<a href='javascript:openMessageModifyDialog()' class='easyui-linkbutton' iconCls='icon-edit' plain='true'>回复</a>";
		}
		return val;
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="留言管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/admin/message/list.do" fit="true"
 toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="20" align="center">编号</th>
    		<th field="content" width="50" align="center">留言内容</th>
    		<th field="msgName" width="50" align="center">留言人</th>
    		<th field="email" width="50" align="center">留言邮箱</th>
    		<th field="createTime" width="50" align="center">留言时间</th>
    		<th field="replyTime" width="20" align="center">回复时间</th>
    		<th field="replyContent" width="20" align="center" formatter="conversionReply">博主回复</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:deleteMessage()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		留言人:<input type="text" id="MessageName"  size="20" onkeydown="if(event.keyCode==13) searchMessage()"/>
		留言内容:<input type="text" id="MessageContent"  size="20" onkeydown="if(event.keyCode==13) searchMessage()"/>
		<a href="javascript:searchMessage()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 300px;height: 250px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="8px;">
  		<tr>
  			<td>留言内容:</td>
  			<td>
  				<input type="text" id="content" name="content" class="easyui-validatebox" required="true"/>
  			</td>
  		</tr>
  		<tr>
  			<td>回复内容:</td>
  			<td>
  				<textarea rows="5" cols="20" name="replyContent" id="replyContent"></textarea>
			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveMessage()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeMessageDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>