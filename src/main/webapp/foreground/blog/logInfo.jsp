<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'LogInfo.jsp' starting page</title>
	<link rel="stylesheet" id="nisarg-style-css" href="${pageContext.request.contextPath}/static/css/comment-style.css" type="text/css" media="all">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/myemojiPl.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/myemojiPl.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
	<script type="text/javascript">
	    SyntaxHighlighter.all();
	    setTimeout(function(){
	  		$.post("${pageContext.request.contextPath}/log/addClick.html",{id:${mylog.id }},function(result){
			},"json");
	  	}, 5000);
	</script>
	<style type="text/css">
		ol li {
		    list-style: none;
		    padding: 5px 0;
		}
		fonter{
			overflow: visible;
		}
	</style>
  </head>
<body>
<h1 class="logo-right hidden-xs margin-bottom-60">White</h1>
	<!-- page container -->
	<div class="tm-right-inner-container" id="about"> 
		<div style="text-align: center;">
			<h1 class="templatemo-header">${mylog.title }</h1>
			写于${mylog.createTime }<br>
			<!-- <div>
				<div class="bshare-custom"><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
			</div> -->
     	</div>
		<div class="page_content" style="text-align: left;" id="log_content">
			${mylog.content }
		</div>
		<p align="center">
			<span>${nextOrLast }</span><br>
			<a href="javascript:reply('comment')">评论</a>-<a href="${pageContext.request.contextPath}/sponsor.html">赞助</a>
		</p>
	</div>
	<ol class="comment-list" id="comment-list">
		<c:forEach var="comment" items="${commentList }">
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
	<div class="page_content" id="comment_body" style="text-align: left;display:none;">
		<form action="${pageContext.request.contextPath}/comment/saveComment.html" onsubmit="return Check();" method="post">
			<input type="hidden" id="fid" name="fid" value="0" />
			<input type="hidden" id="lid" name="log.id" value="${mylog.id }" />
			<input type="hidden" name="user.id" value="${currentUser.id }" />
			<a href="javascript:noreply()">取消回复</a> 评论内容:<br>
			<div class="Main" >
				<div class="Input_Box" >
					<div id="emojiContent" contenteditable="true" class="Input_text"></div>
					<div class="Input_Foot">
						<a class="imgBtn" href="javascript:void(0);">~</a>
					</div>
				</div>
				<div class="faceDiv">
					<section class="emoji_container"></section>
					<section class="emoji_tab"></section>
				</div>
			</div>
			<script>
				var emojiconfig = {
					tieba : {
						name : "贴吧表情",
						path : "${pageContext.request.contextPath}/static/img/tieba/",
						maxNum : 50,
						file : ".jpg",
					},
					qq : {
						name : "qq表情",
						path : "${pageContext.request.contextPath}/static/img/qq/",
						maxNum : 91,
						file : ".gif"
					}
				};
				$('.Main').myEmoji({
					emojiconfig : emojiconfig
				});
			</script>
			<input type="hidden" value="" id="content" name="content">
			<!-- <textarea rows="5" name="content" id="content" cols="20"></textarea> --><br>
			姓名*:<br>
			<input type="text" value="${currentUser.userName }" name="cname" id="cname" /><br>
			邮箱*:<br>
			<input type="email" value="${currentUser.email }" name="email" id="email" /><br>
			<input type="submit" value="提交" /><br>
			<span id="errorMsg" style="color: red;"></span>
		</form>
	</div>
	<nav>
		<ul class="pager">
			${pageCode }
		</ul>
	</nav>
	<jsp:include page="../common/footer.jsp"/>
	<script type="text/javascript">
		function Check(){
			var a = document.getElementById("emojiContent").innerHTML;
			document.getElementById("content").value=a;
			var content = $("#content").val();
			var email = $("#email").val();
			var cname = $("#cname").val();
			if(content=="" || content == null){
				$("#errorMsg").text("评论内容不能为空");
				return false;
			}
			if(email=="" || email == null){
				$("#errorMsg").text("邮箱不能为空");
				return false;
			}
			if(cname=="" || cname == null){
				$("#errorMsg").text("姓名不能为空");
				return false;
			}
			return true;
		}
		function reply(bb){
			if(bb == "comment"){
				$("#fid").val(0);
				$("#comment-list").append($("#comment_body"));
			}else{
				$("#fid").val(bb);
				$("#comment_"+bb).append($("#comment_body"));
			}
			$("#comment_body").css("display","inline");
		}
		function noreply(){
			$("#fid").val(0);
			$("#comment_body").css("display","none");
		}
	</script>
</body>
</html>
