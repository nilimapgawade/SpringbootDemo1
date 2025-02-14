package com.collections.genesys.service;

import com.collections.genesys.dto.InBoundCallHeaderDto;
import com.collections.genesys.dto.InBoundCallRequestDto;


public interface InBoundCallHeaderService {

	public void saveInBoundCallHeader(InBoundCallHeaderDto inBoundCallHeader, InBoundCallRequestDto callRequest, String szStatus, Object szResponseBody);

}
