package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateEx01 {
	
	public static void main(String[] args) {
		DeptVo vo = new DeptVo();
		vo.setNo(1L);
		vo.setName("경영지원");
		
		boolean result = update(vo);
		System.out.println(result ? "성공" : "실패");
	}
	
	public static boolean update(DeptVo vo) {

		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결
			String url = "jdbc:mariadb://192.168.64.7:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement prepare
			String sql = "update dept set name = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. binding
			pstmt.setString(1, sql);
			pstmt.setLong(2, vo.getNo());
			
			// 4. SQL 실행
			int count = pstmt.executeUpdate();
			
			// 5. 결과 처리
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("드라이버 로딩 실패:" + e);
			
		} catch (SQLException e) {
			
			System.out.println("error:" + e);
			
		} finally {
			
			try {
				if(pstmt != null) {		// 열땐 Connection 먼저 열었으니 닫을땐 Statement 먼저
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		
		return result;
	}

}