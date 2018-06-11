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
			<h1>설문 조사<font size='5'> (차트 목록)</font></h1>
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
					
				</div>
				<div id="contents_top_search">
					
					<form action='/MyApart/survey/chartList' method="get">
						제목 : 
						<input type="text" name="keyword" value="<%=keyword%>">&nbsp;
						<input type="submit" id="survey_search" value="검색">	
					</form>
				</div>
				<hr class="line">
			</div>
			
			<div class="contents_middle">
				<table cellpadding="5px" style="table-layout:fixed;">
					
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
					for(int i = startrow ; i <= endrow; i++){
						SurveyInfo survey = surveys.get(i);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date today = formatter.parse(formatter.format(new Date()));
						
						String sur_reg_date = survey.getRegDate().substring(0, 11);
						String sur_end_date = survey.getEndDate().substring(0, 11);
						Date end_date = formatter.parse(sur_end_date);
						
				%>					
						
						<tr>
							<td align="center"><%=survey.getSurNum() %></td>
							<td  align="center">
								<div id="title">
									<%=survey.getSurTitle() %>
								</div>
							</td>
							<td align="center"><%=survey.getName() %></td>
							<td align="center"><font size=2><%= sur_reg_date %></font></td>
							<td align="center"><font size=2><%= sur_end_date %></font></td>
							<td anign="center"><a href="/MyApart/survey/chart?no=<%=survey.getSurNum()%>">통계 확인</a></td>
						</tr>
				<%	} %>

				</table>
			</div>
			<div class="contents_bottom">
					<hr class="line">
					<div id="paging">
						<% if(wheregroup > 1){%>
							[<a href='/MyApart/survey/chartList?go=1&&keyword=<%=keyword %>'>처음</a>]
							[<a href='/MyApart/survey/chartList?gogroup=<%= priorgroup %>&keyword=<%=keyword %>'>이전</a>]
						<% } else {%>
							[처음]
							[이전]
						<% }
							
						for(int jj = startpage; jj<=endpage ; jj++){
							if(jj == where){ %>
								[<%= jj %>]
						<%  } else { %>
								[<a href='/MyApart/survey/chartList?go=<%= jj%>&keyword=<%=keyword %>'><%= jj%></a>]
						<%  } 
						
						} if(wheregroup < totalgroup){ %>
							[<a href='/MyApart/survey/chartList?gogroup=<%= nextgroup%>&keyword=<%=keyword %>'>다음</a>]
							[<a href='/MyApart/survey/chartList?gogroup=<%= totalgroup%>&keyword=<%=keyword %>'>마지막</a>]
						<%} else { %>
							[다음]
							[마지막]
						<%}%>	
					</div>	
			</div>
		</div>
	</div>
	
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>

</body>
</html>

