<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	if(request.getAttribute("loginStatus") != null && !(Boolean)request.getAttribute("loginStatus")){
		out.println("<script>alert('입력하신 정보에 맞는 회원이 없습니다.');</script>");
	}
%>	
<!DOCTYPE html>
<html>
<head>
	<title>MyApart</title>
	<style>
	.button {
		border:#ff0080; /*---테두리 정의---*/
		background-Color: #1478ff; /*--백그라운드 정의---*/
		font: 12px 굴림; /*--폰트 정의---*/
		font-weight: bold; /*--폰트 굵기---*/
		color: #ffffff; /*--폰트 색깔---*/
		width: 130;
		height: 30; /*--버튼 크기---*/
	}
	.loginInfo {
		width: 400px; 
		height:40px; 
		text-align: left; 
		font-size: 20px; 
		margin-top: 5px;
	}
	</style>
	<script>
		function doCheck(){
			var form = document.loginForm;
			var elements = form.getElementsByClassName('loginInfo');
			var radio_val=0;
			
			for(var i = 0 ; i < elements.length ; i++){
				if(elements[i].value == ""){
					alert('모든 입력란에 내용을 입력해 주세요.');
					return;
				}
			}
			if(!checkValue(form.id, '아이디', 5, 15) || !checkValue(form.password, '비밀번호', 4, 15)){
				return;
			}
			
			form.submit();
		}
	
		function checkValue(target, cmt, lmin, lmax){
			var Alpha="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			var Digit = '1234567890';
			var astr = Alpha+Digit;
			var i;
			var tValue = target.value;
			
			if(tValue.length < lmin || tValue.length > lmax){
				if(lmin == lmax) alert(cmt + '는 ' + lmin+'자이어야 합니다.');
				else alert(cmt + '는' + lmin+'~'+lmax+'자 이내로 입력하셔야 합니다.');
				target.focus();
				return false;
			}
			
			if(astr.length > 1){
				for(i = 0 ; i <tValue.length; i++){
					if(astr.indexOf(tValue.substring(i,i+1))<0){
						alert(cmt+'에 허용할 수 없는 문자가 입력되었습니다.');
						target.focus();
						return false;
					}
				}
			}
			return true;
		}
		function joinWindow(){
			window.location.href="/MyApart/member/joinPage";
		}
	</script>

</head>
<body>
	<div style="width: 70%; margin: auto;">
		<div align=center>
			<a href='/MyApart/home'><img src="/MyApart/resources/image/logo.jpg"></a>
		</div>

		<form name=loginForm action='/MyApart/member/login' method=post>
			<div align=center style="margin-top: 5px;">

				<input name=id type="text" class='loginInfo' placeHolder="아이디를 입력하세요." maxlength='15' onkeyup="this.value=this.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');"><br>

				<input name=password type=password class='loginInfo' placeHolder="비밀번호를 입력하세요." maxlength='15'><br>
			</div>
			<br>
			<div align=center>
				<input type=button value=로그인 class="button" onClick="doCheck()"
					style="width: 400px; height: 40px; text-align: center; font-size: 20px; margin-top: 5px"><br>
					<hr width=400px color=gray>
				<input type=button value=회원가입 class="button" onClick='joinWindow()'
					style="width: 400px; height: 40px; text-align: center; font-size: 20px; margin-top: 5px;">
			</div>
			
			<p align=center> copyright ⓒ 2017 IT_YU All right reserved.
		</form>
	</div>
</body>
</html>