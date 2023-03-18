package com.truemeds.truemeds.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>  {

}
