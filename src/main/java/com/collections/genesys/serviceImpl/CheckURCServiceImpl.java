package com.collections.genesys.serviceImpl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collections.genesys.repository.CheckURCRepository;
import com.collections.genesys.service.CheckURCService;

@Service
public class CheckURCServiceImpl implements CheckURCService {
	
	@Autowired
	private CheckURCRepository checkurc;
     
	
	public boolean isValidUrc(String SZURC) {
	    if (SZURC != null) {
	        // Trim any leading/trailing whitespaces
	        SZURC = SZURC.trim();
	        
	        // Regex to allow alphanumeric characters only
	        if (Pattern.matches("^[a-zA-Z0-9]+$", SZURC)) {
	            
	            // Check if the SZURC already exists in the repository
	            if (checkurc.existsByUrc(SZURC)) {
	                return false; // If exists, return false
	            }

	            // If regex matches and it's not in the repository, return true
	            return true;
	        }
	    }

	    return false; // Return false if SZURC is null or doesn't match regex
	}

}
