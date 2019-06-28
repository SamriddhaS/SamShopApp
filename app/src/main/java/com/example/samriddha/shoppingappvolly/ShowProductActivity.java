package com.example.samriddha.shoppingappvolly;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.samriddha.shoppingappvolly.Database.Database;
import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.HelperClasses.WishlistModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowProductActivity extends AppCompatActivity {

    public ShowProductActivity() {
    }

    private TextView productName,productPrice,productID,catagoriID, productAvail ,originalPrice ;
    private ImageView productImage ;
    private FloatingActionButton favButton;
    private ElegantNumberButton  elegantNumberButton ;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ArrayList<ProductDetailHelperClass> productData = new ArrayList<>();
    private int position ;
    private android.support.v7.widget.Toolbar toolbar ;
    private Database localDB ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        productName = (TextView) findViewById(R.id.nameshowproduct_id);
        productPrice = (TextView) findViewById(R.id.priceshowproduct_id);
        productID = (TextView) findViewById(R.id.proidShowProducr_id);
        catagoriID = (TextView) findViewById(R.id.cataidShowProducr_id);
        productAvail = (TextView) findViewById(R.id.availShowProducr_id);
        originalPrice = (TextView) findViewById(R.id.oripriceShowProducr_id);
        productImage = (ImageView) findViewById(R.id.imageShowProducr_id);
        favButton = (FloatingActionButton)findViewById(R.id.favbutton_id);
        elegantNumberButton = (ElegantNumberButton)findViewById(R.id.qntshowproduct_id);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.coolapsingToolbar_id);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarShowProduct_id);


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandeddAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedAppBar);


        //reciving data from FragProducts.
        productData = (ArrayList<ProductDetailHelperClass>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("position",1 );

        //setting back button in toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        if (productData.get(position)!=null)
            getProductDetail();

        localDB = new Database(this);

        // checking if the product is already among favourites
        if (localDB.isWishlist(productData.get(position).getProductId()))
            favButton.setImageResource(R.drawable.ic_favorite);

    }

    private void getProductDetail() {

        productName.setText(productData.get(position).getProductName());
        productPrice.setText(productData.get(position).getPriceOnSale());
        productID.setText(productData.get(position).getProductId());
        catagoriID.setText(productData.get(position).getCatagoriId());
        productAvail.setText(productData.get(position).getProductAval());
        originalPrice.setText(productData.get(position).getOriginalPrice()) ;
        Picasso.with(ShowProductActivity.this).load(productData.get(position).getProductImage()).into(productImage);
        collapsingToolbarLayout.setTitle(productData.get(position).getProductName()) ;

    }

    public void AddToCartMethod(View view) {


        if (localDB.isInCart(productData.get(position).getProductId())) {
            Toast.makeText(this,  "Item Already In Your Cart", Toast.LENGTH_SHORT).show();
        } else {
        new Database(this).addToCart(new OrderDetailModel(

                productData.get(position).getProductName(),
                productData.get(position).getProductId(),
                elegantNumberButton.getNumber(),
                productData.get(position).getPriceOnSale(),
                productData.get(position).getProductImage()
                ));

        

        Toast.makeText(this, productData.get(position).getProductName()+" Added To Cart", Toast.LENGTH_SHORT).show();

    }
    }

    public void favButtonMethod(View view) {

        if (!localDB.isWishlist(productData.get(position).getProductId())){

            localDB.addToWishlist(new WishlistModel(
                    productData.get(position).getProductName(),
                    productData.get(position).getProductId(),
                    productData.get(position).getPriceOnSale(),
                    productData.get(position).getProductImage()
            ));
            favButton.setImageResource(R.drawable.ic_favorite);
            Toast.makeText(this, productData.get(position).getProductName()+" Added To Wishlist", Toast.LENGTH_SHORT).show();
        }else {

            localDB.removeFromWishlist(productData.get(position).getProductId());
            favButton.setImageResource(R.drawable.ic_favorite_border);
            Toast.makeText(this, productData.get(position).getProductName()+" Removed From Wishlist", Toast.LENGTH_SHORT).show();

        }
    }

    public void BuyNowButton(View view) {

        OrderDetailModel product = new OrderDetailModel(productData.get(position).getProductName(),
                productData.get(position).getProductId(),
                elegantNumberButton.getNumber(),
                productData.get(position).getPriceOnSale(),
                productData.get(position).getProductImage()
                );
        ArrayList<OrderDetailModel> itemData = new ArrayList<>();
        itemData.add(product);
        startActivity(new Intent(ShowProductActivity.this,PlaceOrderActivity.class).putExtra("Data",itemData));

    }
}
