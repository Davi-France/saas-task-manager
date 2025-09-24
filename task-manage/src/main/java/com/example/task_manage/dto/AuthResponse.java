package com.example.task_manage.dto;

public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token; // <<--- atribuição necessária
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
