package com.workpool.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workpool.controller.ResourceController;
import com.workpool.controller.TaskController;
import com.workpool.entity.Resource;
import com.workpool.entity.Task;
import com.workpool.enums.ResourceType;
import com.workpool.util.HibernateUtil;

@WebServlet("/ResourceServlet")
public class ResourceServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<String> errorMessageList = new ArrayList<>();
	ResourceController controller = new ResourceController();
	Resource resource = new Resource();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "read":
				listResources(request, response);
				return;

			case "add":
				addResource(request, response);
				return;

			case "update":
				updateResource(request, response);
				return;

			case "delete":
				deleteResource(request, response);
				return;

			case "byname":
				searchByName(request, response);
				return;
			case "deactivate":
				deActivateResource(request, response);
				return;
			case "updateManager":
				updateManager(request, response);
				return;
			case "active":
				activeResources(request, response);
				return;
			case "inactive":
				inactiveResources(request, response);
				return;
			case "activate":
				activateResource(request, response);
				return;
			case "changeFormat":
				dateFormat(request, response);
				return;
			case "calc":
				calculatAjax(request, response);
				return;
			case "activeAjax":
				activeResourcesAjax(request, response);
				return;


			default:
				listResources(request, response);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	private void calculatAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		    int No = Integer.parseInt(request.getParameter("number"));
		    int No2 = Integer.parseInt(request.getParameter("number2"));
		    int ans = No + No2;
		    PrintWriter out = response.getWriter();
		    out.println(ans + " " );
		
	}

	private void activeResourcesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Resource> resourceList = controller.activeResources();
		HashMap<String, String> resource = new HashMap<String, String>();

		for (Resource entry : resourceList) {
			resource.put(entry.getId().toString(), entry.getFirstname());

		}
		// Convert list to json
		String json = gson.toJson(resource);
		PrintWriter out = response.getWriter();
	    out.println(json + " " );

	}
	

	private void listResources(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Resource> resourceList = controller.getAllResource();

		request.setAttribute("resources", resourceList);

		locateJsp("workpool.jsp", request, response);
		return;

	}

	private void activeResources(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Resource> resourceList = controller.activeResources();

		request.setAttribute("activeResources", resourceList);
        request.setAttribute("ActiveChecked", "checked"); //To keep the checkbox checked after jsp reloading
		locateJsp("workpool.jsp", request, response);
		return;
	}
	

	private void inactiveResources(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Resource> resourceList = controller.inactiveResources();

		request.setAttribute("inactiveResources", resourceList);
		 request.setAttribute("InActiveChecked", "checked"); //To keep the checkbox checked after jsp reloading
		locateJsp("workpool.jsp", request, response);
		return;
	}

	private void addResource(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// check if date is valid before formatting
		if (isValid(request.getParameter("dob")) == false) {
			errorMessageList.add("Not a valid Date of birth");
			request.setAttribute("errorList", errorMessageList);
			locateJsp("Create.jsp", request, response);
			return;
		} else {
			// format DOB
			resource.setDob(dateFormat(request.getParameter("dob")));
		}

		resource.setFirstname(request.getParameter("name"));
		resource.setLastname(request.getParameter("lastname"));
		resource.setAddress(request.getParameter("address"));
		resource.setEmail(request.getParameter("email"));
		resource.setUsername(request.getParameter("username"));
		resource.setPassword(request.getParameter("password"));
		resource.setType(ResourceType.valueOf(request.getParameter("type")));
		Resource managerId = controller.getResourceById(Long.parseLong(request.getParameter("manager")));
		resource.setManager(managerId);
		resource.setisActive(true); // active is true by default when adding resource
		
		// resource is a system admin if checkbox is checked
		if (request.getParameter("isAdmin") != null) {
			resource.setisAdmin(true);
		} else {
			resource.setisAdmin(false);
		}
		errorMessageList = controller.validateResource(resource);
		if (errorMessageList.size() > 0) {

			// send back values (Don't clear form)
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("lastname", request.getParameter("lastname"));
			request.setAttribute("dob", request.getParameter("dob"));
			request.setAttribute("address", request.getParameter("address"));
			request.setAttribute("email", request.getParameter("email"));
			request.setAttribute("username", request.getParameter("username"));
			request.setAttribute("password", request.getParameter("password"));
			request.setAttribute("type", request.getParameter("type"));
			request.setAttribute("manmager", request.getParameter("manager"));

			request.setAttribute("errorList", errorMessageList);
			locateJsp("Create.jsp", request, response);
			return;

		} else {
			// create resource
			controller.createResource(resource);
			listResources(request, response);
		}

	}
	private void dateFormat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String format = request.getParameter("dateFormat");
		
		HttpSession session = request.getSession();
		session.setAttribute("dateFormat", format);
         
		PrintWriter out = response.getWriter();
		out.println(format);
		
	}

	private void updateResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long resourceToUpdate = Long.parseLong(request.getParameter("id"));

		String name = request.getParameter("name");
		String lastname = request.getParameter("lastname");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Resource update = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class,
				resourceToUpdate, session);
		Resource copy = new Resource();
        
		//checking if there values is changed(valueTheSame = false)
		if (valueTheSame(update.getFirstname().trim(), name.trim()) == false) {
			copy.setFirstname(name);
			update.setFirstname(name);

			errorMessageList = controller.validateResource(copy);
		}
		if (valueTheSame(update.getLastname().trim(), lastname.trim()) == false) {
			copy.setLastname(lastname);
			update.setLastname(lastname);
			errorMessageList = controller.validateResource(copy);
		}

		if (valueTheSame(update.getAddress().trim(), address.trim()) == false) {
			copy.setAddress(address);
			update.setAddress(address);
			errorMessageList = controller.validateResource(copy);
		}
		if (valueTheSame(update.getEmail().trim(), email.trim()) == false) {
			copy.setEmail(email);
			update.setEmail(email);
			errorMessageList = controller.validateResource(copy);
		}
		if (valueTheSame(update.getUsername().trim(), username.trim()) == false) {
			copy.setUsername(username);
			update.setUsername(username);
			errorMessageList = controller.validateResource(copy);
		}

		if (valueTheSame(update.getPassword().trim(), password.trim()) == false) {
			copy.setPassword(password);
			update.setPassword(password);
			errorMessageList = controller.validateResource(copy);
		}

		if (errorMessageList.size() > 0) {

			request.setAttribute("errorList", errorMessageList);
			locateJsp("Create.jsp", request, response);
			return;

		} else {
			// update resource
			controller.updateResource(update, session);
			listResources(request, response);

		}

	}
    
	// Not using this method anymore,, implement deactivate option instead
	private void deleteResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Task task = new Task();
		TaskController taskController = new TaskController();
		Long resourceToDelete = Long.parseLong(request.getParameter("id"));
		Resource owner_id = controller.getResourceById(resourceToDelete);
		task.setOwner_id(owner_id);

		List<Task> taskList = taskController.taskCreatedBy(task);

		if (!taskList.isEmpty()) {

			request.setAttribute("FKConstraintError", "This resource is the owner of a task");
			locateJsp("Create.jsp", request, response);
			return;
		} else {
			controller.deleteResource(resourceToDelete);
			listResources(request, response);
		}
	}
	private void updateManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long oldManager = Long.parseLong(request.getParameter("manager"));
		Resource manager = controller.getResourceById(oldManager);
		resource.setManager(manager);
		List<Resource> resourceList = controller.resourcesManaged(resource);

		// change manager id to all the resources
		Long newManager = Long.parseLong(request.getParameter("newManager"));
		Resource newManagerId = controller.getResourceById(newManager);
		for (Resource res : resourceList) {

			Session session = HibernateUtil.getSessionFactory().openSession();
			Resource update = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class,
					res.getId(), session);

			update.setManager(newManagerId);
			controller.updateResource(update, session);
		}
		listResources(request, response);

	}

	private void deActivateResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long resourceToDeactivate = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Resource resource = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class,
				resourceToDeactivate, session);

		// check if resource owns a task before deactivating
		Task task = new Task();
		TaskController taskController = new TaskController();
		Resource owner_id = resource;
		task.setOwner_id(owner_id);
		List<Task> taskList = taskController.taskCreatedBy(task);

		// check if resource manages another resource
		Resource res = new Resource();
		res.setManager(resource);
		List<Resource> resourceList = controller.resourcesManaged(res);

		if (!taskList.isEmpty() && !resourceList.isEmpty()) {
			request.setAttribute("tasksOwned", taskList);
			request.setAttribute("resourcesManaged", resourceList);
			List<Resource> allResources = controller.getAllResource();//send all resources for transfer ownership purposes
			request.setAttribute("resources", allResources);
			locateJsp("Responsibilities.jsp", request, response);
			return;
		}
		if (!taskList.isEmpty()) {
			request.setAttribute("tasksOwned", taskList);
			List<Resource> allResources = controller.getAllResource();//send all resources for transfer ownership purposes
			request.setAttribute("resources", allResources);
			locateJsp("Responsibilities.jsp", request, response);
			return;
		}
		if (!resourceList.isEmpty()) {
			request.setAttribute("resourcesManaged", resourceList);
			List<Resource> allResources = controller.getAllResource();//send all resources for transfer ownership purposes
			request.setAttribute("resources", allResources);
			locateJsp("Responsibilities.jsp", request, response);
			return;
		}
		if (taskList.isEmpty() || resourceList.isEmpty()) {
			resource.setisActive(false);
			controller.updateResource(resource, session);
			listResources(request, response);
		}

		
	
	}

	private void activateResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long resourceToactivate = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		Resource resource = (Resource) com.workpool.controller.AbstractController.getObjectById(Resource.class,
				resourceToactivate, session);

		resource.setisActive(true);
		controller.updateResource(resource, session);
		listResources(request, response);
	}
	

	private void searchByName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("myInput");
		List<Resource> resourceList = controller.getResourcesByName(name);

		if (!resourceList.isEmpty()) {

			request.setAttribute("resources", resourceList);
			locateJsp("workpool.jsp", request, response);
			return;
		} else {
			request.setAttribute("errors", "No resource found");
			locateJsp("workpool.jsp", request, response);
			return;

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
