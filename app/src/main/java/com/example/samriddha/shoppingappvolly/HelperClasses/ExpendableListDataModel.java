package com.example.samriddha.shoppingappvolly.HelperClasses;

public class ExpendableListDataModel {


    public String menuName , catgId ;
    public boolean hasChildren, isGroup;

    public ExpendableListDataModel(String menuName, boolean isGroup, boolean hasChildren, String catgId) {

        this.menuName = menuName;
        this.catgId = catgId;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;

    }



}
