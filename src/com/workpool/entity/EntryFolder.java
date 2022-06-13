package com.workpool.entity;

import java.util.Calendar;

public class EntryFolder {

	
	private Long id;
	private String name;
	private Calendar date_created;
	private Calendar date_modified;
	
	public EntryFolder() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate_created() {
		return date_created;
	}

	public void setDate_created(Calendar date_created) {
		this.date_created = date_created;
	}

	public Calendar getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(Calendar date_modified) {
		this.date_modified = date_modified;
	}

	
}
