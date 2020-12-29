package com.example.palsuggest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.palsuggest.EditTextValidator.IsPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsUsernameValid;
import static com.example.palsuggest.MainActivity.activeUser;

public class LoginActivity extends AppCompatActivity {

    Login login;
    EditText editTextUsername;
    EditText editTextPassword;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsUserInputValid())
                {
                    login=new Login(editTextUsername.getText().toString(),editTextPassword.getText().toString());
                    LoginAttempt(v);
                }
            }
        });
    }

    private void LoginAttempt(View v) {
        db.collection("Users").document(login.getUsername()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot UserDocument = task.getResult();
                            if (UserDocument.exists()){
                                if (((String) UserDocument.get("password")).equals(login.getPassword()))
                                {
                                    User user=new User(UserDocument.getData());
                                    OpenMainActivity(v,user);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),
                                            "oh..Password doesn't match username.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),
                                        "Username doesn't exist! New User? Sing Up Here!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }});

    }


    private void OpenMainActivity(View v,User user) {
        Intent intentMainActivity = new Intent(v.getContext(), MainActivity.class);
        activeUser=user;
        startActivity(intentMainActivity);
    }

    public void setupButtonSignup()
    {
        Button buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(v.getContext(), SignupActivity.class));
            }
        });
    }

    private boolean IsUserInputValid()
    {
            return IsUsernameValid(editTextUsername) && IsPasswordValid(editTextPassword);
    }
}