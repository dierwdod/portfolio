<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>MyApart</title>
	
	<style>
		.button {
			border: #ff0080; /*---테두리 정의---*/
			background-Color: #1478ff; /*--백그라운드 정의---*/
			font: 12px 굴림; /*--폰트 정의---*/
			font-weight: bold; /*--폰트 굵기---*/
			color: #ffffff; /*--폰트 색깔---*/
			width: 130;
			height: 30; /*--버튼 크기---*/
		}
		
	
	</style>
	<script>
		function joinCheck(){
			var elements = document.member_join.getElementsByClassName('join_input');
			var radio = document.member_join.getElementsByClassName('gender_check');
			var radio_val=0;
			for(var i = 0 ; i < elements.length ; i++){
				if(elements[i].value == ""){
					alert('모든 입력란에 내용을 입력해 주세요.');
					return;
				}
			}
			for(var j = 0 ; j < radio.length ; j++){
				if(radio[j].checked){
					radio_val=1;
					break;
				}
			}
			if(radio_val == 0){
				alert("성벌을 선택해 주세요.");
				return;
			}
			var pwd = document.member_join.password.value;
			var repwd = document.member_join.password_confirm.value;
			if(pwd != repwd){
				alert('비밀번호가 일치하지 않습니다.');
				pwd.focus();
				return;
			}
			
			document.member_join.submit();
		}
		
		function checkNumber(){
			var form = document.member_join;
			
		    if(isNaN(form.dong.value)){
		        alert("숫자를 입력하세요");
		        form.dong.value="";
		    }
		}
				
		function autoHypenPhone(phone){
			var str = phone.value;
			var form = document.member_join;
			var phoneNum = form.phone.value.replace(/\-/g,'');
			if(isNaN(phoneNum)){
		        alert("숫자만 입력하세요");
		        form.phone.value="";
		        return;
		    }
            str = str.replace(/[^0-9]/g, '');
            var tmp = '';
            if( str.length < 4){
				return str;
            }else if(str.length < 7){
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3);
            	phone.value = tmp;
            	return;
            }else if(str.length < 11){
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3, 3);
                tmp += '-';
                tmp += str.substr(6);
            	phone.value = tmp;
            	return;
            }else{              
                tmp += str.substr(0, 3);
                tmp += '-';
                tmp += str.substr(3, 4);
                tmp += '-';
                tmp += str.substr(7);
            	phone.value = tmp;
            	return;
            }
            return str;
        }

				
		function MM_openBrWindow(theURL, winName, features){
			form = document.member_join;
			window.open(theURL,winName,features);
		}
		
	</script>
</head>
<body>
	<div style="width: 70%; margin: auto;">
		<div align=center>
			<a href='/MyApart/home'><img src="/MyApart/resources/image/logo.jpg"></a>
		</div>

		<form name=member_join action='/MyApart/member/join' method=post>
			<div align=center style="margin-top: 5px;">

				<input name=id type="text" placeHolder="아이디를 입력하세요." value='' class="join_input" maxlength="15" readonly
					style="width: 290px; height: 40px; text-align: left; font-size: 20px; margin-top: 5px;">
				<input name="check" type="button" value="입력" onClick="MM_openBrWindow('/MyApart/member/idCheckPage', 'userid_check', 'width=450, height=200')" 
					style="width: 100px; height: 40px; margin-top:5px; font-size:15px;"><br>

				<input name=password type=password placeHolder="비밀번호를 입력하세요." class="join_input" id="join_pwd" maxlength="15"
					style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>
			
				<input name=password_confirm type=password placeHolder="비밀번호를 다시 입력하세요." class="join_input" id="join_repwd" maxlength="15"
					style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>
			
				<input name=name type=text placeHolder="이름 입력하세요." class="join_input"  maxlength="20"
					style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>
			
				<input name=birth type=date placeHolder="생년월일을 선택하세요." class="join_input"
					style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>

				<div style="width:400px; height:40px; margin-top:10px; text-align:left;"> 
					<input name=gender type=radio value="m" class="gender_check">남&nbsp
					<input name=gender type=radio value="f" class="gender_check">여
				</div>
				<input name=phone type=text placeHolder="전화번호를 입력하세요."  class="join_input" id="cellPhone" maxlength="13" pattern="[0-9]-{10}-[0-9]?" title="잘 못 입력하셨습니다." onkeyup="autoHypenPhone(this)"
					style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>

				<input name=dong type=text placeHolder="거주하는 아파트의 동 ex)201"  class="join_input"maxlength="3" onkeyup="checkNumber()" style="width: 400px; height: 40px; text-align: left; font-size: 15px; margin-top: 5px"><br>
					
			</div>
			<br>

			<div align=center>
				<input type="hidden" name="usable">
				<input type="hidden" name="grade" value="user">
				<input type=button value=가입완료 class="button" onClick="joinCheck()" style="width: 400px; height: 40px; text-align: center; font-size: 20px; margin-top: 5px"><br>
				<hr width=400px color=gray>
				<p> 본인은 MYAPT에 거주하며 모든 이용약관에 동의합니다.
			</div>

			<p align=center>copyright ⓒ 2017 IT_YU All right reserved.
		</form>
	</div>
</body>
</html>