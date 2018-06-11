<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.sql.*,java.util.*,java.text.*,com.myapart.app.model.*"%>
<% 
	request.setCharacterEncoding("UTF-8");
	Member member = null;
	if(session.getAttribute("loginMember") != null){
		member = (Member)session.getAttribute("loginMember");
	}
%>	


<HTML>
<HEAD>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	<title>MyApart</title>
	<style>
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:250px;
		}
		#header_img {
			position:inherit;
			top:0;
			width:100%;
			height:250px;
			margin:0;
		}
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			width: 70%;
			min-width:1000px;
			height: 95%;
			margin-left:auto;
			margin-right:auto;
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
			.contents_bottom>input[type=button]{
				float:right;
				margin-top:5px;
				margin-right:15px;
			}
		</style>
		<script>
			function getNoticeInfo(arg0){
				window.location.href="/MyApart/notice/contents?no=" + arg0 + "&action=update";
			}
			function notDelete(arg0){
				if(confirm("삭제 하시겠습니까?")){
					window.location.href='/MyApart/notice/delete?no=' + arg0;
				}
				else {
					return;
				}
			}
		</script>
</HEAD>
<BODY>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src='/MyApart/resources/image/apt.jpg' alt="아파트" width=100%" id="header_img">
		</div>
	</div>
	
	<div class="board">
		<div class="board_title">
			<h1>공지 사항</h1>
		</div>
		<div class="board_contents">
		<div class="contents_middle">
			<% 
				Notice notice = (Notice)request.getAttribute("notice");
				String not_reg_date = notice.getNotDate().substring(0, 11);
			%>
			<table width="100%">
					<tr>
						<td width="62%" colspan=2 style="font-weight:bold;"><%=notice.getNotTitle() %></td>
						<td align="right" style="font-size:small;" width="23%" >NO. <%=notice.getNotNum() %></td>
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr style="font-size:small;">
						<td width="10%" align="left">글쓴이 : </td>
						<td width="62%"><%=notice.getId() %></td>
						<td align="right" width="23%" >등록날짜 : <%=not_reg_date %></td>
						
					</tr>
					<tr><td colspan="4"><hr></td></tr>
					<tr>
						<td colspan=3 valign="top" align="left" width="100%" height="400px">
							<pre><%=notice.getNotContents() %></pre>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="contents_bottom">
				<hr class="line">
				
				<%if(member != null && member.getGrade().equals("admin")){ %>
					<input type="button" value="삭제" onclick="notDelete('<%=notice.getNotNum()%>')">
					<input type="button" value="수정" onclick="getNoticeInfo('<%=notice.getNotNum()%>')">
				<%} %>
				<input type="button" value="목록" onclick="location.href='/MyApart/notice/list';">
							
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</BODY>
</HTML>
