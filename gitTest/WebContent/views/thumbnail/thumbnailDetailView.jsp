<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.*, java.util.*"%>
<%
	Board b = (Board)request.getAttribute("board");
	// user_no,user_name의 형태로 넘어온 값을 나누어 줌
	String[] bWriter = b.getbWriter().split(",");
	
	int userNo = Integer.parseInt(bWriter[0]);
	b.setbWriter(bWriter[1]);

	ArrayList<Attachment> fileList = (ArrayList<Attachment>)request.getAttribute("fileList");
	Attachment titleImg = fileList.get(0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer {
		width:1000px;
		height:650px;
		background:black;
		color:white;
		margin:auto;
		margin-top:50px;
	}
	
	.detail td {
		text-align:center;
		width:1000px;
		border:1px solid white;
	}
	
	#titleImgArea {
		width:500px;
		height:300px;
		margin:auto;
	}

	#contentArea {
		height:30px;
	}
	
	.detailImgArea {
		width:250px;
		height:210px;
		margin:auto;
	}

	#titleImg {
		width:500px;
		height:300px;
	}
	
	.detailImg {
		width:250px;
		height:180px;
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
		<table class="detail" align="center">
			<tr>
				<td width="50px">제목</td>
				<td colspan="5"><label><%= b.getbTitle() %></label></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><label><%= b.getbWriter() %></label></td>
				<td>조회수</td>
				<td><label><%= b.getbCount() %></label></td>
				<td>작성일</td>
				<td><label><%= b.getModifyDate() %></label></td>
			</tr>
			<tr>
				<td>대표사진</td>
				<td colspan="4">
					<div id="titleImgArea" align="center">
						<img id="titleImg" src="<%= contextPath %>/resources/thumbnail_uploadFile/<%= titleImg.getChangeName() %>">
					</div>
				</td>
				<td>
					<button onclick="location.href='<%= contextPath %>/download.th?fid=<%= titleImg.getfId() %>'">다운로드</button>
				</td>
			</tr>
			<tr>
				<td>사진메모</td>
				<td colspan="6">
					<p id="contentArea"><%= b.getbContent() %></p>
				</td>
			</tr>
		</table>
	
		<table class="detail">
			<tr>
				<% for(int i = 1; i < fileList.size(); i++){ %>
				<td>
					<div class="detailImgArea">
						<img id="detailImg" class="detailImg" src="<%= contextPath %>/resources/thumbnail_uploadFile/<%= fileList.get(i).getChangeName() %>">
						<button onclick="location.href='<%= contextPath %>/download.th?fid=<%= fileList.get(i).getfId()%>'">다운로드</button>
					</div>
				</td>
				<% } %>
			</tr>
		</table>
	</div>

</body>
</html>