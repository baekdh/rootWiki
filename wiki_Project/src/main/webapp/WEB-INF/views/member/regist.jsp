<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="icon" type="image/png"  href="/resources/images/rootwiki_icon.png"/>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/rootstyle.css"/>"> --%>
<style>
	table{
		text-align: center;
	}
</style>
<script type="text/javascript">
<%
	String check = (String)request.getAttribute("msg");
	if(check != null) {
%>
		alert("<%=check%>");
<%
	}
%>

//추가 : ID 중복확인 버튼을 눌렀는지 확인
var idch_confirm = 0;
<%
	String idch = (String)request.getAttribute("idch");
	if(check != null){
%>
	idch_confirm = 1;
<%
	}
%>
	
	function idCheck() {
		if(document.reg_form.memberid.value == "") {
			alert("id를 입력하세요.");
		} else {
			document.reg_form.action = "idCheck";
			document.reg_form.submit();
		}
	}
	
	// 추가 : 회원가입 시 입력 양식 확인
	function regist_btn(){
		var regEex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
		
		if(document.reg_form.memberid.value == ""){
			alert("아이디를 입력하세요.");
		} else if((document.reg_form.memberid.value.length < 4) || (document.reg_form.memberid.value.length >20)){
			alert("아이디의 길이를 확인해 주세요. (4~20자)");
		} else if(idch_confirm == 0){
			alert("아이디 중복검사를 해주세요");
		} else if(document.reg_form.memberpw.value == ""){
			alert("비밀번호를 입력하세요.");
		} else if((document.reg_form.memberpw.value.length < 6) || (document.reg_form.memberpw.value.length >20)){
			alert("비밀번호의 길이를 확인해 주세요.(6~20자)");
		} else if(document.reg_form.pw_re.value == ""){
			alert("비밀번호 확인을 입력하세요.");
		}  else if(document.reg_form.memberpw.value != document.reg_form.pw_re.value){
			alert("비밀번호가 일치하지 않습니다.");
		} else if(document.reg_form.membername.value == ""){
			alert("이름을 입력하세요.");
		} else if((document.reg_form.membername.value.length < 2) || (document.reg_form.membername.value.length >6)){
			alert("이름의 길이를 확인해 주세요. (2~6자)");
		} else if(document.reg_form.email.value == ""){
			alert("이메일을 입력하세요.");	
		}  else if(regEex.test(document.reg_form.email.value) === false){
			alert("잘못된 이메일 형식입니다.");		
		} else if(document.reg_form.tel.value == ""){
			alert("전화번호를 입력하세요.");		
		} else if(regExp.test(document.reg_form.tel.value) === false){
			alert("잘못된 전화번호 형식입니다.");		
		}	else {
			alert(document.reg_form.membername.value+"님 회원가입을 축하합니다.")
			document.reg_form.submit();
		}
	}
	//추가 : 취소버튼 -> main으로 이동
	function regist_cancel(){
		location.href = "main";
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
				<h1>Root Wiki 회원가입</h1>
				<form:form method="post" commandName="memberVO" action="regist"	name="reg_form">
				<div class="mem_row_group">
					<div class="mem_inputDiv">
						<label class="mem_label">사용자 ID</label>
						<input type="text" name="memberid" value="${memberVO.memberid}" placeholder="아이디를 입력하세요" maxlength="20">
						<input class="input_button" type="button" value="중복검사" onclick="idCheck()">
					</div>
					<div class="mem_inputDiv">
						<label class="mem_label">비밀번호</label>
						<input type="password" name="memberpw" value="${memberVO.memberpw}">
					</div>
					<div class="mem_inputDiv">
						<label class="mem_label">비밀번호확인</label>
						<input type="password" name="pw_re">
					</div>
				</div>
				
				<div class="mem_row_group">
					<div class="mem_inputDiv">
						<label class="mem_label">이름</label>
						<input type="text" name="membername" value="${memberVO.membername}">
					</div>
					
					<div class="mem_inputDiv">
						<label class="mem_label">E-mail</label>
						<input type="text" name="email"	value="${memberVO.email}">
					</div>
					
					<div class="mem_inputDiv">
						<label class="mem_label">전화번호</label>
						<input type="text" name="tel" value="${memberVO.tel}">
					</div>
				</div>
				<center>
					<input class="input_button" type="button" value="회원가입" onclick="regist_btn()">
					<input class="input_button" type="button" value="취소" onclick="regist_cancel()">
				</center>
				</form:form>
			</div>
		</div>
	</div>
</body>
<!-- footer -->
<jsp:include page="../footer.jsp"/>
<!-- /footer -->
</html>