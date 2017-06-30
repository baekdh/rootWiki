<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../resources/css/rootstyle.css">
<script type="text/javascript">
	function input_focus(obj) {
		if(obj.value)
		obj.value='';
	}
</script>
</head>
<%-- <jsp:include page="../header.jsp"/> --%>
<body>
	<!-- 수정 : div 클래스 수정 -->
	<div class="loginbody">
		<form:form method="post" commandName="memberVO" action="/rootWiki/login">
			<div class="login_box">
				<div class="input_box">
					<input type="text" name="memberid" value="아이디" onfocus="input_focus(this);">
				</div>
				<div class="input_box">
					<input type="password" name="memberpw" value="비밀번호" onfocus="input_focus(this);">
				</div>
				<span class="btn_login">
					<input class="input_button" type="submit" value="로그인" id="login_btn">
				</span>
				<a href="/rootWiki/regist">회원가입</a>
			</div>
		</form:form>
	</div>
</body>
<%-- <jsp:include page="../footer.jsp"/> --%>
</html>