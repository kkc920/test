<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, board.model.vo.*"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	int listCount = pi.getListCount();
	int currentPage = pi.getCurrentPage();
	int maxPage = pi.getMaxPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
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
		text-align:center;
	}
	
	.tableArea {
		width:650px;
		height:350px;
		margin:auto;
	}
	
	.searchArea {
		width:650px;
		margin:auto;
	} 
	
	#searchBtn, #insertBtn{
		height:22px;
		width:80px;
		background:yellowgreen;
		border:yellowgreen;
		color:white;
		border-radius:5px;
	}
	#searchBtn:hover, #insertBtn:hover{
		cursor:pointer;
	}

</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		
		<h2 align="center">게시판</h2>
		<div class="tableArea">
			<table align="center" id="listArea">
				<tr>
					<th width="100">글번호</th>
					<th width="100">카테고리</th>
					<th width="300">글제목</th>
					<th width="100">작성자</th>
					<th width="100">조회수</th>
					<th width="150">작성일</th>
				</tr>
				<% if(list.isEmpty()){ %>
				<tr>
					<td colspan="6">조회된 리스트가 없습니다.</td>
				</tr>
				<% } else { %>
					<% for(Board b : list) {%>
					<tr>
						<input type="hidden" value="<%= b.getbId() %>">
						<td><%= b.getbId() %></td>
						<td><%= b.getCategory() %></td>
						<td><%= b.getbTitle() %></td>
						<td><%= b.getbWriter() %></td>
						<td><%= b.getbCount() %></td>
						<td><%= b.getModifyDate() %></td>
					</tr>
					<% } %>
				<% } %>
			
			</table>
		</div>
		
		<!-- 페이징 바 -->
		<div class="pagingArea" align="center">
			<!-- 맨 처음으로 (<<) -->
			<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=1'"> &lt;&lt; </button>
			
			<!-- 이전 페이지로 (<) -->
			<% if(currentPage == 1){ %>
				<button disabled> &lt; </button>
			<% } else { %>
				<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= currentPage - 1 %>'"> &lt; </button>
			<% } %>
			
			<!-- 10개의 페이지 목록 -->
			<% for(int p = startPage; p <= endPage; p++){ %>
				<% if(p == currentPage){ %>
					<button disabled> <%= p %> </button>
				<% } else { %>
					<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= p %>'"><%= p %></button>
				<% } %>
			<% } %>
			
			<!-- 다음 페이지로 (>) -->
			<% if(currentPage == maxPage){ %>
				<button disabled> &gt; </button>
			<% } else { %>
				<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= currentPage + 1 %>'"> &gt; </button>
			<% } %>
			
			<!-- 맨 끝으로 (>>) -->
			<button onclick="location.href='<%= contextPath %>/list.bo?currentPage=<%= maxPage %>'"> &gt;&gt; </button>
		</div>
		
		<!-- 공지 사항 때와 마찬가지로 검색 부분을 만들기 (기능 구현은 우선 X) -->
		<div class="searchArea" align="center">
			<select id="searchCondition" name="searchCondition">
				<option>-----</option>
				<option value="category">카테고리</option>
				<option value="writer">작성자</option>
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="search">
			<button id="searchBtn" type="submit">검색하기</button>
			
			<% if(loginUser != null){ %>
				<button id="insertBtn" onclick="location.href='<%= contextPath %>/views/board/boardInsertForm.jsp'">작성하기</button>
			<% } %>
		</div>
		<!-- 게시판 목록을 DB에서 가져오기 위한 쿼리문이 길고 복잡함
		긴 쿼리는 유지보수도 힘들고 매번 DBMS에 보내는 것도 비효율적이므로 미리 여러 테이블
		에서 원하는 정보만을 추출해 하나의 가상 테이블로 만드는 View를 이용해보자! -->
	</div>


	<script>
		// 게시판 상세보기 기능 구현
		$(function(){
			$("#listArea td").mouseenter(function(){
				$(this).parent().css({"background":"darkgray", "cursor":"pointer"});
			}).mouseout(function(){
				$(this).parent().css({"background":"black"});
			}).click(function(){
				var bId = $(this).parent().children("input").val();
				
				// 게시판 상세 내용은 로그인 한 사람만 이용 가능하도록
				<% if(loginUser != null){%>
					location.href="<%= contextPath %>/detail.bo?bId="+bId;
				<% } else { %>
					alert("로그인 해야만 게시글 상세보기가 가능합니다!");
				<% } %>
				
			});
			
			
			
		});
	
	
	
	</script>












</body>
</html>