<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="common.jsp" %>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js">
</script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
 	var ue = UE.getEditor('content');  
	var url;
	function searchLogs(){
		$("#dg").datagrid('load',{
			"title":$("#LogsTitle").val()
		});
	}
	function saveLogs(){
		$("#contentNoTag").val(ue.getContentTxt());
		$("#summary").val(ue.getContentTxt().substring(0,150));
		//alert($("#title").val());
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
 		$("#tid").combobox("setValue","");
		$("#title").val("");	
		//加了样式无法使用正常的设置值来修改值
		ue.setContent("");
		//CKEDITOR.instances.content.setData("");
		$("#content").val("");
	}
	function closeLogsDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function deleteLogs(){
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
				$.post("${pageContext.request.contextPath}/admin/log/delLogs.do",{ids:ids},function(result){
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
	function formatTag(val,row){
		return val.name;
	}
	function openTab(text,url,iconCls){
		window.parent.openTab(text,url,iconCls);
	}
	function openLogsModifyDialog(){
		//得到选中的消息
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		var selectId = row.id;
		//alert(selectId);
		openTab('修改博客','log/preSave.html?id='+selectId,'icon-lxr');
	}
	function formatTitle(val,row){
		return "<a href=\"${pageContext.request.contextPath}/log/info/"+row.id+".html\" target='_blank' class=\"btn btn-warning\">"+val+"</a>";
	}
</script>
<style type="text/css">
	img{
		width: 50px;
		height: 50px
	}
</style>
</head>
<body style="margin: 1px;">
<table id="dg" title="日志管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/admin/log/list.do" 
fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="id" width="20" align="center">编号</th>
    		<th field="createTime" width="20" align="center">日志发布时间</th>
    		<th field="title" width="50" formatter="formatTitle" align="center">日志标题</th>
    		<th field="tag" width="50" align="center" formatter="formatTag">日志标签</th>
    		<th field="summary" width="100" align="center">日志摘要</th>
    		<th field="content" width="100" hidden="true" align="center">日志详情</th>
    		<th field="keyWord" width="20" align="center">关键字</th>
    		<th field="clickHit" width="20" align="center">点击量/浏览量</th>
    		<th field="replyHit" width="50" align="center">回复量</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openLogsModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteLogs()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;日志标题：&nbsp;<input type="text" name="Logs.LogsTitle"  id="LogsTitle"  size="20" onkeydown="if(event.keyCode==13) searchLogs()"/>
		<a href="javascript:searchLogs()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 800px;height: 550px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="8px;">
  		<tr>
  			<td>日志标题:</td>
  			<td><input type="text" id="title" name="title" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>日志标签:</td>
  			<td>
  				<input class="easyui-combobox" id="tid" name="tag.id" 
               	data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/admin/tag/allTag.do'" />
  			</td>
  		</tr>
  		<tr>
  			<td>日志关键字:</td>
  			<td>
  				<input id="contentNoTag" name="contentNoTag" hidden="true" />
  				<input id="createTime" name="createTime" hidden="true" />
  				<input class="easyui-validatebox" id="summary" hidden="true" name="summary"  />
  				<input class="easyui-validatebox" id="keyWord" name="keyWord"  />
  			</td>
  		</tr>
  		<tr>
  		</tr>
  		<tr>
  			<td>日志详情:</td>
  			<td>
  				<textarea rows="5" cols="20" name="content" id="content" style="height: 180px;width: 600px"></textarea><!-- class="ckeditor"  -->
			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveLogs()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeLogsDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

</body>
</html>