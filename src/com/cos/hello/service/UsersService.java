package com.cos.hello.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

public class UsersService { // 테이블명+서비스명

	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 username=ssar&password=1234&email=ssar@nate.com
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

		Users user = Users.builder().username(username).password(password).email(email).build();

		UsersDao usersDao = new UsersDao(); // 싱글톤패턴으로 만들기(UsersDao에서)
		int result = usersDao.insert(user);

		if (result == 1) {
			// 3번 INSERT가 정상적으로 되었다면 index.jsp를 응답!!
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}

	}

	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1번 전달되는 값 받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("=============loginProc Start=============");
		System.out.println(username);
		System.out.println(password);
		System.out.println("=============loginProc End=============");

		Users user = Users.builder().username(username).password(password).build();

		UsersDao usersDao = new UsersDao(); // 싱글톤패턴으로 만들기(UsersDao에서)
		Users userEntity = usersDao.login(user);

		if (userEntity != null) {
			// 3번
			HttpSession session = req.getSession();
			// session에는 사용자 패스워드 절대 넣지 않기
			session.setAttribute("sessionUser", userEntity);
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.

			// 4번 index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");
		} else {
			resp.sendRedirect("auth/login.jsp");
		}
	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user!=null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}
	}

	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if (user!=null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 1번 전달되는 값 받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		System.out.println("=============updateProc Start=============");
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		System.out.println("=============updateProc End=============");
		
		Users user = Users.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
		
		UsersDao usersDao = new UsersDao();
		int result = usersDao.update(user);
		
		if(result == 1) {
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("user/updateOne.jsp");
		}
		
	}
}
