package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JSFunction {
	//alert를 띄우고 지정한 경로로 이동
	public static void alertLocation(HttpServletResponse resp, String msg, String url) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			/*
 				전 예제에선 jsp에서 함수를 불러와서 script를 동적 출력하기 위해 JspWriter를 썼는데
 				이 예제에선 서블릿에서 함수를 불러오므로 PrintWriter를 사용하여 script를 출력한다.
			*/
			PrintWriter writer = resp.getWriter();
			String script = "<script>"
						  + "	alert('" + msg + "');"
						  + "	location.href='" + url + "';"
						  + "</script>";
			writer.print(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//alert를 띄우고 뒤로가기
	public static void alertBack(HttpServletResponse resp, String msg, String url) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			/*
 				전 예제에선 jsp에서 함수를 불러와서 script를 동적 출력하기 위해 JspWriter를 썼는데
 				이 예제에선 서블릿에서 함수를 불러오므로 PrintWriter를 사용하여 script를 출력한다.
			*/
			PrintWriter writer = resp.getWriter();
			String script = "<script>"
						  + "	alert('" + msg + "');"
						  + "	history.back();"
						  + "</script>";
			writer.print(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
