<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script>
	function validateForm(form){
		//작성자 값이 비어있으면
		if(form.name.value == ""){
			alert('작성자를 입력하세요.');
			form.name.focus();
			return false;
		}
		//제목 값이 비어있으면
		if(form.title.value == ""){
			alert('제목을 입력하세요.');
			form.title.focus();
			return false;
		}
		//내용이 비어있으면.
		if(form.content.value == ""){
			alert('내용을 입력하세요.');
			form.content.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<h2>파일 첨부형 게시판 - 수정하기(Edit)</h2>
	<form name="writeFrm" method="post" action="../mvcboard/edit.do" enctype="multipart/form-data"
		onsubmit="return validateForm(this);">
	<input type="hidden" name="idx" value="${dto.idx}"/>
	<input type="hidden" name="prevOfile" value="${dto.ofile}"/>
	<input type="hidden" name="prevSfile" value="${dto.sfile}"/>
		<table border="1" width="90%">
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="name" style="width:150px;" value="${dto.name}"/>
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="title" style="width:90%;" value="${dto.title}"/>
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea name="content" style="width:90%;height:100px;">${dto.content}</textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<input type="file" name="ofile" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="작성 완료"/>
					<input type="reset" value="RESET"/>
					<input type="button" value="목록 바로가기" onclick="location.href='../mvcboard/list.do';"/>
				</td>
			</tr>
		</table>
	
	</form>
</body>
</html>