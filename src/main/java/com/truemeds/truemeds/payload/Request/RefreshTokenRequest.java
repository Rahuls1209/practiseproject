package com.truemeds.truemeds.payload.Request;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {
	
	  @NotBlank
	  private String refreshToken;

	  public String getRefreshToken() {
	    return refreshToken;
	  }

	  public void setRefreshToken(String refreshToken) {
	    this.refreshToken = refreshToken;
	  }

}