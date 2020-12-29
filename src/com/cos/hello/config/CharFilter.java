package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 걸림!!!");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		chain.doFilter(request, response); // 다음 필터가 없으면 index.jsp로 감
	}

}
