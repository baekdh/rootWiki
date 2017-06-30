<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
<%
	String result = (String)request.getAttribute("msg");
	if(result != null) {
		if(result == "noUser") {
%>
			alert("로그아웃 상태입니다.");
			location.href = "main";
<%
		} else if(result == "noAdmin") {
%>
			alert("당신은 관리자가 아닙니다.");
			location.href = "main";
<%
		} else if(result == "noTarget") {
%>
			alert("해당 ID의 회원이 없습니다.");
<%
		} else if(result == "adminBlock") {
%>
			alert("관리자는 차단할 수 없습니다.");
<%
		} else if(result == "complete") {
%>
			alert("차단 완료");
<%
		}
	}
	int type = (Integer)request.getAttribute("type");
%>
</script>
</head>
<jsp:include page="../header.jsp"/>
<body>
	<form method="post" action="blockAction">
		<select name="block_type">
			<option>회원ID</option>
			<option <%if(type==2) {%> selected="selected" <%}%>>IP</option>
		</select>
		<input type="text" name="block_target" value="${target}">
		<input type="submit" value="차단">
	</form>
</body>
</html>