package com.example.palsuggest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.palsuggest.EditTextValidator.IsPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsUsernameValid;

public class LoginActivity extends AppCompatActivity {

    Login login=new Login();
    String username;
    EditText editTextUsername;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupButtonLogin();
        setupButtonSignup();
        editTextUsername=findViewById(R.id.editTextUsername);
        editTextPassword=findViewById(R.id.editTextPassword);
    }


    public void setupButtonLogin()
    {
        Button buttonLogin = findViewById(R.id.btnSignup);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsUserInputValid())
                {
                    login.LoginAttempt();
                    if (login.LoginAttemptSuccessful)
                    {
                        Toast.makeText(getApplicationContext(), "Login was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                        Intent intentMainActivity = new Intent(v.getContext(), MainActivity.class);
                        username=editTextUsername.getText().toString();
                        intentMainActivity.putExtra("username", username);
                        startActivity(intentMainActivity);
                    }
                    else
                    {
                        login.getLoginErrors();
                        SetErrorsOnEditTexts();
                    }
                }
            }
        });
    }

    public void setupButtonSignup()
    {
        Button buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Signup was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                startActivity(new Intent(v.getContext(), SignupActivity.class));

            }
        });
    }


    private boolean IsUserInputValid() //user name and passward is valid
    {
            return IsUsernameValid(editTextUsername) && IsPasswordValid(editTextPassword);
    }



    private void SetErrorsOnEditTexts()
    {
    }
}