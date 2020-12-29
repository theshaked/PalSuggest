package com.example.palsuggest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.palsuggest.EditTextValidator.IsConfirmPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsEmailValid;
import static com.example.palsuggest.EditTextValidator.IsPasswordValid;
import static com.example.palsuggest.EditTextValidator.IsUsernameValid;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    EditText editTextEmail;

    Singup singup;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String Key_USERNAME = "username";
    private static final String Key_PASSWORD = "password";
    private static final String Key_EMAIL = "email";
    private static final String Key_LIKES = "likes";
    private static final String Key_FRIENDS = "friends";
    private static final String Key_ADMIN = "admin";


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
                    singup=new Singup(
                            editTextUsername.getText().toString(),
                            editTextPassword.getText().toString(),
                            editTextEmail.getText().toString());
                    SingupAttempt();
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

    public void SingupAttempt()
    {
            Map<String,Object> newUser = new HashMap<>();
            newUser.put(Key_USERNAME,singup.getUsername());
            newUser.put(Key_PASSWORD,singup.getPassword());
            newUser.put(Key_EMAIL,singup.getEmail());
            newUser.put(Key_LIKES, Arrays.asList());
            newUser.put(Key_FRIENDS, Arrays.asList());
            newUser.put(Key_ADMIN, false);

        db.collection("Users").document(singup.getUsername()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot UserDocument = task.getResult();
                    if (UserDocument.exists())
                    {
                        Toast.makeText(getApplicationContext(),
                                "Username already exists please choose another!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else { RegisterNewUserToDB(newUser); }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
                    return;
                }
            }});
    }

    private void RegisterNewUserToDB(Map<String, Object> userEntry) {
        db.collection("Users").document(singup.getUsername()).set(userEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        finish();
                        Toast.makeText(getApplicationContext(), "Thank you for signing up!", Toast.LENGTH_LONG).show();
                    }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
                    }});

    }
}