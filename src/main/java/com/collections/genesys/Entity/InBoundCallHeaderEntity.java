package com.collections.genesys.Entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="COL_LOG_WS_SERVICE_REQ")
public class InBoundCallHeaderEntity {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generated ID
    @Column(name = "id")
    private Long id;
	
	@Column(name="szorgcoce")
	private String szorgcoce;
	
	@Column(name="SZSERVICENAME")
	private String SZSERVICENAME;

	@Column(name="DTREQUEST")
	private LocalDateTime DTREQUEST;

	@Column(name="SZCHANNEL_ID")
	private String SZCHANNEL_ID;

	@Column(name="SZURC")
	private String urc;

	@Lob
	@Column(name="SZREQUESTBODY")
	private String SZREQUESTBODY;
	
	@Column(name="SZSTATUS")
	private String SZSTATUS;
	
	@Lob
	@Column(name="SZRESPONSEBODY")
	private String SZRESPONSEBODY;
	
	@Column(name = "SZCLIENTTIMESTAMP")
    private String szclienttimestamp;
	

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getSzorgcoce() {
		return szorgcoce;
	}

	public void setSzorgcoce(String szorgcoce) {
		this.szorgcoce = szorgcoce;
	}

	public String getSZSERVICENAME() {
		return SZSERVICENAME;
	}

	public void setSZSERVICENAME(String sZSERVICENAME) {
		SZSERVICENAME = sZSERVICENAME;
	}

	public LocalDateTime getDTREQUEST() {
		return DTREQUEST;
	}

	public void setDTREQUEST(LocalDateTime dTREQUEST) {
		DTREQUEST = dTREQUEST;
	}

	public String getSZCHANNEL_ID() {
		return SZCHANNEL_ID;
	}

	public void setSZCHANNEL_ID(String sZCHANNEL_ID) {
		SZCHANNEL_ID = sZCHANNEL_ID;
	}

	/*public String getSZURC() {
		return SZURC;
	}

	public void setSZURC(String sZURC) {
		SZURC = sZURC;
	}*/

	 public String getUrc() {
	        return urc;
	    }

	    public void setUrc(String urc) {
	        this.urc = urc;
	    }
	    
	public String getSZREQUESTBODY() {
		return SZREQUESTBODY;
	}

	public void setSZREQUESTBODY(String sZREQUESTBODY) {
		SZREQUESTBODY = sZREQUESTBODY;
	}

	public String getSZSTATUS() {
		return SZSTATUS;
	}

	public void setSZSTATUS(String sZSTATUS) {
		SZSTATUS = sZSTATUS;
	}

	public String getSZRESPONSEBODY() {
		return SZRESPONSEBODY;
	}

	public void setSZRESPONSEBODY(String sZRESPONSEBODY) {
		SZRESPONSEBODY = sZRESPONSEBODY;
	}

	public String getSzclienttimestamp() {
	        return szclienttimestamp;
	    }

	 public void setSzclienttimestamp(String szclienttimestamp) {
	        this.szclienttimestamp = szclienttimestamp;
	    }
	
	
}
