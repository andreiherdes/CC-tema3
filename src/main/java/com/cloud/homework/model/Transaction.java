package com.cloud.homework.model;

public class Transaction {

	private String request;
	private String response;
	
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Transaction [request=" + request + ", response=" + response + "]";
	}
	
	

}
