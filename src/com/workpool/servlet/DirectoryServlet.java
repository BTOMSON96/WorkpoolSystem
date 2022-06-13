package com.workpool.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.workpool.controller.EntryFolderController;

import com.workpool.entity.EntryFolder;

import com.workpool.util.HibernateUtil;

@WebServlet("/DirectoryServlet")
public class DirectoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	EntryFolderController controller = new EntryFolderController();
	EntryFolder entry = new EntryFolder();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = request.getParameter("action");

			switch (action) {

			case "directorybyname":
				directoryByName(request, response);
				return;
			case "add":
				addEntryFolder(request, response);
				return;
			case "read":
				getAllEntries(request, response);
				return;
			case "delete":
				deleteEntry(request, response);
				return;
			case "update":
				updateEntry(request, response);
				return;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void directoryByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String entry = request.getParameter("searchDirectory");
		List<EntryFolder> entryList = controller.getEntryFolderByName(entry);

		if (!entryList.isEmpty()) {

			request.setAttribute("entries", entryList);
			locateJsp("Directory.jsp", request, response);
			return;
		} else {
			request.setAttribute("errors", "No directory found");
			locateJsp("Directory.jsp", request, response);
			return;

		}

	}

	private void addEntryFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String entryName = request.getParameter("entry");
		Calendar date_created = Calendar.getInstance();
		Calendar date_modified = Calendar.getInstance();

		entry.setName(entryName);
		entry.setDate_created(date_created);
		entry.setDate_modified(date_modified);

		controller.createEntry(entry);

		locateJsp("Directory.jsp", request, response);
		return;
	}

	private void getAllEntries(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<EntryFolder> entryList = controller.getAllEntryFolders();

		request.setAttribute("entryList", entryList);

		locateJsp("Directory.jsp", request, response);
		return;

	}

	private void deleteEntry(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long entryToDelete = Long.parseLong(request.getParameter("id"));
		controller.deleteEntry(entryToDelete);
		getAllEntries(request, response);

	}

	private void updateEntry(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long entryToUpdate = Long.parseLong(request.getParameter("id"));
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntryFolder update = (EntryFolder) com.workpool.controller.AbstractController.getObjectById(EntryFolder.class,
				entryToUpdate, session);

		String entryName = request.getParameter("entry");
		update.setName(entryName);
		controller.updateEntry(update, session);
		getAllEntries(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
