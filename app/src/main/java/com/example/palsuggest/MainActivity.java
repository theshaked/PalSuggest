package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        setupCommunityBtn();
        setupFriendsBtn();
        setupYouBtn();
    }

    private void setupCommunityBtn()
    {
        Button communityBtn = findViewById(R.id.communityBtn);
        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "communityBtn was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                openActivity(ProductsBrowserActivity.class,"COMMUNITY","filterSuggester");
            }
        });
    }

    private void setupFriendsBtn()
    {
        Button friendsBtn = findViewById(R.id.friendsBtn);
        friendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "friendsBtn was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                openActivity(ProductsBrowserActivity.class,"FRIENDS","filterSuggester");
            }
        });
    }

    private void setupYouBtn()
    {
        Button youBtn = findViewById(R.id.youBtn);
        youBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "youBtn was Clicked", Toast.LENGTH_LONG).show(); //TODO: del this toast
                openActivity(ProductsBrowserActivity.class,"MY","filterSuggester");
            }
        });
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
                openActivity(ShowProductActivity.class,"Test","productName");
            }
        });
    }

    private void openActivity(Class activityClass)
    {
        startActivity(new Intent(this,activityClass));
    }

    private void openActivity(Class activityClass,String Filter ,String filterName)
    {
        Intent mIntent = new Intent(this,activityClass);
        Bundle mBundle = new Bundle();
        mBundle.putString(filterName, Filter);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }

}