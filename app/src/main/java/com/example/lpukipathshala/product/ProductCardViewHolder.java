package com.example.lpukipathshala.product;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpukipathshala.R;

public class ProductCardViewHolder extends RecyclerView.ViewHolder {


    public TextView productTitle;
    public TextView productPrice;
    public ImageView productImage;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);

        productTitle = itemView.findViewById(R.id.product_title);
        productPrice = itemView.findViewById(R.id.product_price);
        productImage = itemView.findViewById(R.id.product_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(),Product_Details.class);
                itemView.getContext().startActivity(intent);

            }
        });
    }
}
