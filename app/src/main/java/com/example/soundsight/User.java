package com.example.soundsight;

public class User {
    private String email;
    private String impairmentType;

    public User(String email, String impairmentType) {
        this.email = email;
        this.impairmentType = impairmentType;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getImpairmentType() { return impairmentType; }
    public void setImpairmentType(String impairmentType) { this.impairmentType = impairmentType; }
}
