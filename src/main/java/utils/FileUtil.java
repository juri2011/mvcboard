package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileUtil {
	
	//파일 업로드
	public static String uploadFile(HttpServletRequest req, String sDirectory)
					throws ServletException, IOException{
		
		//form에서 multipart로 보낸 데이터 중에서 "ofile"이라는 name을 가진 input 데이터를 가져온다.
		Part part = req.getPart("ofile");
		//Part 객체에서 name 속성과 파일명이 들어있는 Content-Disposition 헤더 가져오기
		String partHeader = part.getHeader("Content-Disposition");
		System.out.println(partHeader);
		//헤더에서 파일 이름만 가져오기
		String[] phArr = partHeader.split("filename=");
		//공백이 있으면 다듬고 따옴표 제거
		String originalFileName = phArr[1].trim().replace("\"", "");
		//원본 파일 이름이 비어있지 않다면
		if(!originalFileName.isEmpty()) {
			//Upload 디렉토리에 파일을 저장한다.
			part.write(sDirectory + File.separator + originalFileName);
		}
		//원본 파일명 return
		return originalFileName;
	}
	
	public static String renameFile(String sDirectory, String fileName) {
		//파일명에서 확장자만 빼오기(점이 2개 이상 포함될 수 있으므로 lastIndexOf()사용)
		String ext = fileName.substring(fileName.lastIndexOf("."));
		//현재날짜_시간으로 문자열을 가져온다.
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		String newFileName = now + ext;
		
		//원본파일의 이름을 새 파일 이름으로 바꾼다.
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		
		return newFileName;
	}
	
	public static void download(HttpServletRequest req, HttpServletResponse resp, String directory, String sfileName, String ofileName) {
		String sDirectory = req.getServletContext().getRealPath(directory);
			try {
				File file = new File(sDirectory, sfileName);
				InputStream iStream = new FileInputStream(file);
				
				//한글 파일명 깨짐 방지
				//사용자가 쓰고 있는 브라우저 확인
				String client = req.getHeader("User-Agent");
				if(client.indexOf("WOW64") == -1) {
					ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
				}else {
					ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
				}
				
				resp.reset();
				resp.setContentType("application/octet-stream");
				resp.setHeader("Content-Disposition",
							   "attachment; filename=\"" + ofileName + "\"");
				resp.setHeader("Content-Length","" + file.length());
				
				//response 내장 객체로부터 새로운 출력 스트림 생성
				OutputStream oStream = resp.getOutputStream();
				
				byte b[] = new byte[(int)file.length()];
				int readBuffer = 0;
				while( (readBuffer = iStream.read(b)) > 0 ) {
					oStream.write(b, 0, readBuffer);
				}
				
				//입출력 스트림 닫음
				iStream.close();
				oStream.close();
			} catch (FileNotFoundException e) {
				System.out.println("파일을 찾을 수 없습니다.");
				e.printStackTrace();
			}catch(Exception e) {
				System.out.println("예외가 발생하였습니다.");
				e.printStackTrace();
			}
	}
	
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if(file.exists()) {
			file.delete();
		}
	}
}
