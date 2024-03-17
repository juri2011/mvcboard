<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
</head>
<body>
	<h2>파일 첨부형 게시판 - 상세 보기(View)</h2>
	<table border="1" width="90%">
		<colgroup>
			<col width="15%"/> <col width="35%"/>
			<col width="15%"/> <col width="*"/>
		</colgroup>
		<tr>
			<td>번호</td><td>${dto.idx}</td>
			<td>작성자</td><td>${dto.name}</td>
		</tr>
		<tr>
			<td>작성일</td><td>${dto.postdate}</td>
			<td>조회수</td><td>${dto.visitcount}</td>
		</tr>
		<tr>
			<td>제목</td><td colspan="3">${dto.title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">${dto.content}
				<c:if test="${not empty dto.ofile and isImage eq true }">
					<br /><img src="/mvcboard/Uploads/${dto.sfile}" style="max-width:100%;" alt="${dto.ofile}" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td>첨부 파일</td>
			<td>
				<c:if test="${not empty dto.ofile }">
					${dto.ofile}
					<a href="../mvcboard/download.do?ofile=${dto.ofile}&sfile=${dto.sfile}&idx=${dto.idx}">
						[다운로드]
					</a>
				</c:if>
			</td>
			<td>다운로드수</td>
			<td>${dto.downcount}</td>
		</tr>
		<!-- 하단 메뉴(버튼) -->
		<tr>
			<td colspan="4" align="center">
				<input type="button" value="수정하기" onclick="location.href='../mvcboard/pass.do?mode=edit&idx=${param.idx}';"/>
				<input type="button" value="삭제하기" onclick="location.href='../mvcboard/pass.do?mode=delete&idx=${param.idx}';"/>
				<input type="button" value="목록 바로가기" onclick="location.href='../mvcboard/list.do';"/>
			</td>
		</tr>
	</table>
</body>
</html>