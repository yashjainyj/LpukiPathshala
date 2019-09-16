package com.example.lpukipathshala.quoraa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpukipathshala.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class answercmainclass extends AppCompatActivity {


    RecyclerView recyclerViewans;
    TextView answertext;
    ProgressDialog progressDialog;
    ImageButton buttontouploadimage;
   FloatingActionButton answertouploaddata;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    ArrayList<mAnswerGetSet> list1;

    String q_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answerrecyclerview);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
      q_id= intent.getStringExtra("q_id");
     //   Toast.makeText(this, q_id, Toast.LENGTH_SHORT).show();
//
//        list1 = new ArrayList<>();
//        list1.add(new com.example.lpukipathshala.quoraa.answercontent("R.drawable.imageq",""));
//        list1.add(new com.example.lpukipathshala.quoraa.answercontent("R.drawable.imageq","Answer"));
//        list1.add(new com.example.lpukipathshala.quoraa.answercontent("R.drawable.imageq","Answer"));
//        list1.add(new com.example.lpukipathshala.quoraa.answercontent("R.drawable.imageq","Answer"));
firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewans = findViewById(R.id.answerdetails);
        answertouploaddata = findViewById(R.id.uploadanswer);
        answertext = findViewById(R.id.answer);
        buttontouploadimage = findViewById(R.id.uploadimageofans);
        //answermoreupload = findViewById(R.id.answermore);


        /*answermoreupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(answercmainclass.this, com.example.lpukipathshala.quoraa.answermoreup.class);
                startActivity(intent);
            }
        });*/
        answertouploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setMessage("Please wait a while.....");
                collectionReference = firebaseFirestore.collection("Answers");
                //Date c = Calendar.getInstance().getTime();
                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                mAnswerGetSet answerGetSet = new mAnswerGetSet(firebaseAuth.getUid(),q_id,answertext.getText().toString(),"");


                collectionReference.add(answerGetSet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(answercmainclass.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    getData();
                    progressDialog.dismiss();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(answercmainclass.this, "CanNot Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        getData();

    }
    public void getData(){
        collectionReference = firebaseFirestore.collection("Answers");
        collectionReference.whereEqualTo("quid",q_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list1 = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots)
                {
                    mAnswerGetSet mAnswerGetSet  = queryDocumentSnapshot.toObject(com.example.lpukipathshala.quoraa.mAnswerGetSet.class);
                    //String q_id = queryDocumentSnapshot.getId();
                    list1.add(new mAnswerGetSet(mAnswerGetSet.getUid(),mAnswerGetSet.getQuid(),mAnswerGetSet.getaAnswer(),mAnswerGetSet.getAimgurl()));
                }
                answeradapter answeradapter= new answeradapter(answercmainclass.this,list1);
                recyclerViewans.setLayoutManager(new GridLayoutManager(answercmainclass.this,1));
                recyclerViewans.setAdapter(answeradapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}

