/*
 * package com.collections.genesys.filters;
 * 
 * import java.io.IOException; import java.time.Instant; import
 * java.util.Collections; import java.util.concurrent.ConcurrentHashMap;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter; import
 * org.springframework.web.util.ContentCachingRequestWrapper; import
 * org.springframework.web.util.ContentCachingResponseWrapper;
 * 
 * import com.collections.genesys.Exception.IlligalArgumentWithObjectException;
 * import com.collections.genesys.advices.ApiErrorResponse; import
 * com.collections.genesys.advices.Constant; import
 * com.collections.genesys.advices.ErrorDetails; import
 * com.collections.genesys.service.ApiLogService; import
 * com.collections.genesys.service.ChannelService; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException;
 * import jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * @Component public class HeaderValidationFilter extends OncePerRequestFilter {
 * 
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(JwtAuthenticationLoggingFilter.class);
 * 
 * // Cache to track processed reference numbers private final
 * ConcurrentHashMap<String, Instant> processedReferences = new
 * ConcurrentHashMap<>();
 * 
 * private final ChannelService channelService;
 * 
 * private final ApiLogService apiLogService;
 * 
 * 
 * public HeaderValidationFilter(ChannelService channelService, ApiLogService
 * apiLogService) { super(); this.channelService = channelService;
 * this.apiLogService = apiLogService; }
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * 
 * 
 * ContentCachingRequestWrapper requestWrapper = new
 * ContentCachingRequestWrapper(request); ContentCachingResponseWrapper
 * responseWrapper = new ContentCachingResponseWrapper(response);
 * logger.info("Inside doFilterInternal of HeaderValidationFilter 1 "); String
 * requestBody = new String(requestWrapper.getContentAsByteArray());
 * 
 * logger.
 * info("Inside doFilterInternal of HeaderValidationFilter  2 request body is  {}"
 * +requestBody);
 * 
 * String referenceNumber = request.getHeader("URC"); String timestamp =
 * request.getHeader("Client-Timestamp"); String channel =
 * request.getHeader("Channel-Id"); String financialId =
 * request.getHeader("Financial-Id"); //String accountNo = request.getB try { //
 * Validate headers validateHeaders(referenceNumber,channel, financialId); //
 * Proceed with the request filterChain.doFilter(request, response); } catch
 * (IlligalArgumentWithObjectException e) { // Handle invalid headers
 * 
 * ApiErrorResponse failureResponse = new ApiErrorResponse(
 * Collections.singletonList((ErrorDetails) e.getErrorDetails()));
 * 
 * response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
 * response.getWriter().write(new
 * ObjectMapper().writeValueAsString(failureResponse));
 * 
 * } }
 * 
 * private void validateHeaders(String urc, String channel, String financialId)
 * {
 * 
 * if (financialId == null) {
 * logger.info("financialId validation{}",financialId); ErrorDetails
 * errorDetails = getErrorDetails(Constant.TECHNICAL, "10003",
 * Constant.FINANCIAL_ID, Constant.FINANCIAL_ID); throw new
 * IlligalArgumentWithObjectException(Constant.FINANCIAL_ID, errorDetails);
 * 
 * }
 * 
 * if (urc == null) { logger.info("urc validation{}",urc); ErrorDetails
 * errorDetails = getErrorDetails(Constant.TECHNICAL, "10005",
 * Constant.URC_UNDEFINED, Constant.URC_UNDEFINED); throw new
 * IlligalArgumentWithObjectException(Constant.URC_UNDEFINED, errorDetails);
 * 
 * } else if (!apiLogService.isValidUrc(urc)) {
 * logger.info("urc isvalid? {}",urc); ErrorDetails errorDetails =
 * getErrorDetails(Constant.TECHNICAL, "10005", Constant.URC_WRONGFORMAT,
 * Constant.URC_WRONGFORMAT); throw new
 * IlligalArgumentWithObjectException(Constant.URC_WRONGFORMAT, errorDetails);
 * 
 * }
 * 
 * 
 * 
 * if (timestamp == null) {
 * 
 * logger.info("timestamp{}",timestamp); ErrorDetails errorDetails =
 * getErrorDetails(Constant.TECHNICAL, "10004", Constant.CLIENT_TIMESTAMP,
 * Constant.CLIENT_TIMESTAMP); throw new
 * IlligalArgumentWithObjectException(Constant.CLIENT_TIMESTAMP, errorDetails);
 * 
 * }
 * 
 * // Validate reference number uniqueness if
 * (processedReferences.putIfAbsent(urc, Instant.now()) != null) { ErrorDetails
 * errorDetails = getErrorDetails(Constant.TECHNICAL, "100090",
 * Constant.DUPLICATE_URC, Constant.DUPLICATE_URC); throw new
 * IlligalArgumentWithObjectException(Constant.DUPLICATE_URC + timestamp,
 * errorDetails);
 * 
 * } // Validate timestamp format (e.g., ISO-8601) and freshness
 * 
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
 * } // Validate channel (example: allow only specific values)
 * 
 * 
 * try { if (!"COL".equalsIgnoreCase(channel) ||
 * !channelService.isValidChannel(channel)) { ErrorDetails errorDetails =
 * getErrorDetails(Constant.TECHNICAL, "10003", Constant.INVALID_CHANNEL,
 * Constant.INVALID_CHANNEL); throw new
 * IlligalArgumentWithObjectException(Constant.INVALID_CHANNEL + channel,
 * errorDetails); }
 * 
 * } catch (Exception e) {
 * 
 * ErrorDetails errorDetails = getErrorDetails(Constant.TECHNICAL, "10003",
 * Constant.INVALID_CHANNEL, Constant.INVALID_CHANNEL); throw new
 * IlligalArgumentWithObjectException(Constant.INVALID_CHANNEL + channel,
 * errorDetails);
 * 
 * 
 * }
 * 
 * }
 * 
 * public ErrorDetails getErrorDetails(String type, String code, String message,
 * String additionalProp1) { return new ErrorDetails(type, code, message,
 * additionalProp1); }
 * 
 * }
 */