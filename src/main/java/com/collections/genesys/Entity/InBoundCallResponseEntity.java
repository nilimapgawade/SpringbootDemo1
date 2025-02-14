package com.collections.genesys.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "V_INBOUND_CALL")
public class InBoundCallResponseEntity {

	@Id
	@Column(name = "Acc_No")
	private String accountNO;
	@Column(name = "TOS")
	private String totalOutstandingAmount;
	@Column(name = "Email_Id")
	private String emailID;
	@Column(name = "NTID")
	private String collectorCode;
	@Column(name = "Dailer_Key")
	private String dailerKey;

	public String getAccountNO() {
		return accountNO;
	}

	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
	}

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

}
