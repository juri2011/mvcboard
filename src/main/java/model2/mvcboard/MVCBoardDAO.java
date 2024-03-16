package model2.mvcboard;

import java.util.Map;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool{
	
	//DBConnPool 생성자 호출
		public MVCBoardDAO() {
			super();
		}
		
		//검색 조건에 맞는 게시물의 개수를 반환
		public int selectCount(Map<String, Object> map) {
			int totalCount = 0;
			//쿼리문 준비
			String query = "SELECT COUNT(*) FROM mvcboard";
			
			//검색한 키워드를 포함하는 게시물의 개수를 가져오도록 where절 추가
			if(map.get("searchWord") != null) {
				query += "WHERE " + map.get("searchField") + " "
						+ "LIKE '%" + map.get("searchWord") + "%'";
			}
			try {
				stmt = con.createStatement(); // 쿼리문 생성
				rs = stmt.executeQuery(query);// 쿼리문 실행
				rs.next();
				totalCount = rs.getInt(1); // 검색된 게시물 개수 저장
			} catch (Exception e) {
				System.out.println("게시물 카운트 중 예외 발생");
				e.printStackTrace();
			}
			
			//게시물 개수를 서블릿으로 반환
			return totalCount;
		}
}
