package com.example.lpukipathshala.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.lpukipathshala.Cart.Cart;
import com.example.lpukipathshala.HomeActivity;
import com.example.lpukipathshala.R;

public class Product_Details extends AppCompatActivity {
    TextView description;
    FloatingActionButton cart,favourite;
    boolean a = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_Details.this, Cart.class);
                startActivity(intent);
                finish();
            }
        });
        favourite = findViewById(R.id.favourite);

        favourite.setOnClickListener(v -> {
            if(a)
            {
                favourite.setImageResource(R.drawable.like);
                a=false;
            }
            else
            {
                favourite.setImageResource(R.drawable.like1);
                a=true;
            }
        });
        description = findViewById(R.id.description);
        description.setText("How to set scrollview to my layout? - Stack Overflow\n" +
                "\n" +
                "https://stackoverflow.com › questions › how-to-set-scrollview-to-my-layout\n" +
                "4 answers\n" +
                "Feb 13, 2013 - Set it like this: <RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\" xmlns:tools=\"http://schemas.android.com/tools\" ...\n" +
                "How to use ScrollView in Android?\t8 answers\t16 Nov 2011\n" +
                "Adding a Scroll View to the android layout\t6 answers\t17 Oct 2013\n" +
                "How to add scrollview in linearlayout\t2 answers\t26 Nov 2015\n" +
                "How to add a ScrollView to an Android Layout\t4 answers\t1 Feb 2013\n" +
                "More results from stackoverflow.com\n" +
                "People also ask\n" +
                "How do I make my screen scroll on Android?\n" +
                "\n" +
                "What is scroll view in Android?\n" +
                "\n" +
                "What is scrolling activity in Android Studio");

    }
}
