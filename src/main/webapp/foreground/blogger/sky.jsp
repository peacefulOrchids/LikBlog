<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>

<%@include file="../common/common.jsp" %>
<title>幽兰居</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/images/DDlogo.png" />
</head>
<body>
<h1 class="logo-right hidden-xs margin-bottom-60">White</h1><br> 
 	<div class="tm-right-inner-container" id="about"> 
  <!-- page about -->
		<div>
			<h1 class="templatemo-header">关于我</h1>
			<div class="page_content">
				<pre>
关于我,我会不定时的在这个网站发一些我的日志,分享一些经验,我跟随着我的兴趣和喜好去学习一些技术
qq邮箱:1248427290@qq.com
新浪微博:子建丨<a target="_blank" href="//weibo.com/u/5652210712">weibo.com/u/5652210712</a>
座右铭:
	莫见乎隐,莫显乎微,故君子慎其独也
				</pre>
			</div>
		</div>
		<div id="link" style="display:block">
			<div id="link_content">
				<h3 id="link_h">小伙伴们</h3>
			    <c:forEach var="link" items="${linkList }">
					<a href="${link.linkurl }" target="_blank" title="${link.linkname }">${link.linkname }</a>　　
			    </c:forEach>
			</div>
		</div>

	</div>
</body>
</html>

