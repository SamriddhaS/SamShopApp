package com.example.samriddha.shoppingappvolly.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.samriddha.shoppingappvolly.Common;
import com.example.samriddha.shoppingappvolly.ViewHolder.CustomProductLvAdaptar;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.R;
import com.example.samriddha.shoppingappvolly.ShowProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragProducts extends android.support.v4.app.Fragment {

    private String productCatId ;
    private String url = "http://103.230.103.142/onlineshoppingapp/show.asmx/GetProduct";
    private ArrayList<ProductDetailHelperClass> arrayList = new ArrayList<>();
    private ListView listView ;
    private ProgressDialog progressDialog ;
    private FrameLayout frameLayout1 ;
    private LinearLayout linearLayout ;

    public FragProducts() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_products, container, false);

        productCatId = getArguments().getString("CatId");

        frameLayout1= (FrameLayout)view.findViewById(R.id.fragmainlay_id);
        linearLayout= (LinearLayout) view.findViewById(R.id.aboutapp_id);
        listView = (ListView)view.findViewById(R.id.listViewProduct_id);


        if (Common.isConnectedInternet(getActivity().getBaseContext())) {

            if (productCatId != "") {
                listView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading Please Wait...");
                progressDialog.show();
                ReciveDetaFromUrl();
            }else{

                listView.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }else{
            Snackbar snackbar = Snackbar.make(frameLayout1,"Please Check Your Connection",Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                startActivity(new Intent(getContext() , ShowProductActivity.class).putExtra("position", i )
                        .putExtra("data",arrayList ));

            }
        });
        return view;

    }

    private void ReciveDetaFromUrl() {


        Response.Listener<String> responceListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObjecttop = new JSONObject(response);
                    JSONArray arrayProducts = jsonObjecttop.getJSONArray("Products");

                    for (int i = 0; i < arrayProducts.length(); i++) {

                        JSONObject productNumber = arrayProducts.getJSONObject(i);

                        ProductDetailHelperClass helperClass = new ProductDetailHelperClass();
                        helperClass.setProductName(productNumber.getString("Item_Name"));
                        helperClass.setOriginalPrice(productNumber.getString("Market_Price"));
                        helperClass.setPriceOnSale(productNumber.getString("Web_Price"));
                        helperClass.setProductImage(productNumber.getString("Product_Image"));
                        helperClass.setProductAval(productNumber.getString("Availability"));
                        helperClass.setProductId(productNumber.getString("Product_Id"));
                        helperClass.setCatagoriId(productNumber.getString("Category_Id"));

                        arrayList.add(helperClass);

                    }

                    if (arrayList.size() > 0) {

                        progressDialog.dismiss();
                        CustomProductLvAdaptar cusAdaptar = new CustomProductLvAdaptar(getContext(), arrayList);
                        listView.setAdapter(cusAdaptar);

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                           }
        };


        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,responceListener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> putData=new HashMap<>();
                putData.put("catId",productCatId);

                return putData;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    
}
