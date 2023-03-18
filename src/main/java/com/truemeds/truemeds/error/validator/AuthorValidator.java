package com.truemeds.truemeds.error.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AuthorValidator implements ConstraintValidator<Author, String> {
	
	
	List<String> authors =  Arrays.asList("rohit","parkash","sharma","hitesh");
	
	private static final Logger log = LogManager.getLogger(AuthorValidator.class);
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info("inside authors validator ");
		boolean flag = false;
		
		try {
			flag = authors.contains(value);
		}
		catch(Exception e) {
			log.info("AuthorValidator Exception: " + e);
		}
		
		return flag;
	}

}
