package com.example.palsuggest;

import static com.example.palsuggest.Login.isUserNameExists;

public class Singup {

    public boolean LoginAttemptSuccessful = false;
    private String username;
    private String password;
    private String email;

    public Singup(String usernameInput, String passwordInput, String emailInput)
    {
        username=usernameInput;
        password=passwordInput;
        email=emailInput;
    }

    public void SingupAttempt()
    {
        if (isUserNameExists(username))
        {
            LoginAttemptSuccessful=true; //TODO:add db logic here
        }
    }

}
