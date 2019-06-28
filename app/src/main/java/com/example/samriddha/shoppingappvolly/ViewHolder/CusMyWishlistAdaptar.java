package com.example.samriddha.shoppingappvolly.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.WishlistModel;
import com.example.samriddha.shoppingappvolly.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CusMyWishlistAdaptar extends RecyclerView.Adapter<MyWishlistViewHolder> {

    private List<WishlistModel> listData = new ArrayList<>();
    Context context ;

    public CusMyWishlistAdaptar(List<WishlistModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mywishlist_item,parent,false);

        return new MyWishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWishlistViewHolder holder, int position) {

        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        Float price = Float.parseFloat(listData.get(position).getPrice()) ;
        holder.cartItemPrice.setText(fmt.format(price));

        holder.cartItemName.setText(listData.get(position).getProductName());

        Picasso.with(context).load(listData.get(position).getImage()).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public WishlistModel getItem(int position){
        return listData.get(position);
    }

    public void removeItem(int position){

        listData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(WishlistModel item, int position) {

        listData.add(position,item);
        notifyItemInserted(position);
    }

}
