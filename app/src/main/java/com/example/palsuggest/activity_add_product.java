package com.example.palsuggest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class activity_add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    ImageView uploadImageView;
    EditText prodName;
    EditText prodReview;
    Spinner spinnerTags;
    EditText prodPrice;
    EditText prodLink;
    Button addNewProdBtn;

    private static final String Key_NAME = "name";
    private static final String Key_REVIEW = "review";
    private static final String Key_LINK = "link";
    private static final String Key_PRICE = "price";
    private static final String Key_TAG = "tag";

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


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
            public void onClick(View v)
            {

                boolean prodNameValid=IsUserTextValid(prodName);
                boolean prodReviewValid=IsUserTextValid(prodReview);
                boolean prodLinkValid=IsUserLinkValid(prodLink);
                boolean prodPriceValid=IsUserPriceValid(prodPrice);

                if (prodNameValid && prodReviewValid && prodLinkValid && prodPriceValid)
                {

                    Map<String,Object> product = new HashMap<>();
                    product.put(Key_NAME,prodName.getText().toString());
                    product.put(Key_REVIEW,prodReview.getText().toString());
                    product.put(Key_TAG,spinnerTags.getSelectedItem().toString());
                    product.put(Key_LINK,prodLink.getText().toString());
                    product.put(Key_PRICE,prodPrice.getText().toString());
                    //TODO:add bitmap (max size 1mb)

                    db.collection("Products").document(String.valueOf(prodName.getText())).set(product)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid)
                                    {
                                        Toast.makeText(getApplicationContext(), "Thank you for entering a new suggestion!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }})
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                    {
                                        Toast.makeText(getApplicationContext(), "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
                                    }});
                }
            }
        });
    }

    private boolean IsUserTextValid(EditText editText)
    {
        UserTextNotEmpty(editText);

        if (!editText.getText().toString().matches(getString(R.string.atLeastOneLetter)))
        {
            editText.setError(editText.getHint()+" חייב להכיל לפחות אות אחת ");
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

    private boolean IsUserPriceValid(EditText editTextPrice)
    {
        UserTextNotEmpty(editTextPrice);

        if (!editTextPrice.getText().toString().matches(getString(R.string.positiveNumeric)))
        {
            editTextPrice.setError(editTextPrice.getHint()+" חייב להכיל סכום מספרי חיובי");
            return false;
        }
        return true;
    }

    private boolean IsUserLinkValid(EditText editTextLink)
    {
        if (UserTextNotEmpty(editTextLink) && !Patterns.WEB_URL.matcher(editTextLink.getText()).matches()) //Valid text with invalid link
        {
            editTextLink.setError("כתובת המוצר אינה חוקית");
            return false;
        }
        return true;
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
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
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