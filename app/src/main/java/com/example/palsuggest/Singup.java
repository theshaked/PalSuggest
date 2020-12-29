package com.example.palsuggest;

public class Singup {

    private String username;
    private String password;
    private String email;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    
    public Singup(String usernameInput, String passwordInput, String emailInput)
    {
        username=usernameInput;
        password=passwordInput;
        email=emailInput;
    }

}
