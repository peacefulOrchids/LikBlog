<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>

<%@include file="foreground/common/common.jsp" %>
<%
		String mainPage = (String)request.getAttribute("mainPage");
		if(mainPage==null || mainPage.equals("")){
			mainPage = "page/logs.jsp";
		}
	 %>
<title>幽兰居</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/static/images/DDlogo.png" />
</head>
<!-- 
	 style="background-image: url('${pageContext.request.contextPath}/background/<%=(new Random().nextInt(9))%>.jpg');background-color:#<%=(new Random().nextInt(100))%><%=(new Random().nextInt(100))%><%=(new Random().nextInt(100))%>;background-size:cover;background-repeat:no-repeat;"
 -->
<body>
<jsp:include page="foreground/common/top.jsp"/>
<div class="templatemo-container">
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 black-bg left-container">
		<jsp:include page="foreground/common/left.jsp"/>
	</div> <!-- left section -->
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg right-container">
		<jsp:include page="<%=mainPage%>"></jsp:include>
    </div><!-- right section -->
</div>
</body>
</html>

