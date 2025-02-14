package com.collections.genesys.advices;


import java.util.List;

public class ApiErrorResponse {

	private List<ErrorDetails> errors;

    // Constructor, getters, and setters
    public ApiErrorResponse(List<ErrorDetails> errors) {

        this.errors = errors;
    }

    public List<ErrorDetails> getErrors() {

        return errors;
    }

    public void setErrors(List<ErrorDetails> errors) {
        this.errors = errors;
    }
	
}
