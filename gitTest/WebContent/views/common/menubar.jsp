<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="member.model.vo.Member"%>
<%
	// 2_1_1. session 객체에 담겨진 loginUser 정보를 변수에 담기
	Member loginUser = (Member)session.getAttribute("loginUser");

	// 회원 가입 성공 메세지 출력용
	String msg = (String)session.getAttribute("msg");
	
	//request.getContextPath() 변수화
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	var msg = "<%= msg %>";
	$(function(){
		if(msg != "null"){
			alert(msg);
			<% session.removeAttribute("msg"); %>
		}
	});

</script>
<style>
	body {
		background: url('<%= request.getContextPath() %>/resources/images/city1.PNG') no-repeat;
		background-size : cover;
	}
	
	.loginArea > form, #userInfo {
		float:right;
	}
	
	#memberJoinBtn, #loginBtn, #myPage, #logoutBtn {
		width:110px;
		height:25px;
		color:white;
		border-radius:5px;
		margin-top:5px;
	}
	
	#memberJoinBtn, #myPage {
		background:yellowgreen;
		border:yellowgreen;
	}
	
	#loginBtn, #logoutBtn {
		background:orangered;
		border:orangered;
	
	}
	
	#loginBtn:hover, #memberJoinBtn:hover, #myPage:hover, #logoutBtn:hover {
		cursor:pointer;
	}
	
	.wrap {
		background:black;
		width:100%;
		height:50px;
	}
	
	.nav {
		width:600px;
		margin:auto;
	}
	
	.menu {
		background:black;
		color:white;
		text-align:center;
		vertical-align:middle;
		width:150px;
		height:50px;
		display:table-cell;
	}
	
	.menu:hover {
		background:darkgray;
		color:orangered;
		font-weight:bold;
		cursor:pointer;
	}
</style>
</head>
<body>
	<h1 align="center">Welcome to JSP World!!!</h1>
	<div class="loginArea">
	<!-- 2_1. 로그인이 안 되어 있는 경우와 되어 있는 경우를 다르게 보여주기 위해서
	          if문을 추가 -->
	
	<!-- 로그인이 되어 있지 않은 경우 로그인 관련 폼을 보여줌 -->
	<% if(loginUser == null){ %>
	<!-- 1_1. 로그인 관련 폼 만들기 -->
	<form id="loginForm" action="<%= request.getContextPath() %>/login.me" method="post" onsubmit="return validate();">
		<table>
			<tr>
				<td><label>ID : </label></td>
				<td><input type="text" name="userId" id="userId"></td>
			</tr>
			<tr>
				<td><label>PWD : </label></td>
				<td><input type="password" name="userPwd" id="userPwd"></td>
			</tr>
		</table>
		<div class="btns" align="center">
			<button id="memberJoinBtn" onclick="memberJoin();" type="button">회원가입</button>
			<button id="loginBtn" type="submit">로그인</button>
		</div>
	</form>
	<!-- 2_2. 로그인이 성공적으로 됐을 경우 -->
	<% } else { %>
	<div id="userInfo">
		<label><%= loginUser.getUserName() %>님의 방문을 환영합니다.</label>
		<div class="btns" align="center">
			<!-- 이제부터 마이페이지 작업 시작 -->
			<!-- 원래는 페이지로 바로 전환이 안되서 항상 단순 페이지 전환 시에도 서블릿을
				 거쳐서 가야 함. 하지만 우리가 가고자 하는 페이지가 WEB-INF안에 없고 밖에 있다면
				 WAS 서버를 거칠 필요가 없음!!
			 -->
			<button id="myPage" onclick="location.href='<%= request.getContextPath() %>/views/member/myPage.jsp';">정보수정</button>
			<button id="logoutBtn" onclick="logout();">로그아웃</button>
		</div>
	</div>
	<% } %>
	</div>

	<script>
		// 1_2. validate() 함수 작성하기 -> CDN 추가
		function validate(){
			if($("#userId").val().trim().length==0){
				alert("아이디를 입력하세요");
				$("#userId").select();
				return false;
			}
			
			if($("#userPwd").val().trim().length==0){
				alert("비밀번호를 입력하세요");
				$("#userPwd").select();
				return false;
			}
			
			return true;
			// 여기까지 작성 했으면 LoginServlet 만들러 가기 ~!!
		}
		
		// 2_3. logout() 작성하기
		function logout(){
			// logout 버튼을 눌렀을 경우 logout.me url과 매핑 되는 servlet으로
			// 이동하여 logout 처리 진행
			location.href = '<%= request.getContextPath() %>/logout.me';
			
			//LogoutServlet 만들러 가기~!
		}
		
		// 3. 회원 가입 버튼을 눌렀을 경우 회원 가입 페이지로 이동하는 memberJoin() 작성
		function memberJoin(){
			location.href="<%= request.getContextPath() %>/views/member/memberJoinForm.jsp";
		}
	</script>
	<!-- [2] 회원 관련 서비스를 모두 마치면 이제부터 공지사항 및 게시판 작업 시작 -->
	<br clear="both">
	<br>
	
	<div class="wrap">
		<div class="nav">
			<div class="menu" onclick="goMain();">HOME</div>
			<div class="menu" onclick="goNotice();">공지사항</div>
			<div class="menu" onclick="goBoard();">게시판</div>
			<div class="menu" onclick="goThumbnail();">사진게시판</div>
		</div>
	</div>
	
	<script>
		function goMain(){
			location.href="<%=request.getContextPath()%>";
		}
		
		// 1. 공지사항 먼저 작업해보자!
		function goNotice(){
			location.href="<%=request.getContextPath()%>/list.no";
		}
		
		// 2. 게시판 작업하자!!
		function goBoard(){
			location.href="<%= contextPath %>/list.bo";
			
		}
		
		// 3. 사진 게시판 작업하자!!
		function goThumbnail(){
			location.href="<%= contextPath %>/list.th";
		}
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	








</body>
</html>