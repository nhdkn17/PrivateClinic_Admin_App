package com.admin.shared.model;

public class OnlineUser {
    private String name;
    private String role;

    public OnlineUser(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public OnlineUser() {
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}

