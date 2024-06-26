package com.pms.response;

public class AuthResponse {
 // we will jwt token and message
	private String jwt;
	private String message;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public AuthResponse(String jwt, String message) {
		super();
		this.jwt = jwt;
		this.message = message;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
