package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.FileUtil;
import utils.JSFunction;
@WebServlet("/edit.do")
@MultipartConfig(
	maxFileSize = 1024 * 1024 * 1,
	maxRequestSize = 1024 * 1024 * 10
)
public class EditController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx");
		MVCBoardDAO dao = new MVCBoardDAO();
		MVCBoardDTO dto = dao.selectView(idx);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/Edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. 파일 업로드 처리 ===========================
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		
		String originalFileName = "";
		try {
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		}catch(Exception e){
			JSFunction.alertBack(resp, "파일 업로드 오류입니다.");
			e.printStackTrace();
			return;
		}
		
		//2. 파일 업로드 외 처리 ===========================
		//수정 내용을 매개변수에서 얻어옴
		String idx = req.getParameter("idx");
		String prevOfile = req.getParameter("ofile");
		String prevSfile = req.getParameter("sfile");
		
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		//session에서 비밀번호 가져옴
		HttpSession session = req.getSession();
		String pass = (String) session.getAttribute("pass");
		
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setIdx(idx);
		dto.setName(name);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPass(pass);
		
		if(originalFileName != "") {
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
			
			FileUtil.deleteFile(req, "/Uploads", prevSfile);
		}else {
			//첨부 파일이 없으면 기존 이름 유지
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}
		
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.updatePost(dto);
		dao.close();
		
		if(result == 1) {
			session.removeAttribute("pass");
			resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
		}else {
			JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.", "../mvcboard/view.do?idx=" + idx);
		}
	}
}
