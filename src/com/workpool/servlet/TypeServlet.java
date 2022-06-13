package com.workpool.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.workpool.controller.TaskTypeController;
import com.workpool.entity.*;
import com.workpool.util.HibernateUtil;

@WebServlet("/TypeServlet")
public class TypeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	TaskTypeController controller = new TaskTypeController();
	ArrayList<String> errorMessageList = new ArrayList<>();
	TaskType type = new TaskType();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "read":
				listTypes(request, response);
				return;
			case "create":
				addTypes(request, response);
				return;
			case "update":
				updateTypes(request, response);
				return;
			case "delete":
				removeTypes(request, response);
				return;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void listTypes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<TaskType> typeList = controller.getAllTaskType();

		request.setAttribute("types", typeList);

		locateJsp("Types.jsp", request, response);
		return;
	}

	protected void addTypes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		type.setName(name);
		errorMessageList = controller.validateTaskType(type);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("CreateType.jsp", request, response);
			return;
		} else {
			controller.createTaskType(type);
			listTypes(request, response);
		}

	}

	protected void updateTypes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long typeToUpdate = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		TaskType update = (TaskType) com.workpool.controller.AbstractController.getObjectById(TaskType.class,
				typeToUpdate, session);
		// update records
		update.setName(request.getParameter("name"));

		errorMessageList = controller.validateTaskType(update);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("CreateType.jsp", request, response);
			return;
		} else {
			controller.updateTaskType(update, session);
			listTypes(request, response);
		}
	}

	protected void removeTypes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long typeToDelete = Long.parseLong(request.getParameter("id"));
		controller.deleteTaskType(typeToDelete);
		listTypes(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
