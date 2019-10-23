<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		text-align:center;
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
			<h2 align="center">게시판 작성</h2>
			<div class="tableArea">
				<form action="<%= contextPath %>/insert.bo" method="post">
					<table>
						<tr>
							<td>분야</td>
							<td>
								<select name="category">
									<option>----</option>
									<option value="10">공통</option>
									<option value="20">운동</option>
									<option value="30">등산</option>
									<option value="40">게임</option>
									<option value="50">낚시</option>
									<option value="60">요리</option>
									<option value="70">기타</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>제목</td>
							<td colspan="3"><input type="text" size="58" name="title"></td>
						</tr>
						<tr>
							<td>내용</td>
							<td colspan="3">
								<textarea rows="15" cols="60" name="content" style="resize:none"></textarea>
							</td>
						</tr>
					</table>
					<br>
					<div align="center">
						<button type="button" onclick="javascript:history.back();">취소하기</button>
						<button id="submit" type="submit">등록하기</button>
					</div>
				</form>
			</div>
	</div>










</body>
</html>