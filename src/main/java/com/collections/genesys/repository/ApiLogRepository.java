package com.collections.genesys.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.collections.genesys.Entity.ApiLogEntity;

public interface ApiLogRepository extends JpaRepository<ApiLogEntity, Long> {

}
