package com.workpool.entity;

import java.util.HashSet;
import java.util.Set;

public class Group extends Participant{

	//private Long id;
	//private String name;
	private Resource manager;
	private Resource qualityAssurer;
	private Set<Resource> resources = new HashSet<Resource>();
	
	
	 
	 public Group() {
			
		}
		
	 
	 
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	public Long getId() {
		return super.getId();
	}
	public void setId(Long id) {
		super.setId(id);
	}
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	public Resource getManager() {
		return manager;
	}
	public void setManager(Resource manager) {
		this.manager = manager;
	}
	public Resource getQualityAssurer() {
		return qualityAssurer;
	}
	public void setQualityAssurer(Resource qualityAssurer) {
		this.qualityAssurer = qualityAssurer;
	}
	/*
	@Override
	public String toString() {
		return "Group [id= " + id + ", name= " + name + ", manager= " + manager+ ", qualityAssurer= " + qualityAssurer +"\n"
				+ "]";
	}
	*/
	
	
}
