<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, notice.model.vo.Notice"%>
<% 
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
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
		width:650px;
		height:350px;
		margin:auto;
	}
	
	#listArea{
		border:1px solid white;
		text-align:center;
		margin:auto;
	}

	.searchArea {
		width:650px;
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
	
	#noticeInsert {
		background:orangered;
		border:orangered;
	}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">공지사항</h2>
		<div class="tableArea">
			<table id="listArea">
				<tr>
					<th>글번호</th>
					<th width="300">글제목</th>
					<th width="100">작성자</th>
					<th>조회수</th>
					<th width="100">작성일</th>
				</tr>
				<!-- 
					공지 사항 글이 존재하지 않을 수도 있다.
					ArrayList가 비었는지 안 비었는지를 조건으로 판단
				 -->
				 <%if(list.isEmpty()){ %>
				<tr>
					<td colspan="5">존재하는 공지사항이 없습니다.</td>
				</tr>
				<% } else { 
				for(Notice n : list){%>
				<tr>
					<td><%= n.getnNo() %></td>
					<td><%= n.getnTitle() %></td>
					<td><%= n.getnWriter() %></td>
					<td><%= n.getnCount() %></td>
					<td><%= n.getnDate() %></td>
				</tr>
				<%}
				}%>
			</table>
		</div>
	</div>
	
	<div class="searchArea" align="center">
		<select id="searchCondition" name="searchCondition">
			<option>----</option>
			<option value="writer">작성자</option>
			<option value="title">제목</option>
			<option value="content">내용</option>		
		</select>
		<input type="search" name="search">
		<button type="submit">검색하기</button>
		
		<!-- 공지사항 글쓰기 기능
			  공지사항은 보통 관리자만 사용하므로 관리자만 사용하는 조건을 걸기 -->
		<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
		<!-- 매번 request.getContextPath()가 너무 기므로 어느 페이지든 menubar.jsp
		를 포함하므로 그 곳에 변수로 만들어두기 -->
		<button id="noticeInsert" type="button" 
		onclick="location.href='<%= contextPath %>/views/notice/noticeInsertForm.jsp'">작성하기</button>
		<% } %>	
	
	</div>
	
	<script>
		// 3. 공지사항 상세보기 기능
		$(function(){
			$("#listArea td").mouseenter(function(){
				$(this).parent().css({"background":"darkgray", "cursor":"pointer"});
			}).mouseout(function(){
				$(this).parent().css("background", "black");
			}).click(function(){
				var num = $(this).parent().children().eq(0).text();
				// 쿼리 스트링을 이용하여 get방식으로(url 노출) 글번호를 server로 전달
				location.href = "<%= contextPath %>/detail.no?nno=" + num;
			});
		});
	
	</script>
	
	
	
	
	
	
	
	
	
	

</body>
</html>