package com.workpool.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({ "*.jsp" })

public class SecurityFilter implements Filter {

	/// private String pathToBeIgnored;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Ignore the login page in web filter
		String path = ((HttpServletRequest) request).getRequestURI();
		if (path.contains("/login.jsp")) {
			chain.doFilter(request, response);
		} else {

			// Continue with testing
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = req.getSession();

			if (session.getAttribute("LoggedUser") == null) {
				res.sendRedirect("login.jsp");
			} else {
				chain.doFilter(request, response);

			}

		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// pathToBeIgnored = fConfig.getInitParameter("/login.jsp");

	}

}
