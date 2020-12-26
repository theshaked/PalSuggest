package com.example.palsuggest;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.palsuggest.Login.isUserNameExists;

public class Singup {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public boolean LoginAttemptSuccessful = false;
    private String username;
    private String password;
    private String email;
    private static final String Key_USERNAME = "username";
    private static final String Key_PASSWORD = "password";
    private static final String Key_EMAIL = "email";

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
            Map<String,Object> newUser = new HashMap<>();
            newUser.put(Key_USERNAME,username);
            newUser.put(Key_PASSWORD,password);
            newUser.put(Key_EMAIL,email);

            db.collection("Users").document(username).set(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Toast.makeText(getApplicationContext(), "Thank you for entering a new suggestion!", Toast.LENGTH_LONG).show();
                            //finish();
                            LoginAttemptSuccessful=true; //TODO:add db logic here
                        }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            LoginAttemptSuccessful=false;
                            //Toast.makeText(getApplicationContext(), "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
                        }});
            
        }
    }

}
