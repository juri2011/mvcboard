<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<style>a{text-decoration:none;}</style>
</head>
<body>
	<h2>파일 첨부형 게시판 - 목록 보기(List)</h2>
	
	<!-- action 속성이 생략되면 현재 페이지로 요청한다. -->
	<form method="get">
	<table border="1" width="90%">
	<tr>
		<td align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>
	</table>
	</form>
	
	<!-- 목록 테이블 -->
	<table border="1" width="90%">
		<tr>
			<th width="10%">번호</th>
			<th width="*">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
			<th width="8%">첨부</th>
		</tr>
		<c:choose>
			<c:when test="${empty boardLists}">
				<tr>
					<td colspan="6" align="center">
						등록된 게시물이 없습니다 ^^;
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<!--
					동작 순서
					1. Default.jsp에 의해 ListController가 실행된다.
					2. ListController에 전달된 파라미터 값이 없으므로 1페이지를 보여주기로 정한다.(1번~10번)
					3. 여기서 출력시킬 페이지 링크의 문자열을 받아온다.
					4. ${map.pagingImg}로 동적으로 a태그를 생성한다.
					5. 사용자가 검색을 하거나, 다른 페이지를 클릭했으면 다시 ListController로 요청한다.
						(ListController로 요청하는 a태그는 BoardPage에서 이미 만들어졌다.)
					6. 요청을 받은 ListController는 쿼리스트링(파라미터)에 페이지번호가 들어와있는지 검사한다.
					7. 페이지 번호가 있다면 그 페이지의 게시물 목록만 받아와서 view에 출력한다.
				-->
				<c:forEach items="${boardLists}" var="row" varStatus="loop">
					<tr align="center">
						<!--
							(검색 결과에 맞춰서) 가상 번호를 부여한다. 번호는 최신게시물 순으로 내림차순 출력한다.
							예: (검색결과 5개) 첫번째 게시물 -> 5, 두번째 게시물 -> 4	
						-->
						<td>
							${map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index)}
						</td>
						<td align="left">
							<!-- 제목(링크) -->
							<a href="../mvcboard/view.do?idx=${row.idx}">${row.title}</a>
						</td>
						<td>${row.name}</td>
						<td>${row.visitcount}</td>
						<td>${row.postdate}</td>
						<td>
							<c:if test="${ not empty row.ofile}">
								<a href="../mvcboard/download.do?ofile=${row.ofile}&sfile=${row.sfile}
											&idx=${row.idx}">[Down]</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
	</table>
	
	<table border="1" width="90%">
		<tr align="center">
			<!-- BoardPage에서 페이징에 대해 설정한 것을 바탕으로 동적으로 태그를 생성한다. -->
			<td>
				${map.pagingImg}
			</td>
			<td width="100">
				<button type="button" onclick="location.href='../mvcboard/write.do';">글쓰기</button>
			</td>
		</tr>
	</table>
	
</body>
</html>