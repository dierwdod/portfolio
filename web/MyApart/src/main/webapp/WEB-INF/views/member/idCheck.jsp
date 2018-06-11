<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>



<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MyApart</title>
	<link rel="StyleSheet" href="style.css" type="text/css">
	<script>
	
		window.onload = function(){
			var form = document.id_check;
			if(form.checkId.value.length != 0 && form.usable.value == 'true'){
				form.checkId.readOnly = true;
			}
			else {
				form.checkId.readOnly = false;
			}
		}
		function checkEnd(){
			var form = document.id_check;
			if(form.checkId.value.length == 0){
				alert('아이디를 입력해 주세요.');
				form.checkId.focus();
				return;
			}
			if(form.checkId.value.length != 0){
				if(form.usable.value == 'true') {
					opener.member_join.id.value = form.checkId.value;
					opener.member_join.usable.value = form.usable.value;
					self.close();
				}
				else if(form.usable.value == 'false') {
					alert('중복 확인을 해주세요.');
					form.checkId.focus();
					return;
				}
			}
		}	
		function doCheck(){
			var form = document.id_check;
			if(!checkValue(form.checkId, '아이디', 5, 16)){
				form.usable.value = 'false';
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
				if(lmin == lmax) alert(cmt + '는 ' + lmin+'Byte이어야 합니다.');
				else alert(cmt + '는' + lmin+'~'+lmax+'Byte 이내로 입력하셔야 합니다.');
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
	</script>

</head>
<body text="#000000" bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<br/>
	<br/>
	<%
		String confirm_id = "";	
		boolean usable = false;
		if(request.getAttribute("userid") != null){
			confirm_id = (String)request.getAttribute("userid");
			usable = (Boolean)request.getAttribute("usable");
		}
	%>	
	<form name="id_check" method="post" action="/MyApart/member/exist">
		
		<!-- <input type="hidden" name="check_count" value="???"> -->
		<table width="330" border="0" cellspacing="0" cellpadding="0" align="center" class="style1">
			<tr align=center>
				<td>[원하는 아이디를 입력하세요.]</td>
			</tr>
		</table>
		<table width="300" border="0" bgcolor="#b6c1d6" height="39" align="center" class="style1">
			<tr>
				<td bgcolor="#ffffff" width="40%" align="center">
					<input type="text" name="checkId" value='<%=confirm_id %>' onkeyup="this.value=this.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');" maxlength="16" size="16" class="input_style1">
					<input type="hidden" name="usable" value='<%=usable %>' >
					<input type="button" value="중복 확인" onClick="doCheck()" class="input_style1">
				</td>
			</tr>
			<tr>
				<td>
 					<%
 						if(confirm_id.length() != 0){
 							if(!usable){
 					%>
 								▶ [<%=confirm_id %>]은 등록되어있는 아이디입니다.<br> ▷ 다시시도해 주세요.
 					<%		} else { %>
								▶ [<%=confirm_id %>]는 사용 가능합니다.
					<%		} 
 						}
					%>
				
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td align="center">
					<input type="button" value="확인" onClick="checkEnd()" class="style1">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>