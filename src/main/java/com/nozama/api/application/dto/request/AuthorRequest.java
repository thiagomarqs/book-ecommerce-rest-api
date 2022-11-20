package com.nozama.api.application.dto.request;

public class AuthorRequest {

    private String name;

    public AuthorRequest() {}
    public AuthorRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
