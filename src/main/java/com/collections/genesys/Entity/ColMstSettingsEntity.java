package com.collections.genesys.Entity;

import com.collections.genesys.response.ColMstSettingsPK;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;



@Entity
@Table(name="COL_MST_SETTINGS")
@IdClass(ColMstSettingsPK.class)
public class ColMstSettingsEntity {
	
	@Id
	@Column(name = "szorgcode")
    private String szorgcode;
	
	 @Id
	 @Column(name = "szconditiontype")
	 private String szconditiontype;
	 
	 @Column(name = "szcondition")
	 private String szcondition;
	 
	 @Column(name = "szvalue")
	 private String szvalue;
	 
	 
	 public String getSzorgcode() {
	        return szorgcode;
	    }

	    public void setSzorgcode(String szorgcode) {
	        this.szorgcode = szorgcode;
	    }
	    
	    public String getSzconditiontype() {
	        return szconditiontype;
	    }

	    public void setSzconditiontype(String szconditiontype) {
	        this.szconditiontype = szconditiontype;
	    }

	    public String getSzcondition() {
	        return szcondition;
	    }

	    public void setSzcondition(String szcondition) {
	        this.szcondition = szcondition;
	    }
	    
	    public String getSzvalue() {
	        return szvalue;
	    }

	    public void setSzvalue(String szvalue) {
	        this.szvalue = szvalue;
	    }
	    
	    
}
