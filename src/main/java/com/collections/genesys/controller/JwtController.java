package com.collections.genesys.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collections.genesys.serviceImpl.ChannelServiceImpl;
import com.collections.genesys.serviceImpl.JwtService;


//@RequestMapping("/test")
@RestController
public class JwtController {

	private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
	private final JwtService jwtService;

	public JwtController(JwtService jwtService) {
		this.jwtService = jwtService;

	}

	// Generate JWT Token
	/*
	@PostMapping(value = "/generatetoken" , consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public String generateToken(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		logger.info("Inside generateToken of JwtController");
		String token =jwtService.generateToken(username);
		 return  token;

	//	return "Hello";
	}
*/
	
	 @PostMapping(value = "/generatetoken", 
		        consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
		        produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
		    public ResponseEntity<Map<String, String>> generateToken(@RequestBody Map<String, String> request) {
		        
		        String username = request.get("username");
		        
		        logger.info("Inside generateToken of JwtController");

		        if (username == null || username.isEmpty()) {
		            return ResponseEntity.badRequest().body(Map.of("error", "Username is required"));
		        }

		        String token = jwtService.generateToken(username);

		        // Return token in a JSON response with HTTP 200 OK
		       return ResponseEntity.ok(Map.of("token", token));
		       // return ResponseEntity.ok(Map.of("message", "Test successful"));
		    }
	 
	/* @PostMapping("/generatetoken")
	 public ResponseEntity<String> generateToken() {
	     logger.info("Inside generateToken of JwtController");
	     return ResponseEntity.ok("Token generated successfully!");
	 }*/
	
	/*
	 * // Verify JWT Token
	 * 
	 * @PostMapping("/verifytoken") public boolean verifyToken(@RequestBody
	 * Map<String, String> request) {
	 * 
	 * logger.info("Inside verifyToken of JwtController"); String token =
	 * request.get("token"); return jwtService.verifyToken(token); }
	 */

	@PostMapping("/verifytoken" )
	public boolean verifyToken(@RequestHeader("Authorization") String token) {
		// Remove "Bearer " prefix if present
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		return jwtService.verifyToken(token);
	}
}
