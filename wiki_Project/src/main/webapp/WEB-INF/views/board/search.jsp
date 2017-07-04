<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ROOT WIKI 검색</title>
<link rel="stylesheet" type="text/css" href="/rootWiki/resources/css/rootstyle.css">
<link rel="icon" type="image/png"  href="/resources/images/rootwiki_icon.png"/>
<script type="text/javascript">
	function writeBoard() {
		location.href = 'write?title=${search}';
	}
	function back() {
		location.href = 'javascript:history.go(-1)';
	}
</script>
</head>
<jsp:include page="../header.jsp"/>
<body>
	<div class="content_box">
		'${search}' 문서가 없습니다<br><br>
		<a href="javascript:writeBoard()">문서 작성하기</a><br>
		<br><br>
		제목에 '${search}'(이/가) 포함된 문서<hr>
		<c:forEach var="vo" items="${result}">
			<a href="content?boardNo=${vo.boardNo}&replyPage=1">${vo.title}</a><br>
			<hr>
		</c:forEach>
		<c:if test="${empty result}">
			'${search}'(이/가) 포함된 문서를 찾을 수 없습니다.<br><br>
			<input class="input_button" type="button" value="돌아가기" onclick="back()">
		</c:if> 
	</div>
</body>
<jsp:include page="../footer.jsp"/>
</html>