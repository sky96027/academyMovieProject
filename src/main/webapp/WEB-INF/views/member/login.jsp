<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class = "body">
		<div class = "container">
			<h1 class="text-center">로그인</h1>
			<form action="<%=request.getContextPath()%>/member/login" method="post">
			  	<div class="form-group">
			    	<input type="text" class="form-control" placeholder="아이디" name = "me_id">
			  	</div>
			  	<div class="form-group">
			    	<input type="password" class="form-control" placeholder="비밀번호" name = "me_pw">
			  	</div>
			  	<button class="btn btn-outline-success col-12">로그인</button>
			  	<label>
						<input type="checkbox" name="me_auto_login" value="ok"> 자동로그인
					</label>
				</form>
				<a href="<%=request.getContextPath()%>/member/find">아이디/비번 찾기</a>
			
		</div>
	</div>
</body>
</html>