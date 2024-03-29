<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<div class="container">
		  	<a class="navbar-brand" href="<%=request.getContextPath()%>">ㅇㄱㅂㄹ?</a>
		  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    	<span class="navbar-toggler-icon"></span>
		  	</button>
		  	<div class="collapse navbar-collapse" id="collapsibleNavbar">
		    	<ul class="navbar-nav">
		    		<c:if test="${user == null}">
				      	<li class="nav-item">
				        	<a class="nav-link" href="<%=request.getContextPath()%>/member/login">로그인</a>
				      	</li>
				      	<li class="nav-item">
				        	<a class="nav-link" href="<%=request.getContextPath()%>/member/signup">회원가입</a>
				      	</li>
			      	</c:if>
			      	<c:if test="${ user != null }">
				    	<li class="nav-item">
					      	<a class="nav-link" href="<%=request.getContextPath()%>/logout">로그아웃</a>
					    </li>
					    <li class="nav-item">
					      	<a class="nav-link" href="<%=request.getContextPath()%>/member/mypage">마이페이지</a>
					    </li>
					    <li class="nav-item">
					     	<a class="nav-link" href="<%=request.getContextPath()%>/movieboard/movielocker">추천 목록</a>
					    </li>
					    <li class="nav-item">
					      	<a class="nav-link" href="<%=request.getContextPath()%>/movieboard/register">리뷰 등록</a>
					    </li>
				    </c:if>   
				    <c:if test="${user.me_authority == '최고 관리자'}">
				    	<li class="nav-item">
					      	<a class="nav-link" href="<%=request.getContextPath()%>/admin/member/list">회원관리</a>
					    </li>
				    </c:if>
		    	</ul>
			</div> 
		</div> 
	</nav>
</body>
</html>