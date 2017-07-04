<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.root.domain.MemberVO"%>
<%@ page import="com.root.domain.ReplyVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"	href="/rootWiki/resources/css/rootstyle.css">
<link rel="icon" type="image/png"  href="/resources/images/rootwiki_icon.png"/>
<script type="text/javascript" src="/rootWiki/resources/js/jquery-2.1.1.js" ></script>
<script type="text/javascript">
	<!-- 글자수 제한 댓글 작성창 스크립트 -->
	$(document).ready(function() {
	      $('#r_content').keyup(function (e){
	          var content = $(this).val();
	          $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
	          $('#counter').html(content.length + '/150');
	      });
	      $('#r_content').keyup();
	});
	function updateBoard() {
		location.href = 'update?boardNo='+${boardVO.boardNo};
	}
	function layer_toggle(obj) {
        if (obj.style.display == 'none') obj.style.display = 'block';
        else if (obj.style.display == 'block') obj.style.display = 'none';
	}
	function hit(){
		document.hit_form.hit_val.value = 1;
		document.hit_form.submit();
	}
	function nonhit(){
		document.hit_form.hit_val.value = 0;
		document.hit_form.submit();
	}
	function goHistory(){
		location.href = 'history?boardNo='+${boardVO.boardNo};
	}
	
	/*추가: 댓글  */
	function replyInsert(){
		document.reply_form.submit();
	} 
	function replyDelete(r_no){
		if(confirm("삭제하시겠습니까?")) {
			location.href="../reply/replyDelete?r_boardNo=${boardVO.boardNo}&r_no="+r_no;
		}
	}
	function replyUpdate(r_no){
		window.name = "parentForm";
		window.open("../reply/replyUpdate?r_no="+r_no,"updateForm",
				"width=550px, height=300px, resizable = no, scrollbars = no");
	}
	
	function replyPage(page) {
		location.href = "content?boardNo=${boardVO.boardNo}&replyPage="+page;
	}
</script>
</head>
<jsp:include page="../header.jsp" />
<body>
	<div class="content_box">
		<form action="hit" method="post" name="hit_form">
			<c:if test="${msg ne null}">
				<h1>${msg}<a href="content?boardNo=${boardVO.boardNo}&replyPage=1" style="float:right;">본문으로 가기</a></h1>
			</c:if>
			<input type="hidden" name="boardNo" value="${boardVO.boardNo}">
			<!--추가:backNo를 넘겨줄 hidden 추가-->
			<input type="hidden" name="backupNo" value="${backupNo}"> <input
				type="hidden" name="hit_val" value="0">

			<div class="c_title">
				<h1>${boardVO.title}</h1>
			</div>
			<div class="right_box">
				<c:if test="${empty msg}">
					<input type="button" value="수정" onclick="updateBoard()"
						class="input_button">
					<!-- 삭제버튼 제거  <input type="button" value="삭제" onclick="deleteBoard()">  -->
				</c:if>
				<input type="button" value="수정내역" onclick="goHistory()"
					class="input_button">
			</div>
			<br>
			<div class="right_box">
				최근수정시각 : <fmt:formatDate value="${boardVO.regdate}"
								pattern="yyyy-MM-dd(E) a KK:mm" />
			</div>
			<c:if test="${msg ne null}">
				<br><br><a href="rollback?backupNo=${backupNo}">이 버전으로 되돌리기</a>
			</c:if>
			<div class="c_content">${boardVO.content}</div>
			<div class="right_box">
				<div class="c_hit">
					추천 : ${hit_cnt}<br> 비추천 : ${nonhit_cnt}
				</div>
				<br>
				<br>
				<input type="button" value="추천" onclick="hit()"
					class="input_button">
				<input type="button" value="비추천" onclick="nonhit()"
					class="input_button">
			</div>
		</form>

		<div class="reply">
			<!-- 댓글 조회 -->
			<!-- 디자인 및 위치 수정함 -->
			<c:if test="${replyList ne null}">
				<c:forEach var="rvo" items="${replyList}">

					<form:form action="../reply/replyDelete" method="post"
						name="r_form" commandName="ReplyVO">
						<input type="hidden" name="r_boardNo" value="${boardVO.boardNo}">
						<input type="hidden" name="r_no" value="${rvo.r_no}">
						<table>
							<tr>
								<td class="idsize">${rvo.r_writer}</td>
							</tr>
							<tr>
								<td colspan="3">

									<div id="target" class="writesize">
										<p id="p${rvo.r_no}">${rvo.r_content}</p>
									</div>

									<div class="size">
										<fmt:formatDate value="${rvo.r_regdate}"
											pattern="yyyy-MM-dd hh:mm:ss" />

										<c:if test="${rvo.r_writer == sessionScope.user.getMemberid() }">
											<input class="edit_button" type="button" value="수정" onclick="replyUpdate(${rvo.r_no})">
											<input class="edit_button" type="button" value="삭제" onclick="replyDelete(${rvo.r_no})">
										</c:if>
										<c:if test="${rvo.r_writer == crr_ip and empty sessionScope.user.getMemberid() }">
											<input class="edit_button" type="button" value="수정" onclick="replyUpdate(${rvo.r_no})">
											<input class="edit_button" type="button" value="삭제" onclick="replyDelete(${rvo.r_no})">
										</c:if>
									</div>
									<hr>
								</td>
							</tr>
						</table>
					</form:form>
				</c:forEach>
				<%
					if (request.getAttribute("count").toString() != null) {
						int count = Integer.parseInt(request.getAttribute("count").toString());
						int pagecount = count / 5;
						int repage = Integer.parseInt(request.getParameter("replyPage"));
						if (count % 5 != 0) {
							pagecount++;
						}
						for (int i = 1; i <= pagecount; i++) {
				%>
							<div class="reply_page" onclick="replyPage(<%=i%>)" id="r_page<%=i%>">
	 						<% if(repage == i) { %>
								<b style="color:#ff7400;"><%=i%></b>
							<% } else { %>
								<b><%=i%></b>
	 						<% } %>
							</div>
				<%
						}
					}
			   	%>
			</c:if>
			<br>

			<!--  댓글 작성 페이지  아래로 위치 이동 -->

			<!------------------------ 댓글 작성  및 조회--------------------------->
			<!-- 본문에서만 댓글 작성 가능하게 만들기  -->

			<!-- 이전 댓글 작성창 -->
			<%-- 	<c:if test="${re_hidden eq null}">
		<form:form action="../reply/replyInsert" method="post" commandName="ReplyVO" name="reply_form">
			<input type="hidden" name="r_boardNo" value="${boardVO.boardNo}">
			<textarea rows="5" cols="80" name="r_content" placeholder="댓글을 작성해주세요"></textarea>
			<!-- <input type="submit" value="등록"/> -->
			<input type="button" value="등록" onclick="replyInsert()">
		</form:form>
	</c:if> --%>

			<!-- 글자수 제한 댓글 작성창 -->
			<c:if test="${re_hidden eq null}">
				<div class="wrap">
					<form:form action="../reply/replyInsert" method="post"
						commandName="ReplyVO" name="reply_form">
						<input type="hidden" name="r_boardNo" value="${boardVO.boardNo}">
						<textarea id="r_content" maxlength="150" name="r_content"
							placeholder="댓글을 작성해주세요"></textarea>
						<span id="counter">###</span>
						<!-- <input type="submit" value="등록"/> -->
						<input class="input_button" type="button" value="등록" onclick="replyInsert()">
					</form:form>
				</div>
			</c:if>
		</div>
	</div>
</body>
<jsp:include page="../footer.jsp" />
</html>