<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/images/DDlogo.png" />
<%@include file="../common/common.jsp" %>
<title>幽兰居</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/images/DDlogo.png" />
</head>
<!--  style="background-image: url('${pageContext.request.contextPath}/background/<%=(new Random().nextInt(9))%>.jpg');background-color:#<%=(new Random().nextInt(100))%><%=(new Random().nextInt(100))%><%=(new Random().nextInt(100))%>;background-size:cover;background-repeat:no-repeat;" -->
<body>
<h1 class="logo-right hidden-xs margin-bottom-60">White</h1><br>
 	<div class="tm-right-inner-container" id="about"> 
	<div>
		<h1 class="templatemo-header">赞助</h1>
		<div class="page_content">
			如果觉得我写的一些东西对你有帮助,可以小小的赞助一哈我,好人一生平安
		</div>
	</div>
	<div>
		<img width="30%" height="30%" src="${pageContext.request.contextPath}/static/images/zfb.png">
		<img width="30%" height="30%" src="${pageContext.request.contextPath}/static/images/wx.png">
		<br>
		<a href="javascript:window.history.back();" class="btn btn-warning">返回</a>
	</div>
</div>
</body>
</html>

