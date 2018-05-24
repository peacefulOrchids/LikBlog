<!DOCTYPE html>
<html lang="en">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
<meta charset="UTF-8">
<title>OnePager - One Page Responsive Portfolio Template</title>

<meta name="viewport" content="width=device-width,initial-scale=1">
<style type="text/css">
	body{
		text-align: center;
	}
	#log_content{
		text-align: left;
	}
	img{
		width: 100px;
		height: 100px;
	}
</style>
</head>
<body>
<h1 class="logo-right hidden-xs margin-bottom-60">White</h1>
   <!-- page container -->
   <div class="tm-right-inner-container" id="about"> 
	<h1 class="templatemo-header">享受编程和技术所带来的快乐 - Coding Your Ambition</h1>
     <!-- page about -->
     <c:forEach var="log" items="${logList }">
     	<article>
			<h2>${log.title } </h2><!-- class="page_title" -->
			写于${log.createTime } 浏览量(${log.clickHit }) 评论量(${log.replyHit })<br>
			<a href="${pageContext.request.contextPath}/log/info/${log.id}.html" class="btn btn-warning">阅读全文</a>
			<p class="subText">
				${log.summary }...
				<c:forEach var="img" items="${log.imageList }">
					${img }
				</c:forEach>
			</p>
     	</article>
     	<hr>
     </c:forEach>
   </div>
	<nav>
		<ul class="pager">
			${pageCode }
		</ul>
	</nav>
	<jsp:include page="../common/footer.jsp"/>
</body>
</html>
