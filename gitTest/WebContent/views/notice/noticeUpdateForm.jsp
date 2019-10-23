<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="notice.model.vo.Notice"%>
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
	
	#submit {
		background:orangered;
		border:orangered;
	}

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<br>
		
		<h2 align="center">공지사항 수정하기</h2>
		<div class="tableArea">
			<form action="<%= contextPath %>/update.no" method="post">
			<input type="hidden" value="<%= n.getnNo() %>" name="nno">
			<table>
					<tr>
						<td>제목</td>
						<td colspan="3">
							<input type="text" size="50" name="title" value="<%= n.getnTitle() %>">
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
							style="resize:none"><%= n.getnContent() %></textarea>
						</td>
					</tr>
				</table>
				
				<br>
				
				<div align="center">
					<button type="button" onclick="javascript:history.back();">취소</button>
					<button type="submit" id="submit">수정하기</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>