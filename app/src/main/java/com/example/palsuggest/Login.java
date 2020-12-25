package com.example.palsuggest;

public class Login {


    public boolean LoginAttemptSuccessful = false;
    public int LoginAttemptsCounter = 0;

    public void LoginAttempt()
    {
        if (isUserNameExists() && isPasswordMatchUser())
        {
            LoginAttemptSuccessful=true;
        }
        else
        {
            LoginAttemptsCounter += 1; //TODO:Max the Attempts to 3 for a one min cooldown
        }
    }

    private boolean isPasswordMatchUser()//TODO:DB logic
    {
        return true;
    }

    private boolean isUserNameExists()//TODO:DB logic
    {
        return true;
    }

    public void getLoginErrors()//TODO:set un enum status from DB
    {
    }
}
