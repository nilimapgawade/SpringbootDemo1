package com.collections.genesys.filters;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.collections.genesys.Entity.ApiLogEntity;
import com.collections.genesys.Exception.IlligalArgumentWithObjectException;
import com.collections.genesys.Exception.InvalidTokenException;
import com.collections.genesys.advices.ApiErrorResponse;
import com.collections.genesys.advices.ErrorDetails;
import com.collections.genesys.repository.ApiLogRepository;
import com.collections.genesys.service.DecodeTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationLoggingFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationLoggingFilter.class);

	private final ApiLogRepository logRepository;

	private final DecodeTokenService decodeTokenService;

	private final List<String> excludedUrls = List.of("/generatetoken");

	public JwtAuthenticationLoggingFilter(ApiLogRepository logRepository, DecodeTokenService decodeTokenService) {
		super();
		this.logRepository = logRepository;
		this.decodeTokenService = decodeTokenService;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("Filter started for request: " + request.getRequestURI());

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();

		// Skip filter for excluded URLs

		if (excludedUrls.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		String requestToken = request.getHeader("Authorization");

		if (requestToken == null || !requestToken.startsWith("Bearer ")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
			return;
		}

		try {
			boolean isTokenValid = decodeTokenService.validateToken(request);

			if (!isTokenValid) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Invalid token  either  token is expired or issuer is incorrect");
				return;
			}

			Instant startTime = Instant.now();
			filterChain.doFilter(requestWrapper, responseWrapper); // Proceed with the request
			Instant endTime = Instant.now();

			long timeTaken = Duration.between(startTime, endTime).toMillis();

			// Logging API details
			ApiLogEntity apiLog = new ApiLogEntity();
			apiLog.setEndpoint(request.getRequestURL().toString());
			apiLog.setMethod(request.getMethod());
			apiLog.setRequestBody(new String(requestWrapper.getContentAsByteArray()));
			apiLog.setResponseBody(new String(responseWrapper.getContentAsByteArray()));
			apiLog.setResponseStatus(response.getStatus());
			apiLog.setTimestamp(LocalDateTime.now());
			apiLog.setUrc(request.getHeader("URC"));// need to check
			apiLog.setTimeTaken(timeTaken);

			// Capture headers
			Enumeration<String> headerNames = request.getHeaderNames();
			StringBuilder headers = new StringBuilder();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				headers.append(headerName).append(": ").append(headerValue).append("\n");
			}
			apiLog.setHeaders(headers.toString());

			// Save log
			logRepository.save(apiLog);

		} catch (InvalidTokenException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is wrong or expired");
		} catch (IlligalArgumentWithObjectException e) {
			ApiErrorResponse errorResponse = new ApiErrorResponse(
					Collections.singletonList((ErrorDetails) e.getErrorDetails()));

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
		} catch (Exception e) {
			logger.error("Unexpected error during JWT validation", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
		} finally {
			responseWrapper.copyBodyToResponse(); // Ensure the response body is written back
		}

		logger.info("Filter ended for request: " + request.getRequestURI());

	}

}