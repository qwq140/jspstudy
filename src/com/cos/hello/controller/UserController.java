package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.model.Users;

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
		System.out.println("user process 요청");
		String gubun = req.getParameter("gubun"); // 쿼리스트링
		System.out.println(gubun);
		route(gubun, req, resp);
		
	}
	
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if(gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // 한번 더 request
		} else if(gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // 한번 더 request
		} else if(gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			String result;
			HttpSession session = req.getSession();
			if(session.getAttribute("sessionUser")!=null) { // 인증끝
				Users user = (Users)session.getAttribute("sessionUser"); // 다운캐스팅
				result = "인증되었습니다.";
				System.out.println(user);
			} else {
				result = "인증되지않았습니다.";
			}
			req.setAttribute("result", result);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp"); // request 유지
			dis.forward(req, resp);
		}else if(gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp"); // 한번 더 request
		} else if (gubun.equals("joinProc")) { //회원가입 수행해줘
			// 데이터 원형     username=ssar&password=1234&email=ssar@nate.com
			// 1번 form의 input태그에 있는 3가지 값 username, password, email 받기
			
			// getParameter함수는 get방식의 데이터와 post방식의 데이터를 받을 수 있다.
			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			
			System.out.println("=============joinProc Start=============");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("=============joinProc End=============");
			// 2번 DB에 연결해서 3가지 값을 INSERT 하기
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("index.jsp");
		} else if (gubun.equals("loginProc")) {
			// 1번 전달되는 값 받기
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			System.out.println("=============loginProc Start=============");
			System.out.println(username);
			System.out.println(password);
			System.out.println("=============loginProc End=============");
			// 2번 데이터베이스 값이 있는지 select 해서 확인
			// 생략
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
			// 3번
			HttpSession session = req.getSession();
			// session에는 사용자 패스워드 절대 넣지 않기
			session.setAttribute("sessionUser", user);
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.
			
			// 4번 index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");
		}
	}	
}
