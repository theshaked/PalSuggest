package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.palsuggest.EditTextValidator.IsConfirmPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsEmailValid;
import static com.example.palsuggest.EditTextValidator.IsPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsUsernameValid;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    EditText editTextEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setupBtnSignup();
        editTextUsername=findViewById(R.id.editTextUsername);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextConfirmPassword=findViewById(R.id.editTextConfirmPassword);
        editTextEmail=findViewById(R.id.editTextEmail);
    }

    public void setupBtnSignup()
    {
        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsUserInputValid())
                {
                    Singup singup=new Singup(
                            editTextUsername.getText().toString(),
                            editTextPassword.getText().toString(),
                            editTextEmail.getText().toString());
                    singup.SingupAttempt();
                    if (singup.LoginAttemptSuccessful)
                    {
                        Toast.makeText(getApplicationContext(), "Singup was Successful", Toast.LENGTH_LONG).show(); //TODO: del this toast
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed Singup", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean IsUserInputValid() {

        boolean isUsernameValid=IsUsernameValid(editTextUsername);
        boolean isPasswordValid=IsPasswordValid(editTextPassword);
        boolean isConfirmPasswordValid= IsConfirmPasswordValid(editTextPassword,editTextConfirmPassword);
        boolean isEmailValid=IsEmailValid(editTextEmail);
        return isUsernameValid && isPasswordValid && isConfirmPasswordValid && isEmailValid;
    }
}