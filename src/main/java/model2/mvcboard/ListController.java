package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BoardPage;

public class ListController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MVCBoardDAO dao = new MVCBoardDAO();
		
		// view에 전달할 매개변수용 map 생성
		Map<String,Object> map = new HashMap<>();
		
		//검색 필드(title/content)
		String searchField = req.getParameter("searchField");
		//검색 키워드
		String searchWord = req.getParameter("searchWord");
		if(searchWord != null) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		int totalCount = dao.selectCount(map);
		
		/* 페이지 처리 start */
		ServletContext application = getServletContext();
		
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		
		//현재 페이지 확인
		int pageNum = 1; //기본값
		String pageTemp = req.getParameter("pageNum");
		//페이지 번호가 비어있지 않다면
		if(pageTemp != null && !pageTemp.equals("")) pageNum = Integer.parseInt(pageTemp);
		
		int start = (pageNum - 1) * pageSize + 1; //현재 페이지의 첫 게시물 번호
		int end = pageNum * pageSize; //현재 페이지의 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		/* 페이지 처리 end */
		
		//게시물 목록 받기
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		boardLists.forEach(board -> System.out.println(board.toString()));
		dao.close();//DB 연결 닫기
		
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "../mvcboard/list.do");
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		req.setAttribute("boardLists", boardLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher("/mvcboard/list.jsp").forward(req, resp);
	}
	
	
}
