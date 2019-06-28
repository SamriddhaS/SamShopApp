package com.example.samriddha.shoppingappvolly;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samriddha.shoppingappvolly.Database.Database;
import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.RecyclerItemTouchHelper;
import com.example.samriddha.shoppingappvolly.HelperClasses.WishlistModel;
import com.example.samriddha.shoppingappvolly.Interface.RecyclerItemTouchHelperListner;
import com.example.samriddha.shoppingappvolly.ViewHolder.CusMyCartAdaptar;
import com.example.samriddha.shoppingappvolly.ViewHolder.CusMyWishlistAdaptar;
import com.example.samriddha.shoppingappvolly.ViewHolder.MyCartViewHolder;
import com.example.samriddha.shoppingappvolly.ViewHolder.MyWishlistViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WishlistActivity extends AppCompatActivity implements RecyclerItemTouchHelperListner {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;
    RelativeLayout wishLayout ;
    List<WishlistModel> wishData = new ArrayList<>();
    CusMyWishlistAdaptar adaptar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerView = (RecyclerView)findViewById(R.id.wishlistrecview_id) ;
        wishLayout = (RelativeLayout) findViewById(R.id.wishlistlayout_id) ;

        getSupportActionBar().setTitle("My Wishlist");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        loadWishlistItemList();


    }

    private void loadWishlistItemList() {

        wishData.clear();
        wishData = new Database(this).getAllWishlist();
        adaptar = new CusMyWishlistAdaptar(wishData,this);
        adaptar.notifyDataSetChanged();
        recyclerView.setAdapter(adaptar);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof MyWishlistViewHolder){

            String name = ((CusMyWishlistAdaptar)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();

            final WishlistModel deleteItem = ((CusMyWishlistAdaptar)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adaptar.removeItem(deleteIndex);
            new Database(getBaseContext()).removeFromWishlist(deleteItem.getProductID());


            Snackbar snackbar = Snackbar.make(wishLayout,name+"Removed From Wishlist",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    adaptar.restoreItem(deleteItem,deleteIndex);
                    new Database(getBaseContext()).addToWishlist(deleteItem);

                }
            });

            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
}

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}


