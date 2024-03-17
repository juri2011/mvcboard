<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script>
function validateForm(form){
	//비밀번호 값이 비어있으면
	if(form.pass.value == ""){
		alert('비밀번호를 입력하세요.');
		form.pass.focus();
		return false;
	}
}
</script>
</head>
<body>
	<h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
	<form name="writeFrm" method="post" action="../mvcboard/pass.do" onsubmit="return validateForm(this);">
	<input type="hidden" name="idx" value="${param.idx}" />
	<input type="hidden" name="mode" value="${param.mode}" />
		<table border="1" style="width: 90%;">
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="pass" style="width:100px;" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="검증하기" />
					<input type="reset" value="RESET" />
					<input type="button" value="목록 바로가기" onclick="location.href='../mvcboard/list.do';"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>