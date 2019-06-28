package com.example.samriddha.shoppingappvolly.HelperClasses;

public class SubCatModel {

    private String CatId , Name , Image ;

    public SubCatModel() {
    }

    public SubCatModel(String catId, String name, String image) {
        CatId = catId;
        Name = name;
        Image = image;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
