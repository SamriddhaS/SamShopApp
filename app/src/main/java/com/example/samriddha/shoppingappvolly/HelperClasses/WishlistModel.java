package com.example.samriddha.shoppingappvolly.HelperClasses;

public class WishlistModel {

    private String ProductName, ProductID, Price , Image;

    public WishlistModel() {
    }

    public WishlistModel(String productName, String productID, String price, String image) {
        ProductName = productName;
        ProductID = productID;
        Price = price;
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
