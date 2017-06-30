<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
/* 제목 중복체크 */
<%
	String check = (String)request.getAttribute("msg");
	if(check != null) {
%>
	alert("중복된 제목입니다.");
	location.href = "javascript:history.go(-1)";
<%
	}else {
%>
	if(!confirm("작성한 문서를 확인하시겠습니까?")) {
		location.href = "../main"
	} else {
		location.href = "content?boardNo=" + <%=request.getAttribute("boardNo")%> + "&replyPage=1"
	}
<%}%>
</script>
</head>
<body>

</body>
</html>