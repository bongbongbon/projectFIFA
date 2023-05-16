package pack00.project;


import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.qdox.model.Member;
public class projectDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public Connection getConn() {
		String url = "jdbc:oracle:thin:@211.223.59.99:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hanul" , "0000");			
		}catch (Exception e) {
			System.out.println("알 수 없는 오류발생! 개발자에게 문의하세요 ");
		}
		return conn;
	}

	public void dbClose() {
		try {
			if(rs != null) { rs.close(); }
			if(ps != null) { ps.close(); }
			if(conn != null) { conn.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectList() {
		try {
			conn = getConn(); //DB 연결객체 초기화 시키기
			ps = conn.prepareStatement("select * from FIFAMEMBER" );
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("MEMEBER_ID"));
			}
		} catch (SQLException e) {
			System.out.println(" Exception : " + e.getMessage());
		}finally {
			dbClose();
		}
	}
	
	// 로그인
	public void login(String MEMBER_ID, String MEMBER_PASSWORD) {
			try {
				conn = getConn(); //DB 연결객체 초기화 시키기
				ps = conn.prepareStatement("SELECT * FROM FIFAMEMBER WHERE MEMBER_ID = ? AND MEMBER_PASSWORD = ?" );
				ps.setString(1,  MEMBER_ID);
				ps.setString(2,  MEMBER_PASSWORD);
				rs = ps.executeQuery();
				 if (rs.next()) {
		                System.out.println("로그인 성공!");
		            } else {
		                System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
		            }
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	// 중복확인
	public boolean checkID(String MEMEBER_ID) {
		boolean result = false;
		conn = getConn(); //DB 연결객체 초기화 시키기
			try {
				String SQL = "select MEMEBER_ID from FIFAMEMBER where MEMEBER_ID = ?";
				ps = conn.prepareStatement(SQL);
				ps.setString(1, MEMEBER_ID);
				rs =ps.executeQuery();
				if(rs.next()) {
					System.out.println("중복된 아이디입니다 다른 아이디를 입력해주세요");
					result = true;
				}else {
						System.out.println("사용가능한 아이디 입니다.");
					result = false;
					}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	
	// 회원가입
	public void member(String MEMBER_ID, String MEMBER_PASSWORD, 
			String MEMBER_NAME, String MEMBER_AGE, String MEMBER_BP) {
		try {
			conn = getConn(); //DB 연결객체 초기화 시키기
			ps = conn.prepareStatement("INSERT INTO FIFAMEMBER (MEMBER_ID, MEMBER_PASSWORD,MEMBER_NAME,MEMBER_AGE,MEMBER_BP) VALUES(?,?,?,?,?)" );
			ps.setString(1,MEMBER_ID);
			ps.setString(2,MEMBER_PASSWORD);
			ps.setString(3,MEMBER_NAME);
			ps.setString(4,MEMBER_AGE);
			ps.setString(5,MEMBER_BP);
			int result = ps.executeUpdate();	
			System.out.println("가입에 성공하셨습니다.");
		} catch (Exception e) {
			System.out.println(" Exception : " + e.getMessage());
		} 
	}


	
	// 보유 BP 조회
	public void checkBP(String MEMBER_ID) {
		try {
			conn = getConn(); //DB 연결객체 초기화 시키기
			ps = conn.prepareStatement("SELECT MEMBER_BP FROM FIFAMEMBER WHERE MEMBER_ID = ?" );
			ps.setString(1, MEMBER_ID);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getInt("MEMBER_BP") +" BP");
			}
		} catch (SQLException e) {
			System.out.println(" Exception : " + e.getMessage());
		} 
	}
	

	
public void sell(String MEMBER_ID, String PLAYER_NAME) {
        try {
        	 	conn = getConn();
 	        	String sql1 = "SELECT PLAYER_NAME FROM FIFAMYMEMBER WHERE PLAYER_NAME = ?";
 	        	ps = conn.prepareStatement(sql1);
 	        	ps.setString(1, PLAYER_NAME);
 	        	rs = ps.executeQuery();
				
	          if(rs.next()) {
		           	
	        	  String query1 = "SELECT * FROM KOREA WHERE PLAYER_NAME = ?";
	        	  	ps = conn.prepareStatement(query1);
					ps.setString(1, PLAYER_NAME);
					rs = ps.executeQuery();
					
					if(rs.next()) {
						  int price = rs.getInt("PRICE");
		        		  String updateQuery = "UPDATE FIFAMEMBER SET MEMBER_BP = MEMBER_BP + ? WHERE MEMBER_ID = ?";
			               ps = conn.prepareStatement(updateQuery);
			               ps.setInt(1, price);
			               ps.setString(2, MEMBER_ID);
			               ps.executeUpdate();		            	   
			               
			               System.out.println(price + "원이 추가되었습니다.");
	            
			               String query2 = "DELETE  FROM FIFAMYMEMBER WHERE MEMBER_ID= ? ";
			               ps = conn.prepareStatement(query2);
			               ps.setString(1, MEMBER_ID);
			               ps.executeQuery();
			               System.out.println(PLAYER_NAME + "을 방출하였습니다.");
					}
	          }else {
	        	  System.out.println("선수가 존재하지 않습니다.");
	          }
        } catch (SQLException e) {
            // 예외 처리
        } finally {
        	dbClose();
        }
	    }

	public void purchase(String MEMBER_ID, String PLAYER_NAME) {

	    String sql2 = "UPDATE FIFAMEMBER SET MEMBER_BP = MEMBER_BP - ? WHERE MEMBER_ID = ?";
	    String sql3 = "INSERT INTO FIFAMYMEMBER (MEMBER_ID, PLAYER_NAME) VALUES (?, ?)";

	    try {
	        conn = getConn();
	        
	        String sql1 = "SELECT PLAYER_NAME FROM FIFAMYMEMBER WHERE PLAYER_NAME = ?";
	        ps = conn.prepareStatement(sql1);
	        ps.setString(1, PLAYER_NAME);
	        rs = ps.executeQuery();
	        
	        if (!rs.next()) {
	            String getPriceQuery = "SELECT PRICE FROM KOREA WHERE PLAYER_NAME = ?";
	            ps = conn.prepareStatement(getPriceQuery);
	            ps.setString(1, PLAYER_NAME);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                int price = rs.getInt("PRICE");

	                ps = conn.prepareStatement(sql2);
	                ps.setInt(1, price);
	                ps.setString(2, MEMBER_ID);
	                ps.executeUpdate();
	                System.out.println(price + "원이 차감되었습니다.");

	                ps = conn.prepareStatement(sql3);
	                ps.setString(1, MEMBER_ID);
	                ps.setString(2, PLAYER_NAME);
	                ps.executeUpdate();
	                System.out.println(PLAYER_NAME + "을 영입하였습니다.");
	            }
        }else{
        	System.out.println("중복되는 선수가 있어 영입이 불가합니다.");
        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        dbClose();
	    }
	}

	public void chargeBP(int MEMBER_BP, String MEMBER_ID) {
		conn=getConn();
		String sql = "UPDATE FIFAMEMBER SET MEMBER_BP = MEMBER_BP + ? WHERE MEMBER_ID = ? ";
         try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, MEMBER_BP);
			ps.setString(2, MEMBER_ID);
			ps.executeUpdate();	
			System.out.println(MEMBER_BP + " : 원이 충전되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}

	
	
	



	

