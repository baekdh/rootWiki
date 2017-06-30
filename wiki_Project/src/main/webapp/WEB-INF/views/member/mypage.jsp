<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="com.root.domain.MemberVO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	MemberVO userinfo = (MemberVO) request.getSession().getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="../resources/css/rootstyle.css">
<script type="text/javascript">
	function withdrawClick() {
		if(confirm("탈퇴하시겠습니까?")) {
			document.withdraw_form.submit();
		}
	}
</script>
</head>
<!-- header -->
<jsp:include page="../header.jsp" />
<!-- /header -->
<body class="memberbody">
	<div class="mem_container">
		<div class="mem_contentbox">
			<div class="mem_content">
				<h1>Root Wiki MyPage</h1>
					<div class="mem_row_group">
						<div class="mem_inputDiv">
							<label class="mem_label">사용자 ID</label> 
							<label><%=userinfo.getMemberid() %></label>
						</div>
							<hr>
						<div class="mem_inputDiv">
							<label class="mem_label">이름</label> 
							<label><%=userinfo.getMembername() %></label>
						</div>
						<hr>
						<div class="mem_inputDiv">
							<label class="mem_label">E-mail</label> 
							<label><%=userinfo.getEmail() %></label>
						</div>
						<hr>
						<div class="mem_inputDiv">
							<label class="mem_label">전화 번호</label> 
							<label><%=userinfo.getTel() %></label>
						</div>
						<hr>
						<div class="mem_inputDiv">
							<label class="mem_label">회원 가입일</label> 
							<label><fmt:formatDate value="<%=userinfo.getRegdate() %>" pattern="yyyy년  MM월 dd일  hh:mm:ss"/></label>
						</div>
					</div>
						<center>
						<form method="post" action="withdraw" name="withdraw_form">
							<input class="input_button" type="button" value="회원탈퇴" onclick="withdrawClick()">
						</form>
						</center>

			</div>
		</div>
	</div>
</body>
</html>