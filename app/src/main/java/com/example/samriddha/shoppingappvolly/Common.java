package com.example.samriddha.shoppingappvolly;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {


    public static String OPEN = "Open Item" ;
    public static String Email = "";
    public static String Name = "";
    public static String PhNumber = "";
    public static String RegId = "";
    public static String UMAIL_KEY = "mail" ;
    public static String UPASS_KEY = "password" ;



    public static boolean isConnectedInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager!= null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null){

                for (int i = 0 ; i <info.length;i++){
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }

        return false;

    }
}
