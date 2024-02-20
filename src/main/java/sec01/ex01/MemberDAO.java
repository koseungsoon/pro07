package sec01.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	// 필
//	private static final String driver= "oracle.jdbc.OracleDriver";
//	private static final String url="jdbc:oracle:thin:@localhost:1521:orcl";
//	private static final String user= "scott";
//	private static final String pwd ="12341234";

	private Connection conn;

//	private Statement stmt; //정적 sql문을 실행하고 생성된 결과를 반환하는데 사용되는 개체입니다.
	// Statement 인터페이스가 DMBS에 전달하는 SQL문은 단순한 문자열이므로 DMBS는 이 문자열을 DBMS가 이해할 수 있도록
	// 컴파일하고 실행함
	// 반면에 PrepardStatement 인터페이스는 컴파일된 SQL문을 DMBS 전달하여 성능을 향상시킴
	// 또한 실행하려면 SQL문에 '?'를 넣을 수 있습니다. Statement 보다 SQL문 작성하기가 더 간단하다
	// 생

	private PreparedStatement pstmt;

	private DataSource dataFactory; // 연결을 위한 팩토리입니다. DriverManager 기능의 대안
									// JNDI API 기반으로 하는 이름 지정 서비스에 등록됩니다.

	public MemberDAO() {
		System.out.println("MemberDAO 기본생성자");
		try {
			// 1.연결을 하기 위한 컨텍스트(pro07)인식을 위한 Context 객체
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookupLink("jdbc/oracle");

		}catch(Exception e) {
			System.out.println("연결객체생성시 에러");
		}
		
		
		
	}

	// 메

	// 회원 추가하라는 메소드

	void addMember(MemberVO memberVO) {
		try {
			conn = dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();

			String query = "insert into t_member(id,pwd,name,email) VALUES(?,?,?,?)";
			System.out.println("preparedStatement: " + query);

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);

			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			System.out.println("회원추가시 에러");
		}
	}

	public List<MemberVO> listMembers() {

		List<MemberVO> list = new ArrayList<MemberVO>();

		try {
//			connDB();

			
			conn = dataFactory.getConnection();

			// 4. SQL 작성

			String query = "select * from T_MEMBER";
			System.out.println("실행한 sql: " + query);
			// 3. 연결객체가(conn) sql을 돌려야함, sql을 돌리기위해서는 sql관련문구를 처리하는 PreparedStatement 인터페이스
			// 사용
//			ResultSet rs=stmt.executeQuery(query); //ResultSet : DB에서 가져온 데이터를 읽음
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

//			rs.next();
			while (rs.next()) {
				// ResultSet의 칼럼 인식 후 결과값 가져오기
				String id = rs.getString("id");
				System.out.println("나온아이디" + id);
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");

				// Member VO 객체를 만들어서 그 객체에 ResultSet의 결과를 set해야함.
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);

				list.add(vo);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("연결시 에러");
		}

		return list;
		//

	}

	// DB 연결
//	void connDB(){
//		try {
//			//1. 드라이버 로딩
//			Class.forName(driver);
//			
//			System.out.println("오라클 드라이버 로딩성공");
//			
//			//2. 드라이버와 DB 연결을 위한 DriverManager 클래스 필요
//			
//			conn=DriverManager.getConnection(url, user, pwd);
//			
//			System.out.println("커넥션 생성 성공");
//			
////			stmt=conn.createStatement();
//			
//							
//		} catch (Exception e) {
//			System.out.println("DB연동 에러");
//			System.out.println("에러원인: "+e.getCause().toString());
//		}
//	}

}
