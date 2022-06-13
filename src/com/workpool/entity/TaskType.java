package com.workpool.entity;

public class TaskType {

	private Long id;
	private String name;
	
	public TaskType() {
		
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

	@Override
	public String toString() {
		return "TaskType [id=" + id + ", name=" + name + "\n" +"]";
	}
	
}
