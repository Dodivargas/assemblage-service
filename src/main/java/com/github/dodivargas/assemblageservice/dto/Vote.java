package com.github.dodivargas.assemblageservice.dto;

public class Vote {

    private Integer rulingId;
    private String taxId;
    private Boolean inFavor;

    public Integer getRulingId() {
        return rulingId;
    }

    public void setRulingId(Integer rulingId) {
        this.rulingId = rulingId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Boolean getInFavor() {
        return inFavor;
    }

    public void setInFavor(Boolean inFavor) {
        this.inFavor = inFavor;
    }
}
