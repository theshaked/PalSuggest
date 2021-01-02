package com.example.palsuggest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Product[] productsData;

    public ProductAdapter(Product[] myMovieData, ProductsBrowserActivity activity) {
        this.productsData = myMovieData;
        this.context = activity;
    }

    Context context;


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
        final Product productsList = productsData[position];
        holder.itemImage.setImageBitmap(productsList.getImage());
        holder.itemName.setText(productsList.getName());
        holder.itemPrice.setText(productsList.getPrice()+" ₪ ");
        holder.itemLikes.setText(productsList.getLikesNames().size()+" ♥ ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,productsList.getName()+" was clicked!",Toast.LENGTH_SHORT).show();
                //TODO: Open ShowProductActivity of this product
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
