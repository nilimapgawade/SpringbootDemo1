package com.collections.genesys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.collections.genesys.dto.ApiLogDto;


public interface ApiLogService {
	
	static final Logger logger = LoggerFactory.getLogger(ApiLogService.class);

	
	boolean logApiRequestResponse(ApiLogDto request) throws Exception;

	public boolean isValidUrc(String SZURC);
	
	
}
