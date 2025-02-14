package com.collections.genesys.advices;

public class ErrorDetails {
	
	private String type;
    private String code;
    private String message;
    private String additionalProp1;

    // Constructor
    public ErrorDetails(String type, String code, String message, String additionalProp1) {
        this.type = type;
        this.code = code;
        this.message = message;
        this.additionalProp1 = additionalProp1;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getAdditionalProp1() {

        return additionalProp1;
    }

    public void setAdditionalProp1(String additionalProp1) {

        this.additionalProp1 = additionalProp1;
    }

}
