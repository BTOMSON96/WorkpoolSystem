package com.workpool.entity;

public class Participant {
	
	private Long id;
    private String name;
    
    public Participant() {
    	
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
