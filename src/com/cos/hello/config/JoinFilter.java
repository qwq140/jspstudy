package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cos.hello.Dto.JoinDto;
import com.cos.hello.model.Users;

public class JoinFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String gubun = req.getParameter("gubun");
		
		// ?username=&password=1234 --> username은 공백, email은 null
		if(gubun.equals("joinProc")) {
			if(req.getParameter("username") == null ||
					req.getParameter("password") == null ||
						req.getParameter("email") == null ||
						req.getParameter("username").equals("")||
						req.getParameter("password").equals("")||
						req.getParameter("email").equals("")
				) {
				chain.doFilter(request, response);
				return;
			}
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			
//			Users userRequest = Users.builder()
//					.username(username)
//					.password(password)
//					.email(email)
//					.build();
			JoinDto dto = new JoinDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			
			request.setAttribute("dto", dto);
		}
		chain.doFilter(request, response);
	}

}
