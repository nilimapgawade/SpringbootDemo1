package com.collections.genesys.service;

import jakarta.servlet.http.HttpServletRequest;
public interface DecodeTokenService {

	boolean validateToken(HttpServletRequest request) throws Exception;

}
