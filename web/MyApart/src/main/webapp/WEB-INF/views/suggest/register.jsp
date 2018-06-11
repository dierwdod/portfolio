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
	<script type="text/javascript" src="/MyApart/resources/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>
	
	<title>MyApart</title>
	<style>
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:250px;
		}
		div.board_title>h1{
			margin-left:20px;
		}
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			width: 70%;
			min-width:1000px;
		    height: 95%;
			margin: auto;
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
			with(document.add){
				if(sugTitle.value.length == 0){
					alert("제목을 입력해 주세요!");
					sugTitle.focus();
					return false;
				}
				oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD",[]);
				try {
					document.add.submit();		
				}
				catch(e){}
			}
		}
		
		
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
			<h1>건의사항</h1>
		</div>
		<div class="board_contents">
			<form name="add" action='/MyApart/suggest/register' method='post'>
			<div class="contents_middle">
				<table width="100%" style="margin-top:15px;">
					<tr>
						<td width="8%" style="font-size:small;" align="center">제목 : </td>
						<td  style="font-weight:bold;">
							<input type="text" style="width:100%; height:25px;" maxlength="30" name="sugTitle" placeHolder="제목을 입력해주세요. (30자 이하)">
						</td>
					</tr>
					<tr><td colspan="3"><hr></td></tr>
					<tr>
						<td colspan=3 >
							<textarea style="width:99%; height: 450px; resize:none;" id="ir1"
								name=sugContents maxlength="300" placeHolder="내용을 입력해 주세요. (300자 이하)"></textarea>
							<script type="text/javascript">
								var oEditors=[];
								nhn.husky.EZCreator.createInIFrame({
									oAppRef: oEditors,
									elPlaceHolder: "ir1",
									sSkinURI: "/MyApart/resources/smarteditor/SmartEditor2Skin.html",
									fCreator:"createSEditor2"
								});
							</script>
						</td>
					</tr>
				
				</table>
			</div>
		
			<div class="contents_bottom">
				<hr class="line">
				<input type="hidden" name="id" value="<%=member.getId() %>">
				<input type="button" value="취소" onclick='location.href="/MyApart/suggest/list"'>
				<input type="button" value="완료" onclick="check();">
			</div>
			</form>
		</div>
	
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>