<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	var ue;
	$(document).ready(function(){ 
		ue = UE.getEditor('content');
 		$("#tid").combobox("setValue",'${mylog.tag.id }');
	}); 
 	
	function saveLogs(){
		//alert($("#title").val());
		$("#contentNoTag").val(ue.getContentTxt());
		$("#summary").val(ue.getContentTxt().substring(0,150));
		$("#fm").form("submit",{
			success:function(result){
				var result = eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功");
					if('${mylog.title}' == null||'${mylog.title}' == ''){
						resetValue();
					}
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
		$("#keyWord").val("");	
		//加了样式无法使用正常的设置值来修改值
		ue.setContent("");
		//CKEDITOR.instances.content.setData("");
		$("#content").val("");
	}
</script>
</head>
<body style="margin: 1px;">
<div>
	<form id="fm" method="post" action="${pageContext.request.contextPath}/admin/log/saveLogs.do">
		<table id="dlg" title="写日志" class="easyui-panel"
			style="width: 750px; height:600px; padding: 10px 20px"
			buttons="#dlg-buttons">
	  		<tr>
	  			<td>日志标题:</td>
	  			<td><input type="text" id="title" name="title" value="${mylog.title }" size="20" style="width: 250px;" class="easyui-validatebox" required="true"/></td>
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
	  				<input class="easyui-validatebox" id="keyWord" value="${mylog.keyWord }" name="keyWord"  />(关键字用空格隔开)
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>日志详情:</td>
	  			<td>
	  				<textarea rows="5" cols="20" name="content" id="content" style="height: 180px;width: 600px">${mylog.content }</textarea><!-- class="ckeditor"  -->
				</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<c:if test="${mylog!=null }">
		  				<input id="id" name="id" value="${mylog.id }" hidden="true" />
	  				</c:if>
	  				<input id="summary" name="summary" value="${mylog.summary }" hidden="true" />
  					<input id="createTime" name="createTime" value="${mylog.createTime }" hidden="true" />
	  				<input id="contentNoTag" name="contentNoTag"  hidden="true" />
					<a href="javascript:saveLogs()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
					<a href="javascript:resetValue()" class="easyui-linkbutton" iconCls="icon-cancel" >重置</a>
	  			</td>
	  		</tr>
	  	</table>
	</form>
</div>
</body>
</html>