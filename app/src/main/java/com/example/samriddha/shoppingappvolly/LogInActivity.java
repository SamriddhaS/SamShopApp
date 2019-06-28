package com.example.samriddha.shoppingappvolly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.samriddha.shoppingappvolly.ViewHolder.CustomDialogClass;
import com.rey.material.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class LogInActivity extends AppCompatActivity {

    EditText emailLg , passwordLg ;
    CheckBox remembarCb ;
    RelativeLayout loginLayout ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        passwordLg = (EditText)findViewById(R.id.loginpass_id);
        emailLg = (EditText)findViewById(R.id.loginemail_id);
        remembarCb = (CheckBox)findViewById(R.id.remcb_id);
        loginLayout = (RelativeLayout)findViewById(R.id.loginlayout_id);

        //Init Paper
        Paper.init(this);

    }

    @Override
    public void onBackPressed() {

        CustomDialogClass customDialogClass = new CustomDialogClass(LogInActivity.this);
        customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialogClass.show();

    }

    public void CreateAccountMethod(View view) {

        startActivity(new Intent(LogInActivity.this,SignUp.class));
        finish();
    }

    public void LogInButtonMethod(View view) {

        String mPassword = passwordLg.getText().toString().trim();
        String mEmail = emailLg.getText().toString().trim();


        if (mEmail.isEmpty()){
            emailLg.setError("Please Enter Email");
            return;
        }else if (mPassword.isEmpty()){

            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://103.230.103.142/onlineshoppingapp/show.asmx/LogIn";

        if (Common.isConnectedInternet(LogInActivity.this)) {

            final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
            progressDialog.setMessage("Loading ...");
            progressDialog.show();
            LogInMethod(url);
        }
        else {
            Snackbar snackbar = Snackbar.make(loginLayout, "Please Check Your Connection", Snackbar.LENGTH_SHORT);
            snackbar.show();

        }
    }

    private void LogInMethod(String url) {

        Response.Listener<String> stringListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("User_Reg");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);

                    Common.Email=jsonObject1.getString("Email");
                    Common.Name=jsonObject1.getString("Name");
                    Common.PhNumber=jsonObject1.getString("Phoneno");
                    Common.RegId=jsonObject1.getString("Reg_Id");

                    //int status = 0 ;
                    //status=new JSONObject(response).getInt("success");
                    //Toast.makeText(LogIn.this, status, Toast.LENGTH_SHORT).show();

                    if (new JSONObject(response).getInt("success")== 1){

                        //Saving the user's email and password if choices to remember account
                        if (remembarCb.isChecked()){

                            Paper.book().write(Common.UMAIL_KEY,emailLg.getText().toString().trim());
                            Paper.book().write(Common.UPASS_KEY,passwordLg.getText().toString().trim());

                        }
                        final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
                        progressDialog.setMessage("Loading Please Wait...");
                        progressDialog.show();

                        Toast.makeText(LogInActivity.this, "Successfully Loged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this,AppHomeActivity.class));
                        finish();

                    }
                    else{

                        Toast.makeText(LogInActivity.this, "false", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String x="";
            }
        };


        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,stringListener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("Pwd",passwordLg.getText().toString());
                hashMap.put("EmailId",emailLg.getText().toString());
                return hashMap;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(LogInActivity.this);
        requestQueue.add(stringRequest);

    }

    }

