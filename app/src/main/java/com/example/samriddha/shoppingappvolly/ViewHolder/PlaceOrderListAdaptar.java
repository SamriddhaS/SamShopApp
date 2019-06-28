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
import com.example.samriddha.shoppingappvolly.HelperClasses.SubCatModel;
import com.example.samriddha.shoppingappvolly.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceOrderListAdaptar extends RecyclerView.Adapter<PlaceOrderListViewHolder> {


    private List<OrderDetailModel> listData = new ArrayList<>();
    Context context ;

    public PlaceOrderListAdaptar(List<OrderDetailModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceOrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mycart_item,parent,false);

        return new PlaceOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOrderListViewHolder holder, int position) {

        TextDrawable textDrawable = TextDrawable.builder().buildRect(""+listData.get(position).getQuantity(), Color.GREEN);
        holder.itemQnty.setImageDrawable(textDrawable);

        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        float price = (Float.parseFloat(listData.get(position).getPrice()));
        holder.cartItemPrice.setText(fmt.format(price));

        holder.cartItemName.setText(listData.get(position).getProductName());

        Picasso.with(context).load(listData.get(position).getImage()).into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
