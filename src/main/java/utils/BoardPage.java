package utils;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl) {
		/*
		 	paramter
		 	
		 	totalCount: 게시물 전체 수
		 	pageSize: 페이지 별 제한 게시물 수 (여기서는 10개)
		 	blockPage: 1 블록당 제한 페이지 수 (여기서는 5페이지)
		 	pageNum: 현재 페이지
		 	reqUrl: 페이지 리스트를 보여줄 url(../mvcboard/list.do
		 								  -> web.xml에서 listController의 url-pattern으로 등록함)
		*/
		
		//view의 하단 페이징 부분에 추가할 태그 문자열
		String pagingStr = "";
		
		//전체 페이지 수
		int totalPages = (int) (Math.ceil((double) totalCount / pageSize));
		
		//이전 페이지 블록 바로가기
		//pageTemp: 현재 블록의 첫번째 페이지부터 시작
		int pageTemp = (int) Math.ceil((double)pageNum / blockPage);
		
		//이전 블록이 있을 경우 이동할 수 있도록 링크 추가
		if (pageTemp != 1) {
			//맨 첫번째 페이지로 이동
			pagingStr += "<a href='" + reqUrl + "?pageNum=1'>[첫 페이지]</a>";
			pagingStr += "&nbsp;";
			//이전 블록의 맨 마지막 페이지로 이동
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp-1) + "'>[이전 블록]</a>";
		}
		
		int blockCount = 1; //기본값 1
		
		//한 블록에 대해서 처리
		//현재 페이지 번호가 전체 페이지 수보다 크면 안되기 때문에 &&로 조건을 연결해주었다.
		while(blockCount <= blockPage && pageTemp <= totalPages) {
			
			//현재페이지는 링크를 걸지 않는다.
			if(pageTemp == pageNum) {
				pagingStr += "&nbsp;" + pageTemp + "&nbsp;";
			} else {
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp + "'>" + pageTemp + "</a>&nbsp;";
			}
			pageTemp++;
			blockCount++;
		}
		
		//현재 pageTemp : 다음 블록 첫번째 페이지
		//다음 블록이 있을 경우 처리
		if(pageTemp <= totalPages) {
			//다음 블록의 맨 첫번째 페이지로 이동
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + "'>[다음 블록]</a>";
			pagingStr += "&nbsp;";
			//맨 마지막 페이지로 이동
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages + "'>[마지막 페이지]</a>";
		}
			
		return pagingStr;
	}
}
