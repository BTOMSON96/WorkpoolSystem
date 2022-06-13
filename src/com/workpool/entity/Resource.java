package com.workpool.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.workpool.enums.ResourceType;

public class Resource extends Participant  {
	  //private Long id;    
	  //private String firstName;
	  private String lastName;
	  private Calendar dob ;
	  private String address;
	  private String email;        
      private String username;            
	  private String password;
	  private Resource manager;
	  private ResourceType type;
	  private boolean isActive;
	  private boolean isAdmin;
	  private Set<Group> groups = new HashSet<Group>();
	 
	   
	  public Resource() {
			 
		 }
	  

	public Set<Group> getGroups() {
		return groups;

	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	public Long getId() {
		return super.getId();
	}
	public void setId(Long id) {
		super.setId(id);
	}
	public String getFirstname() {
		return super.getName();
	}
	public void setFirstname(String firstname) {
		super.setName(firstname);
	}
	public String getLastname() {
		return lastName;
	}
	public void setLastname(String lastname) {
		this.lastName = lastname;
	}
	public Calendar getDob() {
		return dob;
	}
	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email; 
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ResourceType getType() {
		return type;
	}
	public void setType(ResourceType type) {
		this.type = type;
	}    
	  
	public Resource getManager() {
		return manager;
	}
	public void setManager(Resource manager) {
		this.manager = manager;
	}


	public boolean getisActive() {
		return isActive;
	}


	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}


	public boolean getisAdmin() {
		return isAdmin;
	}


	public void setisAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	     
	  
	  
	
}
