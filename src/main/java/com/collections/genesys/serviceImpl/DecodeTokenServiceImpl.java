package com.collections.genesys.serviceImpl;

import java.util.Base64;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.collections.genesys.Exception.IlligalArgumentWithObjectException;
import com.collections.genesys.Exception.InvalidTokenException;
import com.collections.genesys.advices.Constant;
import com.collections.genesys.advices.ErrorDetails;
import com.collections.genesys.dto.PublicKeyResponseDto;
import com.collections.genesys.repository.ChannelRepository;
import com.collections.genesys.response.Key;
import com.collections.genesys.service.ApiLogService;
import com.collections.genesys.service.ChannelService;
import com.collections.genesys.service.DecodeTokenService;
import com.collections.genesys.service.PublicKeyService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DecodeTokenServiceImpl implements DecodeTokenService {

	private static final Logger logger = LoggerFactory.getLogger(DecodeTokenServiceImpl.class);

	private final PublicKeyService keyService;
	private final ChannelService channelService;
	private final ApiLogService apiLogService;

	@Value("${iss.enbd}")
	private String issuer;

	public DecodeTokenServiceImpl(PublicKeyService keyService, ChannelService channelService,
			ApiLogService apiLogService) {
		this.keyService = keyService;

		this.channelService = channelService;
		this.apiLogService = apiLogService;
	}

	@Override
	public boolean validateToken(HttpServletRequest request) throws Exception {
		if (request == null) {
			logger.info("Request is null.");
			return false;
		}

		validateHeaders(request.getHeader("URC"), request.getHeader("Channel-Id"), request.getHeader("Financial-Id"));

		String token = extractToken(request.getHeader("Authorization"));
		if (token == null) {
			logger.info("Authorization header is missing or invalid.");
			return false;
		}

		JSONObject header = decodeJWTPart(token.split("\\.")[0]);
		JSONObject payload = decodeJWTPart(token.split("\\.")[1]);

		if (header == null || payload == null) {
			logger.info("Token decoding failed.");
			return false;
		}

		return validateJWT(header, payload);
	}

	private String extractToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}

	private JSONObject decodeJWTPart(String jwtPart) {
		try {
			return new JSONObject(new String(Base64.getUrlDecoder().decode(jwtPart)));
		} catch (Exception e) {
			logger.error("Error decoding JWT part", e);
			return null;
		}
	}

	private boolean validateJWT(JSONObject header, JSONObject payload) throws InvalidTokenException {
		try {
			PublicKeyResponseDto fetchJWKSet = keyService.fetchJWKSet("id");

			if (fetchJWKSet != null) {
				for (Key key : fetchJWKSet.getKeys()) {
					if (keyMatches(header, payload, key)) {
						validateTokenExpiration(payload);
						validateIssuer(payload);

						logger.info("JWT token validated successfully.");
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Token validation failed: {}", e.getMessage());
			throw new InvalidTokenException("Token is invalid",
					getErrorDetails(Constant.TECHNICAL, "10001", "Authentication failed", "Token is invalid."));
		}

		logger.info("Token validation failed.");
		return false;
	}

	private boolean keyMatches(JSONObject header, JSONObject payload, Key key) {
		return key.getKid().equalsIgnoreCase(header.optString("kid")) && "JWT".equalsIgnoreCase(header.optString("typ"))
				&& "Bearer".equals(payload.optString("typ"));
	}

	private void validateIssuer(JSONObject payload) throws InvalidTokenException {
		if (payload.has("iss") && !issuer.equals(payload.getString("iss"))) {
			logger.info("Token issuer is invalid.");
			throw new InvalidTokenException("Authentication failed",
					getErrorDetails(Constant.TECHNICAL, "10001", "Authentication failed", "Token issuer is invalid."));
		}
	}

	private void validateTokenExpiration(JSONObject payload) throws InvalidTokenException {
		if (!payload.has("exp")) {
			logger.info("Token does not contain an expiration field.");
			throw new InvalidTokenException("Authentication failed", getErrorDetails(Constant.TECHNICAL, "10001",
					"Authentication failed", "Token does not contain an expiration field."));
		}

		long expirationTime = payload.getLong("exp") * 1000; // Convert exp from seconds to milliseconds
		long currentTime = System.currentTimeMillis();

		if (expirationTime < currentTime) {
			logger.info("Token has expired.");
			throw new InvalidTokenException("Token has expired",
					getErrorDetails(Constant.TECHNICAL, "10001", "Token has expired", "Token has expired."));
		}
	}

	private ErrorDetails getErrorDetails(String type, String code, String message, String detail) {
		return new ErrorDetails(type, code, message, detail);
	}

	private void validateHeaders(String urc, String channel, String financialId)
			throws IlligalArgumentWithObjectException {

		Set<String> validFinancialIds = Set.of("EBI", "ENBD");
		if (urc == null || channel == null || financialId == null) {
			throw new IlligalArgumentWithObjectException("Missing required headers", getErrorDetails(Constant.TECHNICAL,
					"10001", "Header validation failed", "Required headers are missing."));
		}

		if (!apiLogService.isValidUrc(urc)) {
			logger.info("urc isvalid? {}", urc);
			ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10005", Constant.URC_WRONGFORMAT,
					Constant.URC_WRONGFORMAT);
			throw new IlligalArgumentWithObjectException(Constant.URC_WRONGFORMAT, errorDetails);

		}

		if (!validFinancialIds.contains(financialId)) {

			logger.info("Invalid financial ID");
			ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10005", Constant.INCVLAID_FINAN_ID,
					Constant.INCVLAID_FINAN_ID);
			throw new IlligalArgumentWithObjectException(Constant.INCVLAID_FINAN_ID, errorDetails);
		}

		/*
		 * if (timestamp == null) {
		 * 
		 * logger.info("timestamp{}",timestamp); ErrorDetails errorDetails =
		 * getErrorDetails(Constant.TECHNICAL, "10004", Constant.CLIENT_TIMESTAMP,
		 * Constant.CLIENT_TIMESTAMP); throw new
		 * IlligalArgumentWithObjectException(Constant.CLIENT_TIMESTAMP, errorDetails);
		 * 
		 * }
		 */

		// Validate timestamp format (e.g., ISO-8601) and freshness
		/*
		 * try {
		 * 
		 * long timestampMillis = Long.parseLong(timestamp);
		 * 
		 * // Convert to Instant Instant requestTime =
		 * Instant.ofEpochMilli(timestampMillis); Instant now = Instant.now(); if
		 * (requestTime.isBefore(now.minusSeconds(300)) ||
		 * requestTime.isAfter(now.plusSeconds(300))) {
		 * 
		 * ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "100090",
		 * Constant.INVALID_TIMESTAMP, Constant.INVALID_TIMESTAMP); throw new
		 * IlligalArgumentWithObjectException(Constant.INVALID_TIMESTAMP + timestamp,
		 * errorDetails);
		 * 
		 * } } catch (Exception e) {
		 * 
		 * ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10003",
		 * Constant.INVALID_TIMESTAMP, Constant.INVALID_TIMESTAMP); throw new
		 * IlligalArgumentWithObjectException(Constant.INVALID_TIMESTAMP + timestamp,
		 * errorDetails);
		 * 
		 * }
		 */
		// Validate channel (example: allow only specific values)

		try {
			if (!"COL".equalsIgnoreCase(channel) || !channelService.isValidChannel(channel)) {
				ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10003", Constant.INVALID_CHANNEL,
						Constant.INVALID_CHANNEL);
				throw new IlligalArgumentWithObjectException(Constant.INVALID_CHANNEL + channel, errorDetails);
			}

		} catch (Exception e) {

			ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10003", Constant.INVALID_CHANNEL,
					Constant.INVALID_CHANNEL);
			throw new IlligalArgumentWithObjectException(Constant.INVALID_CHANNEL + channel, errorDetails);

		}

	}

}