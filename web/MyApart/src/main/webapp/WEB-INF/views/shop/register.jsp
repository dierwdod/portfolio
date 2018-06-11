<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java"
	import="java.sql.*,java.util.*,java.text.*,com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("utf-8");
	Member member = (Member)session.getAttribute("loginMember");
%>
<HTML>
<HEAD>

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
	<script language="javascript">
		function setLine(txa) { //자동줄바꾸기 시스템
			line = 5 //기본 줄 수 
	
			new_line = txa.value.split("\n").length + 1;
			if (new_line < line)
				new_line = line;
	
			txa.rows = new_line;
		}
		
		function len_chk(value) { //글자수 제한 시스템 1일땐 제목, 2일땐 내용
			var value1 = value;
			
			if(value1=='1'){
				var title = document.write.title;
	
				if (title.value.length > 60) {
	
					alert("글자수 60자 이내로 제한됩니다.");
					title.value = title.value.substring(0, 60);
					title.focus();
				}
				
			}
			else if(value1=='2'){
				var textarea = document.write.ir1;
	
				if (textarea.value.length > 300) {
	
					alert("글자수 300자 이내로 제한됩니다.");
					textarea.value = textarea.value.substring(0, 300);
					textarea.focus();
				}
		
			}
			
		}
	
		function check() {
			with (document.write) {
				if (shopTitle.value.length == 0) {
					alert("제목을 등록해주세요!");
					shopTitle.focus();
					return;
				}
				if (uploadFile.value.length == 0) {
					alert("사진을 등록해주세요!");
					uploadFile.focus();
					return;
				}
				
				oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD",[]);
				try {
					document.write.submit();		
				}
				catch(e){}
			}
		}
		
		$(function() {
		  	$('#ir1').keyup(function (e){
	          	var content = $(this).val();
	          	$('#counter').html(content.length + '/300');
	      	});
	      	$('#ir1').keyup();
		});
	</script>
</HEAD>
<BODY>
	<%
		Shop shop = (Shop) request.getAttribute("shop");
	%>


	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src='/MyApart/resources/image/apt.jpg' alt="아파트" style="width: 100%; height: 100%;">
		</div>
	</div>

	<div class="board">
		<div class="board_title">
			<h1>중고 장터</h1>
		</div>
		<div class="board_contents">

			<form name=write action='/MyApart/shop/register' method='post' enctype=multipart/form-data>
				<div class="contents_middle" style="margin-top:15px;">

					<table width=100%>
						<tr>
							<th width=10%><font size=-1 color=black>상품명</font></th>
							<td><input type=text name=shopTitle size=30 style="width:100%; height:25px;" maxlength="30" placeHolder="내용을 입력해 주세요. (30자 이내)">
							</td>
						</tr>
						<tr><td colspan=2><hr></td></tr>
						<tr>
							<th width=10%><font size=-1 color=black>이미지</font></th>
							<td><input type=file name=uploadFile style="width:100%; height:25px;" size=30>
							</td>
						</tr>
						<tr><td colspan=2><hr></td></tr>
						<tr>
							<td colspan=2><textarea name=shopContents id="ir1" maxlength='300' style="width: 100%; height: 450px; resize:none;" placeHolder="내용을 입력해 주세요. (300자 이내)"></textarea>
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
						<tr>
							<td colspan=2 align=right style="font-size:small"><span id="counter">0/300</span></td>
						</tr>
					</table>
				</div>
				
				<div class="contents_bottom">
					<hr class="line">
					<input type=hidden name=id value=<%=member.getId() %>>
					<input type=button value='취소' onclick="javascript:history.back();">
					<input type=button value="완료" onClick='check(this.form)'>
				</div>
			</form>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</BODY>
</HTML>