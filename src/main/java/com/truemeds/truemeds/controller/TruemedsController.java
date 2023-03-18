package com.truemeds.truemeds.controller;

import java.awt.PageAttributes.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.truemeds.truemeds.models.InputDetail;
import com.truemeds.truemeds.models.Product;
import com.truemeds.truemeds.payload.Request.AddProduct;
import com.truemeds.truemeds.payload.Request.LoginRequest;
import com.truemeds.truemeds.payload.response.JwtResponse;
import com.truemeds.truemeds.payload.response.Response;
import com.truemeds.truemeds.service.TruemedsService;


@RestController
@RequestMapping("/api")
public class TruemedsController {

	@Autowired
	TruemedsService truemedService;
	
	

	private static final Logger log = LogManager.getLogger(TruemedsController.class);


	@GetMapping("/truemed")
	public ResponseEntity<String> processDetails() {
		log.info("Controller: createTutorial");

	//	try {

			List<InputDetail> input=new ArrayList<InputDetail>();
			input=truemedService.getInputDetails();

			truemedService.processDetails(input);


			return  ResponseEntity.ok("success");// new ResponseEntity<>(null,HttpStatus.OK);
	//	} catch (Exception e) {
		//	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		//}
	}

	
	
	
	  @PostMapping("/addproduct") public ResponseEntity<String>
	  addProduct(@RequestBody AddProduct addProduct){
	  
		  System.out.println(addProduct);
	  truemedService.addProduct(addProduct);
	  
	  return new ResponseEntity<>("Products is Added successfully", HttpStatus.OK);
	  
	  }
	 
	
	   
	  @GetMapping(value= {"/productsearch/{name}/{id}","/productsearch/{name}"})
	  public ResponseEntity<?> searchProduct(@PathVariable String name,@PathVariable Optional<String> id ) {
		  
		  String productId;
		  
		  if(id.isPresent()) {
			  
			  productId=id.get();
		  }
	
	  
		  log.info("Controller: Search Product "+name);
		  
		  
		Response response =truemedService.GetProduct(name);
		 
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	  }
	  
	  
	  @GetMapping(value= {"/productsearchreqparam"})
	  public ResponseEntity<?> searchProductWithRequestParam(@RequestParam String name,@RequestParam Optional<String> id ) {
		  
		  String productId;
		  
		  if(id.isPresent()) {
			  
			  productId=id.get();
		  }
	
	  
		  log.info("Controller: Search Product "+name);
		  
		  
		Response response =truemedService.GetProduct(name);
		 
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	  }
	  
	  
	  
	  
	 
	  
	  @GetMapping("/call")
	  public ResponseEntity<?> callRestApiGet(){
		  
		  
		  
		  RestTemplate restTemplate = new RestTemplate();
		  
		  String url="http://localhost:8080/api/productsearch";
		  
		  ResponseEntity<String> response = restTemplate.getForEntity(url +"/c++" , String.class);
		
		  
		  
		  
		return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
		  
		  
		  
		  
	  }
	  

	  @GetMapping("/addProductPostCall")
	  public ResponseEntity<?> callRestApiPost() {
		  
		  log.info("---------------------insde callRestApiPost--------------------");
	    

	      RestTemplate restTemplate = new RestTemplate();
	      
	      final String baseUrl = "http://localhost:8088/api/addproduct";
	      
	      
	      //URI uri = new URI(baseUrl);
	       
	      Product prod1 =new Product("social Science",4);
	      Product prod2 =new Product("Home Science",5);
	      Product prod3 =new Product("Science",6);
	      Product prod4 =new Product("Geography",7);
	      Product prod5 =new Product("social Science",8);
	      
	      List<Product> productList=new ArrayList<>();
	      productList.add(prod1);
	      productList.add(prod2);
	      productList.add(prod4);
	      productList.add(prod5);
	      
	      AddProduct request =new AddProduct(productList);
	   
	      ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, request, String.class);
	      
	      return new ResponseEntity(result.getBody(),HttpStatus.OK);
	  }
	  
	  
	  
	  @GetMapping("/getusercontent")
	  public ResponseEntity<?> getUserContent (){
		  
		  
		  final String longinBaseUrl= "http://localhost:8088/api/auth/login";
		  
		  RestTemplate loginRestTemplate =new RestTemplate();
		  
		  
		  LoginRequest request =new LoginRequest();
		  
		  request.setUserName("suresh");
		  request.setPassword("rahul@123");
		  
		  ResponseEntity<JwtResponse> result = loginRestTemplate.postForEntity(longinBaseUrl, request, JwtResponse.class);
		  
		  

		  
		  String accessToken= result.getBody().getAccessToken();
		  
		  String RefreshToken=result.getBody().getRefreshToken();
		  
		  
		  RestTemplate usercontentRestTemplate =new RestTemplate();
		 
		  String usercontentBaseUrl="http://localhost:8088/api/auth/user";
		  
		  HttpHeaders header =new HttpHeaders();
		  
		  header.add("Content-Type", "application/json");
		  header.add("Authorization", "Bearer "+accessToken);
		  
		  
		 
		  ResponseEntity<String> userComtentresult = usercontentRestTemplate.exchange(usercontentBaseUrl, HttpMethod.GET
				  ,  new HttpEntity<>("parameters", header), String.class);
		  
		  return new ResponseEntity(userComtentresult.getBody(),HttpStatus.OK);
		  
		  
		 }
	  
	  
	  
	  @PostMapping("/getproductSearchRequestparam")
	  public ResponseEntity<?> getbookRequestparam (){
		  
		  
		  log.info("------------------inside getbookRequestparam Controller------------------------------ ");
		  
		  
		  final String longinBaseUrl= "http://localhost:8088/api/auth/login";
		  
		  RestTemplate loginRestTemplate =new RestTemplate();
		  
		  
		  LoginRequest request =new LoginRequest();
		  
		  request.setUserName("suresh");
		  request.setPassword("rahul@123");
		  
		  ResponseEntity<JwtResponse> result = loginRestTemplate.postForEntity(longinBaseUrl, request, JwtResponse.class);
		  
		  

		  
		  String accessToken= result.getBody().getAccessToken();
		  
		  String RefreshToken=result.getBody().getRefreshToken();
		  
		  
		  RestTemplate usercontentRestTemplate =new RestTemplate();
		 
		  String usercontentBaseUrl="http://localhost:8088/api/productsearchreqparam";
		  
		  HttpHeaders header =new HttpHeaders();
		  
		  //header.add("Content-Type", "application/json");
		  
		  header.add("Content-Type","application/x-www-form-urlencoded");
		  header.add("Authorization", "Bearer "+accessToken);

		  MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		  map.add("id","2");
		 
		  
		 
		  ResponseEntity<String> userComtentresult = usercontentRestTemplate.exchange(usercontentBaseUrl, HttpMethod.GET
				  ,  new HttpEntity<>("id=1", header), String.class);
		  
		  log.info("value of userComtentresult {} ",userComtentresult.getBody());
		  
		  return new ResponseEntity(userComtentresult.getBody(),HttpStatus.OK);
		  
		  
		 }
		

}
