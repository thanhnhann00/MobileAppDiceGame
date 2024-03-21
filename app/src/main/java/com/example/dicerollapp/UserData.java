package com.example.dicerollapp;

public class UserData {
    private String username;
    private String email;
    private String loginMethod;
    private String dateTime;
    private String password;

    public UserData(String email, String password, String loginMethod) {

        this.email = email;
        this.password = password;
        this.loginMethod = loginMethod;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        this.loginMethod = loginMethod;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

