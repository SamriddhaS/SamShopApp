package com.example.samriddha.shoppingappvolly.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samriddha.shoppingappvolly.Interface.ItemClickListner;
import com.example.samriddha.shoppingappvolly.R;

public class PlaceOrderListViewHolder extends RecyclerView.ViewHolder {


    TextView cartItemName , cartItemPrice ;
    ImageView itemQnty, itemImage ;


    public PlaceOrderListViewHolder(@NonNull View itemView) {
        super(itemView);

        cartItemName = (TextView)itemView.findViewById(R.id.cartitemname_id);
        cartItemPrice = (TextView)itemView.findViewById(R.id.cartitemprice_id);
        itemQnty = (ImageView) itemView.findViewById(R.id.cartitemqty_id);
        itemImage = (ImageView)itemView.findViewById(R.id.cartitemimage_id);


    }


}
