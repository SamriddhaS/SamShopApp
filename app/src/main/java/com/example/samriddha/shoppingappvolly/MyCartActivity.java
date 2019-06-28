package com.example.samriddha.shoppingappvolly;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samriddha.shoppingappvolly.Database.Database;
import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.HelperClasses.RecyclerItemTouchHelper;
import com.example.samriddha.shoppingappvolly.Interface.RecyclerItemTouchHelperListner;
import com.example.samriddha.shoppingappvolly.ViewHolder.CusMyCartAdaptar;
import com.example.samriddha.shoppingappvolly.ViewHolder.MyCartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class MyCartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListner {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;
    TextView totalPrice ;
    Button placeOrder ;
    RelativeLayout cartLayout ;
    ArrayList<OrderDetailModel> cartData = new ArrayList<>();
    CusMyCartAdaptar adaptar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        recyclerView = (RecyclerView)findViewById(R.id.recviewmycart_id) ;
        totalPrice = (TextView) findViewById(R.id.totalpricemycart_id) ;
        placeOrder = (Button) findViewById(R.id.placeordermycart_id) ;
        cartLayout = (RelativeLayout) findViewById(R.id.cartlayout_id) ;

        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        loadCartItemList();
    }

    private void loadCartItemList() {

        cartData.clear();
        cartData = new Database(this).getCarts();
        adaptar = new CusMyCartAdaptar(cartData,this);
        adaptar.notifyDataSetChanged();
        recyclerView.setAdapter(adaptar);

        //calculating total price of Cart

        float total = 0 ;
        for (OrderDetailModel orderDetailModel:cartData)
            total+= (Float.parseFloat(orderDetailModel.getPrice()))*(Float.parseFloat(orderDetailModel.getQuantity()));

        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        totalPrice.setText(fmt.format(total));


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals(Common.OPEN))
            openItem(item.getOrder());

        return true;
    }

    private void openItem(int position) {



        startActivity(new Intent(this,ShowProductActivity.class));

        /*
        // Removing the item from database
        new Database(this).removeFromCart(cartData.get(position).getProductID());

        // Removing the item by position from 'List<OrderDetailModel> cartData'
        cartData.remove(position);

        *//*
        Another way to delete from cart

        // Then Cleaing the whole database
        new Database(this).cleanCart();

        // Removing the item by position from 'List<OrderDetailModel> cartData'
        cartData.remove(position);

        //And Finally add the modified cartData into Database Again
        for (OrderDetailModel task:cartData)
            new Database(this).addToCart(task);
        *//*
        // Loading the CartItem To see the modified Cart List
        loadCartItemList();
*/
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof MyCartViewHolder){

            String name = ((CusMyCartAdaptar)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();

            final OrderDetailModel deleteItem = ((CusMyCartAdaptar)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adaptar.removeItem(deleteIndex);
            new Database(getBaseContext()).removeFromCart(deleteItem.getProductID());

            //calculating total price of Cart

            float total = 0 ;
            for (OrderDetailModel orderDetailModel:cartData)
                total+= (Float.parseFloat(orderDetailModel.getPrice()))*(Float.parseFloat(orderDetailModel.getQuantity()));

            Locale locale = new Locale("en","in");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            totalPrice.setText(fmt.format(total));

            Snackbar snackbar = Snackbar.make(cartLayout,name+"Removed From Cart",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    adaptar.restoreItem(deleteItem,deleteIndex);
                    new Database(getBaseContext()).addToCart(deleteItem);

                    //calculating total price of Cart
                    float total = 0 ;
                    for (OrderDetailModel orderDetailModel:cartData)
                        total+= (Float.parseFloat(orderDetailModel.getPrice()))*(Float.parseFloat(orderDetailModel.getQuantity()));

                    Locale locale = new Locale("en","in");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                    totalPrice.setText(fmt.format(total));
                }
            });
            
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
    }

    public void PlaceOrderButton(View view) {

        startActivity(new Intent(MyCartActivity.this,PlaceOrderActivity.class).putExtra("Data",cartData));

    }

}
