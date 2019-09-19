package com.example.lpukipathshala.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lpukipathshala.Cart.Cart;
import com.example.lpukipathshala.DataModels.Add_Book_Model;
import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.HomeActivity;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Product_Details extends AppCompatActivity {
    TextView description,product_name,author_name,price,details;
    FloatingActionButton favourite;
    boolean a = true;
    String b_id,s="",u_id;
    ImageView imageView;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    DocumentReference documentReference ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(Product_Details.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        Intent getintent = getIntent();
        imageView=findViewById(R.id.product_image);
        b_id = getintent.getStringExtra("b_id");
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
        product_name = findViewById(R.id.productname);
        author_name = findViewById(R.id.authorname);
        price = findViewById(R.id.product_price);
        details = findViewById(R.id.details);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference = firebaseFirestore.collection("BOOKS");
        collectionReference.whereEqualTo("bookId",b_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                {
                    Add_Book_Model add_book_model= queryDocumentSnapshot.toObject(Add_Book_Model.class);
                    product_name.setText(add_book_model.getBookName());
                    author_name.setText(add_book_model.getAuthorName());
                    price.setText("Rs."+add_book_model.getPrice());
                    u_id = add_book_model.getUserId();
                    Glide.with(Product_Details.this).load(add_book_model.getPicUrl()).into(imageView);
                     s = add_book_model.getDescription()+"\n\n";
                    description.setText(s);
                  //  Toast.makeText(Product_Details.this, add_book_model.getUserId(), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Product_Details.this, u_id, Toast.LENGTH_SHORT).show();
                documentReference=  firebaseFirestore.collection("Users").document(u_id);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserDetails userDetails = documentSnapshot.toObject(UserDetails.class);
                        s="Seller Contact Details\n\n";
                        s=s+"Name      : "+userDetails.getFname() + " " + userDetails.getLname()+"\n";
                        s=s+"Email       : "+userDetails.getEmail()+"\n";
                        s=s+"Phone      : "+userDetails.getPhone()+"\n";
                        s=s+"Location  : "+userDetails.getLocation()+"\n";
                        details.setText(s);
                       // Toast.makeText(Product_Details.this, s, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Product_Details.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.i("sadad", "onFailure: ---------------------------------------------------------------");
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.chat :
                Intent intent = new Intent(Product_Details.this,Cart.class);
                intent.putExtra("u_id",u_id);
                intent.putExtra("b_id",b_id);
                startActivity(intent);
                break;
        }
        return true;
    }
}
