package com.workpool.entity;

import java.util.Calendar;

import com.workpool.enums.Priority;
import com.workpool.enums.Status;

public class Task {

	private Long id;
	private String title;
	private String description;
	private Calendar date_created;
	private Calendar date_completed;
	private Calendar date_due;
	private Calendar date_next;
	private Resource owner_id;
	private Participant assigned_to_id;
	private TaskType task_type_id;
	private EntryFolder entry_id;
	private Status status;     
    private Priority priority; 
	
	public Task() {
		
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



	public Calendar getDate_created() {
		return date_created;
	}



	public void setDate_created(Calendar date_created) {
		this.date_created = date_created;
	}



	public Calendar getDate_completed() {
		return date_completed;
	}



	public void setDate_completed(Calendar date_completed) {
		this.date_completed = date_completed;
	}



	public Calendar getDate_due() {
		return date_due;
	}



	public void setDate_due(Calendar date_due) {
		this.date_due = date_due;
	}



	public Calendar getDate_next() {
		return date_next;
	}



	public void setDate_next(Calendar date_next) {
		this.date_next = date_next;
	}



	public Resource getOwner_id() {
		return owner_id;
	}



	public void setOwner_id(Resource owner_id) {
		this.owner_id = owner_id;
	}



	public Participant getAssigned_to_id() {
		return assigned_to_id;
	}



	public void setAssigned_to_id(Participant assigned_to_id) {
		this.assigned_to_id = assigned_to_id;
	}



	public TaskType getTask_type_id() {
		return task_type_id;
	}



	public void setTask_type_id(TaskType task_type_id) {
		this.task_type_id = task_type_id;
	}



	public EntryFolder getEntry_id() {
		return entry_id;
	}



	public void setEntry_id(EntryFolder entry_id) {
		this.entry_id = entry_id;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public Priority getPriority() {
		return priority;
	}



	public void setPriority(Priority piority) {
		this.priority = piority;
	}


/*
	@Override
	public String toString() {
			if(date_completed == null) {
		
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", date_created=" + date_created.getTime()
				+ ", date_completed=" + date_completed + ", date_due=" + date_due.getTime() + ", date_next=" + date_next.getTime()
				+ ", owner_id=" + owner_id.getFirstname() + ", assigned_to_id=" + assigned_to_id.getFirstname() + ", task_type_id=" + task_type_id.getName()
				+ ", entry_id=" + entry_id.getName() +  ", Status ="  + status+   ", Priority = "  +priority+ "\n"+"]";
			}
			else {
				
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", date_created=" + date_created.getTime()
				+ ", date_completed=" + date_completed.getTime() + ", date_due=" + date_due.getTime() + ", date_next=" + date_next.getTime()
				+ ", owner_id=" + owner_id.getFirstname() + ", assigned_to_id=" + assigned_to_id.getFirstname() + ", task_type_id=" + task_type_id.getName()
				+ ", entry_id=" + entry_id.getName() + ", Status ="  + status+   ", Priority = "  +priority+ "\n"+"]";
			}
	}
	*/
}
