package com.truemeds.truemeds.payload.Request;

import java.util.List;

import com.truemeds.truemeds.models.Product;




public class AddProduct {
	
	
	List<Product> productList;
	
	
	

	public List<Product> getProductList() {
		return productList;
	}




	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}




	@Override
	public String toString() {
		return "AddProduct [productList=" + productList + "]";
	}




	public AddProduct(List<Product> productList) {
		super();
		this.productList = productList;
	}
	
	public AddProduct() {
		
		
		
	}
	
	
	

}
