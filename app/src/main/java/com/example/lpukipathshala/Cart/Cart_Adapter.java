package com.example.lpukipathshala.Cart;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpukipathshala.R;
import java.util.List;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.CartViewHolder> {

    List<Cart_Details> details;
    public Cart_Adapter(List<Cart_Details> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_layout,viewGroup,false);
        CartViewHolder cartViewHolder=new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        final Cart_Details recycleItemAdd=details.get(i);
        cartViewHolder.product_name.setText(recycleItemAdd.getProduct_name());
        cartViewHolder.product_price.setText(recycleItemAdd.getProduct_price());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView product_image;
        public TextView product_name,product_price;
        public ImageButton delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_price = itemView.findViewById(R.id.product_price);
            product_name = itemView.findViewById(R.id.productname);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
