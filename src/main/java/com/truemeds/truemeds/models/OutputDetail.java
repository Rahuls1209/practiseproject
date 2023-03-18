package com.truemeds.truemeds.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Rahul_Singh_java_output")
public class OutputDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Override
	public String toString() {
		return "OutputDetail [id=" + id + ", count=" + count + ", creation_date=" + creation_date + ", creation_user="
				+ creation_user + ", final_output=" + final_output + "]";
	}

	private int count;
	
	private String creation_date;
	
	private String creation_user;
	
	private String final_output;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getCreation_user() {
		return creation_user;
	}

	public void setCreation_user(String creation_user) {
		this.creation_user = creation_user;
	}

	public String getFinal_output() {
		return final_output;
	}

	public void setFinal_output(String final_output) {
		this.final_output = final_output;
	}

	public OutputDetail( int count, String creation_date, String creation_user, String final_output) {
		super();
		this.count = count;
		this.creation_date = creation_date;
		this.creation_user = creation_user;
		this.final_output = final_output;
	}
	
	
	
	

}
