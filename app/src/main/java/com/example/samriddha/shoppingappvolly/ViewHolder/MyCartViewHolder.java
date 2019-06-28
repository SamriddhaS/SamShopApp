package com.example.samriddha.shoppingappvolly.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samriddha.shoppingappvolly.Common;
import com.example.samriddha.shoppingappvolly.R;

public class MyCartViewHolder extends RecyclerView.ViewHolder{

    TextView cartItemName , cartItemPrice ;
    ImageView itemQnty, itemImage ;
    public RelativeLayout viewBackground;
    public LinearLayout viewForeground;


    public void setCartItemName(TextView cartItemName) {
        this.cartItemName = cartItemName;
    }

    public MyCartViewHolder(@NonNull View itemView) {
        super(itemView);

        cartItemName = (TextView)itemView.findViewById(R.id.cartitemname_id);
        cartItemPrice = (TextView)itemView.findViewById(R.id.cartitemprice_id);
        itemQnty = (ImageView) itemView.findViewById(R.id.cartitemqty_id);
        itemImage = (ImageView)itemView.findViewById(R.id.cartitemimage_id);
        viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewbackground_id);
        viewForeground = (LinearLayout) itemView.findViewById(R.id.viewforeground_id);


    }



}
