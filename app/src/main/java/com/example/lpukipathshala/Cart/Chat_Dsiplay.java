package com.example.lpukipathshala.Cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lpukipathshala.DataModels.Chat_Data;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.Myaccount.OurProduct.Product_Sell;
import com.example.lpukipathshala.Myaccount.OurProduct.Sell_Adapter;
import com.example.lpukipathshala.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Chat_Dsiplay extends AppCompatActivity {
    RecyclerView recyclerView;
    String b_id;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Set<String> set = new HashSet();
    ProgressDialog progressDialog;
    ArrayList<Chat_Data> list = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_display);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chat_Dsiplay.this, Product_Sell.class);
                startActivity(intent);
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        b_id = intent.getStringExtra("b_id");
        //intent.putStringArrayListExtra("u_ida",list);
//        list.add("Yash");
//        list.add("Sagar");
//        list.add("Ishan");
//        list.add("Vaibhav");



}

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Please wait a while...");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                set.clear();
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Chat_Data chat_data = dataSnapshot1.getValue(Chat_Data.class);
                    String s = chat_data.getBook_id().toLowerCase();

                    if(s.equalsIgnoreCase(b_id) && !chat_data.getSender_id().equalsIgnoreCase(mAuth.getUid()) )
                    {

                        set.add(chat_data.getSender_id());
                        // u_id[0] = chat_data.getSender_id();

                    }
                }
                for(String s1:set)
                {
                    list.add(new Chat_Data(s1,b_id));
                    progressDialog.dismiss();
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Chat_Dsiplay.this);
                Chat_Display_Adapter adapter = new Chat_Display_Adapter (Chat_Dsiplay.this,list);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
