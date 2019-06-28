package com.example.samriddha.shoppingappvolly;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.samriddha.shoppingappvolly.Fragments.FragProducts;
import com.example.samriddha.shoppingappvolly.HelperClasses.BannerData;
import com.example.samriddha.shoppingappvolly.HelperClasses.ExpendableListDataModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.ProductDetailHelperClass;
import com.example.samriddha.shoppingappvolly.HelperClasses.SubCatModel;
import com.example.samriddha.shoppingappvolly.ViewHolder.CusExpendableListAdaptar;
import com.example.samriddha.shoppingappvolly.ViewHolder.CustomDialogClass;
import com.example.samriddha.shoppingappvolly.ViewHolder.CustomProductLvAdaptar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class AppHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle mDrawerToggle ;
    private ExpandableListView expandableListView ;
    private ExpandableListAdapter expandableListAdapter ;
    private Toolbar toolbar;
    TextView accName , accEmail ;
    private List<ExpendableListDataModel> headerList = new ArrayList<>();
    private HashMap<ExpendableListDataModel, List<ExpendableListDataModel>> childList = new HashMap<>();
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SubCatModel> subCatList = new ArrayList<>();
    private List<BannerData> bannerData ;
    private SliderLayout bannerSlidder , bannerMan , bannerWoman , bannerKids ;
    private HashMap<String,List<BannerData>> banSubCatData ;
    private FrameLayout frameLayout ;
    private LinearLayout linearLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);

        linearLayout = (LinearLayout)findViewById(R.id.lin_id);
        frameLayout = (FrameLayout)findViewById(R.id.container);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout_id);
        expandableListView =(ExpandableListView)findViewById(R.id.navListViewid) ;
        //recyclerView = (RecyclerView)findViewById(R.id.apphomerecview_id);
        Paper.init(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adding Header and Footer Layout To the Nagivation Drawer
        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_drawer_hader,null,false);
        View listfooterView = getLayoutInflater().inflate(R.layout.nav_drawer_footer,null,false);
        expandableListView.addHeaderView(listHeaderView);
        expandableListView.addFooterView(listfooterView);

        //initialize textViews of Header layout
        accName = listHeaderView.findViewById(R.id.accNameNavHead_id);
        accEmail = listHeaderView.findViewById(R.id.accEmailNavHead_id);


        prepareExListData();

        setUpExListView();

        setUpDrawer();

        //if (savedInstanceState == null)
            //setFirstItemByDefault();

        getSupportActionBar().setTitle("SamShop App");

        setNameAndEmailNavHead();

        //recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        setMainBanner();

        loadSubCatList();

    }

    private void setMainBanner() {

        prepareMainBannerData();

        bannerSlidder = (SliderLayout)findViewById(R.id.sliderlayour_id);

        for (int i = 0 ; i < bannerData.size(); i++){

            TextSliderView textSliderView = new TextSliderView(AppHomeActivity.this);
             textSliderView.description(bannerData.get(i).title)
                     .image(bannerData.get(i).image)
                     .setScaleType(BaseSliderView.ScaleType.Fit);


             bannerSlidder.addSlider(textSliderView);

        }

        bannerSlidder.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        bannerSlidder.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannerSlidder.setCustomAnimation(new DescriptionAnimation());
        bannerSlidder.setDuration(5000);

    }

    private void prepareMainBannerData() {

        bannerData = new ArrayList<>();



        BannerData currentData = new BannerData("0","Welcome To SamShop App!",R.drawable.banner1);
        bannerData.add(currentData);

        currentData = new BannerData("1","Start Shopping Your Favourite Products!",R.drawable.banner2);
        bannerData.add(currentData);

        currentData = new BannerData("2","Get upto 50% off On Various Products!",R.drawable.banner3);
        bannerData.add(currentData);

        currentData = new BannerData("3","Keep Shopping!",R.drawable.banner4);
        bannerData.add(currentData);

        currentData = new BannerData("4","All You Need Is Just A click Away! ",R.drawable.banner5);
        bannerData.add(currentData);


    }

    private void loadSubCatList() {

        bannerMan = (SliderLayout)findViewById(R.id.slidermen_id);
        bannerWoman = (SliderLayout)findViewById(R.id.sliderwomen_id);
        bannerKids = (SliderLayout)findViewById(R.id.sliderkid_id);

        prepareSubBannerData();

        for (int i = 0 ; i < banSubCatData.get("Man").size(); i++){

            final BannerData bannerData = banSubCatData.get("Man").get(i);
            TextSliderView textSliderView = new TextSliderView(AppHomeActivity.this);
            textSliderView.description(bannerData.title)
                    .image(bannerData.image)
                    .setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                    if (bannerData.id=="8")
                        getSupportActionBar().setTitle("Men's Clothing");
                    else if (bannerData.id=="9")
                        getSupportActionBar().setTitle("Men's Shoes");
                    else if (bannerData.id=="10")
                        getSupportActionBar().setTitle("Men's Accessories");

                    FragmentManager fragmentManager = getSupportFragmentManager() ;

                    linearLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("CatId",bannerData.id);
                    FragProducts fragProducts = new FragProducts();
                    fragProducts.setArguments(bundle);

                    fragmentManager.beginTransaction().replace(R.id.container,fragProducts).commit();

                }
            });

            bannerMan.addSlider(textSliderView);

        }

        bannerMan.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        bannerMan.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannerMan.setCustomAnimation(new DescriptionAnimation());
        bannerMan.setDuration(3000);


        for (int i = 0 ; i < banSubCatData.get("Woman").size(); i++){

            final BannerData bannerData = banSubCatData.get("Woman").get(i);
            TextSliderView textSliderView = new TextSliderView(AppHomeActivity.this);
            textSliderView.description(bannerData.title)
                    .image(bannerData.image)
                    .setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                    if (bannerData.id=="4")
                        getSupportActionBar().setTitle("Women's Clothing");
                    else if (bannerData.id=="5")
                        getSupportActionBar().setTitle("Women's Shoes");
                    else if (bannerData.id=="6")
                        getSupportActionBar().setTitle("Women's Bags");
                    else if (bannerData.id=="7")
                        getSupportActionBar().setTitle("Women's Watch & Accessories");

                    FragmentManager fragmentManager = getSupportFragmentManager() ;

                    linearLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("CatId",bannerData.id);
                    FragProducts fragProducts = new FragProducts();
                    fragProducts.setArguments(bundle);


                    fragmentManager.beginTransaction().replace(R.id.container,fragProducts).commit();

                }
            });

            bannerWoman.addSlider(textSliderView);

        }

        bannerWoman.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        bannerWoman.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannerWoman.setCustomAnimation(new DescriptionAnimation());
        bannerWoman.setDuration(3000);


        for (int i = 0 ; i < banSubCatData.get("Kid").size(); i++){

            final BannerData bannerData = banSubCatData.get("Kid").get(i);
            TextSliderView textSliderView = new TextSliderView(AppHomeActivity.this);
            textSliderView.description(bannerData.title)
                    .image(bannerData.image)
                    .setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                    if (bannerData.id=="11")
                        getSupportActionBar().setTitle("Kid's Clothing");
                    else if (bannerData.id=="12")
                        getSupportActionBar().setTitle("Kids's Toys & Games");
                    else if (bannerData.id=="13")
                        getSupportActionBar().setTitle("Kid's Bedding & Batches");

                    FragmentManager fragmentManager = getSupportFragmentManager() ;


                    linearLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);

                    Bundle bundle = new Bundle();
                    bundle.putString("CatId",bannerData.id);
                    FragProducts fragProducts = new FragProducts();
                    fragProducts.setArguments(bundle);


                    fragmentManager.beginTransaction().replace(R.id.container,fragProducts).commit();

                }
            });

            bannerKids.addSlider(textSliderView);

        }

        bannerKids.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        bannerKids.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannerKids.setCustomAnimation(new DescriptionAnimation());
        bannerKids.setDuration(3000);


    }

    private void prepareSubBannerData() {

        banSubCatData = new HashMap<>();

        List<BannerData> subBan = new ArrayList<>();

        BannerData curData = new BannerData("8","Clothing's For Men",R.drawable.man1);
        subBan.add(curData);

        curData = new BannerData("9","Shoes For Men",R.drawable.man2);
        subBan.add(curData);

        curData = new BannerData("10","Accessories For Men",R.drawable.man3);
        subBan.add(curData);

        banSubCatData.put("Man",subBan) ;

        subBan = new ArrayList<>();

        curData = new BannerData("4","Clothing's For Women",R.drawable.women2);
        subBan.add(curData);

        curData = new BannerData("5","Shoes For Women",R.drawable.women3);
        subBan.add(curData);

        curData = new BannerData("6","Handbags For Women",R.drawable.women4);
        subBan.add(curData);

        curData = new BannerData("7","Watches And Jewelry",R.drawable.women5);
        subBan.add(curData);

        banSubCatData.put("Woman",subBan) ;


        subBan = new ArrayList<>();

        curData = new BannerData("11","Clothing's For Kids",R.drawable.kids);
        subBan.add(curData);

        curData = new BannerData("12","Toys And Gifts",R.drawable.kids1);
        subBan.add(curData);

        curData = new BannerData("13","Bedding For Kids",R.drawable.kids2);
        subBan.add(curData);

        banSubCatData.put("Kid",subBan) ;

    }

    private void setUpExListView() {


        expandableListAdapter = new CusExpendableListAdaptar(this, headerList , childList);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    ExpendableListDataModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    String selctedItem = model.menuName.toString();
                    getSupportActionBar().setTitle(selctedItem);

                    FragmentManager fragmentManager = getSupportFragmentManager() ;


                    linearLayout.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);

                    Bundle bundle = new Bundle();
                    bundle.putString("CatId",model.catgId);
                    FragProducts fragProducts = new FragProducts();
                    fragProducts.setArguments(bundle);


                    fragmentManager.beginTransaction().replace(R.id.container,fragProducts).commit();

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setNameAndEmailNavHead() {

        if (Common.Name == "") {
            accName.setText("Please LogIn");
            accEmail.setText("Please LogIn");
        }else{
            accName.setText(Common.Name);
            accEmail.setText(Common.Email);

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    private void setFirstItemByDefault() {

    }

    private void setUpDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.Open,R.string.Close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    private void prepareExListData() {


        ExpendableListDataModel modelListHeader = new ExpendableListDataModel("Women", true, true, "1");
        headerList.add(modelListHeader);
        List<ExpendableListDataModel> childModelsList = new ArrayList<>();
        ExpendableListDataModel childModel = new ExpendableListDataModel("Clothing Women", false, false, "4");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Shoes Women", false, false, "5");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Hand Bags", false, false, "6");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Jewelry And Watches", false, false, "7");
        childModelsList.add(childModel);

        if (modelListHeader.hasChildren) {
            Log.d("API123","here");
            childList.put(modelListHeader, childModelsList);
        }

        childModelsList = new ArrayList<>();
        modelListHeader = new ExpendableListDataModel("Men", true, true, "");
        headerList.add(modelListHeader);

        childModel = new ExpendableListDataModel("Clothing Men", false, false, "8");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Shoes Men" , false, false, "9");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Accessories Men", false, false, "10");
        childModelsList.add(childModel);

        if (modelListHeader.hasChildren) {
            childList.put(modelListHeader, childModelsList);
        }

        childModelsList = new ArrayList<>();
        modelListHeader = new ExpendableListDataModel("Kids", true, true, "");
        headerList.add(modelListHeader);

        childModel = new ExpendableListDataModel("Bedding And Batch", false, false, "11");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Toys,Games & Books" , false, false, "12");
        childModelsList.add(childModel);

        childModel = new ExpendableListDataModel("Clothing,Shoes & Accessories", false, false, "13");
        childModelsList.add(childModel);

        if (modelListHeader.hasChildren) {
            childList.put(modelListHeader, childModelsList);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       switch (id){

           case R.id.menumyprofile_id :

               startActivity(new Intent(this,MyProfileActivity.class));
               break;

           case R.id.menucart_id :

               startActivity(new Intent(this,MyCartActivity.class));
               break;

           case R.id.menumywish_id :

               startActivity(new Intent(AppHomeActivity.this,WishlistActivity.class));
               break;

           case R.id.menulogout_id :

               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setIcon(R.drawable.ic_warning_black_24dp);
               builder.setTitle(" Notification !!!");
               builder.setMessage("Do You Really Want To Log Out ?");
               builder.setCancelable(false);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       Paper.book().destroy();
                       startActivity(new Intent(AppHomeActivity.this,LogInActivity.class));
                       finish();
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               builder.show();
               break;

       }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (linearLayout.getVisibility()==View.INVISIBLE){

            frameLayout.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("SamShop App");
        }else {

            CustomDialogClass customDialogClass = new CustomDialogClass(AppHomeActivity.this);
            customDialogClass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialogClass.show();
        }

    }

    public void OpenMyProfileMethod(View view) {

        startActivity(new Intent(AppHomeActivity.this,MyProfileActivity.class));
    }

    public void OpenMyWishlistMethod(View view) {

        startActivity(new Intent(AppHomeActivity.this,WishlistActivity.class));
    }

    public void OpenMyCartMethod(View view) {

        startActivity(new Intent(AppHomeActivity.this,MyCartActivity.class));
    }


    public void AboutAppButton(View view) {

        getSupportActionBar().setTitle("About The App");

        FragmentManager fragmentManager = getSupportFragmentManager() ;

        linearLayout.setVisibility(View.INVISIBLE);
        frameLayout.setVisibility(View.VISIBLE);

        Bundle bundle = new Bundle();
        bundle.putString("CatId","");
        FragProducts fragProducts = new FragProducts();
        fragProducts.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.container,fragProducts).commit();

        drawerLayout.closeDrawer(GravityCompat.START);

    }
}
