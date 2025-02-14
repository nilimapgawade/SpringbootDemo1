package com.collections.genesys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collections.genesys.Entity.InBoundCallResponseEntity;

@Repository
public interface InBoundCallRepository extends JpaRepository<InBoundCallResponseEntity, String> {

	public InBoundCallResponseEntity findByAccountNO(String accountNO);

}
