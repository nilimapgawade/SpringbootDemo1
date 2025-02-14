package com.collections.genesys.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.collections.genesys.service.PublicKeyService;

@Service
public class SchedulerImpl {

	@Autowired
	PublicKeyService keyService;

	@Scheduled(fixedRate = 1800000)
	public void fetchJWKSet() {
		keyService.fetchJWKSet("id");
	}

}
