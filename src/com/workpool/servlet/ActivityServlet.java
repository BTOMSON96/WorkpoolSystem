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

import com.workpool.controller.ActivityController;
import com.workpool.controller.EntryFolderController;
import com.workpool.controller.ResourceController;
import com.workpool.controller.TaskController;
import com.workpool.controller.TaskTypeController;
import com.workpool.entity.Activity;
import com.workpool.entity.EntryFolder;
import com.workpool.entity.Resource;
import com.workpool.entity.Task;
import com.workpool.entity.TaskType;
import com.workpool.util.HibernateUtil;

@WebServlet("/ActivityServlet")
public class ActivityServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ActivityController controller = new ActivityController();
	Activity activity = new Activity();
	ResourceController resourceController = new ResourceController();
	TaskTypeController typeController = new TaskTypeController();
	TaskController taskController = new TaskController();
	EntryFolderController entryController = new EntryFolderController();
	ArrayList<String> errorMessageList = new ArrayList<>();

	public ActivityServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "create":
				addActivity(request, response);
				return;

			case "read":
				readActivity(request, response);
				return;
			case "delete":
				deleteActivity(request, response);
				return;
			case "update":
				updateActivity(request, response);
				return;
			case "close":
				closeActivity(request, response);
				return;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long entry = Long.parseLong(request.getParameter("entry"));
		Long taskNo = Long.parseLong(request.getParameter("taskno"));
		Long type = Long.parseLong(request.getParameter("type"));
		String started = request.getParameter("started");
		int timeTaken = Integer.parseInt(request.getParameter("taken"));

		activity.setTitle(title);
		activity.setDescription(description);
		EntryFolder entryId = entryController.getEntryById(entry);
		activity.setEntry_id(entryId);
		Task taskId = taskController.getTaskById(taskNo);
		activity.setTask_id(taskId);

		TaskType type_id = typeController.getTaskTypeById(type);
		activity.setType_id(type_id);

		activity.setStart(dateFormat(started));
		Calendar end = Calendar.getInstance();
		end.add(Calendar.MINUTE, timeTaken);
		activity.setEnd(end);

		// get author from the session
		HttpSession session = request.getSession();
		Long id = (Long) (session.getAttribute("LoggedUser"));
		Resource author = resourceController.getResourceById(id);
		activity.setAuthor(author);

		// close activity if checkbox is checked
		if (request.getParameter("close") != null) {
			activity.setisOpen(false);
		} else {
			activity.setisOpen(true);
		}
		errorMessageList = controller.validateActivity(activity);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("AddActivity.jsp", request, response);
			return;
		} else {
			// Create activity
			controller.createActivity(activity);
			readActivity(request, response);
		}

	}

	private void readActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Activity> activityList = controller.getAllActivities();

		request.setAttribute("activities", activityList);
		locateJsp("Activity.jsp", request, response);
		return;

	}

	private void updateActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int activityToUpdate = Integer.parseInt(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Activity update = (Activity) com.workpool.controller.AbstractController.getObjectById(Activity.class,
				activityToUpdate, session);

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long type = Long.parseLong(request.getParameter("type"));

		update.setTitle(title);
		update.setDescription(description);
		TaskType type_id = typeController.getTaskTypeById(type);
		update.setType_id(type_id);

		controller.updateActivity(update, session);
		readActivity(request, response);

	}
  
	//using close activity method instead of deleting
	private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long activityToDelete = Long.parseLong(request.getParameter("id"));
		controller.deleteActivity(activityToDelete);
		readActivity(request, response);

	}

	private void closeActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long activityToClose = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Activity update = (Activity) com.workpool.controller.AbstractController.getObjectById(Activity.class,
				activityToClose, session);
		update.setisOpen(false);
		controller.updateActivity(update, session);
		readActivity(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
