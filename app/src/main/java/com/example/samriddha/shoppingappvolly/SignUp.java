package com.example.samriddha.shoppingappvolly;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.samriddha.shoppingappvolly.ViewHolder.CustomDialogClass;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText name , password , email , phNum , conPassword ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = (EditText)findViewById(R.id.signupname_id);
        password = (EditText)findViewById(R.id.signuppass_id);
        email = (EditText)findViewById(R.id.signupemail_id);
        phNum = (EditText)findViewById(R.id.signupphone_id);
        conPassword = (EditText)findViewById(R.id.signupconpass_id);

    }

    public void SignUpMethod(View view) {

        String mName = name.getText().toString().trim();
        String mPassword = password.getText().toString().trim();
        String mEmail = email.getText().toString().trim();
        String mPhonnum = phNum.getText().toString().trim();
        String mConPassword = conPassword.getText().toString().trim();

        String url = "http://103.230.103.142/onlineshoppingapp/show.asmx/Registration";

        if (mName.isEmpty()){
            name.setError("Please Enter Name");
            return ;
        }else if (mEmail.isEmpty()){
            email.setError("Please Enter Email");
            return ;
        }else if (mPhonnum.isEmpty()){
            phNum.setError("Please Enter Phone Number");
            return ;
        }else if (mPassword.isEmpty()){
            Toast.makeText(this, "Please Enter Account Password", Toast.LENGTH_SHORT).show();
            return ;
        }else if (mConPassword.isEmpty()){
            Toast.makeText(this, "Please Confirm Account Password", Toast.LENGTH_SHORT).show();
            return ;
        }

        if (mPassword.equals(mConPassword)){

            RegisterAccountMethod(url);
        }else {

            Toast.makeText(this, "Password And Confirm Password Doesn't Match" , Toast.LENGTH_SHORT).show();
            return;
        }







    }

    private void RegisterAccountMethod(String url) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    String status=new JSONObject(response).getJSONArray("Response").getJSONObject(0).getString("Messagetext");


                    if (status.equals("Sucess")){

                        Toast.makeText(SignUp.this, "Your Account Successfully Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,LogInActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, "Failed To Create Account", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();

                hashMap.put("Name",name.getText().toString().trim());
                hashMap.put("Password",password.getText().toString().trim());
                hashMap.put("PhNo",phNum.getText().toString().trim());
                hashMap.put("EmailId",email.getText().toString().trim());
                return hashMap;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(stringRequest);


    }


    public void AlreadyHvAccount(View view) {

        startActivity(new Intent(SignUp.this,LogInActivity.class));
        finish();

    }
    @Override
    public void onBackPressed() {

        CustomDialogClass customDialogClass = new CustomDialogClass(SignUp.this);
        customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialogClass.show();

    }

}
