<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	function query(){
		var val = $("#q").val();
		if(val == '' || val == null){
			alert("请输入搜索的关键字!");
			return false;
		}
		return true;
	}
</script>
<div class="templatemo-logo visible-xs-block">
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 black-bg logo-left-container">
		<h1 class="logo-left">Black</h1>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 white-bg logo-right-container">
		<h1 class="logo-right">White</h1>
	</div>			
</div>
<div class="search" style="float: right;position: fixed;right: 0px;z-index: 888">
	<!-- "${pageContext.request.contextPath}/log/list.html?tag.id=${s_log.tag.id>0?s_log.tag.id:0} -->
	<form action="${pageContext.request.contextPath}/log/q.html" onsubmit="return query();" method="post">
	    <div class="input-group" style="width: 300px">
		      <input type="text" class="form-control" id="q" name="q"  value="${q }" placeholder="请输入要查询的日志关键字...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button>
		      </span>
	    </div>
   </form>
</div>
