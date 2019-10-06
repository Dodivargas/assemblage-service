package com.github.dodivargas.assemblageservice.api.rest.v1.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull(message = "RuleId can not null")
    @ApiModelProperty(value = "RulingId for vote.", required = true)
    private String rulingId;
    @NotNull(message = "TaxId can not null")
    @ApiModelProperty(value = "TaxId for user want to vote.", required = true)
    private String taxId;
    @NotNull(message = "Vote of user.")
    @ApiModelProperty(value = "Name for ruling.", required = true)
    private Boolean inFavor;

    public String getRulingId() {
        return rulingId;
    }

    public void setRulingId(String rulingId) {
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
