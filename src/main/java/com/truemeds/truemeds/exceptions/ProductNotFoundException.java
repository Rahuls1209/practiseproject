package com.truemeds.truemeds.exceptions;

public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException (String name) {
		
		super("product Id Not found "+name);
		
	}
	
	
}
