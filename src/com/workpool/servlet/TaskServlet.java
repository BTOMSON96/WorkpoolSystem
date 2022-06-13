package com.workpool.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.workpool.controller.TaskController;
import com.workpool.entity.Activity;
import com.workpool.entity.EntryFolder;
import com.workpool.entity.Resource;
import com.workpool.entity.Participant;
import com.workpool.entity.Task;
import com.workpool.entity.TaskType;
import com.workpool.enums.Priority;
import com.workpool.enums.Status;
import com.workpool.util.HibernateUtil;
import com.workpool.controller.ActivityController;
import com.workpool.controller.EntryFolderController;
import com.workpool.controller.GroupController;
import com.workpool.controller.ResourceController;
import com.workpool.controller.TaskTypeController;

@WebServlet("/TaskServlet")
public class TaskServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	TaskController controller = new TaskController();
	ResourceController resourceController = new ResourceController();
	TaskTypeController typeController = new TaskTypeController();
	EntryFolderController entryController = new EntryFolderController();
	ArrayList<String> errorMessageList = new ArrayList<>();
	Task task = new Task();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "read":
				taskAssigned(request, response);
				return;

			case "getbyid":
				taskById(request, response);
				return;

			case "create":
				addTask(request, response);
				return;

			case "details":
				taskInfor(request, response);
				return;
			case "delete":
				deleteTask(request, response);
				return;

			case "update":
				updateTask(request, response);
				return;
			case "owner":
				tasksOwned(request, response);
				return;
			case "all":
				listTasks(request, response);
				return;
			case "updateTaskOwner":
				 updateTaskOwner(request, response);
				return;
			case "complete":
				markTaskAsComplete(request, response);
				return;
				

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void listTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Task> taskList = controller.getAllTasks();

		request.setAttribute("tasks", taskList);
		locateJsp("Task.jsp", request, response);
		return;

	}

	private void tasksOwned(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("LoggedUser");
		Resource owner_id = resourceController.getResourceById(id);
		task.setOwner_id(owner_id);

		List<Task> taskList = controller.taskCreatedBy(task);

		if (!taskList.isEmpty()) {
			request.setAttribute("tasks", taskList);
			locateJsp("Task.jsp", request, response);
			return;
		} else {

			request.setAttribute("error", " NO TASKS FOUND,, YOU DON'T OWN ANY TASK!");
			locateJsp("Task.jsp", request, response);
			return;
		}

	}

	private void taskAssigned(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("LoggedUser");
		Resource assigned_to_id = resourceController.getResourceById(id);
		task.setAssigned_to_id(assigned_to_id);

		List<Task> taskList = controller.taskAssignedTo(task);

		request.setAttribute("tasks", taskList);
		locateJsp("Task.jsp", request, response);
		return;

	}

	private void taskById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("searchTask"));

		Session session = HibernateUtil.getSessionFactory().openSession();
		Task taskById = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, id, session);

		request.setAttribute("taskById", taskById);
		locateJsp("Task.jsp", request, response);
		return;
	}

	private void addTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long entry = Long.parseLong(request.getParameter("entry"));
		Long owner = Long.parseLong(request.getParameter("owner"));
		Long type = Long.parseLong(request.getParameter("type"));
		String priority = request.getParameter("priority");
		String status = request.getParameter("status");
		// String created = request.getParameter("created");
		String due = request.getParameter("due");
		String next = request.getParameter("next");
        GroupController group = new GroupController();
          
		task.setTitle(title);
		task.setDescription(description);
		EntryFolder entryId = entryController.getEntryById(entry);
		task.setEntry_id(entryId);
		
		
		// assign to group
		if (request.getParameter("assign") == null) {
			Long assignToGroup = Long.parseLong(request.getParameter("assignToGroup"));

			Participant assignGr = group.getGroupById(assignToGroup);
			task.setAssigned_to_id(assignGr);
			System.out.println("Task assigned to group: " + assignGr.getName());
		} else {
			// assign to resource
			Long assignTo = Long.parseLong(request.getParameter("assign"));
			Participant assign = resourceController.getResourceById(assignTo);
			task.setAssigned_to_id(assign);
			System.out.println("Task assigned to resource: " + assign.getName());
		}
		
		Resource ownerid = resourceController.getResourceById(owner);
		task.setOwner_id(ownerid);
		TaskType task_type_id = typeController.getTaskTypeById(type);
		task.setTask_type_id(task_type_id);

		task.setPriority(Priority.valueOf(priority));
		task.setStatus(Status.valueOf(status));

		Calendar dateCreated = Calendar.getInstance();
		task.setDate_created(dateCreated);

		// check if date is valid before formatting
		if (isValid(due) == false || isValid(next) == false) {
			errorMessageList.add("Not a valid Date");
			request.setAttribute("errorList", errorMessageList);
			locateJsp("AddTask.jsp", request, response);
			return;
		} else {
			task.setDate_due(dateFormat(due));
			task.setDate_next(dateFormat(next));
		}

		errorMessageList = controller.validateTask(task);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("AddTask.jsp", request, response);
			return;
		} else {
			// Create task
			controller.createTask(task);
			listTasks(request, response);
		}
	}

	private void taskInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// get all the activities under a task\
		Activity activity = new Activity();
		ActivityController activityController = new ActivityController();
		TaskController taskController = new TaskController();
		Long taskId = Long.parseLong(request.getParameter("id"));
		
		
		Task task = taskController.getTaskById(taskId);
		activity.setTask_id(task);
		List<Activity> activityList = activityController.activitiesUnderTask(activity);

		
		// send results to Info JSP
		request.setAttribute("activityUnderTask", activityList);
		//get task details
		request.setAttribute("taskDetails", task);
		locateJsp("TaskInformation.jsp", request, response);
		return;
		
		



	}

	private void updateTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int taskToUpdate = Integer.parseInt(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Task update = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, taskToUpdate,
				session);

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long type = Long.parseLong(request.getParameter("type"));
		String priority = request.getParameter("priority");
		String status = request.getParameter("status");
		// String created = request.getParameter("created");
		String due = request.getParameter("due");
		String next = request.getParameter("next");

		update.setTitle(title);
		update.setDescription(description);
		TaskType task_type_id = typeController.getTaskTypeById(type);
		update.setTask_type_id(task_type_id);

		update.setPriority(Priority.valueOf(priority));
		update.setStatus(Status.valueOf(status));
		// check if date is valid before formatting
		if (isValid(due) == false || isValid(next) == false) {
			errorMessageList.add("Not a valid Date");
			request.setAttribute("errorList", errorMessageList);
			locateJsp("AddTask.jsp", request, response);
			return;
		} else {
			update.setDate_due(dateFormat(due));
			update.setDate_next(dateFormat(next));
		}

		errorMessageList = controller.validateTask(update);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("AddTask.jsp", request, response);
			return;
		} else {
			controller.updateTask(update, session);
			listTasks(request, response);
		}

	}

	private void updateTaskOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long newOwner = Long.parseLong(request.getParameter("owner"));
		Resource newOwnerid = resourceController.getResourceById(newOwner);

		// get this resource by id and get tasks created by him
		Long owner = Long.parseLong(request.getParameter("tasksOwner"));
		Resource owner_id = resourceController.getResourceById(owner);
		task.setOwner_id(owner_id);
		List<Task> taskList = controller.taskCreatedBy(task);

		// Loop through the list of tasks and change owner id of all the tasks
		for (Task task : taskList) {

			Session session = HibernateUtil.getSessionFactory().openSession();
			Task update = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, task.getId(),
					session);

			update.setOwner_id(newOwnerid);
			controller.updateTask(update, session);
		}
		listTasks(request, response);
		return;
	}

	//use mark as complete method instead of this
	private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskToDelete = Long.parseLong(request.getParameter("id"));
		controller.deleteTask(taskToDelete);
		listTasks(request, response);
	}
	
	private void markTaskAsComplete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long taskToComplete = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Task update = (Task) com.workpool.controller.AbstractController.getObjectById(Task.class, taskToComplete,
				session);
		update.setStatus(Status.Completed);
		controller.updateTask(update, session);
		listTasks(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
