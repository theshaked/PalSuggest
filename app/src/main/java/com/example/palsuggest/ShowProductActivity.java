package com.example.palsuggest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.palsuggest.MainActivity.activeUser;

public class ShowProductActivity extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    Product product;

    TextView productNameText;
    ImageView imageView;
    TextView priceText;
    FloatingActionButton followButton;
    FloatingActionButton likeButton;
    TextView likeCounterText;
    TextView suggesterNameText;
    TextView tagText;
    TextView reviewText;
    FloatingActionButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        String NameOrId=getIntent().getExtras().getString("keyOrName");


        DocumentReference docRef = db.collection("Products").document(NameOrId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                product=GetProductFromDB(documentSnapshot);
                findAllViewsById();
                UpdateActivityWithProductData();
            }
        });
        docRef.get().addOnFailureListener(command ->
        {
            Toast.makeText(getApplicationContext(), "Opposite! Something went wrong please try again later", Toast.LENGTH_LONG).show();
            finish(); //TODO:Go to MAINACTIVITY and not LOGIN
        });
    }

    private void findAllViewsById()
    {
        productNameText=findViewById(R.id.productNameText);
        imageView=findViewById(R.id.imageView);
        priceText=findViewById(R.id.PriceText);
        followButton=findViewById(R.id.followButton);
        likeButton=findViewById(R.id.likeButton);
        likeCounterText=findViewById(R.id.likeCounterText);
        suggesterNameText=findViewById(R.id.suggesterNameText);
        tagText=findViewById(R.id.tagText);
        reviewText=findViewById(R.id.reviewText);
        deleteButton=findViewById(R.id.deleteButton);
    }

    private void UpdateActivityWithProductData()
    {
        setProductNameText();
        setImageView();
        setPriceText();
        setFollowButton();
        setLikeButton();
        setLikeCounterText();
        setSuggesterNameText();
        setTagText();
        setReviewText();
        setDeleteButton();
        //TODO:(we can add un edit button if we want to).
    }

    private void setProductNameText() {
        productNameText.setText(product.getName());
    }

    private void setImageView() {
        imageView.setImageBitmap(product.getImage());
        //TODO: setup link on image
    }

    private void setPriceText() {
        priceText.setText(String.valueOf(product.getPrice())+'₪');
    }

    private void setFollowButton() {
        if (product.getSuggesterName().equals(activeUser.getUsername()))
        {
            followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.DarkGray)));
        }
        else
        {
            if (activeUser.getFriends().contains(product.getSuggesterName()))
                followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.LimeGreen)));
            else
                followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.FloralWhite)));

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activeUser.getFriends().contains(product.getSuggesterName()))
                    {
                        RemoveFriend(product.getSuggesterName());
                    }
                    else
                    {
                        AddFriend(product.getSuggesterName());
                    }
                }
            });
        }
    }

    private void AddFriend(String suggesterName) {

        db.collection("Users").document(activeUser.getUsername()).update("friends", FieldValue.arrayUnion(suggesterName))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(getApplicationContext(), "friend added!", Toast.LENGTH_LONG).show(); //TODO:DEL?
                        activeUser.AddFriend(suggesterName);
                        followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.LimeGreen)));
                    }});
    }

    private void RemoveFriend(String suggesterName) {

        db.collection("Users").document(activeUser.getUsername()).update("friends", FieldValue.arrayRemove(suggesterName))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(getApplicationContext(), "friend removed", Toast.LENGTH_LONG).show(); //TODO:DEL?
                        activeUser.RemoveFriend(suggesterName);
                        followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.FloralWhite)));
                    }});
    }

    private void setLikeButton() {
        //TODO:Disable this button if we are admin user or the prod Suggester
        //TODO:add user id to the prod like arr if clicked
        //TODO:change the image acroding to the follow state (highlight)
    }

    private void setLikeCounterText() {
        likeCounterText.setText(String.valueOf(product.getLikesNames().size()));
    }

    private void setSuggesterNameText() {
        suggesterNameText.setText(product.getSuggesterName());
    }

    private void setTagText() {
        tagText.setText(product.getTag());
    }

    private void setReviewText() {
        reviewText.setText(product.getReview());
    }

    private void setDeleteButton() {
        //TODO:if user is admin or the prod Suggester enable this button
    }


    private Product GetProductFromDB(DocumentSnapshot documentSnapshot)
    {
        String name= (String) documentSnapshot.get("name");
        String review= (String) documentSnapshot.get("review");
        String tag= (String) documentSnapshot.get("tag");
        String link= (String) documentSnapshot.get("link");
        int price= Integer.parseInt((String) documentSnapshot.get("price"));
        byte[] bitmapBytes=((Blob)documentSnapshot.get("Image")).toBytes();
        Bitmap bitmap=BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        String suggesterName = (String) documentSnapshot.get("suggester"); //TODO: get real data
        List<String> likesNames=(List<String>) documentSnapshot.get("likes");
        return new Product(name,review,tag,link,price,bitmap,suggesterName,likesNames);
    }
}