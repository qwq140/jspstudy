package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cos.hello.model.Users;

public class JoinFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String gubun = req.getParameter("gubun");
		
		if(gubun.equals("joinProc")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			
			Users userRequest = Users.builder()
					.username(username)
					.password(password)
					.email(email)
					.build();
			request.setAttribute("user", userRequest);
		}
	}

}
