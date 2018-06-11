<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.myapart.app.model.*"%>

<% 	
	request.setCharacterEncoding("UTF-8"); 
	Member member = (Member)session.getAttribute("loginMember");
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
		.contents_bottom>input[type=button], input[type=submit]{
			float:right;
			margin-top:5px;
			margin-right:15px;
		}
	</style>
</head>
<body>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src="/MyApart/resources/image/apt.jpg" style="width:100%; height:250px" />
		</div>
	</div>
	<%
		Survey si = (Survey)request.getAttribute("surveyAnswer");
		String sur_parti_date = si.getPartiDate().substring(0, 11);
	%>
	<div class="board">
		<div class="board_title">
			<h1>설문 조사<font size='5'> (답변)</font></h1>
		</div>
		<div class="board_contents">
			<div class="contents_middle">
				<table width="100%">
					<tr>
						<th align="left" colspan="2" width="69%" style="text-overflow:ellipsis; overflow:hidden;">
							제목 : <%=si.getSurTitle() %>
						</th>
						<td align="right" width="10%" style="font-size:small;">NO. <%=si.getSurNum() %></td>
					</tr>
					<tr><td colspan="3"><hr></td></tr>
					<tr style="font-size:small;">

						<td align="left" width="5%">글쓴이 :</td>
						<td align="left" width="37%"><%=si.getName() %></td>
						<td align="right" width="13%">참여 일자 : <%=sur_parti_date %></td>
						
					</tr>
					<tr><td colspan="3"><hr></td></tr>
				</table>
				
				<div id="middle_contents">
					
					<table width="100%">
						<%  
							for(int i = 0 ; i < si.ansSize(); i++){ %>
								<tr height=15px></tr>
								<tr height="25px">
									<td align=right width="10%">
										<%= (i+1) %>. 질문 :
									</td>
									<td align=left>
										<font size=3><%=si.getAnsTitle(i) %></font>
									</td>
									
								</tr>
								<tr height="25px">
									<td align=right  width="10%">
										답변 :
									</td>
									<td align=left>
										<p><%= si.getAnsContents(i) %></p>
									</td>
								</tr>
						<% } %>
					</table>
					
				</div>
			</div>
			<div class="contents_bottom">
				<hr class="line">
				<input type="button" value="목록" onclick="javascript:window.location.href='/MyApart/survey/list'">
			</div>
			
		</div>
	</div>

	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>