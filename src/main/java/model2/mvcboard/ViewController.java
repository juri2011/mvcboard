package model2.mvcboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//여기서는 web.xml에서 매핑하는 대신에 annotaion사용
@WebServlet("/view.do")
public class ViewController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MVCBoardDAO dao = new MVCBoardDAO();
		String idx = req.getParameter("idx");
		dao.updateVisitCount(idx);
		MVCBoardDTO dto = dao.selectView(idx);
		dao.close();
		
		//줄바꿈처리
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		//첨부파일 확장자 추출 및 이미지 타입 확인
		String ext = null, fileName = dto.getSfile();
		System.out.println("sfile = "+fileName);
		if(fileName != null) {
			ext = fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println("ext = " + ext);
		}
		
		String[] mimeStr = {"png","jpg","gif"}; //이미지 확장자
		List<String> mimeList = Arrays.asList(mimeStr);
		boolean isImage = false;
		if(mimeList.contains(ext)) {
			isImage = true;
		}
		
		//게시물(dto) 저장 후 뷰로 포워드
		req.setAttribute("dto", dto);
		req.setAttribute("isImage", isImage);
		req.getRequestDispatcher("/View.jsp").forward(req, resp);
	}
	
	

}
