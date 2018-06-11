<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.myapart.app.model.Member"%>

<!DOCTYPE html">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
		<style>
			ul>li>a {
				text-decoration:none;
			}
		</style>
		<script>
			function loginCheck(){
				var memid = document.getElementById('memid');
				if(memid.value.length == 0){
					alert('설문 조사는 로그인 후 이용이 가능합니다.');
					return;
				}
				else {
					window.location.href="/MyApart/survey/list";
				}
			}
		</script>
</head>
<body>
	<div id="header_top">
	<%
		Member member = (Member)session.getAttribute("loginMember");
		if(member == null){ %>
			<ul>
				<li><a href='/MyApart/member/loginPage'><font color='white'>로그인</font></a></li>
				<li><a href='/MyApart/member/joinPage'><font color='white'>회원 가입</font></a></li>
				
			</ul>
			<input type="hidden" id="memid" value=''>		
		<%} 
			else {%> 
				<ul>
					<li>[<%=member.getId() %>]<font size=2>님 환영합니다.</font></li>
				
				<% if((member.getGrade()).equals("admin")){%>
					<li><a href='/MyApart/member/list'><font color='white'>회원 관리</font></a></li>
				<% }else if(member.getGrade().equals("user")){ %>
						<li><a href='/MyApart/member/updateInfo'><font color='white'>회원 정보</font></a></li>
				 <%} %>
				    <li><a href='/MyApart/member/logout'><font color='white'>로그아웃</font></a></li>
		
				</ul>
			<input type="hidden" id="memid" value='<%=member.getId() %>'>	
		<%} %>
	</div>
	<div id="header_middle">
		<a href='/MyApart/home'><img src='/MyApart/resources/image/logo.jpg' alt="로고" style="width:100%; height:100%;"></a>
		<ul>
			<li><a href='/MyApart/notice/list'>공지 사항</a></li>
			<li><a href='/MyApart/suggest/list'>건의 사항</a></li>
			<li><a href='/MyApart/shop/list'>중고 장터</a></li>
			<li><a href='javascript:loginCheck()'>설문 조사</a></li>
		</ul>
	</div>	
</body>
</html>