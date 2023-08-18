package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.member.model.dto.Member;

public class MainDAO {

	
	//필드
	// JDBC 객체 참조 변수
	private Statement stmt;//SQL 수행, 결과 반환
	private PreparedStatement pstmt; // placholder를 포함한 SQL 세팅/수행
	private ResultSet rs; // SELECT 수행 결과 저장
	
	private Properties prop;
	// -Map<String, String> 형태
	// -XML 파일 입/출력 메서드를 제공
	

	
	
	public MainDAO() {
		
		//DAO 객체가 생성될때 Xml 파일을 읽어와 Properties 객체에 저장
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
public Member login(Connection conn, String memberId, String memberPw) throws Exception {
	
	
	//1. 결과 저장용 변수 선언
	Member member = null;
	
	try {
		//2. SQL 작성 후 수행
		String sql = prop.getProperty("login");
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, memberId);
		pstmt.setString(2, memberPw);
		
		rs = pstmt.executeQuery(); // SELECT 수행 후 결과 반환 받기
		
		// 3. 조회 결과를 1행씩 접근해서 얻어오기
		if(rs.next()) {
			int memberNo = rs.getInt("MEMBER_NO");
			
			String memberName = rs.getString("MEMBER_NM");
			String memberGender = rs.getString("MEMBER_GENDER");
			String enrollDate = rs.getString("ENROLL_DT");
			
			
			//Member 객체 생성 후 값 세팅
			member = new Member();
			
			member.setMemberNo(memberNo);
			member.setMemberId(memberId);
			member.setMemberName(memberName);
			member.setMemberGender(memberGender);
			member.setEnrollDate(enrollDate);
			
			
			
			
		}
		
			
		} finally {
			
			close(rs);
			close(pstmt);
		}
		
		return member;
	}

	//기본생성자


}

