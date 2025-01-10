package com.kost.models;

public final class UserSession {
    private String username;
    private String password;
    private String userId;

    public UserSession(String username, String password, String userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }
}
