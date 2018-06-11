<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.text.*, com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String order = (String)request.getAttribute("order");
	String what = (String)request.getAttribute("what");
	String option = (String)request.getAttribute("option");
	String keyword = (String)request.getAttribute("keyword");
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
	<script src="http://code.jquery.com/jquery-3.2.1.js"></script> 
	
	<style>
	
		/*---------------기본 틀 지정---------------*/
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:250px;
		}
		
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			width:70%;
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
			height:85%;
			margin:auto;
			border-radius:12px;
			background-color:white;
			overflow:hidden;
		}
		/*-------------------------------------*/
		.contents_top {
			position:inherit;
			top:15px;
			width:100%;
			
		}
				
		.contents_top>#order_list {
			width:auto;  
			
		}
		.search_list {
			float:right;
			margin-bottom:15px;
			margin-right:40px;
		}
		#search_select {
			margin-top:3px;
			margin-right:5px;
		}
		.line {
			width:100%;
			height:4px;
			background-color:#000000;
		}
		
		div.contents_middle {
			position:inherit;
			top:10px;
			width:97%;
			height:600px;
			overflow-y:auto;
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
			position:absolute;
			bottom:0;
			width:100%;
			height:10%;
		}
		.contents_bottom>#paging{
			width:100%;
			text-align:center;
		}
		a:link {
			text-decoration:none;
		}
		a:visited {
			text-decoration:none;
		}
		a:hover {
			text-decoration:none;
		}
		a:active {
			text-decoration:none;
		}
	</style>
	<script>
		
		$(document).ready(function(){
			$("select").change(function(){
				var selectedVar = $("select option:selected").val();
				var selectInput = $('#search_type');
				if(selectedVar == 'birth'){
					selectInput.attr("type", "date");
				}
				else {
					selectInput.attr("type", "text");
				}
			});
		});
		
	</script>
</head>
<body>
		<% 
		 	ArrayList<Member> members = (ArrayList<Member>)request.getAttribute("memberList"); 
			String listMode = (String)request.getAttribute("mode");
			totalrows = members.size();
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
			<h1>회원 목록</h1>
		</div>
		<div class="board_contents">
			<div class="contents_top">
				<div id="order_list">
					<form action="/MyApart/member/list" class="search_list" method="get" id="search_form">
						<input type="hidden" name="order" value="<%=order %>"/>
						<input type="hidden" name="what" value="<%=what %>"/>
						<input type="text" name="keyword" id="search_type" value="<%=keyword %>"/>
						<input type="submit" value="검색"/>
					</form>
					<select class="search_list" id="search_select" name="search" form="search_form">
						<%if(option.equals("id")){ %>
							<option value="id" selected>아이디</option>
							<option value="name">이름</option>
							<option value="birth">생년월일</option>
							<option value="phone">핸드폰</option>
						<%} else if(option.equals("name")){ %>
							<option value="id">아이디</option>
							<option value="name" selected>이름</option>
							<option value="birth">생년월일</option>
							<option value="phone">핸드폰</option>
						<%} else if(option.equals("birth")){ %>
							<option value="id">아이디</option>
							<option value="name">이름</option>
							<option value="birth" selected>생년월일</option>
							<option value="phone">핸드폰</option>
						<%} else { %>
							<option value="id">아이디</option>
							<option value="name">이름</option>
							<option value="birth">생년월일</option>
							<option value="phone" selected>핸드폰</option>
						<%} %>
					</select>
				</div>
				<hr class="line">
			</div>
			
			<div class="contents_middle">
				<table cellpadding="5px" style="table-layout:fixed;">	
						<tr>
						<% if(what.equals("id")){
								if(order.equals("desc")){ %>			
									<th width="20%"><a href="/MyApart/member/list?what=id&order=asc">아이디 ▼</a></th>
							 <% } else if(order.equals("asc")) { %>
						 			<th width="20%"><a href="/MyApart/member/list?what=id&order=desc">아이디 ▲</a></th>
						 	 <% }%>
						 	<th width="15%"><a href="/MyApart/member/list?what=name&order=desc">이름</a></th>
							<th width="14%"><a href="/MyApart/member/list?what=birth&order=desc">생년월일</a></th>
							<th width="13%"><a href="/MyApart/member/list?what=dong&order=desc">아파트(동)</a></th>
							<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=desc">가입 날짜</a></th>		
						
						<% }else if(what.equals("name")){ %>
							<th width="20%"><a href="/MyApart/member/list?what=id&order=desc">아이디 </a></th>
							
							<% if(order.equals("desc")){ %>			
									<th width="15%"><a href="/MyApart/member/list?what=name&order=asc">이름 ▼</a></th>
							<% } else if(order.equals("asc")) { %>
							 		<th width="15%"><a href="/MyApart/member/list?what=name&order=desc">이름 ▲</a></th>
						 	 <% }%>
							<th width="14%"><a href="/MyApart/member/list?what=birth&order=desc">생년월일</a></th>
							<th width="13%"><a href="/MyApart/member/list?what=dong&order=desc">아파트(동)</a></th>
							<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=desc">가입 날짜</a></th>		
						
						<% }else if(what.equals("birth")){ %>
							<th width="20%"><a href="/MyApart/member/list?what=id&order=desc">아이디 </a></th>
							<th width="15%"><a href="/MyApart/member/list?what=name&order=desc">이름 </a></th>
							
							<% if(order.equals("desc")){ %>	
									<th width="14%"><a href="/MyApart/member/list?what=birth&order=asc">생년월일 ▼</a></th>
									
							<% } else if(order.equals("asc")) { %>
									<th width="14%"><a href="/MyApart/member/list?what=birth&order=desc">생년월일 ▲</a></th>
						 	<% }%>
							<th width="13%"><a href="/MyApart/member/list?what=dong&order=desc">아파트(동)</a></th>
							<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=desc">가입 날짜</a></th>		
						
						<% }else if(what.equals("dong")) {%>
							<th width="20%"><a href="/MyApart/member/list?what=id&order=desc">아이디</a></th>
							<th width="15%"><a href="/MyApart/member/list?what=name&order=desc">이름</a></th>
							<th width="14%"><a href="/MyApart/member/list?what=birth&order=desc">생년월일</a></th>
							<% if(order.equals("desc")){ %>	
									<th width="13%"><a href="/MyApart/member/list?what=dong&order=asc">아파트(동) ▼</a></th>
							<% } else if(order.equals("asc")) { %>
									<th width="13%"><a href="/MyApart/member/list?what=dong&order=desc">아파트(동) ▲</a></th>
						 	<% }%>
							<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=desc">가입 날짜</a></th>		
						
						<% } else { %>
							<th width="20%"><a href="/MyApart/member/list?what=id&order=desc">아이디</a></th>
							<th width="15%"><a href="/MyApart/member/list?what=name&order=desc">이름</a></th>
							<th width="14%"><a href="/MyApart/member/list?what=birth&order=desc">생년월일</a></th>
							<th width="13%"><a href="/MyApart/member/list?what=dong&order=desc">아파트(동)</a></th>
							<% if(order.equals("desc")){ %>	
									<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=asc">가입 날짜 ▼</a></th>		
							<% } else if(order.equals("asc")) { %>
									<th width="23%"><a href="/MyApart/member/list?what=inputDate&order=desc">가입 날짜 ▲</a></th>		
						 	<% }%>
						<% } %>
							<th width="10%">성별</th>
							<th width="20%">핸드폰 번호</th>
						</tr>
						<% 	for(int i = startrow ; i <= endrow; i++){
								Member member = members.get(i);
								String input_date = member.getInputdate().substring(0, 11);
						%>
							<tr>
								<td align=center><%= member.getId() %></td>
								<td align=center><%= member.getName() %></td>
								<td align=center><%= member.getBirth() %></td>
								<td align=center><%= member.getDong() %>동</td>
								<td align=center><%= input_date %></td>
								<% if(member.getGender().equals("m")){ %>
									<td align=center>남</td>
								<% } else {%>
									<td align=center>여</td>
								<% } %>
								<td align=center><%= member.getPhone() %></td>	
							</tr>
						<% } %>
				</table>
			</div>
			<div class="contents_bottom">
					<hr class="line">
					<div id="paging">
						<% if(wheregroup > 1){%>
							[<a href='/MyApart/member/list?go=1&order=<%=order %>&what=<%=what %>&option=<%=option %>&keyword=<%=keyword %>'>처음</a>]
							[<a href='/MyApart/member/list?gogroup=<%= priorgroup %>&order=<%=order %>&what=<%=what %>&option=<%=option %>&keyword=<%=keyword %>'>이전</a>]
						<% } else {%>
							[처음]
							[이전]
						<% }
							
						for(int jj = startpage; jj<=endpage ; jj++){
							if(jj == where){ %>
								[<%= jj %>]
						<%  } else { %>
								[<a href='/MyApart/member/list?go=<%= jj%>&order=<%=order %>&what=<%=what %>&option=<%=option %>&keyword=<%=keyword %>'><%= jj%></a>]
						<%  } 
						
						} if(wheregroup < totalgroup){ %>
							[<a href='/MyApart/member/list?gogroup=<%= nextgroup%>&order=<%=order %>&what=<%=what %>&option=<%=option %>&keyword=<%=keyword %>'>다음</a>]
							[<a href='/MyApart/member/list?gogroup=<%= totalgroup%>&order=<%=order %>&what=<%=what %>&option=<%=option %>&keyword=<%=keyword %>'>마지막</a>]
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

