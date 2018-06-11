<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.myapart.app.model.Home,java.text.*, java.util.*"%>
    
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MyApart</title>
	
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
	<script src="/MyApart/resources/js/jquery.innerfade.js"></script>
	
	<style>
		/* 헤더 마지막 이미지 높이 조정*/
		div#header_bottom {
			height:500px;
		}
		div#header_bottom>ul {
			position:inherit;
			left:-40px;
			top:0;
			width:100%;
			margin:0;
			list-style:none;
		}
		div#header_bottom>ul>li {
			width:100%;
			float:left;
			margin:0;
		}
		.slider_image{
			width:100%;
			height:500px;
		}
		
		/* 게시판 큰 틀 가로 너비 및 중간정렬 */
		div.board {
			margin-top:40px;
			width:98%;
			height:500px;
			margin-left:auto;
			margin-right:auto;
		}		
		div.board>.notice {
			position:inherit;
			top:20px;
			left:1%;
			bottom:20px;
			width:35%;
			height:460px;
			background-color:#ffffff;
			border-radius:8px;
			float:left;
			overflow-x:hidden;
			overflow-y:hidden;
			
		}
		div.board>.market {
			position:inherit;
			top:20px;
			left:2%;
			width:44%;
			height:460px;
			float:left;
			background-color:#ffffff;
			border-radius:8px;
			overflow-x:hidden;
			overflow-y:hidden;
		}
		div.board>.weather {
			position:inherit;
			top:20px;
			left:3%;
			width:16%;
			height:170px;
			float:left;
			background-color:#ffffff;
			border-radius:8px;
			overflow-x:hidden;
			overflow-y:hidden;
		}
		hr {
			position:inherit;
			width:100%;
		}
		
		h2 {
			position:inherit;
			top:5px;
			left:auto;
			right:auto;
			padding-left:15px;
		}
		div#notice_contents{
			position:inherit;
			top:5px;
			left:auto;
			right:auto;
			padding-left:20px;
			overflow:hidden;
			white-space:nowrap;
			text-overflow:ellipsis;
		}
		
		div#market_contents{
			position:inherit;
			top:5px;
			left:auto;
			right:auto;
			overflow:hidden;
			white-space:nowrap;
			text-overflow:ellipsis;
		}
		div>#weather_contents{
			position:inherit;
			top:5px;
			left:auto;
			right:auto;
			overflow:hidden;
			white-space:nowrap;
			text-overflow:ellipsis;
		}
		div>#weather_contents>table {
			width:85%; 
			margin-left:auto; 
			margin-right:auto;
		}
		.weather_line1{ border-right:1px dotted gray; border-bottom:1px dotted gray; }
		.weather_line4{ border-left:1px dotted gray; border-top:1px dotted gray; }
		#title {
			width:100%;
			max-width:420px;
			overflow:hidden; 
			white-space:nowrap;
			text-overflow:ellipsis; 
			text-align:left;
		}
		/*---------------------------------------------------------------*/
	</style>
	<script>
		$(document).ready(function(){
			$('#inner-fade').innerfade({
				animationtype: 'fade',
				speed: 750,
				timeout: 3000,
				type: 'random',
				containerheight: '500px'				
			});
		});
	</script>
	<script>
		jQuery(document).ready(function($) {
		      $.ajax({
		      url : "http://api.wunderground.com/api/{INPUT API KEY}/geolookup/conditions/lang:KR/q/Korea/Daegu.json",
		      dataType : "jsonp",
		      success : function(parsed_json) {
		         	var location = parsed_json.location;
		        	var location_s = "<p style='font-weight:bold;'>"+location.city+"</font></p>";
		         	
		         
		         	$("#locationinfo").append(location_s);
		         	var observ = parsed_json.current_observation;
		
					var icon_s = "<p>"+"<img src='"+observ.icon_url+"'/></p>";
		         	$("#imageinfo").append(icon_s); 
		
		         	var weather_s ="<p>"+observ.temp_c+"℃<br><small>";
					weather_s += observ.weather+"</small></p>";
					$("#tempinfo").append(weather_s);
					
				
		      	}
		      });
		    });

	
	</script>
	
</head>
<body topmargin="-20px" bottommargin="0" leftmargin="0" rightmargin="0">
	<div class="header">
		<jsp:include page="header.jsp"/>
		<div id="header_bottom">
			<ul id="inner-fade">
				<li><img src="/MyApart/resources/image/main.jpg" width="100%" class="slider_image" /></li> 
				<li><img src="/MyApart/resources/image/apt2.jpg" width="100%" class="slider_image" /></li>
				<li><img src="/MyApart/resources/image/apt3.jpg" width="100%" class="slider_image" /></li> 
			</ul>
		</div>
	</div>
	
	<div class="board">
		<div class="notice">
			<h2>공지</h2>
			<hr style="height:5px; background-color:black;">
			<div id="notice_contents">
				<table width="100%" cellspacing=10px>
					<%
						ArrayList<Home> noticeList = (ArrayList)request.getAttribute("homeNotice");
						for(Home home : noticeList){
					%>
						<tr>
							<td align=left>
								<div id="title">
									<strong><%=home.getNotTitle() %></strong>
								</div>
							</td>
						</tr>
					<% } %>
				</table>
			</div>
		</div>
		<div class="market">
			<h2>장터</h2>
			<hr style="height:5px; background-color:black;">
			<div id="market_contents">
				<table cellspacing=10px>
					<%	ArrayList<Home> shopList = (ArrayList)request.getAttribute("homeShop");
						if(shopList.size() < 3){
					%>
							<tr>
					<%		
							for(int i = 0 ; i < shopList.size() ; i++){%>
								<td width=30% height=158px align=center><a href="/MyApart/shop/contents?no=<%=shopList.get(i).getShopNum()%>"><img style="position:inherit; width:100%; height:100%;" src='/MyApart/resources/upload/<%=shopList.get(i).getShopUrl()%>'></a></td>
						<% 	} 
							if(shopList.size() == 1){ %>
								<td width=30% height=158px align=center></td>
								<td width=30% height=158px align=center></td>
						<% 	} 
							else if(shopList.size() == 2){%>			
								<td width=30% height=158px align=center></td>
						<%  } %>
							</tr>	
					<%	} else {%>
							<tr>
					<%
							for(int i = 0 ; i < 3 ; i++){%>
								<td width="30%" height=158px align=center><a href="/MyApart/shop/contents?no=<%=shopList.get(i).getShopNum()%>"><img style="position:inherit; width:100%; height:100%;" src='/MyApart/resources/upload/<%=shopList.get(i).getShopUrl()%>'></a></td>
						<%	} %>
							</tr>
							<tr>
					<%		for(int i = 3 ; i < shopList.size(); i++){ %>
								<td width="30%" height=158px align=center><a href="/MyApart/shop/contents?no=<%=shopList.get(i).getShopNum()%>"><img style="position:inherit; width:100%; height:100%;" src='/MyApart/resources/upload/<%=shopList.get(i).getShopUrl()%>'></a></td>
						<%	} %>	
							</tr>
					<%  } %>
				</table>
			</div>
		</div>
		<div class="weather">
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.(EEE)");
				Date today = new Date();
			%>
			<div id="weather_contents">
				<table>
					<tr>
						<td align=center class="weather_line1">
							<%=sdf.format(today) %>&nbsp
						</td>
						<td align=center class="weather_line2">
							<div id="locationinfo">
								
							</div>
						</td>
					</tr>
					
					<tr>
						<td align=center class="weather_line3">
							<div id="imageinfo">
							
							</div>
						</td>
						<td align=center class="weather_line4">
							<div id="tempinfo">
							
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div class="footer">
		<jsp:include page="tail.jsp"></jsp:include>
	</div>
</body>
</html>