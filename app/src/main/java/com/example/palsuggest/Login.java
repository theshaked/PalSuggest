package com.example.palsuggest;

public class Login {


    private String username;
    private String password;

    public Login(String usernameInput, String passwordInput) {
        username=usernameInput;
        password=passwordInput;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
