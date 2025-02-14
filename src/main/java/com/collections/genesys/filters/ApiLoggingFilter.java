/*
 * package com.collections.genesys.filters;
 * 
 * import java.io.IOException; import java.time.Duration; import
 * java.time.Instant; import java.time.LocalDateTime; import java.util.List;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory;
 * 
 * import org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter; import
 * org.springframework.web.util.ContentCachingRequestWrapper; import
 * org.springframework.web.util.ContentCachingResponseWrapper;
 * 
 * import com.collections.genesys.Entity.ApiLog; import
 * com.collections.genesys.repository.ApiLogRepository;
 * 
 * import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException;
 * import jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * @Component public class ApiLoggingFilter extends OncePerRequestFilter {
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(ApiLoggingFilter.class);
 * 
 * private final ApiLogRepository logRepository;
 * 
 * private final List<String> excludedUrls = List.of("/dcore/v1/generatetoken");
 * 
 * // private final ApiLogService apiLogService;
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * 
 * ContentCachingRequestWrapper requestWrapper = new
 * ContentCachingRequestWrapper(request); ContentCachingResponseWrapper
 * responseWrapper = new ContentCachingResponseWrapper(response);
 * 
 * // filterChain.doFilter(requestWrapper, responseWrapper);
 * 
 * String requestBody = new String(requestWrapper.getContentAsByteArray());
 * String responseBody = new String(responseWrapper.getContentAsByteArray());
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 1 "
 * );
 * 
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 2 "
 * );
 * 
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 5");
 * ApiLog apilog = new ApiLog();
 * apilog.setEndpoint(request.getRequestURL().toString());
 * apilog.setHeaders(request.getHeaderNames().asIterator().toString());
 * apilog.setUrc(request.getHeader("URC"));// need to check // String headers =
 * request.getHeaderNames().asIterator().toString();
 * apilog.setMethod(request.getMethod()); apilog.setRequestBody(requestBody);
 * apilog.setResponseBody(responseBody);
 * apilog.setResponseStatus(response.getStatus());
 * apilog.setTimestamp(LocalDateTime.now()); // Save current timestamp
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 6 "
 * ); // Start time tracking Instant startTime = Instant.now();
 * 
 * // End time tracking Instant endTime = Instant.now(); long timeTaken =
 * Duration.between(startTime, endTime).toMillis(); // Time in milliseconds
 * apilog.setTimeTaken(timeTaken); // Save the time taken
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 7 "
 * ); // Save log entry to the repository logRepository.save(apilog);
 * 
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 8 "
 * );
 * 
 * responseWrapper.copyBodyToResponse();
 * 
 * // filterChain.doFilter(request, response);
 * 
 * logger.info("Inside doFilterInternal JwtAuthenticationLoggingFilter call 9 "
 * );
 * 
 * }
 * 
 * public ApiLoggingFilter(ApiLogRepository logRepository) { super();
 * this.logRepository = logRepository;
 * 
 * }
 * 
 * }
 */