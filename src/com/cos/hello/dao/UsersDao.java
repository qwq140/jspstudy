package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {

	public Users selectById(int id) {
		// 2번 데이터베이스 값이 있는지 select 해서 확인
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, username, password, email FROM users ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int insert(Users user) {
		// 2번 DB에 연결해서 3가지 값을 INSERT 하기
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션 (동기화), 긴 문장을 쓸때 사용하자
		sb.append("INSERT INTO users(username, password, email)");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴, DML(insert, update, delete)
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Users login(Users user) {
		// 2번 데이터베이스 값이 있는지 select 해서 확인
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, username, email FROM users ");
		sb.append("WHERE username = ? AND password = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(Users user) {
		String sql = "UPDATE users SET password = ?, email = ? WHERE username = ?";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getUsername());
			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴, DML(insert, update, delete)
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
