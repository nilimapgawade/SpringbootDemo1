package com.collections.genesys.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Lob;

public class ApiLogDto {

	private String endpoint; // API endpoint
	private String headers; // Serialized headers
	@Lob
	private String requestBody; // Request body
	@Lob
	private String responseBody; // Response body
	private int responseStatus; // HTTP status code
	private LocalDateTime clientTimestamp; // Request time
	private long timeTaken; // Time taken in milliseconds

	// Getters and Setters

	private String method;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public LocalDateTime getClientTimestamp() {
		return clientTimestamp;
	}

	public void setClientTimestamp(LocalDateTime clientTimestamp) {
		this.clientTimestamp = clientTimestamp;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
