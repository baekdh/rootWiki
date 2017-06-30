<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
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
		<h1>History -${title}-</h1>
		<c:if test="${backupList ne null}">
		  <table class="history_table">
		      <c:forEach var="vo" items="${backupList}">
		         <tr>
			         <td>${vo.backupNo}.</td>
			         <td>${vo.title}</td>
			         <td> ${vo.regdate} </td>
			           <td>(<a href="bkcontent?backupNo=${vo.backupNo}">보기</a> |
			           
			         <!-- 추가:롤백 -->
			         <a href="rollback?backupNo=${vo.backupNo}">이전으로 되돌리기</a>)</td>         
			         <td>수정목적:${vo.purpose}</td>
			         <td>
			 		 <% int flag = 0; %>
			         <c:if test="${vo.memberId ne null}">
			 		           작성자:${vo.memberId}
			         </c:if>
			         <c:if test="${vo.memberId eq null}">
			           	작성자:${vo.ip}
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