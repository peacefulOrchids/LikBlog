<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>幽兰居</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function openTab(text,url,iconCls){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%;' src='${pageContext.request.contextPath}/admin/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				iconCls:iconCls,
				closable:true,
				content:content
			});
		}
	}
	function refreshSystem(){
		$.post("${pageContext.request.contextPath}/admin/system/refreshSystem.do",function(result){
			if(result.success){
				$.messager.alert("系统提示","数据刷新成功！");
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
	}
	
	function openUpdatePwdDialog(){
		$("#dlg").dialog("open").dialog("setTitle","修改管理员密码");
		$("#userName").val("${currentUser.userName}");	
		url="${pageContext.request.contextPath}/admin/user/updatePwd.do?id=${currentUser.id}";
	}
	function saveNewPwd(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				var oldPwd = $("#oldPassword").val();
				var newPwd = $("#newPassword").val();
				var newPwd2 = $("#newPassword2").val();
				if(oldPwd!='${oldPwd}'){
					$.messager.alert("系统提示","旧密码错误");
					return false;
				}
				if(newPwd!=newPwd2){
					$.messager.alert("系统提示","新密码与确认密码不一致");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				var result = eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","修改成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","修改失败");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#userName").val("");	
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}
	
	function closeUpdatePwdDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
</script>
<title>幽兰居</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/static/images/DDlogo.png" />
</head>
<body class="easyui-layout">
<div align="center" style="padding-top:100px">
				<font color="red" size="10">欢迎使用幽兰居后台管理系统</font>
			</div><div region="north" style="height:78px;background-color: #EOECFF">
	<table style="padding:5px" width="100%">
		<tr>
			<td width="50%">
				<img alt="" src="${pageContext.request.contextPath }/static/images/DDlogoIcon.png" width="160" height="60">
			</td>
			<td valign="bottom" align="right" width="50%">
				<font size="3">&nbsp;&nbsp;<strong>欢迎:</strong>${currentUser.userName }</font>
			</td>
		</tr>
	</table>
</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" data-options="iconCls:'icon-home'">
			
		</div>
	</div>
</div>
<div region="west" style="width:200px" title="导航菜单" split="true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		<div title="常用操作" data-options="selected:true,iconCls:'icon-item'" style="padding: 10px">
			<a href="javascript:openTab('写博客','writeLog.jsp','icon-writeblog')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">写博客</a>
		</div>
		<div title="用户管理" data-options="selected:true,iconCls:'icon-user'" style="padding:10px">
			<a href="javascript:openTab('管理用户','userManage.jsp','icon-user')" class="easyui-linkbutton" 
			data-options="plain:true,iconCls:'icon-user'">管理用户</a>
		</div>
		<div title="日志管理" data-options="iconCls:'icon-tjbb'" style="padding: 10px">
			<a href="javascript:openTab('管理日志','logsManage.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px;">管理日志</a>
		</div>
		<div title="标签管理" data-options="iconCls:'icon-jcsjgl'" style="padding: 10px">
			<a href="javascript:openTab('管理标签','tagManage.jsp','icon-sjzdgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-sjzdgl'" style="width: 150px;">管理标签</a>
		</div>
		<div title="友链管理" data-options="iconCls:'icon-jcsjgl'" style="padding: 10px">
			<a href="javascript:openTab('管理友链','linkManage.jsp','icon-sjzdgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-sjzdgl'" style="width: 150px;">管理友链</a>
		</div>
		<div title="留言管理" data-options="iconCls:'icon-fwcj'" style="padding: 10px">
			<a href="javascript:openTab('管理留言','messageManage.jsp','icon-fwcj')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwcj'" style="width: 150px;">管理留言</a>
		</div>
		<div title="评论管理" data-options="iconCls:'icon-fwgl'" style="padding: 10px">
			<a href="javascript:openTab('管理评论','commentManage.jsp','icon-fwgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fwgl'" style="width: 150px;">管理评论</a>
		</div>
		<div title="系统管理" data-options="iconCls:'icon-item'" style="padding: 10px">
			<a href="javascript:openUpdatePwdDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
			<a href="${pageContext.request.contextPath}/admin/user/logout.do" onclick="return confirm('确定要离开吗?')?true:false;" 
			class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
			<a href="javascript:refreshSystem()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
		</div>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 370px;height: 200px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="8px;">
  		<tr>
  			<td>用户名：</td>
  			<td>
  				<input type="text" id="userName" readonly="readonly" name="userName" class="easyui-validatebox" required="true"/>
  			</td>
  		</tr>
  		<tr>
  			<td>旧密码：</td>
  			<td><input type="password" id="oldPassword" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>新密码：</td>
  			<td><input type="password" id="newPassword" name="password" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>确认密码：</td>
  			<td><input type="password" id="newPassword2" class="easyui-validatebox" required="true"/></td>
  		</tr>
  	</table>
  </form>
</div>
<div id="dlg-buttons">
	<a href="javascript:saveNewPwd()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeUpdatePwdDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>
<div region="south" style="height:25px;padding: 5px;" align="center">
	版权所有2018博客博主
</div>
</body>
</html>