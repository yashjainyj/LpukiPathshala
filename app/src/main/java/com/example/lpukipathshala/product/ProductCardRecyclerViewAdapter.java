package com.example.lpukipathshala.product;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lpukipathshala.R;

import java.util.ArrayList;

/**
* Adapter used to show a simple grid of products.
*/
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

   private ArrayList<ProductEntry> productList;


   ProductCardRecyclerViewAdapter(ArrayList<ProductEntry> productList) {
       this.productList = productList;
   }

   @NonNull
   @Override
   public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);

       return new ProductCardViewHolder(layoutView);

   }

    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            ProductEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);

        }
    }

   @Override
   public int getItemCount() {
       return productList.size();
   }
}