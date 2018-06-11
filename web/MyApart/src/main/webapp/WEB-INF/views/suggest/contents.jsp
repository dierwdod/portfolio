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
			function getSuggestInfo(arg0){
				window.location.href="/MyApart/suggest/contents?no=" + arg0 + "&action=update";
			}
			function sugDelete(arg0){
				if(confirm("삭제 하시겠습니까?")){
					window.location.href='/MyApart/suggest/delete?no='+arg0;
				}
				else {
					return;
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
			<h1>건의 사항</h1>
		</div>
		<div class="board_contents">
			<div class="contents_middle">
			
				<%
					Suggest suggest = (Suggest)request.getAttribute("suggest");
					String sug_reg_date = suggest.getSugDate().substring(0, 11);
				%>
				<table width="100%">
					<tr>
						<td width="62%" colspan=2 style="font-weight:bold;"><%=suggest.getSugTitle() %></td>
						<td align="right" style="font-size:small;" width="23%" >NO. <%=suggest.getSugNum() %></td>
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr style="font-size:small;">
						<td width="10%" align="left">글쓴이 : </td>
						<td width="62%"><%=suggest.getId() %></td>
						<td align="right" width="23%" >등록날짜 : <%=sug_reg_date %></td>
						
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr>
						<td colspan=3 valign="top" align="left" width="100%" height="400px">
							<pre><%=suggest.getSugContents() %></pre>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="contents_bottom">
				<hr class="line">
				
				<%if(member != null && member.getId().equals(suggest.getId())){ %>
					<input type="button" value="삭제" onclick="sugDelete('<%=suggest.getSugNum()%>')">
					<input type="button" value="수정" onclick="getSuggestInfo('<%=suggest.getSugNum()%>')">
				<%} %>
				<input type="button" value="목록" onclick="location.href='/MyApart/suggest/list';">
							
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</body>
</html>