package com.workpool.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.workpool.controller.GroupController;
import com.workpool.entity.Group;
import com.workpool.entity.Resource;
import com.workpool.util.HibernateUtil;
import com.workpool.controller.ResourceController;

@WebServlet("/GroupsServlet")
public class GroupsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	GroupController controller = new GroupController();
	ResourceController resourceController = new ResourceController();
	ArrayList<String> errorMessageList = new ArrayList<>();
	Group group = new Group();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "read":
				listGroups(request, response);
				return;

			case "create":
				addGroup(request, response);
				return;

			case "delete":
				deleteGroup(request, response);
				return;

			case "update":
				updateGroup(request, response);
				return;
			case "viewGroups":
				assignTaskToGroupOption(request, response);
				return;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void listGroups(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Group> groupList = controller.getAllGroup();

		request.setAttribute("groups", groupList);

		locateJsp("Groups.jsp", request, response);
		return;

	}
	protected void assignTaskToGroupOption(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Group> groupList = controller.getAllGroup();

		request.setAttribute("viewGroups", groupList);

		locateJsp("AddTask.jsp", request, response);
		return;

	}

	protected void addGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		Long manager = Long.parseLong(request.getParameter("managerId"));
		Long qa = Long.parseLong(request.getParameter("qaId"));
		String[] members = request.getParameterValues("membersId");

		group.setName(name);
		Resource managerId = resourceController.getResourceById(manager);
		Resource assurerId = resourceController.getResourceById(qa);
		group.setManager(managerId);
		group.setQualityAssurer(assurerId);

		// Add Members
		Set<Resource> resources = new HashSet<Resource>();

		for (int i = 0; i < members.length; i++) {

			resources.add(resourceController.getResourceById(Long.parseLong(members[i])));
			group.setResources(resources);
		}

		errorMessageList = controller.validateGroup(group);
		if (errorMessageList.size() > 0) {
			request.setAttribute("errorList", errorMessageList);
			locateJsp("CreateGroup.jsp", request, response);
			return;
		} else {
			controller.createGroup(group);
			listGroups(request, response);
		}
	}

	protected void updateGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int groupToUpdate = Integer.parseInt(request.getParameter("id"));
		Long manager = Long.parseLong(request.getParameter("manager"));
		Long qa = Long.parseLong(request.getParameter("qa"));
		String[] members = request.getParameterValues("members");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Group update = (Group) com.workpool.controller.AbstractController.getObjectById(Group.class, groupToUpdate,
				session);
		Resource managerId = resourceController.getResourceById(manager);
		Resource assurerId = resourceController.getResourceById(qa);

		// update records
		Group copy = new Group();
		if (valueTheSame(update.getName().trim(), request.getParameter("name").trim()) == false) {
			copy.setName(request.getParameter("name"));
			update.setName(request.getParameter("name"));
			errorMessageList = controller.validateGroup(copy);
		}
	
		update.setManager(managerId);
		update.setQualityAssurer(assurerId);

		// Add Members if applicable
		if (members != null) {
			Set<Resource> resources = new HashSet<Resource>();
			for (int i = 0; i < members.length; i++) {

				resources.add(resourceController.getResourceById(Long.parseLong(members[i])));
				update.setResources(resources);
			}
		}
	
		if(errorMessageList.size() > 0) {
		request.setAttribute("errorList", errorMessageList);
		 locateJsp("CreateGroup.jsp", request, response);
		 return;
		 }
		 else {	
			 controller.updateGroup(update, session);
			 listGroups(request, response);
		 }
		
		
	}

	protected void deleteGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Long groupToDelete = Long.parseLong(request.getParameter("id"));
		controller.deleteGroup(groupToDelete);
		listGroups(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
