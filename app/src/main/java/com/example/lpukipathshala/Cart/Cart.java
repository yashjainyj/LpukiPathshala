package com.example.lpukipathshala.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.lpukipathshala.DataModels.Chat_Data;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText answer;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        recyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        answer = findViewById(R.id.message);
        recyclerView.setHasFixedSize(true);
        List<Chat_Data> details = new ArrayList<>();
        String u = firebaseAuth.getUid();
       details.add(new Chat_Data(u,"hy","HELLO"));
        details.add(new Chat_Data("hy",u,"Hy"));
        details.add(new Chat_Data(u,u,u));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        Cart_Adapter adapter = new Cart_Adapter(details);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }
}
