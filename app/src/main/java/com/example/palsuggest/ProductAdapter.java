package com.example.palsuggest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<Product> productsData;
    Context context;

    public ProductAdapter(List<Product> products, ProductsBrowserActivity activity) {
        this.productsData = products;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product productsList = productsData.get(position);
        //holder.itemImage.setImageBitmap(productsList.getImage());
        holder.itemImage.setImageBitmap( Bitmap.createScaledBitmap(productsList.getImage(), 100, 100, false));
        holder.itemName.setText(productsList.getName());
        holder.itemPrice.setText(productsList.getPrice()+" ₪ ");
        holder.itemLikes.setText(productsList.getLikesNames().size()+" ♥ ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,productsList.getName()+" was clicked!",Toast.LENGTH_SHORT).show();
                //TODO: Open ShowProductActivity of this product
                openActivity(ShowProductActivity.class,productsList.getName());
            }
        });
    }

    private void openActivity(Class activityClass,String productName)
    {
        Intent mIntent = new Intent(context,activityClass);
        Bundle mBundle = new Bundle();
        mBundle.putString("productName", productName);
        mIntent.putExtras(mBundle);
        context.startActivity(mIntent);
    }

    @Override
    public int getItemCount() {
        return productsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.productItemImage);
            itemName = itemView.findViewById(R.id.productItemName);
            itemPrice = itemView.findViewById(R.id.productItemPrice);
            itemLikes = itemView.findViewById(R.id.productItemLikes);

        }
    }
}
