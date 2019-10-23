<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Insert title here</title>
<script>
	var msg = "<%= msg %>";
	
	$(function(){
		if(msg != "null"){
			alert(msg);
		}	
		if(msg == "성공적으로 비밀번호를 변경하였습니다."){
			window.close();
		}
	});
	
</script>
<style>
	h3 {
		text-align:center;
	}
	table {
		margin:auto;
	}
	td{
		text-align:right;
	}
	button{
		height:22px;
		width:100px;
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
	<h3>비밀번호 변경</h3>
	<br>
	<form id="updatePwdForm" action="<%= request.getContextPath() %>/updatePwd.me" 
	method="post" onsubmit="return checkPwd();">
		<table>
			<tr>
				<td><label>현재 비밀번호</label></td>
				<td width="50"></td>
				<td><input type="password" name="userPwd" id="userPwd" maxlength="15"></td>
			</tr>
			<tr>
				<td><label>변경할 비밀번호</label></td>
				<td></td>
				<td><input type="password" name="newPwd" id="newPwd" maxlength="15"></td>
			</tr>
			<tr>
				<td><label>변경할 비밀번호 확인</label></td>
				<td></td>
				<td><input type="password" name="newPwd2" id="newPwd2" maxlength="15"></td>
			</tr>
		</table>
		<br><br>
		<div class="btns" align="center">
			<button id="updatePwdBtn">변경하기</button>
		</div>
	</form>
	
	<script>
		function checkPwd(){
			var userPwd = $("#userPwd");
			var newPwd = $("#newPwd");
			var newPwd2 = $("#newPwd2");
			
			if(userPwd.val().trim() == "" || newPwd.val().trim() == "" 
					|| newPwd2.val().trim() == ""){
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			
			if(newPwd.val() != newPwd2.val()){
				alert("비밀번호가 다릅니다.");
				newPwd2.select();
				return false;
			}
			
			return true;
			
			
			
			
			
			
			
			
			
		}
	</script>
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>