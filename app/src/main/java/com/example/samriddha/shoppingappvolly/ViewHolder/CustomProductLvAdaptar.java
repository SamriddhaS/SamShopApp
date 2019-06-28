package com.example.samriddha.shoppingappvolly.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samriddha.shoppingappvolly.Fragments.FragProducts;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomProductLvAdaptar extends BaseAdapter {

    Context context ;
    ArrayList<ProductDetailHelperClass> arrayList  ;
    public CustomProductLvAdaptar(Context context , ArrayList<ProductDetailHelperClass> arrayList) {

        this.context = context ;
        this.arrayList = arrayList ;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.listviewrow_showproduct,parent,false);

        TextView productName , originalPrice , salePrice , availability ;
        ImageView productImage ;

        productName = (TextView) view.findViewById(R.id.lvproductname_id);
        originalPrice = (TextView) view.findViewById(R.id.lvproductOprice_id);
        salePrice = (TextView) view.findViewById(R.id.lvproductprice_id);
        availability = (TextView) view.findViewById(R.id.lvproductavali_id);

        productImage = (ImageView)view.findViewById(R.id.lvproductimage_id);



        productName.setText(arrayList.get(position).getProductName());
        originalPrice.setText(arrayList.get(position).getOriginalPrice());
        salePrice.setText(arrayList.get(position).getPriceOnSale());
        availability.setText(arrayList.get(position).getProductAval());

        Picasso.with(context).load(arrayList.get(position).getProductImage()).into(productImage);

        return view;
    }


}
