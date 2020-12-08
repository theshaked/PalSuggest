package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class activity_add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    ImageView uploadImageView;
    Uri imageUri;
    EditText prodName;
    EditText prodReview;
    Spinner spinnerTags;
    EditText prodPrice;
    EditText prodLink;
    Button addNewProdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        prodName=findViewById(R.id.prodName);
        prodReview=findViewById(R.id.prodReview);
        spinnerTags =findViewById(R.id.prodTag);
        prodPrice=findViewById(R.id.prodPrice);
        prodLink=findViewById(R.id.prodLink);
        addNewProdBtn=findViewById(R.id.addNewProdBtn);

        setupSpinnerTags();
        setupuploadImageView();
        setupAddNewProdBtn();
    }

    private void setupAddNewProdBtn() {
        addNewProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prodName.requestFocus();
                prodName.setError("Your Error Message");

                String dataText="prodName="+prodName.getText()+"\n"+
                        "prodReview="+prodReview.getText()+"\n"+
                        "spinnerTags="+spinnerTags.getSelectedItem().toString()+"\n"+
                        "prodPrice="+prodPrice.getText()+"\n"+
                        "prodLink="+prodLink.getText()+"\n"+
                        "imageUri="+imageUri.getPath()+"\n";

                Toast.makeText(getApplicationContext(), dataText, Toast.LENGTH_LONG).show(); //TODO: del this toast
                //openActivity(activity_add_product.class);
            }
        });
    }

    public void setupSpinnerTags()
    {
        ArrayAdapter<CharSequence> adapterTags=ArrayAdapter.createFromResource(this,R.array.ProductTags, android.R.layout.simple_spinner_item);
        adapterTags.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTags.setAdapter(adapterTags);
        spinnerTags.setOnItemSelectedListener(this);
    }

    public void setupuploadImageView()
    {
        uploadImageView = findViewById(R.id.uploadImageView);
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryPickImage();
            }
        });
    }

    private void GalleryPickImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            uploadImageView.setImageURI(imageUri);
            uploadImageView.setBackgroundResource(R.drawable.edit_text_background);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}