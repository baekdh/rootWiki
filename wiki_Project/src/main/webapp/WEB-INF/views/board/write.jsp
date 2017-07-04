<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"	href="/rootWiki/resources/css/rootstyle.css">
<link rel="icon" type="image/png"  href="/resources/images/rootwiki_icon.png"/>
<script type="text/javascript" src="/rootWiki/resources/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
<%
	String check = (String)request.getAttribute("mode");
	if (check != null) {
%>
		var a = document.getElementById("preview");
		a.style.border="2px solid #bebebe";
		$("#edit").click(function(e) {
			document.write_form.mode.value = 0;
			document.write_form.action = "writeChange";
			document.write_form.submit();
		});
<%
	} else {
%>
		var b = document.getElementById("editor");
		b.style.border="2px solid #bebebe";
		$("#prev").click(function(e) {
			document.write_form.mode.value = 1;
			document.write_form.action = "writeChange";
			document.write_form.submit();
		});
<%
	}
%>
});
</script>
</head>
<jsp:include page="../header.jsp"/>
<body>
	<div class="content_box">
	<form:form method="post" commandName="BoardVO" action="write" name="write_form">
		<%-- <input type="hidden" name="boardNo" value="${boardVO.boardNo}"> --%>
		<input type="hidden" name="title" value="${boardVO.title}">
		<input type="hidden" name="mode" value="0">
		<div>
			<h1>${boardVO.title}</h1>
		</div>
		<div class="view_mode">
				<div id="editor">
					<a id="edit">편집</a>
				</div>
				<div id="preview">
					<a id="prev">미리보기</a>
				</div>
		</div>
		<div class="c_content" style="margin-top:20px;">
			<div class="edit_box">
			<%
				if(check != null) {
			%>
				<input type="hidden" name="content" value="${boardVO.content}">
				${prevStr}
			<%} else { %>
				<textarea rows="40" cols="117" name="content">${boardVO.content}</textarea>
			<%} %>
			</div>
		</div>
		<div class="right_box">
			<input class="input_button" type="submit" value="등록">
		</div>
		<br>
		<div class="exam_box">
			<center><h2>작성 예</h2></center><br>
			<div class="exam_left">
				==주제목==<br>
				내용1<br>
				===부제목===<br>
				내용2[[문서태그]]<br>
			</div>
			<div class="exam_arrow">
				<img src="/rootWiki/resources/images/right_arrow.png" width="50px" height="50px">
			</div>
			<div class="exam_right">
				<h2>1. 주제목</h2><hr>
				내용1<br>
				<h3>1.1 부제목</h3><hr>
				내용2<a href="javascript:void(0)">문서태그</a>
			</div>
		</div>
	</form:form>
	</div>
</body>
<jsp:include page="../footer.jsp"/>
</html>