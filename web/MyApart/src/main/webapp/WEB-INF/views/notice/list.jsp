<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.sql.*,java.util.*,java.text.*,com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("utf-8");
	Member member = null;
	if(session.getAttribute("loginMember") != null){
		member = (Member)session.getAttribute("loginMember");
	}
%>
<%
	String category = null;
	int where = 1;
	int totalgroup = 0;
	int maxpages = 10;
	int startpage = 1;
	int endpage = startpage + maxpages - 1;
	int wheregroup = 1;
	if (request.getAttribute("go") != null) {
		where = Integer.parseInt((String) request.getAttribute("go"));
		wheregroup = (where - 1) / maxpages + 1;
		startpage = (wheregroup - 1) * maxpages + 1;
		endpage = startpage + maxpages - 1;
	} else if (request.getAttribute("gogroup") != null) {
		wheregroup = Integer.parseInt((String) request.getAttribute("gogroup"));
		startpage = (wheregroup - 1) * maxpages + 1;
		where = startpage;
		endpage = startpage + maxpages - 1;
	}
	int nextgroup = wheregroup + 1;
	int priorgroup = wheregroup - 1;

	int stocknums = 0;
	int startrow = 0;
	int endrow = 0;
	int maxrows = 20;
	int totalrows = 0;
	int totalpages = 0;

	ArrayList<Notice> notices = (ArrayList) request.getAttribute("noticeList");

	totalrows = notices.size();
	totalpages = (totalrows - 1) / maxrows + 1;
	startrow = (where - 1) * maxrows;
	endrow = startrow + maxrows - 1;
	if (endrow >= totalrows)
		endrow = totalrows - 1;
	totalgroup = (totalpages - 1) / maxpages + 1;
	if (endpage > totalpages)
		endpage = totalpages;
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
		   overflow-y: hidden;
		}
		.contents_top {
			position:inherit;
			top:15px;
			width:100%;
			
		}
		.contents_top>#all_list {
			width:45%;  
			float:right; 
			text-align:right;
			margin-right:15px; 

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
			height:auto;
			overflow:hidden;
			margin-left:auto;
			margin-right:auto;
			
		}
		div.contents_middle>table{
			width:100%;
			height:auto;
			font-size:15px;
			
		}
		
		/*하단 바 + 등록 버튼 */
		.contents_bottom {
			position:inherit;
			top:15px;
			width:100%;
			height:7%;
		}
		.contents_bottom>#paging{
			width:90%;
			float:left;
			text-align:center;
		}
		.contents_bottom>a{
			width:7%;
			float:right;
			margin-right:15px;
		}
		#title {
			width:90%;
			max-width:500px;
			overflow:hidden; 
			white-space:nowrap;
			text-overflow:ellipsis; 
			text-align:left;
		}
	</style>
</HEAD>
<BODY>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src="/MyApart/resources/image/apt.jpg" style="width:100%; height:100%" />
		</div>
	</div>
	
	<div class="board">
		<div class="board_title">
			<h1>공지 사항</h1>
		</div>
		<div class="board_contents">
			<div class="contents_top">
	      		<div id="all_list">
			         <FORM method=get name=search action="/MyApart/notice/list">
						 제목 : 
						<INPUT type=text name=keyword value=<%=(String)request.getAttribute("keyword")%>> <INPUT type=submit value="검색">
			         </FORM>
				</div>
				<hr class="line">
			</div>
			<div class="contents_middle">
				<table  width="95" cellspacing="7">
					<tr>
						<TH width=5% >
							<FONT color=black face=굴림><NOBR>번호</NOBR></FONT>
						</TH>
		                <TH width=27% >
		                	<FONT color=black face=굴림><NOBR>제목</NOBR></FONT>
		                </TH>
		                <TH width=10% >
		                	<FONT color=black face=굴림><NOBR>작성자</NOBR></FONT>
		                </TH>
		                <TH width=13%>
		                	<FONT color=black face=굴림><NOBR>작성일</NOBR></FONT>
		                </TH>
		                <TH width=5% >
		                	<FONT color=black face=굴림><NOBR>조회수</NOBR></FONT>
		               	</TH>
					</tr>
				<% if(notices.size() == 0){ %>
						<tr>
							<td colspan=6 align=center>등록된 글이 없습니다.</td>
						</tr>	
				<%    
					}
					else {
						for (int i=startrow; i<=endrow;i++) {
				
                  	  		String not_reg_date = notices.get(i).getNotDate().substring(0, 11);
				%> 
							<tr>
								<td align=center><%=notices.get(i).getNotNum()%></td>
								<td align="center">
									<div id="title">
										<a href='/MyApart/notice/contents?no=<%=notices.get(i).getNotNum()%>'><%=notices.get(i).getNotTitle()%></a></td>
									</div>
								<td align=center><%=notices.get(i).getId()%></td>
								<td align=center><%=not_reg_date%></td>
								<td align=center><%=notices.get(i).getNotCount()%></td>
							</tr>
				<%
	            		}
					}
				%>
	 			</table>
 			</div>
 			<div class="contents_bottom">
				<hr style="width: 100%; height: 4px; background-color: black;">
				<div id="paging">
					<% if (wheregroup > 1) {%>
		               <A href="/MyApart/notice/list?gogroup=1">처음</A>
		               <A href="/MyApart/notice/list?gogroup=<%=priorgroup%>">이전</A>
		            <% } else {%>
			               [처음]
			               [이전]
		            <%}%>
		
		            <% if (totalrows != 0) {%>
		               <% for (int jj = startpage; jj <= endpage; jj++) {%>
		                  <%if (jj == where){ %>
		                     [<%=jj%>]
		                  <%}else {%>
		                     [<A href="/MyApart/notice/list?go=<%=jj%>&keyword=<%=(String)request.getAttribute("keyword")%>"><%=jj%></A>]
		                  <% }
		                  }
		               }%>
		            <%
		            if(wheregroup < totalgroup) {%>
			               [<A href="/MyApart/notice/list?gogroup=<%=nextgroup%>">다음</A>]
			               [<A href="/MyApart/notice/list?gogroup=<%=totalgroup%>">마지막</A>]
		            <% } else {%>
			               [다음]
			               [마지막]
		            <%}%>
					
				</div>
				<%	if(member != null && member.getGrade().equals("admin")){ %>
						<input type=button value='글쓰기' onclick="location.href='/MyApart/notice/registerPage'">
				<%	} %>
			</div>
		</div>
		
	</div>
	
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
		
</BODY>
</HTML>