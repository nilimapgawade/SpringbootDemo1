package com.collections.genesys.service;

import com.collections.genesys.dto.InBoundCallRequestDto;
import com.collections.genesys.dto.InBoundCallResponseDto;

public interface InBoundCallService {

	InBoundCallResponseDto fetchAccountData(InBoundCallRequestDto callRequest);

}
