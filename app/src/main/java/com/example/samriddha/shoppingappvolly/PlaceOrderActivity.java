package com.example.samriddha.shoppingappvolly;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.ViewHolder.CusMyCartAdaptar;
import com.example.samriddha.shoppingappvolly.ViewHolder.PlaceOrderListAdaptar;
import com.rey.material.widget.EditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PlaceOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;
    private List<OrderDetailModel> orderDetail ;
    private TextView toPay ;
    private PlaceOrderListAdaptar adaptar;
    private android.widget.EditText name , mail , phon ,pin ,city , state , landmark ;
    private RelativeLayout relLay;
    private Spinner countrySpin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);


        getSupportActionBar().setTitle("Place Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        orderDetail = (List<OrderDetailModel>) getIntent().getSerializableExtra("Data");
        recyclerView =(RecyclerView)findViewById(R.id.porecview_id);
        toPay = (TextView)findViewById(R.id.potopay_id);
        name = (android.widget.EditText) findViewById(R.id.pocusname_id);
        mail = (android.widget.EditText)findViewById(R.id.pomail_id);
        phon = (android.widget.EditText)findViewById(R.id.pomobile_id);
        pin = (android.widget.EditText)findViewById(R.id.popin_id);
        city = (android.widget.EditText)findViewById(R.id.pocity_id);
        state = (android.widget.EditText)findViewById(R.id.postate_id);
        landmark = (android.widget.EditText)findViewById(R.id.poland_id);
        relLay = (RelativeLayout)findViewById(R.id.relativelay) ;
        countrySpin = (Spinner)findViewById(R.id.pospincou_id);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (orderDetail!=null)
            loadItems();


    }

    private void loadItems() {


        adaptar = new PlaceOrderListAdaptar(orderDetail,this);
        recyclerView.setAdapter(adaptar);

        //Setting Total Price have To Pay
        float total = 0 ;
        for (OrderDetailModel orderDetailModel:orderDetail)
            total+= (Float.parseFloat(orderDetailModel.getPrice()))*(Float.parseFloat(orderDetailModel.getQuantity()));
        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        toPay.setText(fmt.format(total));

        //setting default name , mail and phon number.
        if (Common.Name!="") {
            name.setText(Common.Name);
            mail.setText(Common.Email);
            phon.setText(Common.PhNumber);
        }

        //setting country list on spinner
        Locale[] loca = Locale.getAvailableLocales();
        final String DEFAULT_LOCAL = "India";
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : loca ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinertext, countries);

        adapter.setDropDownViewResource(R.layout.spinnerdropdown);
        countrySpin.setAdapter(adapter);
        countrySpin.setSelection(adapter.getPosition(DEFAULT_LOCAL));

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void PlaceOdButton(View view) {

        if (orderDetail.size()>0){
        String mName = name.getText().toString().trim();
        String mMail = mail.getText().toString().trim();
        String mPhon = phon.getText().toString().trim();
        String mPin = pin.getText().toString().trim();
        String mCity = city.getText().toString().trim();
        String mState = state.getText().toString().trim();
        String mLandmark = landmark.getText().toString().trim();

        if (mName.isEmpty() || mMail.isEmpty()||mPhon.isEmpty()||mPin.isEmpty()||mCity.isEmpty()||mState.isEmpty()||mLandmark.isEmpty()){

            Snackbar snackbar = Snackbar.make(relLay,"Some Star(*) Fileds Are Empty",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }else {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.shopping2);
            builder.setTitle(" Confirm Order !");
            builder.setMessage("Sure To Place Your Order ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PlaceOrderActivity.this, "Your Order Is Successfully Placed! Thank You . Keep Shopping!", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        }else {

            Snackbar snackbar = Snackbar.make(relLay,"Please Select Some Products To Buy",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
}
