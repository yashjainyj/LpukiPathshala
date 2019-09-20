package com.example.lpukipathshala.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lpukipathshala.DataModels.Chat_Data;
import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.Notification.Client;
import com.example.lpukipathshala.Notification.Data;
import com.example.lpukipathshala.Notification.MyResponse;
import com.example.lpukipathshala.Notification.Sender;
import com.example.lpukipathshala.Notification.Token;
import com.example.lpukipathshala.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText answer;
    boolean notigy = false;
    FirebaseAuth firebaseAuth;
    FloatingActionButton send;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    DatabaseReference databaseReference;
    List<Chat_Data> list;
    String u_id;
    ArrayList u_ida=new ArrayList();
    String my_id ;
    private String book_id;
    APIService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        recyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Chats");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        Intent intent = getIntent();
        book_id = intent.getStringExtra("b_id");
        u_id= intent.getStringExtra("u_id");
//        u_ida = intent.getStringArrayListExtra("u_ida");
        my_id = firebaseAuth.getUid();
        answer = findViewById(R.id.other);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notigy = true;
                String s = answer.getText().toString();
                if(!s.equalsIgnoreCase(""))
                sendMessage(firebaseAuth.getUid(),u_id,s);
                else
                    Toast.makeText(Cart.this, "Empty", Toast.LENGTH_SHORT).show();
                answer.setText("");
            }
        });
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        readMessage();
    }

    private void sendMessage(String sender, String receiver, String s) {
        Chat_Data chat_data  = new Chat_Data(sender,receiver,s, book_id);
        databaseReference.push().setValue(chat_data);
        readMessage();

        final String msg = s;
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //UserDetails user = dataSnapshot.getValue(UserDetails.class);
                if(notigy)
                sendNotification(receiver,"YAsh jain",msg);
                notigy = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(String receiver, String uid, String msg) {
        DatabaseReference tokend = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokend.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Token token = dataSnapshot1.getValue(Token.class);
                    Data data = new Data(firebaseAuth.getUid(),R.mipmap.ic_launcher,uid+" :",msg,"New Message");
                    Sender sender = new Sender(data,token.getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code()==200){
                                if(response.body().success!=1)
                                {
                                    Toast.makeText(Cart.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readMessage()
    {
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Chat_Data chat_data = dataSnapshot1.getValue(Chat_Data.class);
                 //   Toast.makeText(Cart.this, chat_data.getSender_id()+ " " + chat_data.getReceiver_id(), Toast.LENGTH_SHORT).show();
//                    if(chat_data.getReceiver_id().equals(my_id)  &&  chat_data.getSender_id().equals(u_id) ||
//                            chat_data.getReceiver_id().equals(u_id) && chat_data.getSender_id().equals(my_id)){
//                        list.add(chat_data);
//                    }
                    if(chat_data.getReceiver_id().equals(my_id)  &&  chat_data.getSender_id().equals(u_id) ||
                            chat_data.getReceiver_id().equals(u_id) && chat_data.getSender_id().equals(my_id)){
                        list.add(chat_data);
                    }
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
                Cart_Adapter adapter = new Cart_Adapter(list);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
