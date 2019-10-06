package com.github.dodivargas.assemblageservice.api.rest.v1.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class RulingRequest {

    @NotNull(message = "Your ruling must have a name")
    @ApiModelProperty(value = "Name for ruling.", required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
