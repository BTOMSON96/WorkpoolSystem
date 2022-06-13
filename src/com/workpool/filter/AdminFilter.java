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

import org.hibernate.Session;

import com.workpool.entity.Resource;
import com.workpool.util.HibernateUtil;


@WebFilter(urlPatterns = { "/AdminFilter", "/GroupsServlet", "/Create.jsp", "/TypeServlet", "/CreateType.jsp",
		"/AddDirectory.jsp" }, servletNames = { "ResourceServlet" })
public class AdminFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// check if user isAdmin
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Long id = Long.parseLong(session.getAttribute("LoggedUser").toString());
		Session sess = HibernateUtil.getSessionFactory().openSession();
		Resource resource = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class, id,
				sess);
		if (resource.getisAdmin() == false) {
			res.sendRedirect("error.jsp");

		} else {
			chain.doFilter(request, response);

		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
