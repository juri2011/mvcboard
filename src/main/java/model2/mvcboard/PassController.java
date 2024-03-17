package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.FileUtil;
import utils.JSFunction;

@WebServlet("/pass.do")
public class PassController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//쿼리스트링으로부터 가져온 mode를 attribute로 등록
		req.setAttribute("mode", req.getAttribute("mode"));
		req.getRequestDispatcher("/Pass.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");
		
		MVCBoardDAO dao = new MVCBoardDAO();
		boolean confirmed = dao.confirmPassword(pass, idx);
		dao.close();
		
		
		if(confirmed) {//비밀번호가 같을 때
			if(mode.equals("edit")) { //수정모드라면
				/*
 					비밀번호를 session에 저장한 이유?
 					사용자가 URL 패턴을 이용해서 비밀번호 입력 없이 수정하기 페이지에 접속하면 안 되기 때문에
 					session에 저장된 비밀번호 값을 검사해서 정상적으로 수정 작업이 이루어질 수 있도록 하는 것이다.
				*/
				
				HttpSession session = req.getSession();
				session.setAttribute("pass", pass);
				resp.sendRedirect("../mvcboard/edit.do?idx="+idx);
			} else if(mode.equals("delete")) {//삭제모드라면
				dao = new MVCBoardDAO();
				MVCBoardDTO dto = dao.selectView(idx);
				int result = dao.deletePost(idx);
				//성공적으로 삭제되었다면
				if(result == 1) {
					//첨부파일도 삭제하기
					String saveFileName = dto.getSfile();
					FileUtil.deleteFile(req, "/Uploads", saveFileName);
				}
				JSFunction.alertLocation(resp, "삭제되었습니다.", "../mvcboard/list.do");
			}
		}else { //비밀번호가 틀릴 때
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
		}
	}
	

}
