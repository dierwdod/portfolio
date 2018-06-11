<%-- 프런트 컨트롤러 적용 - 링크에 .do 붙임 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.myapart.app.model.*"%>
	
<% 
	request.setCharacterEncoding("UTF-8");
	Member member = null;
	if(session.getAttribute("loginMember") != null){
		member = (Member)session.getAttribute("loginMember");
	}
%>	
	
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
	<title>MyApart</title>
	<style>
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:250px;
		}
		
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			width: 70%;
			min-width:1000px;
		    height: 95%;
			margin: auto;
		}
		div.board_title>h1{
			margin-left:20px;
		}
		/* 게시판안 내용 지정*/
		div.board_contents {
			position: relative;
			top: 40px;
			width: 90%;
			min-width:860px;
			height: 85%;
			margin: auto;
			border-radius: 12px;
			background-color: white;
			overflow-x: hidden;
			overflow-y: auto;
		}
		div.contents_middle {
			position:inherit;
			top:10px;
			width:95%;
			height:600px;
			overflow-x:hidden;
			overflow-y:auto;
			margin-left:auto;
			margin-right:auto;
		}
		div.contents_middle>table{
			position:inherit;
			top:5px;
			width:100%;
			height:auto;
		}
		.line {
			width:100%;
			height:4px;
			background-color:#000000;
		}
		/*하단 바 + 등록 버튼 */
		.contents_bottom {
			position:absolute;
			bottom:0;
			width:100%;
			height:10%;
		}
		.contents_bottom>input[type=button], input[type=submit]{
			float:right;
			margin-top:5px;
			margin-right:15px;
		}
	</style>
	<script>
				
		function check(){
			with(document.update){
				if(sugTitle.value.length == 0){
					alert("제목을 입력해 주세요!");
					sugTitle.focus();
					return false;
				}
				if(sugContents.value.length == 0){
					alert("내용을 입력해 주세요!");
					sugContents.focus();
					return false;
				}
				document.update.submit();
			}
		}
		$(function() {
		  	$('#contents').keyup(function (e){
	          	var content = $(this).val();
	          	$('#counter').html(content.length + '/300');
	      	});
	      	$('#contents').keyup();
		});	
	</script>
</head>

<body>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src='/MyApart/resources/image/apt.jpg' alt="아파트"
				style="width: 100%; height: 100%;">
		</div>
	</div>

	<div class="board">
		<div class="board_title">
			<h1>건의사항 작성</h1>
		</div>
		<div class="board_contents">
			<form name="update" action='/MyApart/suggest/update' method='post'>
			<div class="contents_middle">
			
			<%
				Suggest suggest = (Suggest)request.getAttribute("suggest");
				String sug_reg_date = suggest.getSugDate().substring(0, 11);
			%>
				<table width="100%" style="margin-top:15px;">
					<tr>
						<td width="10%" style="font-size:small;" align="left">제목 : </td>
						<td width="68%" style="font-weight:bold;">
							<input type="text" style="width:100%; height:25px;" name="sugTitle" maxlength="30"
								 placeHolder="제목을 입력해 주세요. (30자 이내)" value="<%=suggest.getSugTitle() %>"></td>
						<td align="right" style="font-size:small;" width="10%" >NO. <%=suggest.getSugNum() %></td>
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr style="font-size:small;">
						<td width="10%" align="left">글쓴이 : </td>
						<td width="62%"><%=suggest.getId() %></td>
						<td align="right" width="23%" >등록날짜 : <%=sug_reg_date %></td>
						
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr>
						<td colspan=3>
							<textarea style="width: 99%; height: 425px; resize:none;" id="contents"
								name=sugContents maxlength="300" placeHolder="내용을 입력해 주세요. (300자 이내)"
								
								class="textarea_style1"><%=suggest.getSugContents() %> </textarea>
						</td>
					</tr>
					<tr>
						<td colspan=3 align=right style="font-size:small"><span id="counter">0/300</span></td>
					</tr>
				</table>
			</div>
		
			<div class="contents_bottom">
				<hr class="line">
				<input type="hidden" name="sugNum" value="<%=suggest.getSugNum() %>">
				<input type="hidden" name="id" value="<%=suggest.getId() %>">
				
				<input type="button" value="취소" onclick='javascript:history.back();'>
				<input type="button" value="완료" onclick="check(this.form);">
							
			</div>
			</form>
		</div>
	
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>