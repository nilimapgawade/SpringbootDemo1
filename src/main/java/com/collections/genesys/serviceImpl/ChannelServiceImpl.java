package com.collections.genesys.serviceImpl;


import com.collections.genesys.repository.ChannelRepository;
import com.collections.genesys.repository.CheckURCRepository;
import com.collections.genesys.repository.InBoundCallHeaderRepository;
import com.collections.genesys.service.ChannelService;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ChannelServiceImpl implements ChannelService{

	private static final Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
	
	@Autowired
	private ChannelRepository repo;
	
	
	
	
	
	public boolean isValidChannel(String channelid) {
	  String  Channel = repo.findChannelIdFromSettings();
	  logger.info("Value of channel id from database: {}", Channel);

		if((channelid != null) && (Channel.equalsIgnoreCase(channelid))) {
			return true;
		}
		return false;
	}
	
	
	
	

	
}
