package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//쿼리스트링으로부터 가져온 mode를 attribute로 등록
		req.setAttribute("mode", req.getAttribute("mode"));
		req.getRequestDispatcher("/Pass.jsp").forward(req, resp);
	}
	

}
