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
        setupFloatingActBtnShowItem(); //TODO: DEL THIS btn
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

    public void setupFloatingActBtnShowItem()
    {
        FloatingActionButton floatingActBtnAddProd = findViewById(R.id.showItemFloatingActionButton);
        floatingActBtnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Open item was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                openActivity(ShowProductActivity.class,"Test");
            }
        });
    }

    private void openActivity(Class activityClass)
    {
        startActivity(new Intent(this,activityClass));
    }

    private void openActivity(Class activityClass,String productName)
    {
        Intent mIntent = new Intent(this,activityClass);
        Bundle mBundle = new Bundle();
        mBundle.putString("productName", productName);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
}