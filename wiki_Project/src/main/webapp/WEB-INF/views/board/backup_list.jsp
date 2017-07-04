<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="icon" type="image/png"  href="resources/images/rootwiki_icon.png"/>
<script type="text/javascript">
	function enterEvent2(e) {
		if(e.keyCode==13)
			bSearchClick();
	}
	function bSearchClick() {
		var url_front = '/board/historyOpen?bSearch_str=';
		var url_back = document.getElementById('bSearch_str');
		location.href = url_front.concat(url_back.value);
	}
	function block_post(check, id, ip) {
		var form = document.createElement("form");
	    form.setAttribute("method", "post");
	    form.setAttribute("action", "../block");
	    
	    var hiddenField = document.createElement("input");
	    hiddenField.setAttribute("type", "hidden");
	    if(check == 0) {
	    	hiddenField.setAttribute("name", "id");
	    	hiddenField.setAttribute("value", id);
	    } else {
	    	hiddenField.setAttribute("name", "ip");
	    	hiddenField.setAttribute("value", ip);
	    }
	    form.appendChild(hiddenField);
	    document.body.appendChild(form);
	    form.submit();
	}
</script>
</head>
<jsp:include page="../header.jsp"/>
<body>
	<div class="content_box">
		<h1>히스토리</h1>
		<input type="text" id="bSearch_str" onkeypress="enterEvent2(event);">
		<input type="button" value="히스토리 검색" id="bSearch_btn" onclick="bSearchClick()">
		<br>검색결과<hr>
		<c:if test="${backupList ne null}">
		  <table class="history_table">
		      <c:forEach var="vo" items="${backupList}">
		         <tr>
			         <td>${vo.backupNo}.</td>
			         <td><b>${vo.title}</b></td>
			         <td> ${vo.regdate} </td>
			         <td>
			         	(<a href="bkcontent?backupNo=${vo.backupNo}">보기</a> |
				         <!-- 추가:롤백 -->
				         <a href="rollback?backupNo=${vo.backupNo}">이전으로 되돌리기</a>)
				     </td>         
			         <td><b>수정목적</b>:${vo.purpose}</td>
			         <td>
				 		 <% int flag = 0; %>
				 		 <b>작성자</b>:
				         <c:if test="${vo.memberId ne null}">
				 		 	${vo.memberId}
				         </c:if>
				         <c:if test="${vo.memberId eq null}">
				           	${vo.ip}
				 		     <% flag = 1; %>
				         </c:if>
			         </td>
			         <c:if test="${user.memberlevel eq 1}">
			         	<td>
			         		<a href="javascript:block_post(<%=flag %>, '${vo.memberId}', '${vo.ip}')">차단</a>
			         	</td>
			         </c:if>
		         </tr>
		      </c:forEach>
	      </table>
		</c:if>
		<c:if test="${backupList eq null}">
			수정 이력이 없습니다
		</c:if>
	</div>
</body>
<jsp:include page="../footer.jsp"/>
</html>