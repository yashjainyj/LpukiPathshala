package com.example.lpukipathshala.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lpukipathshala.HomeActivity;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.R;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        recyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        List<Cart_Details> details = new ArrayList<>();
        details.add(new Cart_Details("Science","Rs.500"));
        details.add(new Cart_Details("Science","Rs.500"));
        details.add(new Cart_Details("Science","Rs.500"));
        details.add(new Cart_Details("Science","Rs.500"));
        details.add(new Cart_Details("Science","Rs.500"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        Cart_Adapter adapter = new Cart_Adapter(details);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
