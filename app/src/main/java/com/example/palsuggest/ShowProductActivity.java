package com.example.palsuggest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.Bytes;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowProductActivity extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        String NameOrId=getIntent().getExtras().getString("keyOrName");


        DocumentReference docRef = db.collection("Products").document(NameOrId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Product product = documentSnapshot.toObject(Product.class);
                String name= (String) documentSnapshot.get("name");
                String review= (String) documentSnapshot.get("review");
                String tag= (String) documentSnapshot.get("tag");
                String link= (String) documentSnapshot.get("link");
                int price= Integer.parseInt((String) documentSnapshot.get("price"));
                byte[] bitmapBytes=((Blob)documentSnapshot.get("Image")).toBytes();
                Bitmap bitmap=BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                int suggesterID = 11; //TODO: get real data
                int [] likesIDs=new int[1]; //TODO: get real data
                Product product=new Product(name,review,tag,link,price,bitmap,suggesterID,likesIDs);
                Toast.makeText(getApplicationContext(), "yay1", Toast.LENGTH_LONG).show(); //TODO: del this toast
            }
        });
    }
}