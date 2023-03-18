package com.truemeds.truemeds.models;

import javax.persistence.*;

@Entity
@Table(name="input_details")
public class InputDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "input_string")
	private String input;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	
	

}
