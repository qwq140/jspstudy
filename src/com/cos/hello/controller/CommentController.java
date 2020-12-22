package com.cos.hello.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/comment") // xml의 필터를 만드는 마법의 문장
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CommentController() {
        super();
       
    }

    // http1.0 프로토콜 = SELECT(get), DELETE,UPDATE,INSERT(post)
    // http1.1 프로토콜 = SELECT(get), DELETE(delete), UPDATE(put), INSERT(post)
    
    // get요청은 브라우저에 주소적고 엔터!! = SELECT
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("comment get 요청");
		doProcess(request, response);
	}

	// post요청은 FORM태그 만들고 요청 = INSERT, DELETE, UPDATE
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("comment post 요청");
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("comment process 요청");
	}

}
