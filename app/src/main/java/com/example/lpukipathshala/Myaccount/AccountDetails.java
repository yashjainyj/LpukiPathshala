package com.example.lpukipathshala.Myaccount;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpukipathshala.Cart.Cart;
import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.HomeActivity;
import com.example.lpukipathshala.MainActivity;
import com.example.lpukipathshala.MyUtility;
import com.example.lpukipathshala.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AccountDetails extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    TextView name,location,email,phone,about,cart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        setUpToolbar();
        floatingActionButton = findViewById(R.id.edit);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phonenumber);
        about = findViewById(R.id.about);
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetails.this, Cart.class);
                startActivity(intent);
                finish();
            }
        });

        if(MyUtility.userDetails!=null)
        {

            name.setText(MyUtility.userDetails.get(0).getFname() + " " + MyUtility.userDetails.get(0).getLname());
            location.setText(MyUtility.userDetails.get(0).getLocation());
            email.setText(MyUtility.userDetails.get(0).getEmail());
            phone.setText(MyUtility.userDetails.get(0).getPhone());
            about.setText(MyUtility.userDetails.get(0).getAbout());
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetails.this,EditProfile.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetails.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        myaccount = findViewById(R.id.myaccount);
//        myaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // FirebaseAuth.getInstance().signOut();
////                Intent intent = new Intent(AccountDetails.this, AccountDetails.class);
////                startActivity(intent);
////               finish();
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.account_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout :  FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AccountDetails.this, MainActivity.class);
            startActivity(intent);
            break;
        }
        return true;
    }
}
