package com.truemeds.truemeds.service;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import com.truemeds.truemeds.models.InputDetail;
import com.truemeds.truemeds.models.Product;
import com.truemeds.truemeds.payload.Request.AddProduct;
import com.truemeds.truemeds.payload.response.Response;

@Service
public interface TruemedsService {

	public List<InputDetail> getInputDetails();

	public void processDetails(List<InputDetail> input);
	
	
	public void addProduct(AddProduct productList);
	
	

	public Response GetProduct(String name);
	
	
	
	
	

}
