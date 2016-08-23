package com.donglu.carpark;

import java.io.Serializable;

public class TestUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -927246829064366892L;
	private String name;
	
	public TestUser(){
		
	}
	public TestUser(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
