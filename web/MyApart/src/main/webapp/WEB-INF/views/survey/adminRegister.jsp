<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.myapart.app.model.*"%>

<%
	request.setCharacterEncoding("UTF-8");

	Member member = (Member)session.getAttribute("loginMember");
	
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
			width:70%;
			min-width:1000px;
		    height:95%;
			margin:auto;
		}
		div.board_title>h1{
			margin-left:20px;
		}
		
		/* 게시판안 내용 지정*/
		div.board_contents {
			position:relative;
			top:40px;
			width:90%;
			min-width:860px;
			height:85%;
			margin:auto;
			border-radius:12px;
			background-color:#ffffff;
			overflow-x:hidden;
			overflow-y:hidden;
		}
		
		
		/*중단 바 + 질문,답변 선택 + 추가 버튼 */
		.contents_top {
			position:inherit;
			top:15px;
			width:100%;
		}
				
		#survey_subject_question {
			width:100%;
			margin-top:15px;
			
		}
		#survey_subject_question>span {
			width:65%;
			float:left;
			margin-left:15px;
			margin-bottom:15px;
		}
		#survey_subject_question>#selector {
			width:30%;
			float:right;
			height:25px;
			margin-right:15px;
			text-align:right;
		}
		.line {
			width:100%;
			height:4px;
			background-color:#000000;
		}
				
		.contents_middle {
			position:inherit;
			top:10px;
			width:95%;
			height:600px;
			overflow-y:auto;
			margin-left:auto;
			margin-right:auto;
		}
				
		/*하단 바 + 등록 버튼 */
		.contents_bottom {
			position:absolute;
			bottom:0;
			width:100%;
			height:10%;
		}
		.contents_bottom>input[type=button]{
			float:right;
			margin-top:5px;
			margin-right:15px;
		}
		
	
	</style>
	
	<script>
		$(document).ready(function(){
		
			$('#add_question').on('click', function(){
				var select_question = $("#question_select option:selected").val();
				//질문 개수
				let count = $(".qst").length;
							
				if(count > 9){
					alert('최대 10개의 질문만 지정할 수 있습니다.');
					return;
				}
				var $qst_ans = '';
				var row = '';
				var line = "<tr><td colspan='2'><hr style='border:#039 1px dotted'></td></tr>";
			
				//질문 및 답변 name 설정
				var question_attr_name = 'list['+count+'].questTitle';
			
				//질문 엘리먼트 생성
				var $question = $("<input type='text' name='' placeHolder='30자 이내로 입력하세요.' class='survey_question' style='width:80%; height:20px;'/>");
				$question.attr("name",question_attr_name);
				
				row += "<tr cellspacing='5px' class='qst'>";
				row += "<td align='right' width='10%'>"+ (count+1) + ". 질문 : </td><td align='left'>" + $question.prop("outerHTML") + "</td>";
				
				if(select_question == "input"){
					var $answer = $("<input type='text' readonly style='width:80%; height:20px;' />");				
					var $type = $("<input type='hidden' name='' value='input' />");
					$type.attr("name", 'list['+count+'].questType');
					
					row += "<tr><td align='right' width='10%'>   답변 : </td><td align='left'>" + $answer.prop("outerHTML") + "</td>";
					row += "<td>"+ $type.prop("outerHTML") + "</td></tr>";
				}
				else if(select_question == "choice_2"){
					var $answer1 = $("<input type='radio' disabled value='agree'/>");
					var $answer2 = $("<input type='radio' disabled value='disagree'/>");
					var $type = $("<input type='hidden' name='' value='choice2'/>");
					$type.attr("name", 'list['+count+'].questType');
					
					row += "<tr><td align='right' width='10%'>답변 : </td><td align='left'>";
					row += $answer1.prop("outerHTML") +"찬성 " + $answer2.prop("outerHTML") + "반대</td>";
					row += "<td>"+ $type.prop("outerHTML") + "</td></tr>";
				}
				else if(select_question == "choice_5"){
					var $answer1 = $("<input type='radio' disabled/>");
					var $answer2 = $("<input type='radio' disabled/>");
					var $answer3 = $("<input type='radio' disabled/>");
					var $answer4 = $("<input type='radio' disabled/>");
					var $answer5 = $("<input type='radio' disabled/>");
					var $type = $("<input type='hidden' name='' value='choice5'/>");
					$type.attr("name", 'list['+count+'].questType');
		
					row += "<tr><td align='right' width='10%'>답변 : </td><td align='left'>";
					row += $answer1.prop("outerHTML") + "매우 좋음 " + $answer2.prop("outerHTML") + "좋음 ";
					row += $answer3.prop("outerHTML") + "보통 ";
					row += $answer4.prop("outerHTML") + "나쁨 " + $answer5.prop("outerHTML")+ "매우나쁨</td>";
					row += "<td>"+ $type.prop("outerHTML") + "</td></tr>";
				}
				else {
					var areaCount = $("textarea").length+1;
					if(areaCount > 1){
						alert('의견(100자) 항목은 1개만 추가할 수 있습니다.');
						return;
					}
					var $answer = $("<textarea style='width:80%; height:100px; resize:none;' readonly/>");
					var $type = $("<input type='hidden' name='' value='opinion'/>");
					$type.attr("name", 'list['+count+'].questType');
					row += "<tr><td align='right' width='10%'>답변 : </td><td align='left'>";
					row += $answer.prop("outerHTML") + "</td>";
					row += "<td>"+ $type.prop("outerHTML") + "</td></tr>";
				}
				row += "</tr>";
				row += line;
				$('.contents_middle>table').append(row);
			});
			
			$('#reset').on('click', function(){
				$("#quest_table").empty();
			});
			
		})
		
		function check(){
			var elements = document.survey_write.getElementsByClassName('survey_question');
			if(elements.length == 0){
				alert('1~10개 사이의 질문을 등록해 주세요.');
				return;
			}
			for(var i = 0 ; i < elements.length; i++){
				if(elements[i].value == ""){
					alert('생성한 입력란에 내용을 입력해 주세요.');
					return;
				}
			}
			document.survey_write.submit();
		}
		
	</script>
</head>
<body>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src="/MyApart/resources/image/apt.jpg" style="width:100%; height:250px" />
		</div>
	</div>
	
	<div class="board">
		<div class="board_title">
			<h1>설문 조사<font size='5'> (등록)</font></h1>
		</div>
		
		<div class="board_contents">
			<form action="/MyApart/survey/register" method="post" name="survey_write">
				<div class="contents_top">
					<div id="survey_subject_question">
						<span>주제 : <input type="text" class='survey_question' name="surTitle" size=67 maxlength='30' placeHolder='30자 이내로 입력해주세요.' style='width:90%; height:25px;'></span>
						
						<div id="selector">
							<select id="question_select">
								<option value="input" selected>입력 (30자)</option>
								<option value="choice_2">2개 항목 선택 (찬/반)</option>
								<option value="choice_5">5개 항목 선택</option>
								<option value="opinion">의견 (100자)</option>
							</select>&nbsp;
							<input type="button" id="add_question" name="answer_add" value="추가"><br>					
						</div>
					</div>
					<hr class="line">
				</div>
				
				<div class="contents_middle">
					
						<table width=100% id="quest_table">
						
						</table>						
				</div>
				
				<div class="contents_bottom">
					<hr class="line">
					<input type="hidden" name="id" value="<%=member.getId()%>">
					<input type="button" value="완료" onClick="check()">
					<input type="button" id="reset" value="다시 쓰기">
					<input type="button" value="취소" onClick="javascript:history.back();">
					
				</div>
			</form>	
		</div>
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>