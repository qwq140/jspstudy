package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.service.UsersService;


// 디스패쳐의 역할 = 분기 = 필요한 View를 응답해주는 것
public class UserController extends HttpServlet{
	
	// req와 res는 톰켓이 만들어줍니다. (사용자의 요청이 있을때 마다)
	// req는 BufferedReader 할 수 있는 ByteStream
	// res는 BufferedWriter 할 수 있는 ByteStream
	
	// http://localhost:8000/hello/user?gubun=login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("user get 요청");
		doProcess(req, resp);
		
//		System.out.println("FrontController 실행됨");
		
//		String gubun = req.getRequestURI(); // /hello/front
//		String gubun = req.getParameter("gubun");
//		System.out.println(gubun);
//		
//		if(gubun.equals("login")) {
//			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
//		} else if(gubun.equals("join")) {
//			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
//		} else if(gubun.equals("selectOne")) {
//			resp.sendRedirect("auth/selectOne.jsp"); // 한번 더 request
//		}else if(gubun.equals("updateOne")) {
//			resp.sendRedirect("auth/updateOne.jsp"); // 한번 더 request
//		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("user post 요청");
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("utf-8");
		// req.getParameter 함수 실행시에 파싱하기 때문에
		// 파싱전에 인코딩 해줘야 함.
		System.out.println("user process 요청");
		String gubun = req.getParameter("gubun"); // 쿼리스트링
		System.out.println(gubun);
		route(gubun, req, resp);
		
	}
	
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		
		UsersService usersService = new UsersService();
		
		if(gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
		} else if(gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
		} else if(gubun.equals("selectOne")) { // 유저정보 보기
			usersService.유저정보보기(req,resp);
		}else if(gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req,resp);
		} else if (gubun.equals("joinProc")) { //회원가입 수행해줘
			usersService.회원가입(req, resp);
		} else if (gubun.equals("loginProc")) {
			usersService.로그인(req, resp);
//			// SELECT id, username, email FROM users WHERE username = ? AND password = ?
//			// DAO의 함수명 : login() return을 Users 오브젝트를 리턴
//			// 정상 : 세션에 Users 오브젝트를 담고 index.jsp
//			// 비정상 : login.jsp
		} else if (gubun.equals("updateProc")) {
			usersService.유저정보수정(req, resp);
		} else if (gubun.equals("deleteProc")) {
			usersService.회원탈퇴(req,resp);
		}
	}	
}
