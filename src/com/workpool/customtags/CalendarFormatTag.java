package com.workpool.customtags;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CalendarFormatTag extends SimpleTagSupport {

	private Calendar calendar;
	private String format;

	

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@SuppressWarnings("static-access")
	@Override
	public void doTag() throws JspException, IOException {

		String date = null;

		try {
			

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

			if (format.trim().equalsIgnoreCase("YYYY-MM-DD")) {
				date = Integer.toString(year) + '-' + month + '-' + day;
				getJspContext().getOut().write(date);
			}
			if (format.trim().equalsIgnoreCase("YYYY/MM/DD")) {
				date = Integer.toString(year) + '/' + month + '/' + day;
				getJspContext().getOut().write(date);
			}
			if (format.trim().equalsIgnoreCase("DD/MM/YYYY")) {
				date = day + '/' + month + '/' + Integer.toString(year);
				getJspContext().getOut().write(date);
			}
			if (format.trim().equalsIgnoreCase("DD-MM-YYYY")) {
				date = day + '-' + month + '-' + Integer.toString(year);
				getJspContext().getOut().write(date);
			}
			if (format.trim().equalsIgnoreCase("MM-DD-YYYY")) {
				date = month + '-' + day +  '-' + Integer.toString(year);
				getJspContext().getOut().write(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// stop page from loading further by throwing SkipPageException
			throw new SkipPageException("Exception in formatting " + calendar);
		}
	}
}
