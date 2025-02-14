package com.collections.genesys.controller;

import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.collections.genesys.advices.ErrorDetails;
import com.collections.genesys.dto.InBoundCallHeaderDto;
import com.collections.genesys.dto.InBoundCallRequestDto;
import com.collections.genesys.dto.InBoundCallResponseDto;
import com.collections.genesys.response.FailureResponse;
import com.collections.genesys.service.InBoundCallService;
import com.collections.genesys.service.InBoundCallHeaderService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/dcore/v1")
public class InBoundCallController {

	private static final Logger logger = LoggerFactory.getLogger(InBoundCallController.class);

	InBoundCallService boundCallService;

	InBoundCallHeaderService inBoundCallHeaderService;

	public InBoundCallController(InBoundCallService boundCallService,
			InBoundCallHeaderService inBoundCallHeaderService) {
		super();
		this.boundCallService = boundCallService;
		this.inBoundCallHeaderService = inBoundCallHeaderService;
	}

	@GetMapping(value = "/accountdetails", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<InBoundCallResponseDto> fetchScreen(@RequestBody InBoundCallRequestDto callRequest,
			HttpServletRequest request) throws JsonProcessingException {
		String response = null;
		boolean isError = false;
		boolean unknownUser = true;

		ResponseEntity<InBoundCallResponseDto> responseEntity = null;
		InBoundCallResponseDto boundCallResponse = null;
		FailureResponse failureResponse = new FailureResponse();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			logger.info("Received request to fetch account details: {}", callRequest);

			// Collecting headers
			InBoundCallHeaderDto inBoundCallHeader = new InBoundCallHeaderDto();
			inBoundCallHeader.setAuthorization(request.getHeader("Authorization"));
			inBoundCallHeader.setContentType(request.getHeader("Content-Type"));
			inBoundCallHeader.setChannelId(request.getHeader("Channel-ID"));
			inBoundCallHeader.setClientTimestamp(request.getHeader("Client-Timestamp"));
			inBoundCallHeader.setUrc(request.getHeader("URC"));

			logger.info(
					"Extracted headers: Authorization: {}, Content-Type: {}, Channel-ID: {}, Client-Timestamp: {}, URC: {}",
					inBoundCallHeader.getAuthorization(), inBoundCallHeader.getContentType(),
					inBoundCallHeader.getChannelId(), inBoundCallHeader.getClientTimestamp(),
					inBoundCallHeader.getUrc());

			String Accountno = callRequest.getAccountNo();
			if (Accountno != null) {
				Accountno.trim();
				String specialCharacterRegex = ".*[!@#$%^&*(),.?\":{}|<>].*";
				if (Pattern.matches(specialCharacterRegex, Accountno)) {

					logger.error("Special Character are Not allowed in Account Number");
					failureResponse.setStatus("failure");
					failureResponse.setMsg("Special Character are Not allowed in Account Number");
					try {
						response = objectMapper.writeValueAsString(failureResponse);
						inBoundCallHeaderService.saveInBoundCallHeader(inBoundCallHeader, callRequest, "FAIL",
								"Account No can not be Blank");
					} catch (JsonProcessingException jsonException) {
						logger.error("Error serializing failure response: {}", jsonException.getMessage(),
								jsonException);
					}
					responseEntity = new ResponseEntity(response, HttpStatus.BAD_REQUEST);
					return responseEntity;

				}

			}

			// unknownUser = decodeTokenService.validateToken(request);

			/*
			 * if (!unknownUser) {
			 * 
			 * inBoundCallHeaderService.saveInBoundCallHeader(inBoundCallHeader,
			 * callRequest, "FAIL", "Token validation failed"); }
			 */

			if (Accountno != null) {
				logger.info("Token validated successfully for user.");

				boundCallResponse = boundCallService.fetchAccountData(callRequest);
				if (boundCallResponse != null && boundCallResponse.getTotalOutstandingAmount()!=null) {
					logger.info("Successfully fetched account data for Account No: {}", callRequest.getAccountNo());

					//return responseEntity = ResponseEntity.ok().header("URC", inBoundCallHeader.getUrc()) // Add URC
																											// value to
						//	.body(boundCallResponse);

					 return responseEntity = ResponseEntity.ok().body(boundCallResponse);

					// inBoundCallHeaderService.saveInBoundCallHeader(inBoundCallHeader,
					// callRequest, "success",
					// boundCallResponse);
				} else {
					
					return responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

			} else {
				logger.error("Unauthorized request: Invalid token or user.");
				failureResponse.setStatus("failure");
				failureResponse.setMsg("Token validation failed");
				try {
					response = objectMapper.writeValueAsString(failureResponse);
				} catch (JsonProcessingException jsonException) {
					logger.error("Error serializing failure response: {}", jsonException.getMessage(), jsonException);
				}
				responseEntity = new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			isError = true;
			logger.info("In the catch block making it true ");
			logger.error("An error occurred while fetching account details: {}", e.getMessage(), e);

		}finally {
			if (isError) {
				failureResponse.setStatus("failure");
				failureResponse.setMsg("Exception occurred");
				try {
					response = objectMapper.writeValueAsString(failureResponse);
				} catch (JsonProcessingException jsonException) {
					logger.error("Error serializing failure response: {}", jsonException.getMessage(), jsonException);
				}
				responseEntity = new ResponseEntity(response, HttpStatus.OK);
			}
		}
		return responseEntity;
	}

	public ErrorDetails getErrorDetails(String type, String code, String message, String additionalProp1) {
		return new ErrorDetails(type, code, message, additionalProp1);
	}

}
