<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 用于递归子评论 -->
<ol class="comment-list">
	<c:forEach var="comment" items="${comments }">
		<li id="comment_${comment.id }" class="comment even thread-even depth-1 parent">
			<article id="div-comment-${comment.id }" class="comment-body">
				<footer class="comment-meta">
					<div class="comment-author vcard">
						<img src="${pageContext.request.contextPath}/static/images/DDlogo.png" style="width:50px; height:50px; border-radius:50%;" align="left" />
						<b class="fn"><a href="javascript:void(0)">${comment.cname }:</a></b><span class="says">说道：</span>		
					</div><!-- .comment-author -->
					<div class="comment-metadata">
						${comment.createTime }
					</div>
				</footer><!-- .comment-meta -->
				<div class="comment-content">
					<p>${comment.content }</p>
				</div><!-- .comment-content -->
				<div class="reply">
					<a class="comment-reply-link" href="javascript:reply(${comment.id })">回复</a>
				</div>
			</article><!-- .comment-body -->
			<c:set var="comments" value="${comment.commentList}" scope="request"></c:set>
			<c:set var="num" value='1' scope="request"></c:set>
			<jsp:include page="Subcomments.jsp"/>
		</li>
	</c:forEach>
</ol>