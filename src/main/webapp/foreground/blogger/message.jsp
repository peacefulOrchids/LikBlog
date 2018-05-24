<!DOCTYPE html>
<html lang="en">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
<meta charset="UTF-8">
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/static/images/DDlogo.png" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/unlock.css">
<script type="text/javascript" src='${pageContext.request.contextPath }/static/js/unlock.js'></script>
</head>
<body>
<h1 class="logo-right hidden-xs margin-bottom-60">White</h1><br>
	<div class="tm-right-inner-container" id="about"> 
	  <!-- page about -->
		<div>
			<h1 class="templatemo-header">留言板</h1>
			<div class="page_content">
				欢迎各位给我留言
			</div>
		</div>
		<c:forEach var="msg" items="${msgList }">
			<div class="page_content">
				<p>
					<img src="${pageContext.request.contextPath}/static/images/DDlogo.png" style="width:100px; height:100px; border-radius:50%;" align="left" />
					<span style="color:#ccc;">${msg.msgName }</span><br>
					${msg.createTime } <br>
					email:${msg.email }<br>
					${msg.content }
				</p>
				<c:if test="${msg.replyContent!=null }">
					<span style="color:#00FF00;">博主回复:${msg.replyContent } 回复时间:${msg.replyTime }</span>
				</c:if>
				<c:if test="${msg.replyContent==null }">
					<span style="color:#ADD8E6;">等待回复</span>
				</c:if>
				<hr>
			</div>
		</c:forEach>
	</div>
	<nav>
		<ul class="pager">
			${pageCode }
		</ul>
	</nav>
	<div class="page_content">
		<form action="${pageContext.request.contextPath}/message/saveMessage.html" onsubmit="return Check();" method="post">
			留言*:<br>
			<textarea rows="5" name="content" id="content" cols="20">${msg.content }</textarea><br>
			姓名*:<br>
			<input type="text" value="${msg.msgName }" name="msgName" id="msgName" /><br>
			邮箱*:<br>
			<input type="email" value="${msg.email }" name="email" id="email" /><br>
			<div class="bar3 bar"></div><br><br>
			<input type="submit" value="提交" /><br>
			<span id="errorMsg" style="color: red;"></span>
		</form>
	</div>
	<jsp:include page="../common/footer.jsp"/>
	<script type="text/javascript">
		if('${errorMsg}'!=''){
			alert("${errorMsg}");
		}
		function Check(){
			var lock = $("#lock").html();
			if(lock!='解锁成功'){
				$("#errorMsg").text("请解锁");
				return false;
			}
			var msgName = $("#msgName").val();
			var email = $("#email").val();
			if(msgName=="" || msgName == null){
				$("#errorMsg").text("姓名不能为空");
				return false;
			}
			if(email=="" || email == null){
				$("#errorMsg").text("邮箱不能为空");
				return false;
			}
			return true;
		}
		$('.bar3').slideToUnlock({
			height : 40,
			width : 300,
			text : '未解锁',
			succText : '解锁成功'
		});
	</script>
</body>
</html>