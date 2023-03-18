package com.truemeds.truemeds.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truemeds.truemeds.models.Product;
import com.truemeds.truemeds.payload.Request.AddProduct;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


	public Product findByName(String name);
	
	
	
}
