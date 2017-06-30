<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	function historyOpen() {
		location.href="board/historyOpen?bSearch_str=";
	}
</script>
</head>
<jsp:include page="../header.jsp"/>
<body>
	<form method="post" action="block">
		<input type="button" value="히스토리 열람" onclick="historyOpen()">
		<input type="submit" value="회원정지">
	</form>
</body>
</html>