package com.example.lpukipathshala.Myaccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.HomeActivity;
import com.example.lpukipathshala.MyUtility;
import com.example.lpukipathshala.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    TextInputEditText fname,lname,location,about,phone;
    MaterialButton submit;
    String email="",phone1 = "";
    CircleImageView circleImageView;
    ImageView backimage;
    private Uri uriProfileImage;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    String profileUrl="";
    FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditProfile.this,AccountDetails.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        storageReference= FirebaseStorage.getInstance().getReference();
        circleImageView = findViewById(R.id.circleImageView);
        fname = findViewById(R.id.name1);
        lname = findViewById(R.id.l1);
        location= findViewById(R.id.location1);
        about = findViewById(R.id.about1);
        phone = findViewById(R.id.phonenumber1);
        submit = findViewById(R.id.submit);
      //  backimage = findViewById(R.id.back);
//        if(MyUtility.userDetails!=null)
//        {
//            phone.setFocusable(false);
//            phone.setText(MyUtility.userDetails.get(0).getPhone());
//            phone.setFocusableInTouchMode(false);
//            email = MyUtility.userDetails.get(0).getEmail();
//            phone1 = MyUtility.userDetails.get(0).getPhone();
//        }
//        List<UserDetails> list = new ArrayList<>();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();

            }
        });





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference d = storageReference.child("images/user/"+mAuth.getUid()+"/"+mAuth.getUid() +".jpg");
                d.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        profileUrl = storageReference.getDownloadUrl().toString();
                        Toast.makeText(EditProfile.this, "Upload Success" + profileUrl, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


              UserDetails userDetails  =  new UserDetails(fname.getText().toString(),lname.getText().toString(),phone.getText().toString(),mAuth.getCurrentUser().getEmail(),location.getText().toString(),about.getText().toString());
              //MyUtility.userDetails = list;

                firebaseFirestore.collection("Users").document(mAuth.getUid()).set(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(EditProfile.this, HomeActivity.class);
//                startActivity(intent);
//                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode ==RESULT_OK &&data!=null && data.getData()!=null)
        {
            
          uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                circleImageView.setImageBitmap(bitmap);
                //backimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Profile Picture"),CHOOSE_IMAGE);
    }
}
