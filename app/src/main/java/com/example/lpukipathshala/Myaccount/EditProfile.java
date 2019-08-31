package com.example.lpukipathshala.Myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.lpukipathshala.DataModels.UserDetails;
import com.example.lpukipathshala.MyUtility;
import com.example.lpukipathshala.R;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {
    TextInputEditText fname,lname,location,about,phone;
    MaterialButton submit;
    String email="",phone1 = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this,AccountDetails.class);
                startActivity(intent);
                finish();
            }
        });
        fname = findViewById(R.id.name1);
        lname = findViewById(R.id.l1);
        location= findViewById(R.id.location1);
        about = findViewById(R.id.about1);
        phone = findViewById(R.id.phonenumber1);
        submit = findViewById(R.id.submit);

        if(MyUtility.userDetails!=null)
        {
            phone.setFocusable(false);
            phone.setText(MyUtility.userDetails.get(0).getPhone());
            phone.setFocusableInTouchMode(false);
            email = MyUtility.userDetails.get(0).getEmail();
            phone1 = MyUtility.userDetails.get(0).getPhone();
        }
        List<UserDetails> list = new ArrayList<>();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new UserDetails(fname.getText().toString(),lname.getText().toString(),phone1,email,location.getText().toString(),about.getText().toString()));
                Toast.makeText(EditProfile.this, fname.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(EditProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                MyUtility.userDetails = list;
finish();

            }
        });
    }
}
