package com.github.dodivargas.assemblageservice.api.rest.v1.response;

public class RulingResultResponse {

    private String name;
    private Boolean aproved;
    private Boolean openedForVotes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAproved() {
        return aproved;
    }

    public void setAproved(Boolean aproved) {
        this.aproved = aproved;
    }

    public Boolean getOpenedForVotes() {
        return openedForVotes;
    }

    public void setOpenedForVotes(Boolean openedForVotes) {
        this.openedForVotes = openedForVotes;
    }
}
