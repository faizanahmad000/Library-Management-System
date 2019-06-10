package com.lms.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
	private String excludedUrl = "/login,/logout,/registration";
	private List<String> excludedUrls;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		excludedUrls = Arrays.asList(excludedUrl.split(","));
		if (excludedUrls.contains(req.getServletPath())) {
			chain.doFilter(request, response);
		} else {
			ArrayList<String> sessionVar;
			sessionVar = (ArrayList<String>) req.getSession().getAttribute("lUser");
			if (sessionVar == null) {
				res.sendError(400, "You need to login First");
			} else {
				handlePreviliges(request, response, chain);
			}
		}

	}

	private void handlePreviliges(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String userPrev = "/updateMember,/books,/transaction/personid/*";
		List<String> userPrevs;
		userPrevs = Arrays.asList(userPrev.split(","));
		for (String string : userPrevs) {
			System.out.println(string);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) req.getSession().getAttribute("lUser");
		String role = sessionVar.get(1);
		System.out.println(req.getServletPath());
		if (role.equalsIgnoreCase("member")) {
			if (userPrevs.contains(req.getServletPath())) {
				chain.doFilter(request, response);
			} else if (req.getServletPath().contains("transaction/personid")) {
				chain.doFilter(request, response);
			} else {
				res.sendError(403, "You dont have previliges for this request");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}
