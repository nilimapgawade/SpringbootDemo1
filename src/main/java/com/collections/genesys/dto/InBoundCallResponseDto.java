package com.collections.genesys.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InBoundCallResponseDto {

	@JsonProperty(value = "TOS")
	private String totalOutstandingAmount;
	@JsonProperty(value = "EmailID")
	private String emailID;
	@JsonProperty(value = "NTID")
	private String collectorCode;

	public String getTotalOutstandingAmount() {
		return totalOutstandingAmount;
	}

	public void setTotalOutstandingAmount(String totalOutstandingAmount) {
		this.totalOutstandingAmount = totalOutstandingAmount;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getCollectorCode() {
		return collectorCode;
	}

	public void setCollectorCode(String collectorCode) {
		this.collectorCode = collectorCode;
	}

	public String getDailerKey() {
		return dailerKey;
	}

	public void setDailerKey(String dailerKey) {
		this.dailerKey = dailerKey;
	}

	@JsonProperty(value = "DialerKey")
	private String dailerKey;

}
