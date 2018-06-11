<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java"
	import="java.sql.*,java.util.*,java.text.*,com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("utf-8");
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
		function setLine(txa) {
			line = 5 //기본 줄 수
	
			new_line = txa.value.split("\n").length + 1;
			if (new_line < line)
				new_line = line;
	
			txa.rows = new_line;
		}
	
		function check(f) {
			blank = false;
			for (i = 0; i < f.elements.length; i++) {
				if (f.elements[i].value == "") {
					if (f.elements[i].name != "large")
						brank = true;
				}
			}
	
			if (blank == true)
				alert("모든 항목을 입력하셔야 합니다.");
			else
				f.submit();
		}
		function deleteCheck(arg0){
			if(confirm("삭제하시겠습니까?")){
				window.location.href='/MyApart/shop/delete?no=' + arg0;
			}else{
				return false;
			}				
		}	

	</script>
</HEAD>
<BODY>
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src='/MyApart/resources/image/apt.jpg' alt="아파트" style="width: 100%; height: 100%;">
		</div>
	</div>

	<div class="board">
		<div class="board_title">
			<h1>중고 장터</h1>
		</div>
		<div class="board_contents">
			<div class="contents_middle">
				<%
					Shop shop = (Shop) request.getAttribute("shop");
					String shop_reg_date = shop.getShopDate().substring(0, 11);
				%>
					<table width=100%>
						<tr>
							<td align=left style="width:70%; font-weight:bold; "><font
								size=4 face=돋움 color=black><%=shop.getShopTitle()%></font></td>
							<td width="20%" style="font-size:small;" align=right>NO. <%=shop.getShopNum()%></td>
						</tr>
						<tr>
							<td  colspan=3><hr></td>
						</tr>
						<tr>
							<td  style="font-size:small;" align=left>작성자 : <%=shop.getId()%></td>
							<td  style="font-size:small;" align=right>등록 일자 : <%=shop_reg_date%></td>
						</tr>
						<tr>
							<td  colspan=3><hr></td>
						</tr>
						<tr>
							<td style="width:100%; height:350px;" colspan=4 align=center>
								<img style="position:inherit; width:80%; height:100%;" src='/MyApart/resources/upload/<%=shop.getShopUrl()%>'>
							</td>
						</tr>
						<tr>
							<td colspan=3><hr></td>
						</tr>
						<tr>
							<td colspan=2 style="padding:15px;" valign=top width=70%><pre><%=shop.getShopContents()%></pre></td>
						</tr>
					</table>
			</div>
			<div class="contents_bottom">
				<hr class="line">
				
				<input type="hidden" name="num" value=<%=shop.getShopNum()%>>
				
				<%if(member != null && member.getId().equals(shop.getId())){ %>
					<input type=button value=삭제 onclick="deleteCheck('<%=shop.getShopNum()%>')"></a>
					<input type=button value="수정" onclick="location.href='/MyApart/shop/contents?no=<%=shop.getShopNum()%>&action=update';"> 
				<%} %>
				<input type=button value='목록' onclick="location.href='/MyApart/shop/list';">
							
			</div>
		</div>
	</div>
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>
</BODY>
</HTML>