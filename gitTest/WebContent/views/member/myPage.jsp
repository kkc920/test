<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Member m = (Member)session.getAttribute("loginUser");

	String userId = m.getUserId();
	String userPwd = m.getUserPwd();
	String userName = m.getUserName();
	String phone = (m.getPhone() != null) ? m.getPhone() : "";
	String email = (m.getEmail() != null) ? m.getEmail() : "";
	String address = (m.getAddress() != null) ? m.getAddress() : "";
	
	String[] checkInterest = new String[6]; 
	
	if(m.getInterest() != null){
		String[] interests = m.getInterest().split(",");
		
		for(int i = 0; i < interests.length; i++){
			switch(interests[i]){
			case "운동" : checkInterest[0] = "checked"; break;
			case "등산" : checkInterest[1] = "checked"; break;
			case "낚시" : checkInterest[2] = "checked"; break;
			case "요리" : checkInterest[3] = "checked"; break;
			case "게임" : checkInterest[4] = "checked"; break;
			case "기타" : checkInterest[5] = "checked"; break;
			
			}
		}
		
		
	}
	

%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer {
		width:600px;
		height:500px;
		background:black;
		color:white;
		margin-left:auto;
		margin-right:auto;
		margin-top:50px;
	}
	
	#updateForm table {
		width:100%;
		margin-left:auto;
		margin-right:auto;
	}
	
	#updateForm td {
		text-align:right;
	}
	
	button {
		height:22px;
		width:100px;
		background:yellowgreen;
		border:yellowgreen;
		color:white;
		border-radius:5px;
	}
	
	button:hover {
		cursor : pointer;
	}
	
	#pwdUpdateBtn {
		float:left;
	}
	
	#deleteBtn {
		background:orangered;
		border:orangered;
	}
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	
	<!-- memberJoinForm에 있는 table을 복사하기 -->
	<div class="outer">
		<br>
		<h2 align="center">정보 수정</h2>
		<form id="updateForm" name="updateForm" action="<%= request.getContextPath() %>/update.me" method="post">
			<table>
				<tr>
					<td width="200px">* 아이디</td>
					<td><input type="text" maxlength="13" name="userId" value="<%= userId %>" readonly></td>
					<td width="200px"></td>
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" maxlength="15" name="userPwd" readonly></td>
					<td><button id="pwdUpdateBtn" onclick="updatePwd();" type="button">비밀번호 변경</button></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" maxlength="5" name="userName" value="<%= userName %>" required></td>
				</tr>
				<tr>
					<td>연락처</td>
					<td><input type="tel" maxlength="11" name="phone" placeholder="(-없이 입력)" value="<%= phone %>"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value="<%= email %>"></td>
					<td></td>
				</tr>
				<tr>
					<td>주소</td>
					<td><input type="text" name="address" value="<%= address %>"></td>
				</tr>
				<tr>
					<td>관심분야</td>
					<td>
						<input type="checkbox" id="sports" name="interest" value="운동" <%= checkInterest[0] %>>
						<label for="sports">운동</label>
						<input type="checkbox" id="climbing" name="interest" value="등산" <%= checkInterest[1] %>>
						<label for="climbing">등산</label>
						<input type="checkbox" id="fishing" name="interest" value="낚시" <%= checkInterest[2] %>>
						<label for="fishing">낚시</label>
						<input type="checkbox" id="cooking" name="interest" value="요리" <%= checkInterest[3] %>>
						<label for="cooking">요리</label>
						<input type="checkbox" id="game" name="interest" value="게임" <%= checkInterest[4] %>>
						<label for="game">게임</label>
						<input type="checkbox" id="etc" name="interest" value="기타" <%= checkInterest[5] %>>
						<label for="etc">기타</label>
					</td>
					<td></td>
				</tr>
			</table>
			<br>
			
			<div class="btns" align="center">
				<button id="goMain" onclick="returnToMain();" type="button">메인으로</button>
				<button id="updateBtn">수정하기</button><br><br>
				<button id="deleteBtn" onclick="deleteMember();" type="button">탈퇴하기</button>
			</div>
		</form>
	</div>

	<script>
		// 1. 메인으로 돌아가기
		function returnToMain(){
			location.href = "<%= request.getContextPath() %>";
		}
		
		// 2. 비밀번호 변경 - 새 창 띄우기
		function updatePwd(){
			window.open("pwdUpdateForm.jsp", "비밀번호 변경 창", "width=500, height=300");
			
		}
		
		// 3. 회원 탈퇴
		function deleteMember(){
			var pwd = prompt("비밀번호를 입력해주세요.");
			
			if("<%= userPwd %>" == pwd){
				var bool = confirm("정말로 탈퇴하시겠습니까?");
				
				if(bool){
					// updateForm 속성 변경
					$("#updateForm").attr("action", "<%= request.getContextPath() %>/delete.me");
					// 그리고 submit 실행
					$("#updateForm").submit();
				}
			}else{
				alert("비밀번호를 잘못 입력하였습니다.");
			}
			
		}
		
		
		
		
		
	
	</script>
	
	
	
	
	
	
	
	
	
	

</body>
</html>