<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.Board"%>
<%
	Board board = (Board)request.getAttribute("board");

	String category = board.getCategory();
	
	int cate = 0;
	switch(category){
	case "공통": cate = 10; break;
	case "운동": cate = 20; break;
	case "등산": cate = 30; break;
	case "게임": cate = 40; break;
	case "낚시": cate = 50; break;
	case "요리": cate = 60; break;
	case "기타": cate = 70; break;
	}
	
	String[] selected = new String[7];
	selected[(cate/10)-1] = "selected";

%>	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.outer {
		width:900px;
		height:500px;
		background:black;
		color:white;
		margin:auto;
		margin-top:50px;
	}
	
	table {
		border:1px solid white;
	}
	
	.tableArea {
		width:500px;
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

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시판 수정</h2>
		<div class="tableArea">
			<form action="<%= contextPath %>/update.bo" method="post">
				<table>
					<tr>
						<th>분야<input type="hidden" name="bId" value=<%= board.getbId() %>></th>
						<td>
							<select name="category">
								<option>--------</option>
								<option value="10" <%= selected[0] %>>공통</option>
								<option value="20" <%= selected[1] %>>운동</option>
								<option value="30" <%= selected[2] %>>등산</option>
								<option value="40" <%= selected[3] %>>게임</option>
								<option value="50" <%= selected[4] %>>낚시</option>
								<option value="60" <%= selected[5] %>>요리</option>
								<option value="70" <%= selected[6] %>>기타</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3"><input type="text" size="58" name="title" value="<%= board.getbTitle() %>"></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3">
							<textarea rows="15" cols="60" name="content" style="resize:none;"><%= board.getbContent() %></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<button type="submit" id="updateBtn">수정하기</button>
					<button type="button" onclick="location.href='<%= contextPath %>/detail.bo?bId='+<%= board.getbId() %>">취소하기</button>
				</div>
			</form>
		</div>
	</div>














</body>
</html>