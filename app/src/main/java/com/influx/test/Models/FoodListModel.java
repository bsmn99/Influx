package com.influx.test.Models;


import java.io.Serializable;
import java.util.ArrayList;

public class FoodListModel implements Serializable {

    private String TabName;
    private ArrayList<MainItemModel> fnblist;

    public String getTabName() {
        return TabName;
    }

    public void setTabName(String tabName) {
        TabName = tabName;
    }

    public ArrayList<MainItemModel> getFnblist() {
        return fnblist;
    }

    public void setFnblist(ArrayList<MainItemModel> fnblist) {
        this.fnblist = fnblist;
    }
}
