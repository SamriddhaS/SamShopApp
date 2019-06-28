package com.example.samriddha.shoppingappvolly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    TextView name1 , name2 , email , phon , regId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        name1 = (TextView)findViewById(R.id.mypro_name_id);
        name2 = (TextView)findViewById(R.id.mypro_name_id2);
        email = (TextView)findViewById(R.id.mypro_email_id);
        phon = (TextView)findViewById(R.id.mypro_phon_id);
        regId = (TextView)findViewById(R.id.mypro_reg_id);

        if (Common.Name == ""){
            name1.setText("Please LogIn To See Details");
        }else {

            name1.setText(Common.Name);
            name2.setText(Common.Name);
            email.setText(Common.Email);
            phon.setText(Common.PhNumber);
            regId.setText(Common.RegId);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
