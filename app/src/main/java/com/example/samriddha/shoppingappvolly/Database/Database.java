package com.example.samriddha.shoppingappvolly.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.samriddha.shoppingappvolly.HelperClasses.OrderDetailModel;
import com.example.samriddha.shoppingappvolly.HelperClasses.WishlistModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "samshopcartDB.db" ;
    private static final int DB_VER = 1 ;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    public ArrayList<OrderDetailModel> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductID","ProductName","Quantity","Price","Image"} ;
        String sqlTable = "OrderDetail" ;

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final ArrayList<OrderDetailModel> result = new ArrayList<>();

        if (cursor.moveToFirst()){

            do{
                result.add(new OrderDetailModel(cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Image"))
                        ));
            }while (cursor.moveToNext());

        }

        return result ;
        }

    public void addToCart(OrderDetailModel orderDetailModel){

            SQLiteDatabase db = getReadableDatabase() ;

            String quary = String.format("INSERT INTO OrderDetail(ProductID,ProductName,Quantity,Price,Image)VALUES('%s','%s','%s','%s','%s');",
                    orderDetailModel.getProductID(),
                    orderDetailModel.getProductName(),
                    orderDetailModel.getQuantity(),
                    orderDetailModel.getPrice(),
                    orderDetailModel.getImage()
                    );

            db.execSQL(quary);

        }

    public void cleanCart(){

        SQLiteDatabase db = getReadableDatabase() ;

        String quary = String.format("DELETE FROM OrderDetail");
        db.execSQL(quary);

    }

    public void removeFromCart(String productID){

        SQLiteDatabase db = getReadableDatabase();
        String quary = String.format("DELETE FROM OrderDetail WHERE ProductID='%s';",productID);
        db.execSQL(quary);

    }

    public boolean isInCart(String productId){

        SQLiteDatabase db = getReadableDatabase();
        String quary = String.format("SELECT * FROM OrderDetail WHERE ProductID='%s';",productId);

        Cursor cursor = db.rawQuery(quary,null);

        if (cursor.getCount()<= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;

    }


    public void addToWishlist(WishlistModel item){

        SQLiteDatabase db = getReadableDatabase();
        String quary = String.format("INSERT INTO Wishlist(ProductID,ProductName,Price,Image)VALUES('%s','%s','%s','%s');",
                item.getProductID(),
                item.getProductName(),
                item.getPrice(),
                item.getImage()
        );

        db.execSQL(quary);
    }

    public void removeFromWishlist(String productId){

        SQLiteDatabase db = getReadableDatabase();
        String quary = String.format("DELETE FROM Wishlist WHERE ProductID='%s';",productId);
        db.execSQL(quary);
    }

    public boolean isWishlist(String productId){

        SQLiteDatabase db = getReadableDatabase();
        String quary = String.format("SELECT * FROM Wishlist WHERE ProductID='%s';",productId);

        Cursor cursor = db.rawQuery(quary,null);

        if (cursor.getCount()<= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;

    }

    public List<WishlistModel> getAllWishlist(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductID","ProductName","Price","Image"} ;
        String sqlTable = "Wishlist" ;

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<WishlistModel> result = new ArrayList<>();

        if (cursor.moveToFirst()){

            do{
                result.add(new WishlistModel(cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Image"))
                ));
            }while (cursor.moveToNext());

        }

        return result ;
    }




}
