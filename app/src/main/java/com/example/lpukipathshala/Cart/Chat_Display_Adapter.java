package com.example.lpukipathshala.Cart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpukipathshala.DataModels.Chat_Data;
import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Chat_Display_Adapter extends RecyclerView.Adapter<Chat_Display_Adapter.chatViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context context;
    List<Chat_Data> details;
    public Chat_Display_Adapter(Context context,List<Chat_Data> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_display_layout,viewGroup,false);
            return new Chat_Display_Adapter.chatViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Chat_Display_Adapter.chatViewHolder cartViewHolder, int i) {
        Chat_Data chat_data = details.get(i);
        cartViewHolder.documentReference=  cartViewHolder.firebaseFirestore.collection("Users").document(chat_data.getSender_id());
        cartViewHolder.documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserDetails userDetails = documentSnapshot.toObject(UserDetails.class);
                cartViewHolder.name.setText(userDetails.getFname()+" "+userDetails.getLname());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
       // cartViewHolder.name.setText(chat_data.getSender_id());
       cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,Cart.class);
               intent.putExtra("u_id",chat_data.getSender_id());
               intent.putExtra("b_id",chat_data.getBook_id());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class chatViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        DocumentReference documentReference;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }


}

