package model2.mvcboard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
			
			System.out.println(map.get("searchWord"));
			System.out.println(map.get("searchField"));
			
			//제목/내용으로 검색한 키워드를 포함하는 게시물의 개수를 가져오도록 where절 추가
			if(map.get("searchWord") != null) {
				query += " WHERE " + map.get("searchField") + " "
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

		public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
			
			List<MVCBoardDTO> board = new Vector<>();
			
			//게시글 번호와 게시글 정보를 가져온다.
			//rownum의 범위를 구하기 위해서 우선 rownum을 검색하는 select문을 from절에 서브쿼리로 넣어야 한다.
			String query = "SELECT * FROM ( "
						 + "	SELECT Tb.*, ROWNUM rNum FROM ( "
						 + "		SELECT * FROM mvcboard ";
			//검색조건이 있으면 where절 추가
			if(map.get("searchWord") != null) {
				query += " WHERE " + map.get("searchField")
					   + " LIKE '%" + map.get("searchWord") + "%' ";
			}
			
			query+="		ORDER BY idx DESC "
				 + "	)Tb "
				 + " ) "
				 + " WHERE rNum BETWEEN ? AND ?"; //게시물 구간은 인파라미터로
			
			try {
				pstmt = con.prepareStatement(query); // 동적 쿼리문 생성
				pstmt.setString(1, map.get("start").toString()); // 인파라미터 설정
				pstmt.setString(2, map.get("end").toString());
				rs = pstmt.executeQuery(); //쿼리문 실행
				
				while(rs.next()) {
					MVCBoardDTO dto = new MVCBoardDTO();
					dto.setIdx(rs.getString(1));
					dto.setName(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setPostdate(rs.getDate(5));
					dto.setOfile(rs.getString(6));
					dto.setSfile(rs.getString(7));
					dto.setDowncount(rs.getInt(8));
					dto.setPass(rs.getString(9));
					dto.setVisitcount(rs.getInt(10));
					
					board.add(dto);
				}
			} catch (Exception e) {
				System.out.println("게시물 조회 중 예외 발생");
				e.printStackTrace();
			}
			return board;
		}
}
