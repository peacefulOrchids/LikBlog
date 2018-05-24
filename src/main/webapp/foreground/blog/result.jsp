<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1 class="logo-right hidden-xs margin-bottom-60">White</h1>
<!-- page container -->
<div class="tm-right-inner-container" id="about"> 
	<h1 class="templatemo-header">搜索<font color="red">${q }</font>的结果(总共搜索到&nbsp;${resultTotal }条记录)</h1>
	<div style="text-align: center;">
		<c:forEach var="log" items="${logList }">
	     	<article>
				<a href="${pageContext.request.contextPath}/log/info/${log.id}.html">${log.title } </a>写于${log.createTime }
				<p class="subText">
					摘要:<a href="${pageContext.request.contextPath}/log/info/${log.id}.html">${log.content }...</a>
				</p>
	     	</article>
	     	<hr>
    	</c:forEach>
   	</div>
   	${pageCode }
</div>
	