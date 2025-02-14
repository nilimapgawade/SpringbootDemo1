package com.collections.genesys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collections.genesys.Entity.ApiLogEntity;

@Repository
public interface CheckURCRepository extends JpaRepository<ApiLogEntity, Long> {
	boolean existsByUrc(String SZURC);
}
