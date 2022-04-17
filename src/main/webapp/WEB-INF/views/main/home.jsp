<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.box::after{ display: block; content: ''; clear: both;}
.thumbnail{ display: block; float: left;} 
.thumbnail-img { width:240px; height : 320px; background-color: gray; display: block}
.thumbnail-title{ display: block; text-decoration: none; color:black}
.thumbnail-like{ display: block; text-decoration: none; color:black} 

</style> 
</head> 
    
<body>   
<div style="min-height: 800px; width:1200px; margin:0 auto;" class="box">

	<form class="input-group mb-3" action="<%=request.getContextPath()%>">
	    <input type="text" class="form-control" name="search" placeholder="검색어를 입력하세요." value="${pm.criteria.search }">
	    <div class="input-group-append">
	    	<button class="btn btn-success" type="submit">검색</button>
	    </div>
	    <input type="hidden" name="type" value="${pm.criteria.type}">
	</form>
	
	
	<c:forEach items="${list }" var="mv">
		<a class="thumbnail" href="<%=request.getContextPath()%>/movieboard/detail?mv_num=${mv.mv_num}">
			<img class="thumbnail-img" src="<c:url value="/img${mv.mv_thumb }"></c:url>">
			<span class="thumbnail-title">${mv.mv_name}</span>
			<span class="thumbnail-like">추천 : ${mv.mv_up}</span> 
		</a>	
	</c:forEach>
	
</div>
</body>
</html>

