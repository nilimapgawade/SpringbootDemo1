package com.collections.genesys.serviceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collections.genesys.Entity.InBoundCallHeaderEntity;
import com.collections.genesys.dto.InBoundCallHeaderDto;
import com.collections.genesys.dto.InBoundCallRequestDto;
import com.collections.genesys.repository.InBoundCallHeaderRepository;
import com.collections.genesys.service.InBoundCallHeaderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InBoundCallHeaderServiceImpl implements InBoundCallHeaderService {
    
    private static final Logger logger = LoggerFactory.getLogger(InBoundCallHeaderServiceImpl.class);
    
    @Autowired
    private InBoundCallHeaderRepository repository;

    public void saveInBoundCallHeader(InBoundCallHeaderDto inBoundCallHeader, InBoundCallRequestDto callRequest, String szStatus, Object szResponseBody)
 {
        try {
            InBoundCallHeaderEntity entity = new InBoundCallHeaderEntity();
            
            entity.setSzorgcoce("001");
            entity.setSZSERVICENAME("InBoundCallService");
            entity.setDTREQUEST(LocalDateTime.now());
            entity.setSZCHANNEL_ID(inBoundCallHeader.getChannelId());
            entity.setUrc(inBoundCallHeader.getUrc());
            entity.setSZREQUESTBODY(callRequest.getAccountNo());
            entity.setSZSTATUS(szStatus);
            
            
            if (szResponseBody != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBodyJson = objectMapper.writeValueAsString(szResponseBody);
                entity.setSZRESPONSEBODY(responseBodyJson);
            } else {
                entity.setSZRESPONSEBODY(null);
            }
            
            entity.setSzclienttimestamp(inBoundCallHeader.getClientTimestamp());
            
            // Logging entity data for debugging
            logger.info("Saving entity: {}", entity);
            
            // Save the entity
            InBoundCallHeaderEntity savedEntity = repository.save(entity);
            logger.info("Entity saved with ID: {}", savedEntity.getId());
            
        } catch (Exception e) {
            logger.error("Error while saving entity: ", e);
            throw new RuntimeException("Error saving InBoundCallHeader", e);
        }
    }
}

