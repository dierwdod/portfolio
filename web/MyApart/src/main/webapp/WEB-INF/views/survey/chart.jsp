<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, java.text.*,com.myapart.app.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
%>    
<%
	SurveyChart sc = null;
	ArrayList<String> ageList = null;
	int sur_num = (Integer)request.getAttribute("no");
	if(request.getAttribute("participationChart")!= null){
		sc = (SurveyChart)request.getAttribute("participationChart");
	}
	else if(request.getAttribute("genderChart") != null){
		sc = (SurveyChart)request.getAttribute("genderChart");
	}
	else if(request.getAttribute("ageChart")!= null){
		sc = (SurveyChart)request.getAttribute("ageChart");
		ageList = (ArrayList<String>)sc.getBirthList();
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MyApart</title>
	<link rel="stylesheet" type="text/css" href="/MyApart/resources/css/board.css">
	<script src="/MyApart/resources/chart/amcharts.js"></script>
	<script src="/MyApart/resources/chart/pie.js"></script>
	<!-- <script src="chart/themes/light.js"></script> -->
	<script src="//www.amcharts.com/lib/3/themes/light.js"></script>
	<script src="/MyApart/resources//chart/plugins/animate/animate.min.js"></script>
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
		.contents_top>#chartSelect {
			width:90%; 
			float:left; 
			text-align:center;
			margin-bottom:15px;
			
		}
		.contents_top>#chartSelect>a{
			text-decoration:none;
			color:black;
			font-size:20px;
			margin-left:10%;
			padding:3px;
			border:1px solid black;
			border-radius:5px;
			
		}
		.contents_top>#chartSelect>a:hover {
			background-color:#eeeeee;
		}
		.contents_top>#chartSelect>a:active {
			background-color:#eeeeee;
		}
		.contents_top>#chartSelect>a:visited {
			background-color:#eeeeee;
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
			height:100%;
			overflow:hidden;
			margin-left:auto;
			margin-right:auto;
		}
		.contents_middle>#chartdiv {
			position:inherit;
			top:-50px;
			left:0;
			width:100%;
			height:100%;
		}
		#chartHeader {
			width:100%;
			margin-top:20px;
			font-weight:bold;
			font-size:20px;
			text-align:center;
		}
		#chartSubHeader{
			width:100%;
			margin-top:10px;
			font-size:15px;
			text-align:center;
		}
		.chartContents {
			width:100%;
			margin-top:15%;
			text-align:center;
		}
	</style>
	<script>
	 document.addEventListener("DOMContentLoaded", function(){

	 
		 AmCharts.addInitHandler(function(chart) {
				
			  if (!chart.growSlices)
			    return;
	
			  // save original data
			  chart.originalData = copyObject(chart.dataProvider);
	
			  // replace data
			  chart.dataProvider = [];
			  var sum = 0;
			  for (var i = 0; i < chart.originalData.length; i++) {
			    var dp = copyObject(chart.originalData[i]);
			    dp[chart.valueField] = chart.originalData[i][chart.valueField] / 1000;
			    sum += chart.originalData[i][chart.valueField];
			    chart.dataProvider.push(dp)
			  }
	
			  // is alphaField set?
			  if (chart.alphaField === undefined)
			    chart.alphaField = "_alpha";
	
			  // add big data point
			  var dp = {};
			  dp[chart.titleField] = "";
			  dp[chart.valueField] = sum * 500;
			  dp[chart.alphaField] = 0;
			  chart.dataProvider.push(dp);
	
			  var dp = {};
			  dp[chart.titleField] = "";
			  dp[chart.valueField] = 0;
			  dp[chart.alphaField] = 0;
			  chart.originalData.push(dp);
	
			  // add event to animate data
			  if (chart.listeners === undefined)
			    chart.listeners = [];
	
			  chart.addListener("rendered", function(e) {
			    e.chart.animateData(e.chart.originalData, {
			      duration: 1700,
			      complete: function() {
			        // remove the last data point
			        chart.dataProvider.pop();
			      }
			    });
			  });
			  
			  function copyObject(obj) {
			    return JSON.parse(JSON.stringify(obj));
			  }
	
			})
	

			var type = document.getElementById("chartType").value;

			if(type=='gender'){
				<% if(sc != null){%>
					var manCount = Number(<%=sc.getManCount() %>);
					var womanCount = Number(<%=sc.getWomanCount()%>);
				<%}%>
				var chart = AmCharts.makeChart("chartdiv", {
					  "type": "pie",
					  "theme": "light",
					  "growSlices": true,
					  "dataProvider": [{
					    "category": "남",
					    "value": manCount
					  }, {
					    "category": "여",
					    "value": womanCount
					  }],
					  "valueField": "value",
					  "titleField": "category",
					  "labelRadiusField": "labelRadius",
					  "alphaField": "alpha",
					  "startDuration": 0
					});
			}
				
			else if(type=='parti'){
				<% if(sc != null){%>
					var allMember = <%=sc.getMemberCount() %>;
					var partiCount = <%=sc.getPartiCount() %>;
					var nonePartiCount = allMember-partiCount;
					
					var pCount = Number(partiCount);
					var npCount = Number(nonePartiCount);
				<%}%>
				var chart = AmCharts.makeChart("chartdiv", {
					  "type": "pie",
					  "theme": "light",
					  "growSlices": true,
					  "dataProvider": [{
					    "category": "참여",
						"value": pCount
					  }, {
					    "category": "미참여",
					    "value": npCount
					  }],
					  "valueField": "value",
					  "titleField": "category",
					  "labelRadiusField": "labelRadius",
					  "alphaField": "alpha",
					  "startDuration": 0
					});
			}
			else if(type=='age'){
				<% if(ageList != null){%>
					var agelist = new Array(<%
						for(int i = 0 ; i < ageList.size(); i++){
							if(i == 0){
								out.print("\""+ageList.get(i)+"\"");
							}
							else {
								out.print(","+ "\""+ageList.get(i)+"\"");
							}
						}%>);
				<%}%>

				var chart = AmCharts.makeChart("chartdiv", {
					  "type": "pie",
					  "theme": "light",
					  "growSlices": true,
					  "dataProvider": ageProvider(agelist),
					  "valueField": "value",
					  "titleField": "category",
					  "labelRadiusField": "labelRadius",
					  "alphaField": "alpha",
					  "startDuration": 0
					});
			}
			
			function ageProvider(arr){
				var xarr = new Array();
				var ageArr = [0,0,0,0,0,0];
				var d = new Date();
				var n = d.getFullYear();
				var cate = '';
				for(let k = 0 ; k < arr.length ; k++){
					var curAge = arr[k].substring(0,4);
					var age = n - curAge;
					age = parseInt(age/10);
					ageArr[age]++;
				}
				for(let j = 1 ; j < ageArr.length ; j++){
					var x = new Object();
					cate = (j*10)+'대';
					x.category=cate;
					x.value=Number(ageArr[j]);
					xarr.push(x);
				}

				return xarr;
			}
			
		});
			
	</script>
</head>
<body>
	
	<div class="header">
		<jsp:include page="../home/header.jsp"/>
		<div id="header_bottom">
			<img src="/MyApart/resources/image/apt.jpg" style="width:100%; height:250px" />
		</div>
	</div>
	
	<div class="board">
		<div class="board_title">
			<h1>설문 조사<font size='5'> (차트)</font></h1>
		</div>
		
		<div class="navigation">
			<ul>
				<li><a href='/MyApart/survey/list'>설문 목록</a></li>
				<li><a href='/MyApart/survey/chartList'>통계 자료</a></li>
			</ul>
		</div>
		
		<div class="board_contents">
			<div class="contents_top">
				<div id="chartSelect">
					<a href="/MyApart/survey/chart?no=<%=sur_num %>&mode=participation">참여 비율 차트</a>
					<a href='/MyApart/survey/chart?no=<%=sur_num %>&mode=age'>연령별 차트</a>&nbsp&nbsp	
					<a href='/MyApart/survey/chart?no=<%=sur_num %>&mode=gender'>성별 차트</a>&nbsp&nbsp	
				</div>
				<hr class="line">
			</div>
			
			<div class="contents_middle">
				<div id="chartHeader">
					[<%=sc.getSurTitle() %>]
				</div>
			<%	if(request.getAttribute("genderChart") != null){%>
					<div id="chartSubHeader">
						남/여 설문 참여 비율
					</div>
					<%if(sc.getManCount() == 0 && sc.getWomanCount() == 0){ %>
						<div class="chartContents"><font size="4"><strong>작성된 설문지가 없습니다.</strong></font></div>
					<%} else { %>
						<div id="chartdiv"></div>
					<%} %>
					<input type="hidden" id="chartType" value="gender">
			<% 	} if(request.getAttribute("participationChart")!= null){ %>
					<div id="chartSubHeader">
						참여/미참여 비율
					</div>
					<div id="chartdiv"></div>
					<input type="hidden" id="chartType" value="parti">
			<% 	} if(request.getAttribute("ageChart")!= null){ %>
					<div id="chartSubHeader">
						연령 별 참여 비율
					</div>
					<%if(ageList.size() == 0){ %>
						<div class="chartContents"><font size="4"><strong>작성된 설문지가 없습니다.</strong></font></div>
					<%} else { %>
						<div id="chartdiv"></div>
					<%} %>
					<input type="hidden" id="chartType" value="age">
			<% 	} %>
			</div>
		</div>
	</div>
	
	<div class="footer">
		<jsp:include page="../home/tail.jsp"/>
	</div>

</body>
</html>

