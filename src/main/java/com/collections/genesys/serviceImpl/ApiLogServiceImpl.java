package com.collections.genesys.serviceImpl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.collections.genesys.Entity.ApiLogEntity;
import com.collections.genesys.dto.ApiLogDto;

import com.collections.genesys.repository.ApiLogRepository;
import com.collections.genesys.repository.CheckURCRepository;
import com.collections.genesys.repository.CheckURCRepositoryFilter;
import com.collections.genesys.service.ApiLogService;

@Service
public class ApiLogServiceImpl implements ApiLogService {

	private final ApiLogRepository apiLogRepository;

	private final CheckURCRepositoryFilter checkurc;

	private final ModelMapper modelMapper;

	private static final Logger logger = LoggerFactory.getLogger(ApiLogServiceImpl.class);

	public ApiLogServiceImpl(ApiLogRepository apiLogRepository, CheckURCRepositoryFilter checkurc,
			ModelMapper modelMapper) {
		super();
		this.apiLogRepository = apiLogRepository;
		this.checkurc = checkurc;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean logApiRequestResponse(ApiLogDto request) throws Exception {
		// TODO Auto-generated method stub
		ApiLogEntity apilog = new ApiLogEntity();
		apilog.setEndpoint(request.getEndpoint());
		apilog.setHeaders(request.getHeaders());
		apilog.setMethod(request.getMethod());
		apilog.setRequestBody(request.getRequestBody());
		apilog.setResponseBody(request.getResponseBody());
		apilog.setResponseStatus(request.getResponseStatus());
		apilog.setTimestamp(LocalDateTime.now()); // Save current timestamp

		// Start time tracking
		Instant startTime = Instant.now();

		// End time tracking
		Instant endTime = Instant.now();
		long timeTaken = Duration.between(startTime, endTime).toMillis(); // Time in milliseconds
		apilog.setTimeTaken(timeTaken); // Save the time taken

		// Save log entry to the repository
		apiLogRepository.save(apilog);

		return false;
	}

	public ApiLogEntity convertToEntity(ApiLogDto apiDTO) {
		return modelMapper.map(apiDTO, ApiLogEntity.class);
	}

	@Override
	public boolean isValidUrc(String SZURC) {
		if (SZURC != null) {
			// Trim any leading/trailing whitespaces
			SZURC = SZURC.trim();

			// Regex to allow alphanumeric characters only
			if (Pattern.matches("^[a-zA-Z0-9]+$", SZURC)) {

				// Check if the SZURC already exists in the repository
				if (checkurc.existsByUrc(SZURC)) {
					return false; // If exists, return false
				}

				// If regex matches and it's not in the repository, return true
				return true;
			}
		}

		return false; // Return false if SZURC is null or doesn't match regex
	}

}
