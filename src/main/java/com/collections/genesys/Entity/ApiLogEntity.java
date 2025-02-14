package com.collections.genesys.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "COL_LOG_DCAPI")
public class ApiLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "szendpoint")
	private String endpoint; // API endpoint

	@Column(name = "szheaders")
	private String headers; // Serialized headers
	
	@Column(name = "szrequestbody")
	private String requestBody; // Request body
	
	
	@Column(name = "szresponsebody")
	private String responseBody; // Response body

	@Column(name = "iresponsestatus")
	private int responseStatus; // HTTP status code

	@Column(name = "dttimestamp")
	private LocalDateTime timestamp; // Request time

	@Column(name = "itimetaken")
	private long timeTaken; // Time taken in milliseconds

	@Column(name = "SZURC")
	private String urc;
	


	// Getters and Setters
	@Column(name = "szmethod")
	private String method; // HTTP method (GET, POST, etc.)

	public String getUrc() {
		return urc;
	}

	public void setUrc(String urc) {
		this.urc = urc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

}
