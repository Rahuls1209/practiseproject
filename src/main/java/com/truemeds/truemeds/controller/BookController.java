package com.truemeds.truemeds.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.truemeds.truemeds.Repository.BookRepository;
import com.truemeds.truemeds.error.validator.Author;
import com.truemeds.truemeds.exceptions.BookNotFoundException;
import com.truemeds.truemeds.exceptions.BookUnSupportedFieldPatchException;
import com.truemeds.truemeds.models.Book;


@RestController
@Validated
public class BookController {
	
	
	private static final Logger log = LogManager.getLogger(BookController.class);
	
	@Autowired
	BookRepository repo;
	
	
	
	@RequestMapping(value= {"/books"}, method= {RequestMethod.GET})
	List<Book> listofBooks(){
		
		return repo.findAll();
		
	}
		
		
	@RequestMapping(value= {"/books"},method= {RequestMethod.POST})
	@ResponseStatus(HttpStatus.CREATED)
	Book newBook (@RequestBody Book newBook) {
			
		return repo.save(newBook);

	}
	
	@RequestMapping(value= {"/books/{id}"},method= {RequestMethod.GET})
	Book findOne(@PathVariable Long id ) {
		return repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}
	
	
	// Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody  Book newBook , @PathVariable @NotNull Long id ) {

        return repo.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return repo.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repo.save(newBook);
                });
    }

    // update author only
    @PatchMapping("/books/{id}")
    Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repo.findById(id)
                .map(x -> {

                    String author = update.get("author");
                    String name= update.get("name");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);
                        x.setName(name);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repo.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });

    }
	
	
	  @RequestMapping(value= {"/books/{id}"},method= {RequestMethod.DELETE})
	    void deleteBook(@PathVariable Long id) {
	        repo.deleteById(id);
	    }
	  
	  
	  @RequestMapping(value= {"/booki"},method= {RequestMethod.GET})
		Book findOneRequestparam(@RequestParam @NotNull Long id  ) {
		  
		  log.info("------------/booki controller value of id  {}",id);
			return repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
		}
	  
		
	}
	
	
	
	

