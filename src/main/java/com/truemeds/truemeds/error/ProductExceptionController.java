package com.truemeds.truemeds.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.truemeds.truemeds.controller.TruemedsController;
import com.truemeds.truemeds.exceptions.ProductNotFoundException;

@ControllerAdvice
public class ProductExceptionController {
	
	private static final Logger log = LogManager.getLogger(TruemedsController.class);
	
	@ExceptionHandler(value=ProductNotFoundException.class)
	public ResponseEntity<Object> exception(ProductNotFoundException exception) {
		
		log.info("inside productException Controller");
		return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
		
		
		
	}

}
