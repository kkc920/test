<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="notice.model.vo.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("notice");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.outer {
		width:800px;
		height:500px;
		background:black;
		color:white;
		margin:auto;
		margin-top:50px;
	}
	
	.tableArea {
		width:450px;
		height:350px;
		margin:auto;
	}
	
	button{
		height:22px;
		width:80px;
		background:yellowgreen;
		border:yellowgreen;
		color:white;
		border-radius:5px;
	}
	button:hover{
		cursor:pointer;
	}
	
	#deleteBtn, #updateBtn {
		background:orangered;
		border:orangered;
	}

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 상세보기</h2>
		<div class="tableArea">
		<!-- noticeInsertForm.jsp table만 복사 -->
			<table>
					<tr>
						<td>제목</td>
						<td colspan="3">
							<input type="text" size="50" name="title" value="<%= n.getnTitle() %>" readonly>
						</td>
					</tr>
					<tr>
						<td>작성자</td>
						<td>
							<input type="text" value="<%= n.getnWriter() %>"
							name="writer" readonly>
						</td>
						<td>작성일</td>
						<td><input type="date" name="date" value="<%= n.getnDate() %>" readonly></td>
					</tr>
					<tr>
						<td>내용</td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea name="content" cols="60" rows="15"
							style="resize:none" readonly><%= n.getnContent() %></textarea>
						</td>
					</tr>
				</table>
				
				<br>
				
				<div align="center">
					<button onclick="location.href='<%= contextPath %>/list.no'">목록으로</button>
					<!-- 글을 보는 회원이 admin인 경우 수정하기, 삭제하기 버튼이 보이도록 -->
					<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
						<button id="updateBtn" 
						onclick="location.href='<%= contextPath %>/updateForm.no?nno=<%= n.getnNo() %>'">
						수정하기</button>
						<button id="deleteBtn" type="button" onclick="deleteNotice();">삭제하기</button>
					<% } %>
				</div>
		</div>
	</div>
	
	<script>
		// 공지사항 삭제하기
		function deleteNotice(){
			if(confirm("해당 글을 삭제하시겠습니까?")){
				location.href="<%= contextPath %>/delete.no?nno=" + <%= n.getnNo() %>;
				// NoticeDeleteServlet을 만들어서 (url 맵핑 : delete.no)
				// 삭제가 잘 됐을 경우 -> 공지사항 목록
				// 삭제 실패 -> msg 에러 페이지로
			}
		}
	</script>
</body>
</html>