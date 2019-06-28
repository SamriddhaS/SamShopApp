package com.example.samriddha.shoppingappvolly.HelperClasses;

import java.io.Serializable;

public class ProductDetailHelperClass implements Serializable{

    String productName , productImage, originalPrice , priceOnSale , productAval , productId , catagoriId ;

    public String getCatagoriId() {
        return catagoriId;
    }

    public void setCatagoriId(String catagoriId) {
        this.catagoriId = catagoriId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPriceOnSale() {
        return priceOnSale;
    }

    public void setPriceOnSale(String priceOnSale) {
        this.priceOnSale = priceOnSale;
    }

    public String getProductAval() {
        return productAval;
    }

    public void setProductAval(String productAval) {
        this.productAval = productAval;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
