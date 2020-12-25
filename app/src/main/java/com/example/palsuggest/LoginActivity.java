package com.example.palsuggest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                startActivity(new Intent(v.getContext(), Signup.class));

            }
        });
    }


    private boolean IsUserInputValid() //user name and passward is valid
    {
            return IsUsernameValid(editTextUsername) && IsPasswordValid(editTextPassword);
    }

    private boolean IsUsernameValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(getString(R.string.validUsername)))
        {
            editText.setError("שם המשתמש חייב להתחיל באות לטינית ולהכיל בין 4 ל20 מספרים ואותיות לטיניות");
            return false;
        }
        return true;
    }

    private boolean IsPasswordValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(getString(R.string.validPassword)))
        {
            editText.setError("סיסמה חייבת להכיל לפחות 8 אותיות ומספרים ולפחות מספר ואות לטינית אחת");
            return false;
        }
        return true;
    }

    private boolean UserTextNotEmpty(EditText editText)
    {
        if (editText.getText() == null || (editText.getText().toString().isEmpty()))
        {
            editText.setError("בבקשה הכנס את " + editText.getHint());
            return false;
        }
        return true;
    }

    private void SetErrorsOnEditTexts()
    {
    }
}