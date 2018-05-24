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
	function searchComment(){
		$("#dg").datagrid('load',{
			"cname":$("#CommentName").val(),
			"content":$("#CommentContent").val(),
			"log.title":$("#logTitle").val()
		});
	}
	function deleteComment(){
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
				$.post("${pageContext.request.contextPath}/admin/comment/delComment.do",{ids:ids},function(result){
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
		if(val==0 || val==null){
			return "无父评论";
		}
		return "父评论id:"+val;
	}
	function conversionLogTitle(val,row){
		return "<a href=\"${pageContext.request.contextPath}/log/info/"+row.log.id+".html\" target='_blank' class=\"btn btn-warning\">"+row.log.title+"</a>";
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="评论管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/admin/comment/list.do" fit="true"
 toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="20" align="center">编号</th>
    		<th field="content" width="50" align="center">评论内容</th>
    		<th field="cname" width="50" align="center">评论人</th>
    		<th field="aa" width="50" formatter="conversionLogTitle" align="center">评论的日志标题</th>
    		<th field="fid" width="50" align="center" formatter="conversionReply">父评论编号</th>
    		<th field="email" width="50" align="center">评论邮箱</th>
    		<th field="createTime" width="50" align="center">评论时间</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:deleteComment()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		评论人:<input type="text" id="CommentName"  size="20" onkeydown="if(event.keyCode==13) searchComment()"/>
		评论内容:<input type="text" id="CommentContent"  size="20" onkeydown="if(event.keyCode==13) searchComment()"/>
		评论的日志主题:<input type="text" id="logTitle"  size="50" onkeydown="if(event.keyCode==13) searchComment()"/>
		<a href="javascript:searchComment()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>
<div id="dlg-buttons">
	<a href="javascript:saveComment()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeCommentDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>