package com.truemeds.truemeds.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import com.truemeds.truemeds.error.validator.Author;


@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Please enter Book name")
	private String name;
	
	@Author
	@NotEmpty(message="Please enter author name")
	private String author;
	
	@DecimalMin(value = "1.00")
	//@Null(message="Please provide price")
	private BigDecimal price;
	
	
	  public Book() {
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


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public Book(Long id, @NotEmpty(message = "Please enter Book name") String name,
			@NotEmpty(message = "Please enter author name") String author,
			@DecimalMin("1.00") @Null(message = "Please provide price") BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
	}


	public Book(@NotEmpty(message = "Please enter Book name") String name,
			@NotEmpty(message = "Please enter author name") String author,
			@DecimalMin("1.00") @Null(message = "Please provide price") BigDecimal price) {
		super();
		this.name = name;
		this.author = author;
		this.price = price;
	}


	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + "]";
	}
	  
	  
	  
	

}
