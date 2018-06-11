<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.myapart.app.model.*"%>

<% 	
	request.setCharacterEncoding("UTF-8"); 
%>

<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	
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
			height:auto;
		}
		.contents_top>span {
			font-size:30px;
			font-weight:bold;
			margin-left:15px;
		}		
		#survey_subject_question {
			margin-top:15px;
			margin-right:15px;
			margin-left:15px;
			margin-bottom:15px;
			float:left;
		}
		.line {
			width:100%;
			height:4px;
			background-color:#000000;
		}
				
		div.contents_middle {
			position:inherit;
			top:10px;
			width:95%;
			height:600px;
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
	
		/*하단 바 + 등록 버튼 */
		.contents_bottom {
			position:absolute;
			bottom:0;
			width:100%;
			height:10%;
			
		}
		.contents_bottom>#btn{
			float:right;
			margin-top:5px;
			margin-right:15px;
		}
	</style>
	<script>
		function deleteSurvey(arg){
			if(confirm("삭제하시겠습니까?")==true){
				location.href="/MyApart/surveyDelete?no="+arg;
			}
			else{
				return
			}
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
	<%
		Survey si = (Survey)request.getAttribute("survey");
		String sur_reg_date = si.getRegDate().substring(0, 11);
		String sur_end_date = si.getEndDate().substring(0, 11);
	%>
	<div class="board">
		<div class="board_title">
			<h1>설문 조사<font size='5'> (수정)</font></h1>
		</div>
		<form action="/MyApart/survey/update" method="post">
		<div class="board_contents">
			<div class="contents_middle">
				<table width="100%">
					<tr width="100%" >
						<th align="left" colspan="3" width="69%" style="text-overflow:ellipsis; overflow:hidden;">
							<span>주제 : <input type="text" value="<%=si.getSurTitle() %>" class='survey_question' name="surTitle" size=67 maxlength='30' placeHolder='30자 이내로 입력해주세요.' style='width:90%; height:25px;'></span>
						</th>
						<td align="right" style="font-size:small;">NO. <%=si.getSurNum() %></td>
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr style="font-size:small;">
						<td align="left" width="7%">글쓴이 :</td>
						<td align="left" width="53%"><%=si.getName() %></td>
						<td align="right" width="20%">등록 일자 : <%=sur_reg_date %></td>
						<td align="right" width="20%">마감 일자 : <%=sur_end_date %></td>
					</tr>
					<tr><td colspan="6"><hr></td></tr>
				</table>
				
				<div id="middle_contents">
					
					<table width="100%">
						<%  
							for(int i = 0 ; i < si.questSize(); i++){ %>
							<tr height=15px></tr>
							<tr>
								<td align=right>
									<%= (i+1) %>. 질문 :
								</td>
								<td align=left>
									 <input type="hidden", name='list[<%= i%>].questNum' value='<%= si.getQuestNum(i)%>'>
									 <input type="text" name='list[<%= i %>].questTitle' maxlength="30" placeHolder="제목을 입력해 주세요. (30자 이내)"
									 	 style="width:80%; height:20px;" value="<%= si.getQuestTitle(i)%>"/>
								</td>
								
							</tr>
							
							<% if(si.getQuestType(i).equals("input")){%>
									<tr>
										<td align=right width="10%">
											답변 :
										</td>
										<td align=left>
											<input type='text' maxlength="50" style="width:80%; height:20px;" placeHolder="50자 이내로 입력해 주세요." readonly
												style="height:25px;"/>
										</td>
									</tr>
									<tr height=15px></tr>
							<% } else if(si.getQuestType(i).equals("choice2")){ %>
									<tr>
										<td align=right width="10%">
											답변 :
										</td>
										<td align=left>
											<div style="height:25px;">	
												<input type='radio' disabled/>찬성&nbsp
												<input type='radio' disabled/>반대
											</div>
										</td>
									</tr>
									<tr height=15px></tr>									
							<% } else if(si.getQuestType(i).equals("choice5")){%>
									<tr>
										<td align=right width="10%">
											답변 :
										</td>
										<td align=left>
											<div style="height:30px;">	
												<input type='radio' disabled/>매우 좋음&nbsp
												<input type='radio' disabled/>좋음&nbsp
												<input type='radio' disabled/>보통&nbsp
												<input type='radio' disabled/>나쁨&nbsp
												<input type='radio' disabled/>매우 나쁨
											</div>
										</td>
									</tr>
									<tr height=15px></tr>
							<% } else {%>
									<tr>
										<td align=right width="10%" valign="top">
											답변 :
										</td>
										<td align=left>
											<textarea maxlength='100' style="width:80%; height:150px; resize:none;" readonly placeHolder="내용을 입력해 주세요. (100자 이내)"></textarea>
										</td>
									</tr>
									<tr height=20px></tr>
							<% } %>
						<% } %>
					</table>
					
				</div>
			</div>
			<div class="contents_bottom">
				<hr class="line">
				<input type="hidden" name="surNum" value="<%=si.getSurNum() %>">
				<input type="submit" id="btn" value="완료">
				<input type="button" id="btn" value="목록" onclick="javascript:window.location.href='/MyApart/survey/list';">
				<input type="button" id="btn" value="취소" onclick="javascript:history.back();">
			</div>
			
		</div>
		</form>
	</div>

	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>