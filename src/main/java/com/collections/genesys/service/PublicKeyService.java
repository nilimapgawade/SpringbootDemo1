package com.collections.genesys.service;

import com.collections.genesys.dto.PublicKeyResponseDto;

public interface PublicKeyService {

	PublicKeyResponseDto fetchJWKSet(String key);

}
