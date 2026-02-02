package com.example.hotel_system.enumeration;

public enum EnumRole {
    USER, ADMIN;
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
