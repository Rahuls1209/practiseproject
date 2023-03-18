package com.truemeds.truemeds.exceptions;

public class BookNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(Long id) {
		
		super("Book id Not Found ="+ id);
		
	}

}
