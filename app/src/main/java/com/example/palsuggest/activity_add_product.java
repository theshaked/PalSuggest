package com.example.palsuggest;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.palsuggest.EditTextValidator.IsUserLinkValid;
import static com.example.palsuggest.EditTextValidator.IsUserPriceValid;
import static com.example.palsuggest.EditTextValidator.IsUserTextValid;
import static com.example.palsuggest.MainActivity.activeUser;
import static com.google.firebase.firestore.Blob.fromBytes;

public class activity_add_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Bitmap bitmap;
    byte[] byteArrayImage;
    boolean ImageValid=false;

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
    private static final String Key_IMAGE = "Image";
    private static final String Key_LIKES = "likes";
    private static final String Key_SUGGESTER = "suggester";

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

                boolean prodNameValid = IsUserTextValid(prodName);
                boolean prodReviewValid = IsUserTextValid(prodReview);
                boolean prodLinkValid = IsUserLinkValid(prodLink);
                boolean prodPriceValid = IsUserPriceValid(prodPrice);
                if (!ImageValid){
                uploadImageView.setBackgroundResource(android.R.drawable.ic_dialog_alert);
                }

                if (prodNameValid && prodReviewValid && prodLinkValid && prodPriceValid && ImageValid)
                {
                    Map<String,Object> product = new HashMap<>();
                    product.put(Key_NAME,prodName.getText().toString());
                    product.put(Key_REVIEW,prodReview.getText().toString());
                    product.put(Key_TAG,spinnerTags.getSelectedItem().toString());
                    product.put(Key_LINK,prodLink.getText().toString());
                    product.put(Key_PRICE,prodPrice.getText().toString());
                    product.put(Key_IMAGE,fromBytes(byteArrayImage));
                    product.put(Key_LIKES, Arrays.asList());
                    product.put(Key_SUGGESTER, activeUser.getUsername());

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
            GetImageNormalisdeSize();
            if (ImageValid){
                uploadImageView.setImageBitmap(bitmap);
                uploadImageView.setBackgroundResource(R.drawable.edit_text_background);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void GetImageNormalisdeSize() {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            bitmap = CropImageSquare(bitmap);
            int imageQualityPercent=100;
            do
            {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, imageQualityPercent, stream);
                byteArrayImage = stream.toByteArray();
                imageQualityPercent -= Math.sqrt(imageQualityPercent);
            } while(byteArrayImage.length > 1000000);
        } catch (IOException e) {
            e.printStackTrace();
            ImageValid=false;
            Toast.makeText(getApplicationContext(), "Opposite! Image upload failed :C ", Toast.LENGTH_LONG).show();
        }
        ImageValid=true;
    }

    private Bitmap CropImageSquare(Bitmap srcBmp) {

        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return dstBmp;
    }


}