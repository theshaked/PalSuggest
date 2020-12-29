package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFloatingActBtnAddProd();
    }

    public void setupFloatingActBtnAddProd()
    {
        FloatingActionButton floatingActBtnAddProd = findViewById(R.id.addProdFloatingActionButton);
        floatingActBtnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "+ was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                openActivity(activity_add_product.class);
            }
        });
    }

    private void openActivity(Class activityClass)
    {
        startActivity(new Intent(this,activityClass));
    }
}