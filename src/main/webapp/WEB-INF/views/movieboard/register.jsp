<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery.validate.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/additional-methods.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="body container">
		<h1>리뷰 등록</h1>
		<form action="<%=request.getContextPath()%>/movieboard/register" method="post" enctype="multipart/form-data">
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_name" placeholder="영화 제목">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_reg_date" id="reg" placeholder="개봉일">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_genre" placeholder="장르">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_time" placeholder="런닝 타임">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_age" placeholder="관람 연령">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="mv_director" placeholder="감독">
			</div>
			<div class="form-group">
			  <textarea class="form-control" name="mv_outline" placeholder="줄거리" rows="10"></textarea>
			</div>
			<div class="form-group">
				<label>포스터, 메인 이미지</label>
				<input type="file" class="form-control" name="thumb" accept="image/*,video/*">
			</div>
			<div class="form-group">
				<label>관련 이미지/동영상</label>
				<input type="file" class="form-control" name="files2" accept="image/*,video/*">
				<input type="file" class="form-control" name="files2" accept="image/*,video/*">
				<input type="file" class="form-control" name="files2" accept="image/*,video/*">
			</div>
			<button class="btn btn-outline-success col-12">등록</button>
		</form>
	</div>
<script>

	$('#reg').datepicker();
	$('#reg').datepicker('option','dateFormat', 'yy-mm-dd');
	
	$('[name=mv_outline]').summernote({
   		placeholder: '줄거리',
    	tabsize: 2,
    	height: 400
    });
</script>
</body>
</html>