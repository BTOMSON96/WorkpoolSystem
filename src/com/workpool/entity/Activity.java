package com.workpool.entity;

import java.util.Calendar;

public class Activity {

	private Long id;
	private String title;
	private String description;
	private Calendar start;
	private Calendar end;
	private Resource author;
	private TaskType type_id;
	private Task task_id;
	private EntryFolder entry_id;
	private boolean isOpen;
	
	
	public Activity() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	public Calendar getEnd() {
		return end;
	}
	public void setEnd(Calendar end) {
		this.end = end;
	}
	public Resource getAuthor() {
		return author;
	}
	public void setAuthor(Resource author) {
		this.author = author;
	}
	public TaskType getType_id() {
		return type_id;
	}
	public void setType_id(TaskType type_id) {
		this.type_id = type_id;
	}
	public Task getTask_id() {
		return task_id;
	}
	public void setTask_id(Task task_id) {
		this.task_id = task_id;
	}
	public EntryFolder getEntry_id() {
		return entry_id;
	}
	public void setEntry_id(EntryFolder entry_id) {
		this.entry_id = entry_id;
	}
	public boolean getisOpen() {
		return isOpen;
	}
	public void setisOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", title=" + title + ", description=" + description + ", start=" + start.getTime()
				+ ", end=" + end.getTime() + ", author=" + author.getFirstname() + ", type_id=" + type_id.getName() + ", task_id=" + task_id.getTitle()
				+ ", entry_id=" + entry_id.getName() +"\n"+ "]";
	}
	
	
	
	
}
