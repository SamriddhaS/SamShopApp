package com.example.samriddha.shoppingappvolly;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;

import io.paperdb.Paper;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView appicon , apptitle ;
    View cutview ;
    Animation animation ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appicon = (ImageView)findViewById(R.id.ivappicon_id);
        apptitle = (ImageView)findViewById(R.id.ivapptitle_id);
        cutview = (View)findViewById(R.id.vcut_id);
        Paper.init(this);

        animation = AnimationUtils.loadAnimation(this,R.anim.push_down);
        appicon.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(this,R.anim.push_right);
        apptitle.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(this,R.anim.push_right);
        cutview.setAnimation(animation);


        final String mail = Paper.book().read(Common.UMAIL_KEY);
        final String pass = Paper.book().read(Common.UPASS_KEY);


        Thread thread = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(2000);
                    if (mail!= null && pass!= null) {
                        if (!mail.isEmpty() && !pass.isEmpty()){
                            if (Common.isConnectedInternet(getBaseContext())) {

                                SplashScreenActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final ProgressDialog progressDialog = new ProgressDialog(SplashScreenActivity.this);
                                        progressDialog.setMessage("Loading Please Wait...");
                                        progressDialog.show();
                                        autoLogIn(mail, pass);
                                    }
                                });

                            }
                            else {

                                SplashScreenActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                                        builder.setIcon(R.drawable.ic_warning_black_24dp);
                                        builder.setTitle("Network Error !");
                                        builder.setMessage("Please Check Your Connection !");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                        }
                                });
                            }
                        }
                    }else {

                        startActivity(new Intent(SplashScreenActivity.this, LogInActivity.class));
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();

    }

    private void autoLogIn(final String mail, final String pass) {

        String url = "http://103.230.103.142/onlineshoppingapp/show.asmx/LogIn";

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


                        startActivity(new Intent(SplashScreenActivity.this,AppHomeActivity.class));
                        finish();

                    }
                    else{

                        Toast.makeText(SplashScreenActivity.this, "false", Toast.LENGTH_SHORT).show();
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
                hashMap.put("Pwd",pass);
                hashMap.put("EmailId",mail);
                return hashMap;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(SplashScreenActivity.this);
        requestQueue.add(stringRequest);

    }
}
