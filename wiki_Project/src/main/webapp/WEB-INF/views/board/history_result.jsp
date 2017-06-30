<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	if(!confirm("되돌리시겠습니까?")) {
		location.href = 'history?boardNo=${boardNo}'
	} else {
		location.href = "rollbackAction?backupNo=" + <%=request.getAttribute("backupNo")%>
	}
</script>
</head>
<body>

</body>
</html>