package com.example.samriddha.shoppingappvolly.HelperClasses;

import java.io.Serializable;

public class OrderDetailModel implements Serializable{

    private String ProductName, ProductID, Quantity , Price , Image;

    public OrderDetailModel() {

    }

    public OrderDetailModel(String productName, String productID, String quantity, String price, String image) {
        ProductName = productName;
        ProductID = productID;
        Quantity = quantity;
        Price = price;
        Image = image;

    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

}

