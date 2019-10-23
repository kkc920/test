<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	table {
		border:1px solid white;
	}
	
	.insertArea {
		width:500px;
		height:450px;
		margin:auto;
	}

	#titleImgArea {
		width:350px;
		height:200px;
		border: 2px dashed darkgray;
		text-align:center;
		display:table-cell;
		vertical-align:middle;
	}
	
	#contentImgArea1, #contentImgArea2, #contentImgArea3 {
		width:150px;
		height:100px;
		border:2px dashed darkgray;
		text-align:center;
		display:table-cell;
		vertical-align:middle;
	}
	
	#titleImgArea:hover, #contentImgArea1:hover,
	#contentImgArea2:hover, #contentImgArea3:hover {
		cursor:pointer;
	}

	.btnArea {
		width:200px;
		margin:auto;
	}
	
	button {
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
		<h2 align="center">사진 게시판 작성</h2>
		
		<!-- 파일 업로드를 위해서는 enctype을 지정해줘야 함 -->
		<form action="<%= contextPath %>/insert.th" method="post" enctype="multipart/form-data">
			<div class="insertArea">
				<table align="center">
					<tr>
						<td width="100px">제목</td>
						<td colspan="3"><input type="text" size="45" name="title"></td>
					</tr>
				    <tr>
				    	<td>대표 이미지</td>
				    	<td colspan="3">
				    		<div id="titleImgArea">
				    			<img id="titleImg" width="350" height="200">
				    		</div>
				    	</td>
				    </tr>
				    <tr>
				    	<td>내용 사진</td>
				    	<td>
				    		<div id="contentImgArea1">
				    			<img id="contentImg1" width="120" height="100">
				    		</div>
				    	</td>
				    	<td>
				    		<div id="contentImgArea2">
				    			<img id="contentImg2" width="120" height="100">
				    		</div>
				    	</td>
				    	<td>
				    		<div id="contentImgArea3">
				    			<img id="contentImg3" width="120" height="100">
				    		</div>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="100px">내용</td>
				    	<td colspan="3"><textarea name="content" rows="5" cols="50" style="resize:none;"></textarea></td>
				    </tr>
				</table>
				
				<!-- 파일 업로드 하는 부분 -->
				<div id="fileArea">
					<input type="file" id="thumbnailImg1" name="thumbnailImg1" onchange="LoadImg(this,1)">
					<input type="file" id="thumbnailImg2" name="thumbnailImg2" onchange="LoadImg(this,2)">
					<input type="file" id="thumbnailImg3" name="thumbnailImg3" onchange="LoadImg(this,3)">
					<input type="file" id="thumbnailImg4" name="thumbnailImg4" onchange="LoadImg(this,4)">
				</div>
			
				<script>
				// 내용 작성 부분의 공간을 클릭할 때 파일 첨부 창이 뜨도록 설정하는 함수
				$(function(){
					$("#fileArea").hide();
					
					$("#titleImgArea").click(function(){
						$("#thumbnailImg1").click();
					});
					
					$("#contentImgArea1").click(function(){
						$("#thumbnailImg2").click();
					});
					
					$("#contentImgArea2").click(function(){
						$("#thumbnailImg3").click();
					});
					
					$("#contentImgArea3").click(function(){
						$("#thumbnailImg4").click();
					});
					
				});
				
				// 파일 첨부 했을 때 미리보기 공간에 미리보기가 가능하게 하는 함수
				function LoadImg(value, num){
					// value => input type="file"
					// num => 이후에 조건문 작성하여 번호에 맞춰서 img 적용 시킬 것
					
					// file이 존재하는지 확인
					if(value.files && value.files[0]){
						// 파일을 읽어 들일 FileReader 객체 생성
						var reader = new FileReader();
						
						// 파일 읽기가 다 완료 되었을 때 실행되는 메소드
						reader.onload = function(e){
							
							switch(num){
							case 1:
								// e.target.result -> data:URL (파일의 컨텐츠)
								$("#titleImg").attr("src", e.target.result);
								break;
							case 2:
								$("#contentImg1").attr("src", e.target.result);
								break;
							case 3:
								$("#contentImg2").attr("src", e.target.result);
								break;
							case 4:
								$("#contentImg3").attr("src", e.target.result);
								break;
							}
						}
						// 파일 내용을 읽어들여 dataURL 형식의 문자열로 설정
						reader.readAsDataURL(value.files[0]);
					}
				}
				</script>
			</div>
			<br>
			<div class="btnArea">
				<button type="button" onclick="javascript:history.back();">취소하기</button>
				<button type="submit">작성완료</button>
			</div>
		</form>
	
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>