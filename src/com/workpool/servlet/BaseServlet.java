package com.workpool.servlet;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.workpool.controller.GroupController;
import com.workpool.controller.ResourceController;
import com.workpool.entity.Group;
import com.workpool.entity.Resource;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BaseServlet() {
		super();

	}

	protected void locateJsp(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher(url).forward(request, response);
	}

	protected Calendar dateFormat(String date) throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date formatDate = dateformat.parse(date);
		calendar.setTimeInMillis(formatDate.getTime());
		return calendar;
	}

	public boolean isValid(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public static String ConvertToDate(Calendar calendar) {

		int year = calendar.get(calendar.YEAR);
		String month = "";
		String day = "";

		if (1 + calendar.get(calendar.MONTH) < 10) {
			month = "0" + String.valueOf(1 + calendar.get(calendar.MONTH));
		} else {
			month = Integer.toString(1 + calendar.get(calendar.MONTH));
		}
		if (calendar.get(calendar.DAY_OF_MONTH) < 10) {
			day = "0" + String.valueOf(calendar.get(calendar.DAY_OF_MONTH));
		} else {
			day = Integer.toString(calendar.get(calendar.DAY_OF_MONTH));
		}

		String date = Integer.toString(year) + '-' + month + '-' + day;
		return date;
	}
	
public static boolean valueTheSame(String notEdited, String edited) {
		
		if (notEdited == null && edited == null) {
			return true;//no changes
		}
		if (notEdited == null && edited != null) {
			return false;//there's a change,, do validations
		}
		if (notEdited != null && edited == null) {
			return false; //there's a change,,  do validations
		}

		return notEdited.equals(edited);
	}

	public static String resourceOrGroupObject(Long id) {
		ResourceController resourceController = new ResourceController();
		GroupController groupController = new GroupController();
		Resource resource = resourceController.getResourceById(id);
		Group group = groupController.getGroupById(id);

		if (resource == null) {
			return group.getName();
		} else {
			return resource.getFirstname() + " " + resource.getLastname();
		}

	}
}
