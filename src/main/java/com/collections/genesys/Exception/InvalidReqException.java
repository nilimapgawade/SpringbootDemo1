package com.collections.genesys.Exception;
public class InvalidReqException extends Exception {

	private String errorcode;

	public String getErrorcode() {
		return errorcode;
	}

	public InvalidReqException(String message, String errorcode) {
		super(message);
		this.errorcode = errorcode;
	}
	
	

}