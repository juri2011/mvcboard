package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
}