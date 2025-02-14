package com.collections.genesys.response;

import java.io.Serializable;
import java.util.Objects;

public class ColMstSettingsPK implements Serializable {

    private String szorgcode;
    private String szconditiontype;

    // Getters, Setters, Equals, and HashCode

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColMstSettingsPK that = (ColMstSettingsPK) o;
        return Objects.equals(szorgcode, that.szorgcode) && Objects.equals(szconditiontype, that.szconditiontype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(szorgcode, szconditiontype);
    }

}
