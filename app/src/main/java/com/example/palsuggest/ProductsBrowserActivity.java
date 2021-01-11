package com.example.palsuggest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.palsuggest.MainActivity.activeUser;

public class ProductsBrowserActivity extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_browser);

        String filterSuggester=getIntent().getExtras().getString("filterSuggester");
        String filterTag=getIntent().getExtras().getString("tagFilter");

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db.collection("Products")
                .whereEqualTo("tag", filterTag) //TODO: get tag from getExtra
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Product> products=new ArrayList<Product>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                products.add(GetProductFromDB((DocumentSnapshot) document));
                            }
                            if (filterSuggester.equals("FRIENDS"))
                            {
                                products.removeIf(product ->
                                        !activeUser.getFriends().contains(product.getSuggesterName()) && //any products not by your friends
                                        Collections.disjoint(product.getLikesNames(), activeUser.getFriends())); //any products without friends likes
                            }
                            else if (filterSuggester.equals("MY"))
                            {
                                products.removeIf(product ->
                                         !product.getSuggesterName().equals(activeUser.getUsername()) && //product that active user isn't the suggester
                                         !activeUser.getLikes().contains(product.getName())); //product that active user didn't like
                            }
                            products.sort((o1, o2) -> o2.getLikesNames().size() - o1.getLikesNames().size());
                            ProductAdapter productAdapter = new ProductAdapter(products,ProductsBrowserActivity.this);
                            recyclerView.setAdapter(productAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "loading failed :C", Toast.LENGTH_LONG).show(); //TODO: del this toast
                        }
                    }
                });


    }


    private Product GetProductFromDB(DocumentSnapshot documentSnapshot)
    {
        String name= (String) documentSnapshot.get("name");
        String review= (String) documentSnapshot.get("review");
        String tag= (String) documentSnapshot.get("tag");
        String link= (String) documentSnapshot.get("link");
        int price= Integer.parseInt((String) documentSnapshot.get("price"));
        byte[] bitmapBytes=((Blob)documentSnapshot.get("Image")).toBytes();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        String suggesterName = (String) documentSnapshot.get("suggester");
        List<String> likesNames=(List<String>) documentSnapshot.get("likes");
        return new Product(name,review,tag,link,price,bitmap,suggesterName,likesNames);
    }


}