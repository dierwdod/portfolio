<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.text.*, com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	Member member = (Member)session.getAttribute("loginMember");
	String keyword="";
	if(request.getAttribute("keyword") != null){
		keyword = (String)request.getAttribute("keyword");
	}
%>    
<%
	int where = 1;
	int totalgroup = 0;
	int maxpages = 10;
	int startpage = 1;
	int endpage = startpage + maxpages-1;
	int wheregroup=1;
	
	if(request.getAttribute("go") != null){
		where = Integer.parseInt((String)request.getAttribute("go"));
		wheregroup = (where -1)/maxpages + 1;
		startpage = (wheregroup-1)*maxpages +1;
		endpage = startpage+maxpages-1;
	}
	else if(request.getAttribute("gogroup") != null){
		wheregroup = Integer.parseInt((String)request.getAttribute("gogroup"));
		startpage = (wheregroup-1) *maxpages +1;
		where = startpage;
		endpage = startpage+maxpages-1;
	}
	
	int nextgroup = wheregroup+1;
	int priorgroup = wheregroup-1;
	
	int nextpage = where+1;
	int priorpage = where-1;
	int startrow = 0;
	int endrow = 0;
	int maxrows = 20;
	int totalrows = 0;
	int totalpages = 0;
%>    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MyApart</title>
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	<style>
	
		/*---------------기본 틀 지정---------------*/
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:250px;
		}
		
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			width:70%;
		   	height:105%;
			margin:auto;			
		}
		div.board_title>h1{
			margin-left:20px;
		}
		
		div.navigation {
			position:inherit;
			top:40px;
			left:5%;
			width:12%;
			height:85%;
			float:left;
			background-color:white;
			border-radius:10px;
			text-align:left;
		
		}
		.navigation>ul {
			list-style:none;
			margin-left:-40px;			
		}
		.navigation>ul>li {
			margin-left:auto;
			margin-right:auto;
			text-align:center;
			margin-top:20px;
			font-weight:bold;
			font-color:black;
		}
		/* 게시판안 내용 지정*/
		div.board_contents {
			position:inherit;
			top:40px;
			left:6%;
			width:77%;
			height:85%;
			background-color:white;
			border-radius:12px;
			overflow:hidden;
		}
		/*-------------------------------------*/
		.contents_top {
			position:inherit;
			top:15px;
			width:100%;
			min-width:800px;
		}
		.contents_top>#contents_top_list {
			width:53%; 
			float:left;
			text-align:center;
			margin-bottom:15px;
			
		}
		.contents_top>#contents_top_list>a{
			text-decoration:none;
			color:black;
			font-size:20px;
			margin-left:40px;
			padding:3px;
			border:1px solid black;
			border-radius:5px;
			
		}
		.contents_top>#contents_top_list>a:hover {
			background-color:#eeeeee;
		}
		.contents_top>#contents_top_list>a:active {
			background-color:#eeeeee;
		}
		.contents_top>#contents_top_list>a:visited {
			background-color:#eeeeee;
		}
		.contents_top>#contents_top_search {
			width:45%;  
			float:right; 
			text-align:right;
			margin-right:15px; 
			margin-bottom:15px;
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
			min-width:800px;
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
			min-width:800px;
			height:7%;
		}
		.contents_bottom>#paging{
			width:90%;
			float:left;
			text-align:center;
		}
		.contents_bottom>input[type=button]{
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
	
</head>
<body>
	<% 
		ArrayList<SurveyInfo> surveys = (ArrayList)request.getAttribute("surveyList");
		String listMode = (String)request.getAttribute("mode");
		totalrows = surveys.size();
		totalpages = totalrows/maxrows;
		if(totalrows % maxrows > 0){
			totalpages++;
		}
		startrow = (where-1)*maxrows;
		endrow = startrow + maxrows -1;

		if(endrow >= totalrows){
			endrow = totalrows-1;
		}
		
		totalgroup = (totalpages-1)/maxpages + 1;
		if(endpage > totalpages){
			endpage = totalpages;
		}
	%>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src="/MyApart/resources/image/apt.jpg" style="width:100%; height:250px" />
		</div>
	</div>
	
	<div class="board">
		<div class="board_title">
			<h1>설문 조사<font size='5'> (설문 목록)</font></h1>
		</div>
		
		<div class="navigation">
			<ul>
				<li><a href='/MyApart/survey/list'>설문 목록</a></li>
				<li><a href='/MyApart/survey/chartList'>차트 목록</a></li>
			</ul>
		</div>
		
		<div class="board_contents">
			<div class="contents_top">
				<div id="contents_top_list">
					<a href="/MyApart/survey/list">전체 설문 목록</a>
					<a href="/MyApart/survey/list?mode=parti">참여 설문 목록</a>
				</div>
				<div id="contents_top_search">
					
					<form action='/MyApart/survey/list' method="get">
						제목 : 					
						<input type="text" name="keyword" value="<%=keyword%>">&nbsp;
						<input type="hidden" name="mode" value="<%=listMode %>">
						<input type="submit" id="survey_search" value="검색">	
					</form>
				</div>
				<hr class="line">
			</div>
			
			<div class="contents_middle">
				<table cellpadding="5px" style="table-layout:fixed;">
					
				<%
					
					if(listMode.equals("list")){
				%>
						<th width="7%">NO</th>
						<th width="48%">제목</th>
						<th width="10%">글쓴이</th>
						<th width="13%">등록일자</th>
						<th width="13%">마감일자</th>
						<th width="10%"></th>
						<tr>
							<th colspan="6"></th>
						</tr>		
						
					<%	if(surveys.size() == 0){%>
								<tr>
									<td colspan=6 align=center>등록된 설문지가 없습니다.</td>
								</tr>	
					<%	}
											
					}else {%>
						
						<th width="7%">NO</th>
						<th width="34%">제목</th>
						<th width="13%">등록일자</th>
						<th width="13%">참여자</th>
						<th width="10%">동</th>
						<th width="13%">참여일자</th>
						<th width="10%"></th>
						<tr>
							<th colspan="7"></th>
						</tr>
						<%	if(surveys.size() == 0){%>
							<tr>
								<td colspan=6 align=center>참여한 설문지가 없습니다.</td>
							</tr>	
					<%	}					
					
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date today = formatter.parse(formatter.format(new Date()));
					
					for(int i = startrow ; i <= endrow; i++){
						SurveyInfo survey = surveys.get(i);
						String sur_reg_date = survey.getRegDate().substring(0, 11);
						
				%>					
						<% if(listMode.equals("list")){ 
							String sur_end_date = survey.getEndDate().substring(0, 11);
							Date end_date = formatter.parse(sur_end_date);
						%>	
						
								<tr>
									<td align="center"><%=survey.getSurNum() %></td>
									<td  align="center">
										<div id="title">
											<a href="/MyApart/survey/contents?no=<%=survey.getSurNum()%>"><%=survey.getSurTitle() %></a>
										</div>
									</td>
									<td align="center"><%=survey.getName() %></td>
									<td align="center"><font size=2><%= sur_reg_date %></font></td>
									<td align="center"><font size=2><%= sur_end_date %></font></td>
							<% if(end_date.compareTo(today) < 0){ %>
									<td>[마감]</td>						
							<% } else { %>	
									<td>[진행 중]</td>
							<% } %>	
								</tr>	
						<% } else {
							String sur_parti_date = survey.getPartiDate().substring(0,11);
						%>
						
								<tr>
									<td align="center"><%=survey.getSurNum() %></td>
									<td align="left">
										<div id="title">
											<%=survey.getSurTitle() %>
										</div>
									</td>
									<td align="center"><font size=2><%= sur_reg_date %></font></td>
									<td align="center"><%= survey.getId() %></td>
									<td align="center"><%= survey.getDong() %>동</td>
									<td align="center"><font size=2><%= sur_parti_date %></font></td>
									<td align="center">
										<form method="post" action="/MyApart/survey/answer">
											<input type="hidden" name="surNum" value="<%=survey.getSurNum() %>">
											<input type="hidden" name="id" value="<%= survey.getId() %>">
											<input type="submit" value="확인">
										</form>
									</td>
								</tr>
								
						<% }
					}
				%>

				</table>
			</div>
			<div class="contents_bottom">
					<hr class="line">
					<div id="paging">
						<% if(wheregroup > 1){%>
							[<a href='/MyApart/survey/list?go=1&mode=<%=listMode%>&keyword=<%=keyword %>'>처음</a>]
							[<a href='/MyApart/survey/list?gogroup=<%= priorgroup %>&mode=<%=listMode%>&keyword=<%=keyword %>'>이전</a>]
						<% } else {%>
							[처음]
							[이전]
						<% }
							
						for(int jj = startpage; jj<=endpage ; jj++){
							if(jj == where){ %>
								[<%= jj %>]
						<%  } else { %>
								[<a href='/MyApart/survey/list?go=<%= jj%>&mode=<%=listMode%>&keyword=<%=keyword %>'><%= jj%></a>]
						<%  } 
						
						} if(wheregroup < totalgroup){ %>
							[<a href='/MyApart/survey/list?gogroup=<%= nextgroup%>&mode=<%=listMode%>&keyword=<%=keyword %>'>다음</a>]
							[<a href='/MyApart/survey/list?gogroup=<%= totalgroup%>&mode=<%=listMode%>&keyword=<%=keyword %>'>마지막</a>]
						<%} else { %>
							[다음]
							[마지막]
						<%}%>	
					</div>	
					<input type="button" value="글쓰기" onClick="javascript:window.location.href='/MyApart/survey/registerPage'">
			</div>
		</div>
	</div>
	
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>

</body>
</html>

