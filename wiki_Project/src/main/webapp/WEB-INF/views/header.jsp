<%@page import="com.root.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% MemberVO userinfo = (MemberVO)request.getSession().getAttribute("user");
	String userid=null;
	if(userinfo == null){}
	else{
		userid = userinfo.getMemberid();
		System.out.print(userinfo.getMemberid());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/rootWiki/resources/css/rootstyle.css">
<script type="text/javascript" src="/rootWiki/resources/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).keydown(function(event) {
		    if (event.keyCode == '27') {
		    	document.getElementById('id01').style.display='none';
		    }
		});
		$("#search_str1").keypress(function(key) {
			if(key.keyCode == 13) {
				searchClick();
			}
		});
	});
	function searchClick() {
		var url_front = '/board/search?search_str=';
		var url_back = document.getElementById('search_str1');
		if(url_back.value.trim() != "") {
			location.href = url_front.concat(url_back.value);
		}
	}
	
	// 수정
	function loginClick() {
		document.getElementById('id01').style.display = 'block';
		//location.href = '/login';
	}
	function logoutClick() {
		location.href = '/logout';
	}
	function goHome(){
		location.href='/';
	}
</script>
</head>
<body>
	<div id="header">
		<div id="left_box">
			<input type="hidden" name="user_state" value="true">
			<a onclick="goHome()"><img width="250px;" height="50px;" alt="메인으로" src="/rootWiki/resources/images/mainwiki2.PNG" ></a>
		</div>
		<div id="right_box">
			<input type="text" id="search_str1">
			<input type="button" value="검색" id="search_btn1" onclick="searchClick()"> &nbsp;&nbsp;
			<%if(userid != null){%>
						<a href="/mypage">마이페이지</a>
				<input class="input_button" type="button" value="로그아웃" onclick="logoutClick()">
				<%}else{%>
				<input class="input_button" type="button" value="로그인" onclick="loginClick()">
			<!-- 추가  : 로그인 화면 나타나게 하기-->
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
			<%} %>
		</div><br><br>
	</div>
</body>
</html>