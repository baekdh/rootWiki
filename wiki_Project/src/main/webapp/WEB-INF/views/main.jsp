<%@page import="com.root.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ROOT WIKI</title>
<link rel="stylesheet" type="text/css" href="/rootWiki/resources/css/rootstyle.css">
<link rel="icon" type="image/png"  href="resources/images/rootwiki_icon.png"/>
<script type="text/javascript" src="/rootWiki/resources/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
<%
	String check = (String)request.getAttribute("msg");
	if(check != null) {
%>
	alert("<%=check%>");
	location.href="../main";
<%
	}
%>
	$(document).ready(function() {
		$(document).keydown(function(event) {
		    if (event.keyCode == '27') {
		    	document.getElementById('id01').style.display='none';
		    }
		});
	});
	function enterEvent(e) {
		if(e.keyCode==13)
			searchClick();
	}
	function searchClick() {
		var url_front = 'board/search?search_str=';
		var url_back = document.getElementById('search_str');
		if(url_back.value.trim() != "") {
			location.href = url_front.concat(url_back.value);
		}
	}
	function loginClick() {
		document.getElementById('id01').style.display = 'block';
		//location.href = 'login';
	}
	function logoutClick() {
		location.href = 'logout';
	}
</script>
</head>
<%-- <jsp:include page="header.jsp"/> --%>
<body id="main">
	<!-- 추가 : 로그인 화면 -->
	<div id="id01" class="login-modal">
		<div class="login-modal-content login-animate-top">
			<header class="login-container"> <span
				class="login-button login-display-topright"
				onclick="document.getElementById('id01').style.display='none'">×</span>
				<h2>Root Wiki Login</h2>
			</header>
			<div>
				<jsp:include page="member/login.jsp"/>
			</div>
		</div>
	</div>
	<p class="login">		
		<c:if test="${empty userid}">
			<input class="input_button" type="button" value="로그인" onclick="loginClick()">
		</c:if>
		<c:if test="${userid ne null}" >
			<a href="/mypage">마이페이지</a>
			<input class="input_button" type="button" value="로그아웃" onclick="logoutClick()">
		</c:if>
	</p>
	<p class="mainwiki">
		<img src="resources/images/MainWiki.png">
	</p><br>
	<div class="box">
		<div class="container-4">
			<input type="hidden" name="user_state" value="true">
			<input type="text" id="search_str" onkeypress="enterEvent(event);">
			<!-- <input type="submit" value="검색" id="search_btn"> -->
			<input class="search_btn" type="button" value="검색" id="search_btn" onclick="searchClick()">
		</div>
	</div>
</body>
<jsp:include page="footer.jsp"/>
</html>