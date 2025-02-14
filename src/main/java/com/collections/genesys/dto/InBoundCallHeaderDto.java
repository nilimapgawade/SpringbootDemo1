package com.collections.genesys.dto;

public class InBoundCallHeaderDto {
	private String authorization;
    private String contentType;
    private String channelId;
    private String clientTimestamp;
    private String urc;
    
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getClientTimestamp() {
		return clientTimestamp;
	}
	public void setClientTimestamp(String clientTimestamp) {
		this.clientTimestamp = clientTimestamp;
	}
	public String getUrc() {
		return urc;
	}
	public void setUrc(String urc) {
		this.urc = urc;
	}
    
    
	

}
