<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/rootWiki/resources/css/rootstyle.css">
<script type="text/javascript" src="/rootWiki/resources/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
	<!-- 글자수 제한 댓글 작성창 스크립트 -->
	$(document).ready(function() {
	      $('#r_content').keyup(function (e){
	          var content = $(this).val();
	          $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
	          $('#counter').html(content.length + '/150');
	      });
	      $('#r_content').keyup();
	});
</script>
</head>
<body>
	<div class="reply">
		<!-- 글자수 제한 댓글 작성창 -->
		<div class="wrap">
			<h2>댓글 수정</h2>
			<hr>
			<form:form action="replyUpdateAction" method="post" commandName="ReplyVO" name="r_updateForm">
				<input type="hidden" name="r_no" value="${rvo.r_no }">
				<input type="hidden" name="r_boardNo" value="${rvo.r_boardNo }">
				<textarea id="r_content" maxlength="150" name="r_content"
							placeholder="댓글을 작성해주세요">${rvo.r_content}</textarea>
				<span id="counter">###</span>
				<br><br>
				<!-- <input type="button" value="수정" onclick="updateInsert()"> -->
				<input type="submit" value="수정">
				<input type="button" value="창닫기" onclick="window.close()">
			</form:form>
		</div>
	</div>
</body>
</html>