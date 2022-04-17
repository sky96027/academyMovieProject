<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="body container" >
			<h1>게시글 수정</h1>
			<form action="<%=request.getContextPath()%>/movieboard/modify" method="post" enctype="multipart/form-data">
				<div class="form-group">
				  <input type="text" class="form-control" name="mv_name" value="${movieboard.mv_name}" 
				  placeholder="${movieboard.mv_name}">
				</div>
				<div class="form-group">
				  <input type="text" class="form-control" name="mv_reg_date" value="${movieboard.mv_reg_date_str}" 
				  placeholder="${movieboard.mv_reg_date}">
				</div>
				<div class="form-group">
				  <input type="text" class="form-control" name="mv_time" value="${movieboard.mv_time}" 
				  placeholder="${movieboard.mv_time}">
				</div>
				<div class="form-group">
				  <input type="text" class="form-control" name="mv_age" value="${movieboard.mv_age}" 
				  placeholder="${movieboard.mv_age}">
				</div>
				<div class="form-group">
				  <input type="text" class="form-control" name="mv_director" value="${movieboard.mv_director}" 
				  placeholder="${movieboard.mv_director}">
				</div>
				<div class="form-group">
				  <textarea class="form-control" name="mv_outline" rows="10">${movieboard.mv_outline}</textarea>
				</div>
				<input type = "hidden" name = "mv_num" value = "${movieboard.mv_num}">
				<div class = "form-group attachment">
					<div class="form-group">
						<label>포스터, 메인 이미지</label>
						<input type="file" class="form-control" name="thumb" accept="image/*,video/*">
					</div>
					<c:forEach items="${fileList}" var="file">
						<div class="form-control">
							<input type = "hidden" name="fileNums" value="${file.fi_num}">
							<span>${file.fi_ori_name}</span>
							<a class = "btn-close" href = "#">X</a>
						</div>
					</c:forEach>
					<c:forEach begin="1" end="${3-fileList.size() }">
						<input type="file" class="form-control" name ="files2">
					</c:forEach>
				</div>
				<button class="btn btn-outline-success col-12">등록</button>
			</form>
			<script>
				$(function(){
					$('.attachment .btn-close').click(function(e){
						e.preventDefault();
						$(this).parent().remove();
						var str = '<input type="file" class="form-control" name ="files2">';
						$('.attachment').append(str);
					});
				});
				$('[name=mv_outline]').summernote({
			   		placeholder: '${movieboard.mv_outline}',
			    	tabsize: 2,
			    	height: 400
			    });
			</script>
		</div>
</body>
</html>