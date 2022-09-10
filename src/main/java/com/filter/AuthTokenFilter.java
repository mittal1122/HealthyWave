package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bean.UserBean;
import com.repository.UserRepository;
@Order(value = 2)
@Component
public class AuthTokenFilter implements Filter{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = ((HttpServletRequest)(request));
		String url = req.getRequestURL().toString();
		
		System.out.println("incoming url..."+url);
		System.out.println("method ---> " + req.getMethod());
		if(url.contains("/public/") || req.getMethod().toLowerCase().equals("options")) {
			System.out.println("options by pass....");
			chain.doFilter(request, response);
		}else {
			//token - authentication
			String authToken = req.getHeader("authToken");
			System.out.println("authToken => " + authToken);
			HttpServletResponse resp = ((HttpServletResponse)response);
			if(authToken == null || authToken.trim().length() !=16) {
				
				System.out.println("token verification failed.......");
				resp.setContentType("application/json");
				resp.setStatus(401);
				resp.getWriter().write("{'msg':'please Login before access service'}");
			}else {
				UserBean user =  userRepo.findByAuthToken(authToken);
				System.out.println(user);
				System.out.println("url.contains(/doctor/).."+url.contains("/doctor/"));
				if(user == null) {
					
					resp.setContentType("application/json");
					resp.setStatus(401);
					resp.getWriter().write("{'msg':'user was not match'}");
				}
				else if(authToken == user.getAuthToken() || url.contains("/doctor/") || url.contains("/staff/")|| url.contains("/admin/")) {
					System.out.println("user.getAuthToken() : "+user.getAuthToken());
					System.out.println("user verfied....");
					chain.doFilter(request, response);
				}
				
			}
		}
		
		
	}

}
