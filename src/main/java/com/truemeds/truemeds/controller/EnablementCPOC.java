package com.truemeds.truemeds.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnablementCPOC {


	public static String qpos_sred_bdk = null;
	public static String qpos_pin_bdk = null;
	
	
//	@PostMapping(
//	        value = "/enablementSPOC"
////	        produces = "text/plain",
////	        consumes ="application/x-www-form-u

	@PostMapping("/enablementCPOC")
	
	public String enablement(@RequestParam("deviceId") long deviceId, @RequestParam("requestMessage") String requestMessage,@RequestParam("requestHash")String requestHash,@RequestParam("requestMac")String requestMac){
		return "THIS IS ENABLEMENT API";
				//enhan.enablement(deviceId, requestMac, requestMac, requestMac);
	}
	
}
