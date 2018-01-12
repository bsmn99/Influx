package com.influx.test.Models;


import java.util.ArrayList;

public class ItemModel {

    private String Currency;
    private ArrayList<FoodListModel> FoodList;

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public ArrayList<FoodListModel> getFoodList() {
        return FoodList;
    }

    public void setFoodList(ArrayList<FoodListModel> foodList) {
        FoodList = foodList;
    }
}
