package com.example.soundsight;

public class User {
    private String email;
    private String impairmentType;

    // Default constructor (no arguments)
    public User() {
        // Default values can be set here if needed
        this.email = "";
        this.impairmentType = "";
    }

    // Parameterized constructor
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
