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

public class MyWishlistViewHolder extends RecyclerView.ViewHolder {

    TextView cartItemName , cartItemPrice ;
    ImageView  itemImage ;
    public RelativeLayout viewBackground;
    public LinearLayout viewForeground;


    public void setCartItemName(TextView cartItemName) {
        this.cartItemName = cartItemName;
    }

    public MyWishlistViewHolder(@NonNull View itemView) {
        super(itemView);

        cartItemName = (TextView)itemView.findViewById(R.id.wishitemname_id);
        cartItemPrice = (TextView)itemView.findViewById(R.id.wishitemprice_id);
        itemImage = (ImageView)itemView.findViewById(R.id.wishitemimage_id);
        viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewbackground_id1);
        viewForeground = (LinearLayout) itemView.findViewById(R.id.viewforeground_id1);


    }


    public void onClick(View view) {

    }


}
