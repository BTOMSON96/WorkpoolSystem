package com.workpool.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.workpool.controller.ResourceController;
import com.workpool.entity.Resource;

@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ResourceController controller = new ResourceController();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String action = request.getParameter("action");
			switch (action) {

			case "login":
				login(request, response);
				return;

			case "signout":
				signout(request, response);
				return;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<Resource> resourceList = controller.getByUsernameAndPassword(username.trim(), password.trim());

		if (!resourceList.isEmpty()) {

			HttpSession session = request.getSession();
			session.setAttribute("LoggedUser", resourceList.iterator().next().getId());
			session.setAttribute("dateFormat", "DD-MM-YYYY" ); //default date format

			// session.setMaxInactiveInterval(5*60);
			System.out.println("Logged in user: " + resourceList.iterator().next().getId());
			locateJsp("home.jsp", request, response);
		} else {
			request.setAttribute("error", "Invalid Credentials, Please try again");
			locateJsp("login.jsp", request, response);
		}
	}

	protected void signout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
