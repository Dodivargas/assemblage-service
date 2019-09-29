package com.github.dodivargas.assemblageservice.api.rest.v1.request;

import javax.validation.constraints.NotNull;

public class RulingRequest {

    @NotNull(message = "Your ruling must have a name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
