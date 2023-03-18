package com.truemeds.truemeds.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity(name="refreshToken")

public class RefreshToken {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	
	@OneToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	@Column(nullable=false,unique=true)
	private String token;
	
	
	@Column(nullable = false)
	private Instant expiryDate;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public User getUsers() {
		return user;
	}


	public void setUsers(User users) {
		this.user = users;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Instant getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
	
	public RefreshToken() {
		
		
	}


	public RefreshToken(String token) {
		super();
		this.token = token;
	}
	
	
	
	
}
