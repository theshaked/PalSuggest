package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class activity_add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Spinner spinnerTags =findViewById(R.id.prodTag);
        ArrayAdapter<CharSequence> adapterTags=ArrayAdapter.createFromResource(this,R.array.ProductTags, android.R.layout.simple_spinner_item);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTags.setAdapter(adapterTags);
        spinnerTags.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}