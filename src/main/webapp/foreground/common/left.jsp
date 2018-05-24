<!DOCTYPE html>
<html lang="en">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="UTF-8">
<title>OnePager - One Page Responsive Portfolio Template</title>

<meta name="viewport" content="width=device-width,initial-scale=1">
<style type="text/css">
	ul {
		list-style: none;
		text-align: center;
	}
</style>
</head>
<body>
	<h1 class="logo-left hidden-xs margin-bottom-60" style="color: white;">Black</h1>			
	<div class="tm-left-inner-container">
	   <ul class="nav nav-stacked templatemo-nav">
	     <li><a href="${pageContext.request.contextPath}/index.html">主页</a></li>
	     <c:forEach items="${tagList }" var="tag">
	      <li><a href="${pageContext.request.contextPath}/log/tag/${tag.id }.html">${tag.name }</a></li>
	     </c:forEach>
	     <li><a href="${pageContext.request.contextPath}/message/list.html">留言板</a></li>
	     <li><a href="${pageContext.request.contextPath}/about.html">关于本站</a></li>
	     <li><a href="${pageContext.request.contextPath}/sky.html">关于我</a></li>
	   </ul>
	</div>
</body>
</html>
